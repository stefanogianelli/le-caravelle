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
	 */
	public void creaPacchetto (HotelDTO hotel) {
		try {			
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
			pacchettoBean.creaPacchettoPersonalizzato(this.getPacchetto());
			JsfUtil.infoMessage("Pacchetto creato correttamente!");
		} catch (EntityExistsException e) {
			JsfUtil.errorMessage("Il pacchetto è già presente nel database!");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta!");
		} catch (HotelInesistenteException e) {
			JsfUtil.errorMessage("Hotel inesistente!");
		}
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
			JsfUtil.infoMessage("Pacchetto eliminato!");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
	}
	
	/**
	 * Permette la creazione di un pacchetto a partire da un pacchetto predefinito
	 * @param pacchetto I dati del pacchetto
	 */
	public void salvaPacchettoPredefinito (PacchettoDTO pacchetto) {
		try {
			pacchettoBean.salvaPacchettoPredefinito(pacchetto);
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
	 * @param pacchetto Il pacchetto da acquistare
	 */
	public void acquistaPacchetto (PacchettoDTO pacchetto) {
		try {
			pacchettoBean.acquistaPacchetto(pacchetto);
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
	}
	
	/**
	 * Permette la condivisione di un pacchetto con un amico
	 * @param pacchetto Il pacchetto che si desidera condividere
	 * @param email L'email dell'amico con il quale condividere il pacchetto
	 */
	public void condividiPacchetto (PacchettoDTO pacchetto, String email) {
		try {
			pacchettoBean.condividiPacchetto(pacchetto, email);
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
	 * @param pacchetto Il pacchetto nel quale è contenuta la destinazione
	 * @param destinazione La destinazione da controllare
	 * @return true se la destinazione è l'ultima, false altrimenti
	 */
	public boolean isUltimaDestinazione (PacchettoDTO pacchetto, DestinazioneDTO destinazione) {
		if (pacchetto.getDestinazioni().indexOf(destinazione) == (pacchetto.getDestinazioni().size() - 1)) {
			return true;
		} else
			return false;
	}
	
	/**
	 * Restituisce la città di partenza della destinazione preceedente, se esiste, altrimenti la città di partenza del pacchetto
	 * @param pacchetto Il pacchetto nel quale è contenuta la destinazione
	 * @param destinazione La destinazione desiderata
	 * @return La città di partenza richiesta
	 */
	public String getCittaPartenza (PacchettoDTO pacchetto, DestinazioneDTO destinazione) {
		if (pacchetto.getDestinazioni().indexOf(destinazione) == 0)
			return pacchetto.getCitta().getNome();
		else
			return pacchetto.getDestinazioni().get(pacchetto.getDestinazioni().indexOf(destinazione) - 1).getCitta().getNome();
	}
	
	/**
	 * Salva la nuova destinazione nel database
	 */
	public void salvaDestinazione (int id, HotelDTO hotel) {
		try {
			this.getDestinazione().setHotel(hotel);
			this.getDestinazione().setCitta(hotel.getCitta());
			pacchettoBean.aggiuntaDestinazione(id, this.getDestinazione());
			JsfUtil.infoMessage("Destinazione aggiunta!"); 
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta!");
		} catch (HotelInesistenteException e) {
			JsfUtil.errorMessage("Hotel inesistente!");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		} catch (DataException e) {
			JsfUtil.errorMessage("Date non valide");
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
		} catch (DeleteException e) {
			JsfUtil.errorMessage("Impossibile eliminare la destinazione!");
		}
	}
	
	/*
	 * Metodi relativi alla gestione dei collegamenti
	 */
	
	/**
	 * Controlla se nel pacchetto esiste un collegamento nella data desiderata
	 * @param pacchetto Il pacchetto nel quale cercare
	 * @param data La data desiderata
	 * @return true se viene trovato il collegamento, false altrimenti
	 */
	public boolean esisteCollegamento (PacchettoDTO pacchetto, Date data) {
		for (CollegamentoDTO c : pacchetto.getCollegamenti()) {
			if (data.compareTo(c.getDataPartenza()) == 0)
				return true;
		}
		return false;
	}
	
	/**
	 * Restituisce il collegamento, se esistente, nel pacchetto nella data desiderata
	 * @param pacchetto Il pacchetto di riferimento
	 * @param data La data desiderata
	 * @return Il collegamento, se esiste, null altrimenti
	 */
	public CollegamentoDTO getCollegamento (PacchettoDTO pacchetto, Date data) {
		for (CollegamentoDTO c : pacchetto.getCollegamenti()) {
			if (data.compareTo(c.getDataPartenza()) == 0)
				return c;
		}
		return null;
	}
	
	/**
	 * Permette di aggiungere un collegamento nel pacchetto
	 * @param id L'identificativo pacchetto nel quale aggiungere il collegamento
	 * @param collegamento Il collegamento scelto
	 */
	public void aggiuntaCollegamento (int id, CollegamentoDTO collegamento) {
		try {
			pacchettoBean.aggiuntaCollegamento(id, collegamento);
			JsfUtil.infoMessage("Collegamento aggiunto!");
		} catch (CollegamentoInesistenteException e) {
			JsfUtil.errorMessage("Collegamento inesistente!");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
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
