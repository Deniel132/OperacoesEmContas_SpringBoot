package dev.Deniel.ContaBancaria.DTO;

import dev.Deniel.ContaBancaria.model.Enums.EsolhaTransacao;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransacaoDTO {

    private Long idConta;
    private Long numeroDaConta;
    private Long agenciaDaConta;

    private Long idSegundaConta = 0L;
    private Long numeroDaSegundaConta = 0L;
    private Long agenciaDaSegundaConta = 0L;

    private EsolhaTransacao operacao;
    private BigDecimal valor;

}
