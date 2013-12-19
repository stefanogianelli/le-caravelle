package ejbs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.codec.digest.DigestUtils;

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

	@PersistenceContext
	private EntityManager em;
	
    /**
     * Default constructor. 
     */
    public GestoreProfiloEJB() {
     
    }

    /**
     * Permette la creazione di un profilo utente
     * @param datiUtente I dati dell'utente che si vuole registrare
     */
	@Override
	public void registrazioneUtente(UtenteDTO datiUtente) {
		List<Gruppi> gruppi = new ArrayList<Gruppi>();
		gruppi.add(Gruppi.UTENTE);
		
		PersonePK personapk = new PersonePK();
		//personapk.setNome(datiUtente.getNome());
		//personapk.setCognome(datiUtente.getCognome());
		//personapk.setDataNascita(datiUtente.getDataNascita());
		
		Persone persona = new Persone ();
		//persona.setDocumentoIdentita(datiUtente.getDocumentoIdentita());
		//persona.setTelefono(datiUtente.getTelefono());
		persona.setId(personapk);
		
		Utenti utente = new Utenti ();
		utente.setEmail(datiUtente.getEmail());
		utente.setPersona(persona);
		utente.setGruppi(gruppi);
		String password = this.generaPassword(utente);
		utente.setPassword(password);		
		
		//inviare per email la password all'utente
		
		em.persist(utente);
	}

	@Override
	public void resetPassword(String email) {
		// TODO Auto-generated method stub		
	}

	/**
	 * Permette la modifica dei dati personali dell'utente
	 * @param email L'indirizzo email dell'utente
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
	 * Genera una password casuale
	 * @return La password cifrata con l'algoritmo SHA-256
	 */
	private String generaPassword (Utenti utente) {
		String password = "funzione casuale";
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
