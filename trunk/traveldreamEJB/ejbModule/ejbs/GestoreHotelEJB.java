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
import dtos.HotelDTO;
import entities.Citta;
import entities.Hotel;

/**
 * Session Bean implementation class GestoreHotelEJB
 */
@Stateless
public class GestoreHotelEJB implements GestoreHotel {

	@PersistenceContext
	private EntityManager em;
	
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
		entity.setCitta(this.getCitta(hotel.getCitta().getNome()));			
		
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
		entity.setCitta(this.getCitta(hotel.getCitta().getNome()));		
		
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
	 * @throws NoResultException Quando non esiste un hotel con l'id scelto 
	 */
	private Hotel getHotel (int idHotel) throws NoResultException {
		Query q = em.createNamedQuery("Hotel.getHotel", Hotel.class);
		q.setParameter("id", idHotel);
		return (Hotel) q.getSingleResult();
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
	 * Permette la conversione da un oggetto hotel al rispettivo DTO
	 * @param hotel L'oggetto da converitre
	 * @return Il DTO risultante
	 */
	private HotelDTO convertiInDTO (Hotel hotel) {
		HotelDTO dto = new HotelDTO (
				hotel.getEmail(),
				hotel.getIndirizzo(),
				hotel.getNome(),
				hotel.getPrezzo(),
				hotel.getStelle(),
				hotel.getTelefono(),
				hotel.getWebsite(),
				new CittaDTO (hotel.getCitta().getNazione(), hotel.getCitta().getNome(), hotel.getCitta().getRegione())
				);
		return dto;
	}
	
}
