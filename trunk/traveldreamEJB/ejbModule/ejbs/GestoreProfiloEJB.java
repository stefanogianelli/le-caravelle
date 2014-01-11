package ejbs;

import interfaces.EmailBeanLocal;
import interfaces.GestoreProfiloLocal;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dtos.PersonaDTO;
import dtos.UtenteDTO;
import eccezioni.EntitaEsistenteException;
import entities.Gruppi;
import entities.Persone;
import entities.Utenti;

/**
 * Session Bean implementation class GestoreProfiloEJB
 */
@Stateless
public class GestoreProfiloEJB implements GestoreProfilo, GestoreProfiloLocal {
	
	private final int LUNGHEZZA_PASSWORD = 6;

	@PersistenceContext
	private EntityManager em;
	
	@Resource
	private EJBContext context;
	
	@EJB
	private EmailBeanLocal email;
	
	@Override
	public UtenteDTO getUtenteCorrente() {
		return this.convertiInDTO(this.getUtente());
	}
	
	@Override
	public Utenti getUtente() {
		String email = context.getCallerPrincipal().getName();
		return em.find(Utenti.class, email);
	}	

	@Override
	public void registrazioneUtente(String email) throws MessagingException, EntitaEsistenteException {
		//verifico che l'email non sia già stata utilizzata
		if(em.find(Utenti.class, email) != null)
			throw new EntitaEsistenteException();
			
		List<Gruppi> gruppi = new ArrayList<Gruppi>();
		gruppi.add(Gruppi.UTENTE);
		
		Utenti utente = new Utenti ();
		utente.setEmail(email);
		utente.setGruppi(gruppi);		
		String password = this.generaPassword();
		utente.setPassword(password);
		/*
		 * Per eseguire i test viene disabilitato l'invio dell'email con la password
		 */
		//this.email.inviaPassword(email, password);
		System.out.println("Utente: " + email + "\nPassword: " + password);
		
		em.persist(utente);
	}
	
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
		String password = new BigInteger(130, new SecureRandom()).toString(32).substring(0, LUNGHEZZA_PASSWORD);
		
		return password;
	}
	
	@Override
	public Utenti convertiInEntita (UtenteDTO utente) {
		return em.find(Utenti.class, utente.getEmail());
	}
	
	@Override
	public UtenteDTO convertiInDTO (Utenti utente) {
		UtenteDTO utenteDTO = new UtenteDTO();
		
		utenteDTO.setEmail(utente.getEmail());
		utenteDTO.setPassword(utente.getPassword());
		
		if (utente.getPersona() != null) {
			PersonaDTO personaDTO = new PersonaDTO();
			personaDTO.setNome(utente.getPersona().getId().getNome());
			personaDTO.setCognome(utente.getPersona().getId().getCognome());
			personaDTO.setDataNascita(utente.getPersona().getId().getDataNascita());
			personaDTO.setDocumentoIdentita(utente.getPersona().getDocumentoIdentita());
			personaDTO.setTelefono(utente.getPersona().getTelefono());
			
			utenteDTO.setPersona(personaDTO);
		}
		
		return utenteDTO;
	}
}
