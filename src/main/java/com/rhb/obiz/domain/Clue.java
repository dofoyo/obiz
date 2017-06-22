package com.rhb.obiz.domain;

import java.util.Date;
import java.util.List;

/*
 * 线索
 * 线索的目的是生成新客户,因此一个线索生成一个客户
 * 线索是客户的前生
 */
public class Clue {

	private String id;
	private String companyname;
	private String address;
	private String discription; //业务机会
	private Salesman creator; //线索的创建人	
	private Date creationDate; //创建日期
	private List<Linkman> linkmen;//该线索的联系人	
	private List<Salesman> salesmen;//跟进该线索的销售员
	private List<Activity> activities;//针对该线索的活动
	private int status; //状态，open,close,customer


	public void addLinkman(){
		
	}
	
	public void removeLinkman(){
		
	}
	
	public void addSalesman(){
		
	}
	
	public void removeSalesman(){
		
	}
	
	public void createCustomer(){
		
	}
}
