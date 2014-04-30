package com.jajeem.licensing.util;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConvert {
	public String ConvertToJson(Object obj) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		return mapper.writeValueAsString(obj);
	}
	
	public <T> T ConvertFromJson(String json,Class<T> cls) throws IOException{
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper.readValue(json, cls);
	}
}
