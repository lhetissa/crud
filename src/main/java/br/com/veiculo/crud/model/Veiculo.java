package br.com.veiculo.crud.model;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Veiculo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String marca;
	@NotBlank
	private String modelo;
	@NotBlank
	private String ano;
	private String valor;
	
	public Veiculo() { 
		
	}
	
	public Veiculo(@NotBlank String marca, @NotBlank String modelo, @NotBlank String ano) {
		this.marca = marca;
		this.modelo = modelo;
		this.ano = ano;
	}
	
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getAno() {
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}
	
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getDiaRodizio() {
		switch(this.ano.charAt(3)) {
			case '0': case '1':				
				return "segunda-feira";
			case '2': case '3':
				return "terça-feira";
			case '4': case '5':
				return "quarta-feira";
			case '6': case '7':
				return "quinta-feira";
			case '8': case '9':
				return "sexta-feira";	
			default: 
				return "Erro";
		}
	}
	public Boolean getRodizioAtivo() {		
		String diaSemana = LocalDateTime.now().getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));
		if(diaSemana.equals(getDiaRodizio()))
			return true;
		
		return false;
	}
}
