package com.example.api.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AddressClient {

	private WebClient webClient;

	@Autowired
	public AddressClient() {
		this.webClient = WebClient.create("https://viacep.com.br/ws");
	}

	public String findAddress(String cep) {

		WebClient.UriSpec<WebClient.RequestBodySpec> request = webClient.method(HttpMethod.GET);
		return request.uri("/{cep}/json", cep).retrieve().bodyToMono(String.class).block();
	}
}
