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
import ejbs.GestoreCitta;
import ejbs.GestoreCollegamento;
import enums.TipoCollegamento;

@ManagedBean(name="collegamento")
@ViewScoped
public class CollegamentoBean {

	@EJB
	private GestoreCollegamento collegamentoBean;
	
	@EJB
	private GestoreCitta cittaBean;
	
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
	 * Elenca tutti i collegamenti presenti nel database
	 */
	public void elencoCollegamenti () {
		if (this.getElenco().isEmpty())
			this.getElenco().addAll(this.collegamentoBean.elencoCollegamenti());
		if (this.getElenco().isEmpty())
			JsfUtil.infoMessage("Nessun risultato");
	}

	/**
	 * Ricerca i collegamenti disponibili tra due destinazioni nella data indicata
	 */
	public void cercaCollegamenti (Date dataPartenza, String cittaPartenza, String cittaArrivo, TipoCollegamento tipo) {
		this.getElenco().clear();
		this.getElenco().addAll(this.collegamentoBean.elencoCollegamenti(dataPartenza, cittaPartenza, cittaArrivo, tipo));
		if (this.getElenco().isEmpty())
			JsfUtil.infoMessage("Nessun risultato");
	}
	
	/**
	 * Permette la creazione di un nuovo collegamento
	 */
	public void creaCollegamento () {
		try {
			collegamentoBean.creaCollegamento(this.getCollegamento());
			JsfUtil.infoMessage("Collegamento aggiunto correttamente!");
		} catch (EntityExistsException e) {
			JsfUtil.errorMessage("Il collegamento è già presente nel database!");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta!");
		}
	}
	
	/**
	 * Abilita la modifica di un collegamento
	 * @param collegamento Il collegamento da modificare
	 */
	public void abilitaModifica (CollegamentoDTO collegamento) {
		collegamento.setEditable(true);
	}
	
	/**
	 * Disabilita la modifica di un collegamento
	 * @param collegamento Il collegamento del quale disabilitare la modifica
	 */
	public void disabilitaModifica (CollegamentoDTO collegamento) {
		collegamento.setEditable(false);
	}
	
	/**
	 * Permette la modifica dei dati di un collegamento esistente
	 * @param collegamento I dati del collegamento
	 */
	public void modificaCollegamento (CollegamentoDTO collegamento) {
		try {
			collegamentoBean.modificaDatiCollegamento(collegamento);
			collegamento.setEditable(false);
			JsfUtil.infoMessage("Collegamento modificato correttamente!");
		} catch (CollegamentoInesistenteException e) {
			JsfUtil.errorMessage("Collegamento inesistente!");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta!");
		}
	}
	
	/**
	 * Permette l'eliminazione di un collegamento
	 * @param collegamento Il collegamento da eliminare
	 */
	public void eliminaCollegamento (CollegamentoDTO collegamento) {
		try {
			collegamentoBean.eliminaCollegamento(collegamento);
			JsfUtil.infoMessage("Collegamento eliminato!");
		} catch (CollegamentoInesistenteException e) {
			JsfUtil.errorMessage("Collegamento inesistente!");
		}
	}
}
