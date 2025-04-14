package com.example.controllers;

import com.example.repositories.PrestamosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class prestamosController {

    @Autowired
    private PrestamosRepository prestamosRepository;

    @GetMapping("/prestamos")
    public String mostrarPrestamos(Model model) {
        model.addAttribute("prestamos", prestamosRepository.findAll());
        return "prestamos";
    }
}
