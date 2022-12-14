package com.gvendas.gestaovendas.controllers;

import com.gvendas.gestaovendas.entities.Categoria;
import com.gvendas.gestaovendas.services.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categoria")
@Tag(name = "Categoria", description = "Endpoints relacionados a categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Operation(summary = "Listar")
    @GetMapping
    public List<Categoria> listarTodas(){
        return categoriaService.listarTodas();
    }

    @Operation(summary = "Listar por código")
    @GetMapping("/{codigo}")
    public ResponseEntity<Optional<Categoria>> buscarPorId(@PathVariable Long codigo){
        Optional<Categoria> categoria = categoriaService.buscarPorId(codigo);
        return categoria.isPresent() ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Salvar")
    @PostMapping
    public ResponseEntity<Categoria> salvar(@Valid @RequestBody Categoria categoria){
        Categoria categoriaSalva = categoriaService.salvar(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
    }

    @Operation(summary = "Alterar Categoria")
    @PatchMapping("/{codigo}")
    public ResponseEntity<Categoria> atualizar(@PathVariable Long codigo, @Valid @RequestBody Categoria categoria){
        return ResponseEntity.ok().body(categoriaService.atualizar(codigo, categoria));
    }

    @Operation(summary = "Deletar Categoria")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{codigo}")
    public void deletarCategoria(@PathVariable Long codigo){
        categoriaService.deletar(codigo);
    }

}
