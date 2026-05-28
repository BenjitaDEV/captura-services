package com.caleta.captura.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caleta.captura.dto.CreateCapturaRequest;
import com.caleta.captura.dto.UpdateCapturaRequest;
import com.caleta.captura.exception.ResourceNotFoundException;
import com.caleta.captura.mapper.CapturaMapper;
import com.caleta.captura.model.Captura;
import com.caleta.captura.services.CapturaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/capturas")
public class CapturaController {

    private final CapturaService capturaService;

    public CapturaController(CapturaService capturaService){
        this.capturaService = capturaService;
    }

    //obtener todo
    @GetMapping
    public ResponseEntity<List<Captura>> listarCapturas(){
        return ResponseEntity.ok(capturaService.getCapturas());
    }
    
    //obtener por id
    @GetMapping("/{id}")
    public ResponseEntity<Captura> buscarCaptura(@PathVariable Long id){

        Captura captura = capturaService.getCapturaById(id);

        if (captura == null){
            throw new ResourceNotFoundException("Captura no encontrada con id: " + id);
        }

        return ResponseEntity.ok(captura);
    }

    @GetMapping ("/especie/{id}")
    public ResponseEntity<List<Captura>> obtenerPorEspecie(@PathVariable Long id){
        return ResponseEntity.ok(capturaService.getByEspecie(id));
    }

    //CREATE
    @PostMapping
    public ResponseEntity<Captura> crearCaptura(@Valid @RequestBody CreateCapturaRequest request){
        Captura CapturaNueva = capturaService.saveCaptura(CapturaMapper.toModel(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(CapturaNueva);
    }

    //UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Captura> actualizarCaptura(@PathVariable Long id, @Valid @RequestBody UpdateCapturaRequest request){

        Captura CapturaAct = capturaService.updateCaptura(id, CapturaMapper.toModel(id, request));
        return ResponseEntity.ok(CapturaAct);
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCaptura(@PathVariable Long id){
        capturaService.deleteCaptura(id);
        return ResponseEntity.noContent().build();
    }
    

}
