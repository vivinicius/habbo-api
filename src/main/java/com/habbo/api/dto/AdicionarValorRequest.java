package com.habbo.api.dto;

import jakarta.validation.constraints.NotNull;

public class AdicionarValorRequest {

    @NotNull(message = "o campo \"valor\" é obrigatório")
    private int valor;

    private String descricao; // NOVO

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}