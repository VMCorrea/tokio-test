package com.example.api.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.api.domain.Customer;

/**
 * <h1>CustomerRepository</h1>
 * <p>
 * Interface utilizada para aplicar os métodos de persistência de Customer
 * </p>
 * 
 * @author Victor Correa
 *
 */
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

	List<Customer> findAllByOrderByNameAsc();

}
