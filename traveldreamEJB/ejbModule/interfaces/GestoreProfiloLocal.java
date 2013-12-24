package interfaces;

import javax.ejb.Local;

import dtos.UtenteDTO;
import entities.Utenti;

@Local
public interface GestoreProfiloLocal {

	/**
	 * Permette la conversione da un DTO alla rispettiva entità
	 * @param utente Il DTO dell'utente
	 * @return L'entità desiderata
	 */
	Utenti convertiInEntita (UtenteDTO utente);
	
	/**
	 * Permette la conversione da un'entità al rispettivo DTO
	 * @param utente L'entità di partenza
	 * @return Il relativo DTO
	 */
	UtenteDTO convertiInDTO (Utenti utente);
}
