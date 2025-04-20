package com.example.controllers;

import com.example.entities.Autores;
import com.example.entities.Libros;
import com.example.entities.Prestamos;
import com.example.repositories.PrestamosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Controller
public class prestamosController {

    @Autowired
    private PrestamosRepository prestamosRepository;

    @GetMapping("/prestamos")
    public String mostrarPrestamos(Model model) {
        model.addAttribute("prestamos", prestamosRepository.findAll());
        return "prestamos";
    }

    @PostMapping("/prestamos")
    public String agregarPrestamo(@ModelAttribute Prestamos prestamo) {
        prestamosRepository.save(prestamo);
        return "redirect:/prestamos";
    }

    @PostMapping("/prestamos/eliminar")
    public String eliminarPrestamo(@RequestParam("idPrestamo") Long idPrestamo, Model model) {
        if (prestamosRepository.existsById(idPrestamo)) {
            prestamosRepository.deleteById(idPrestamo);
        } else {
            model.addAttribute("error", "El ID proporcionado no existe.");
        }
        return "redirect:/prestamos";
    }

    @GetMapping("/prestamos/editar")
    public String mostrarFormularioEdicion(@RequestParam("idPrestamo") Long idPrestamo, Model model) {
        Prestamos prestamo = prestamosRepository.findById(idPrestamo).orElseThrow(() -> new IllegalArgumentException("ID no válido: " + idPrestamo));
        model.addAttribute("prestamo", prestamo);
        return "editar-prestamos";
    }

    @PostMapping("/prestamos/editar")
    public String actualizarPrestamo(@ModelAttribute Prestamos prestamo) {
        prestamosRepository.save(prestamo);
        return "redirect:/prestamos";
    }

    @PostMapping("/prestamos/importar")
    public String importarCSV(@RequestParam("file") MultipartFile file, Model model) {
        if (!file.isEmpty()) {
            try {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
                while ((line = br.readLine()) != null) {
                    String[] datos = line.split(",");
                    if (datos.length == 5) {
                        Prestamos prestamo = new Prestamos();
                        prestamo.setIdLibro(Long.parseLong(datos[1]));
                        prestamo.setIdCliente(Long.parseLong(datos[2]));
                        prestamo.setFechaPrestamo(datos[3]);
                        prestamo.setFechaDevolucion(datos[4]);
                        prestamosRepository.save(prestamo);
                    } else {
                        model.addAttribute("error", "Formato de archivo incorrecto");
                    }
                }
            } catch (IOException e) {
                model.addAttribute("error", "Error al importar el archivo: " + e.getMessage());
            }
        }
        return "redirect:/prestamos";
    }
}