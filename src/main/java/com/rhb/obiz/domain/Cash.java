package com.rhb.obiz.domain;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.rhb.obiz.util.Convert;

public class Cash {
	private String id;
	private java.math.BigDecimal amount;
	private String term; //款项：预付款、提货款、到货款、调试款、质保款
	private java.sql.Timestamp receivedDate; //实际收款日期
	
	public Cash(String term, BigDecimal amount, java.sql.Timestamp receivedDate){
		this.term = term;
		this.amount = amount;
		this.receivedDate = receivedDate;
	}

	public java.math.BigDecimal getAmount() {
		return amount;
	}
	
	
	public Map<String,String> getCashInfo(){
		Map<String,String> m = new HashMap<String,String>();
		m.put("term", term);
		m.put("amount", this.amount.toString());
		m.put("receivedDate", Convert.t2s(receivedDate,"yyyy-MM-dd"));
		return m;
	}

	@Override
	public String toString() {
		return ""+"\n"+"Cash [id=" + id + ", amount=" + amount + ", term=" + term
				+ ", receivedDate=" + receivedDate + "]";
	}
	
	
	
}
