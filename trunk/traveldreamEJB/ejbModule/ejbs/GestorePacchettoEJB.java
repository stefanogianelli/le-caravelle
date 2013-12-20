package ejbs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dtos.CollegamentoDTO;
import dtos.DestinazioneDTO;
import dtos.PacchettoDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.CollegamentoInesistenteException;
import entities.Amici;
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
	public void creaPacchettoPersonalizzato(PacchettoDTO pacchetto) throws CittaInesistenteException {
		Pacchetti entity = new Pacchetti();
		
		entity.setNome(pacchetto.getNome());
		entity.setNumPartecipanti(pacchetto.getNumPartecipanti());
		entity.setPrezzo(pacchetto.getPrezzo());
		entity.setTipoPacchetto(TipoPacchetto.PERSONALIZZATO);
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
	public void salvaPacchetto(PacchettoDTO pacchetto) throws CittaInesistenteException {
		Pacchetti entity = this.convertiInEntita(pacchetto);
		
		entity.setNome(pacchetto.getNome());
		entity.setNumPartecipanti(pacchetto.getNumPartecipanti());
		entity.setPrezzo(pacchetto.getPrezzo());
		entity.setCitta(this.citta.convertiInEntita(pacchetto.getCitta()));
		
		em.merge(entity);
	}
	
	@Override
	public void salvaPacchettoPredefinito (PacchettoDTO pacchetto) throws CittaInesistenteException {
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
	public void acquistaPacchetto(PacchettoDTO pacchetto) {
		Pacchetti entity = this.convertiInEntita(pacchetto);
		
		entity.setTipoPacchetto(TipoPacchetto.ACQUISTATO);
		
		em.merge(entity);
	}

	@Override
	public void condividiPacchetto(PacchettoDTO pacchetto, String email) throws CittaInesistenteException {
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
		entity.setPacchettoPredefinito(this.predefinito.convertiInEntita(pacchetto.getPacchettoPredefinito()));
		entity.setUtente(this.profilo.convertiInEntita(pacchetto.getUtente()));
		
		amico.addPacchetto(entity);	
		
		em.persist(amico);
	}

	@Override
	public void eliminaPacchetto(PacchettoDTO pacchetto) {
		em.remove(this.convertiInEntita(pacchetto));		
	}

	@Override
	public void aggiuntaDestinazione(PacchettoDTO pacchetto, DestinazioneDTO destinazione) throws CittaInesistenteException {
		Pacchetti entity = this.convertiInEntita(pacchetto);
		
		entity.addDestinazione(this.destinazione.creaDestinazione(destinazione));
		
		em.merge(entity);
	}

	@Override
	public void eliminaDestinazione(PacchettoDTO pacchetto, DestinazioneDTO destinazione) {
		Pacchetti entity = this.convertiInEntita(pacchetto);
		
		entity.removeDestinazione(this.destinazione.convertiInEntita(destinazione));
		
		em.merge(entity);
	}

	@Override
	public void aggiuntaCollegamento(PacchettoDTO pacchetto, CollegamentoDTO collegamento) throws CollegamentoInesistenteException {
		Pacchetti entity = this.convertiInEntita(pacchetto);		
	
		entity.addCollegamento(this.collegamento.convertiInEntita(collegamento));
		
		em.merge(entity);
	}

	@Override
	public void modificaCollegamento(PacchettoDTO pacchetto, CollegamentoDTO collegamento) throws CollegamentoInesistenteException {
		Pacchetti entity = this.convertiInEntita(pacchetto);
		
		entity.removeCollegamento(this.collegamento.convertiInEntita(collegamento));
		
		em.merge(entity);
	}

	/**
	 * Permette la conversione da un DTO alla rispettiva entità
	 * @param pacchetto Il DTO del pacchetto
	 * @return L'entità desiderata
	 */
	protected Pacchetti convertiInEntita (PacchettoDTO pacchetto) {
		return em.find(Pacchetti.class, pacchetto.getId());
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
