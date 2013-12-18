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

    /**
     * Mostra l'elenco dei pacchetti per tipologia
     * @param tipo La tipologia di pacchetto da cercare
     * @return L'elenco dei pacchetti
     */
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
	
	/**
	 * Permette la creazione di un nuovo pacchetto personalizzato
	 * @param pacchetto I dati del pacchetto
	 */
	@Override
	public void creaPacchettoPersonalizzato(PacchettoDTO pacchetto) {
		Pacchetti pacchettoDAO = new Pacchetti();
		
		pacchettoDAO.setNome(pacchetto.getNome());
		pacchettoDAO.setNumPartecipanti(pacchetto.getNumPartecipanti());
		pacchettoDAO.setPrezzo(pacchetto.getPrezzo());
		pacchettoDAO.setTipoPacchetto(pacchetto.getTipoPacchetto());
		List<Destinazioni> destinazioni = new ArrayList<Destinazioni>();
		for (DestinazioneDTO d : pacchetto.getDestinazioni()) {
			destinazioni.add(this.destinazione.convertiInDAO(d));
		}
		pacchettoDAO.setDestinazioni(destinazioni);
		pacchettoDAO.setCitta(this.citta.convertiInDAO(pacchetto.getCitta()));
		pacchettoDAO.setPacchettoPredefinito(this.predefinito.convertiInDAO(pacchetto.getPacchettoPredefinito()));
		pacchettoDAO.setUtente(this.profilo.convertiInDAO(pacchetto.getUtente()));
		
		em.persist(pacchettoDAO);
	}

	/**
	 * Permette il salvataggio di un pacchetto personalizzato
	 * @param I dati del pacchetto
	 */
	@Override
	public void salvaPacchetto(PacchettoDTO pacchetto) {
		Pacchetti pacchettoDAO = this.convertiInDAO(pacchetto);
		
		pacchettoDAO.setNome(pacchetto.getNome());
		pacchettoDAO.setNumPartecipanti(pacchetto.getNumPartecipanti());
		pacchettoDAO.setPrezzo(pacchetto.getPrezzo());
		pacchettoDAO.setTipoPacchetto(pacchetto.getTipoPacchetto());
		List<Destinazioni> destinazioni = new ArrayList<Destinazioni>();
		for (DestinazioneDTO d : pacchetto.getDestinazioni()) {
			destinazioni.add(this.destinazione.convertiInDAO(d));
		}
		pacchettoDAO.setDestinazioni(destinazioni);
		pacchettoDAO.setCitta(this.citta.convertiInDAO(pacchetto.getCitta()));
		
		em.merge(pacchettoDAO);			
	}

	/**
	 * Permette l'acquisto di un pacchetto
	 * @param pacchetto Il pacchetto da acquistare
	 */
	@Override
	public void acquistaPacchetto(PacchettoDTO pacchetto) {
		Pacchetti pacchettoDAO = this.convertiInDAO(pacchetto);
		
		pacchettoDAO.setTipoPacchetto(TipoPacchetto.ACQUISTATO);
		
		em.merge(pacchettoDAO);
	}

	/**
	 * Permette la condivisione di un pacchetto
	 * @param pacchetto Il pacchetto da condividere
	 * @param email L'indirizzo email dell'amico con cui condividere il paccheto
	 */
	@Override
	public void condividiPacchetto(PacchettoDTO pacchetto, String email) {
		Amici amico = em.find(Amici.class, email);
		
		amico.addPacchetto(this.convertiInDAO(pacchetto));	
		
		em.persist(amico);
	}

	/**
	 * Permette l'eliminazione di un pacchetto
	 * @param pacchetto Il pacchetto da eliminare
	 */
	@Override
	public void eliminaPacchetto(PacchettoDTO pacchetto) {
		em.remove(this.convertiInDAO(pacchetto));		
	}

	/**
	 * Permette l'aggiunta di una nuova destinazione nel pacchetto
	 * @param pacchetto Il pacchetto nel quale aggiungere la destinazione
	 * @param destinazione La destinazione da aggiungere
	 */
	@Override
	public void aggiuntaDestinazione(PacchettoDTO pacchetto, DestinazioneDTO destinazione) {
		Pacchetti pacchettoDAO = this.convertiInDAO(pacchetto);
		
		pacchettoDAO.addDestinazione(this.destinazione.creaDestinazione(destinazione));
		
		em.merge(pacchettoDAO);
	}

	/**
	 * Permette l'eliminazione di una destinazione da un pacchetto
	 * @param pacchetto Il pacchetto nel quale si vuole eliminare la destinazione
	 * @param destinazione La destinazione da eliminare
	 */
	@Override
	public void eliminaDestinazione(PacchettoDTO pacchetto, DestinazioneDTO destinazione) {
		Pacchetti pacchettoDAO = this.convertiInDAO(pacchetto);
		
		pacchettoDAO.removeDestinazione(this.destinazione.convertiInDAO(destinazione));
		
		em.merge(pacchettoDAO);
	}

	/**
	 * Permette l'aggiunta di un collegamento nel pacchetto
	 * @param pacchetto Il pacchetto nel quale aggiungere il collegamento
	 * @param collegamento Il collegamento selezionato
	 */
	@Override
	public void aggiuntaCollegamento(PacchettoDTO pacchetto, CollegamentoDTO collegamento) {
		Pacchetti pacchettoDAO = this.convertiInDAO(pacchetto);
		
		pacchettoDAO.addCollegamento(this.collegamento.convertiInDAO(collegamento));
		
		em.merge(pacchettoDAO);
	}

	/**
	 * Permette l'eliminazione di un collegamento dal pacchetto
	 * @param pacchetto Il pacchetto dal quale di vuole rimuovere il collegamento
	 * @param collegamento Il collegamento da rimuovere
	 */
	@Override
	public void modificaCollegamento(PacchettoDTO pacchetto, CollegamentoDTO collegamento) {
		Pacchetti pacchettoDAO = this.convertiInDAO(pacchetto);
		
		pacchettoDAO.removeCollegamento(this.collegamento.convertiInDAO(collegamento));
		
		em.merge(pacchettoDAO);	
	}

	/**
	 * Permette la conversione da un DTO alla rispettiva entità
	 * @param pacchetto Il DTO del pacchetto
	 * @return L'entità desiderata
	 */
	protected Pacchetti convertiInDAO (PacchettoDTO pacchetto) {
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
		pacchettoDTO.setTipoPacchetto(pacchetto.getTipoPacchetto());
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
