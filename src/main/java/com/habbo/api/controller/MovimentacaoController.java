package com.habbo.api.controller;

import com.habbo.api.dto.AdicionarValorRequest;
import com.habbo.api.model.Movimentacao;
import com.habbo.api.service.MovimentacaoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    public MovimentacaoController(MovimentacaoService movimentacaoService) {
        this.movimentacaoService = movimentacaoService;
    }

    @PostMapping("/jogos/{jogoId}/jogadores/{jogadorId}/movimentacoes/adicionar")
    public Movimentacao adicionarValor(
            @PathVariable Long jogoId,
            @PathVariable Long jogadorId,
            @RequestBody @Valid AdicionarValorRequest request
    ) {
        return movimentacaoService.adicionarValor(
                jogadorId,
                request.getValor(),
                request.getDescricao()
        );
    }

    @GetMapping("/jogos/{jogoId}/jogadores/{jogadorId}/movimentacoes")
    public List<Movimentacao> listarPorJogo(
            @PathVariable Long jogoId,
            @PathVariable Long jogadorId
    ) {
        return movimentacaoService.listar(jogadorId);
    }

    @GetMapping("/jogadores/{jogadorId}/movimentacoes")
    public List<Movimentacao> listarSomentePorJogador(@PathVariable Long jogadorId) {
        return movimentacaoService.listar(jogadorId);
    }

    @DeleteMapping("/movimentacoes/{movimentacaoId}")
    public void deletarMovimentacao(@PathVariable Long movimentacaoId) {
        movimentacaoService.excluirMovimentacao(movimentacaoId);
    }
}
