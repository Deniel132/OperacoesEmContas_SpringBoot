package dev.Deniel.ContaBancaria.service;

import dev.Deniel.ContaBancaria.model.ContaModel;
import dev.Deniel.ContaBancaria.model.Enums.TiposDeConta;
import dev.Deniel.ContaBancaria.repository.ContaBancariaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaBancariaService {

    private final ContaBancariaRepository contaBancariaRepository;

    public ContaBancariaService(ContaBancariaRepository contaBancariaRepository) {
        this.contaBancariaRepository = contaBancariaRepository;
    }


    public List<ContaModel> getAll(){return this.contaBancariaRepository.findAll();}

    public ContaModel save(ContaModel conta){

        if(getAll().stream().anyMatch(contaModel -> contaModel.getNumeroDaConta().equals(conta.getNumeroDaConta())
                && contaModel.getAgenciaDaConta().equals(conta.getAgenciaDaConta()))){
            throw new IllegalArgumentException("Conta ja Existente");
        }else {
            if (conta.getTipoDaConta().equals(TiposDeConta.INVALIDA)){
                throw new IllegalArgumentException("Tipo De Conta Invalido");
            }else {

                return this.contaBancariaRepository.save(conta);
            }
        }
    }

    public void delet(Long id){this.contaBancariaRepository.deleteById(id);}

    public ContaModel getById(Long id,Long numeroDaConta,Long numeroDaAgencia){
        ContaModel conta = this.contaBancariaRepository.findById(id).orElse(null);
        if (conta ==  null){
            throw new IllegalArgumentException("Conta Inesistente");
        }
            if (conta.getNumeroDaConta().equals(numeroDaConta) && conta.getAgenciaDaConta().equals(numeroDaAgencia)){
                return conta;
            }else {
                throw new IllegalArgumentException("Numero ou Agencia Da conta Incorreta");
            }
    }

    public ContaModel getByIdSemException(Long id){return this.contaBancariaRepository.findById(id).orElse(null);}


}
