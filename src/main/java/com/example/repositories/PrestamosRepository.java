package com.example.repositories;

import com.example.entities.Prestamos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PrestamosRepository extends JpaRepository<Prestamos, Long>, JpaSpecificationExecutor<Prestamos> {

}