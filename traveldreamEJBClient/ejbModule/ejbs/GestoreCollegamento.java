package ejbs;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import dtos.CollegamentoDTO;
import enums.TipoCollegamento;

@Local
public interface GestoreCollegamento {

	List<CollegamentoDTO> elencoCollegamenti ();
	
	List<CollegamentoDTO> elencoCollegamenti(TipoCollegamento tipo);	
	
	void creaCollegamento (CollegamentoDTO collegamento) throws EntityExistsException, NoResultException;
	
	void modificaDatiCollegamento (CollegamentoDTO collegamento) throws NoResultException;
	
	void eliminaCollegamento (CollegamentoDTO collegamento) throws NoResultException;
	
}
