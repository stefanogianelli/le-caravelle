package ejbs;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
	 * Permette la conversione da un DTO alla rispettiva entit�
	 * @param citta Il DTO della citta
	 * @return L'entit� desiderata
	 */
	protected Citta convertiInDAO (CittaDTO citta) {
		return em.find(Citta.class, citta.getId());		
	}	
	
	/**
	 * Permette la conversione da un'entit� al rispettivo DTO
	 * @param citta L'entit� di partenza
	 * @return Il relativo DTO
	 */
	protected CittaDTO convertiInDTO (Citta citta) {
		CittaDTO dto = new CittaDTO();
		
		dto.setNazione(citta.getNazione());
		dto.setNome(citta.getNome());
		dto.setRegione(citta.getRegione());
		
		return dto;
	}

}
