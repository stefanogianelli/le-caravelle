package ejbs;

import java.util.List;

import javax.ejb.Local;

import dtos.CittaDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.InsertException;

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
	
	/**
	 * Permette di aggiungere una nuova città nel database
	 * @param citta I dati della città
	 * @throws InsertException Quando la città è già presente nel database
	 */
	void nuovaCitta (CittaDTO citta) throws InsertException;
}
