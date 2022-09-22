package com.utils.mylibs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * <p> Classe utilitária que busca a data da compilação do pacote JAR executável deste programa.</p>
 * É considerado como data da compilação a mesma data do arquivo 
 * /META-INF/MANIFEST.MF que é criado e empacotado junto na mesma data e hora da compilação do executável JAR.
 * 
 * @author Rodrigo Eggea
 *
 */
public class BuildVersion {

	/**
	 * <p> Retorna a data de compilacao do arquivo JAR executavel. </p>
	 * Se não for um arquivo JAR, estiver rodando pela IDE vai retornar <code> null </code>. 
	 * A data da compilação é considerada a mesma do arquivo /META-INF/MANIFEST.MF que 
	 * é gerado na exportação do JAR.
	 * 
	 * @return Data da compilação do arquivo JAR atual, ou null se não for um JAR.
	 */
	public static String getCompiledDate() {
		String manifestLastModified = null;
		try {
			Date date = getLastUpdatedTime(getJarPath() + "/" + getJarFilename(), "/META-INF/MANIFEST.MF");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyddMM-HHmmss");
			manifestLastModified = sdf.format(date);
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return manifestLastModified;
	}
	
	/**
	 * Descobre o path do arquivo JAR executável deste programa.
	 * @return
	 */
	private static String getJarPath() {
		File jarDir = new File(ClassLoader.getSystemClassLoader().getResource(".").getPath());
		return jarDir.getAbsolutePath();
	}

	/**
	 * Descobre o nome do arquivo JAR executável deste programa.
	 * @return
	 * @throws FileNotFoundException
	 */
	private static String getJarFilename() throws FileNotFoundException {
		String path = BuildVersion.class.getResource(BuildVersion.class.getSimpleName() + ".class").getFile();
		if (path.startsWith("/")) {
			throw new FileNotFoundException("ERRO: Não foi possível obter data da compilação, não é um arquivo JAR. \n");
		}
		path = ClassLoader.getSystemClassLoader().getResource(path).getFile();
		File jarFile = new File(path.substring(0, path.lastIndexOf('!')));
		return jarFile.getName();
	}

	/**
	 * Retorna a data da última modificação de qualquer arquivo dentro do JAR especificado.
	 * @param jarFilePath - Caminho completo do arquivo JAR
	 * @param classFilePath - Arquivo dentro do JAR que se deseja saber a data da última modificação.
	 * @return
	 */
	private static Date getLastUpdatedTime(String jarFilePath, String classFilePath) {
		JarFile jar = null;
		try {
			jar = new JarFile(jarFilePath);
			Enumeration<JarEntry> enumEntries = jar.entries();
			while (enumEntries.hasMoreElements()) {
				JarEntry file = (JarEntry) enumEntries.nextElement();
				if (file.getName().equals(classFilePath.substring(1))) {
					long time = file.getTime();
					return time == -1 ? null : new Date(time);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (jar != null) {
				try {
					jar.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
