package beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityExistsException;

import utils.JsfUtil;
import dtos.CollegamentoDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.CollegamentoInesistenteException;
import ejbs.GestoreCollegamento;

@ManagedBean(name="collegamento")
@RequestScoped
public class CollegamentoBean {

	@EJB
	private GestoreCollegamento collegamentoBean;
	
	private CollegamentoDTO collegamento;
	
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
