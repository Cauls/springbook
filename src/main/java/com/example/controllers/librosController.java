package com.example.controllers;

import com.example.entities.Clientes;
import com.example.entities.Libros;
import com.example.repositories.LibrosRepository;
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
import java.sql.Date;

@Controller
public class librosController {

    @Autowired
    private LibrosRepository librosRepository;

    @GetMapping("/libros")
    public String mostrarLibros(Model model) {
        model.addAttribute("libros", librosRepository.findAll());
        return "libros";
    }

    @PostMapping("/libros")
    public String agregarLibro(@ModelAttribute Libros libro) {
        librosRepository.save(libro);
        return "redirect:/libros";
    }

    @PostMapping("/libros/eliminar")
    public String eliminarLibro(@RequestParam("idLibro") Long idLibro, Model model) {
        if (librosRepository.existsById(idLibro)) {
            librosRepository.deleteById(idLibro);
        } else {
            model.addAttribute("error", "El ID proporcionado no existe.");
        }
        return "redirect:/libros";
    }

    @GetMapping("/libros/editar")
    public String mostrarFormularioEdicion(@RequestParam("idLibro") Long idLibro, Model model) {
        Libros libro = librosRepository.findById(idLibro).orElseThrow(() -> new IllegalArgumentException("ID no válido: " + idLibro));
        model.addAttribute("libro", libro);
        return "editar-libros";
    }

    @PostMapping("/libros/editar")
    public String actualizarLibro(@ModelAttribute Libros libro) {
        librosRepository.save(libro);
        return "redirect:/libros";
    }

    @PostMapping("/libros/importar")
    public String importarCSV(@RequestParam("file") MultipartFile file, Model model) {
        if (!file.isEmpty()) {
            try{
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
                while ((line = br.readLine()) != null) {
                    String[] datos = line.split(",");
                    if (datos.length == 7) {
                        Libros libro = new Libros();
                        libro.setTitulo(datos[1]);
                        libro.setAutor(datos[2]);
                        libro.setIdAutor(Long.parseLong(datos[3]));
                        libro.setEditorial(datos[4]);
                        libro.setPublicacion(datos[5]);
                        libro.setIsbn(Long.parseLong(datos[6]));
                        librosRepository.save(libro);
                    } else {
                        model.addAttribute("error", "Formato de archivo incorrecto");
                    }
                }
            }catch (IOException e) {
                model.addAttribute("error", "Error al importar el archivo: " + e.getMessage());
            }
        }

        return "redirect:/libros";
    }
}
