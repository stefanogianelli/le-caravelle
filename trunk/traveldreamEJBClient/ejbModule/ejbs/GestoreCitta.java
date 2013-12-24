package ejbs;

import java.util.List;

import javax.ejb.Local;

import dtos.CittaDTO;

@Local
public interface GestoreCitta {

	/**
	 * Ritorna l'elenco di tutte le citt� presenti nel database
	 * @return L'elenco delle citt�
	 */
	List<CittaDTO> elencoCitta();
	
	/**
	 * Permette di restituire il DTO della citt� associata ad un nome
	 * @param nome Il nome della citt�
	 * @return Il rispettivo DTO
	 */
	CittaDTO cercaCitta (String nome);
}
