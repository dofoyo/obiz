package com.rhb.obiz.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rhb.obiz.domain.Customer;
import com.rhb.obiz.repository.CustomerRepository;

public class CustomerServiceImpl implements CustomerService {
	private CustomerRepository customerRepository;

	private HashMap<String,Customer>  customers = new HashMap<String,Customer>();
	
	private List<String> persons;

	public void setCustomerRepository(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	/*
	 * 获得欠款客户列表
	 * 客户代码，客户名称，欠款额
	 */
	@Override
	public List<Map<String,String>> getDebitCustomers() {
		List<Map<String,String>> dc = new ArrayList<Map<String,String>>();
		String[] ids  = this.customerRepository.getIds();
		int i = 1;
		for(String id : ids){
			Customer c = this.getCustomer(id);
			if(c.isDebitCustomer()){
				dc.add(c.getCustomerDebitInfo());
			}
			System.out.print(i++ +"/"+ ids.length + "\r");
		}
		return dc;
	}
	
	/*
	 * 获得欠款客户
	 * 客户代码，客户名称，欠款额
	 */	
	@Override
	public Map<String,String> getDebitCustomer(String customerId) {
		Customer c = getCustomer(customerId);
		return c.getCustomerDebitInfo();
	}

	
	/*
	 * 获得欠款合同列表
	 * 客户代码，客户名称，合同号，签订日期，欠款额
	 */	
	@Override
	public List<Map<String,String>> getDebitOrders(String customerId) {
		Customer c = getCustomer(customerId);
		return c.getDebitOrders();
	}

	@Override
	public List<Map<String, String>> getDebitOrders() {
		List<Map<String,String>> dc = new ArrayList<Map<String,String>>();
		String[] ids  = this.customerRepository.getIds();
		int i = 1;
		for(String id : ids){
			Customer c = this.getCustomer(id);
			if(c.isDebitCustomer()){
				dc.addAll(c.getDebitOrders());
			}
			System.out.print(i++ +"/"+ ids.length + "\r");
		}
		return dc;
	}
	
	
	/*
	 * 获得欠款合同
	 * 客户名称，合同号，签订日期，欠款额
	 */		
	@Override
	public Map<String,String> getDebitOrder(String customerId, String ordernumber) {
		Map<String, String> debitOrder = new HashMap<String,String>();
		Customer c = getCustomer(customerId);
		List<Map<String,String>> list = c.getDebitOrders();
		for(Map<String,String> m : list){
			if(m.containsValue(ordernumber)){
				debitOrder = m;
				break;
			}		
		}
		
		return debitOrder;
	}

	/*
	 * 获得欠款合同的收款计划列表和实际收款列表
	 * 款项，金额，计划收款日期
	 * 款项，金额，实际收款日期
	 */		
	@Override
	public Map<String,Object> getDebitOrderDetail(String customerId, String ordernumber) {
		Map<String, Object> debitOrder = new HashMap<String,Object>();
		Customer c = getCustomer(customerId);
		List<Map<String,Object>> list = c.getDebitOrderDetails();
		for(Map<String,Object> m : list){
			if(m.containsValue(ordernumber)){
				debitOrder = m;
				break;
			}		
		}
		return debitOrder;
	}

	@Override
	public Map<String,Object> getOrderDetail(String ordernumber) {
		Map<String, Object> orderDetail = null;
		
		String[] ids  = this.customerRepository.getIds();
		int i = 1;
		for(String id : ids){
			Customer c = this.getCustomer(id);
			if(c.containOrder(ordernumber)){
				orderDetail = c.getOrderDetails(ordernumber);
				break;
			}
			System.out.print(i++ +"/"+ ids.length + "\r");
		}

		return orderDetail;
	}
	
	//------------------------------------- 
	
	private Customer getCustomer(String customerId){
		Customer c;
		if(customers.containsKey(customerId)){
			c = customers.get(customerId);
		}else{
			c = customerRepository.find(customerId);
			customers.put(customerId, c);
		}
		return c;
	}

	@Override
	public List<String> greeting(String personname) {
		this.persons.add(personname);
		return this.persons;
	}

}
