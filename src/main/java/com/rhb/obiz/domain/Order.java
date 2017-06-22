package com.rhb.obiz.domain;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rhb.obiz.util.Convert;

/*
 * 订单
 */
public class Order {

	private String id;
	private String ordernumber; //订单自编号
	private java.sql.Timestamp signedDate; //订单日期
	private String business;//业务类型：CNG、LNG、配件、服务
	private int year;  //年份，即计入哪一年的业绩
	private java.math.BigDecimal amount; //合同总额，临时字段，后期应根据合同明细自动计算出来
	
	private List<Item> items = new ArrayList<Item>(); //订单明细
	private List<Payment> payments = new ArrayList<Payment>(); //收款明显

	private List<Delivery> deliveries = new ArrayList<Delivery>(); //发货
	private List<Invoice> invoices = new ArrayList<Invoice>(); //发票
	private List<Cash> cashes = new ArrayList<Cash>(); //回款
	

	public Order(String id, String ordernumber,java.sql.Timestamp signedDate, String business, int year){
		this.id = id;
		this.ordernumber = ordernumber;
		this.signedDate = signedDate;
		this.business = business;
		this.year = year;
	}
	
	public boolean fitOrdernumber(String ordernumber){
		return this.ordernumber.equals(ordernumber) ? true : false;
	}

	public String getOrdernumber(){
		return this.ordernumber;
	}
	
	
	public void addPayment(Payment payment){
		this.payments.add(payment);
	}
	
	public void addItem(Item item){
		this.items.add(item);
	}
	
	public void addDelivery(Delivery delivery){
		this.deliveries.add(delivery);
	}
	
	public void addInvoice(Invoice invoice){
		this.invoices.add(invoice);
	}
	
	public void addCash(Cash cash){
		this.cashes.add(cash);
	}
	
	public BigDecimal getOrderDebit(){
		BigDecimal debit = new BigDecimal("0.00");
		for(Payment p : this.payments){
			debit = debit.add(p.getDebit());
		}
		for(Cash c : this.cashes){
			debit = debit.subtract(c.getAmount());
		}
		return debit;
	}
	
	public boolean isDebitOrder(){
		return this.getOrderDebit().compareTo(new BigDecimal("0.00"))==1 ? true : false;
	}
	
	public Map<String,String> getOrderDebitInfo(){

		Map<String,String> m = new HashMap<String,String>();
		m.put("ordernumber", ordernumber);
		m.put("signedDate", Convert.t2s(this.signedDate, "yyyy-MM-dd"));
		m.put("debit", this.getOrderDebit().toString());
		return m;
	}
	
	
	public Map<String,Object> getOrderDebitDetail(){
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("ordernumber", ordernumber);
		m.put("signedDate", Convert.t2s(this.signedDate,"yyyy-MM-dd"));
		m.put("debit", this.getOrderDebit().toString());
		m.put("payments", this.getPayments());
		m.put("cashs",this.getCashes());
		return m;
	}

	private List<Map<String,String>> getPayments(){
		List<Map<String,String>> l = new ArrayList<Map<String,String>>();
		for(Payment p : payments){
			l.add(p.getPaymentInfo());
		}
		return l;
	}
	
	private List<Map<String,String>> getCashes(){
		List<Map<String,String>> l = new ArrayList<Map<String,String>>();
		for(Cash c : cashes){
			l.add(c.getCashInfo());
		}
		return l;
	}
	
	
	public double amountOfOrder(){
		double amount = 0.0;
		for(Item it : items){
			amount =+ it.total();
		}
		return amount;
	}
	
	public double amountOfDeliveries(){
		double amount = 0.0;
		for(Delivery it : deliveries){
			amount =+ it.amountOfDelivery();
		}
		return amount;
	}
	
	public double amountOfInvoices(){
		double amount = 0.0;
		for(Invoice it : invoices){
			amount =+ it.amountOfInvoice();
		}
		return amount;
	}
	public java.math.BigDecimal amountOfCashes(){
		java.math.BigDecimal amount = new BigDecimal("0.00");
		for(Cash it : cashes){
			amount.add(it.getAmount());
		}
		return amount;
	}



	public void setAmount(java.math.BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return ""+"\n"+"Order [id=" + id + ", ordernumber=" + ordernumber
				+ ", signedDate=" + signedDate + ", business=" + business
				+ ", year=" + year + ", amount=" + amount + ", "+"\n"+"items=" + items
				+ ", "+"\n"+" peyments=" + payments + ", "+"\n"+" deliveries=" + deliveries
				+ ", "+"\n"+" invoices=" + invoices + ", "+"\n"+" cashes=" + cashes + "]";
	}


	
}
