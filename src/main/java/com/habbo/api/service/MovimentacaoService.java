package com.habbo.api.service;

import com.habbo.api.model.Jogador;
import com.habbo.api.model.Movimentacao;
import com.habbo.api.repository.JogadorRepository;
import com.habbo.api.repository.JogoRepository;
import com.habbo.api.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimentacaoService {

    @Autowired
    private JogoRepository jogoRepository;

    private final MovimentacaoRepository repository;
    private final JogadorRepository jogadorRepository;

    public MovimentacaoService(MovimentacaoRepository repository,
                               JogadorRepository jogadorRepository) {
        this.repository = repository;
        this.jogadorRepository = jogadorRepository;
    }

    public List<Movimentacao> listar(Long jogadorId) {
        return repository.findByJogadorId(jogadorId);
    }

    public Movimentacao adicionarValor(Long jogadorId, int valorAdicionado, String descricao) {
        Jogador jogador = jogadorRepository.findById(jogadorId).orElseThrow();

        int valorRodada = jogador.getJogo().getValorPagamentoRodada();

        Movimentacao ultimaMov = repository.findTopByJogadorIdOrderByIdDesc(jogadorId);
        int saldoAtual = (ultimaMov != null) ? ultimaMov.getSaldo() : 0;

        int totalDisponivel = saldoAtual + valorAdicionado;

        if (totalDisponivel < valorRodada) {
            throw new IllegalArgumentException("Valor + saldo insuficientes para pagar a rodada.");
        }

        int novoSaldo = totalDisponivel - valorRodada;

        Movimentacao mov = new Movimentacao();
        mov.setValor(valorAdicionado);
        mov.setSaldo(novoSaldo);
        mov.setJogador(jogador);
        mov.setDescricao(descricao);
        mov.setEntradaComPague(false);

        repository.save(mov);

        jogador.setValorPago(jogador.getValorPago() + valorAdicionado);
        jogadorRepository.save(jogador);

        atualizarValores(jogador.getJogo().getId());

        return mov;
    }

    public void atualizarValores(Long jogoId) {

        var jogo = jogoRepository.findById(jogoId).orElseThrow();

        // Total de valores pagos em entradas normais
        int totalPago = jogadorRepository.findByJogoId(jogoId)
                .stream()
                .mapToInt(Jogador::getValorPago)
                .sum();

        // Total vindo de movimentações com PAGUE x2
        int totalPagues = repository.findAll()
                .stream()
                .filter(m -> m.getJogador().getJogo().getId().equals(jogoId))
                .filter(Movimentacao::isEntradaComPague)
                .mapToInt(Movimentacao::getValor)
                .sum();

        int total = totalPago;

        System.out.println("Total Pago: "+totalPago);
        System.out.println("Total Pagues: "+totalPagues);
        System.out.println("Total: "+total);

        jogo.setTotalSemValorPremio(total);

        int baseCalculo = jogo.getValorInicial() + total;
        double perc = jogo.getPercentualPague() / 100.0;
        int novoValorRodada = (int) Math.round(baseCalculo * perc);

        jogo.setValorPagamentoRodada(novoValorRodada);

        int lucroGanhador = (int) Math.round(total * 0.65);
        int lucroHost = (int) Math.round(total * 0.35);

        jogo.setLucroGanhador(lucroGanhador);
        jogo.setLucroHost(lucroHost);

        jogoRepository.save(jogo);
    }

    public void excluirMovimentacao(Long movimentacaoId) {
        Movimentacao mov = repository.findById(movimentacaoId)
                .orElseThrow(() -> new IllegalArgumentException("Movimentação não encontrada."));

        Jogador jogador = mov.getJogador();
        Long jogoId = jogador.getJogo().getId();

        // Se NÃO for entradaComPague, ela impacta jogador.valorPago → subtrai
        if (!mov.isEntradaComPague()) {
            int novoValor = jogador.getValorPago() - mov.getValor();
            jogador.setValorPago(Math.max(0, novoValor));
            jogadorRepository.save(jogador);
        }

        repository.delete(mov);

        atualizarValores(jogoId);
    }
}
