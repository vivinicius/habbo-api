package com.habbo.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "movimentacoes")
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int valor;

    private int saldo;

    @Column(name = "entrada_com_pague", nullable = false)
    private boolean entradaComPague = false;

    @Column(name = "descricao")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "jogador_id")
    @JsonIgnore
    private Jogador jogador;

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public Long getId() { return id; }
    public int getValor() { return valor; }
    public int getSaldo() { return saldo; }
    public boolean isEntradaComPague() { return entradaComPague; }
    public Jogador getJogador() { return jogador; }

    public void setId(Long id) { this.id = id; }
    public void setValor(int valor) { this.valor = valor; }
    public void setSaldo(int saldo) { this.saldo = saldo; }
    public void setEntradaComPague(boolean entradaComPague) { this.entradaComPague = entradaComPague; }
    public void setJogador(Jogador jogador) { this.jogador = jogador; }
}
