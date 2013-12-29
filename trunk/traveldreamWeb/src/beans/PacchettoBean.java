package beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import dtos.PacchettoPredefinitoDTO;
import dtos.UtenteDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.CollegamentoInesistenteException;
import eccezioni.DeleteException;
import eccezioni.DestinazioneInesistenteException;
import eccezioni.HotelInesistenteException;
import eccezioni.InsertException;
import eccezioni.PacchettoInesistenteException;
import ejbs.GestorePacchetto;
import ejbs.GestorePacchettoPredefinito;
import enums.TipoPacchetto;

@ManagedBean(name="pacchetto")
@ViewScoped
public class PacchettoBean {

	@EJB
	private GestorePacchetto pacchettoBean;
	
	@EJB
	private GestorePacchettoPredefinito predefinitoBean;
	
	private PacchettoDTO pacchetto;
	private DestinazioneDTO destinazione;
	private List<PacchettoDTO> elenco;
	private PacchettoPredefinitoDTO predefinito;
	
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

	public PacchettoPredefinitoDTO getPredefinito() {
		return predefinito;
	}

	public void setPredefinito(PacchettoPredefinitoDTO predefinito) {
		this.predefinito = predefinito;
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
	 * Cerca il pacchetto corrispondente all'identificativo
	 * @param id L'identificativo del pacchetto
	 */
	public void getPacchetto (int id) {
		try {
			this.setPacchetto(pacchettoBean.getPacchetto(id));
			if (this.isTipoPredefinito())
				this.setPredefinito(this.getPacchetto().getPacchettoPredefinito());
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
	 * Permette la modifica del nome del pacchetto
	 */
	public void modificaNomePacchetto () {
		try {
			pacchettoBean.modificaNomePacchetto(getPacchetto());
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
	}
	
	/**
	 * Permette la modifica della città di partenza
	 */
	public void modificaCittaPartenza () {
		try {
			pacchettoBean.modificaCittaPartenzaPacchetto(getPacchetto());
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta!");
		}
	}
	
	/**
	 * Permette la modifica del numero di partecipanti
	 */
	public void modificaNumeroPartecipanti () {
		try {
			pacchettoBean.modificaNumeroPartecipanti(getPacchetto());
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
	 * Permette di convertire le informazione contenute nel pacchetto predefinito
	 * @param idPredefinito L'identificativo del pacchetto predefinito
	 */
	public void convertiPacchettoPredefinito (int idPredefinito) {
		try {
			this.setPredefinito(predefinitoBean.getPacchetto(idPredefinito));
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
	}
	
	/**
	 * Permette la creazione di un pacchetto a partire da un pacchetto predefinito
	 */
	public void salvaPacchettoPredefinito (int durata, String dataArrivo) {
		try {
			/*
			 * Utente usato per test
			 * Da sostituire con l'utente correntemente loggato nel sistema
			 */
			UtenteDTO utente = new UtenteDTO();
			utente.setEmail("stefano@gmail.com");
			this.getPacchetto().setUtente(utente);
			
			this.getPacchetto().setNome(this.getPredefinito().getNome());
			this.getPacchetto().setPacchettoPredefinito(this.getPredefinito());
			this.getPacchetto().setPrezzo(this.getPredefinito().getPrezzo());
			Date dataArr = new SimpleDateFormat("dd/MM/yyyy").parse(dataArrivo);
			this.getDestinazione().setDataArrivo(dataArr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dataArr);
			cal.add(Calendar.DATE, durata);
			this.getDestinazione().setDataPartenza(cal.getTime());
			this.getDestinazione().setHotel(this.getPredefinito().getHotel());
			this.getDestinazione().setCitta(this.getDestinazione().getHotel().getCitta());
			this.getPacchetto().getDestinazioni().add(this.getDestinazione());
			pacchettoBean.salvaPacchettoPredefinito(this.getPacchetto());
			JsfUtil.infoMessage("Pacchetto salvato!");
		} catch (EntityExistsException e) {
			JsfUtil.errorMessage("Il pacchetto è già presente nel database!");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta!");
		} catch (HotelInesistenteException e) {
			JsfUtil.errorMessage("Hotel inesistente!");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		} catch (ParseException e) {
			System.out.println("Errore nel convertire la data " + dataArrivo);
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
		} catch (InsertException e) {
			JsfUtil.errorMessage("Date non valide");
		}
		return null;
	}
	
	/**
	 * Permette di eliminare una destinazione da un pacchetto
	 * @param destinazione La destinazione da eliminare
	 */
	public void eliminaDestinazione (DestinazioneDTO destinazione) {
		try {
			pacchettoBean.eliminaDestinazione(this.getPacchetto(), destinazione);
			JsfUtil.infoMessage("Destinazione rimossa!");
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
	
	/**
	 * Verificata se il pacchetto corrente è di tipo predefinito
	 * @return true se il pacchetto è predefinito, false altrimenti
	 */
	public boolean isTipoPredefinito () {
		if (this.getPacchetto().getTipoPacchetto() == TipoPacchetto.PREDEFINITO)
			return true;
		else
			return false;
	}
}
