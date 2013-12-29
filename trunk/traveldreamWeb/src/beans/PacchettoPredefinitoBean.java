package beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import utils.JsfUtil;
import dtos.CittaDTO;
import dtos.CollegamentoDTO;
import dtos.HotelDTO;
import dtos.PacchettoPredefinitoDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.CollegamentoInesistenteException;
import eccezioni.DeleteException;
import eccezioni.HotelInesistenteException;
import eccezioni.InsertException;
import eccezioni.PacchettoInesistenteException;
import ejbs.GestorePacchettoPredefinito;

@ManagedBean(name="pacchettoPredefinito")
@ViewScoped
public class PacchettoPredefinitoBean {
	
	@EJB
	private GestorePacchettoPredefinito pacchettoBean;

	private PacchettoPredefinitoDTO pacchetto;
	private List<PacchettoPredefinitoDTO> elenco;
	
	@PostConstruct
	public void setUp () {
		pacchetto = new PacchettoPredefinitoDTO();
		elenco = new ArrayList<PacchettoPredefinitoDTO>();
	}

	public PacchettoPredefinitoDTO getPacchetto() {
		return pacchetto;
	}

	public void setPacchetto(PacchettoPredefinitoDTO pacchetto) {
		this.pacchetto = pacchetto;
	}
	
	public List<PacchettoPredefinitoDTO> getElenco() {
		return elenco;
	}

	public void setElenco(List<PacchettoPredefinitoDTO> elenco) {
		this.elenco = elenco;
	}
	
	/**
	 * Mostra tutti i pacchetti predefiniti presente nel database
	 */
	public void elencoPacchetti () {
		if (this.getElenco().isEmpty())
			this.setElenco(pacchettoBean.elencoPacchetti());
	}
	
	/**
	 * Cerca il pacchetto corrispondente all'identificativo
	 * @param id L'identificativo del pacchetto
	 */
	public void getPacchetto (int id) {
		try {
			this.setPacchetto(pacchettoBean.getPacchetto(id));
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
	}

	/**
	 * Aggiunge una data al pacchetto
	 * @param data La data di partenza
	 */
	public void aggiuntaData (Date data) {
		this.getPacchetto().getDatePartenza().add(data);
	}
	
	/**
	 * Aggiunge una durata al pacchetto
	 * @param durata La durata
	 */
	public void aggiuntaDurata (int durata) {
		this.getPacchetto().getDurate().add(durata);
	}
	
	public void aggiuntaCitta (String nome) {
		CittaDTO citta = new CittaDTO();
		citta.setNome(nome);
		this.getPacchetto().getCittaPartenza().add(citta);
	}
	
	/**
	 * Crea un nuovo pacchetto predefinito
	 * @param hotel L'hotel da aggiungere al pacchetto
	 */
	public void creaPacchetto (HotelDTO hotel) {
		try {
			this.getPacchetto().setHotel(hotel);
			pacchettoBean.creaPacchetto(getPacchetto());
			JsfUtil.infoMessage("Pacchetto creato!");
		} catch (HotelInesistenteException e) {
			JsfUtil.errorMessage("Hotel inesistente!");
		} catch (CittaInesistenteException w) {
			JsfUtil.errorMessage("Citt� inesistente!");
		}
	}
	
	/**
	 * Permette di eliminare un pacchetto
	 * @return L'indirizzo della pagine con l'elenco dei pacchetti
	 */
	public String eliminaPacchetto () {
		try {
			pacchettoBean.eliminaPacchetto(getPacchetto());
			return "elencoPacchettiPredefiniti?faces-redirect=true";
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
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
	 * Permette la modifica del prezzo del pacchetto
	 */
	public void modificaPrezzoPacchetto () {
		try {
			pacchettoBean.modificaPrezzo(getPacchetto());
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
	}
	
	/**
	 * Permette di modificare l'hotel inserito nel pacchetto
	 * @param idPacchetto L'identificativo del pacchetto
	 * @param hotel L'hotel da inserire
	 * @return L'indirizzo della pagina dettagli del pacchetto
	 */
	public String modificaHotel (int idPacchetto, HotelDTO hotel) {
		try {
			pacchettoBean.modificaHotel(idPacchetto, hotel);
			return "dettagliPacchettoPredefinito?idPacchetto=" + idPacchetto + "&faces-redirect=true";
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		} catch (HotelInesistenteException e) {
			JsfUtil.errorMessage("Hotel inesistente!");
		}
		return null;
	}
	
	/**
	 * Permette di aggiungere una citt� di partenza nel pacchetto
	 * @param nome Il nome della citt�
	 */
	public void salvaCitta (String nome) {
		try {
			pacchettoBean.aggiuntaCittaPartenza(getPacchetto(), nome);
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Citt� inesistente!");
		} catch (InsertException e) {
			JsfUtil.errorMessage("Citt� gi� inserita!");
		}
	}
	
	public void rimuoviCitta (CittaDTO citta) {
		try {
			pacchettoBean.rimuoviCittaPartenza(getPacchetto(), citta);
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Citt� inesistente!");
		} catch (DeleteException e) {
			JsfUtil.errorMessage("Impossibile eliminare la citt�!");
		}
	}
	
	/**
	 * Permette di aggiungere una data di partenza nel pacchetto
	 * @param data
	 */
	public void salvaData (Date data) {
		try {
			pacchettoBean.aggiuntaDataPartenza(getPacchetto(), data);
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		} catch (InsertException e) {
			JsfUtil.errorMessage("Data gi� presente!");
		}
	}
	
	/**
	 * Permette di rimuovere una data di partenza dal pacchetto
	 * @param data La data da rimuovere
	 */
	public void rimuoviDataPartenza (Date data) {
		try {
			pacchettoBean.rimuoviDataPartenza(getPacchetto(), data);
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		} catch (DeleteException e) {
			JsfUtil.errorMessage("Impossibile rimuovere la data!");
		}
	}
	
	/**
	 * Permette di aggiungere una durata nel pacchetto
	 * @param durata La durata da aggiungere
	 */
	public void salvaDurata (int durata) {
		try {
			pacchettoBean.aggiuntaDurata(getPacchetto(), durata);
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		} catch (InsertException e) {
			JsfUtil.errorMessage("Durata gi� esistente!");
		}
	}
	
	/**
	 * Permette di rimuovere una durata dal pacchetto
	 * @param durata La durata da rimuovere
	 */
	public void rimuoviDurata (int durata) {
		try {
			pacchettoBean.rimuoviDurata(getPacchetto(), durata);
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		} catch (DeleteException e) {
			JsfUtil.errorMessage("Impossibile rimuovere la durata!");
		}
	}
	
	/**
	 * Permette di aggiungere un collegamento nel pacchetto
	 * @param idPacchetto L'identificativo del pacchetto
	 * @param collegamento Il collegamento
	 * @return L'indirizzo della pagina di dettaglio del pacchetto
	 */
	public String aggiuntaCollegamento (int idPacchetto, CollegamentoDTO collegamento) {
		try {
			pacchettoBean.aggiuntaCollegamento(idPacchetto, collegamento);
			return "dettagliPacchettoPredefinito?idPacchetto=" + idPacchetto + "&faces-redirect=true";
		} catch (CollegamentoInesistenteException e) {
			JsfUtil.errorMessage("Collegamento inesistente!");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		} catch (InsertException e) {
			JsfUtil.errorMessage("Collegamento gi� esistente!");
		}
		return null;
	}
	
	/**
	 * Permette di rimuovere un collegamento dal pacchetto
	 * @param collegamento Il collegamento che si vuole rimuovere
	 */
	public void rimuoviCollegamento (CollegamentoDTO collegamento) {
		try {
			pacchettoBean.rimuoviCollegamento(getPacchetto(), collegamento);
			JsfUtil.infoMessage("Collegamento rimosso!");
		} catch (CollegamentoInesistenteException e) {
			JsfUtil.errorMessage("Collegamento inesistente!");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
	}
}