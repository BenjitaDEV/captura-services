package com.caleta.captura.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caleta.captura.dto.CreateCapturaRequest;
import com.caleta.captura.dto.UpdateCapturaRequest;
import com.caleta.captura.exception.ResourceNotFoundException;
import com.caleta.captura.model.Captura;
import com.caleta.captura.services.CapturaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/capturas")
@Tag(name = "Capturas", description = "API para gestionar capturas")
public class CapturaController {

    private final CapturaService capturaService;

    public CapturaController(CapturaService capturaService) {
        this.capturaService = capturaService;
    }

    @Operation(
            summary = "Listar capturas",
            description = "Obtiene todas las capturas registradas"
    )
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<Captura>> listarCapturas() {
        return ResponseEntity.ok(capturaService.getCapturas());
    }

    @Operation(
            summary = "Buscar captura por ID",
            description = "Obtiene una captura mediante su ID"
    )
    @ApiResponse(responseCode = "200", description = "Captura encontrada")
    @ApiResponse(responseCode = "404", description = "Captura no encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<Captura> buscarCaptura(@PathVariable Long id) {

        Captura captura = capturaService.getCapturaById(id);

        if (captura == null) {
            throw new ResourceNotFoundException("Captura no encontrada con id: " + id);
        }

        return ResponseEntity.ok(captura);
    }

    @Operation(
            summary = "Buscar capturas por especie",
            description = "Obtiene todas las capturas asociadas a una especie"
    )
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping("/especie/{id}")
    public ResponseEntity<List<Captura>> obtenerPorEspecie(@PathVariable Long id) {
        return ResponseEntity.ok(capturaService.getByEspecie(id));
    }

    @Operation(
            summary = "Crear captura",
            description = "Registra una nueva captura"
    )
    @ApiResponse(responseCode = "201", description = "Captura creada correctamente")
    @PostMapping
    public ResponseEntity<Captura> crearCaptura(

            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos para registrar una captura",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Ejemplo de creación",
                                    summary = "Crear captura",
                                    value = """
                                    {
                                      "especieId": 1,
                                      "kilos": 350.5,
                                      "fecha": "2026-07-03"
                                    }
                                    """
                            )
                    )
            )
            @org.springframework.web.bind.annotation.RequestBody CreateCapturaRequest request) {

        Captura capturaNueva = capturaService.saveCaptura(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(capturaNueva);
    }

    @Operation(
            summary = "Actualizar captura",
            description = "Actualiza la información de una captura"
    )
    @ApiResponse(responseCode = "200", description = "Captura actualizada correctamente")
    @ApiResponse(responseCode = "404", description = "Captura no encontrada")
    @PutMapping("/{id}")
    public ResponseEntity<Captura> actualizarCaptura(

            @PathVariable Long id,

            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos para actualizar una captura",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Ejemplo de actualización",
                                    summary = "Actualizar captura",
                                    value = """
                                    {
                                      "especieId": 2,
                                      "kilos": 420.75,
                                      "fecha": "2026-07-10"
                                    }
                                    """
                            )
                    )
            )
            @org.springframework.web.bind.annotation.RequestBody UpdateCapturaRequest request) {

        Captura capturaAct = capturaService.updateCaptura(id, request);

        if (capturaAct == null) {
            throw new ResourceNotFoundException("Captura no encontrada con id: " + id);
        }

        return ResponseEntity.ok(capturaAct);
    }

    @Operation(
            summary = "Eliminar captura",
            description = "Elimina una captura por su ID"
    )
    @ApiResponse(responseCode = "204", description = "Captura eliminada correctamente")
    @ApiResponse(responseCode = "404", description = "Captura no encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCaptura(@PathVariable Long id) {

        capturaService.deleteCaptura(id);

        return ResponseEntity.noContent().build();
    }

}