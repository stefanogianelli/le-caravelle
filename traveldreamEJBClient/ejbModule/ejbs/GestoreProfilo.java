package ejbs;

import javax.ejb.Local;
import javax.mail.MessagingException;

import dtos.UtenteDTO;
import eccezioni.EntitaEsistenteException;
import eccezioni.InsertException;
import eccezioni.UtenteInesistenteException;

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
	 * Permette la modifica della password
	 * @param vecchiaPassword La password corrente
	 * @param nuovaPassword La nuova password
	 * @throws InsertException Quando la vecchia password inserita dall'utente non combacia con quella presente nel database
	 */
	void modificaPassword (String vecchiaPassword, String nuovaPassword) throws InsertException;
	
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
	 * @param email L'indirizzo email dell'utente
	 * @throws MessagingException Quando si verifica un errore nell'invio del messaggio
	 * @throws UtenteInesistenteException Quando l'utente non viene trovato nel database
	 */
	void resetPassword (String email) throws MessagingException, UtenteInesistenteException;
	
	/**
	 * Permette l'iscrizione alla newsletter
	 * @param email L'indirizzo email
	 * @throws MessagingException Quando si verifica un errore nell'invio del messaggio
	 */
	void iscrizioneNewsletter (String email) throws MessagingException;
}
