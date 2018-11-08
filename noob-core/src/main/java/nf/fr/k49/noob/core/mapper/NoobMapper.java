package nf.fr.k49.noob.core.mapper;

import java.lang.reflect.Type;

public interface NoobMapper {

	<T> T toObject(String json, Type type);

	<T> String toJson(T obj);
}
