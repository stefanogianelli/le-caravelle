package dtos;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class PersonaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message="Inserire un nome")
	private String nome;

	@NotEmpty(message="Inserire un cognome")
	private String cognome;

	private Date dataNascita;
	
	@NotEmpty(message="Inserire un documento d'identità")
	private String documentoIdentita;

	@Pattern(regexp="^\\+(?:[0-9] ?){6,14}[0-9]$",
			message="Numero di telefono non valido")
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
