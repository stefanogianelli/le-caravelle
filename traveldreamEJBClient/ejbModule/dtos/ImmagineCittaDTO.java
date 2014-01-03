package dtos;

import java.io.Serializable;

public class ImmagineCittaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String immagine;

	public String getImmagine() {
		return immagine;
	}

	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}

}
