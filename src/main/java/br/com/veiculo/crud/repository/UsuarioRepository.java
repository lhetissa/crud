package br.com.veiculo.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.veiculo.crud.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Usuario findByCpf(String cpf);

}
