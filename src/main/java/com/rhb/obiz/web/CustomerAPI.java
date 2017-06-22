package com.rhb.obiz.web;

import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.rhb.obiz.service.CustomerService;

import net.minidev.json.JSONValue;
//import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Root resource (exposed at "customer" path)
 */

@Component
@Path("customer")
public class CustomerAPI {
	//private static ApplicationContext appContext = new ClassPathXmlApplicationContext("ApplicationContext.xml");
	
	//private static CustomerService cs = (CustomerService) appContext.getBean("customerService");

	@Autowired
	CustomerService customerService;

	
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
	@Path("greeting/{personname}")
    @Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
    public String getCustomerGreetingPersons(@PathParam("personname")String personname) {

		List<String> persons = customerService.greeting(personname);

        return JSONValue.toJSONString(persons);
    }
	
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
	@Path("id/{id}")
    @Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
    public String getCustomerDebitInfo(@PathParam("id")String id) {
		//System.out.println("******** the customer id is "+id+" *******");

		Map<String,String> customer = customerService.getDebitCustomer(id);
		List<Map<String,String>> order = customerService.getDebitOrders(id);

        return JSONValue.toJSONString(customer) + "\n" + JSONValue.toJSONString(order);
    }

    
    @GET
	@Path("debits")
    @Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
    public String getDebitCustomerList() {
		List<Map<String,String>> list = customerService.getDebitCustomers();
		return JSONValue.toJSONString(list);
    }
    
}
