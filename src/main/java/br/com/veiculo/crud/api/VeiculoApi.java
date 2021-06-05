package br.com.veiculo.crud.api;

import java.util.List;

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
import br.com.veiculo.crud.model.Veiculo;
import br.com.veiculo.crud.repository.UsuarioRepository;
import br.com.veiculo.crud.repository.VeiculoRepository;
import br.com.veiculo.crud.service.FipeService;

@RestController
@RequestMapping("/veiculo")
public class VeiculoApi {
	@Autowired
	private VeiculoRepository repository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private FipeService fipeService;
	
	@GetMapping
	public List<Veiculo> listar() {
		return repository.findAll();
	}
	
	@PostMapping("/{cpf}")
	public ResponseEntity<?> salvar(@Valid @RequestBody(required = false) Veiculo veiculo, @PathVariable("cpf") String cpf) {
		veiculo.setValor(fipeService.getValor(veiculo));
		Usuario usuario = usuarioRepository.findByCpf(cpf);
		usuario.adicionaVeiculo(veiculo);
		repository.save(veiculo);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(usuario));
	}
	
	
}
