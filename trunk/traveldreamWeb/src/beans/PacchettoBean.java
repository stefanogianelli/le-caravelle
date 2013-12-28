package beans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityExistsException;

import utils.JsfUtil;
import dtos.CollegamentoDTO;
import dtos.DestinazioneDTO;
import dtos.HotelDTO;
import dtos.PacchettoDTO;
import dtos.UtenteDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.CollegamentoInesistenteException;
import eccezioni.DataException;
import eccezioni.DeleteException;
import eccezioni.DestinazioneInesistenteException;
import eccezioni.HotelInesistenteException;
import eccezioni.PacchettoInesistenteException;
import ejbs.GestorePacchetto;
import enums.TipoPacchetto;

@ManagedBean(name="pacchetto")
@ViewScoped
public class PacchettoBean {

	@EJB
	private GestorePacchetto pacchettoBean;
	
	private PacchettoDTO pacchetto;
	private DestinazioneDTO destinazione;
	private List<PacchettoDTO> elenco;
	private boolean editable;
	
	@PostConstruct
	public void setUp () {
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

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
	/*
	 * Metodi relativi alla gestione del pacchetto
	 */

	/**
	 * Restituisce le tipologie di pacchetto disponibili
	 * @return Le tipologie di pacchetto
	 */
	public TipoPacchetto [] getTipoPacchetti () {
		return TipoPacchetto.values();
	}
	
	public void getPacchetto (int id) {
		try {
			this.setPacchetto(pacchettoBean.getPacchetto(id));
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
	}
	
	/**
	 * Ricerca tutti i pacchetti personalizzati posseduti dall'utente
	 * @param email L'indirizzo email dell'utente
	 */
	public void cercaPacchetto (String email) {
		this.cercaPacchetto(email, TipoPacchetto.PERSONALIZZATO);
	}	
	
	/**
	 * Ricerca tutti i pacchetti posseduti da un utente dello stesso tipo
	 * @param email L'indirizzo email dell'utente
	 * @param tipo La tipologia di pacchetto
	 */
	public void cercaPacchetto (String email, TipoPacchetto tipo) {
		this.getElenco().clear();
		this.getElenco().addAll(pacchettoBean.elencoPacchetti(email, tipo));
		if (this.getElenco().isEmpty())
			JsfUtil.infoMessage("Nessun risultato");
	}

	/**
	 * Permette la creazione di un nuovo pacchetto
	 * @param hotel L'hotel scelto da aggiungere alla destinazione di default
	 * @return L'indirizzo della pagina con i dettagli del pacchetto creato
	 */
	public String creaPacchetto (HotelDTO hotel) {
		try {	
			if (!hotel.getCitta().getNome().equalsIgnoreCase(getPacchetto().getCitta().getNome())) {
				if (getDestinazione().getDataArrivo().before(getDestinazione().getDataPartenza())) {
					/*
					 * Utente usato per test
					 * Da sostituire con l'utente correntemente loggato nel sistema
					 */
					UtenteDTO utente = new UtenteDTO();
					utente.setEmail("stefano@gmail.com");
					this.getPacchetto().setUtente(utente);
					
					this.getDestinazione().setHotel(hotel);
					this.getDestinazione().setCitta(hotel.getCitta());
					this.getPacchetto().getDestinazioni().add(this.getDestinazione());
					int id = pacchettoBean.creaPacchettoPersonalizzato(this.getPacchetto());
					return "dettagliPacchetto?idPacchetto=" + id + "&faces-redirect=true";
				} else {
					JsfUtil.errorMessage("La data di partenza deve essere precedente alla data di partenza!");
				}
			} else {
				JsfUtil.errorMessage("La città di partenza e la destinazione non possono essere uguali!");
			}
		} catch (EntityExistsException e) {
			JsfUtil.errorMessage("Il pacchetto è già presente nel database!");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta!");
		} catch (HotelInesistenteException e) {
			JsfUtil.errorMessage("Hotel inesistente!");
		}
		return null;
	}
	
	/**
	 * Permette la modifica di un pacchetto
	 */
	public void abilitaModifica () {
		this.setEditable(true);
	}
	
	/**
	 * Disabilita la modifica di un pacchetto
	 */
	public void disabilitaModifica () {
		this.setEditable(false);
	}
	
	/**
	 * Permette il salvataggio delle modifiche fatte nel pacchetto
	 */
	public void salvaPacchetto () {
		try {
			pacchettoBean.salvaPacchetto(this.getPacchetto());
			this.disabilitaModifica();
			JsfUtil.infoMessage("Pacchetto modificato correttamente!");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta!");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
	}
	
	/**
	 * Permette l'eliminazione di un pacchetto
	 * @return L'indirizzo della pagina dell'elenco pacchetti
	 */
	public String eliminaPacchetto () {
		try {
			pacchettoBean.eliminaPacchetto(this.getPacchetto());
			return "elencoPacchettiTest?faces-redirect=true";
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
		return null;
	}
	
	/**
	 * Permette la creazione di un pacchetto a partire da un pacchetto predefinito
	 */
	public void salvaPacchettoPredefinito () {
		try {
			pacchettoBean.salvaPacchettoPredefinito(this.getPacchetto());
		} catch (EntityExistsException e) {
			JsfUtil.errorMessage("Il pacchetto è già presente nel database!");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta!");
		} catch (HotelInesistenteException e) {
			JsfUtil.errorMessage("Hotel inesistente!");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
	}
	
	/**
	 * Permette l'acquisto di un pacchetto
	 */
	public void acquistaPacchetto () {
		try {
			pacchettoBean.acquistaPacchetto(this.getPacchetto());
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
	}
	
	/**
	 * Permette la condivisione di un pacchetto con un amico
	 * @param email L'email dell'amico con il quale condividere il pacchetto
	 */
	public void condividiPacchetto (String email) {
		try {
			pacchettoBean.condividiPacchetto(this.getPacchetto(), email);
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta!");
		} catch (HotelInesistenteException e) {
			JsfUtil.errorMessage("Hotel inesistente!");
		}
	}
	
	/*
	 * Metodi relativi alla gestione delle destinazioni
	 */
	
	/**
	 * Verifica se la destinazione desiderata è l'ultima (in ordine di data di partenza)
	 * @param destinazione La destinazione da controllare
	 * @return true se la destinazione è l'ultima, false altrimenti
	 */
	public boolean isUltimaDestinazione (DestinazioneDTO destinazione) {
		if (this.getPacchetto().getDestinazioni().indexOf(destinazione) == (this.getPacchetto().getDestinazioni().size() - 1)) {
			return true;
		} else
			return false;
	}
	
	/**
	 * Restituisce la città di partenza della destinazione preceedente, se esiste, altrimenti la città di partenza del pacchetto
	 * @param destinazione La destinazione desiderata
	 * @return La città di partenza richiesta
	 */
	public String getCittaPartenza (DestinazioneDTO destinazione) {
		if (this.getPacchetto().getDestinazioni().indexOf(destinazione) == 0)
			return this.getPacchetto().getCitta().getNome();
		else
			return this.getPacchetto().getDestinazioni().get(this.getPacchetto().getDestinazioni().indexOf(destinazione) - 1).getCitta().getNome();
	}
	
	/**
	 * Salva la nuova destinazione nel database
	 * @return L'indirizzo della pagina con i dettagli del pacchetto
	 */
	public String salvaDestinazione (int id, HotelDTO hotel) {
		try {
			this.getDestinazione().setHotel(hotel);
			this.getDestinazione().setCitta(hotel.getCitta());
			pacchettoBean.aggiuntaDestinazione(id, this.getDestinazione());
			return "dettagliPacchetto?idPacchetto=" + id + "&faces-redirect=true";
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta!");
		} catch (HotelInesistenteException e) {
			JsfUtil.errorMessage("Hotel inesistente!");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		} catch (DataException e) {
			JsfUtil.errorMessage("Date non valide");
		}
		return null;
	}
	
	/**
	 * Permette di eliminare una destinazione da un pacchetto
	 * @param destinazione La destinazione da eliminare
	 * @return L'indirizzo della pagina elenco pacchetti
	 */
	public String eliminaDestinazione (DestinazioneDTO destinazione) {
		try {
			pacchettoBean.eliminaDestinazione(this.getPacchetto(), destinazione);
			return "elencoPacchettiTest?faces-redirect=true"; 
		} catch (DestinazioneInesistenteException e) {
			JsfUtil.errorMessage("Destinazione inesistente!");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		} catch (DeleteException e) {
			JsfUtil.errorMessage("Impossibile eliminare la destinazione!");
		}
		return null;
	}
	
	/*
	 * Metodi relativi alla gestione dei collegamenti
	 */
	
	/**
	 * Controlla se nel pacchetto esiste un collegamento nella data desiderata
	 * @param data La data desiderata
	 * @return true se viene trovato il collegamento, false altrimenti
	 */
	public boolean esisteCollegamento (Date data) {
		for (CollegamentoDTO c : this.getPacchetto().getCollegamenti()) {
			if (data.compareTo(c.getDataPartenza()) == 0)
				return true;
		}
		return false;
	}
	
	/**
	 * Restituisce il collegamento, se esistente, nel pacchetto nella data desiderata
	 * @param data La data desiderata
	 * @return Il collegamento, se esiste, null altrimenti
	 */
	public CollegamentoDTO getCollegamento (Date data) {
		for (CollegamentoDTO c : this.getPacchetto().getCollegamenti()) {
			if (data.compareTo(c.getDataPartenza()) == 0)
				return c;
		}
		return null;
	}
	
	/**
	 * Permette di aggiungere un collegamento nel pacchetto
	 * @param id L'identificativo pacchetto nel quale aggiungere il collegamento
	 * @param collegamento Il collegamento scelto
	 * @return L'indirizzo della pagina con i dettagli del pacchetto
	 */
	public String aggiuntaCollegamento (int id, CollegamentoDTO collegamento) {
		try {
			pacchettoBean.aggiuntaCollegamento(id, collegamento);
			return "dettagliPacchetto?idPacchetto=" + id + "&faces-redirect=true";
		} catch (CollegamentoInesistenteException e) {
			JsfUtil.errorMessage("Collegamento inesistente!");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
		return null;
	}
	
	/**
	 * Si occupa di formattare la data in dd/MM/yyyy (es.: 28/12/2013)
	 * @param data La data da formattare
	 * @return La stringa del formato desiderato
	 */
	public String getData (Date data) {
		return new SimpleDateFormat("dd/MM/yyy").format(data);
	}
}
