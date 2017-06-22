package com.rhb.obiz.domain;

import java.util.ArrayList;
import java.util.List;

public class Delivery {

	private String id;
	private java.util.Date deliveryDate;
	private java.util.Date arrivalDate;
	private List<Item> items = new ArrayList<Item>();
	
	public Delivery(String id,String deliverDate){
		this.id = id;
		this.deliveryDate = deliveryDate;
	}
	
	public void addItem(Item item){
		items.add(item);
	}
	
	public void arrive(java.util.Date arrivalDate){
		this.arrivalDate = arrivalDate;
	}
	
	public double amountOfDelivery(){
		double amount = 0.0;
		for(Item it : items){
			amount =+ it.total();
		}
		return amount;
	}
	
	
	
	
}
