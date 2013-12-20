package beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityExistsException;

import dtos.EscursioneDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.EscursioneInesistenteException;
import ejbs.GestoreEscursione;

@ManagedBean(name="escursione")
@RequestScoped
public class EscursioneBean {
	
	@EJB
	private GestoreEscursione escursioneBean;
	
	private EscursioneDTO escursione;
	
	@PostConstruct
	public void setUp () {
		escursione = new EscursioneDTO();
	}
	
	public EscursioneDTO getEscursione() {
		return escursione;
	}

	public void setEscursione(EscursioneDTO escursione) {
		this.escursione = escursione;
	}

	/**
	 * Ricerca le escursioni nella regione selezionata
	 * @param regione Il nome della regione
	 * @return L'elenco delle escursioni trovate
	 */
	public List<EscursioneDTO> cercaEscursioni (String regione) {
		return escursioneBean.elencoEscursioni(regione);
	}
	
	/**
	 * Permette la creazione di una nuova escursione
	 */
	public void creaEscursione () {
		try {
			escursioneBean.creaEscursione(getEscursione());
		} catch (EntityExistsException e) {
			FacesMessage messaggio = new FacesMessage(FacesMessage.SEVERITY_ERROR, "L'escursione è già presente nel database!", "L'escursione è già presente nel database!");
			FacesContext.getCurrentInstance().addMessage(null, messaggio);
		} catch (CittaInesistenteException e) {
			FacesMessage messaggio = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Regione sconosciuta!", "Regione sconosciuta!");
			FacesContext.getCurrentInstance().addMessage(null, messaggio);
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
			FacesMessage messaggio = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Escursione inesistente!", "Escursione inesistente!");
			FacesContext.getCurrentInstance().addMessage(null, messaggio);
		} catch (CittaInesistenteException e) {
			FacesMessage messaggio = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Regione sconosciuta!", "Regione sconosciuta!");
			FacesContext.getCurrentInstance().addMessage(null, messaggio);
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
			FacesMessage messaggio = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Escursione inesistente!", "Escursione inesistente!");
			FacesContext.getCurrentInstance().addMessage(null, messaggio);
		}
	}

}
