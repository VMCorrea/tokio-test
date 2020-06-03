package com.example.api.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.api.client.AddressClient;
import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.repository.AddressRepository;
import com.example.api.repository.CustomerRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CustomerService {

	private CustomerRepository customerRepository;
	private AddressRepository addressRepository;
	private AddressClient addressClient;

	@Autowired
	public CustomerService(CustomerRepository repository, AddressRepository addressRepository, AddressClient client) {
		this.customerRepository = repository;
		this.addressRepository = addressRepository;
		this.addressClient = client;
	}

	public List<Customer> findAll(Integer pageNo, Integer pageSize, String sortBy) {

		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		Page<Customer> page = customerRepository.findAll(pageable);

		return page.getContent();
	}

	public Optional<Customer> findById(Long id) {
		return customerRepository.findById(id);
	}

	public Customer create(Customer customer) {
		
		customer.getAdresses().forEach(address -> {
			try {
				fillAddress(address);
			} catch (IOException e) {
				System.out.println();
			}
		});

		addressRepository.saveAll(customer.getAdresses());
		return customerRepository.save(customer);
	}

	public void delete(Long id) {
		customerRepository.deleteById(id);
	}

	public void update(Customer customer) {
		customerRepository.save(customer);
	}

	private void fillAddress(Address address) throws IOException {

		ObjectMapper mapper = new ObjectMapper();

		String json = addressClient.findAddress(address.getCep());

		JsonNode node = mapper.readTree(json);
		address.setBairro(node.get("bairro").asText());
		address.setComplemento(node.get("complemento").asText());
		address.setLocalidade(node.get("localidade").asText());
		address.setLogradouro(node.get("logradouro").asText());
		address.setUf(node.get("uf").asText());
	}
}
