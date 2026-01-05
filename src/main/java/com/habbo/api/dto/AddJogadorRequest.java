package com.habbo.api.dto;

import jakarta.validation.constraints.NotBlank;

public class AddJogadorRequest {

    @NotBlank(message = "o campo \"nomeJogador\" é obrigatório")
    private String nomeJogador;

    private Integer valorPago;

    public String getNomeJogador() {
        return nomeJogador;
    }

    public Integer getValorPago() {
        return valorPago;
    }

    public void setNomeJogador(String nomeJogador) {
        this.nomeJogador = nomeJogador;
    }
}
