package nf.fr.k49.noob.demo.app;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import nf.fr.k49.noob.core.Noob;
import nf.fr.k49.noob.demo.app.model1.Element;
import nf.fr.k49.noob.mapper.gson.adapters.LocalDateAdapter;
import nf.fr.k49.noob.mapper.gson.adapters.LocalDateTimeAdapter;
import nf.fr.k49.noob.mapper.gson.NoobGsonMapper;

public class App {
	public static void main(String[] args) {
		final StatusCtl statusCtl = new StatusCtl();
		final ElementCtl elementCtl = new ElementCtl();

		System.out.println("Starting NoobServer...");
		Noob.builder()//
				.listenPort(8081)//
				.mapper(new NoobGsonMapper(getGson()))//
				.get("/status", statusCtl::getStatus, Void.class)//
				.put("/elements", elementCtl::add, Element.class)//
				.start();
		System.out.println("NoobServer started");
	}

	public static Gson getGson() {
		return new GsonBuilder()//
				.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())//
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())//
				.create();
	}
}
