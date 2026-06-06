package com.caleta.captura.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.caleta.captura.dto.CreateCapturaRequest;
import com.caleta.captura.dto.EspecieResponse;
import com.caleta.captura.dto.UpdateCapturaRequest;
import com.caleta.captura.model.Captura;
import com.caleta.captura.repository.CapturaRepository;

@Service
public class CapturaService {

    private final CapturaRepository capturaRepository;
    private final WebClient especieWebClient;

    public CapturaService(CapturaRepository capturaRepository, WebClient especieWebClient) {
        this.capturaRepository = capturaRepository;
        this.especieWebClient = especieWebClient;
    }

    public List<Captura> getCapturas(){
        return capturaRepository.findAll();
    }

    public Captura getCapturaById(Long id){
        return capturaRepository.findById(id).orElse(null);
    }

    public List<Captura> getByEspecie(Long especieId){
        return capturaRepository.selectPorEspecieId(especieId);
    }

    public String deleteCaptura(Long id){
        capturaRepository.deleteById(id);
        return "Captura " + id + " eliminada";
    }

    public Captura updateCaptura(Long id, UpdateCapturaRequest request){
        Captura captura = capturaRepository.findById(id).orElseThrow(() -> new RuntimeException("Captura no encontrada"));


        if(request.kilos() <= 0){
            throw new RuntimeException("Los kilos deben ser mayores a 0");
        }

         EspecieResponse especie = especieWebClient.get()
            .uri("/{id}", request.especieId())
            .retrieve()
            .bodyToMono(EspecieResponse.class)
            .block();
        
        if (especie == null) {
            throw new RuntimeException("Especie no encontrada");
        }


        if (especie.isEnVeda()){
            throw new RuntimeException("La especie " + especie.getNombre() + " está en veda");
         }

        captura.setEspecieId(request.especieId());
        captura.setKilos(request.kilos());
        captura.setFecha(request.fecha());

        return capturaRepository.save(captura);
    }

    //NEGOCIO

    public Captura saveCaptura(CreateCapturaRequest request){
        //Validar kilos
        if (request.kilos() <= 0) {
            throw new RuntimeException("Los kilos deben ser mayores a 0");
        }

        EspecieResponse especie = especieWebClient.get()
            .uri("/{id}", request.especieId())
            .retrieve()
            .bodyToMono(EspecieResponse.class)
            .block();

        if (especie == null) {
            throw new RuntimeException("Especie no encontrada");
        }

        if (especie.isEnVeda()){
            throw new RuntimeException("La especie " + especie.getNombre() + " está en veda");
        }

        Captura captura = new Captura();
        captura.setEspecieId(request.especieId());
        captura.setKilos(request.kilos());
        captura.setFecha(request.fecha());

        return capturaRepository.save(captura);
    }



}
