package beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import utils.JsfUtil;
import dtos.UtenteDTO;
import eccezioni.EntitaEsistenteException;
import ejbs.GestoreProfilo;

@ManagedBean(name="profilo")
@RequestScoped
public class ProfiloBean {
	
	@EJB
	private GestoreProfilo profiloBean;
	
	private UtenteDTO profilo;
	
	@PostConstruct
	public void setUp () {
		profilo = new UtenteDTO();
	}

	public UtenteDTO getProfilo() {
		return profilo;
	}

	public void setProfilo(UtenteDTO profilo) {
		this.profilo = profilo;
	}
	
	/**
	 * Restituisce il profilo dell'utente che ne fa richiesta
	 * @return Il DTO dell'utente corrente
	 */
	public UtenteDTO getUtente () {
		return profiloBean.getUtenteCorrente();
	}
	
	/**
	 * Permette la creazione di un nuovo utente
	 */
	public void registrazione () {
		try {
			profiloBean.registrazioneUtente(profilo.getEmail());
		} catch (MessagingException e) {
			JsfUtil.errorMessage("Errore");
		} catch (EntitaEsistenteException e) {
			JsfUtil.errorMessage("email già usata");
		}
	}
	
	public String login () {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
	    
	    try {
	    	request.login(this.getProfilo().getEmail(), this.getProfilo().getPassword());
	    } catch (ServletException e) {
	    	JsfUtil.errorMessage("Login fallito");
	    	return null;
	    }
	    return "/utente/areaPersonale.xhtml?faces-redirect=true";
	}
	
	public String logout () {
	    FacesContext context = FacesContext.getCurrentInstance();
	    HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
	    
	    try {
	    	request.logout();
	    } catch (ServletException e) {
	      	JsfUtil.errorMessage("Logout fallito");
	      	return null;
	    }
	    return "/homepage.xhtml?faces-redirect=true";
    }
	
	/**
	 * Consente l'aggiunta dei dati personali di un utente
	 * @param datiUtente I dati dell'utente
	 */
	public void aggiuntaDatiPersonali (UtenteDTO datiUtente) {
		profiloBean.aggiuntaDatiPersonali(datiUtente);
	}
	
	/**
	 * Consente la modifica dei dati personali di un utente
	 * @param datiUtente I dati dell'utente
	 */
	public void modificaDatiPersonali (UtenteDTO datiUtente) {
		profiloBean.modificaDatiPersonali(datiUtente);
	}
	
	/**
	 * Permette il reset della passwrod
	 * @param datiUtente I dati dell'utente
	 */
	public void resetPassword (UtenteDTO datiUtente) {
		try {
			profiloBean.resetPassword(datiUtente);
		} catch (MessagingException e) {
			JsfUtil.errorMessage("Errore");
		}
	}

}
