package ejbs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.RandomStringUtils;

import utils.EmailBean;
import dtos.PersonaDTO;
import dtos.UtenteDTO;
import entities.Gruppi;
import entities.Persone;
import entities.Utenti;

/**
 * Session Bean implementation class GestoreProfiloEJB
 */
@Stateless
public class GestoreProfiloEJB implements GestoreProfilo {
	
	private final int LUNGHEZZA_PASSWORD = 6;

	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private EmailBean email;
	
    /**
     * Default constructor. 
     */
    public GestoreProfiloEJB() {
     
    }

    /**
     * Permette la creazione di un profilo utente
     * @param datiUtente I dati dell'utente che si vuole registrare
     * @throws MessagingException 
     */
	@Override
	public void registrazioneUtente(String email) throws MessagingException {
		List<Gruppi> gruppi = new ArrayList<Gruppi>();
		gruppi.add(Gruppi.UTENTE);
		
		Utenti utente = new Utenti ();
		utente.setEmail(email);
		utente.setGruppi(gruppi);		
		String password = this.generaPassword();
		utente.setPassword(password);
		this.email.inviaPassword(email, password);
		
		em.persist(utente);
	}
	
	/**
	 * Permette l'aggiunta dei dati personali di un utente
	 * @param datiUtente I dati dell'utente
	 */
	@Override
	public void aggiuntaDatiPersonali(UtenteDTO datiUtente) {
		Utenti utente = this.convertiInEntita(datiUtente);
		
		Persone persona = new Persone ();
		persona.setNome(datiUtente.getPersona().getNome());
		persona.setCognome(datiUtente.getPersona().getCognome());
		persona.setDataNascita(datiUtente.getPersona().getDataNascita());
		persona.setDocumentoIdentita(datiUtente.getPersona().getDocumentoIdentita());
		persona.setTelefono(datiUtente.getPersona().getTelefono());
		
		utente.setPersona(persona);
		
		em.merge(utente);
	}
	
	/**
	 * Permette la modifica dei dati personali dell'utente
	 * @param datiUtente I dati dell'utente
	 */
	@Override
	public void modificaDatiPersonali(UtenteDTO datiUtente) {
		Utenti utente = this.convertiInEntita(datiUtente);
		
		utente.setEmail(datiUtente.getEmail());
		utente.setPassword(datiUtente.getPassword());
		
		Persone persona = utente.getPersona();
		persona.setDocumentoIdentita(datiUtente.getPersona().getDocumentoIdentita());
		persona.setTelefono(datiUtente.getPersona().getTelefono());
		persona.setNome(datiUtente.getPersona().getNome());
		persona.setCognome(datiUtente.getPersona().getCognome());
		persona.setDataNascita(datiUtente.getPersona().getDataNascita());

		utente.setPersona(persona);
		
		em.merge(utente);
	}	

	/**
	 * Permette il reset della password
	 * @param datiUtente I dati dell'utente che ha richiesto il reset
	 * @param MessagingException
	 */
	@Override
	public void resetPassword(UtenteDTO datiUtente) throws MessagingException {
		Utenti utente = this.convertiInEntita(datiUtente);
		
		String password = this.generaPassword();
		utente.setPassword(password);
		email.resetPassword(utente.getEmail(), password);
		
		em.merge(utente);
	}
	
	/**
	 * Genera una password casuale
	 * @return La password generata in chiaro
	 */
	private String generaPassword () {
		String password = RandomStringUtils.random(LUNGHEZZA_PASSWORD);
		
		return password;
	}
	
	/**
	 * Permette la conversione da un DTO alla rispettiva entit�
	 * @param utente Il DTO dell'utente
	 * @return L'entit� desiderata
	 */
	protected Utenti convertiInEntita (UtenteDTO utente) {
		return em.find(Utenti.class, utente.getEmail());
	}
	
	/**
	 * Permette la conversione da un'entit� al rispettivo DTO
	 * @param utente L'entit� di partenza
	 * @return Il relativo DTO
	 */
	protected UtenteDTO convertiInDTO (Utenti utente) {
		UtenteDTO utenteDTO = new UtenteDTO();
		
		utenteDTO.setEmail(utente.getEmail());
		utenteDTO.setPassword(utenteDTO.getPassword());
		
		PersonaDTO personaDTO = new PersonaDTO();
		personaDTO.setNome(utente.getPersona().getId().getNome());
		personaDTO.setCognome(utente.getPersona().getId().getCognome());
		personaDTO.setDataNascita(utente.getPersona().getId().getDataNascita());
		personaDTO.setDocumentoIdentita(utente.getPersona().getDocumentoIdentita());
		personaDTO.setTelefono(utente.getPersona().getTelefono());
		
		utenteDTO.setPersona(personaDTO);;
		
		return utenteDTO;
	}
}
