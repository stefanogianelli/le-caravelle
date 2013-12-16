package ejbs;

import javax.ejb.Local;

import dtos.UtenteDTO;

@Local
public interface GestoreProfilo {

	void registrazioneUtente (UtenteDTO datiUtente);
	
	void resetPassword (String email);
	
	void modificaDatiPersonali (UtenteDTO datiUtenti);
}
