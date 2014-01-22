package interfaces;

import javax.ejb.Local;

import dtos.PersonaDTO;
import dtos.UtenteDTO;
import entities.Persone;
import entities.Utenti;

@Local
public interface GestoreProfiloLocal {
	
	/**
	 * Restituisce il profilo dell'utente corrente
	 * @return I dati dell'utente
	 */
	Utenti getUtente();	
	
	/**
	 * Permette di creare una nuova entità persona se la persona non esiste database, altrimenti ne modifica i dati
	 * @param persona I dati della persona
	 * @return L'entità creata o modificata
	 */
	Persone creaPersona (PersonaDTO persona);
	
	/**
	 * Permette la conversione da un'entità al rispettivo DTO
	 * @param utente L'entità di partenza
	 * @return Il relativo DTO
	 */
	UtenteDTO convertiInDTO (Utenti utente);
	
	/**
	 * Permette la conversione da un'entità al rispettivo DTO
	 * @param persona L'entità di partenza
	 * @return Il relativo DTO
	 */
	PersonaDTO convertiInDTO (Persone persona);

}
