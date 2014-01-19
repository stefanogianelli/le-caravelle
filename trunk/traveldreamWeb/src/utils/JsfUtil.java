package utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class JsfUtil {

	/**
	 * Invia un messaggio d'errore globale
	 * @param msg Il messsaggio d'errore
	 */
	public static void errorMessage (String msg) {
		FacesContext context = FacesContext.getCurrentInstance();
		
		FacesMessage messaggio = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
		context.addMessage(null, messaggio);
		context.getExternalContext().getFlash().setKeepMessages(true);
	}
	
	/**
	 * Invia un messaggio d'errore ad un componente specifico
	 * @param msg Il messaggio d'errore
	 * @param id L'identificativo del componente
	 */
	public static void errorMessage (String msg, String id) {
		FacesContext context = FacesContext.getCurrentInstance();
		
		FacesMessage messaggio = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
		context.addMessage(id, messaggio);
		context.getExternalContext().getFlash().setKeepMessages(true);
	}
	
	/**
	 * Invia un messaggio informativo globale
	 * @param msg Il messaggio d'errore
	 */
	public static void infoMessage (String msg) {
		FacesContext context = FacesContext.getCurrentInstance();
		
		FacesMessage messaggio = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
		context.addMessage(null, messaggio);
		context.getExternalContext().getFlash().setKeepMessages(true);
	}
	
	/**
	 * Invia un messaggio informativo ad un componente specifico
	 * @param msg Il messaggio d'errore
	 * @param id L'identificativo del componente
	 */
	public static void infoMessage (String msg, String id) {
		FacesContext context = FacesContext.getCurrentInstance();
		
		FacesMessage messaggio = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
		context.addMessage(id, messaggio);
		context.getExternalContext().getFlash().setKeepMessages(true);
	}
}
