package ejbs;

import interfaces.EmailBeanLocal;
import interfaces.GestoreCittaLocal;
import interfaces.GestoreCollegamentoLocal;
import interfaces.GestoreDestinazioneLocal;
import interfaces.GestoreEscursioneLocal;
import interfaces.GestoreHotelLocal;
import interfaces.GestorePacchettoPredefinitoLocal;
import interfaces.GestoreProfiloLocal;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import utils.DataUtils;
import dtos.AmicoDTO;
import dtos.AttivitaDTO;
import dtos.CollegamentoDTO;
import dtos.DestinazioneDTO;
import dtos.PacchettoDTO;
import dtos.PersonaDTO;
import eccezioni.AcquistoException;
import eccezioni.CittaInesistenteException;
import eccezioni.CollegamentoInesistenteException;
import eccezioni.DestinazioneInesistenteException;
import eccezioni.EscursioneInesistenteException;
import eccezioni.HotelInesistenteException;
import eccezioni.InsertException;
import eccezioni.PacchettoInesistenteException;
import entities.Amici;
import entities.Attivita;
import entities.AttivitaPred;
import entities.Collegamenti;
import entities.Destinazioni;
import entities.Pacchetti;
import entities.PacchettiPredefiniti;
import entities.Persone;
import enums.TipoPacchetto;

/**
 * Session Bean implementation class GestorePacchettoEJB
 */
@Stateless
public class GestorePacchettoEJB implements GestorePacchetto {

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
	
	@EJB
	private EmailBeanLocal emailBean;
	
	@Override
	public PacchettoDTO getPacchetto (int idPacchetto) throws PacchettoInesistenteException {
		Pacchetti pacchetto = this.convertiInEntita(idPacchetto);
		if (profilo.getUtente().equals(pacchetto.getUtente()))
			return this.convertiInDTO(pacchetto);
		else
			throw new PacchettoInesistenteException();
	}
	
	@Override
	public PacchettoDTO getPacchettoCondiviso (int idPacchetto, String email)  throws PacchettoInesistenteException {
		//controllo che il pacchetto sia stato effettivamente condiviso con l'amico
		TypedQuery<Amici> q = em.createNamedQuery("Amici.controlloCondivisione", Amici.class);
		q.setParameter("id", idPacchetto);
		q.setParameter("email", email);
		if (!q.getResultList().isEmpty())
			return this.convertiInDTO(this.convertiInEntita(idPacchetto));
		else
			throw new PacchettoInesistenteException();
	}
	
	public PacchettoDTO getPacchettoDipendente (int idPacchetto) throws PacchettoInesistenteException {
		return this.convertiInDTO(this.convertiInEntita(idPacchetto));
	}
	
	@Override
	public List<PacchettoDTO> elencoPacchetti(TipoPacchetto tipo) {		
		TypedQuery<Pacchetti> q = em.createNamedQuery("Pacchetti.getPacchettiPerTipo", Pacchetti.class);
		q.setParameter("utente", profilo.getUtente().getEmail());
		q.setParameter("tipo", tipo);
		List<Pacchetti> pacchetti = q.getResultList();
		List<PacchettoDTO> pacchettiDTO = new ArrayList<PacchettoDTO>();
		for (Pacchetti p : pacchetti) {
			pacchettiDTO.add(this.convertiInDTO(p));
		}
		return pacchettiDTO;
	}
	
	@Override
	public List<PacchettoDTO> elencoTrePacchetti(TipoPacchetto tipo) {		
		TypedQuery<Pacchetti> q = em.createNamedQuery("Pacchetti.getPacchettiPerTipo", Pacchetti.class);
		q.setParameter("utente", profilo.getUtente().getEmail());
		q.setParameter("tipo", tipo);
		List<Pacchetti> pacchetti = q.setMaxResults(3).getResultList();
		List<PacchettoDTO> pacchettiDTO = new ArrayList<PacchettoDTO>();
		for (Pacchetti p : pacchetti) {
			pacchettiDTO.add(this.convertiInDTO(p));
		}
		return pacchettiDTO;
	}
	
	@Override
	public List<PacchettoDTO> elencoPacchettiUtenti (TipoPacchetto tipo) {
		List<Pacchetti> pacchetti = new ArrayList<Pacchetti>();
		if (tipo == null) {
			TypedQuery<Pacchetti> q = em.createNamedQuery("Pacchetti.getPacchettiUtenti", Pacchetti.class);
			q.setParameter("tipo", TipoPacchetto.DACONFERMARE);
			TypedQuery<Pacchetti> q1 = em.createNamedQuery("Pacchetti.getPacchettiUtenti", Pacchetti.class);
			q1.setParameter("tipo", TipoPacchetto.ACQUISTATO);
			pacchetti.addAll(q.getResultList());
			pacchetti.addAll(q1.getResultList());
		} else {
			TypedQuery<Pacchetti> q = em.createNamedQuery("Pacchetti.getPacchettiUtenti", Pacchetti.class);
			q.setParameter("tipo", tipo);
			pacchetti.addAll(q.getResultList());
		}		
		List<PacchettoDTO> pacchettiDTO = new ArrayList<PacchettoDTO>();
		for (Pacchetti p : pacchetti) {
			pacchettiDTO.add(this.convertiInDTO(p));
		}
		return pacchettiDTO;		
	}
	
	@Override
	public int creaPacchettoPersonalizzato(PacchettoDTO pacchetto) throws CittaInesistenteException, HotelInesistenteException, InsertException {
		Pacchetti entity = new Pacchetti();
		
		entity.setNome("Nuovo pacchetto");
		entity.setNumPartecipanti(pacchetto.getNumPartecipanti());		
		entity.setTipoPacchetto(TipoPacchetto.PERSONALIZZATO);
		for (DestinazioneDTO d : pacchetto.getDestinazioni()) {
			entity.addDestinazione(this.destinazione.creaDestinazione(d));
		}
		entity.setCitta(this.citta.getCitta(pacchetto.getCitta().getNome()));	
		entity.setUtente(this.profilo.getUtente());
		entity.setPrezzo(this.calcolaPrezzo(entity));
		
		em.persist(entity);
		em.flush();
		
		return entity.getId();
	}

	@Override
	public void modificaNomePacchetto (PacchettoDTO pacchetto) throws PacchettoInesistenteException {
		Pacchetti entity = this.convertiInEntita(pacchetto);
		
		entity.setNome(pacchetto.getNome());
		
		em.merge(entity);
	}
	
	@Override
	public void modificaCittaPartenzaPacchetto (PacchettoDTO pacchetto) throws PacchettoInesistenteException, CittaInesistenteException {
		Pacchetti entity = this.convertiInEntita(pacchetto);
		
		//se viene cambiata la citt� di partenza rimuovo il primo e ultimo collegamento
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
	public int salvaPacchettoPredefinito (int idPacchetto,String cittaPartenza, String dataArrivo, int durata) throws PacchettoInesistenteException, CittaInesistenteException {
		PacchettiPredefiniti pred = predefinito.convertiInEntita(idPacchetto);		
			
		Pacchetti entity = new Pacchetti();
		Destinazioni destinazione = new Destinazioni();
		
		entity.setNome(pred.getNome());			
		entity.setNumPartecipanti(1);
		entity.setTipoPacchetto(TipoPacchetto.PREDEFINITO);
		entity.setPacchettoPredefinito(pred);
		entity.setCitta(citta.getCitta(cittaPartenza));
		entity.setUtente(this.profilo.getUtente());
		
		destinazione.setDataArrivo(DataUtils.parseData(dataArrivo));
		destinazione.setDataPartenza(DataUtils.sommaGiorni(destinazione.getDataArrivo(), durata));
		destinazione.setHotel(pred.getHotel());
		destinazione.setCitta(pred.getHotel().getCitta());
		for (AttivitaPred a : pred.getAttivita()) {
			if (a.getEscursione().getData().after(destinazione.getDataArrivo()) && a.getEscursione().getData().before(destinazione.getDataPartenza())) {
				Attivita attivita = new Attivita();
				attivita.setEscursione(a.getEscursione());
				attivita.setNumPartecipanti(1);
				destinazione.addAttivita(attivita);
			}
		}
		entity.addDestinazione(destinazione);	
		
		entity.setPrezzo(this.calcolaPrezzoPredefinito(entity));
		
		em.persist(entity);
		em.flush();
		
		return entity.getId();
	}

	@Override
	public void acquistaPacchetto(int idPacchetto, List<PersonaDTO> partecipanti) throws PacchettoInesistenteException, AcquistoException, MessagingException {
		Pacchetti entity = this.convertiInEntita(idPacchetto);
		
		//verifico che l'utente connesso sia il proprietario del pacchetto
		if (entity.getUtente().equals(profilo.getUtente())) {		
			//controllo che il pacchetto sia completo
			int numeroDestinazioni = entity.getDestinazioni().size();
			int numeroCollegamenti = entity.getCollegamenti().size();
			
			if (numeroCollegamenti == numeroDestinazioni + 1) {
				//aggiungo i dati dei partecipanti nel pacchetto
				if (entity.getNumPartecipanti() == partecipanti.size() + 1) {
					for (PersonaDTO p : partecipanti) {
						entity.addPartecipante(profilo.creaPersona(p));
					}			
					entity.setTipoPacchetto(TipoPacchetto.DACONFERMARE);					
					em.merge(entity);
					
					emailBean.confermaAcquisto(entity.getUtente().getEmail(), entity.getNome());
				} else
					throw new AcquistoException("Errore: il numero di partecipanti non combacia con il numero di partecipanti del pacchetto");
			} else
				throw new AcquistoException("Il pacchetto � incompleto");
		} else
			throw new AcquistoException("Acquisto non consentito");
	}
	
	@Override
	public void confermaPacchetto (int idPacchetto) throws PacchettoInesistenteException {
		Pacchetti pacchetto = this.convertiInEntita(idPacchetto);
		pacchetto.setTipoPacchetto(TipoPacchetto.ACQUISTATO);
		em.merge(pacchetto);
	}

	@Override
	public void condividiPacchetto(PacchettoDTO pacchetto, AmicoDTO datiAmico) throws CittaInesistenteException, HotelInesistenteException, EscursioneInesistenteException, CollegamentoInesistenteException, MessagingException, PacchettoInesistenteException {
		Amici amico = new Amici();
		//verifico se l'indirizzo email � gi� esistente
		TypedQuery<Amici> q = em.createNamedQuery("Amici.getAmico", Amici.class);
		q.setParameter("email", datiAmico.getEmail());
		if (q.getResultList().isEmpty()) {
			amico.setEmail(datiAmico.getEmail());
			amico.setNome(datiAmico.getNome());
			amico.setCognome(datiAmico.getCognome());			
		} else {
			amico = q.getResultList().get(0);
		}
		
		Pacchetti entity = new Pacchetti();
		
		entity.setNome(pacchetto.getNome());
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
		entity.setUtente(profilo.getUtente());
		if (pacchetto.getTipoPacchetto() == TipoPacchetto.PREDEFINITO)
			entity.setPacchettoPredefinito(predefinito.convertiInEntita(pacchetto.getPacchettoPredefinito()));
		
		em.persist(entity);
		
		amico.addPacchetto(entity);	
		
		em.merge(amico);
		em.flush();
		
		emailBean.condividiPacchetto(pacchetto.getUtente().getPersona().getNome() != null ? pacchetto.getUtente().getPersona().getNome() : pacchetto.getUtente().getEmail(), amico.getEmail(), entity.getId());
	}
	
	@Override
	public int salvaPacchettoCondiviso (PacchettoDTO pacchetto) throws CittaInesistenteException, HotelInesistenteException, EscursioneInesistenteException, CollegamentoInesistenteException, PacchettoInesistenteException {
		Pacchetti entity = new Pacchetti();
		
		entity.setNome(pacchetto.getNome());
		entity.setNumPartecipanti(pacchetto.getNumPartecipanti());	
		if (pacchetto.getPacchettoPredefinito() != null) {
			entity.setPacchettoPredefinito(predefinito.convertiInEntita(pacchetto.getPacchettoPredefinito()));
			entity.setTipoPacchetto(TipoPacchetto.PREDEFINITO);
		} else
			entity.setTipoPacchetto(TipoPacchetto.PERSONALIZZATO);
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
		entity.setCitta(this.citta.getCitta(pacchetto.getCitta().getNome()));	
		entity.setUtente(this.profilo.getUtente());
		entity.setPrezzo(pacchetto.getPrezzo());
		
		em.persist(entity);
		em.flush();
		
		return entity.getId();		
	}

	@Override
	public void eliminaPacchetto(PacchettoDTO pacchetto) throws PacchettoInesistenteException {
		Pacchetti entity = this.convertiInEntita(pacchetto);
		if (pacchetto.getTipoPacchetto() == TipoPacchetto.CONDIVISO) {
			//rimuovo tutte le condivisioni del pacchetto
			TypedQuery<Amici> q = em.createNamedQuery("Amici.getPacchetti", Amici.class);
			q.setParameter("id", pacchetto.getId());
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
		int numeroDestinazioni = entity.getDestinazioni().size();
		
		boolean checkDate = false;
		boolean checkDest = true;
		
		//controllo le date della destinazione
		if (destinazione.getDataPartenza().equals(entity.getDestinazioni().get(0).getDataArrivo())) {
			checkDate = true;
		} else if (destinazione.getDataArrivo().equals(entity.getDestinazioni().get(numeroDestinazioni - 1).getDataPartenza())) {
			checkDate = true;
		} else {
			for (int i = 0; i < numeroDestinazioni - 1; i++) {
				if (destinazione.getDataArrivo().equals(entity.getDestinazioni().get(i).getDataPartenza()) && destinazione.getDataPartenza().equals(entity.getDestinazioni().get(i + 1).getDataArrivo()) && !entity.getDestinazioni().get(i).getDataPartenza().equals(entity.getDestinazioni().get(i + 1).getDataArrivo())) {
					checkDate = true;
					break;
				}
			}
		}
		
		//controllo che la destinazione non sia gi� stata inserita
		for (Destinazioni d : entity.getDestinazioni()) {
			if (destinazione.getCitta().getNome().equals(d.getCitta().getNome())) {
				checkDest = false;
				break;
			}
		}
		
		//controllo che la destinazione non sia gi� stata inserita
		if (checkDest) {
			//controllo le date della destinazione
			if (checkDate) {
				//impedisco all'utente di selezionare un hotel nella stessa citt� di partenza
				if (!destinazione.getCitta().getNome().equalsIgnoreCase(entity.getCitta().getNome())) {
					//controllo che la data di arrivo sia minore della data di partenza dalla destinazione
					if (destinazione.getDataArrivo().before(destinazione.getDataPartenza())) {			
						entity.addDestinazione(this.destinazione.creaDestinazione(destinazione));					
						this.rimuoviCollegamenti(entity, destinazione.getDataArrivo(), destinazione.getDataPartenza());
						//aggiorno il prezzo del pacchetto
						entity.setPrezzo(this.calcolaPrezzo(entity));
						
						em.merge(entity);
					} else
						throw new InsertException("La data di partenza deve essere precedente alla data di ritorno");
				} else
					throw new InsertException("La citt� di partenza e la destinazione non possono essere uguali");
			} else
				throw new InsertException("Date non valide");
		} else
			throw new InsertException("Destinazione gi� inserita");
	}
	
	@Override
	public void modificaDataArrivo (PacchettoDTO pacchetto, DestinazioneDTO destinazione) throws PacchettoInesistenteException, DestinazioneInesistenteException {
		Pacchetti entity = this.convertiInEntita(pacchetto);
		
		Date vecchiaData = this.destinazione.modificaDataArrivo(destinazione);
		
		//rimuovo i collegamenti non pi� coerenti
		this.rimuoviCollegamenti(entity, vecchiaData, vecchiaData);
		
		//aggiorno il prezzo del pacchetto
		if (entity.getTipoPacchetto() == TipoPacchetto.PERSONALIZZATO)
			entity.setPrezzo(this.calcolaPrezzo(entity));
		else
			entity.setPrezzo(this.calcolaPrezzoPredefinito(entity));
		
		em.merge(entity);		
	}	
	
	@Override
	public void modificaDataPartenza (PacchettoDTO pacchetto, DestinazioneDTO destinazione) throws PacchettoInesistenteException, DestinazioneInesistenteException {
		Pacchetti entity = this.convertiInEntita(pacchetto);
		
		Date vecchiaData = this.destinazione.modificaDataPartenza(destinazione);
		
		//rimuovo i collegamenti non pi� coerenti
		this.rimuoviCollegamenti(entity, vecchiaData, vecchiaData);
		
		//aggiorno il prezzo del pacchetto
		if (entity.getTipoPacchetto() == TipoPacchetto.PERSONALIZZATO)
			entity.setPrezzo(this.calcolaPrezzo(entity));
		else
			entity.setPrezzo(this.calcolaPrezzoPredefinito(entity));
		
		em.merge(entity);
	}
	
	@Override
	public void eliminaDestinazione(PacchettoDTO pacchetto, DestinazioneDTO destinazione) throws DestinazioneInesistenteException, PacchettoInesistenteException {
		Pacchetti entity = this.convertiInEntita(pacchetto);	
		
		entity.removeDestinazione(this.destinazione.convertiInEntita(destinazione));	
		//rimuovo gli eventuali collegamenti collegati
		this.rimuoviCollegamenti(entity, destinazione.getDataArrivo(), destinazione.getDataPartenza());
		//aggiorno il prezzo del pacchetto
		entity.setPrezzo(this.calcolaPrezzo(entity));
		
		em.merge(entity);
	}

	@Override
	public void aggiuntaCollegamento(int idPacchetto, CollegamentoDTO collegamento) throws CollegamentoInesistenteException, PacchettoInesistenteException, InsertException {
		Pacchetti entity = this.convertiInEntita(idPacchetto);
		boolean check = false;
		
		//controllo che il collegamento che si vuole aggiungere sia coerente col pacchetto
		for (Destinazioni d : entity.getDestinazioni()) {			
			if (entity.getDestinazioni().indexOf(d) == 0 && collegamento.getDataPartenza().equals(d.getDataArrivo()) && collegamento.getCittaPartenza().getNome().equals(entity.getCitta().getNome()) && collegamento.getCittaArrivo().getNome().equals(d.getCitta().getNome())) {
				check = true;
				break;
			} else if (entity.getDestinazioni().indexOf(d) > 0 && collegamento.getDataPartenza().equals(d.getDataArrivo()) && collegamento.getCittaPartenza().getNome().equals(entity.getDestinazioni().get(entity.getDestinazioni().indexOf(d) - 1).getCitta().getNome()) && collegamento.getCittaArrivo().getNome().equals(d.getCitta().getNome())) {
				check = true;
				break;
			}  else if ((entity.getDestinazioni().indexOf(d) == entity.getDestinazioni().size() - 1) && collegamento.getDataPartenza().equals(d.getDataPartenza()) && collegamento.getCittaPartenza().getNome().equals(d.getCitta().getNome()) && collegamento.getCittaArrivo().getNome().equals(entity.getCitta().getNome())) {
				check = true;
				break;
			}
		}
	
		if (check) {
			//rimuovo l'eventuale collegamento gi� presente nella stessa data
			for (Collegamenti c : entity.getCollegamenti()) {
				if (collegamento.getDataPartenza().compareTo(c.getDataPartenza()) == 0) {
					entity.removeCollegamento(c);
					break;
				}
			}
			
			entity.addCollegamento(this.collegamento.convertiInEntita(collegamento));
			//aggiorno il prezzo del pacchetto
			if (entity.getTipoPacchetto() != TipoPacchetto.PREDEFINITO)
				entity.setPrezzo(this.calcolaPrezzo(entity));
			
			em.merge(entity);
		} else
			throw new InsertException();
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
		
		for (Attivita a : pacchetto.getDestinazioni().get(0).getAttivita()) {
			totale += a.getEscursione().getPrezzo()*a.getNumPartecipanti();
		}
		
		return totale;
	}
	
	/**
	 * Si occupa di rimuovere i collegemnti non pi� coerenti.
	 * Questo pu� accadere in caso di aggiunta nuova destinazione, modifica date di una destinazione o eliminazione di una destinazione.
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
			if (dataArrivo.compareTo(dataPartenza) != 0 && c.getDataPartenza().equals(dataPartenza))
				ritorno = c;
		}		

		if (andata != null)			
			pacchetto.removeCollegamento(andata);
		if (ritorno != null)
			pacchetto.removeCollegamento(ritorno);
	}

	/**
	 * Permette la conversione da un DTO alla rispettiva entit�
	 * @param pacchetto Il DTO del pacchetto
	 * @return L'entit� desiderata
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 */
	private Pacchetti convertiInEntita (PacchettoDTO pacchetto) throws PacchettoInesistenteException {
		Pacchetti pacchettoEntity = em.find(Pacchetti.class, pacchetto.getId());
		if (pacchettoEntity != null)
			return pacchettoEntity;
		else
			throw new PacchettoInesistenteException();
	}

	/**
	 * Permette la conversione da un DTO alla rispettiva entit�
	 * @param idPacchetto L'identificativo del pacchetto
	 * @return L'entit� desiderata
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 */
	private Pacchetti convertiInEntita (int idPacchetto) throws PacchettoInesistenteException {
		Pacchetti pacchettoEntity = em.find(Pacchetti.class, idPacchetto);
		if (pacchettoEntity != null)
			return pacchettoEntity;
		else
			throw new PacchettoInesistenteException();
	}
	
    /**
     * Permette la conversione da un'entit� al rispettivo DTO
     * @param pacchetto L'entit� di partenza
     * @return Il relativo DTO
     */	
	private PacchettoDTO convertiInDTO (Pacchetti pacchetto) {
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
		List<PersonaDTO> part = new ArrayList<PersonaDTO>();
		for (Persone p : pacchetto.getDatiPartecipanti()) {
			part.add(profilo.convertiInDTO(p));
		}
		pacchettoDTO.setDatiPartecipanti(part);
		
		return pacchettoDTO;
	}

}
