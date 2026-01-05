package com.habbo.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateGameRequest {

    @NotBlank(message = "o campo \"nome\" é obrigatório")
    private String nome;

    @NotNull(message = "o campo \"valorInicial\" é obrigatório")
    private Integer valorInicial;

    @NotNull(message = "o campo \"percentualPague\" é obrigatório")
    private int percentualPague;

    public String getNome() {
        return nome;
    }

    public Integer getValorInicial() {
        return valorInicial;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setValorInicial(Integer valorInicial) {
        this.valorInicial = valorInicial;
    }

    public int getPercentualPague() {
        return percentualPague;
    }

    public void setPercentualPague(int percentualPague) {
        this.percentualPague = percentualPague;
    }

}
