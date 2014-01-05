package beans;

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
import eccezioni.CittaInesistenteException;
import eccezioni.CollegamentoInesistenteException;
import eccezioni.PacchettoInesistenteException;
import ejbs.GestoreCitta;
import ejbs.GestoreCollegamento;
import ejbs.GestorePacchettoPredefinito;
import enums.TipoCollegamento;

@ManagedBean(name="collegamento")
@ViewScoped
public class CollegamentoBean {

	@EJB
	private GestoreCollegamento collegamentoBean;
	
	@EJB
	private GestoreCitta cittaBean;
	
	@EJB
	private GestorePacchettoPredefinito predefinitoBean;
	
	private CollegamentoDTO collegamento;
	private List<CollegamentoDTO> elenco;
	
	@PostConstruct
	public void setUp () {
		collegamento = new CollegamentoDTO();
		elenco = new ArrayList<CollegamentoDTO>();
	}
	
	public CollegamentoDTO getCollegamento() {
		return collegamento;
	}

	public void setCollegamento(CollegamentoDTO collegamento) {
		this.collegamento = collegamento;
	}

	public List<CollegamentoDTO> getElenco() {
		return elenco;
	}

	public void setElenco(List<CollegamentoDTO> elenco) {
		this.elenco = elenco;
	}

	/**
	 * Ritorna l'elenco delle tipologie di collegamento
	 * @return Le tipologie di collegamento disponibili
	 */
	public TipoCollegamento [] getTipiCollegamento () {
		return TipoCollegamento.values();
	}
	
	/**
	 * Permette la ricerca di un collegamento tramite il suo codice
	 * @param codiceCollegamento Il codice del collegamento
	 */
	public void getCollegamento (int codiceCollegamento) {
		try {
			this.setCollegamento(collegamentoBean.getCollegamento(codiceCollegamento));
		} catch (CollegamentoInesistenteException e) {
			JsfUtil.errorMessage("Collegamento inesistente!");
		}
	}
	
	/**
	 * Elenca tutti i collegamenti presenti nel database
	 * @return L'elenco dei collegamenti
	 */
	public List<CollegamentoDTO> elencoCollegamenti () {
		return this.collegamentoBean.elencoCollegamenti();
	}

	/**
	 * Ricerca i collegamenti disponibili tra due destinazioni nella data indicata
	 * @param dataPartenza La data di partenza
	 * @param cittaPartenza Il nome della città di partenza
	 * @param cittaArrivo Il nome della città di arrivo
	 * @param tipo La tipologia del collegamento
	 */
	public void cercaCollegamenti (Date dataPartenza, String cittaPartenza, String cittaArrivo, TipoCollegamento tipo) {
		System.out.println("data: " + dataPartenza + "\ncittà partenza: " + cittaPartenza + "\ncittaArrivo: " + cittaArrivo + "\ntipo: " + tipo.getLabel());
		this.getElenco().clear();
		this.getElenco().addAll(this.collegamentoBean.elencoCollegamenti(dataPartenza, cittaPartenza, cittaArrivo, tipo));
		if (this.getElenco().isEmpty())
			JsfUtil.infoMessage("Nessun risultato");
	}
	
	/**
	 * Ricerca i collegamenti inseriti nel pacchetto predefinito tra due destinazioni nella data indicata
	 * @param idPacchetto L'identificativo del pacchetto predefinito nel quale effettuare la ricerca
	 * @param dataPartenza La data di partenza
	 * @param cittaPartenza Il nome della città di partenza
	 * @param cittaArrivo Il nome della città di arrivo
	 * @param tipo La tipologia del collegamento
	 */
	public void cercaCollegamentiPred (int idPacchetto, Date dataPartenza, String cittaPartenza, String cittaArrivo, TipoCollegamento tipo) {
		try {
			List<CollegamentoDTO> col = predefinitoBean.getPacchetto(idPacchetto).getCollegamenti();
			this.getElenco().clear();
			for (CollegamentoDTO c : col) {
				if (c.getDataPartenza().equals(dataPartenza) && c.getCittaPartenza().getNome().equals(cittaPartenza) && c.getCittaArrivo().getNome().equals(cittaArrivo) && c.getTipoCollegamento() == tipo)
					this.getElenco().add(c);
			}
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
	}
	
	/**
	 * Permette la creazione di un nuovo collegamento
	 * @return L'indirizzo della pagina con i dettagli del collegamento creato
	 */
	public String creaCollegamento () {
		try {
			return "dettagliCollegamento?codiceCollegamento=" + collegamentoBean.creaCollegamento(this.getCollegamento()) + "&faces-redirect=true";
		} catch (EntityExistsException e) {
			JsfUtil.errorMessage("Il collegamento è già presente nel database!");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta!");
		}
		return null;
	}
	
	/**
	 * Permette la modifica dei dati di un collegamento esistente
	 * @return L'indirizzo della pagina con i dettagli del collegamento
	 */
	public String modificaCollegamento () {
		try {
			collegamentoBean.modificaDatiCollegamento(this.getCollegamento());
			return "dettagliCollegamento?codiceCollegamento=" + this.getCollegamento().getCodice() + "&faces-redirect=true";
		} catch (CollegamentoInesistenteException e) {
			JsfUtil.errorMessage("Collegamento inesistente!");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta!");
		}
		return null;
	}
	
	/**
	 * Permette l'eliminazione di un collegamento
	 * @param codiceCollegamento Il codice del collegamento da eliminare
	 * @return L'indirizzo della pagina con l'elenco dei collegamenti
	 */
	public String eliminaCollegamento (int codiceCollegamento) {
		try {
			collegamentoBean.eliminaCollegamento(codiceCollegamento);
			return "elencoCollegamenti?faces-redirect=true";
		} catch (CollegamentoInesistenteException e) {
			JsfUtil.errorMessage("Collegamento inesistente!");
		}
		return null;
	}	
}
