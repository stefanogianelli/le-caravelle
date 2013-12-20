package ejbs;

import java.util.List;

import javax.ejb.Local;

import dtos.CittaDTO;

@Local
public interface GestoreCitta {

	/**
	 * Ritorna l'elenco di tutte le città presenti nel database
	 * @return L'elenco delle città
	 */
	List<CittaDTO> elencoCitta();
}
