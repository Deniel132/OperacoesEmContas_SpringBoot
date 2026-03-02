package dev.Deniel.ContaBancaria.model;

import dev.Deniel.ContaBancaria.model.Enums.TiposDeConta;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class ContaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long numeroDaConta;
    private Long agenciaDaConta;

    private String nomeTitular;
    private boolean status = true;

    private TiposDeConta tipoDaConta;
    private BigDecimal saldo;

}
