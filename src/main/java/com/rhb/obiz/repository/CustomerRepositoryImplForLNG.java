package com.rhb.obiz.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.JdbcTemplate;

import com.rhb.obiz.domain.Cash;
import com.rhb.obiz.domain.Customer;
import com.rhb.obiz.domain.Order;
import com.rhb.obiz.domain.Payment;

public class CustomerRepositoryImplForLNG implements CustomerRepository {
	private JdbcTemplate jdbcTemplate;
	
	public CustomerRepositoryImplForLNG(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public String[] getIds() {
		String sql = "select fitemid from t_Organization where fdeleted=0";
		List<String> list = this.jdbcTemplate.queryForList(sql,String.class);
		
		return list.toArray(new String[0]);
	}
	
	@Override
	public Customer find(String id) {
		return this.createCustomerFromDB(id);
	}

//----------------------------------------------------
	
	private Customer createCustomerFromDB(String customerId){
		Customer customer = this.assembleCustomerFromDB(customerId);
		customer.addOrders(this.assembleOrdersFromDB(customerId));
		//System.out.println(customer.getDebitInfo());
		//System.out.println(customer.toString());
		return customer;
	}
	
	private List<Order> assembleOrdersFromDB(String customerId){
		List<Order> orders = new ArrayList<Order>();
		String sql = "select fbillno from dbo.t_lngsellorder where fchangemark='0' and fcustid=?";
		Set<String> fbillnos = new HashSet<String>(this.jdbcTemplate.queryForList(sql,new Object[]{customerId},String.class));  //通过set去掉重复的
		for(String fbillno : fbillnos){
			 orders.add(this.assembleOrderFromDB(fbillno));
		}
		
		return orders;
	}

	private Order assembleOrderFromDB(String fbillno){
		//System.out.println(fbillno);
		Order order = null;
		String sql = "select it.fname,tt.fhtdate,tt.f2017contract,tt.fhtamount from dbo.t_lngsellorder as tt inner join dbo.t_item as it on tt.fhtselfid=it.fitemid where fchangemark='0' and fbillno=?";
		Map<String, Object> map = this.jdbcTemplate.queryForMap(sql,new Object[]{fbillno});
		String ordernumber = (String)map.get("fname");
		java.sql.Timestamp signedDate = (java.sql.Timestamp)map.get("fhtdate");
		Integer is2017 = (Integer)map.get("f2017contract");
		java.math.BigDecimal amount = (java.math.BigDecimal)map.get("fhtamount");
		int year = (is2017==null || is2017==0)? this.getYear(signedDate) : 2017;
		order = new Order(fbillno,ordernumber,signedDate,"LNG",year);
		order.setAmount(amount);
		
		List<Payment> payments = this.assemblePaymentFromDb(fbillno,amount);
		for(Payment p : payments){
			if(p.getRate().compareTo(BigDecimal.ZERO)==1){
				order.addPayment(p);
			}
		}

		List<Cash> cashes = this.assembleCashFromDb(ordernumber);
		for(Cash c : cashes){
			if(c.getAmount().compareTo(BigDecimal.ZERO)==1){
				order.addCash(c);
			}
		}
		
		return order;
	}
	
	private List<Payment> assemblePaymentFromDb(String fbillno,BigDecimal amount){
		List<Payment> list = new ArrayList<Payment>();
		String sql = "SELECT fadvancepaydate,fadvancepaytxt,fadvancepaypr,fpickuppaydate,fpickuppaytxt,fpickuppaypr,fpaymentspaydate,fpaymentspaytxt,fpaymentspaypr,fdebugpaydate,fdebugpaytxt,fdebugpaypr,fqualitypaydate,	fqualitypaytxt,	fqualitypaypr,fotherpaydate,fotherpaytxt,fotherpaypr from t_lngsellorder where fchangemark=0 and fbillNo=?";
		Map<String, Object> map = this.jdbcTemplate.queryForMap(sql,new Object[]{fbillno});
		
		Payment advance = new Payment("预付款",(String)map.get("fadvancepaytxt"),(java.math.BigDecimal)map.get("fadvancepaypr"),this.getTimestamp((String)map.get("fadvancepaydate")),amount);
		Payment pickup = new Payment("提货款",(String)map.get("fpickuppaytxt"),(java.math.BigDecimal)map.get("fpickuppaypr"),this.getTimestamp((String)map.get("fpickuppaydate")),amount);
		Payment payments = new Payment("到货款",(String)map.get("fpaymentspaytxt"),(java.math.BigDecimal)map.get("fpaymentspaypr"),this.getTimestamp((String)map.get("fpaymentspaydate")),amount);
		Payment debug = new Payment("调试款",(String)map.get("fdebugpaytxt"),(java.math.BigDecimal)map.get("fdebugpaypr"),this.getTimestamp((String)map.get("fdebugpaydate")),amount);
		Payment quality = new Payment("质保款",(String)map.get("fqualitypaytxt"),(java.math.BigDecimal)map.get("fqualitypaypr"),this.getTimestamp((String)map.get("fqualitypaydate")),amount);
		Payment other = new Payment("其他款",(String)map.get("fotherpaytxt"),(java.math.BigDecimal)map.get("fotherpaypr"),this.getTimestamp((String)map.get("fotherpaydate")),amount);
		list.add(advance);
		list.add(pickup);
		list.add(payments);
		list.add(debug);
		list.add(quality);
		list.add(other);
		
		return list;
	}

	private List<Cash> assembleCashFromDb(String ordernumber){
		List<Cash> list = new ArrayList<Cash>();
		String sql = "SELECT fadvancepaynow,fpickuppaynow,fpaymentspaynow,fdebugpaynow,fqualitypaynow,fotherpaynow,fdate from t_paymentorder t1 where fhtselfnumber=?";
		//System.out.println("fbillno=" + ordernumber);
		List<Map<String,Object>> l = this.jdbcTemplate.queryForList(sql,new Object[]{ordernumber});
		for(Map<String,Object> map : l){
			Cash advance = new Cash("预付款",(java.math.BigDecimal)map.get("fadvancepaynow"),(java.sql.Timestamp)map.get("fdate"));
			Cash pickup = new Cash("提货款",(java.math.BigDecimal)map.get("fpickuppaynow"),(java.sql.Timestamp)map.get("fdate"));
			Cash payments = new Cash("到货款",(java.math.BigDecimal)map.get("fpaymentspaynow"),(java.sql.Timestamp)map.get("fdate"));
			Cash debug = new Cash("调试款",(java.math.BigDecimal)map.get("fdebugpaynow"),(java.sql.Timestamp)map.get("fdate"));
			Cash quality = new Cash("质保款",(java.math.BigDecimal)map.get("fqualitypaynow"),(java.sql.Timestamp)map.get("fdate"));
			Cash other = new Cash("其他款",(java.math.BigDecimal)map.get("fotherpaynow"),(java.sql.Timestamp)map.get("fdate"));
			list.add(advance);
			list.add(pickup);
			list.add(payments);
			list.add(debug);
			list.add(quality);
			list.add(other);
		}
		return list;
	}	
	
	
	private Customer assembleCustomerFromDB(String customerId){
		String sql1 = "select fname, faddress from t_organization where fitemid=?";
		Map map1 = (Map)this.jdbcTemplate.queryForMap(sql1,new Object[]{customerId});
		String fname = (String)map1.get("fname");
		String faddress = (String)map1.get("faddress");
		
		String farea = null;
		String fprovince = null;
		String fclerkname = null;		
		String sql2 = "select farea,fprovince,fclerkname from rms_custom where fcustid=?";
		List<Map<String,Object>> list =this.jdbcTemplate.queryForList(sql2,new Object[]{customerId});
		if(list.size()>0){
			Map map2 = list.get(0);
			farea = (String)map2.get("farea");
			fprovince = (String)map2.get("fprovince");
			fclerkname = (String)map2.get("fclerkname");		
		}
		
		
		Customer customer = new Customer(customerId,fname,faddress,farea,fprovince,fclerkname);
		return customer;
	}
	
	private int getYear(java.sql.Timestamp date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(calendar.YEAR);
	}
	
	private Timestamp getTimestamp(String dueDate){
		Timestamp due = null;
		if(dueDate!=null && !dueDate.isEmpty()){
			StringBuilder dd = new StringBuilder(dueDate);
			try{
				//System.out.println("dueDate = " + dueDate);
				dd.insert(6, "-");
				dd.insert(4, "-");
				dd.append(" 00:00:00");
				//System.out.println(dd.toString());
				due = Timestamp.valueOf(dd.toString());
			}catch(Exception e){
				//System.out.println("*********** error : " + this.term + ", date = '" +this.dueDate + "' **************");
				//e.printStackTrace();
			}
		}
		return due;
		
	}

}
