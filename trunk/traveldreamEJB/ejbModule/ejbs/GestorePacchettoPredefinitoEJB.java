package ejbs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dtos.CollegamentoDTO;
import dtos.EscursioneDTO;
import dtos.PacchettoPredefinitoDTO;
import entities.AttivitaPred;
import entities.AttivitaPredPK;
import entities.DatePartenza;
import entities.DatePartenzaPK;
import entities.Durate;
import entities.DuratePK;
import entities.Escursioni;
import entities.PacchettiPredefiniti;

/**
 * Session Bean implementation class GestorePacchettoPredefinitoEJB
 */
@Stateless
public class GestorePacchettoPredefinitoEJB implements GestorePacchettoPredefinito {

	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private GestoreHotelEJB hotel;
	
	@EJB
	private GestoreCollegamentoEJB collegamento;
	
	@EJB
	private GestoreEscursioneEJB escursione;
	
    /**
     * Default constructor. 
     */
    public GestorePacchettoPredefinitoEJB() {
        
    }
    
	/**
	 * Mostra l'elenco dei pacchetti predefiniti
	 * @return L'elenco dei pacchetti predefiniti
	 */
	@Override
	public List<PacchettoPredefinitoDTO> elencoPacchetti() {
		List<PacchettiPredefiniti> pacchetti = em.createNamedQuery("PacchettiPredefiniti.elenco", PacchettiPredefiniti.class).getResultList();
		List<PacchettoPredefinitoDTO> pacchettiDTO = new ArrayList<PacchettoPredefinitoDTO>();
		for (PacchettiPredefiniti p : pacchetti) {
			pacchettiDTO.add(this.convertiInDTO(p));
		}
		return pacchettiDTO;
	}

	/**
	 * Permette la creazione di un nuovo pacchetto predefinito
	 * @param pacchetto I dati del pacchetto
	 */
	@Override
	public void creaPacchetto(PacchettoPredefinitoDTO pacchetto) {
		PacchettiPredefiniti entity = new PacchettiPredefiniti();
		
		entity.setNome(pacchetto.getNome());
		entity.setPrezzo(pacchetto.getPrezzo());
		for (Date d : pacchetto.getDatePartenza()) {
			DatePartenzaPK dataPK = new DatePartenzaPK();
			dataPK.setData(d);
			dataPK.setIdPacchettoPredefinito(entity.getId());
			
			DatePartenza data = new DatePartenza();
			data.setId(dataPK);
			data.setPacchettoPredefinito(entity);
			
			entity.addDataPartenza(data);
		}
		for (Integer i : pacchetto.getDurate()) {
			DuratePK duratePK = new DuratePK();
			duratePK.setDurata(i);
			duratePK.setIdPacchettoPredefinito(entity.getId());
			
			Durate durate = new Durate();
			durate.setId(duratePK);
			durate.setPacchettoPredefinito(entity);
			
			entity.addDurata(durate);
		}
		entity.setHotel(hotel.convertiInEntita(pacchetto.getHotel()));
		
		em.persist(entity);		
	}
	
	/**
	 * Permette l'aggiunta di un collegamento nel pacchetto
	 * @param pacchetto Il pacchetto nel quale si vuole aggiungere il collegamento
	 * @param collegamento Il collegamento da aggiungere
	 */
	@Override
	public void aggiuntaCollegamento(PacchettoPredefinitoDTO pacchetto, CollegamentoDTO collegamento) {
		PacchettiPredefiniti entity = this.convertiInEntita(pacchetto);
		
		entity.addCollegamento(this.collegamento.convertiInEntita(collegamento));
		
		em.merge(entity);
	}

	/**
	 * Permette la rimozione di un collegamento da un pacchetto
	 * @param pacchetto Il pacchetto dal quale si vuole rimuovere il collegamento
	 * @param collegamento Il collegamento che si vuole eliminare
	 */
	@Override
	public void rimuoviCollegamento(PacchettoPredefinitoDTO pacchetto, CollegamentoDTO collegamento) {
		PacchettiPredefiniti entity = this.convertiInEntita(pacchetto);
		
		entity.removeCollegamento(this.collegamento.convertiInEntita(collegamento));
		
		em.merge(entity);
	}

	/**
	 * Permette l'aggiunta di un'escursione nel pacchetto
	 * @param pacchetto Il pacchetto nel quale si vuole aggiungere l'escursione
	 * @param escursione L'escursione che si vuole aggiungere
	 */
	@Override
	public void aggiuntaEscursione(PacchettoPredefinitoDTO pacchetto, EscursioneDTO escursione) {
		PacchettiPredefiniti entity = this.convertiInEntita(pacchetto);
		
		Escursioni escursioneEntity = this.escursione.convertiInEntita(escursione);
		
		AttivitaPredPK attivitaPK = new AttivitaPredPK();
		attivitaPK.setIdEscursione(escursioneEntity.getId());
		attivitaPK.setIdPacchettoPredefinito(entity.getId());
		
		AttivitaPred attivita = new AttivitaPred();
		attivita.setEscursione(escursioneEntity);
		attivita.setId(attivitaPK);
		
		entity.addAttivita(attivita);
		
		em.merge(entity);		
	}

	/**
	 * Permette la rimozione di un'escursione da un pacchetto
	 * @param pacchetto Il pacchetto dal quale si vuole rimuovere l'escursione
	 * @param escursione L'escursione da rimuovere
	 */
	@Override
	public void rimuoviEscursione(PacchettoPredefinitoDTO pacchetto, EscursioneDTO escursione) {
		PacchettiPredefiniti entity = this.convertiInEntita(pacchetto);
		
		Escursioni escursioneEntity = this.escursione.convertiInEntita(escursione);
		
		Query q = em.createNamedQuery("AttivitaPred.getAttivita", AttivitaPred.class);
		q.setParameter("pacchetto", entity);
		q.setParameter("escursione", escursioneEntity);
		AttivitaPred attivita = (AttivitaPred) q.getSingleResult();
		
		entity.removeAttivita(attivita);
		
		em.merge(entity);			
	}

	/**
	 * Permette il salvataggio di un pacchetto
	 * @param pacchetto Il pacchetto da salvare
	 */
	@Override
	public void salvaPacchetto(PacchettoPredefinitoDTO pacchetto) {
		PacchettiPredefiniti entity = this.convertiInEntita(pacchetto);
		
		entity.setNome(pacchetto.getNome());
		entity.setPrezzo(pacchetto.getPrezzo());
		entity.setDatePartenza(null);
		for (Date d : pacchetto.getDatePartenza()) {
			DatePartenzaPK dataPK = new DatePartenzaPK();
			dataPK.setData(d);
			dataPK.setIdPacchettoPredefinito(entity.getId());
			
			DatePartenza data = new DatePartenza();
			data.setId(dataPK);
			data.setPacchettoPredefinito(entity);
			
			entity.addDataPartenza(data);
		}
		entity.setDurate(null);
		for (Integer i : pacchetto.getDurate()) {
			DuratePK duratePK = new DuratePK();
			duratePK.setDurata(i);
			duratePK.setIdPacchettoPredefinito(entity.getId());
			
			Durate durate = new Durate();
			durate.setId(duratePK);
			durate.setPacchettoPredefinito(entity);
			
			entity.addDurata(durate);
		}
		entity.setHotel(hotel.convertiInEntita(pacchetto.getHotel()));
		
		em.merge(entity);		
	}

	/**
	 * Permette l'eliminazione di un pacchetto
	 * @param pacchetto Il pacchetto da eliminare
	 */
	@Override
	public void eliminaPacchetto(PacchettoPredefinitoDTO pacchetto) {
		em.remove(this.convertiInEntita(pacchetto));
	}
	
	/**
	 * Permette la conversione da un DTO alla rispettiva entità
	 * @param pacchetto Il DTO del pacchetto predefinito
	 * @return L'entità desiderata
	 */
	protected PacchettiPredefiniti convertiInEntita (PacchettoPredefinitoDTO pacchetto) {
		return em.find(PacchettiPredefiniti.class, pacchetto.getId());
	}
	
	/**
	 * Permette la conversione da un'entità al rispettivo DTO
	 * @param pacchetto L'entità di partenza
	 * @return Il relativo DTO
	 */
	protected PacchettoPredefinitoDTO convertiInDTO (PacchettiPredefiniti pacchetto) {
		PacchettoPredefinitoDTO pacchettoDTO = new PacchettoPredefinitoDTO();
		
		pacchettoDTO.setId(pacchetto.getId());
		pacchettoDTO.setNome(pacchetto.getNome());
		pacchettoDTO.setPrezzo(pacchetto.getPrezzo());
		List<Date> datePartenza = new ArrayList<Date>();
		for (DatePartenza d : pacchetto.getDatePartenza()) {
			datePartenza.add(d.getId().getData());
		}
		pacchettoDTO.setDatePartenza(datePartenza);
		List<Integer> durate = new ArrayList<Integer>();
		for (Durate d : pacchetto.getDurate()) {
			durate.add(d.getId().getDurata());
		}
		pacchettoDTO.setDurate(durate);
		pacchettoDTO.setHotel(hotel.convertiInDTO(pacchetto.getHotel()));
		
		return pacchettoDTO;
	}
}
