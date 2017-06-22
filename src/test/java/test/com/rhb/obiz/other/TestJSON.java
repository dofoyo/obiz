package test.com.rhb.obiz.other;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

public class TestJSON {
	@Test
	public void testJsonFromMap(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("userName", "rhb");
		map.put("tel", "18508118669");
		
		JSONObject js = new JSONObject(map);
		System.out.println(js.toJSONString());
		System.out.println(JSONValue.toJSONString(map));
	}
	
	@Test
	public void testJsonValueFromMap(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("userName", "rlw");
		map.put("tel", "13568986330");
		
		System.out.println(JSONValue.toJSONString(map));
	}
	
	
	@Test
	public void testJsonFromObject(){
		A a = new A("zl","13548015698");
		System.out.println(JSONValue.toJSONString(a));
	}
}
