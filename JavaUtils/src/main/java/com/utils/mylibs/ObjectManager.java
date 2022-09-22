package com.utils.mylibs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Classe utilitária para serializar e desserializar objetos para arquivo.
 * 
 * @author Rodrigo Eggea
 */
public class ObjectManager {

	/**
	 * Escreve objeto para um arquivo.
	 * @param object 
	 * @param filename
	 * @throws Exception
	 */
	public static void writeToFile(Object object, String filename) throws Exception {
		FileOutputStream f = new FileOutputStream(new File(filename));
		ObjectOutputStream os = new ObjectOutputStream(f);
		os.writeObject(object);
		os.close();
		f.close();
	}

	/**
	 * Restaura um objeto de um arquivo.
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T readFromFile(String filename) throws Exception {
		FileInputStream fi = new FileInputStream(new File(filename));
		ObjectInputStream oi = new ObjectInputStream(fi);
		T object = (T) oi.readObject();
		oi.close();
		fi.close();
		return object;
	}
}
