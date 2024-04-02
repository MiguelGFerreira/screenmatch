package dev.miguel.screenmatch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataConverter implements IDataConverter {

	private ObjectMapper mapper = new ObjectMapper();


	@Override
	public <T> T getData(String json, Class<T> classe) {
		try {
			return mapper.readValue(json, classe);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	
}
