package dev.Deniel.ContaBancaria.service;

import dev.Deniel.ContaBancaria.DTO.TransacaoDTO;
import dev.Deniel.ContaBancaria.model.ContaModel;
import dev.Deniel.ContaBancaria.model.TransacaoModel;
import dev.Deniel.ContaBancaria.repository.TransacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final ContaBancariaService contaBancariaService;

    public TransacaoService(TransacaoRepository transacaoRepository,ContaBancariaService contaBancariaService) {
        this.transacaoRepository = transacaoRepository;
        this.contaBancariaService = contaBancariaService;

    }

    public List<TransacaoModel>getAll(){return this.transacaoRepository.findAll();}

    private TransacaoModel save(@NonNull TransacaoDTO transacaoDTO){

        ContaModel conta = contaBancariaService.getById(transacaoDTO.getIdConta(),transacaoDTO.getNumeroDaConta(),transacaoDTO.getAgenciaDaConta());
        ContaModel SegundacontaModel = contaBancariaService.getByIdSemException(transacaoDTO.getIdSegundaConta());

        TransacaoModel transacaoModel = new TransacaoModel();

        transacaoModel.setOperacao(transacaoDTO.getOperacao());
        transacaoModel.setConta(conta);
        transacaoModel.setSegundaConta(SegundacontaModel);
        transacaoModel.setValor(transacaoDTO.getValor());

        return this.transacaoRepository.save(transacaoModel);
    }

    public void delet(Long id){

        TransacaoModel transacaoModel = getById(id);

        switch (transacaoModel.getOperacao()){
            case SAQUE:
                deposito(transacaoModel.getConta().getId(),transacaoModel.getConta().getNumeroDaConta(),
                        transacaoModel.getConta().getAgenciaDaConta(),transacaoModel.getValor());

                break;
            case DEPOSITO:
                saque(transacaoModel.getConta().getId(),transacaoModel.getConta().getNumeroDaConta(),
                        transacaoModel.getConta().getAgenciaDaConta(),transacaoModel.getValor());

                break;
            case TRANSFERENCIA:
                    saque(transacaoModel.getSegundaConta().getId(),transacaoModel.getSegundaConta().getNumeroDaConta(),
                            transacaoModel.getSegundaConta().getAgenciaDaConta(),transacaoModel.getValor());

                    deposito(transacaoModel.getConta().getId(),transacaoModel.getConta().getNumeroDaConta(),
                            transacaoModel.getConta().getAgenciaDaConta(),transacaoModel.getValor());
                break;
        }


        this.transacaoRepository.deleteById(id);
    }

    public TransacaoModel getById(Long id){
        TransacaoModel transacao = this.transacaoRepository.findById(id).orElse(null);
        if (transacao ==  null){
            throw new EntityNotFoundException();
        }
        return transacao;
    }

    @Transactional
    public TransacaoModel operacao(@NonNull TransacaoDTO transacaoDTO){

        switch (transacaoDTO.getOperacao()){
            case SAQUE:
               saque(transacaoDTO.getIdConta(),transacaoDTO.getNumeroDaConta(),transacaoDTO.getAgenciaDaConta(),transacaoDTO.getValor());
                break;
            case DEPOSITO:
                deposito(transacaoDTO.getIdConta(),transacaoDTO.getNumeroDaConta(),transacaoDTO.getAgenciaDaConta(),transacaoDTO.getValor());
                break;
            case TRANSFERENCIA:
                transferncia(transacaoDTO);
                break;

            default:
                throw new EntityNotFoundException();
        }

        return save(transacaoDTO);
    }



    private void saque(Long idConta,Long numeroConta,Long numeroAgencia, BigDecimal valor){
        ContaModel conta = contaBancariaService.getById(idConta,numeroConta,numeroAgencia);

        if (conta.getSaldo().compareTo(valor) >= 0){
            conta.setSaldo(conta.getSaldo().subtract(valor));
        }else {
            throw new IllegalArgumentException("Valor de saque maior que o saldo");
        }
    }


    private void deposito(Long idConta,Long numeroConta,Long numeroAgencia, BigDecimal valor){
        ContaModel conta = contaBancariaService.getById(idConta,numeroConta,numeroAgencia);
        conta.setSaldo(conta.getSaldo().add(valor));
    }

    private void transferncia(TransacaoDTO transacaoDTO){
        if (transacaoDTO.getIdConta().equals(transacaoDTO.getIdSegundaConta())){
            throw new EntityNotFoundException();
        }else {
            ContaModel conta = contaBancariaService.getById(transacaoDTO.getIdConta(),transacaoDTO.getNumeroDaConta(),transacaoDTO.getAgenciaDaConta());
            ContaModel contaSecundaria = contaBancariaService.getById(transacaoDTO.getIdSegundaConta(),transacaoDTO.getNumeroDaSegundaConta(),transacaoDTO.getAgenciaDaSegundaConta());

            if (conta != contaSecundaria){
                saque(transacaoDTO.getIdConta(),transacaoDTO.getNumeroDaConta(), transacaoDTO.getAgenciaDaConta(), transacaoDTO.getValor());
                deposito(transacaoDTO.getIdSegundaConta(),transacaoDTO.getNumeroDaSegundaConta(), transacaoDTO.getAgenciaDaSegundaConta(), transacaoDTO.getValor());
            }

        }

    }






}
