package com.rhb.obiz.domain;

import java.util.ArrayList;
import java.util.List;

public class Invoice {
	private String id;
	private java.util.Date invoiceDate;
	private List<Item> items = new ArrayList<Item>();
	
	public Invoice(String id, java.util.Date invoiceDate){
		this.id = id;
		this.invoiceDate = invoiceDate;
	}
	
	public double amountOfInvoice(){
		double amount = 0.0;
		for(Item it : items){
			amount =+ it.total();
		}
		return amount;
	}

}
