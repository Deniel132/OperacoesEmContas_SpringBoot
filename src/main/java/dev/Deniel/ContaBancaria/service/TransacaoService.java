package dev.Deniel.ContaBancaria.service;

import dev.Deniel.ContaBancaria.DTO.TransacaoDTO;
import dev.Deniel.ContaBancaria.model.ContaModel;
import dev.Deniel.ContaBancaria.model.TransacaoModel;
import dev.Deniel.ContaBancaria.repository.ContaBancariaRepository;
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
    private final ContaBancariaRepository contaBancariaRepository;

    public TransacaoService(TransacaoRepository transacaoRepository, ContaBancariaService contaBancariaService, ContaBancariaRepository contaBancariaRepository) {
        this.transacaoRepository = transacaoRepository;
        this.contaBancariaService = contaBancariaService;
        this.contaBancariaRepository = contaBancariaRepository;
    }

    public List<TransacaoModel>getAll(){return this.transacaoRepository.findAll();}

    private TransacaoModel save(@NonNull TransacaoDTO transacaoDTO){

        ContaModel conta = contaBancariaService.getById(transacaoDTO.getIdConta());
        ContaModel SegundacontaModel = contaBancariaRepository.findById(transacaoDTO.getIdSegundaConta()).orElse(null);

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
            case 1:
                deposito(transacaoModel.getConta().getId(),transacaoModel.getValor());

                break;
            case 2:
                saque(transacaoModel.getConta().getId(),transacaoModel.getValor());

                break;
            case 3:
                    saque(transacaoModel.getSegundaConta().getId(),transacaoModel.getValor());
                    deposito(transacaoModel.getConta().getId(),transacaoModel.getValor());
                break;

            default:
                throw new EntityNotFoundException();
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
            case 1:
               saque(transacaoDTO.getIdConta(),transacaoDTO.getValor());
                break;
            case 2:
                deposito(transacaoDTO.getIdConta(),transacaoDTO.getValor());
                break;
            case 3:
                transferncia(transacaoDTO);
                break;

            default:
                throw new EntityNotFoundException();
        }

        return save(transacaoDTO);
    }



    private void saque(Long idConta, BigDecimal valor){
        ContaModel conta = contaBancariaService.getById(idConta);

        if (conta.getSaldo().compareTo(valor) > 0){
            conta.setSaldo(conta.getSaldo().subtract(valor));
        }else {
            throw new RuntimeException();
        }
    }


    private void deposito(Long idConta, BigDecimal valor){
        ContaModel conta = contaBancariaService.getById(idConta);
        conta.setSaldo(conta.getSaldo().add(valor));
    }

    private void transferncia(TransacaoDTO transacaoDTO){
        if (transacaoDTO.getIdConta().equals(transacaoDTO.getIdSegundaConta())){
            throw new EntityNotFoundException();
        }else {
            ContaModel conta = contaBancariaService.getById(transacaoDTO.getIdConta());
            ContaModel contaSecundaria = contaBancariaRepository.findById(transacaoDTO.getIdSegundaConta()).orElse(null);

            if (conta != null && contaSecundaria != null){
                saque(transacaoDTO.getIdConta(), transacaoDTO.getValor());
                deposito(transacaoDTO.getIdSegundaConta(), transacaoDTO.getValor());
            }

        }

    }






}
