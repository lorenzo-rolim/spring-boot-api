package com.gvendas.gestaovendas.repositories;

import com.gvendas.gestaovendas.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
