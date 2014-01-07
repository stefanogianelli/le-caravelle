package beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import utils.DataUtils;
import utils.JsfUtil;
import dtos.CollegamentoDTO;
import dtos.DestinazioneDTO;
import dtos.HotelDTO;
import dtos.PacchettoDTO;
import dtos.PacchettoPredefinitoDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.CollegamentoInesistenteException;
import eccezioni.DestinazioneInesistenteException;
import eccezioni.EscursioneInesistenteException;
import eccezioni.HotelInesistenteException;
import eccezioni.InsertException;
import eccezioni.PacchettoInesistenteException;
import ejbs.GestoreCitta;
import ejbs.GestoreDestinazione;
import ejbs.GestorePacchetto;
import ejbs.GestorePacchettoPredefinito;
import ejbs.GestoreProfilo;
import enums.TipoPacchetto;

@ManagedBean(name="pacchetto")
@ViewScoped
public class PacchettoBean {

	@EJB
	private GestorePacchetto pacchettoBean;
	
	@EJB
	private GestorePacchettoPredefinito predefinitoBean;
	
	@EJB
	private GestoreDestinazione destinazioneBean;
	
	@EJB
	private GestoreCitta cittaBean;
	
	@EJB
	private GestoreProfilo profiloBean;
	
	private PacchettoDTO pacchetto;
	private DestinazioneDTO destinazione;
	private List<PacchettoDTO> elenco;
	private PacchettoPredefinitoDTO predefinito;
	private MapModel simpleModel;
	
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
	
	public MapModel getSimpleModel() {
		return simpleModel;
	}	
	
	/*
	 * Metodi relativi alla gestione del pacchetto
	 */
	
	/**
	 * Inizializza la mappa con i marker delle varie destinazioni visitate
	 */
	public void initMappa () {
		//genero i marker sulla mappa
		simpleModel = new DefaultMapModel();
		List<PacchettoDTO> pacchetti = pacchettoBean.elencoPacchetti(profiloBean.getUtenteCorrente().getEmail(), TipoPacchetto.ACQUISTATO);
		for (PacchettoDTO p : pacchetti) {
			if (p.getDestinazioni().get(p.getDestinazioni().size() - 1).getDataPartenza().before(DataUtils.getDataOdierna())) {
				for (DestinazioneDTO d : p.getDestinazioni()) {
					simpleModel.addOverlay(new Marker(new LatLng(d.getCitta().getLatitudine(), d.getCitta().getLongitudine()), d.getCitta().getNome()));
				}
			}
		}			
	}

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
	 * Ritorna il nome di un immagine casuale tra le destinazioni inserite nel pacchetto
	 * @param idPacchetto L'identificativo del pacchetto
	 * @return Il nome dell'immagine
	 */
	public String getImmagine (int idPacchetto) {
		try {
			PacchettoDTO pacchetto = pacchettoBean.getPacchetto(idPacchetto);
			int size = pacchetto.getDestinazioni().size();
			int index = 0 + (int)(Math.random() * size);
			int immSize = pacchetto.getDestinazioni().get(index).getCitta().getImmagini().size();
			if (immSize != 0) {
				int immIndex = 0 + (int)(Math.random() * immSize);
				return pacchetto.getDestinazioni().get(index).getCitta().getImmagini().get(immIndex);
			} else
				return "noImage.png";
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
		return null;
	}
	
	/**
	 * Ricerca tutti i pacchetti personalizzati posseduti dall'utente
	 */
	public void cercaPacchetti () {
		this.cercaPacchetti(TipoPacchetto.PERSONALIZZATO);
	}	
	
	/**
	 * Ricerca tutti i pacchetti posseduti da un utente dello stesso tipo
	 * @param tipo La tipologia di pacchetto
	 */
	public void cercaPacchetti (TipoPacchetto tipo) {
		this.getElenco().clear();
		this.getElenco().addAll(pacchettoBean.elencoPacchetti(profiloBean.getUtenteCorrente().getEmail(), tipo));
		if (this.getElenco().isEmpty())
			JsfUtil.infoMessage("Nessun risultato");
	}
	
	/**
	 * Restituisce tutti i pacchetti personalizzati, predefiniti e acquistati (in date future) posseduti dall'utente
	 * @return I pacchetti posseduti dall'utente
	 */
	public List<PacchettoDTO> elencoMieiPacchetti () {
		PacchettoDTO p;
		List<PacchettoDTO> pacchetti = pacchettoBean.elencoPacchetti(profiloBean.getUtenteCorrente().getEmail(), TipoPacchetto.ACQUISTATO);
		//elimino dal vettore i pacchetti acquistati con date di partenza nel passato
		for (Iterator<PacchettoDTO> itr = pacchetti.iterator(); itr.hasNext();) {
			p = itr.next();
			if (p.getDestinazioni().get(p.getDestinazioni().size() - 1).getDataPartenza().before(DataUtils.getDataOdierna()))
				itr.remove();
		}
		pacchetti.addAll(pacchettoBean.elencoPacchetti(profiloBean.getUtenteCorrente().getEmail(), TipoPacchetto.PREDEFINITO));
		pacchetti.addAll(pacchettoBean.elencoPacchetti(profiloBean.getUtenteCorrente().getEmail(), TipoPacchetto.PERSONALIZZATO));
		
		return pacchetti;
	}
	
	/**
	 * Restituisce i primi tre pacchetti personalizzati o predefiniti posseduti dall'utente
	 * @return I pacchetti posseduti dall'utente
	 */
	public List<PacchettoDTO> elencoTreMieiPacchetti () {
		List<PacchettoDTO> pacchetti = new ArrayList<PacchettoDTO>();
		int size = 0;
		for (PacchettoDTO p : this.elencoMieiPacchetti()) {
			if (size < 3) {
				pacchetti.add(p);
				size++;
			} else
				break;
		}
		return pacchetti;
	}
	
	/**
	 * Restituisce i primi tre pacchetti condivisi posseduti dall'utente
	 * @return I pacchetti posseduti dall'utente
	 */
	public List<PacchettoDTO> elencoTrePacchettiCondivisi () {
		return pacchettoBean.elencoTrePacchetti(profiloBean.getUtenteCorrente().getEmail(), TipoPacchetto.CONDIVISO);
	}
	
	/**
	 * Restituisce tutti i pacchetti condivisi posseduti dall'utente
	 * @return I pacchetti posseduti dall'utente
	 */
	public List<PacchettoDTO> elencoPacchettiCondivisi () {
		return pacchettoBean.elencoPacchetti(profiloBean.getUtenteCorrente().getEmail(), TipoPacchetto.CONDIVISO);
	}
	
	/**
	 * Restituisce i primi tre pacchetti acquistati posseduti dall'utente
	 * @return I pacchetti posseduti dall'utente
	 */
	public List<PacchettoDTO> elencoTrePacchettiAcquistati () {
		PacchettoDTO p;
		List<PacchettoDTO> pacchetti = pacchettoBean.elencoTrePacchetti(profiloBean.getUtenteCorrente().getEmail(), TipoPacchetto.ACQUISTATO);
		//elimino dal vettore i pacchetti acquistati con date di partenza nel futuro
		for (Iterator<PacchettoDTO> itr = pacchetti.iterator(); itr.hasNext();) {
			p = itr.next();
			if (p.getDestinazioni().get(p.getDestinazioni().size() - 1).getDataPartenza().after(DataUtils.getDataOdierna()))
				itr.remove();
		}
		return pacchetti;
	}
	
	/**
	 * Restituisce tutti i pacchetti acquistati posseduti dall'utente
	 * @return I pacchetti posseduti dall'utente
	 */
	public List<PacchettoDTO> elencoPacchettiAcquistati () {
		PacchettoDTO p;
		List<PacchettoDTO> pacchetti = pacchettoBean.elencoPacchetti(profiloBean.getUtenteCorrente().getEmail(), TipoPacchetto.ACQUISTATO);
		//elimino dal vettore i pacchetti acquistati con date di partenza nel futuro
		for (Iterator<PacchettoDTO> itr = pacchetti.iterator(); itr.hasNext();) {
			p = itr.next();
			if (p.getDestinazioni().get(p.getDestinazioni().size() - 1).getDataPartenza().after(DataUtils.getDataOdierna()))
				itr.remove();
		}
		return pacchetti;
	}		

	/**
	 * Permette la creazione di un nuovo pacchetto
	 * @param hotel L'hotel scelto da aggiungere alla destinazione di default
	 * @return L'indirizzo della pagina con i dettagli del pacchetto creato
	 */
	public String creaPacchetto (HotelDTO hotel) {
		try {	
			//impedisco all'utente di selezionare un hotel nella stessa città di partenza
			if (!hotel.getCitta().getNome().equalsIgnoreCase(getPacchetto().getCitta().getNome())) {
				//controllo che la data di arrivo sia minore della data di partenza dalla destinazione
				if (getDestinazione().getDataArrivo().before(getDestinazione().getDataPartenza())) {
					this.getPacchetto().setUtente(profiloBean.getUtenteCorrente());					
					this.getDestinazione().setHotel(hotel);
					this.getDestinazione().setCitta(hotel.getCitta());
					this.getPacchetto().getDestinazioni().add(this.getDestinazione());
					int id = pacchettoBean.creaPacchettoPersonalizzato(this.getPacchetto());
					return "dettagliPacchetto?idPacchetto=" + id + "&faces-redirect=true";
				} else {
					JsfUtil.errorMessage("La data di arrivo deve essere precedente alla data di partenza!");
				}
			} else {
				JsfUtil.errorMessage("La città di partenza e la destinazione non possono essere uguali!");
			}
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta!");
		} catch (HotelInesistenteException e) {
			JsfUtil.errorMessage("Hotel inesistente!");
		} catch (InsertException e) {
			JsfUtil.errorMessage("Il nome del pacchetto è già esistente nel database!");
		}
		return null;
	}
	
	/**
	 * Permette la modifica del nome del pacchetto
	 */
	public void modificaNomePacchetto () {
		try {
			pacchettoBean.modificaNomePacchetto(getPacchetto());
			JsfUtil.infoMessage("Nome modificato!");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		} catch (InsertException e) {
			JsfUtil.errorMessage("Nome già utilizzato!");
		}
	}
	
	/**
	 * Permette la modifica della città di partenza
	 */
	public void modificaCittaPartenza (String nomeNuovaCitta) {
		try {
			boolean check = true;
			//controllo che la nuova città non sia già stata inserita in una delle destinazioni
			for (DestinazioneDTO d : this.getPacchetto().getDestinazioni()) {
				if (d.getCitta().getNome().equalsIgnoreCase(nomeNuovaCitta)) {
					check = false;
					break;
				}
			}
			if (check) {
				this.getPacchetto().getCitta().setNome(nomeNuovaCitta);
				pacchettoBean.modificaCittaPartenzaPacchetto(getPacchetto());
			} else
				JsfUtil.errorMessage("Città inserita in una destinazione!");
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
			return "areaCliente?faces-redirect=true";
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
	 * @param idPacchetto L'identificativo del pacchetto predefinito
	 * @param cittaPartenza Il nome della città di partenza scelta
	 * @param dataArrivo La data di arrivo scelta
	 * @param durata La durata scelta
	 * @return L'indirizzo della pagina dettagli del pacchetto creato
	 */
	public String salvaPacchettoPredefinito (int idPacchetto,String cittaPartenza, String dataArrivo, int durata) {
		try {			
			PacchettoPredefinitoDTO pred = predefinitoBean.getPacchetto(idPacchetto);
			
			this.getPacchetto().setUtente(profiloBean.getUtenteCorrente());
			this.getPacchetto().setNome(pred.getNome());
			this.getPacchetto().setPacchettoPredefinito(pred);
			this.getPacchetto().setPrezzo(pred.getPrezzo());
			this.getPacchetto().setCitta(cittaBean.cercaCitta(cittaPartenza));
			this.getDestinazione().setDataArrivo(DataUtils.parseData(dataArrivo));
			this.getDestinazione().setDataPartenza(DataUtils.sommaGiorni(this.getDestinazione().getDataArrivo(), durata));
			this.getDestinazione().setHotel(pred.getHotel());
			this.getDestinazione().setCitta(this.getDestinazione().getHotel().getCitta());
			this.getPacchetto().getDestinazioni().add(this.getDestinazione());
			return "/utente/dettagliPacchetto?idPacchetto=" + pacchettoBean.salvaPacchettoPredefinito(this.getPacchetto()) + "&faces-redirect=true";
		} catch (InsertException e) {
			JsfUtil.errorMessage("Il pacchetto è già presente nel database!");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta!");
		} catch (HotelInesistenteException e) {
			JsfUtil.errorMessage("Hotel inesistente!");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
		return null;
	}
	
	/**
	 * Permette la modifica della durata
	 * @param durata La nuova durata
	 * @param destinazione La destinazione che si vuole modificare
	 */
	public void modificaDurata (int durata, DestinazioneDTO destinazione) {
		destinazione.setDataPartenza(DataUtils.sommaGiorni(destinazione.getDataArrivo(), durata));	
		try {
			pacchettoBean.modificaDataPartenza(getPacchetto(), destinazione);
			JsfUtil.infoMessage("Durata modificata!");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		} catch (DestinazioneInesistenteException e) {
			JsfUtil.errorMessage("Destinazione inesistente!");
		}	
	}
	
	/**
	 * Permette la modifica della data di partenza di un pacchetto predefinito
	 * @param dataArrivo La nuova data di arrivo
	 * @param destinazione La destinazione che si vuole modificare
	 */
	public void modificaDataPredefinito (String dataArrivo, DestinazioneDTO destinazione) {		
		try {
			//calcolo durata corrente
			int durata = (int)( (destinazione.getDataPartenza().getTime() - destinazione.getDataArrivo().getTime()) / (1000 * 60 * 60 * 24));	
			destinazione.setDataArrivo(DataUtils.parseData(dataArrivo));
			destinazione.setDataPartenza(DataUtils.sommaGiorni(destinazione.getDataArrivo(), durata));
			pacchettoBean.modificaDataArrivo(getPacchetto(), destinazione);
			pacchettoBean.modificaDataPartenza(getPacchetto(), destinazione);
			JsfUtil.infoMessage("Data modificata!");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		} catch (DestinazioneInesistenteException e) {
			JsfUtil.errorMessage("Destinazione inesistente!");
		}
	}
	
	/**
	 * Permette l'acquisto di un pacchetto
	 */
	public void acquistaPacchetto () {		
		int numeroDestinazioni = this.getPacchetto().getDestinazioni().size();
		int numeroCollegamenti = this.getPacchetto().getCollegamenti().size();
		
		try {	
			//controllo che il pacchetto sia completo
			if (numeroCollegamenti == numeroDestinazioni + 1) {
				//TODO: check dati utente e aggiunta dati dei passeggeri
				pacchettoBean.acquistaPacchetto(this.getPacchetto());
				JsfUtil.infoMessage("Pacchetto acquistato!");
			}
			else
				JsfUtil.errorMessage("Pacchetto incompleto!");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
	}
	
	/**
	 * Permette la condivisione di un pacchetto con un amico
	 * @param email L'email dell'amico con il quale condividere il pacchetto
	 * @param nome Il nome dell'amico
	 * @param cognome il cognome dell'amico
	 */
	public void condividiPacchetto (String email, String nome, String cognome) {
		int numeroDestinazioni = this.getPacchetto().getDestinazioni().size();
		int numeroCollegamenti = this.getPacchetto().getCollegamenti().size();
		
		//controllo che il pacchetto sia completo
		if (numeroCollegamenti == numeroDestinazioni + 1) {
			if(email != null && !email.isEmpty()) {
				if (nome != null && !nome.isEmpty()) {
					if (cognome != null && !cognome.isEmpty()) {
						try {
							pacchettoBean.condividiPacchetto(this.getPacchetto(), email, nome, cognome);
							JsfUtil.infoMessage("Pacchetto condiviso con " + nome);
						} catch (CittaInesistenteException e) {
							JsfUtil.errorMessage("Città sconosciuta!");
						} catch (HotelInesistenteException e) {
							JsfUtil.errorMessage("Hotel inesistente!");
						} catch (CollegamentoInesistenteException e) {
							JsfUtil.errorMessage("Collegamento inesistente!");
						} catch (EscursioneInesistenteException e) {
							JsfUtil.errorMessage("Escursione inesistente!");
						}
					} else
						JsfUtil.errorMessage("Inserire un cognome");
				} else
					JsfUtil.errorMessage("Inserire un nome");
			} else
				JsfUtil.errorMessage("Inserire un indirizzo email");
		} else
			JsfUtil.errorMessage("Pacchetto Incompleto! Impossibile condividere.");
	}
	
	/*
	 * Metodi relativi alla gestione delle destinazioni
	 */
	
	/**
	 * Verifica se la destinazione desiderata è la prima (in ordine di data di partenza)
	 * @param destinazione La destinazione da controllare
	 * @return true se la destinazione è la prima, false altrimenti
	 */
	public boolean isPrimaDestinazione (DestinazioneDTO destinazione) {
		if (this.getPacchetto().getDestinazioni().indexOf(destinazione) == 0) {
			return true;
		} else
			return false;		
	}
	
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
	 * @param id L'identificativo del pacchetto
	 * @param hotel L'hotel scelto
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
			JsfUtil.errorMessage(e.getMessage());
		}
		return null;
	}
	
	/**
	 * Permette la modifica della data di arrivo in una destinazione
	 * @param destinazione La destinazione da modificare
	 */
	public void modificaDataArrivo (DestinazioneDTO destinazione) {
		String success = "Data modificata!";
		try {
			//verifico se la destinazione è la prima
			if (this.isPrimaDestinazione(destinazione)) {
				//se è la prima allora controllo che la data di arrivo sia antecedente la data di partenza
				if (destinazione.getDataArrivo().before(destinazione.getDataPartenza())) {
					pacchettoBean.modificaDataArrivo(getPacchetto(), destinazione);
					JsfUtil.infoMessage(success);
				} else
					JsfUtil.errorMessage("La data di arrivo deve precedere la data di partenza!");
			} else {
				DestinazioneDTO precedente = this.getPacchetto().getDestinazioni().get(this.getPacchetto().getDestinazioni().indexOf(destinazione) - 1);
				//se non è la prima allora controllo che la nuova data di arrivo sia superiore alla data di arrivo della destinazione precedente
				if (destinazione.getDataArrivo().after(precedente.getDataArrivo())) {
					//modifico anche la data di partenza della destinazione precedente
					precedente.setDataPartenza(destinazione.getDataArrivo());
					pacchettoBean.modificaDataPartenza(getPacchetto(), precedente);
					pacchettoBean.modificaDataArrivo(getPacchetto(), destinazione);
					JsfUtil.infoMessage(success);
				} else
					JsfUtil.errorMessage("La data scelta è in conflitto con la destinazione precedente!");
			}
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		} catch (DestinazioneInesistenteException e) {
			JsfUtil.errorMessage("Destinazione inesistente!");
		}
	}
	
	/**
	 * Permette la modifica della data di partenza da una destinazione
	 * @param destinazione La destinazione da modificare
	 */
	public void modificaDataPartenza (DestinazioneDTO destinazione) {
		String success = "Data modificata!";
		try {
			//verifico se la destinazione è l'utlima
			if(this.isUltimaDestinazione(destinazione)) {
				//se è l'ultima verifico che la data di partenza sia successiva alla data di arrivo
				if (destinazione.getDataPartenza().after(destinazione.getDataArrivo())) {
					pacchettoBean.modificaDataPartenza(getPacchetto(), destinazione);
					JsfUtil.infoMessage(success);
				} else
					JsfUtil.errorMessage("La data di partenza deve essere successiva alla data di arrivo nella destinazione!");
			} else {
				//se non è l'ultima controllo che la nuova data di partenza sia minore della data di partenza della destinazione successiva
				DestinazioneDTO successiva = this.getPacchetto().getDestinazioni().get(this.getPacchetto().getDestinazioni().indexOf(destinazione) + 1);
				if (destinazione.getDataPartenza().before(successiva.getDataPartenza())) {
					//modifico anche la data di arrivo nella destinazione successiva
					successiva.setDataArrivo(destinazione.getDataPartenza());
					pacchettoBean.modificaDataArrivo(getPacchetto(), successiva);
					pacchettoBean.modificaDataPartenza(getPacchetto(), destinazione);
					JsfUtil.infoMessage(success);
				} else
					JsfUtil.errorMessage("La data scelta è in conflitto con la destinazione successiva!");
			}
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		} catch (DestinazioneInesistenteException e) {
			JsfUtil.errorMessage("Destinazione inesistente!");
		}	
	}
	
	/**
	 * Permette di modificare l'hotel inserito in una destinazione
	 * @param idPacchetto L'identificativo del pacchetto
	 * @param idDestinazione L'identificativo dell'hotel
	 * @param hotel Il nuovo hotel
	 * @return L'indirizzo della pagina dettagli
	 */
	public String modificaHotelDestinazione (int idPacchetto, int idDestinazione, HotelDTO hotel) {
		try {
			destinazioneBean.modificaHotel(idDestinazione, hotel);
			return "dettagliPacchetto?idPacchetto=" + idPacchetto + "&faces-redirect=true";
		} catch (HotelInesistenteException e) {
			JsfUtil.errorMessage("Hotel inesistente!");
		} catch (DestinazioneInesistenteException e) {
			JsfUtil.errorMessage("Destinazione inesistente!");
		} catch (InsertException e) {
			JsfUtil.errorMessage("L'hotel non si trova nella stessa città della destinazione!");
		}
		return null;
	}
	
	/**
	 * Permette di eliminare una destinazione da un pacchetto
	 * @param destinazione La destinazione da eliminare
	 */
	public void eliminaDestinazione (DestinazioneDTO destinazione) {
		try {
			//controllo che rimanga almeno una destinazione nel pacchetto
			if (this.getPacchetto().getDestinazioni().size() > 1) {	
				pacchettoBean.eliminaDestinazione(this.getPacchetto(), destinazione);
				JsfUtil.infoMessage("Destinazione rimossa!");
			} else
				JsfUtil.errorMessage("Impossibile eliminare la destinazione!");
		} catch (DestinazioneInesistenteException e) {
			JsfUtil.errorMessage("Destinazione inesistente!");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
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
	 * Restituisce il collegamento, se esistente, nella data desiderata
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
	 * Verificata se il pacchetto corrente è di tipo predefinito
	 * @return true se il pacchetto è predefinito, false altrimenti
	 */
	public boolean isTipoPredefinito () {
		if (this.getPacchetto().getTipoPacchetto() == TipoPacchetto.PREDEFINITO)
			return true;
		else
			return false;
	}

	/**
	 * Verificata se il pacchetto corrente è di tipo personalizzato
	 * @return true se il pacchetto è personalizzato, false altrimenti
	 */
	public boolean isTipoPersonalizzato () {
		if (this.getPacchetto().getTipoPacchetto() == TipoPacchetto.PERSONALIZZATO)
			return true;
		else
			return false;
	}
}
