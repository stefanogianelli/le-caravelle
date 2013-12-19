package ejbs;

import javax.ejb.Local;
import javax.mail.MessagingException;

import dtos.UtenteDTO;

@Local
public interface GestoreProfilo {

	void registrazioneUtente (UtenteDTO datiUtente) throws MessagingException;
	
	void resetPassword (UtenteDTO datiUtente) throws MessagingException;
	
	void modificaDatiPersonali (UtenteDTO datiUtente);
}
