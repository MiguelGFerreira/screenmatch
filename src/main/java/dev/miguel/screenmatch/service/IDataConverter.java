package dev.miguel.screenmatch.service;

public interface IDataConverter {
	<T> T getData(String json, Class<T> classe);
}
