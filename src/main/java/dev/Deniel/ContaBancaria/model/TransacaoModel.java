package dev.Deniel.ContaBancaria.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class TransacaoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer operacao;
    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "conta_origem_id")
    private ContaModel conta;


    @ManyToOne
    @JoinColumn(name = "conta_destino_id")
    private ContaModel segundaConta;



}
