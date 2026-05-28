package com.caleta.captura.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.caleta.captura.model.Captura;

@Repository
public interface CapturaRepository extends JpaRepository<Captura, Long> {

    @Query(value = "SELECT * FROM capturas WHERE especie_id = :especieId", nativeQuery = true)
    List<Captura> selectPorEspecieId(@Param("especieId") Long especieId);

}
