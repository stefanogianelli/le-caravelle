package ejbs;

import javax.ejb.Local;

import dtos.UtenteDTO;

@Local
public interface GestoreProfilo {

	public void registrazioneUtente (UtenteDTO datiUtente);
	
	public void resetPassword (String email);
	
	public void modificaDatiPersonali (UtenteDTO datiUtenti);
}
