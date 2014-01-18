package dtos;

import java.io.Serializable;

public class AttivitaPredDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private PacchettoPredefinitoDTO pacchetto;
	
	private EscursioneDTO escursione;

	public PacchettoPredefinitoDTO getPacchetto() {
		return pacchetto;
	}

	public void setPacchetto(PacchettoPredefinitoDTO pacchetto) {
		this.pacchetto = pacchetto;
	}

	public EscursioneDTO getEscursione() {
		return escursione;
	}

	public void setEscursione(EscursioneDTO escursione) {
		this.escursione = escursione;
	}
}
