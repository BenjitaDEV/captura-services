package com.caleta.captura.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caleta.captura.exception.ResourceNotFoundException;
import com.caleta.captura.exception.GlobalExceptionHandler;
import com.caleta.captura.model.Captura;
import com.caleta.captura.services.CapturaService;

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

    @GetMapping
    

}
