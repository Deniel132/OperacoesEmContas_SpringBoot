package dev.Deniel.ContaBancaria.repository;

import dev.Deniel.ContaBancaria.model.ContaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaBancariaRepository extends JpaRepository<ContaModel,Long> {

}
