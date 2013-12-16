package ejbs;

import java.util.List;

import javax.ejb.Local;

import dtos.CollegamentoDTO;

@Local
public interface GestoreCollegamento {

	public List<CollegamentoDTO> elencoCollegamenti ();
	
	public CollegamentoDTO dettagliCollegamento (int codiceCollegamento);
	
	public void creaCollegamento (CollegamentoDTO collegamento);
	
	public void modificaDatiCollegamento (CollegamentoDTO collegamento);
	
	public void eliminaCollegamento (int idCollegamento);
}
