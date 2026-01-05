package com.habbo.api.repository;

import com.habbo.api.model.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JogadorRepository extends JpaRepository<Jogador, Long> {

    List<Jogador> findByJogoId(Long jogoId);

}
