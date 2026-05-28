package com.caleta.captura.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.caleta.captura.dto.EspecieResponse;
import com.caleta.captura.model.Captura;
import com.caleta.captura.repository.CapturaRepository;

@Service
public class CapturaService {
    @Autowired
    private CapturaRepository capturaRepository;

    @Autowired
    private WebClient especieWebClient;

    public List<Captura> getCapturas(){
        return capturaRepository.findAll();
    }

    public Captura getCapturaById(Long id){
        return capturaRepository.findById(id).orElse(null);
    }

    public String deleteCaptura(Long id){
        capturaRepository.deleteById(id);
        return "Captura " + id + " eliminada";
    }

    public Captura updateCaptura(Long id, Captura capturaAct){
        Captura captura = capturaRepository.findById(id).orElse(null);

        if (captura != null){
            captura.setKilos(capturaAct.getKilos());
            captura.setEspecieId(capturaAct.getEspecieId());
            return capturaRepository.save(captura);
        }
        return null;
    }

    //NEGOCIO

    public Captura saveCaptura(Captura captura){
        //Validar kilos
        if (captura.getKilos() <= 0) {
            throw new RuntimeException("Los kilos deben ser mayores a 0");
        }

        EspecieResponse especie = especieWebClient.get()
            .uri("/{id}", captura.getEspecieId())
            .retrieve()
            .bodyToMono(EspecieResponse.class)
            .block();

        if (especie == null) {
            throw new RuntimeException("Especie no encontrada");
        }

        if (especie.isEnVeda()){
            throw new RuntimeException("La especie " + especie.getNombre() + " está en veda");
        }

        return capturaRepository.save(captura);
    }



}
