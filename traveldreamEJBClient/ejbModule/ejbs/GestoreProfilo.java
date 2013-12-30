package ejbs;

import javax.ejb.Local;
import javax.mail.MessagingException;

import dtos.UtenteDTO;
import eccezioni.EntitaEsistenteException;

@Local
public interface GestoreProfilo {
	
    /**
     * Restituisce i dati dell'utente corrente
     * @return Il DTO dell'utente corrente
     */
	UtenteDTO getUtenteCorrente ();

    /**
     * Permette la creazione di un profilo utente
     * @param datiUtente I dati dell'utente che si vuole registrare
     * @throws MessagingException 
     * @throws EntitaEsistenteException Quando esiste un utente con la stessa email
     */
	void registrazioneUtente (String email) throws MessagingException, EntitaEsistenteException;
	
	/**
	 * Permette l'aggiunta dei dati personali di un utente
	 * @param datiUtente I dati dell'utente
	 */
	void aggiuntaDatiPersonali (UtenteDTO datiUtente);
	
	/**
	 * Permette la modifica dei dati personali dell'utente
	 * @param datiUtente I dati dell'utente
	 */
	void modificaDatiPersonali (UtenteDTO datiUtente);
	
	/**
	 * Permette il reset della password
	 * @param datiUtente I dati dell'utente che ha richiesto il reset
	 * @throws MessagingException
	 */
	void resetPassword (UtenteDTO datiUtente) throws MessagingException;	
}
