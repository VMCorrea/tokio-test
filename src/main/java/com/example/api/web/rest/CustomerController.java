package com.example.api.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.domain.Customer;
import com.example.api.service.CustomerService;

/**
 * <h1>CustomerController</h1>
 * <p>
 * Controle REST, que contém os endpoints relacionados a entidade Customer
 * </p>
 * 
 * @author Victor Corrêa
 *
 */
@RestController
@RequestMapping("/customers")
public class CustomerController {

	private CustomerService service;

	@Autowired
	public CustomerController(CustomerService service) {
		this.service = service;
	}

	/**
	 * Método que configura o endpoint do tipo GET, responsável por buscar a lista
	 * de Customers com paginação.
	 * 
	 * @param page   Número da página
	 * @param size   Quantidade de registros por página
	 * @param sortBy Campo que será utilizado para ordenação dos customers
	 * @return Resposta da requisição
	 */
	@GetMapping
	public ResponseEntity<List<Customer>> findAll(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "id") String sortBy) {
		return service.findAll(page, size, sortBy);
	}

	/**
	 * Método que configura o endpoint do tipo GET, responsável por buscar um
	 * Customer por id
	 * 
	 * @param id identificador do Customer
	 * @return Resposta da requisição
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Customer> findById(@PathVariable Long id) {
		return service.findById(id);
	}

	/**
	 * Método que configura o endpoint do tipo POST, responsável por cadastrar um
	 * Customer novo.
	 * 
	 * @param customer Customer que será persistido
	 * @return Resposta da requisição
	 */
	@PostMapping
	public ResponseEntity<String> addCustomer(@RequestBody Customer customer) {
		return service.create(customer);
	}

	/**
	 * Método que configura o endpoint do tipo PUT, responsável por atualizar um
	 * Customer existente.
	 * 
	 * @param customer Customer com os campos modificados
	 * @param id       Identificador do Customer que será atualizado
	 * @return Resposta da requisição
	 */
	@PutMapping("/{id}")
	public ResponseEntity<String> updateCustomer(@RequestBody Customer customer, @PathVariable Long id) {
		customer.setId(id);
		return service.update(customer);
	}

	/**
	 * Método que configura o endpoint do tipo DELETE, responsável deletar um
	 * customer
	 * 
	 * @param id Identificador do Customer que será removido
	 * @return Resposta da requisição
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
		return service.delete(id);
	}
}
