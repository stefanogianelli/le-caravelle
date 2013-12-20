package ejbs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dtos.CittaDTO;
import eccezioni.CittaInesistenteException;
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
    
	@Override
	public List<CittaDTO> elencoCitta() {
		List<Citta> citta = em.createNamedQuery("Citta.elenco", Citta.class).getResultList();
		List<CittaDTO> dto = new ArrayList<CittaDTO>();
		for (Citta c : citta) {
			dto.add(this.convertiInDTO(c));
		}
		return dto;
	}
    
	/**
	 * Permette la conversione da un DTO alla rispettiva entità
	 * @param citta Il DTO della citta
	 * @return L'entità desiderata
	 * @throws CittaInesistenteException Se la città non viene trovata nel database
	 */
	protected Citta convertiInEntita (CittaDTO citta) throws CittaInesistenteException {
		Citta cittaEntity = em.find(Citta.class, citta.getId());
		if (cittaEntity != null)
			return cittaEntity;
		else
			throw new CittaInesistenteException ();
	}	
	
	/**
	 * Permette la conversione da un'entità al rispettivo DTO
	 * @param citta L'entità di partenza
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
