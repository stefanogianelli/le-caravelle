package dtos;

import java.io.Serializable;

import javax.validation.constraints.Min;

public class AttivitaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private DestinazioneDTO destinazione;
	
	private EscursioneDTO escursione;
	
	@Min(value = 1, message = "Il numero di partecipanti all'escursione deve essere maggiore o uguale a 1")
	private int numeroPartecipanti;
	
	public AttivitaDTO () {
		destinazione = new DestinazioneDTO();
		escursione = new EscursioneDTO();
	}

	public DestinazioneDTO getDestinazione() {
		return destinazione;
	}

	public void setDestinazione(DestinazioneDTO destinazione) {
		this.destinazione = destinazione;
	}

	public EscursioneDTO getEscursione() {
		return escursione;
	}

	public void setEscursione(EscursioneDTO escursione) {
		this.escursione = escursione;
	}

	public int getNumeroPartecipanti() {
		return numeroPartecipanti;
	}

	public void setNumeroPartecipanti(int numeroPartecipanti) {
		this.numeroPartecipanti = numeroPartecipanti;
	}
}
