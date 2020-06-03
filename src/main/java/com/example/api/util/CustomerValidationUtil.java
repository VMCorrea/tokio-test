package com.example.api.util;

import com.example.api.domain.Customer;

/**
 * <h1>CustomerValidationUtil</h1>
 * <p>
 * Simples classe com métodos de validação de Customer
 * </p>
 * 
 * @author Victor Corrêa
 *
 */
public class CustomerValidationUtil {

	/**
	 * Método que valida uma string em formato de email
	 * 
	 * @param email String que será validada
	 * @return Boolean com a resposta da validação
	 */
	public static Boolean validateEmail(String email) {

		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}

	/**
	 * Método que valida se os campos de Customer estão vazios
	 * 
	 * @param customer Customer que será validado
	 * @return Boolean com a resposta da validação
	 */
	public static Boolean validateCustomerMissingFields(Customer customer) {

		if (customer.getAdresses().size() <= 0)
			return false;

		if (customer.getName() == null || customer.getName().isBlank())
			return false;

		if (customer.getEmail() == null || customer.getEmail().isBlank())
			return false;

		return true;
	}
}
