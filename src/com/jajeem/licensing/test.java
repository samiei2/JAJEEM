package com.jajeem.licensing;

import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jajeem.licensing.util.JsonConvert;

public class test {
	public static void main(String[] args) throws IOException {
		JsonConvert converter = new JsonConvert();
		HashMap<String, String> map = new HashMap<>();
		map.put("serial", "123");
		map.put("serial1", "123");
		map.put("serial2", "123");
		map.put("serial3", "123");
		map.put("serial4", "123");
		map.put("serial5", "123");
		String jsonstr = converter.ConvertToJson(map);
		HashMap<String, String> map2 = converter.ConvertFromJson(jsonstr.replace("\"serial5\":\"123\",", "\"serial5\":\"123\",\"test\":\"123\","), HashMap.class);
		System.out.println(map2);
	}
	
	private static void ChangeValidity(boolean valid2) {
		valid2 = !valid2;
	}

	public boolean valid;
}
