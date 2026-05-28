package com.caleta.captura.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateCapturaRequest (
    @NotBlank(message = "Se necesita el ID de la especie") long especieId,
    @NotBlank(message = "Se necesita la cantidad de kilos") double kilos,
    @NotBlank(message = "Se necesita la fecha") java.time.LocalDate fecha
) {

}
