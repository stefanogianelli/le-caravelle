package remote;

import javax.ejb.Local;
import javax.mail.MessagingException;

@Local
public interface EmailBeanLocal {

    /**
     * Invia l'email di benvenuto al nuovo utente. L'email contiene i dati riassuntivi per eseguire l'accesso al sito
     * @param email L'indirizzo email a cui inviare il messaggio
     * @param password La password generata
     * @throws MessagingException
     */
	void inviaPassword (String email, String password) throws MessagingException;
	
    /**
     * Invia l'email di reset della password. L'email contiene solo la nuova password generata
     * @param email L'indirizzo email a cui inviare il messaggio
     * @param password La password generata
     * @throws MessagingException
     */
	void resetPassword (String email, String password) throws MessagingException;
}
