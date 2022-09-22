package com.utils.mylibs;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;

public class GsonUtil {
	private static final Gson gson = new GsonBuilder()
			//.setPrettyPrinting()
			//.enableComplexMapKeySerialization()
			.serializeSpecialFloatingPointValues()
			.serializeNulls()
			.create();
	
	public static Gson getInstance() {
		return gson;
	}
	
	public static void write(Object object, String filename) throws JsonIOException, IOException {
		Writer writer = new FileWriter(filename);
		gson.toJson(object, writer);
	        writer.flush(); 
	        writer.close(); 
	}

	public static List<String> readList(String filename) throws IOException {
	    Type listType = new TypeToken<List<String>>(){}.getType(); 
	    Reader reader = Files.newBufferedReader(Paths.get(filename));
	    List<String> list = gson.fromJson(reader, listType);
	    return list;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T read(String filename, Type type) throws IOException {
	    Reader reader = Files.newBufferedReader(Paths.get(filename));
	    T object = (T) gson.fromJson(reader, type);
		return object;
	}
}
