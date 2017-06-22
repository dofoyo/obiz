package com.rhb.obiz.repository;

import com.rhb.obiz.domain.Customer;

public interface CustomerRepository {
	public String[] getIds();
	public Customer find(String id);

}
