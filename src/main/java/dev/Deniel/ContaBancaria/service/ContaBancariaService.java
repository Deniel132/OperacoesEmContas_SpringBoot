package dev.Deniel.ContaBancaria.service;

import dev.Deniel.ContaBancaria.model.ContaModel;
import dev.Deniel.ContaBancaria.repository.ContaBancariaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContaBancariaService {

    private final ContaBancariaRepository contaBancariaRepository;

    public ContaBancariaService(ContaBancariaRepository contaBancariaRepository) {
        this.contaBancariaRepository = contaBancariaRepository;
    }


    public List<ContaModel> getAll(){return this.contaBancariaRepository.findAll();}

    public ContaModel save(ContaModel conta){return this.contaBancariaRepository.save(conta);}

    public void delet(Long id){this.contaBancariaRepository.deleteById(id);}

    public ContaModel getById(Long id){
        ContaModel conta = this.contaBancariaRepository.findById(id).orElse(null);
        if (conta ==  null){
            throw new EntityNotFoundException();
        }
        return conta;
    }


}
