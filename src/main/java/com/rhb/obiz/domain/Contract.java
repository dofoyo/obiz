package com.rhb.obiz.domain;

import java.util.ArrayList;
import java.util.List;

/*
 * 合同
 */
public class Contract {

	private String id;
	private String contractCode; //合同编号
	private String contractNumber; //合同自编号
	private java.util.Date signedDate; //签订日期
	private List<Item> items = new ArrayList<Item>(); //合同明细
	
	private List<Order> orders; //一份合同有多个订单

	
}
