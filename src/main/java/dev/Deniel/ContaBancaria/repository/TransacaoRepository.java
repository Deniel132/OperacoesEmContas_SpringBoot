package dev.Deniel.ContaBancaria.repository;

import dev.Deniel.ContaBancaria.model.TransacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<TransacaoModel,Long> {
}
