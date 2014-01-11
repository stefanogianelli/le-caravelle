package interfaces;

import javax.ejb.Local;

import dtos.UtenteDTO;
import entities.Utenti;

@Local
public interface GestoreProfiloLocal {
	
	/**
	 * Restituisce il profilo dell'utente corrente
	 * @return I dati dell'utente
	 */
	Utenti getUtente();	

	/**
	 * Permette la conversione da un DTO alla rispettiva entit�
	 * @param utente Il DTO dell'utente
	 * @return L'entit� desiderata
	 */
	Utenti convertiInEntita (UtenteDTO utente);
	
	/**
	 * Permette la conversione da un'entit� al rispettivo DTO
	 * @param utente L'entit� di partenza
	 * @return Il relativo DTO
	 */
	UtenteDTO convertiInDTO (Utenti utente);

}
