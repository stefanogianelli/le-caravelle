package ejbs;

import javax.ejb.Local;
import javax.persistence.EntityExistsException;

import dtos.UtenteDTO;

@Local
public interface GestoreProfilo {

	void registrazioneUtente (UtenteDTO datiUtente) throws EntityExistsException;
	
	void resetPassword (String email);
	
	void modificaDatiPersonali (UtenteDTO datiUtente);
}
