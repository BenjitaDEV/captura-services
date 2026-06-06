package com.caleta.captura.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateCapturaRequest (
    @NotNull(message = "Se necesita el ID de la especie") long especieId,
    @Positive(message = "Se necesita la cantidad de kilos") double kilos,
    @NotNull(message = "Se necesita la fecha") java.time.LocalDate fecha
) {

}
