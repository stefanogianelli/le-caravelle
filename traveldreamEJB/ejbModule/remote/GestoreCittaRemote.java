package remote;

import javax.ejb.Remote;

import dtos.CittaDTO;
import eccezioni.CittaInesistenteException;
import entities.Citta;

@Remote
public interface GestoreCittaRemote {

	/**
	 * Ritorna l'entità associata al nome fornito in input
	 * @param nome Il nome della città
	 * @return L'entità corrispondente
	 */
	Citta getCitta (String nome);
	
	/**
	 * Permette la conversione da un DTO alla rispettiva entità
	 * @param citta Il DTO della citta
	 * @return L'entità desiderata
	 * @throws CittaInesistenteException Se la città non viene trovata nel database
	 */
	Citta convertiInEntita (CittaDTO citta) throws CittaInesistenteException;
	
	/**
	 * Permette la conversione da un'entità al rispettivo DTO
	 * @param citta L'entità di partenza
	 * @return Il relativo DTO
	 */
	CittaDTO convertiInDTO (Citta citta);
	
}
