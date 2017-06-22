package com.rhb.obiz.domain;

public class Item {
	private String id;
	private String itemNumber; //物料编码
	private String itemName;//物料名称
	private String specification;//型号规格
	private String unit;//单位
	private double quantity;//数量
	private double unitprice;//含税单价
	private double taxRate; //税率，以小数表示
	
	public double total(){
		return quantity * unitprice;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}


	public double getQuantity() {
		return quantity;
	}


	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}


	public double getUnitprice() {
		return unitprice;
	}


	public void setUnitprice(double unitprice) {
		this.unitprice = unitprice;
	}

	
	
	
}
