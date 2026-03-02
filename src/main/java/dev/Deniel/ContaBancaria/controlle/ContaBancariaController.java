package dev.Deniel.ContaBancaria.controlle;

import dev.Deniel.ContaBancaria.model.ContaModel;
import dev.Deniel.ContaBancaria.service.ContaBancariaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contabancaria")
public class ContaBancariaController {

    private final ContaBancariaService contaBancariaService;


    public ContaBancariaController(ContaBancariaService contaBancariaService) {
        this.contaBancariaService = contaBancariaService;
    }


    @GetMapping("/mostrarconta/{id}/{numeroConta}/{numeroAgencia}")
    public ContaModel mostrarConta(@PathVariable() Long id,@PathVariable() Long numeroConta,@PathVariable() Long numeroAgencia){return contaBancariaService.getById(id,numeroConta,numeroAgencia);}

    @GetMapping
    public List<ContaModel> listarContas(){return contaBancariaService.getAll();}

    @PostMapping
    public ContaModel criarConta(@RequestBody ContaModel conta){return this.contaBancariaService.save(conta);}

    @DeleteMapping("/deletarconta/{id}")
    public void deletarConta(@PathVariable() Long id){this.contaBancariaService.delet(id);}


}
