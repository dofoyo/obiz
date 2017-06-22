package test.com.rhb.obiz.service;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rhb.obiz.service.CustomerService;

public class TestReceivablesService{
	
	private static ApplicationContext appContext;
	
	private static CustomerService rs;
	
	@Before
	public void initial() {
		appContext = new ClassPathXmlApplicationContext("TestApplicationContext.xml");
		rs = (CustomerService) appContext.getBean("customerService");
	}
	
	//@Test
	public void testGetDebitCustomers(){
		long starTime=System.currentTimeMillis();
		List<Map<String, String>> list = rs.getDebitCustomers();
		System.out.println("********* there are " + list.size() + " debit customers ***************");
		for(Map<String, String> s : list){
			System.out.println(s);
		}
		long endTime=System.currentTimeMillis();
		System.out.println(endTime - starTime);
		System.out.println("********** testGetDebitCustomers over *************");
	}

	//@Test
	public void testgetDebitCustomer(){
		String customerId = "690";
		Map debit = rs.getDebitCustomer(customerId);
		//String debitDetail = rs.getDebitDetail(customerId);
		System.out.println(debit);
		//System.out.println(debitDetail);
		System.out.println("********** testFindDateErrorOrders over *************");

	}

	
	//@Test
	public void testGetDebitOrders(){
		long starTime=System.currentTimeMillis();
		List<Map<String, String>> list = rs.getDebitOrders();
		System.out.println("********* there are " + list.size() + " debit orders ***************");
		for(Map<String, String> s : list){
			System.out.println(s);
		}
		long endTime=System.currentTimeMillis();
		System.out.println(endTime - starTime);
		System.out.println("**********testGetDebitOrders over *************");
	}
	
	//@Test
	public void testGetDebitOrdersByCustomerId(){
		String customerId = "690";
		List<Map<String, String>> list = rs.getDebitOrders(customerId);
		for(Map<String, String> s : list){
			System.out.println(s);
		}
		System.out.println("**********testGetDebitOrdersByCustomerId over *************");
	}
	
	//@Test
	public void testGetDebitOrdersByCustomerIdAndOrdernumber(){
		String customerId = "690";
		String ordernumber = "DLX140610-1229";
		Map<String, String> m = rs.getDebitOrder(customerId,ordernumber);
		System.out.println(m);
		System.out.println("**********testGetDebitOrdersByCustomerIdAndOrdernumber over *************");
	}	
	
	@Test
	public void testGetDebitOrderDetail(){
		String customerId = "690";
		String ordernumber = "DLX140610-1229";
		Map<String, Object> m = rs.getDebitOrderDetail(customerId,ordernumber);
		System.out.println(m);
		System.out.println("**********testGetDebitOrderDetail over *************");
	}	
}
