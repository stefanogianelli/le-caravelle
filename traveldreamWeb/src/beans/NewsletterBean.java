package beans;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.mail.MessagingException;
import javax.validation.constraints.Pattern;

import utils.JsfUtil;
import ejbs.GestoreProfilo;

@ManagedBean(name="newsletter")
@ViewScoped
public class NewsletterBean {
	
	@EJB
	private GestoreProfilo profilo;

	@Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
            message="email non valida")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Permette l'iscrizione alla newsletter
	 */
	public void iscriviti () {
		try {
			profilo.iscrizioneNewsletter(getEmail());
			JsfUtil.infoMessage("Grazie per esserti iscritto alla nostra newsletter");
		} catch (MessagingException e) {
			JsfUtil.errorMessage("Errore nell'invio dell'email");
		}
	}
}
