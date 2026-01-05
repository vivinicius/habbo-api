package com.habbo.api.repository;

import com.habbo.api.model.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    List<Movimentacao> findByJogadorId(Long jogadorId);
    Movimentacao findTopByJogadorIdOrderByIdDesc(Long jogadorId);
}
