package com.rhb.obiz.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.rhb.obiz.util.Convert;

public class Payment {
	private String id;
	private java.math.BigDecimal rate; //比率
	private String term;//款项：预付款、提货款、到货款、调试款、质保款
	private String description; //客户付款条件描述
	private Timestamp dueDate; //约定收款日期
	private BigDecimal amount; //金额
	
	public Payment(String term, String description, java.math.BigDecimal rate, Timestamp dueDate,BigDecimal amount){
		this.term = term;
		this.description = description;
		this.rate = rate;
		this.dueDate = dueDate;
		this.amount = rate.multiply(amount).divide(new BigDecimal("100.00"));
	}
	
	public String getTerm(){
		return this.term;
	}
	
	public Timestamp getDueDate(){
		return this.dueDate;
	}
	
	public java.math.BigDecimal getDebit(){
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		if(dueDate!=null && dueDate.before(now)){
			return this.amount;
		}else{
			return new BigDecimal("0.00");
		}
	}
	
	public Map<String,String> getPaymentInfo(){
		Map<String,String> m = new HashMap<String,String>();
		m.put("term", term);
		m.put("amount", this.amount.toString());
		m.put("dueDate", Convert.t2s(dueDate, "yyyy-MM-dd"));
		return m;
	}
	

	public java.math.BigDecimal getRate() {
		return rate;
	}

	@Override
	public String toString() {
		return ""+"\n"+"Payment [id=" + id + ", rate=" + rate + ", term=" + term
				+ ", description=" + description + ", dueDate=" + dueDate
				+ ", amount=" + amount + "]";
	}


	
	
	
}
