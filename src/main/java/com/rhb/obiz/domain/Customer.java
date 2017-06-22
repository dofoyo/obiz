package com.rhb.obiz.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 客户，聚合根
 */
public class Customer {
	private String id; 
	private String customerNumber; //客户编号
	private String companyname; //客户名称
	private String address;//客户地址
	private List<Linkman> linkmen;//该客户的联系人

	private List<Clue> clues = new ArrayList<Clue>();  //该客户的线索
	private List<Opportunity> opportunities = new ArrayList<Opportunity>();//该客户的商机
	private List<Activity> activities = new ArrayList<Activity>();  //该客户的活动
	private List<Contract> contracts = new ArrayList<Contract>(); //该客户的合同
	private List<Order> orders = new ArrayList<Order>(); //该客户的订单

	
	private String area; //所属片区（东、西、南、北）
	private String province;//所属省份
	private List<Salesman> salesmen;//维护该客户的销售员
	
	public Customer(String id,String name,String address,String area, String province, String manager){
		this.id = id;
		this.companyname = name;
		this.address = address;
		this.area = area;
		this.province = province;
		//this.manager = manager;
	}

	public Customer(String id,String name,String address){
		this.id = id;
		this.companyname = name;
		this.address = address;
	}
	
	public String getId() {
		return id;
	}

	public void addOrder(Order order){
		this.orders.add(order);
	}
	
	public void addOrders(List<Order> orders){
		this.orders.addAll(orders);
	}
	
	public boolean containOrder(String ordernumber){
		boolean flag = false;
		for(Order order : orders){
			if(order.fitOrdernumber(ordernumber)){
				flag = true;
				break;
			}
		}
		return flag;
	}

	public boolean isDebitCustomer(){
		return (this.getCustomerDebit().compareTo(new BigDecimal("0.00"))==1) ? true : false;
	}
	
	public BigDecimal getCustomerDebit(){
		BigDecimal debit = new BigDecimal("0.00"); 
		for(Order order : orders){
			debit = debit.add(order.getOrderDebit());
		}
		return debit;
	}
	
	/*
	 * 客户欠款信息
	 * 客户代码，客户名称，欠款额
	 */	
	public Map<String,String> getCustomerDebitInfo() {
		Map<String,String> debitInfo = new HashMap<String,String>();
		debitInfo.put("companyid", this.id);
		debitInfo.put("companyname", this.companyname);
		debitInfo.put("debit", this.getCustomerDebit().toString());
		return debitInfo;
	}

	/*
	 * 合同欠款信息
	 * 客户代码，客户名称，合同号，签订日期，欠款额
	 */		
	public List<Map<String,String>> getDebitOrders() {
		List<Map<String,String>> l = new ArrayList<Map<String,String>>();
		for(Order order : orders){
			if(order.isDebitOrder()){
				Map<String,String> m = order.getOrderDebitInfo();
				m.put("companyid", this.id);
				m.put("companyname", this.companyname);
				l.add(m);
			}
		}
		return l;
	}
	
	/*
	 * 获得欠款合同的收款计划列表和实际收款列表
	 * 款项，金额，计划收款日期
	 * 款项，金额，实际收款日期
	 */		
	public List<Map<String,Object>> getDebitOrderDetails(){
		List<Map<String,Object>> l = new ArrayList<Map<String,Object>>();
		for(Order order : orders){
			if(order.isDebitOrder()){
				Map<String,Object> m = order.getOrderDebitDetail();
				m.put("companyid", this.id);
				m.put("companyname", this.companyname);
				l.add(m);
			}
		}
		return l;		
	}
	
	public Map<String,Object> getOrderDetails(String ordernumber){
		Map<String,Object> orderDetail = null;
		for(Order order : orders){
			if(order.fitOrdernumber(ordernumber)){
				Map<String,Object> m = order.getOrderDebitDetail();
				m.put("companyid", this.id);
				m.put("companyname", this.companyname);
				break;
			}
		}
		return orderDetail;		
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", customerNumber=" + customerNumber
				+ ", companyname=" + companyname + ", address=" + address
				+ ", area=" + area + ", province=" + province + ", "+"\n"+" salesmen=" + salesmen + ", "+"\n"+" linkmen=" + linkmen
				+ ", "+"\n"+" clues=" + clues + ", "+"\n"+" opportunities=" + opportunities
				+ ", "+"\n"+" activities=" + activities + ", "+"\n"+" contracts=" + contracts
				+ ", "+"\n"+" orders=" + orders + "]";
	}

	public String getCompanyname() {
		return companyname;
	}
	
	
}