package ejbs;

import java.util.List;

import javax.ejb.Local;

import dtos.CittaDTO;
import eccezioni.CittaInesistenteException;

@Local
public interface GestoreCitta {

	/**
	 * Ritorna l'elenco di tutte le città presenti nel database
	 * @return L'elenco delle città
	 */
	List<CittaDTO> elencoCitta();
	
	/**
	 * Permette di restituire il DTO della città associata ad un nome
	 * @param nome Il nome della città
	 * @return Il rispettivo DTO
	 * @throws CittaInesistenteException Se la città non viene trovata nel database
	 */
	CittaDTO cercaCitta (String nome)  throws CittaInesistenteException;
}
