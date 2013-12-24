package remote;

import javax.ejb.Remote;

import dtos.EscursioneDTO;
import eccezioni.EscursioneInesistenteException;
import entities.Escursioni;

@Remote
public interface GestoreEscursioneRemote {

	/**
	 * Permette la conversione da un DTO alla rispettiva entit�
	 * @param escursione Il DTO dell'escursione
	 * @return L'entit� desiderata
	 * @throws EscursioneInesistenteException Quando l'escursione non viene trovata nel database
	 */
	Escursioni convertiInEntita (EscursioneDTO escursione) throws EscursioneInesistenteException;
	
	/**
	 * Permette la conversione da un DTO alla rispettiva entit�
	 * @param idEscursione L'identificativo dell'escursione
	 * @return L'entit� desiderata
	 * @throws EscursioneInesistenteException Quando l'escursione non viene trovata nel database
	 */
	Escursioni convertiInEntita (int idEscursione) throws EscursioneInesistenteException;
	
	/**
	 * Permette la conversione da un'entit� al rispettivo DTO
	 * @param escursione L'oggetto da converitre
	 * @return Il DTO risultante
	 */
	EscursioneDTO convertiInDTO (Escursioni escursione);
}
