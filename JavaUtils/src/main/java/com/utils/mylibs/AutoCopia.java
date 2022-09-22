package com.utils.mylibs;

import java.io.Serializable;

/**
 * <p> Interface com método default que realiza "Deep Copy" da instância </p>
 * Basta implementar esta interface em qualquer classe que ela ganhará o método
 * objeto.autoCopia(); que retorna uma cópia idêntica do objeto
 */
public interface AutoCopia extends Serializable{
	static final long serialVersionUID = 1L;
	@SuppressWarnings("unchecked")
	default <T> T autoCopia() {
		return (T) ObjectCloner.deepCopy(this);
	}
}
