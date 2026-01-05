package com.habbo.api.controller;

import com.habbo.api.model.Jogo;
import com.habbo.api.service.JogoService;
import org.springframework.web.bind.annotation.*;
import com.habbo.api.dto.CreateGameRequest;
import jakarta.validation.Valid;


import java.util.List;

@RestController
@RequestMapping("/jogos")
public class JogoController {

    private final JogoService service;

    public JogoController(JogoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Jogo> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Jogo buscarPorId(@PathVariable Long id) {
        Jogo jogo = service.buscarPorId(id);

        // força o carregamento da lista (evita lazy loading issues)
        jogo.getJogadores().size();

        return jogo;
    }

    @PostMapping
    public Jogo criar(@RequestBody @Valid CreateGameRequest request) {
        return service.criarJogo(
                request.getNome(),
                request.getValorInicial(),
                request.getPercentualPague()
        );
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletarJogo(id);
    }

}
