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
	
	CollegamentoDTO dettagliCollegamento (int codiceCollegamento) throws NoResultException;
	
	void creaCollegamento (CollegamentoDTO collegamento) throws EntityExistsException, NoResultException;
	
	void modificaDatiCollegamento (int codice, CollegamentoDTO collegamento) throws NoResultException;
	
	void eliminaCollegamento (int codice) throws NoResultException;
	
}
