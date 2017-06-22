package com.rhb.obiz.service;

import java.util.List;
import java.util.Map;

public interface CustomerService {

	public List<String> greeting(String personname);
	
	/*
	 * 获得欠款客户列表
	 * 客户代码，客户名称，欠款额
	 */
	public List<Map<String,String>> getDebitCustomers();
	
	/*
	 * 获得欠款客户
	 * 客户代码，客户名称，欠款额
	 */	
	public Map<String,String> getDebitCustomer(String customerId);

	/*
	 * 获得欠款合同列表
	 * 客户名称，合同号，签订日期，欠款额
	 */	
	public List<Map<String,String>> getDebitOrders();

	
	/*
	 * 获得欠款合同列表
	 * 客户名称，合同号，签订日期，欠款额
	 */	
	public List<Map<String,String>> getDebitOrders(String customerId);
	
	
	/*
	 * 获得欠款合同
	 * 客户名称，合同号，签订日期，欠款额
	 */		
	public Map<String,String> getDebitOrder(String customerId,String ordernumber);
	
	
	/*
	 * 获得欠款合同的收款计划列表和实际收款列表
	 * 款项，金额，计划收款日期
	 * 款项，金额，实际收款日期
	 */		
	public Map<String,Object> getDebitOrderDetail(String customerId,String ordernumber);
	public Map<String,Object> getOrderDetail(String ordernumber);

}
