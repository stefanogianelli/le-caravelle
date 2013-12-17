package dtos;

import java.io.Serializable;

public class AttivitaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int idDestinazione;
	
	private int idEscursione;
	
	private int numeroPartecipanti;

	public int getIdDestinazione() {
		return idDestinazione;
	}

	public void setIdDestinazione(int idDestinazione) {
		this.idDestinazione = idDestinazione;
	}

	public int getIdEscursione() {
		return idEscursione;
	}

	public void setIdEscursione(int idEscursione) {
		this.idEscursione = idEscursione;
	}

	public int getNumeroPartecipanti() {
		return numeroPartecipanti;
	}

	public void setNumeroPartecipanti(int numeroPartecipanti) {
		this.numeroPartecipanti = numeroPartecipanti;
	}
}
