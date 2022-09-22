package com.utils.mylibs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Classe utilitária para fazer Deep Copy de qualquer objeto que implemente serializable. <br>
 * 
 * @author Rodrigo Eggea
 *
 */
public class ObjectCloner {
    // construtor privado para que ninguém consiga instancias essa classe utilitária.
    private ObjectCloner() {
    }

    /**
     * Retorna uma cópia (deep copy) de qualquer objeto que implemente serializable. <br>
     * Como utilizar: <br>
     * <code> CustomClass custom = new CustomClass(); <br>
     * CustomClass customCopy = (CustomClass) ObjectCloner.deepCopy(custom) <br>
     * 
     * @param t
     *            - Objeto a ser copiado.
     * @return Retorna uma copia do objeto, ou null se ocorrer erro na clonagem.
     */
    @SuppressWarnings("unchecked")
    static public <T> T deepCopy(T t) {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            // serialize and pass the object
            oos.writeObject(t);
            oos.flush();
            ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bin);
            // return the new object
            return (T) ois.readObject();
        } catch (Exception e) {
            System.out.println("Exception in ObjectCloner = " + e);
            // throw(e);
        } finally {
            try {
                oos.close();
                ois.close();
            } catch (IOException e) {
                System.out.println("Exception in Closing Stream = " + e);
            }
        }
        return null;
    }
}