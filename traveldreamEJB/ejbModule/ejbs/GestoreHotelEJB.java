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

import dtos.HotelDTO;
import entities.Hotel;

/**
 * Session Bean implementation class GestoreHotelEJB
 */
@Stateless
public class GestoreHotelEJB implements GestoreHotel {

	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private GestoreCittaEJB citta;
	
    /**
     * Default constructor. 
     */
    public GestoreHotelEJB() {

    }

    /**
     * Mostra l'elenco di tutti gli hotel presenti nel database
     * @return L'elenco degli hotel
     */
	@Override
	public List<HotelDTO> elencoHotel() {
		List<Hotel> hotel = em.createNamedQuery("Hotel.elenco", Hotel.class).getResultList();
		List<HotelDTO> dto = new ArrayList<HotelDTO>();
		for (Hotel h : hotel) {
			dto.add(this.convertiInDTO(h));
		}
		return dto;
	}
	
    /**
     * Mostra l'elenco di tutti gli hotel in una città
     * @param nomeCitta Il nome della città
     * @return L'elenco degli hotel
     */
	@Override
	public List<HotelDTO> elencoHotel(String nomeCitta) {
		Query q = em.createNamedQuery("Hotel.elenco", Hotel.class);
		q.setParameter("citta", nomeCitta);
		@SuppressWarnings("unchecked")
		List<Hotel> hotel = q.getResultList();
		List<HotelDTO> dto = new ArrayList<HotelDTO>();
		for (Hotel h : hotel) {
			dto.add(this.convertiInDTO(h));
		}
		return dto;
	}	

	/**
	 * Mostra i dettagli relativi all'hotel con l'id in input
	 * @param idHotel L'identificativo dell'hotel
	 * @return L'hotel selezionato
	 * @throws NoResultException Quando non esiste l'hotel con l'id selezionato
	 */
	@Override
	public HotelDTO dettagliHotel(int idHotel) throws NoResultException {
		return this.convertiInDTO(this.getHotel(idHotel));
	}

	/**
	 * Crea un nuovo hotel nel database
	 * @param hotel L'oggetto da salvare
	 * @throws EntityExistsException Quando l'hotel esiste già nel database
	 * @throws NoResultException Quando non viene trovata la città dell'hotel
	 */
	@Override
	public void creaHotel(HotelDTO hotel) throws EntityExistsException, NoResultException {
		Hotel entity = new Hotel ();
		
		entity.setEmail(hotel.getEmail());
		entity.setIndirizzo(hotel.getIndirizzo());
		entity.setNome(hotel.getNome());
		entity.setPrezzo(hotel.getPrezzo());
		entity.setStelle(hotel.getStelle());
		entity.setTelefono(hotel.getTelefono());
		entity.setWebsite(hotel.getWebsite());
		entity.setCitta(citta.getCitta(hotel.getCitta().getNome()));			
		
		em.persist(entity);
	}

	/**
	 * Permette di modificare i dati di un hotel
	 * @param idHotel L'identificativo dell'hotel
	 * @param hotel I dati modificati dell'hotel
	 * @throws NoResultException Quando non viene trovata la città dell'hotel
	 */
	@Override
	public void modificaDatiHotel(int idHotel, HotelDTO hotel) throws NoResultException {
		Hotel entity = this.getHotel(idHotel);
		
		entity.setEmail(hotel.getEmail());
		entity.setIndirizzo(hotel.getIndirizzo());
		entity.setNome(hotel.getNome());
		entity.setPrezzo(hotel.getPrezzo());
		entity.setStelle(hotel.getStelle());
		entity.setTelefono(hotel.getTelefono());
		entity.setWebsite(hotel.getWebsite());
		entity.setCitta(citta.getCitta(hotel.getCitta().getNome()));		
		
		em.merge(entity);
	}

	/**
	 * Permette l'eliminazione di un hotel dal database
	 * @param idHotel L'identificativo dell'hotel
	 * @throws NoResultException Quando non esiste l'hotel con l'id selezionato
	 */
	@Override
	public void eliminaHotel(int idHotel) throws NoResultException {
		em.remove(this.getHotel(idHotel));
	}
	
	/**
	 * Ritorna l'oggetto hotel corrispondente all'id in input
	 * @param idHotel L'identificativo dell'hotel
	 * @return L'oggetto hotel
	 */
	protected Hotel getHotel (int idHotel) {
		return em.find(Hotel.class, idHotel);
	}
	
	/**
	 * Permette la conversione da un'entità hotel al rispettivo DTO
	 * @param hotel L'oggetto da converitre
	 * @return Il DTO risultante
	 */
	protected HotelDTO convertiInDTO (Hotel hotel) {
		HotelDTO dto = new HotelDTO ();
		dto.setEmail(hotel.getEmail());
		dto.setIndirizzo(hotel.getIndirizzo());
		dto.setNome(hotel.getNome());
		dto.setPrezzo(hotel.getPrezzo());
		dto.setStelle(hotel.getStelle());
		dto.setTelefono(hotel.getTelefono());
		dto.setWebsite(hotel.getWebsite());
		dto.setCitta(citta.convertiInDTO(hotel.getCitta()));
		return dto;
	}
	
}
