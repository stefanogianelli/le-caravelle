package beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityExistsException;

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
			FacesMessage messaggio = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Il collegamento è già presente nel database!", "Il collegamento è già presente nel database!");
			FacesContext.getCurrentInstance().addMessage(null, messaggio);
		} catch (CittaInesistenteException e) {
			FacesMessage messaggio = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Città sconosciuta!", "Città sconosciuta!");
			FacesContext.getCurrentInstance().addMessage(null, messaggio);
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
			FacesMessage messaggio = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Collegamento inesistente!", "Collegamento inesistente!");
			FacesContext.getCurrentInstance().addMessage(null, messaggio);
		} catch (CittaInesistenteException e) {
			FacesMessage messaggio = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Città sconosciuta!", "Città sconosciuta!");
			FacesContext.getCurrentInstance().addMessage(null, messaggio);
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
			FacesMessage messaggio = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Collegamento inesistente!", "Collegamento inesistente!");
			FacesContext.getCurrentInstance().addMessage(null, messaggio);
		}
	}
}
