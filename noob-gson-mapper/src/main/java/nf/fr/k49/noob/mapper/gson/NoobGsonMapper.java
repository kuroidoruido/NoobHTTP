package nf.fr.k49.noob.mapper.gson;


import java.lang.reflect.Type;

import com.google.gson.Gson;

import nf.fr.k49.noob.core.mapper.NoobMapper;

public class NoobGsonMapper implements NoobMapper {

	private Gson gson;
	
	public NoobGsonMapper(final Gson gson) {
		this.gson = gson;
	}
	
	public <T> T toObject(String json, Type type) {
		return gson.fromJson(json, type);
	}

	public <T> String toJson(T obj) {
		return gson.toJson(obj);
	}

}
