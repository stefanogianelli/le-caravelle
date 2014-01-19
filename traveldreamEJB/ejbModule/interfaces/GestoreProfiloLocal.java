package interfaces;

import javax.ejb.Local;

import dtos.UtenteDTO;
import eccezioni.UtenteInesistenteException;
import entities.Utenti;

@Local
public interface GestoreProfiloLocal {
	
	/**
	 * Restituisce il profilo dell'utente corrente
	 * @return I dati dell'utente
	 */
	Utenti getUtente();	

	/**
	 * Permette la conversione da un DTO alla rispettiva entità
	 * @param utente Il DTO dell'utente
	 * @return L'entità desiderata
	 * @throws UtenteInesistenteException Quando l'utente non viene trovato nel database
	 */
	Utenti convertiInEntita (UtenteDTO utente) throws UtenteInesistenteException;
	
	/**
	 * Permette la conversione da un'entità al rispettivo DTO
	 * @param utente L'entità di partenza
	 * @return Il relativo DTO
	 */
	UtenteDTO convertiInDTO (Utenti utente);

}
