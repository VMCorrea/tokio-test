package com.example.api.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.api.client.AddressClient;
import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.repository.AddressRepository;
import com.example.api.repository.CustomerRepository;
import com.example.api.util.CustomerValidationUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Victor Corrêa
 *
 */
@Service
public class CustomerService {

	private CustomerRepository customerRepository;
	private AddressRepository addressRepository;
	private AddressClient addressClient;

	private Logger logger = LoggerFactory.getLogger(CustomerService.class);

	@Autowired
	public CustomerService(CustomerRepository repository, AddressRepository addressRepository, AddressClient client) {
		this.customerRepository = repository;
		this.addressRepository = addressRepository;
		this.addressClient = client;
	}

	/**
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param sortBy
	 * @return
	 */
	public ResponseEntity<List<Customer>> findAll(Integer pageNo, Integer pageSize, String sortBy) {

		logger.info("Fetching list of customers");
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<Customer> page = customerRepository.findAll(pageable);

		return ResponseEntity.ok(page.getContent());
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public ResponseEntity<Customer> findById(Long id) {

		logger.info("Fetching customer with id " + id);
		return ResponseEntity.of(customerRepository.findById(id));
	}

	/**
	 * 
	 * @param customer
	 * @return
	 * @throws URISyntaxException
	 */
	public ResponseEntity<String> create(Customer customer) throws URISyntaxException {

		// Validações de Customer
		logger.info("Validating Customer");
		if (!CustomerValidationUtil.validateCustomerMissingFields(customer)) {

			logger.error("Missing fields for customer");
			return ResponseEntity.badRequest().body("Missing Fields!");
		}
		if (!CustomerValidationUtil.validateEmail(customer.getEmail())) {

			logger.error("Invalid email for customer");
			return ResponseEntity.badRequest().body("Invalid Email!");
		}

		// Processamento dos endereços. Preenchendo os demais dados de acordo com o CEP
		// enviado.
		try {

			logger.info("Fetching Adresses Data");
			for (Address address : customer.getAdresses())
				fillAddress(address);
		} catch (IOException e) {

			logger.error("Server Error: " + e.getMessage());
			return ResponseEntity.status(500).body("Server Error!");
		} catch (IllegalArgumentException e) {

			logger.error("Invalid CEP received by the client");
			return ResponseEntity.badRequest().body("Invalid CEP!");
		}

		// Salvando os endereços no banco
		addressRepository.saveAll(customer.getAdresses());

		// Salvando o customer no banco
		customerRepository.save(customer);

		logger.info("Customer created with id " + customer.getId());

		URI uri = new URI("/customers/" + customer.getId());
		return ResponseEntity.created(uri).body("Customer created!");
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public ResponseEntity<String> delete(Long id) {

		try {

			logger.info("Deleting customer with id " + id);
			customerRepository.deleteById(id);

			logger.info("Customer with id " + id + " deleted");
			return ResponseEntity.ok().body("Customer deleted");
		} catch (EmptyResultDataAccessException e) {

			logger.error("Customer with id " + id + " not found");
			return ResponseEntity.status(404).body("Customer not found!");
		}
	}

	/**
	 * 
	 * @param customer
	 * @return
	 */
	public ResponseEntity<String> update(Customer customer) {

		// Validações de Customer
		logger.info("Validating Customer");
		if (!CustomerValidationUtil.validateCustomerMissingFields(customer)) {

			logger.error("Missing fields for customer");
			return ResponseEntity.badRequest().body("Missing Fields!");
		}
		if (!CustomerValidationUtil.validateEmail(customer.getEmail())) {

			logger.error("Invalid email for customer");
			return ResponseEntity.badRequest().body("Invalid Email!");
		}

		// Verifica se o customer já existe no banco de dados
		logger.info("Fetching customer for update");
		if (customerRepository.findById(customer.getId()).isEmpty()) {

			logger.error("Customer with id " + customer.getId() + " not found");
			return ResponseEntity.status(404).body("Customer not found!");
		}

		// Processamento dos endereços. Preenchendo os demais dados de acordo com o CEP
		// enviado.
		try {

			logger.info("Fetching Adresses Data");
			for (Address address : customer.getAdresses())
				fillAddress(address);
		} catch (IOException e) {

			logger.error("Server Error: " + e.getMessage());
			return ResponseEntity.status(500).body("Server Error!");
		} catch (IllegalArgumentException e) {

			logger.error("Invalid CEP received by the client");
			return ResponseEntity.badRequest().body("Invalid CEP!");
		}

		// Salvando os endereços no banco
		addressRepository.saveAll(customer.getAdresses());

		// Salvando o customer no banco
		customerRepository.save(customer);

		return ResponseEntity.ok().body("User Updated!");
	}

	private void fillAddress(Address address) throws IOException {

		ObjectMapper mapper = new ObjectMapper();

		String json = addressClient.findAddress(address.getCep());

		JsonNode node = mapper.readTree(json);

		if (node.has("erro"))
			throw new IllegalArgumentException();

		address.setBairro(node.get("bairro").asText());
		address.setComplemento(node.get("complemento").asText());
		address.setLocalidade(node.get("localidade").asText());
		address.setLogradouro(node.get("logradouro").asText());
		address.setUf(node.get("uf").asText());
	}

}
