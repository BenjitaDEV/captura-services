package com.caleta.captura.mapper;

import com.caleta.captura.dto.CreateCapturaRequest;
import com.caleta.captura.dto.UpdateCapturaRequest;
import com.caleta.captura.model.Captura;

public class CapturaMapper {

    //CREATE
    public static Captura toModel(CreateCapturaRequest request){
        return new Captura(
            null,
            request.especieId(),
            request.kilos(),
            request.fecha()
        );
    }

    //UPDATE
    public static Captura toModel(Long id, UpdateCapturaRequest request){
        return new Captura(
            id,
            request.especieId(),
            request.kilos(),
            request.fecha()
        );
    }
}
