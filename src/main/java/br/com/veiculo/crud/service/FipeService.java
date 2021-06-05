package br.com.veiculo.crud.service;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.veiculo.crud.model.Veiculo;

@Service
public class FipeService {

	public String getValor(Veiculo veiculo) {
		String codigoMarca = getCodigoMarca(veiculo);
		int codigoModelo = getCodigoModelo(veiculo, codigoMarca);
		String codigoAno = getCodigoAno(veiculo, codigoMarca, codigoModelo);
		String valorVeiculo = getValorVeiculo(veiculo, codigoMarca, codigoModelo, codigoAno);
		return valorVeiculo;
	}

	private String getCodigoMarca(Veiculo veiculo) {	
		String uri = "https://parallelum.com.br/fipe/api/v1/carros/marcas";
		
		try {
			JSONArray json = new JSONArray(getJsonFromURI(uri));
		
			for (Object elemento : json) {
				JSONObject marca = (JSONObject) elemento;
				if(marca.getString("nome").equalsIgnoreCase(veiculo.getMarca()))
					return marca.getString("codigo");
			}

			throw new IllegalArgumentException("Não foi encontrado a marca informada: " +veiculo.getMarca());
		} catch (JSONException e) {
			throw new IllegalArgumentException("Não foi encontrado a marca informada: " +veiculo.getMarca());
		}
	}
	
	private int getCodigoModelo(Veiculo veiculo, String codigoMarca) {		
		String uri = "https://parallelum.com.br/fipe/api/v1/carros/marcas/" +codigoMarca +"/modelos";
		
		try {
			JSONObject json = new JSONObject(getJsonFromURI(uri));
			JSONArray jsonArray = new JSONArray(json.getJSONArray("modelos"));
		
			for (Object elemento : jsonArray) {
				JSONObject modelo = (JSONObject) elemento;
				if(modelo.getString("nome").equalsIgnoreCase(veiculo.getModelo()))
					return modelo.getInt("codigo");
			}

			throw new IllegalArgumentException("Não foi encontrado o modelo informado: " +veiculo.getModelo());
		} catch (JSONException e) {
			throw new IllegalArgumentException("Não foi encontrado o modelo informado: " +veiculo.getModelo());
		}
	}
	
	private String getCodigoAno(Veiculo veiculo, String codigoMarca, int codigoModelo) {
		String uri = "https://parallelum.com.br/fipe/api/v1/carros/marcas/" 
				+codigoMarca +"/modelos/" +codigoModelo +"/anos";		
		try {
			JSONArray json = new JSONArray(getJsonFromURI(uri));
		
			for (Object elemento : json) {
				JSONObject ano = (JSONObject) elemento;
				if(ano.getString("nome").equalsIgnoreCase(veiculo.getAno()))
					return ano.getString("codigo");
			}
			
			throw new IllegalArgumentException("Não foi encontrado o ano informado: " +veiculo.getAno());
		} catch (JSONException e) {
			throw new IllegalArgumentException("Não foi encontrado o ano informado: " +veiculo.getAno());
		}
	}

	private String getValorVeiculo(Veiculo veiculo, String codigoMarca, int codigoModelo, String codigoAno) {
		String uri = "https://parallelum.com.br/fipe/api/v1/carros/marcas/" 
				+codigoMarca +"/modelos/" +codigoModelo +"/anos/" +codigoAno;
		
		try {
			JSONObject json = new JSONObject(getJsonFromURI(uri));
			return json.getString("Valor");
		} catch (JSONException e) {
			throw new IllegalStateException("Não foi encontrado o valor do veículo informado");
		}
	}
	
	private String getJsonFromURI(String uri) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<String> resp = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
		return resp.getBody();
	}
}
