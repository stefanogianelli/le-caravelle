package ejbs;

import javax.ejb.Stateless;

import dtos.UtenteDTO;

/**
 * Session Bean implementation class GestoreProfiloEJB
 */
@Stateless
public class GestoreProfiloEJB implements GestoreProfilo {

    /**
     * Default constructor. 
     */
    public GestoreProfiloEJB() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void registrazioneUtente(UtenteDTO datiUtente) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetPassword(String email) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificaDatiPersonali(UtenteDTO datiUtenti) {
		// TODO Auto-generated method stub
		
	}

}
