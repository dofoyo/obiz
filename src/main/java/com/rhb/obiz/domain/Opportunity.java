package com.rhb.obiz.domain;

import java.util.Date;
import java.util.List;

/*
 * 机会
 */
public class Opportunity {

	private String cludeID;
	private String id;
	private String description;
	
	private Salesman creator; //机会的创建人
	private Date creationDate; //创建日期
	
	private String opportunitynumber; //编号，完成立项后才有，未来的合同号和订单号都以此基础编制
	
	private Contract contract; //机会生成合同
	
}
