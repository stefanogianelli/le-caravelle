package ejbs;

import java.util.List;

import javax.ejb.Stateless;

import dtos.CollegamentoDTO;

/**
 * Session Bean implementation class GestoreCollegamentoEJB
 */
@Stateless
public class GestoreCollegamentoEJB implements GestoreCollegamento {

    /**
     * Default constructor. 
     */
    public GestoreCollegamentoEJB() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public List<CollegamentoDTO> elencoCollegamenti() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CollegamentoDTO dettagliCollegamento(int codiceCollegamento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void creaCollegamento(CollegamentoDTO collegamento) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificaDatiCollegamento(CollegamentoDTO collegamento) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminaCollegamento(int idCollegamento) {
		// TODO Auto-generated method stub
		
	}

}
