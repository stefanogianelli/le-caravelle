package beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import utils.JsfUtil;
import dtos.DestinazioneDTO;
import dtos.EscursioneDTO;
import eccezioni.DestinazioneInesistenteException;
import eccezioni.EscursioneEsistenteException;
import eccezioni.EscursioneInesistenteException;
import ejbs.GestoreDestinazione;

@ManagedBean(name="destinazione")
@ViewScoped
public class DestinazioneBean {

	@EJB
	private GestoreDestinazione destinazioneBean;
	
	private DestinazioneDTO destinazione;
	private int partecipanti;
	
	@PostConstruct
	public void setUp () {
		partecipanti = 1;
		destinazione = new DestinazioneDTO();
	}

	public DestinazioneDTO getDestinazione() {
		return destinazione;
	}

	public void setDestinazione(DestinazioneDTO destinazione) {
		this.destinazione = destinazione;
	}
	
	public int getPartecipanti() {
		return partecipanti;
	}

	public void setPartecipanti(int partecipanti) {
		this.partecipanti = partecipanti;
	}

	/**
	 * Permette di aggiungere un'escursione ad una destinazione
	 * @param idPacchetto L'identificativo del pacchetto
	 * @param idDestinazione L'identificativo della destinazione nella quale aggiungere l'escursione
	 * @param escursione L'escursione scelta
	 */
	public String aggiuntaEscursione (int idPacchetto, int idDestinazione, EscursioneDTO escursione) {
		try {
			destinazioneBean.aggiuntaEscursione(idDestinazione, escursione.getId(), this.getPartecipanti());
			return "dettagliPacchetto?idPacchetto=" + idPacchetto + "&faces-redirect=true";
		} catch (EscursioneInesistenteException e) {
			JsfUtil.errorMessage("Escursione inesistente!");
		} catch (DestinazioneInesistenteException e) {
			JsfUtil.errorMessage("Destinazione inesistente!");
		} catch (EscursioneEsistenteException e) {
			JsfUtil.errorMessage("Escursione già inserita!");
		}
		return null;
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
