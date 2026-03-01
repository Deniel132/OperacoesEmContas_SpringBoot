package dev.Deniel.ContaBancaria.controlle;

import dev.Deniel.ContaBancaria.DTO.TransacaoDTO;
import dev.Deniel.ContaBancaria.model.ContaModel;
import dev.Deniel.ContaBancaria.model.TransacaoModel;
import dev.Deniel.ContaBancaria.service.TransacaoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;


    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @GetMapping("/mostrarconta/{id}")
    public TransacaoModel mostrarTransacao(@PathVariable() Long id){return transacaoService.getById(id);}

    @GetMapping
    public List<TransacaoModel> listarContas(){return transacaoService.getAll();}

    @PostMapping
    public TransacaoModel realisarOperacao(@RequestBody TransacaoDTO transacaoDTO){return this.transacaoService.operacao(transacaoDTO);}

    @DeleteMapping("/deletarconta/{id}")
    public void deletarOperacao(@PathVariable() Long id){this.transacaoService.delet(id);}


}
