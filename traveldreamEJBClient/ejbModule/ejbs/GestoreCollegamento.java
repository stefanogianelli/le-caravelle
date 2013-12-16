package ejbs;

import java.util.List;

import javax.ejb.Local;

import dtos.CollegamentoDTO;
import enums.TipoCollegamento;

@Local
public interface GestoreCollegamento {

	List<CollegamentoDTO> elencoCollegamenti ();
	
	List<CollegamentoDTO> elencoCollegamenti(TipoCollegamento tipo);	
	
	CollegamentoDTO dettagliCollegamento (int codiceCollegamento);
	
	void creaCollegamento (CollegamentoDTO collegamento);
	
	void modificaDatiCollegamento (int codice, CollegamentoDTO collegamento);
	
	void eliminaCollegamento (int codice);
	
}
