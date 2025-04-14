package com.example.controllers;

import com.example.entities.Libros;
import com.example.repositories.LibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

}
