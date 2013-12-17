package ejbs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.codec.digest.DigestUtils;

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
     * @throws EntityExistsException Quando esiste già l'utente nel database
     */
	@Override
	public void registrazioneUtente(UtenteDTO datiUtente) throws EntityExistsException {
		List<Gruppi> gruppi = new ArrayList<Gruppi>();
		gruppi.add(Gruppi.UTENTE);
		
		PersonePK personapk = new PersonePK();
		personapk.setNome(datiUtente.getNome());
		personapk.setCognome(datiUtente.getCognome());
		personapk.setDataNascita(datiUtente.getDataNascita());
		
		Persone persona = new Persone ();
		persona.setDocumentoIdentita(datiUtente.getDocumentoIdentita());
		persona.setTelefono(datiUtente.getTelefono());
		persona.setId(personapk);
		
		Utenti utente = new Utenti ();
		utente.setEmail(datiUtente.getEmail());
		utente.setPersona(persona);
		utente.setGruppi(gruppi);
		
		String password = this.generaPassword(utente);
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
	public void modificaDatiPersonali(String email, UtenteDTO datiUtente) {
		Utenti utente = this.getUtente(email);
		
		utente.setEmail(datiUtente.getEmail());
		//utente.setPassword(password);
		
		Persone persona = utente.getPersona();
		persona.setDocumentoIdentita(datiUtente.getDocumentoIdentita());
		persona.setTelefono(datiUtente.getTelefono());
		
		PersonePK personapk = persona.getId();
		personapk.setNome(datiUtente.getNome());
		personapk.setCognome(datiUtente.getCognome());
		personapk.setDataNascita(datiUtente.getDataNascita());
		
		em.merge(utente);
	}
	
	/**
	 * Ricerca un utente nel database in base all'indirizzo email
	 * @param email L'indirizzo email
	 * @return L'utente desiderato
	 */
	private Utenti getUtente (String email) {
		return em.find(Utenti.class, email);
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

}
