package ejbs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import utils.EmailBean;
import dtos.PersonaDTO;
import dtos.UtenteDTO;
import entities.Gruppi;
import entities.Persone;
import entities.PersonePK;
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
	public void registrazioneUtente(UtenteDTO datiUtente) throws MessagingException {
		List<Gruppi> gruppi = new ArrayList<Gruppi>();
		gruppi.add(Gruppi.UTENTE);
		
		PersonePK personapk = new PersonePK();
		personapk.setNome(datiUtente.getPersona().getNome());
		personapk.setCognome(datiUtente.getPersona().getCognome());
		personapk.setDataNascita(datiUtente.getPersona().getDataNascita());
		
		Persone persona = new Persone ();
		persona.setDocumentoIdentita(datiUtente.getPersona().getDocumentoIdentita());
		persona.setTelefono(datiUtente.getPersona().getTelefono());
		persona.setId(personapk);
		
		Utenti utente = new Utenti ();
		utente.setEmail(datiUtente.getEmail());
		utente.setPersona(persona);
		utente.setGruppi(gruppi);		
		email.inviaPassword(datiUtente.getEmail(), this.generaPassword(utente));
		
		em.persist(utente);
	}
	
	@Override
	public void aggiuntaDatiPersonali(UtenteDTO datiUtente) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Permette il reset della password
	 * @param datiUtente I dati dell'utente che ha richiesto il reset
	 * @param MessagingException
	 */
	@Override
	public void resetPassword(UtenteDTO datiUtente) throws MessagingException {
		Utenti utente = this.convertiInEntita(datiUtente);
		
		email.resetPassword(utente.getEmail(), this.generaPassword(utente));
		
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
		
		PersonePK personapk = persona.getId();
		personapk.setNome(datiUtente.getPersona().getNome());
		personapk.setCognome(datiUtente.getPersona().getCognome());
		personapk.setDataNascita(datiUtente.getPersona().getDataNascita());
		
		persona.setId(personapk);
		utente.setPersona(persona);
		
		em.merge(utente);
	}
	
	/**
	 * Genera una password casuale e assegna la password cifrata all'utente
	 * @param utente L'utente al quale assegnare la password
	 * @return La password generata in chiaro
	 */
	private String generaPassword (Utenti utente) {
		String password = RandomStringUtils.random(LUNGHEZZA_PASSWORD);
		utente.setPassword(DigestUtils.sha256Hex(password));
		return password;
	}
	
	/**
	 * Permette la conversione da un DTO alla rispettiva entità
	 * @param utente Il DTO dell'utente
	 * @return L'entità desiderata
	 */
	protected Utenti convertiInEntita (UtenteDTO utente) {
		return em.find(Utenti.class, utente.getEmail());
	}
	
	/**
	 * Permette la conversione da un'entità al rispettivo DTO
	 * @param utente L'entità di partenza
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
