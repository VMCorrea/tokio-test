package com.example.api.util;

import com.example.api.domain.Customer;

/**
 * 
 * @author Victor Corrêa
 *
 */
public class CustomerValidationUtil {

	public static Boolean validateEmail(String email) {

		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}

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
