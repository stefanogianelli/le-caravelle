package ejbs;

import java.util.List;

import javax.ejb.Local;
import dtos.CollegamentoDTO;
import enums.TipoCollegamento;

@Local
public interface GestoreCollegamento {

	List<CollegamentoDTO> elencoCollegamenti ();
	
	List<CollegamentoDTO> elencoCollegamenti(TipoCollegamento tipo);	
	
	void creaCollegamento (CollegamentoDTO collegamento);
	
	void modificaDatiCollegamento (CollegamentoDTO collegamento);
	
	void eliminaCollegamento (CollegamentoDTO collegamento);
	
}
