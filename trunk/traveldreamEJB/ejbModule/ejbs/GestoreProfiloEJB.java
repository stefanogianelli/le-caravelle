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

import org.apache.commons.codec.digest.DigestUtils;

import dtos.PersonaDTO;
import dtos.UtenteDTO;
import eccezioni.EntitaEsistenteException;
import eccezioni.InsertException;
import eccezioni.UtenteInesistenteException;
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
	private EmailBeanLocal emailBean;
	
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
		emailBean.inviaPassword(email, password);
		/*
		 * DEBUG
		 */
		System.out.println("Utente: " + email + "\nPassword: " + password);
		
		em.persist(utente);
	}
	
	@Override
	public void modificaPassword (String vecchiaPassword, String nuovaPassword) throws InsertException {
		Utenti utente = this.getUtente();
		
		if (DigestUtils.sha256Hex(vecchiaPassword).equals(utente.getPassword()))
			utente.setPassword(nuovaPassword);
		else
			throw new InsertException();
	}
	
	@Override
	public void aggiuntaDatiPersonali(UtenteDTO datiUtente) {
		Utenti utente = this.getUtente();

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
		Utenti utente = this.getUtente();
		
		Persone persona = utente.getPersona();
		persona.setDocumentoIdentita(datiUtente.getPersona().getDocumentoIdentita());
		persona.setTelefono(datiUtente.getPersona().getTelefono());

		utente.setPersona(persona);
		
		em.merge(utente);
	}	

	@Override
	public void resetPassword(String email) throws MessagingException, UtenteInesistenteException {
		Utenti utente = this.convertiInEntita(email);
		
		String password = this.generaPassword();
		utente.setPassword(password);
		emailBean.resetPassword(utente.getEmail(), password);
		
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

	/**
	 * Permette la conversione da un DTO alla rispettiva entità
	 * @param email L'indirizzo email dell'utente
	 * @return L'entità desiderata
	 * @throws UtenteInesistenteException Quando l'utente non viene trovato nel database
	 */	
	private Utenti convertiInEntita (String email) throws UtenteInesistenteException {
		Utenti utente = em.find(Utenti.class, email);
		if (utente != null)
			return utente;
		else
			throw new UtenteInesistenteException();
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
	
	public PersonaDTO convertiInDTO (Persone persona) {
		PersonaDTO p = new PersonaDTO();
		
		p.setNome(persona.getNome());
		p.setCognome(persona.getCognome());
		p.setDataNascita(persona.getDataNascita());
		p.setDocumentoIdentita(persona.getDocumentoIdentita());
		p.setTelefono(persona.getTelefono());
		
		return p;
	}
}
