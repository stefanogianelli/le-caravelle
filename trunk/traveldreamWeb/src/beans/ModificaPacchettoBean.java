package beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import utils.JsfUtil;
import dtos.DestinazioneDTO;
import dtos.HotelDTO;
import dtos.PacchettoDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.DestinazioneInesistenteException;
import eccezioni.HotelInesistenteException;
import eccezioni.PacchettoInesistenteException;
import ejbs.GestorePacchetto;
import enums.TipoPacchetto;

@ManagedBean(name="modificaPacchetto")
@SessionScoped
public class ModificaPacchettoBean {
	
	@EJB
	private GestorePacchetto pacchettoBean;

	private PacchettoDTO pacchetto;
	private DestinazioneDTO destinazione;
	private List<PacchettoDTO> elenco;
	private String email;
	
	public ModificaPacchettoBean () {
		pacchetto = new PacchettoDTO();
		destinazione = new DestinazioneDTO();
		elenco = new ArrayList<PacchettoDTO>();
	}

	public PacchettoDTO getPacchetto() {
		return pacchetto;
	}

	public void setPacchetto(PacchettoDTO pacchetto) {
		this.pacchetto = pacchetto;
	}

	public DestinazioneDTO getDestinazione() {
		return destinazione;
	}

	public void setDestinazione(DestinazioneDTO destinazione) {
		this.destinazione = destinazione;
	}

	public List<PacchettoDTO> getElenco() {
		return elenco;
	}

	public void setElenco(List<PacchettoDTO> elenco) {
		this.elenco = elenco;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Ricerca tutti i pacchetti personalizzati posseduti dall'utente
	 * @param email L'indirizzo email dell'utente
	 */
	public void cercaPacchetto () {
		this.getElenco().clear();
		this.getElenco().addAll(pacchettoBean.elencoPacchetti(this.getEmail(), TipoPacchetto.PERSONALIZZATO));
		if (this.getElenco().isEmpty())
			JsfUtil.infoMessage("Nessun risultato");
	}
	
	/**
	 * Permette la modifica di un pacchetto
	 * @param pacchetto Il pacchetto da modificare
	 */
	public void abilitaModifica (PacchettoDTO pacchetto) {
		pacchetto.setEditable(true);
	}
	
	/**
	 * Disabilita la modifica di un pacchetto
	 * @param pacchetto Il pacchetto del quale si vuole interrompere la modifica
	 */
	public void disabilitaModifica (PacchettoDTO pacchetto) {
		pacchetto.setEditable(false);
	}
	
	/**
	 * Permette il salvataggio delle modifiche fatte nel pacchetto
	 * @param pacchetto I dati del pacchetto
	 */
	public void salvaPacchetto (PacchettoDTO pacchetto) {
		try {
			pacchettoBean.salvaPacchetto(pacchetto);
			pacchetto.setEditable(false);
			JsfUtil.infoMessage("Pacchetto modificato correttamente!");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta!");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
	}
	
	/**
	 * Permette l'eliminazione di un pacchetto
	 * @param pacchetto Il pacchetto che si desidera eliminare
	 */
	public void eliminaPacchetto (PacchettoDTO pacchetto) {
		try {
			pacchettoBean.eliminaPacchetto(pacchetto);
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
	}
	
	/**
	 * Reindirizza alla pagina di aggiunta di una destinazione
	 * @param pacchetto Il pacchetto nel quale si vuole aggiungere la destinazione
	 * @return L'indirizzo della pagina di aggiunta nuova destinazione
	 */
	public String aggiuntaDestinazione (PacchettoDTO pacchetto) {
		this.setPacchetto(pacchetto);
		//TODO: sostiturire con configurazione in faces-config.xml
		return "aggiuntaDestinazioneTest?faces-redirect=true";
	}
	
	/**
	 * Aggiunge un hotel nella destinazione
	 * @param hotel L'hotel scelto
	 */
	public void sceltaHotel (HotelDTO hotel) {
		this.getDestinazione().setHotel(hotel);
		this.getDestinazione().setCitta(hotel.getCitta());
		JsfUtil.infoMessage("Aggiunto hotel " + hotel.getNome()); 
	}

	
	/**
	 * Salva la nuova destinazione nel database
	 */
	public void salvaDestinazione () {
		//TODO: configurare redirect corretti al termine dell'operazione
		try {
			pacchettoBean.aggiuntaDestinazione(this.getPacchetto(), this.getDestinazione());
			destinazione = new DestinazioneDTO();
			JsfUtil.infoMessage("Destinazione aggiunta!"); 
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta!");
		} catch (HotelInesistenteException e) {
			JsfUtil.errorMessage("Hotel inesistente!");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
	}
	
	/**
	 * Permette di eliminare una destinazione da un pacchetto
	 * @param destinazione La destinazione da eliminare
	 */
	public void eliminaDestinazione (PacchettoDTO pacchetto, DestinazioneDTO destinazione) {
		try {
			pacchettoBean.eliminaDestinazione(pacchetto, destinazione);
			JsfUtil.infoMessage("Destinazione eliminata!"); 
		} catch (DestinazioneInesistenteException e) {
			JsfUtil.errorMessage("Destinazione inesistente!");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
	}
}
