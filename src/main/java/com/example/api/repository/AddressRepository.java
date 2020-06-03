package com.example.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.api.domain.Address;

/**
 * 
 * @author Victor Correa
 *
 */
public interface AddressRepository extends CrudRepository<Address, String> {

}
