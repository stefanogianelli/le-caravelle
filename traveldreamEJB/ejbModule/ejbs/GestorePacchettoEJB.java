package ejbs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dtos.CollegamentoDTO;
import dtos.DestinazioneDTO;
import dtos.PacchettoDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.CollegamentoInesistenteException;
import eccezioni.DestinazioneInesistenteException;
import eccezioni.HotelInesistenteException;
import eccezioni.PacchettoInesistenteException;
import entities.Amici;
import entities.Collegamenti;
import entities.Destinazioni;
import entities.Pacchetti;
import enums.TipoPacchetto;

/**
 * Session Bean implementation class GestorePacchettoEJB
 */
@Stateless
public class GestorePacchettoEJB implements GestorePacchetto {

	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private GestoreHotelEJB hotel;
	
	@EJB
	private GestoreDestinazioneEJB destinazione;
	
	@EJB
	private GestoreCollegamentoEJB collegamento;
	
	@EJB
	private GestoreCittaEJB citta;
	
	@EJB
	private GestoreProfiloEJB profilo;
	
	@EJB
	private GestorePacchettoPredefinitoEJB predefinito;
	
    /**
     * Default constructor. 
     */
    public GestorePacchettoEJB() {
        
    }

	@Override
	public List<PacchettoDTO> elencoPacchetti(TipoPacchetto tipo) {		
		Query q = em.createNamedQuery("Pacchetti.getPacchettiPerTipo", Pacchetti.class);
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
	public void creaPacchettoPersonalizzato(PacchettoDTO pacchetto) throws CittaInesistenteException, HotelInesistenteException, EntityExistsException {
		Pacchetti entity = new Pacchetti();
		
		entity.setNome(pacchetto.getNome());
		entity.setNumPartecipanti(pacchetto.getNumPartecipanti());
		//TODO: implementare funzione per il calcolo del prezzo
		entity.setPrezzo(pacchetto.getPrezzo());
		entity.setTipoPacchetto(TipoPacchetto.PERSONALIZZATO);
		List<Destinazioni> destinazioni = new ArrayList<Destinazioni>();
		for (DestinazioneDTO d : pacchetto.getDestinazioni()) {
			destinazioni.add(this.destinazione.creaDestinazione(d));
		}
		entity.setDestinazioni(destinazioni);		
		entity.setCitta(this.citta.convertiInEntita(pacchetto.getCitta()));	
		entity.setUtente(this.profilo.convertiInEntita(pacchetto.getUtente()));
		
		em.persist(entity);
	}

	@Override
	public void salvaPacchetto(PacchettoDTO pacchetto) throws CittaInesistenteException, PacchettoInesistenteException {
		Pacchetti entity = this.convertiInEntita(pacchetto);
		
		entity.setNome(pacchetto.getNome());
		entity.setNumPartecipanti(pacchetto.getNumPartecipanti());
		entity.setPrezzo(pacchetto.getPrezzo());
		entity.setCitta(this.citta.convertiInEntita(pacchetto.getCitta()));
		
		em.merge(entity);
	}
	
	@Override
	public void salvaPacchettoPredefinito (PacchettoDTO pacchetto) throws CittaInesistenteException, HotelInesistenteException, PacchettoInesistenteException, EntityExistsException {
		Pacchetti entity = new Pacchetti();
		
		entity.setNome(pacchetto.getNome());
		entity.setNumPartecipanti(pacchetto.getNumPartecipanti());
		entity.setPrezzo(pacchetto.getPrezzo());
		entity.setTipoPacchetto(TipoPacchetto.PREDEFINITO);
		List<Destinazioni> destinazioni = new ArrayList<Destinazioni>();
		for (DestinazioneDTO d : pacchetto.getDestinazioni()) {
			destinazioni.add(this.destinazione.creaDestinazione(d));
		}
		entity.setDestinazioni(destinazioni);
		entity.setCitta(this.citta.convertiInEntita(pacchetto.getCitta()));
		entity.setPacchettoPredefinito(this.predefinito.convertiInEntita(pacchetto.getPacchettoPredefinito()));
		entity.setUtente(this.profilo.convertiInEntita(pacchetto.getUtente()));
		
		em.persist(entity);
	}

	@Override
	public void acquistaPacchetto(PacchettoDTO pacchetto) throws PacchettoInesistenteException {
		Pacchetti entity = this.convertiInEntita(pacchetto);
		
		entity.setTipoPacchetto(TipoPacchetto.ACQUISTATO);
		
		em.merge(entity);
	}

	@Override
	public void condividiPacchetto(PacchettoDTO pacchetto, String email) throws CittaInesistenteException, HotelInesistenteException {
		Amici amico = em.find(Amici.class, email);
		
		Pacchetti entity = new Pacchetti();
		
		entity.setNome(pacchetto.getNome());
		entity.setNumPartecipanti(pacchetto.getNumPartecipanti());
		entity.setPrezzo(pacchetto.getPrezzo());
		entity.setTipoPacchetto(TipoPacchetto.CONDIVISO);
		List<Destinazioni> destinazioni = new ArrayList<Destinazioni>();
		for (DestinazioneDTO d : pacchetto.getDestinazioni()) {
			destinazioni.add(this.destinazione.creaDestinazione(d));
		}
		entity.setDestinazioni(destinazioni);
		entity.setCitta(this.citta.convertiInEntita(pacchetto.getCitta()));
		entity.setUtente(this.profilo.convertiInEntita(pacchetto.getUtente()));
		
		amico.addPacchetto(entity);	
		
		em.persist(amico);
	}

	@Override
	public void eliminaPacchetto(PacchettoDTO pacchetto) throws PacchettoInesistenteException {
		em.remove(this.convertiInEntita(pacchetto));		
	}

	@Override
	public void aggiuntaDestinazione(PacchettoDTO pacchetto, DestinazioneDTO destinazione) throws CittaInesistenteException, HotelInesistenteException, PacchettoInesistenteException {
		Pacchetti entity = this.convertiInEntita(pacchetto);
		
		entity.addDestinazione(this.destinazione.creaDestinazione(destinazione));
		
		this.rimuoviCollegamenti(entity, destinazione.getDataArrivo(), destinazione.getDataPartenza());
		
		em.merge(entity);
	}
	
	@Override
	public void modificaDestinazione(PacchettoDTO pacchetto, DestinazioneDTO destinazione) throws PacchettoInesistenteException, CittaInesistenteException, HotelInesistenteException {
		Pacchetti entity = this.convertiInEntita(pacchetto);
		
		this.destinazione.modificaDatiDestinazione(destinazione);
		
		this.rimuoviCollegamenti(entity, destinazione.getDataArrivo(), destinazione.getDataPartenza());
		
		em.merge(entity);
	}

	@Override
	public void eliminaDestinazione(PacchettoDTO pacchetto, DestinazioneDTO destinazione) throws DestinazioneInesistenteException, PacchettoInesistenteException {
		Pacchetti entity = this.convertiInEntita(pacchetto);
		
		entity.removeDestinazione(this.destinazione.convertiInEntita(destinazione));
		
		this.rimuoviCollegamenti(entity, destinazione.getDataArrivo(), destinazione.getDataPartenza());
		
		em.merge(entity);
	}

	@Override
	public void aggiuntaCollegamento(PacchettoDTO pacchetto, CollegamentoDTO collegamento) throws CollegamentoInesistenteException, PacchettoInesistenteException {
		Pacchetti entity = this.convertiInEntita(pacchetto);		
	
		entity.addCollegamento(this.collegamento.convertiInEntita(collegamento));
		
		em.merge(entity);
	}

	@Override
	public void modificaCollegamento(PacchettoDTO pacchetto, CollegamentoDTO collegamento) throws CollegamentoInesistenteException, PacchettoInesistenteException {
		Pacchetti entity = this.convertiInEntita(pacchetto);
		
		Query q = em.createNamedQuery("Collegamenti.getCollegamentoDaData", Collegamenti.class);
		q.setParameter("data", collegamento.getDataPartenza());
		Collegamenti vecchioCollegamento = (Collegamenti) q.getSingleResult();
		
		entity.removeCollegamento(vecchioCollegamento);
		entity.addCollegamento(this.collegamento.convertiInEntita(collegamento));
		
		em.merge(entity);
	}
	
	/**
	 * Si occupa di rimuovere i collegemnti non più coerenti.
	 * Questo può accadere in caso di aggiunta nuova destinazione, modifica date di una destinazione o eliminazione di una destinazione.
	 * @param pacchetto Il pacchetto nel quale rimuovere il/i collegamento/i
	 * @param dataArrivo La data nella quale rimuovere il collegamento di andata
	 * @param dataPartenza La data nella quale rimuovere il collegamento di ritorno
	 */
	protected void rimuoviCollegamenti (Pacchetti pacchetto, Date dataArrivo, Date dataPartenza) {
		Collegamenti andata, ritorno;
		
		Query q = em.createNamedQuery("Collegamenti.getCollegamentoDaData", Collegamenti.class);
		q.setParameter("data", dataArrivo);
		try {
			andata = (Collegamenti) q.getSingleResult();
		} catch (NoResultException e) {
			andata = null;
		}
		
		Query q1 = em.createNamedQuery("Collegamenti.getCollegamentoDaData", Collegamenti.class);
		q1.setParameter("data", dataPartenza);
		try {
			ritorno = (Collegamenti) q1.getSingleResult();
		} catch (NoResultException e) {
			ritorno = null;
		}
		
		if (andata != null)
			pacchetto.removeCollegamento(andata);
		if (ritorno != null)
			pacchetto.removeCollegamento(ritorno);
		
		em.merge(pacchetto);
	}

	/**
	 * Permette la conversione da un DTO alla rispettiva entità
	 * @param pacchetto Il DTO del pacchetto
	 * @return L'entità desiderata
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 */
	protected Pacchetti convertiInEntita (PacchettoDTO pacchetto) throws PacchettoInesistenteException {
		Pacchetti pacchettoEntity = em.find(Pacchetti.class, pacchetto.getId());
		if (pacchettoEntity != null)
			return pacchettoEntity;
		else
			throw new PacchettoInesistenteException();
	}
	
	/**
	 * Permette la conversione da un'entità al rispettivo DTO
	 * @param pacchetto L'entità di partenza
	 * @return Il relativo DTO
	 */
	protected PacchettoDTO convertiInDTO (Pacchetti pacchetto) {
		PacchettoDTO pacchettoDTO = new PacchettoDTO();
		
		pacchettoDTO.setId(pacchetto.getId());
		pacchettoDTO.setNome(pacchetto.getNome());
		pacchettoDTO.setNumPartecipanti(pacchetto.getNumPartecipanti());
		pacchettoDTO.setPrezzo(pacchetto.getPrezzo());
		List<DestinazioneDTO> destinazioni = new ArrayList<DestinazioneDTO>();
		for (Destinazioni d : pacchetto.getDestinazioni()) {
			destinazioni.add(this.destinazione.convertiInDTO(d));
		}
		pacchettoDTO.setDestinazioni(destinazioni);
		pacchettoDTO.setCitta(citta.convertiInDTO(pacchetto.getCitta()));
		pacchettoDTO.setPacchettoPredefinito(this.predefinito.convertiInDTO(pacchetto.getPacchettoPredefinito()));
		pacchettoDTO.setUtente(profilo.convertiInDTO(pacchetto.getUtente()));
		
		return pacchettoDTO;
	}

}
