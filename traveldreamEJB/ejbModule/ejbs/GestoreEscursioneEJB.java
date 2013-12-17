package ejbs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dtos.EscursioneDTO;
import entities.Escursioni;

/**
 * Session Bean implementation class GestoreEscursioneEJB
 */
@Stateless
public class GestoreEscursioneEJB implements GestoreEscursione {

	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private GestoreCittaEJB citta;
	
    /**
     * Default constructor. 
     */
    public GestoreEscursioneEJB() {
    
    }
    
    /**
     * Mostra l'elenco di tutte escursioni presenti nel database
     * @return L'elenco delle escursioni
     */
	@Override
	public List<EscursioneDTO> elencoEscursioni() {
		List<Escursioni> escursioni = em.createNamedQuery("Escursioni.elenco", Escursioni.class).getResultList();
		List<EscursioneDTO> dto = new ArrayList<EscursioneDTO>();
		for (Escursioni e : escursioni) {
			dto.add(this.convertiInDTO(e));
		}
		return dto;
	}
	
    /**
     * Mostra l'elenco di tutte escursioni in una città
     * @param nomeCitta Il nome della città
     * @return L'elenco delle escursioni
     */
	@Override
	public List<EscursioneDTO> elencoEscursioni(String nomeCitta) {
		Query q = em.createNamedQuery("Escursioni.elencoPerCitta", Escursioni.class);
		q.setParameter("citta", nomeCitta);
		@SuppressWarnings("unchecked")
		List<Escursioni> escursioni = q.getResultList();
		List<EscursioneDTO> dto = new ArrayList<EscursioneDTO>();
		for (Escursioni e : escursioni) {
			dto.add(this.convertiInDTO(e));
		}
		return dto;
	}	

	/**
	 * Crea una nuova escursione nel database
	 * @param escursione L'oggetto da salvare
	 * @throws EntityExistsException Quando l'escursione esiste già nel database
	 * @throws NoResultException Quando non viene trovata la città dell'escursione
	 */
	@Override
	public void creaEscursione(EscursioneDTO escursione) throws EntityExistsException, NoResultException {
		Escursioni entity = new Escursioni ();
		
		entity.setCategoria(escursione.getCategoria());
		entity.setData(escursione.getData());
		entity.setDurata(escursione.getDurata());
		entity.setNome(escursione.getNome());
		entity.setOra(escursione.getOra());
		entity.setPrezzo(escursione.getPrezzo());
		entity.setCitta(citta.convertiInDAO(escursione.getCitta()));
		
		em.persist(entity);		
	}

	/**
	 * Permette di modificare i dati di una escursione
	 * @param escursione L'escursione da modifciare
	 * @throws NoResultException Quando non viene trovata la città dell'escursione
	 */
	@Override
	public void modificaDatiEscursione(EscursioneDTO escursione) throws NoResultException {
		Escursioni entity = this.convertiInDAO(escursione);
		
		entity.setCategoria(escursione.getCategoria());
		entity.setData(escursione.getData());
		entity.setDurata(escursione.getDurata());
		entity.setNome(escursione.getNome());
		entity.setOra(escursione.getOra());
		entity.setPrezzo(escursione.getPrezzo());
		entity.setCitta(citta.convertiInDAO(escursione.getCitta()));
		
		em.merge(entity);
	}

	/**
	 * Permette l'eliminazione di una escursione dal database
	 * @param escursione L'escursione da eliminare
	 * @throws NoResultException Quando non esiste l'escursione con l'id selezionato
	 */
	@Override
	public void eliminaEscursione(EscursioneDTO escursione) throws NoResultException {
		em.remove(this.convertiInDAO(escursione));
	}

	/**
	 * Permette la conversione da un DTO alla rispettiva entità
	 * @param escursione Il DTO dell'escursione
	 * @return L'entità desiderata
	 */
	protected Escursioni convertiInDAO (EscursioneDTO escursione) {
		return em.find(Escursioni.class, escursione.getId());		
	}
	
	/**
	 * Permette la conversione da un'entità al rispettivo DTO
	 * @param escursione L'oggetto da converitre
	 * @return Il DTO risultante
	 */
	protected EscursioneDTO convertiInDTO (Escursioni escursione) {
		EscursioneDTO dto = new EscursioneDTO();
		dto.setCategoria(escursione.getCategoria());
		dto.setData(escursione.getData());
		dto.setDurata(escursione.getDurata());
		dto.setNome(escursione.getNome());
		dto.setOra(escursione.getOra());
		dto.setPrezzo(escursione.getPrezzo());
		dto.setCitta(citta.convertiInDTO(escursione.getCitta()));
		return dto;
	}

}
