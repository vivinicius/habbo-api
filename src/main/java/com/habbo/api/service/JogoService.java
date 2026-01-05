package com.habbo.api.service;

import com.habbo.api.model.Jogo;
import com.habbo.api.repository.JogoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JogoService {

    private final JogoRepository repository;

    public Jogo criarJogo(String nome, Integer valorInicial, Integer percentualPague) {
        Jogo jogo = new Jogo();
        jogo.setNome(nome);
        jogo.setValorInicial(valorInicial);
        jogo.setPercentualPague(percentualPague);

        // cálculo inicial do pague
        double perc = percentualPague / 100.0;
        int pagamentoRodada = (int) Math.round((valorInicial) * perc);

        jogo.setValorPagamentoRodada(pagamentoRodada);

        jogo.setLucroHost(0);
        jogo.setLucroGanhador(0);
        jogo.setTotalSemValorPremio(0);

        return repository.save(jogo);
    }


    public JogoService(JogoRepository repository) {
        this.repository = repository;
    }

    public List<Jogo> listar() {
        return repository.findAll();
    }

    public Jogo buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deletarJogo(Long jogoId) {
        Jogo jogo = repository.findById(jogoId)
                .orElseThrow(() -> new IllegalArgumentException("Jogo não encontrado."));

        repository.delete(jogo);
    }


}
