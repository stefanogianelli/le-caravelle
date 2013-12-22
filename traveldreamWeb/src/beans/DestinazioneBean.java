package beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import utils.JsfUtil;
import dtos.DestinazioneDTO;
import dtos.EscursioneDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.DestinazioneInesistenteException;
import eccezioni.EscursioneInesistenteException;
import eccezioni.HotelInesistenteException;
import ejbs.GestoreDestinazione;

@ManagedBean(name="destinazione")
@RequestScoped
public class DestinazioneBean {

	@EJB
	private GestoreDestinazione destinazioneBean;
	
	private DestinazioneDTO destinazione;
	
	@PostConstruct
	public void setUp () {
		destinazione = new DestinazioneDTO();
	}

	public DestinazioneDTO getDestinazione() {
		return destinazione;
	}

	public void setDestinazione(DestinazioneDTO destinazione) {
		this.destinazione = destinazione;
	}
	
	/**
	 * Permette la modifica dei dati di una destinazione
	 * @param destinazione I dati della destinazione
	 */
	public void modificaDestinazione (DestinazioneDTO destinazione) {
		try {
			destinazioneBean.modificaDatiDestinazione(destinazione);
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta!");
		} catch (HotelInesistenteException e) {
			JsfUtil.errorMessage("Hotel inesistente!");
		}
	}
	
	/**
	 * Permette di aggiungere un'escursione ad una destinazione
	 * @param destinazione La destinazione nella quale aggiungere l'escursione
	 * @param escursione L'escursione scelta
	 * @param numeroPartecipanti Il numero dei partecipanti all'escursione
	 */
	public void aggiuntaEscursione (DestinazioneDTO destinazione, EscursioneDTO escursione, int numeroPartecipanti) {
		try {
			destinazioneBean.aggiuntaEscursione(destinazione, escursione, numeroPartecipanti);
		} catch (EscursioneInesistenteException e) {
			JsfUtil.errorMessage("Escursione inesistente!");
		} catch (DestinazioneInesistenteException e) {
			JsfUtil.errorMessage("Destinazione inesistente!");
		}
	}
	
	/**
	 * Permette la modifica del numero di partecipanti ad una escursione
	 * @param destinazione La destinazione di riferimento
	 * @param escursione L'escursione di riferimento
	 * @param numeroPartecipanti Il nuovo numero di partecipanti
	 */
	public void modificascursione (DestinazioneDTO destinazione, EscursioneDTO escursione, int numeroPartecipanti) {
		try {
			destinazioneBean.modificaDatiEscursione(destinazione, escursione, numeroPartecipanti);
		} catch (EscursioneInesistenteException e) {
			JsfUtil.errorMessage("Escursione inesistente!");
		} catch (DestinazioneInesistenteException e) {
			JsfUtil.errorMessage("Destinazione inesistente!");
		}
	}
	
	/**
	 * Permette la rimozione di un'escursione da una destinazione
	 * @param destinazione La destinazione dalla quale rimuovere l'escursione
	 * @param escursione L'escursione da rimuovere
	 */
	public void eliminaEscursione (DestinazioneDTO destinazione, EscursioneDTO escursione) {
		try {
			destinazioneBean.eliminaEscursione(destinazione, escursione);
		} catch (EscursioneInesistenteException e) {
			JsfUtil.errorMessage("Escursione inesistente!");
		} catch (DestinazioneInesistenteException e) {
			JsfUtil.errorMessage("Destinazione inesistente!");
		}
	}
}
