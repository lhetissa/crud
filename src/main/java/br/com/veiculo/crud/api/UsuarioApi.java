package br.com.veiculo.crud.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.veiculo.crud.model.Usuario;
import br.com.veiculo.crud.repository.UsuarioRepository;

@RestController 
@RequestMapping("/usuario")
public class UsuarioApi {
	@Autowired
	private UsuarioRepository repository;
	
	@GetMapping("/{cpf}")
	public Usuario buscar(@PathVariable String cpf) {
		return repository.findByCpf(cpf);
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody(required = false) Usuario usuario) {
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(usuario));
	}
	
	
}
