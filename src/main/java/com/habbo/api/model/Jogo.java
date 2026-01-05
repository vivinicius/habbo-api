package com.habbo.api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "jogos")
public class Jogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(name = "lucro_host")
    private int lucroHost;

    @Column(name = "lucro_ganhador")
    private int lucroGanhador;

    @Column(name = "valor_pagamento_rodada")
    private int valorPagamentoRodada;

    @Column(name = "valor_inicial")
    private Integer valorInicial;

    @Column(name = "total_sem_valor_premio")
    private int totalSemValorPremio;

    @Column(name = "percentual_pague")
    private int percentualPague;

    @OneToMany(mappedBy = "jogo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference   // 🔥 Permite retornar jogadores no JSON
    private List<Jogador> jogadores;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getLucroHost() {
        return lucroHost;
    }

    public int getLucroGanhador() {
        return lucroGanhador;
    }

    public int getValorPagamentoRodada() {
        return valorPagamentoRodada;
    }

    public Integer getValorInicial() {
        return valorInicial;
    }

    public int getTotalSemValorPremio() {
        return totalSemValorPremio;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLucroHost(int lucroHost) {
        this.lucroHost = lucroHost;
    }

    public void setLucroGanhador(int lucroGanhador) {
        this.lucroGanhador = lucroGanhador;
    }

    public void setValorPagamentoRodada(int valorPagamentoRodada) {
        this.valorPagamentoRodada = valorPagamentoRodada;
    }

    public void setValorInicial(Integer valorInicial) {
        this.valorInicial = valorInicial;
    }

    public void setTotalSemValorPremio(int totalSemValorPremio) {
        this.totalSemValorPremio = totalSemValorPremio;
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    public int getPercentualPague() {
        return percentualPague;
    }

    public void setPercentualPague(int percentualPague) {
        this.percentualPague = percentualPague;
    }
}
