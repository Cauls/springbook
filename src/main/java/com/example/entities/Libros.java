package com.example.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Libros")
public class Libros implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_libro", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLibro;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "autor", nullable = false)
    private String autor;

    @Column(name = "id_autor", nullable = false)
    private Long idAutor;

    @Column(name = "editorial")
    private String editorial;

    @Column(name = "publicacion", nullable = false)
    private Date publicacion;

    @Column(name = "isbn", nullable = false)
    private Long isbn;



}
