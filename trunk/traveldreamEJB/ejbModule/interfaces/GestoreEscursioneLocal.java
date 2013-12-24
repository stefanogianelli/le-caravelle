package interfaces;

import javax.ejb.Local;

import dtos.EscursioneDTO;
import eccezioni.EscursioneInesistenteException;
import entities.Escursioni;

@Local
public interface GestoreEscursioneLocal {

	/**
	 * Permette la conversione da un DTO alla rispettiva entità
	 * @param escursione Il DTO dell'escursione
	 * @return L'entità desiderata
	 * @throws EscursioneInesistenteException Quando l'escursione non viene trovata nel database
	 */
	Escursioni convertiInEntita (EscursioneDTO escursione) throws EscursioneInesistenteException;
	
	/**
	 * Permette la conversione da un DTO alla rispettiva entità
	 * @param idEscursione L'identificativo dell'escursione
	 * @return L'entità desiderata
	 * @throws EscursioneInesistenteException Quando l'escursione non viene trovata nel database
	 */
	Escursioni convertiInEntita (int idEscursione) throws EscursioneInesistenteException;
	
	/**
	 * Permette la conversione da un'entità al rispettivo DTO
	 * @param escursione L'oggetto da converitre
	 * @return Il DTO risultante
	 */
	EscursioneDTO convertiInDTO (Escursioni escursione);
}
