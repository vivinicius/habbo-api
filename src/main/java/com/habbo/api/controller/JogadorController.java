package com.habbo.api.controller;

import com.habbo.api.dto.AddJogadorRequest;
import com.habbo.api.model.Jogador;
import com.habbo.api.service.JogadorService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jogos/{jogoId}/jogadores")
public class JogadorController {

    private final JogadorService jogadorService;

    public JogadorController(JogadorService jogadorService) {
        this.jogadorService = jogadorService;
    }

    @PostMapping
    public Jogador adicionarJogador(
            @PathVariable Long jogoId,
            @RequestBody @Valid AddJogadorRequest request
    ) {
        return jogadorService.adicionarJogador(jogoId, request.getNomeJogador());
    }

    @PostMapping("/entrar-com-pague")
    public Jogador entrarComPague(
            @PathVariable Long jogoId,
            @RequestBody @Valid AddJogadorRequest request
    ) {
        return jogadorService.adicionarJogadorComPague(
                jogoId,
                request.getNomeJogador(),
                request.getValorPago()
        );
    }

    @GetMapping
    public List<Jogador> listarJogadores(@PathVariable Long jogoId) {
        return jogadorService.listarPorJogo(jogoId);
    }

    @DeleteMapping("/{jogadorId}")
    public void deletarJogador(
            @PathVariable Long jogoId,
            @PathVariable Long jogadorId
    ) {
        jogadorService.excluirJogador(jogoId, jogadorId);
    }

}
