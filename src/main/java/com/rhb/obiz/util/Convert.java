package com.rhb.obiz.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Convert {
	public static String t2s(Timestamp timestamp,String pattern){
		String str;
		if(timestamp==null){
			str = "";
		}else{
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			str = df.format(timestamp);
		}
		return str;
	}
	
}
