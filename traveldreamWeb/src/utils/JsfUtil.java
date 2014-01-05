package utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class JsfUtil {

	/**
	 * Invia un messaggio d'errore globale
	 * @param msg Il messsaggio d'errore
	 */
	public static void errorMessage (String msg) {
		FacesMessage messaggio = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
		FacesContext.getCurrentInstance().addMessage(null, messaggio);
	}
	
	/**
	 * Invia un messaggio d'errore ad un componente specifico
	 * @param msg Il messaggio d'errore
	 * @param id L'identificativo del componente
	 */
	public static void errorMessage (String msg, String id) {
		FacesMessage messaggio = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
		FacesContext.getCurrentInstance().addMessage(id, messaggio);
	}
	
	/**
	 * Invia un messaggio informativo globale
	 * @param msg Il messaggio d'errore
	 */
	public static void infoMessage (String msg) {
		FacesMessage messaggio = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
		FacesContext.getCurrentInstance().addMessage(null, messaggio);
	}
	
	/**
	 * Invia un messaggio informativo ad un componente specifico
	 * @param msg Il messaggio d'errore
	 * @param id L'identificativo del componente
	 */
	public static void infoMessage (String msg, String id) {
		FacesMessage messaggio = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
		FacesContext.getCurrentInstance().addMessage(id, messaggio);
	}
}
