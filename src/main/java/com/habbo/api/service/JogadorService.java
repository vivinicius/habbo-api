package com.habbo.api.service;

import com.habbo.api.model.Jogador;
import com.habbo.api.model.Jogo;
import com.habbo.api.model.Movimentacao;
import com.habbo.api.repository.JogadorRepository;
import com.habbo.api.repository.JogoRepository;
import com.habbo.api.repository.MovimentacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JogadorService {

    private final JogadorRepository jogadorRepository;
    private final JogoRepository jogoRepository;
    private final MovimentacaoRepository movimentacaoRepository;
    private final MovimentacaoService movimentacaoService;

    public JogadorService(JogadorRepository jogadorRepository,
                          JogoRepository jogoRepository,
                          MovimentacaoRepository movimentacaoRepository,
                          MovimentacaoService movimentacaoService) {
        this.jogadorRepository = jogadorRepository;
        this.jogoRepository = jogoRepository;
        this.movimentacaoRepository = movimentacaoRepository;
        this.movimentacaoService = movimentacaoService;
    }

    public Jogador adicionarJogador(Long jogoId, String nomeJogador) {
        Jogo jogo = jogoRepository.findById(jogoId).orElseThrow();

        Jogador jogador = new Jogador();
        jogador.setNomeJogador(nomeJogador);
        jogador.setJogo(jogo);

        return jogadorRepository.save(jogador);
    }

    public Jogador adicionarJogadorComPague(Long jogoId, String nomeJogador, int valorPago) {
        Jogo jogo = jogoRepository.findById(jogoId)
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado"));

        int valorMinimo = jogo.getValorPagamentoRodada() * 2;

        if (valorPago < valorMinimo) {
            throw new IllegalArgumentException(
                    "Valor insuficiente para entrar no jogo. Mínimo: " + valorMinimo
            );
        }

        int saldo = valorPago - valorMinimo;

        Jogador jogador = new Jogador();
        jogador.setNomeJogador(nomeJogador);
        jogador.setJogo(jogo);
        jogador.setValorPago(jogador.getValorPago() + valorPago);

        jogadorRepository.save(jogador);

        Movimentacao mov = new Movimentacao();
        mov.setJogador(jogador);

        mov.setValor(valorPago);

        mov.setSaldo(saldo);

        mov.setEntradaComPague(true);

        movimentacaoRepository.save(mov);

        movimentacaoService.atualizarValores(jogoId);

        return jogador;
    }


    public List<Jogador> listarPorJogo(Long jogoId) {
        return jogadorRepository.findByJogoId(jogoId);
    }

    public void excluirJogador(Long jogoId, Long jogadorId) {
        Jogador jogador = jogadorRepository.findById(jogadorId)
                .orElseThrow(() -> new IllegalArgumentException("Jogador não encontrado."));

        // segurança extra
        if (!jogador.getJogo().getId().equals(jogoId)) {
            throw new IllegalArgumentException("Jogador não pertence a este jogo.");
        }

        // 🔥 deletar movimentações primeiro (para evitar erro de FK)
        movimentacaoRepository.deleteAll(
                movimentacaoRepository.findByJogadorId(jogadorId)
        );

        // agora pode deletar o jogador
        jogadorRepository.delete(jogador);

        // recalcular agora que os valores mudaram
        movimentacaoService.atualizarValores(jogoId);
    }
}
