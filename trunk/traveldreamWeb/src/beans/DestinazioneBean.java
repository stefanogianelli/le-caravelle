package beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import utils.JsfUtil;
import dtos.AttivitaDTO;
import dtos.DestinazioneDTO;
import eccezioni.DestinazioneInesistenteException;
import eccezioni.EntitaEsistenteException;
import eccezioni.EscursioneInesistenteException;
import eccezioni.NumeroPartecipantiException;
import ejbs.GestoreDestinazione;

@ManagedBean(name="destinazione")
@ViewScoped
public class DestinazioneBean {

	@EJB
	private GestoreDestinazione destinazioneBean;
	
	private DestinazioneDTO destinazione;
	private int partecipanti;
	private AttivitaDTO attivita;
	
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

	public AttivitaDTO getAttivita() {
		return attivita;
	}

	public void setAttivita(AttivitaDTO attivita) {
		this.attivita = attivita;
	}

	/**
	 * Permette di aggiungere un'escursione ad una destinazione
	 * @param idPacchetto L'identificativo del pacchetto
	 * @param idDestinazione L'identificativo della destinazione nella quale aggiungere l'escursione
	 * @param escursione L'escursione scelta
	 */
	public String aggiuntaEscursione (int idPacchetto, int idDestinazione, int idEscursione) {
		try {
			destinazioneBean.aggiuntaEscursione(idDestinazione, idEscursione, this.getPartecipanti());
			return "dettagliPacchetto?idPacchetto=" + idPacchetto + "&faces-redirect=true";
		} catch (EscursioneInesistenteException e) {
			JsfUtil.errorMessage("Escursione inesistente!");
		} catch (DestinazioneInesistenteException e) {
			JsfUtil.errorMessage("Destinazione inesistente!");
		} catch (EntitaEsistenteException e) {
			JsfUtil.errorMessage("Escursione già inserita!");
		} catch (NumeroPartecipantiException e) {
			JsfUtil.errorMessage("Il numero di partecipanti all'escursione deve essere minore o uguale al numero di partecipanti al viaggio!");
		}
		return null;
	}
	
	public void abilitaModifica (AttivitaDTO attivita) {
		this.setAttivita(attivita);
	}
	
	public void disabilitaModifica () {
		this.setAttivita(null);
	}
	
	/**
	 * Permette la modifica del numero di partecipanti ad una escursione
	 * @param attivita L'attività di riferimento
	 */
	public void modificaEscursione (AttivitaDTO attivita) {
		try {
			destinazioneBean.modificaDatiEscursione(attivita);
			this.setAttivita(null);
		} catch (EscursioneInesistenteException e) {
			JsfUtil.errorMessage("Escursione inesistente!");
		} catch (DestinazioneInesistenteException e) {
			JsfUtil.errorMessage("Destinazione inesistente!");
		} catch (NumeroPartecipantiException e) {
			JsfUtil.errorMessage("Il numero di partecipanti all'escursione deve essere minore o uguale al numero di partecipanti al viaggio!");
		}
	}
	
	/**
	 * Permette la rimozione di un'escursione da una destinazione
	 * @param attivita L'attività da rimuovere
	 */
	public void eliminaEscursione (AttivitaDTO attivita) {
		try {
			destinazioneBean.eliminaEscursione(attivita);
			JsfUtil.infoMessage("Escursione eliminata!");
		} catch (EscursioneInesistenteException e) {
			JsfUtil.errorMessage("Escursione inesistente!");
		} catch (DestinazioneInesistenteException e) {
			JsfUtil.errorMessage("Destinazione inesistente!");
		}
	}
}
