package com.example.api.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * <h1>AddressClient</h1>
 * <p>
 * Classe utilizada como cliente para fazer requisições HTTP em
 * {@link https://viacep.com.br}, api que trás os dados dos endereços
 * </p>
 * 
 * @author Victor Corrêa
 *
 */
@Component
public class AddressClient {

	private WebClient webClient;

	@Autowired
	public AddressClient() {
		this.webClient = WebClient.create("https://viacep.com.br/ws");
	}

	/**
	 * Método responsável por executar uma requisição HTTP GET, para coletar os
	 * dados de um endereço
	 * 
	 * @param cep CEP do endereço que será buscado
	 * @return String no formato json com as informações do endereço
	 */
	public String findAddress(String cep) {

		WebClient.UriSpec<WebClient.RequestBodySpec> request = webClient.method(HttpMethod.GET);
		return request.uri("/{cep}/json", cep).retrieve().bodyToMono(String.class).block();
	}
}
