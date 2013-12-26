package beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import utils.JsfUtil;
import dtos.CittaDTO;
import dtos.DestinazioneDTO;
import dtos.EscursioneDTO;
import eccezioni.DestinazioneInesistenteException;
import eccezioni.EscursioneInesistenteException;
import ejbs.GestoreDestinazione;

@ManagedBean(name="destinazione")
@ViewScoped
public class DestinazioneBean {

	@EJB
	private GestoreDestinazione destinazioneBean;
	
	private DestinazioneDTO destinazione;
	
	@PostConstruct
	public void setUp () {
		destinazione = new DestinazioneDTO();
		destinazione.setCitta(new CittaDTO());
	}

	public DestinazioneDTO getDestinazione() {
		return destinazione;
	}

	public void setDestinazione(DestinazioneDTO destinazione) {
		this.destinazione = destinazione;
	}
	
	/**
	 * Permette di aggiungere un'escursione ad una destinazione
	 * @param destinazione La destinazione nella quale aggiungere l'escursione
	 * @param escursione L'escursione scelta
	 * @param numeroPartecipanti Il numero dei partecipanti all'escursione
	 */
	public void aggiuntaEscursione (DestinazioneDTO destinazione, EscursioneDTO escursione, int numeroPartecipanti) {
		try {
			destinazioneBean.aggiuntaEscursione(destinazione.getId(), escursione.getId(), numeroPartecipanti);
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
			destinazioneBean.modificaDatiEscursione(destinazione.getId(), escursione.getId(), numeroPartecipanti);
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
			destinazioneBean.eliminaEscursione(destinazione.getId(), escursione.getId());
		} catch (EscursioneInesistenteException e) {
			JsfUtil.errorMessage("Escursione inesistente!");
		} catch (DestinazioneInesistenteException e) {
			JsfUtil.errorMessage("Destinazione inesistente!");
		}
	}
}
