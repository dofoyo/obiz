package com.rhb.obiz.web;

import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rhb.obiz.service.CustomerService;

import net.minidev.json.JSONValue;


/**
 * Root resource (exposed at "order" path)
 */
@Path("order")
public class OrderAPI {
	private static ApplicationContext appContext = new ClassPathXmlApplicationContext("ApplicationContext.xml");
	
	private static CustomerService cs = (CustomerService) appContext.getBean("customerService");

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
	@Path("id/{id}")
    @Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
    public String getOrderDetail(@PathParam("id")String id) {
		Map<String,Object> order = cs.getOrderDetail(id);
        return JSONValue.toJSONString(order);
    }

    
    @GET
	@Path("debits")
    @Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
    public String getDebitOrderList() {
		List<Map<String,String>> list = cs.getDebitOrders();
		return JSONValue.toJSONString(list);
    }
    
}
