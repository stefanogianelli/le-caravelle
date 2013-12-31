package ejbs;

import interfaces.GestoreCittaLocal;
import interfaces.GestoreCollegamentoLocal;
import interfaces.GestoreDestinazioneLocal;
import interfaces.GestoreEscursioneLocal;
import interfaces.GestoreHotelLocal;
import interfaces.GestorePacchettoLocal;
import interfaces.GestorePacchettoPredefinitoLocal;
import interfaces.GestoreProfiloLocal;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dtos.AttivitaDTO;
import dtos.CollegamentoDTO;
import dtos.DestinazioneDTO;
import dtos.PacchettoDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.CollegamentoInesistenteException;
import eccezioni.DestinazioneInesistenteException;
import eccezioni.EscursioneInesistenteException;
import eccezioni.HotelInesistenteException;
import eccezioni.InsertException;
import eccezioni.PacchettoInesistenteException;
import entities.Amici;
import entities.Attivita;
import entities.Collegamenti;
import entities.Destinazioni;
import entities.Pacchetti;
import enums.TipoPacchetto;

/**
 * Session Bean implementation class GestorePacchettoEJB
 */
@Stateless
public class GestorePacchettoEJB implements GestorePacchetto, GestorePacchettoLocal {

	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private GestoreHotelLocal hotel;
	
	@EJB
	private GestoreDestinazioneLocal destinazione;
	
	@EJB
	private GestoreCollegamentoLocal collegamento;
	
	@EJB
	private GestoreCittaLocal citta;
	
	@EJB
	private GestoreProfiloLocal profilo;
	
	@EJB
	private GestorePacchettoPredefinitoLocal predefinito;
	
	@EJB
	private GestoreEscursioneLocal escursione;
	
	@Override
	public PacchettoDTO getPacchetto (int idPacchetto) throws PacchettoInesistenteException {
		Query q = em.createNamedQuery("Pacchetti.getPacchettoDaId", Pacchetti.class);
		q.setParameter("id", idPacchetto);
		Pacchetti pacchetto;
		try {
			pacchetto = (Pacchetti) q.getSingleResult();
		} catch (NoResultException e) {
			throw new PacchettoInesistenteException();
		}
		return this.convertiInDTO(pacchetto);
	}
	
	@Override
	public List<PacchettoDTO> elencoPacchetti(String email, TipoPacchetto tipo) {		
		Query q = em.createNamedQuery("Pacchetti.getPacchettiPerTipo", Pacchetti.class);
		q.setParameter("utente", email);
		q.setParameter("tipo", tipo);
		@SuppressWarnings("unchecked")
		List<Pacchetti> pacchetti = q.getResultList();
		List<PacchettoDTO> pacchettiDTO = new ArrayList<PacchettoDTO>();
		for (Pacchetti p : pacchetti) {
			pacchettiDTO.add(this.convertiInDTO(p));
		}
		return pacchettiDTO;
	}
	
	@Override
	public int creaPacchettoPersonalizzato(PacchettoDTO pacchetto) throws CittaInesistenteException, HotelInesistenteException, InsertException {
		Pacchetti entity = new Pacchetti();		

		if (pacchetto.getNome() == null ||pacchetto.getNome().isEmpty())
			entity.setNome("Pacchetto" + Math.random());
		else {
			//controllo che il nome del pacchetto non esista nel database
			Query q = em.createNamedQuery("Pacchetti.getPacchettiPerNome", Pacchetti.class);
			q.setParameter("nome", pacchetto.getNome());
			q.setParameter("utente", pacchetto.getUtente().getEmail());
			if (!q.getResultList().isEmpty())
				throw new InsertException();
			else
				entity.setNome(pacchetto.getNome());
		}		
		entity.setNumPartecipanti(pacchetto.getNumPartecipanti());		
		entity.setTipoPacchetto(TipoPacchetto.PERSONALIZZATO);
		for (DestinazioneDTO d : pacchetto.getDestinazioni()) {
			entity.addDestinazione(this.destinazione.creaDestinazione(d));
		}
		entity.setCitta(this.citta.getCitta(pacchetto.getCitta().getNome()));	
		entity.setUtente(this.profilo.convertiInEntita(pacchetto.getUtente()));
		entity.setPrezzo(this.calcolaPrezzo(entity));
		
		em.persist(entity);
		em.flush();
		
		return entity.getId();
	}

	@Override
	public void modificaNomePacchetto (PacchettoDTO pacchetto) throws PacchettoInesistenteException, InsertException {
		Pacchetti entity = this.convertiInEntita(pacchetto);
		
		//controllo che il nome del pacchetto non esista nel database
		Query q = em.createNamedQuery("Pacchetti.getPacchettiPerNome", Pacchetti.class);
		q.setParameter("nome", pacchetto.getNome());
		q.setParameter("utente", pacchetto.getUtente().getEmail());
		if (!q.getResultList().isEmpty())
			throw new InsertException();
		else
			entity.setNome(pacchetto.getNome());
		
		em.merge(entity);
	}
	
	@Override
	public void modificaCittaPartenzaPacchetto (PacchettoDTO pacchetto) throws PacchettoInesistenteException, CittaInesistenteException {
		Pacchetti entity = this.convertiInEntita(pacchetto);
		
		//se viene cambiata la città di partenza rimuovo il primo e ultimo collegamento
		if (!entity.getCitta().getNome().equals(pacchetto.getCitta().getNome()) && !entity.getDestinazioni().isEmpty())
			this.rimuoviCollegamenti(entity, entity.getDestinazioni().get(0).getDataArrivo(), entity.getDestinazioni().get(entity.getDestinazioni().size() - 1).getDataPartenza());
		
		entity.setCitta(this.citta.getCitta(pacchetto.getCitta().getNome()));
		
		if (entity.getTipoPacchetto() != TipoPacchetto.PREDEFINITO)
			entity.setPrezzo(this.calcolaPrezzo(entity));
		
		em.merge(entity);
	}
	
	@Override
	public void modificaNumeroPartecipanti (PacchettoDTO pacchetto) throws PacchettoInesistenteException {
		Pacchetti entity = this.convertiInEntita(pacchetto);
		
		entity.setNumPartecipanti(pacchetto.getNumPartecipanti());
		//aggiorno il numero di partecipanti delle (eventuali) escursioni
		for (Destinazioni d : entity.getDestinazioni()) {
			for (Attivita a : d.getAttivita()) {
				if (a.getNumPartecipanti() > entity.getNumPartecipanti())
					a.setNumPartecipanti(entity.getNumPartecipanti());
			}
		}
		if (entity.getTipoPacchetto() != TipoPacchetto.PREDEFINITO)
			entity.setPrezzo(this.calcolaPrezzo(entity));
		else
			entity.setPrezzo(this.calcolaPrezzoPredefinito(entity));
		
		em.merge(entity);		
	}
	
	@Override
	public void salvaPacchettoPredefinito (PacchettoDTO pacchetto) throws CittaInesistenteException, HotelInesistenteException, PacchettoInesistenteException, InsertException {
		Pacchetti entity = new Pacchetti();
		
		//controllo che il nome del pacchetto non esista nel database
		Query q = em.createNamedQuery("Pacchetti.getPacchettiPerNome", Pacchetti.class);
		q.setParameter("nome", pacchetto.getNome());
		q.setParameter("utente", pacchetto.getUtente().getEmail());
		if(q.getResultList().isEmpty()) {		
			entity.setNome(pacchetto.getNome());
			entity.setNumPartecipanti(pacchetto.getNumPartecipanti());
			entity.setTipoPacchetto(TipoPacchetto.PREDEFINITO);
			for (DestinazioneDTO d : pacchetto.getDestinazioni()) {
				entity.addDestinazione(this.destinazione.creaDestinazione(d));
			}
			entity.setCitta(this.citta.getCitta(pacchetto.getCitta().getNome()));
			entity.setPacchettoPredefinito(this.predefinito.convertiInEntita(pacchetto.getPacchettoPredefinito()));
			entity.setUtente(this.profilo.convertiInEntita(pacchetto.getUtente()));
			entity.setPrezzo(this.calcolaPrezzoPredefinito(entity));
			
			em.persist(entity);
		}
		else
			throw new InsertException();
	}

	@Override
	public void acquistaPacchetto(PacchettoDTO pacchetto) throws PacchettoInesistenteException {
		Pacchetti entity = this.convertiInEntita(pacchetto);

		entity.setTipoPacchetto(TipoPacchetto.ACQUISTATO);
			
		em.merge(entity);
	}

	@Override
	public void condividiPacchetto(PacchettoDTO pacchetto, String email, String nome, String cognome) throws CittaInesistenteException, HotelInesistenteException, CollegamentoInesistenteException, EscursioneInesistenteException {
		Amici amico = new Amici();
		//verifico se l'indirizzo email è già esistente
		Query q = em.createNamedQuery("Amici.getAmico", Amici.class);
		q.setParameter("email", email);
		if (q.getResultList().isEmpty()) {
			amico.setEmail(email);
			amico.setNome(nome);
			amico.setCognome(cognome);			
		} else {
			amico = (Amici) q.getResultList().get(0);
		}
		
		Pacchetti entity = new Pacchetti();
		
		entity.setNome(pacchetto.getNome() + " (Condiviso con " + nome + " " + cognome + ")");
		entity.setNumPartecipanti(pacchetto.getNumPartecipanti());
		entity.setPrezzo(pacchetto.getPrezzo());
		entity.setTipoPacchetto(TipoPacchetto.CONDIVISO);
		for (DestinazioneDTO d : pacchetto.getDestinazioni()) {
			entity.addDestinazione(this.destinazione.creaDestinazione(d));
			for (AttivitaDTO a : d.getAttivita()) {
				Attivita attivita = new Attivita();
				attivita.setEscursione(this.escursione.convertiInEntita(a.getEscursione()));
				attivita.setNumPartecipanti(a.getNumeroPartecipanti());
				entity.getDestinazioni().get(entity.getDestinazioni().size() - 1).addAttivita(attivita);
			}
		}
		for (CollegamentoDTO c : pacchetto.getCollegamenti()) {
			entity.addCollegamento(this.collegamento.convertiInEntita(c));
		}
		entity.setCitta(this.citta.convertiInEntita(pacchetto.getCitta()));
		entity.setUtente(this.profilo.convertiInEntita(pacchetto.getUtente()));
		
		amico.addPacchetto(entity);	
		
		em.merge(amico);
	}

	@Override
	public void eliminaPacchetto(PacchettoDTO pacchetto) throws PacchettoInesistenteException {
		Pacchetti entity = this.convertiInEntita(pacchetto);
		if (pacchetto.getTipoPacchetto() == TipoPacchetto.CONDIVISO) {
			//rimuovo tutte le condivisione del pacchetto
			Query q = em.createNamedQuery("Amici.getPacchetti", Amici.class);
			q.setParameter("id", pacchetto.getId());
			@SuppressWarnings("unchecked")
			List<Amici> amici = q.getResultList();
			for (Amici a : amici) {
				a.removePacchetto(entity);
			}
		}
		em.remove(entity);		
	}

	@Override
	public void aggiuntaDestinazione(int idPacchetto, DestinazioneDTO destinazione) throws CittaInesistenteException, HotelInesistenteException, PacchettoInesistenteException, InsertException {
		Pacchetti entity = this.convertiInEntita(idPacchetto);
		
		//controllo le date della destinazione
		if (destinazione.getDataPartenza().compareTo(entity.getDestinazioni().get(0).getDataArrivo()) == 0 || destinazione.getDataArrivo().compareTo(entity.getDestinazioni().get(entity.getDestinazioni().size() - 1).getDataPartenza()) == 0) {
			//impedisco all'utente di selezionare un hotel nella stessa città di partenza
			if (!destinazione.getCitta().getNome().equalsIgnoreCase(entity.getCitta().getNome())) {
				//controllo che la data di arrivo sia minore della data di partenza dalla destinazione
				if (destinazione.getDataArrivo().before(destinazione.getDataPartenza())) {			
					entity.addDestinazione(this.destinazione.creaDestinazione(destinazione));
					//aggiorno il prezzo del pacchetto
					entity.setPrezzo(entity.getPrezzo() + destinazione.getHotel().getPrezzo()*entity.getNumPartecipanti()*(int)( (destinazione.getDataPartenza().getTime() - destinazione.getDataArrivo().getTime()) / (1000 * 60 * 60 * 24)));
					this.rimuoviCollegamenti(entity, destinazione.getDataArrivo(), destinazione.getDataPartenza());
					
					em.merge(entity);
				} else
					throw new InsertException("La data di arrivo deve essere precedente alla data di partenza!");
			} else
				throw new InsertException("La città di partenza e la destinazione non possono essere uguali!");
		} else
			throw new InsertException("Date non valide");
	}
	
	@Override
	public void modificaDateDestinazione(PacchettoDTO pacchetto, DestinazioneDTO destinazione) throws PacchettoInesistenteException, CittaInesistenteException {
		Pacchetti entity = this.convertiInEntita(pacchetto);
		
		this.destinazione.modificaDateDestinazione(destinazione);
		
		//rimuovo i collegamenti non più coerenti
		this.rimuoviCollegamenti(entity, destinazione.getDataArrivo(), destinazione.getDataPartenza());
		
		//aggiorno il prezzo del pacchetto
		entity.setPrezzo(this.calcolaPrezzo(entity));
		
		em.merge(entity);
	}
	
	@Override
	public void eliminaDestinazione(PacchettoDTO pacchetto, DestinazioneDTO destinazione) throws DestinazioneInesistenteException, PacchettoInesistenteException {
		Pacchetti entity = this.convertiInEntita(pacchetto);	
		
		//aggiorno il prezzo del pacchetto
		entity.setPrezzo(entity.getPrezzo() - destinazione.getHotel().getPrezzo()*entity.getNumPartecipanti()*(int)( (destinazione.getDataPartenza().getTime() - destinazione.getDataArrivo().getTime()) / (1000 * 60 * 60 * 24)));
		entity.removeDestinazione(this.destinazione.convertiInEntita(destinazione));	
		//rimuovo gli eventuali collegamenti collegati
		this.rimuoviCollegamenti(entity, destinazione.getDataArrivo(), destinazione.getDataPartenza());		
		
		em.merge(entity);
	}

	@Override
	public void aggiuntaCollegamento(int idPacchetto, CollegamentoDTO collegamento) throws CollegamentoInesistenteException, PacchettoInesistenteException {
		Pacchetti entity = this.convertiInEntita(idPacchetto);
	
		//rimuovo l'eventuale collegamento già presente nella stessa data
		for (Collegamenti c : entity.getCollegamenti()) {
			if (collegamento.getDataPartenza().compareTo(c.getDataPartenza()) == 0) {
				entity.removeCollegamento(c);
				break;
			}
		}
		
		entity.addCollegamento(this.collegamento.convertiInEntita(collegamento));
		//aggiorno il prezzo del pacchetto
		if (entity.getTipoPacchetto() != TipoPacchetto.PREDEFINITO)
			entity.setPrezzo(entity.getPrezzo() + collegamento.getPrezzo()*entity.getNumPartecipanti());
		
		em.merge(entity);
	}
	
	/**
	 * Calcola il costo complessivo di un pacchetto creato dall'utente
	 * @param pacchetto Il pacchetto del quale si vuole calcolare il totale
	 * @return Il costo totale del pacchetto
	 */
	private double calcolaPrezzo (Pacchetti pacchetto) {
		double totale = 0.0;
		double esc = 0.0;
		//sommo il costo degli hotel delle varie destinazioni
		for (Destinazioni d : pacchetto.getDestinazioni()) {
			totale += d.getHotel().getPrezzo()*(int)( (d.getDataPartenza().getTime() - d.getDataArrivo().getTime()) / (1000 * 60 * 60 * 24));
			//calcolo il costo delle escursioni inserite nella destinazione
			for (Attivita a : d.getAttivita()) {
				esc += a.getEscursione().getPrezzo()*a.getNumPartecipanti();
			}
		}
		//sommo il costo dei vari collegamenti inseriti
		for (Collegamenti c : pacchetto.getCollegamenti())
			totale += c.getPrezzo();
		//moltiplico per il numero di partecipanti
		totale *= pacchetto.getNumPartecipanti();
		//aggiungo il costo delle escursioni
		totale += esc;
		
		return totale;
	}
	
	/**
	 * Calcola il costo di un pacchetto predefinito
	 * @param pacchetto Il pacchetto del quale si vuole calcolare il totale
	 * @return Il costo del pacchetto
	 */
	private double calcolaPrezzoPredefinito (Pacchetti pacchetto) {
		double totale = 0.0;
		
		totale = pacchetto.getPacchettoPredefinito().getPrezzo()*pacchetto.getNumPartecipanti()*(int)( (pacchetto.getDestinazioni().get(0).getDataPartenza().getTime() - pacchetto.getDestinazioni().get(0).getDataArrivo().getTime()) / (1000 * 60 * 60 * 24));
		
		return totale;
	}
	
	/**
	 * Si occupa di rimuovere i collegemnti non più coerenti.
	 * Questo può accadere in caso di aggiunta nuova destinazione, modifica date di una destinazione o eliminazione di una destinazione.
	 * @param pacchetto Il pacchetto nel quale rimuovere il/i collegamento/i
	 * @param dataArrivo La data nella quale rimuovere il collegamento di andata
	 * @param dataPartenza La data nella quale rimuovere il collegamento di ritorno
	 */
	private void rimuoviCollegamenti (Pacchetti pacchetto, Date dataArrivo, Date dataPartenza) {
		Collegamenti andata = null;
		Collegamenti ritorno = null;
		Collegamenti c = null;
		
		for (Iterator<Collegamenti> itr = pacchetto.getCollegamenti().iterator(); itr.hasNext();) {
			c = itr.next();
			if (c.getDataPartenza().equals(dataArrivo)) 
				andata = c;
			if (c.getDataPartenza().equals(dataPartenza))
				ritorno = c;
		}
		
		if (andata != null) {			
			//aggiorno il prezzo del pacchetto
			if (pacchetto.getTipoPacchetto() != TipoPacchetto.PREDEFINITO)
				pacchetto.setPrezzo(pacchetto.getPrezzo() - andata.getPrezzo()*pacchetto.getNumPartecipanti());
			pacchetto.removeCollegamento(andata);
		}
		if (ritorno != null) {
			//aggiorno il prezzo del pacchetto
			if (pacchetto.getTipoPacchetto() != TipoPacchetto.PREDEFINITO)
				pacchetto.setPrezzo(pacchetto.getPrezzo() - ritorno.getPrezzo()*pacchetto.getNumPartecipanti());
			pacchetto.removeCollegamento(ritorno);
		}
	}

	@Override
	public Pacchetti convertiInEntita (PacchettoDTO pacchetto) throws PacchettoInesistenteException {
		Pacchetti pacchettoEntity = em.find(Pacchetti.class, pacchetto.getId());
		if (pacchettoEntity != null)
			return pacchettoEntity;
		else
			throw new PacchettoInesistenteException();
	}

	@Override
	public Pacchetti convertiInEntita (int idPacchetto) throws PacchettoInesistenteException {
		Pacchetti pacchettoEntity = em.find(Pacchetti.class, idPacchetto);
		if (pacchettoEntity != null)
			return pacchettoEntity;
		else
			throw new PacchettoInesistenteException();
	}
	
	@Override
	public PacchettoDTO convertiInDTO (Pacchetti pacchetto) {
		PacchettoDTO pacchettoDTO = new PacchettoDTO();
		
		pacchettoDTO.setId(pacchetto.getId());
		pacchettoDTO.setNome(pacchetto.getNome());
		pacchettoDTO.setNumPartecipanti(pacchetto.getNumPartecipanti());
		pacchettoDTO.setPrezzo(pacchetto.getPrezzo());
		pacchettoDTO.setTipoPacchetto(pacchetto.getTipoPacchetto());
		List<DestinazioneDTO> destinazioni = new ArrayList<DestinazioneDTO>();
		for (Destinazioni d : pacchetto.getDestinazioni()) {
			destinazioni.add(this.destinazione.convertiInDTO(d));
			destinazioni.get(destinazioni.size() - 1).setPacchetto(pacchettoDTO);
		}
		pacchettoDTO.setDestinazioni(destinazioni);
		List<CollegamentoDTO> collegamenti = new ArrayList<CollegamentoDTO>();
		for (Collegamenti c : pacchetto.getCollegamenti()) {
			collegamenti.add(collegamento.convertiInDTO(c));
		}
		pacchettoDTO.setCollegamenti(collegamenti);
		pacchettoDTO.setCitta(citta.convertiInDTO(pacchetto.getCitta()));
		if (pacchetto.getPacchettoPredefinito() != null)
			pacchettoDTO.setPacchettoPredefinito(this.predefinito.convertiInDTO(pacchetto.getPacchettoPredefinito()));
		pacchettoDTO.setUtente(profilo.convertiInDTO(pacchetto.getUtente()));
		
		return pacchettoDTO;
	}

}
