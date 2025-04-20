package com.example.controllers;

import com.example.entities.Autores;
import com.example.entities.Clientes;
import com.example.repositories.ClientesRepository;
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
public class clientesController {

    @Autowired
    private ClientesRepository clientesRepository;

    @GetMapping("/clientes")
    public String mostrarClientes(Model model) {
        model.addAttribute("clientes", clientesRepository.findAll());
        return "clientes";
    }

    @PostMapping("/clientes")
    public String agregarCliente(@ModelAttribute Clientes cliente) {
        clientesRepository.save(cliente);
        return "redirect:/clientes";
    }

    @PostMapping("/clientes/eliminar")
    public String eliminarCliente(@RequestParam("idCliente") Long idCliente, Model model) {
        if (clientesRepository.existsById(idCliente)) {
            clientesRepository.deleteById(idCliente);
        } else {
            model.addAttribute("error", "El ID proporcionado no existe.");
        }
        return "redirect:/clientes";
    }

    @GetMapping("/clientes/editar")
    public String mostrarFormularioEdicion(@RequestParam("idCliente") Long idCliente, Model model) {
        Clientes cliente = clientesRepository.findById(idCliente).orElseThrow(() -> new IllegalArgumentException("ID no válido: " + idCliente));
        model.addAttribute("cliente", cliente);
        return "editar-clientes";
    }

    @PostMapping("/clientes/editar")
    public String actualizarAutor(@ModelAttribute Clientes cliente) {
        clientesRepository.save(cliente);
        return "redirect:/clientes";
    }

    @PostMapping("/clientes/importar")
    public String importarCSV(@RequestParam("file") MultipartFile file, Model model) {
        if (!file.isEmpty()) {
            try{
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
                while ((line = br.readLine()) != null) {
                    String[] datos = line.split(",");
                    if (datos.length == 6) {
                        Clientes cliente = new Clientes();
                        cliente.setNombre(datos[1]);
                        cliente.setApellidos(datos[2]);
                        cliente.setEmail(datos[3]);
                        cliente.setNumTelefono(Long.parseLong(datos[4]));
                        cliente.setDni(datos[5]);
                        clientesRepository.save(cliente);
                    } else {
                        model.addAttribute("error", "Formato de archivo incorrecto");
                    }
                }
            }catch (IOException e) {
                model.addAttribute("error", "Error al importar el archivo: " + e.getMessage());
            }
        }

        return "redirect:/clientes";
    }
}
