package br.com.veiculo.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.veiculo.crud.model.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

	
}
