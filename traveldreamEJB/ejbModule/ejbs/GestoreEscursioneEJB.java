package ejbs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dtos.EscursioneDTO;
import eccezioni.CittaInesistenteException;
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
    
	@Override
	public List<EscursioneDTO> elencoEscursioni() {
		List<Escursioni> escursioni = em.createNamedQuery("Escursioni.elenco", Escursioni.class).getResultList();
		List<EscursioneDTO> dto = new ArrayList<EscursioneDTO>();
		for (Escursioni e : escursioni) {
			dto.add(this.convertiInDTO(e));
		}
		return dto;
	}
	
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

	@Override
	public void creaEscursione(EscursioneDTO escursione) throws CittaInesistenteException {
		Escursioni entity = new Escursioni ();
		
		entity.setCategoria(escursione.getCategoria());
		entity.setData(escursione.getData());
		entity.setDurata(escursione.getDurata());
		entity.setNome(escursione.getNome());
		entity.setOra(escursione.getOra());
		entity.setPrezzo(escursione.getPrezzo());
		entity.setCitta(citta.convertiInEntita(escursione.getCitta()));
		
		em.persist(entity);
	}

	@Override
	public void modificaDatiEscursione(EscursioneDTO escursione) throws CittaInesistenteException {
		Escursioni entity = this.convertiInEntita(escursione);
		
		entity.setCategoria(escursione.getCategoria());
		entity.setData(escursione.getData());
		entity.setDurata(escursione.getDurata());
		entity.setNome(escursione.getNome());
		entity.setOra(escursione.getOra());
		entity.setPrezzo(escursione.getPrezzo());
		entity.setCitta(citta.convertiInEntita(escursione.getCitta()));
		
		em.merge(entity);
	}

	@Override
	public void eliminaEscursione(EscursioneDTO escursione) {
		em.remove(this.convertiInEntita(escursione));
	}

	/**
	 * Permette la conversione da un DTO alla rispettiva entità
	 * @param escursione Il DTO dell'escursione
	 * @return L'entità desiderata
	 */
	protected Escursioni convertiInEntita (EscursioneDTO escursione) {
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
