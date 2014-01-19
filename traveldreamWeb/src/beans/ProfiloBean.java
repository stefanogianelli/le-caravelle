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
import eccezioni.UtenteInesistenteException;
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
	 * Restituisce il nome dell'utente corrente
	 * @return Il nome dell'utente (se presente) o l'indirizzo email
	 */
	public String getNome () {
		UtenteDTO utente = this.getUtente();
		return utente.getPersona().getNome() != null ? utente.getPersona().getNome() : utente.getEmail();
	}
	
	/**
	 * Permette la creazione di un nuovo utente
	 */
	public void registrazione () {
		try {
			profiloBean.registrazioneUtente(profilo.getEmail());
			JsfUtil.infoMessage("Registrazione effettuata con successo. Controlla la tua casella di posta, riceverai una mail con la password per accedere ai servizi.");
		} catch (MessagingException e) {
			JsfUtil.errorMessage("Errore nell'invio della email");
		} catch (EntitaEsistenteException e) {
			JsfUtil.errorMessage("email già usata");
		}
	}
	
	/**
	 * Permette il login nel sistema
	 * @return L'indirizzo della pagina personale nel caso dell'utente o l'indirizzo dell'homepage del dipendente
	 */
	public String login () {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
	    
	    try {
	    	request.login(this.getProfilo().getEmail(), this.getProfilo().getPassword());
	    	if (request.isUserInRole("UTENTE"))
	    		return "/utente/areaCliente?faces-redirect=true";
	    	else
	    		return "/dipendente/homepageDip?faces-redirect=true";
	    } catch (ServletException e) {
	    	JsfUtil.errorMessage("Login fallito");
	    }
	    return null;
	}
	
	/**
	 * Permette il logout
	 * @return L'indirizzo dell'homepage
	 */
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
	 * @return L'indirizzo della pagina di login
	 */
	public String resetPassword () {
		try {
			profiloBean.resetPassword(getProfilo().getEmail());
			JsfUtil.infoMessage("Richiesta accettata. Riceverai a breve una email all'indirizzo selezionato con la nuova password");
			return "login.xhtml?faces-redirect=true";
		} catch (MessagingException e) {
			JsfUtil.errorMessage("Errore nell'invio dell'email");
		} catch (UtenteInesistenteException e) {
			JsfUtil.errorMessage("Utente inesistente");
		}
		return null;
	}

}
