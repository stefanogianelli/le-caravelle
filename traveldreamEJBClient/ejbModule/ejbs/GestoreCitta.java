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
}
