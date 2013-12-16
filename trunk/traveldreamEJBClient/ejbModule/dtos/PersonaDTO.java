package dtos;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class PersonaDTO {

	@NotEmpty
	private String nome;

	@NotEmpty
	private String cognome;

	@NotEmpty
	private Date dataNascita;
	
	@NotEmpty
	private String documentoIdentita;

	@NotEmpty
	private String telefono;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getDocumentoIdentita() {
		return documentoIdentita;
	}

	public void setDocumentoIdentita(String documentoIdentita) {
		this.documentoIdentita = documentoIdentita;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
}
