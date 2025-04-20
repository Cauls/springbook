package com.example.controllers;

import com.example.entities.Autores;
import com.example.repositories.AutoresRepository;
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
public class autoresController {

    @Autowired
    private AutoresRepository autoresRepository;

    @GetMapping("/autores")
    public String mostrarAutores(Model model) {
        model.addAttribute("autores", autoresRepository.findAll());
        return "autores";
    }

    @PostMapping("/autores")
    public String agregarAutor(@ModelAttribute Autores autor) {
        autoresRepository.save(autor);
        return "redirect:/autores";
    }

    @PostMapping("/autores/eliminar")
    public String eliminarAutor(@RequestParam("idAutor") Long idAutor, Model model) {
        if (autoresRepository.existsById(idAutor)) {
            autoresRepository.deleteById(idAutor);
        } else {
            model.addAttribute("error", "El ID proporcionado no existe.");
        }
        return "redirect:/autores";
    }

    @GetMapping("/autores/editar")
    public String mostrarFormularioEdicion(@RequestParam("idAutor") Long idAutor, Model model) {
        Autores autor = autoresRepository.findById(idAutor).orElseThrow(() -> new IllegalArgumentException("ID no válido: " + idAutor));
        model.addAttribute("autor", autor);
        return "editar-autores";
    }

    @PostMapping("/autores/editar")
    public String actualizarAutor(@ModelAttribute Autores autor) {
            autoresRepository.save(autor);
        return "redirect:/autores";
    }

    @PostMapping("/autores/importar")
    public String importarCSV(@RequestParam("file") MultipartFile file, Model model) {
        if (!file.isEmpty()) {
            try{
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
                while ((line = br.readLine()) != null) {
                    String[] datos = line.split(",");
                    if (datos.length == 3) {
                        Autores autor = new Autores();
                        autor.setNombre(datos[1]);
                        autor.setApellidos(datos[2]);
                        autoresRepository.save(autor);
                    } else {
                        model.addAttribute("error", "Formato de archivo incorrecto");
                    }
                }
            }catch (IOException e) {
                model.addAttribute("error", "Error al importar el archivo: " + e.getMessage());
            }
        }

        return "redirect:/autores";
    }
}
