package com.example.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "Prestamos")
public class Prestamos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_prestamo", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrestamo;

    @Column(name = "id_libro", nullable = false)
    private Long idLibro;

    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;

    @Column(name = "fecha_prestamo", nullable = false)
    private String fechaPrestamo;

    @Column(name = "fecha_devolucion", nullable = true)
    private String fechaDevolucion;

}
