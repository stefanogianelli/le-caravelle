package beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityExistsException;

import utils.JsfUtil;
import dtos.EscursioneDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.EscursioneInesistenteException;
import ejbs.GestoreEscursione;
import enums.CategoriaEscursione;

@ManagedBean(name="escursione")
@ViewScoped
public class EscursioneBean {
	
	@EJB
	private GestoreEscursione escursioneBean;
	
	private EscursioneDTO escursione;
	private List<EscursioneDTO> elenco;
	private String regione;
	
	@PostConstruct
	public void setUp () {
		escursione = new EscursioneDTO();
		elenco = new ArrayList<EscursioneDTO>();
	}
	
	public EscursioneDTO getEscursione() {
		return escursione;
	}

	public void setEscursione(EscursioneDTO escursione) {
		this.escursione = escursione;
	}

	public List<EscursioneDTO> getElenco() {
		return elenco;
	}

	public void setElenco(List<EscursioneDTO> elenco) {
		this.elenco = elenco;
	}
	
	public String getRegione() {
		return regione;
	}

	public void setRegione(String regione) {
		this.regione = regione;
	}

	/**
	 * Fornisce l'elenco di categorie disponibili
	 * @return Le categorie di un'escursione
	 */
	public CategoriaEscursione [] getCategorie () {
		return CategoriaEscursione.values();
	}
	
	/**
	 * Elenca tutte le escursioni presenti nel database
	 */
	public void elencoEscursioni () {
		if (this.getElenco().isEmpty())
			this.getElenco().addAll(escursioneBean.elencoEscursioni());
		if (this.getElenco().isEmpty())
			JsfUtil.infoMessage("Nessun risultato");
	}

	/**
	 * Ricerca le escursioni nella regione selezionata
	 */
	public void cercaEscursioni () {
		this.getElenco().clear();
		this.getElenco().addAll(escursioneBean.elencoEscursioni(this.getRegione()));
		if (this.getElenco().isEmpty())
			JsfUtil.infoMessage("Nessun risultato");
	}
	
	/**
	 * Permette la creazione di una nuova escursione
	 */
	public void creaEscursione () {
		try {
			escursioneBean.creaEscursione(this.getEscursione());
			JsfUtil.infoMessage("Escursione creata con successo!");
		} catch (EntityExistsException e) {
			JsfUtil.errorMessage("L'escursione è già presente nel database!");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Regione sconosciuta!");
		}
	}
	
	/**
	 * Abilita la modifica di una escursione
	 * @param escursione L'escursione da modificare
	 */
	public void abilitaModifica (EscursioneDTO escursione) {
		escursione.setEditable(true);
	}
	
	/**
	 * Disabilita la modifica di un'escursione
	 * @param escursione L'escursione della quale si vuole interrompere la modifica
	 */
	public void disabilitaModifica (EscursioneDTO escursione) {
		escursione.setEditable(false);
	}
	
	/**
	 * Permette la modifica dei dati di una escursione
	 * @param escursione I dati dell'escursione
	 */
	public void modificaEscursione (EscursioneDTO escursione) {
		try {
			escursioneBean.modificaDatiEscursione(escursione);
			escursione.setEditable(false);
			JsfUtil.infoMessage("Collegamento modificato correttamente!");
		} catch (EscursioneInesistenteException e) {
			JsfUtil.errorMessage("Escursione inesistente!");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Regione sconosciuta!");
		}
	}
	
	/**
	 * Permette l'eliminazione di un'escursione
	 * @param escursione L'escursione da eliminare
	 */
	public void eliminaEscursione (EscursioneDTO escursione) {
		try {
			escursioneBean.eliminaEscursione(escursione);
			JsfUtil.infoMessage("Escursione eliminata!");
		} catch (EscursioneInesistenteException e) {
			JsfUtil.errorMessage("Escursione inesistente!");
		}
	}

}
