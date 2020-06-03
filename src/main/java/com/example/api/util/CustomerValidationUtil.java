package com.example.api.util;

import com.example.api.domain.Customer;

public class CustomerValidationUtil {

	public void validate(Customer customer) {

		validateEmail(customer.getEmail());
	}

	private Boolean validateEmail(String email) {

		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}
	
	private Boolean validateCEP(String cep) {
		return true;
	}
}
