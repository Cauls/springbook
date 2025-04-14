package com.example.controllers;

import com.example.entities.Autores;
import com.example.repositories.AutoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
}
