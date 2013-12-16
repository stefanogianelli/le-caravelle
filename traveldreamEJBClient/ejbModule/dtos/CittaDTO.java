package dtos;

public class CittaDTO {

	private String nazione;

	private String nome;

	private String regione;
	
	public CittaDTO () {
		
	}

	public CittaDTO (String nazione, String nome, String regione) {
		this.nazione = nazione;
		this.nome = nome;
		this.regione = regione;
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
	
}
