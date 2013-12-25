package beans;

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
	private Date dataPartenza;
	private String cittaPartenza;
	private String cittaArrivo;
	
	@PostConstruct
	public void setUp () {
		collegamento = new CollegamentoDTO();
	}
	
	public CollegamentoDTO getCollegamento() {
		return collegamento;
	}

	public void setCollegamento(CollegamentoDTO collegamento) {
		this.collegamento = collegamento;
	}

	public Date getDataPartenza() {
		return dataPartenza;
	}

	public void setDataPartenza(Date dataPartenza) {
		this.dataPartenza = dataPartenza;
	}

	public String getCittaPartenza() {
		return cittaPartenza;
	}

	public void setCittaPartenza(String cittaPartenza) {
		this.cittaPartenza = cittaPartenza;
	}

	public String getCittaArrivo() {
		return cittaArrivo;
	}

	public void setCittaArrivo(String cittaArrivo) {
		this.cittaArrivo = cittaArrivo;
	}
	
	/**
	 * Ritorna l'elenco delle tipologie di collegamento
	 * @return Le tipologie di collegamento disponibili
	 */
	public TipoCollegamento [] getTipiCollegamento () {
		return TipoCollegamento.values();
	}

	public List<CollegamentoDTO> cercaCollegamenti () {
		//TODO: trovare modo furbo per ordinare le destinazioni e cercare i collegamenti tra le due città e nella data di riferimento ...
		return null;
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
	 * Permette la modifica dei dati di un collegamento esistente
	 * @param collegamento I dati del collegamento
	 */
	public void modificaCollegamento (CollegamentoDTO collegamento) {
		try {
			collegamentoBean.modificaDatiCollegamento(collegamento);
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
		} catch (CollegamentoInesistenteException e) {
			JsfUtil.errorMessage("Collegamento inesistente!");
		}
	}
}
