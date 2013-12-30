package beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.mail.MessagingException;

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
			JsfUtil.errorMessage("Errore!");
		} catch (EntitaEsistenteException e) {
			JsfUtil.errorMessage("email già usata!");
		}
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
			JsfUtil.errorMessage("Errore!");
		}
	}

}
