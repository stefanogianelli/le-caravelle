package ejbs;

import javax.ejb.Local;
import javax.mail.MessagingException;

import dtos.UtenteDTO;

@Local
public interface GestoreProfilo {

	void registrazioneUtente (UtenteDTO datiUtente) throws MessagingException;
	
	void aggiuntaDatiPersonali (UtenteDTO datiUtente);
	
	void modificaDatiPersonali (UtenteDTO datiUtente);
	
	void resetPassword (UtenteDTO datiUtente) throws MessagingException;	
}
