package com.utils.mylibs;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;

/**
 * Exemplos para escrever e ler instâncias em JSON
 * @author Rodrigo Eggea
 */
public class GsonSamples {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) throws JsonIOException, IOException {

        // ************** SALVAR E LER OBJETOS GRANDES *************
        List<String> nomes = new ArrayList<>();
        nomes.add("Rodrigo");
        nomes.add("Fulano");
        nomes.add("Ciclano");

        // Escrevendo arquivo grande
        Writer writer = new FileWriter("c:/temp/nomes.json");
        System.out.println("Dados salvos    = " + nomes);
        gson.toJson(nomes, writer);
        writer.flush();
        writer.close();

        // Lendo um arquivo grande
        Type listType = new TypeToken<List<String>>() {
        }.getType();
        Reader reader = Files.newBufferedReader(Paths.get("c:/temp/nomes.json"));
        List<String> loadedNames = gson.fromJson(reader, listType);
        System.out.println("Dados carregados= " + loadedNames);

        // ************* READ WRITE CUSTOM CLASS ****************
        Pessoa person = new Pessoa("Rodrigo");
        Files.write(Paths.get("c:/temp/pessoa.json"), gson.toJson(person).getBytes()); // ESCREVENDO
        Pessoa loadedPerson = gson.fromJson(Files.readString(Paths.get("c:/temp/pessoa.json")), Pessoa.class); // LENDO
        System.out.println("PESSOA DO ARQUIVO= " + loadedPerson);

        // ************** READ WRITE MAP *************************
        Map<Integer, String> idNome = new HashMap<>();
        idNome.put(1, "Rodrigo");
        idNome.put(2, "Fulano");
        idNome.put(3, "Ciclano");

        Type mapType = new TypeToken<Map<Integer, String>>() {
        }.getType();
        Files.write(Paths.get("c:/temp/idnome.json"), gson.toJson(idNome).getBytes());
        Map<Integer, String> idNomesLoaded = gson.fromJson(Files.readString(Paths.get("c:/temp/idnome.json")), mapType);
        System.out.println("MAPA ID NOMES= " + idNomesLoaded);
    }
}

class Pessoa {
    private String nome;
    public Pessoa(String nome) {
        this.nome = nome;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    @Override
    public String toString() {
        return String.format("Pessoa [nome=%s]", nome);
    }
}
