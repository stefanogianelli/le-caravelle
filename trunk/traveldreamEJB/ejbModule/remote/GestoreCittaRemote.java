package remote;

import javax.ejb.Remote;

import dtos.CittaDTO;
import eccezioni.CittaInesistenteException;
import entities.Citta;

@Remote
public interface GestoreCittaRemote {

	/**
	 * Ritorna l'entit� associata al nome fornito in input
	 * @param nome Il nome della citt�
	 * @return L'entit� corrispondente
	 */
	Citta getCitta (String nome);
	
	/**
	 * Permette la conversione da un DTO alla rispettiva entit�
	 * @param citta Il DTO della citta
	 * @return L'entit� desiderata
	 * @throws CittaInesistenteException Se la citt� non viene trovata nel database
	 */
	Citta convertiInEntita (CittaDTO citta) throws CittaInesistenteException;
	
	/**
	 * Permette la conversione da un'entit� al rispettivo DTO
	 * @param citta L'entit� di partenza
	 * @return Il relativo DTO
	 */
	CittaDTO convertiInDTO (Citta citta);
	
}
