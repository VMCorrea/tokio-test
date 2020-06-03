package com.example.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.api.domain.Address;

/**
 * <h1>AddressRepository</h1>
 * <p>
 * Interface utilizada para aplicar os métodos de persistência de Address
 * </p>
 * 
 * @author Victor Correa
 *
 */
public interface AddressRepository extends CrudRepository<Address, String> {

}
