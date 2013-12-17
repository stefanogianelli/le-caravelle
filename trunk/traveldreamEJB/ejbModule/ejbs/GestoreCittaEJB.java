package ejbs;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dtos.CittaDTO;
import entities.Citta;

/**
 * Session Bean implementation class GestoreCittaEJB
 */
@Stateless
public class GestoreCittaEJB implements GestoreCitta {

	@PersistenceContext
	private EntityManager em;
	
    /**
     * Default constructor. 
     */
    public GestoreCittaEJB() {
       
    }
    
	/**
	 * Ritorna l'oggetto città con l'id in input
	 * @param id L'identificativo della città
	 * @return L'oggetto desiderato
	 */
	protected Citta getCitta (int id) {
		return em.find(Citta.class, id);		
	}	
	
	/**
	 * Ritorna l'oggetto città con il nome in input
	 * @param nome Il nome della città
	 * @return L'oggetto desiderato
	 * @throws NoResultException Quando non viene trovata la città nel database
	 */
	protected Citta getCitta (String nome) throws NoResultException {
		Query q = em.createNamedQuery("Citta.getCitta", Citta.class);
		q.setParameter("nome", nome);
		return (Citta) q.getSingleResult();			
	}
	
	protected CittaDTO convertiInDTO (Citta citta) {
		CittaDTO dto = new CittaDTO();
		
		dto.setNazione(citta.getNazione());
		dto.setNome(citta.getNome());
		dto.setRegione(citta.getRegione());
		
		return dto;
	}

}
