package beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityExistsException;

import utils.JsfUtil;
import dtos.EscursioneDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.EscursioneInesistenteException;
import ejbs.GestoreEscursione;
import enums.CategoriaEscursione;

@ManagedBean(name="escursione")
@RequestScoped
public class EscursioneBean {
	
	@EJB
	private GestoreEscursione escursioneBean;
	
	private EscursioneDTO escursione;
	private List<EscursioneDTO> elenco;
	
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
	
	/**
	 * Fornisce l'elenco di categorie disponibili
	 * @return Le categorie di un'escursione
	 */
	public CategoriaEscursione [] getCategorie () {
		return CategoriaEscursione.values();
	}

	/**
	 * Ricerca le escursioni nella regione selezionata
	 * @param regione Il nome della regione
	 */
	public void cercaEscursioni (String regione) {
		this.getElenco().clear();
		this.getElenco().addAll(escursioneBean.elencoEscursioni(regione));
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
	 * Permette la modifica dei dati di una escursione
	 * @param escursione I dati dell'escursione
	 */
	public void modificaEscursione (EscursioneDTO escursione) {
		try {
			escursioneBean.modificaDatiEscursione(escursione);
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
		} catch (EscursioneInesistenteException e) {
			JsfUtil.errorMessage("Escursione inesistente!");
		}
	}

}
