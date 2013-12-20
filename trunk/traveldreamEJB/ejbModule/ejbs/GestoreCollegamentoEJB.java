package ejbs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dtos.CollegamentoDTO;
import entities.Collegamenti;
import enums.TipoCollegamento;

/**
 * Session Bean implementation class GestoreCollegamentoEJB
 */
@Stateless
public class GestoreCollegamentoEJB implements GestoreCollegamento {

	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private GestoreCittaEJB citta;
	
    /**
     * Default constructor. 
     */
    public GestoreCollegamentoEJB() {

    }

	@Override
	public List<CollegamentoDTO> elencoCollegamenti() {
		List<Collegamenti> collegamenti = em.createNamedQuery("Collegamenti.elenco", Collegamenti.class).getResultList();
		List<CollegamentoDTO> dto = new ArrayList<CollegamentoDTO>();
		for (Collegamenti c : collegamenti) {
			dto.add(this.convertiInDTO(c));
		}
		return dto;
	}
	
	@Override
	public List<CollegamentoDTO> elencoCollegamenti(TipoCollegamento tipo) {
		Query q = em.createNamedQuery("Collegamenti.elencoPerTipo", Collegamenti.class);
		q.setParameter("tipo", tipo);
		@SuppressWarnings("unchecked")
		List<Collegamenti> collegamenti = q.getResultList();
		List<CollegamentoDTO> dto = new ArrayList<CollegamentoDTO>();
		for (Collegamenti c : collegamenti) {
			dto.add(this.convertiInDTO(c));
		}
		return dto;
	}	

	@Override
	public void creaCollegamento(CollegamentoDTO collegamento) {
		Collegamenti entity = new Collegamenti ();
		
		entity.setDataPartenza(collegamento.getDataPartenza());
		entity.setDestinazione(collegamento.getDestinazione());
		entity.setOraArrivo(collegamento.getOraArrivo());
		entity.setOraPartenza(collegamento.getOraPartenza());
		entity.setOrigine(collegamento.getOrigine());
		entity.setPrezzo(collegamento.getPrezzo());
		entity.setTipoCollegamento(collegamento.getTipoCollegamento());
		entity.setCittaArrivo(citta.convertiInEntita(collegamento.getCittaArrivo()));
		entity.setCittaPartenza(citta.convertiInEntita(collegamento.getCittaPartenza()));
		
		em.persist(entity);		
	}

	@Override
	public void modificaDatiCollegamento(CollegamentoDTO collegamento) {
		Collegamenti entity = this.convertiInEntita(collegamento);
		
		entity.setDataPartenza(collegamento.getDataPartenza());
		entity.setDestinazione(collegamento.getDestinazione());
		entity.setOraArrivo(collegamento.getOraArrivo());
		entity.setOraPartenza(collegamento.getOraPartenza());
		entity.setOrigine(collegamento.getOrigine());
		entity.setPrezzo(collegamento.getPrezzo());
		entity.setTipoCollegamento(collegamento.getTipoCollegamento());
		entity.setCittaArrivo(citta.convertiInEntita(collegamento.getCittaArrivo()));
		entity.setCittaPartenza(citta.convertiInEntita(collegamento.getCittaPartenza()));
		
		em.merge(entity);
	}

	@Override
	public void eliminaCollegamento(CollegamentoDTO collegamento){
		em.remove(this.convertiInEntita(collegamento));		
	}
	
	/**
	 * Permette la conversione da un DTO alla rispettiva entità
	 * @param collegamento Il DTO del collegamento
	 * @return L'entità desiderata
	 */
	protected Collegamenti convertiInEntita (CollegamentoDTO collegamento) {
		return em.find(Collegamenti.class, collegamento.getCodice());
	}
	
	/**
	 * Permette la conversione da un'entità al rispettivo DTO
	 * @param collegamento L'entità di partenza
	 * @return Il relativo DTO
	 */
	protected CollegamentoDTO convertiInDTO (Collegamenti collegamento) {
		CollegamentoDTO dto = new CollegamentoDTO ();
		
		dto.setDataPartenza(collegamento.getDataPartenza());
		dto.setDestinazione(collegamento.getDestinazione());
		dto.setOraArrivo(collegamento.getOraArrivo());
		dto.setOraPartenza(collegamento.getOraPartenza());
		dto.setOrigine(collegamento.getOrigine());
		dto.setPrezzo(collegamento.getPrezzo());
		dto.setTipoCollegamento(collegamento.getTipoCollegamento());
		dto.setCittaArrivo(citta.convertiInDTO(collegamento.getCittaArrivo()));
		dto.setCittaPartenza(citta.convertiInDTO(collegamento.getCittaPartenza()));
		
		return dto;
	}

}
