package com.habbo.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "jogadores")
public class Jogador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_jogador")
    private String nomeJogador;

    @Column(name = "valor_pago", nullable = false)
    private Integer valorPago = 0;

    @ManyToOne
    @JoinColumn(name = "jogo_id")
    @JsonBackReference   // 🔥 Agora funciona corretamente
    private Jogo jogo;

    @OneToMany(mappedBy = "jogador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Movimentacao> movimentacoes;

    // Getters e Setters
    public Long getId() { return id; }
    public String getNomeJogador() { return nomeJogador; }
    public int getValorPago() { return valorPago; }
    public Jogo getJogo() { return jogo; }

    public void setId(Long id) { this.id = id; }
    public void setNomeJogador(String nomeJogador) { this.nomeJogador = nomeJogador; }
    public void setValorPago(int valorPago) { this.valorPago = valorPago; }
    public void setJogo(Jogo jogo) { this.jogo = jogo; }
}
