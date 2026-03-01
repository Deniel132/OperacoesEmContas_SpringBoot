package dev.Deniel.ContaBancaria.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransacaoDTO {

    private Long idConta;
    private Long idSegundaConta = 0L;

    private Integer operacao;
    private BigDecimal valor;

}
