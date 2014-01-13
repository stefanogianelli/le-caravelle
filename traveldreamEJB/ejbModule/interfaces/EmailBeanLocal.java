package interfaces;

import javax.ejb.Local;
import javax.mail.MessagingException;

@Local
public interface EmailBeanLocal {

    /**
     * Invia l'email di benvenuto al nuovo utente. L'email contiene i dati riassuntivi per eseguire l'accesso al sito
     * @param email L'indirizzo email a cui inviare il messaggio
     * @param password La password generata
     * @throws MessagingException Quando non è possibile inviare la mail
     */
	void inviaPassword (String email, String password) throws MessagingException;
	
    /**
     * Invia l'email di reset della password. L'email contiene solo la nuova password generata
     * @param email L'indirizzo email a cui inviare il messaggio
     * @param password La password generata
     * @throws MessagingException Quando non è possibile inviare la mail
     */
	void resetPassword (String email, String password) throws MessagingException;
	
	 /**
	  * Invia l'email a un amico con il link del pacchetto condiviso
	  * @param utente Il nome dell'utente che ha creato il pacchetto
	  * @param emailAmico L'indirizzo email dell'amico
	  * @param idPacchetto L'identificativo del pacchetto da condividere
	  * @throws MessagingException Quando non è possibile inviare la mail
	  */
	void condividiPacchetto (String utente, String emailAmico, int idPacchetto) throws MessagingException;
}
