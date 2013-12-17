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

    /**
     * Mostra l'elenco dei collegamenti disponibili
     * @return L'elenco dei collegamenti
     */
	@Override
	public List<CollegamentoDTO> elencoCollegamenti() {
		List<Collegamenti> collegamenti = em.createNamedQuery("Collegamenti.elenco", Collegamenti.class).getResultList();
		List<CollegamentoDTO> dto = new ArrayList<CollegamentoDTO>();
		for (Collegamenti c : collegamenti) {
			dto.add(this.convertiInDTO(c));
		}
		return dto;
	}
	
    /**
     * Mostra l'elenco dei collegamenti disponibili per tipologia
     * @param tipo La tipologia del collegamento
     * @return L'elenco dei collegamenti
     */
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

	/**
	 * Mostra i dettagli relativi al collegamento con il codice in input
	 * @param codiceCollegamento Il codice del collegamento
	 * @return L'oggetto collegamento con i dettagli
	 * @throws NoResultException Quando non esiste il collegamento con il codice selezionato
	 */
	@Override
	public CollegamentoDTO dettagliCollegamento(int codiceCollegamento) throws NoResultException {
		return this.convertiInDTO(this.getCollegamento(codiceCollegamento));
	}

	/**
	 * Aggiunge un nuovo collegamento nel database
	 * @param collegamento I dati del collegamento da aggiungere
	 * @throws EntityExistsException Quando il collegamento esiste già nel database
	 * @throws NoResultException Quando non vengono trovate le città del collegamento
	 */
	@Override
	public void creaCollegamento(CollegamentoDTO collegamento) throws EntityExistsException, NoResultException {
		Collegamenti entity = new Collegamenti ();
		
		entity.setDataPartenza(collegamento.getDataPartenza());
		entity.setDestinazione(collegamento.getDestinazione());
		entity.setOraArrivo(collegamento.getOraArrivo());
		entity.setOraPartenza(collegamento.getOraPartenza());
		entity.setOrigine(collegamento.getOrigine());
		entity.setPrezzo(collegamento.getPrezzo());
		entity.setTipoCollegamento(collegamento.getTipoCollegamento());
		entity.setCittaArrivo(citta.getCitta(collegamento.getCittaArrivo().getNome()));
		entity.setCittaPartenza(citta.getCitta(collegamento.getCittaPartenza().getNome()));
		
		em.persist(entity);		
	}

	/**
	 * Permette la modifica dei dati di un collegamento
	 * @param codice Il codice del collegamento da modificare
	 * @param collegamento I nuovi dati del collegamento
	 * @throws NoResultException Quando non vengono trovate le città del collegamento
	 */
	@Override
	public void modificaDatiCollegamento(int codice, CollegamentoDTO collegamento) throws NoResultException {
		Collegamenti entity = this.getCollegamento(codice);
		
		entity.setDataPartenza(collegamento.getDataPartenza());
		entity.setDestinazione(collegamento.getDestinazione());
		entity.setOraArrivo(collegamento.getOraArrivo());
		entity.setOraPartenza(collegamento.getOraPartenza());
		entity.setOrigine(collegamento.getOrigine());
		entity.setPrezzo(collegamento.getPrezzo());
		entity.setTipoCollegamento(collegamento.getTipoCollegamento());
		entity.setCittaArrivo(citta.getCitta(collegamento.getCittaArrivo().getNome()));
		entity.setCittaPartenza(citta.getCitta(collegamento.getCittaPartenza().getNome()));
		
		em.merge(entity);
	}

	/**
	 * Permette l'eliminazione di un collegamento dal database
	 * @param codice Il codice del collegamento da eliminare
	 * @throws NoResultException Quando non esiste il collegamento con il codice selezionato
	 */
	@Override
	public void eliminaCollegamento(int codice) throws NoResultException {
		em.remove(this.getCollegamento(codice));		
	}
	
	/**
	 * Mostra il collegamento relativo al codice in input
	 * @param codice Il codice del collegamento
	 * @return Il collegameno desiderato
	 */
	protected Collegamenti getCollegamento (int codice) {
		return em.find(Collegamenti.class, codice);
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
