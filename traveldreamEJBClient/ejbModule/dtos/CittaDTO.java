package dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

public class CittaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	@NotEmpty(message="Inserire una nazione")
	private String nazione;

	@NotEmpty(message="Inserire un nome")
	private String nome;

	@NotEmpty(message="Inserire una regione")
	private String regione;
	
	private float latitudine;
	
	private float longitudine;
	
	private List<String> immagini;
	
	private int attivo;
	
	public CittaDTO () {
		immagini = new ArrayList<String>();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNazione() {
		return nazione;
	}

	public void setNazione(String nazione) {
		this.nazione = nazione;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRegione() {
		return regione;
	}

	public void setRegione(String regione) {
		this.regione = regione;
	}
	
	public float getLatitudine() {
		return latitudine;
	}

	public void setLatitudine(float latitudine) {
		this.latitudine = latitudine;
	}

	public float getLongitudine() {
		return longitudine;
	}

	public void setLongitudine(float longitudine) {
		this.longitudine = longitudine;
	}

	public List<String> getImmagini() {
		return immagini;
	}

	public void setImmagini(List<String> immagini) {
		this.immagini = immagini;
	}
	
	public void addImmagine (String immagine) {
		immagini.add(immagine);
	}
	
	public void removeImmagine (String immagine) {
		immagini.remove(immagine);
	}

	public int getAttivo() {
		return attivo;
	}

	public void setAttivo(int attivo) {
		this.attivo = attivo;
	}

	@Override
	public boolean equals (Object other) {		
		if (this.getNome().equals(((CittaDTO) other).getNome()))
			return true;
		else
			return false;
	}
	
}
