package ejbs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dtos.CittaDTO;
import dtos.EscursioneDTO;
import entities.Citta;
import entities.Escursioni;

/**
 * Session Bean implementation class GestoreEscursioneEJB
 */
@Stateless
public class GestoreEscursioneEJB implements GestoreEscursione {

	@PersistenceContext
	private EntityManager em;
	
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
	 * Mostra i dettagli relativi all'escursione con l'id in input
	 * @param idEscursione L'identificativo dell'escursione
	 * @return L'escursione selezionata
	 * @throws NoResultException Quando non esiste l'escursione con l'id selezionato
	 */
	@Override
	public EscursioneDTO dettagliEscursione(int idEscursione) throws NoResultException {
		return this.convertiInDTO(this.getEscursione(idEscursione));
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
		entity.setCitta(this.getCitta(escursione.getCitta().getNome()));
		
		em.persist(entity);		
	}

	/**
	 * Permette di modificare i dati di una escursione
	 * @param id L'identificativo dell'escursione
	 * @param escursione I dati modificati dell'escursione
	 * @throws NoResultException Quando non viene trovata la città dell'escursione
	 */
	@Override
	public void modificaDatiEscursione(int id, EscursioneDTO escursione) throws NoResultException {
		Escursioni entity = this.getEscursione(id);
		
		entity.setCategoria(escursione.getCategoria());
		entity.setData(escursione.getData());
		entity.setDurata(escursione.getDurata());
		entity.setNome(escursione.getNome());
		entity.setOra(escursione.getOra());
		entity.setPrezzo(escursione.getPrezzo());
		entity.setCitta(this.getCitta(escursione.getCitta().getNome()));
		
		em.merge(entity);
	}

	/**
	 * Permette l'eliminazione di una escursione dal database
	 * @param idEscursione L'identificativo dell'escursione
	 * @throws NoResultException Quando non esiste l'escursione con l'id selezionato
	 */
	@Override
	public void eliminaEscursione(int idEscursione) throws NoResultException {
		em.remove(this.getEscursione(idEscursione));
	}
	
	/**
	 * Ritorna l'oggetto escursione corrispondente all'id in input
	 * @param id L'identificativo dell'escursione
	 * @return L'oggetto escursione
	 * @throws NoResultException Quando non esiste una escursione con l'id scelto 
	 */
	private Escursioni getEscursione (int id) throws NoResultException {
		Query q = em.createNamedQuery("Escursioni.getEscursione", Escursioni.class);
		q.setParameter("id", id);
		return (Escursioni) q.getSingleResult();
	}
	
	/**
	 * Ritorna l'oggetto città corrispondente al nome in input
	 * @param nome Il nome della città
	 * @return L'oggetto città
	 * @throws NoResultException Quando non esiste una città con il nome scelto
	 */
	private Citta getCitta (String nome) throws NoResultException {
		Query q = em.createNamedQuery("Citta.getCitta", Citta.class);
		q.setParameter("nome", nome);
		return (Citta) q.getSingleResult();
	}
	
	/**
	 * Permette la conversione da un'entità al rispettivo DTO
	 * @param escursione L'oggetto da converitre
	 * @return Il DTO risultante
	 */
	private EscursioneDTO convertiInDTO (Escursioni escursione) {
		EscursioneDTO dto = new EscursioneDTO();
		dto.setCategoria(escursione.getCategoria());
		dto.setData(escursione.getData());
		dto.setDurata(escursione.getDurata());
		dto.setNome(escursione.getNome());
		dto.setOra(escursione.getOra());
		dto.setPrezzo(escursione.getPrezzo());
		dto.setCitta(new CittaDTO (escursione.getCitta().getNazione(), escursione.getCitta().getNome(), escursione.getCitta().getRegione()));
		return dto;
	}

}
