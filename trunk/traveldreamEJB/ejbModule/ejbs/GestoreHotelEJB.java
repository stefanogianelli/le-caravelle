package ejbs;

import interfaces.GestoreCittaLocal;
import interfaces.GestoreHotelLocal;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dtos.HotelDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.HotelInesistenteException;
import entities.Hotel;

/**
 * Session Bean implementation class GestoreHotelEJB
 */
@Stateless
public class GestoreHotelEJB implements GestoreHotel, GestoreHotelLocal {

	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private GestoreCittaLocal citta;
	
    /**
     * Default constructor. 
     */
    public GestoreHotelEJB() {

    }

	@Override
	public List<HotelDTO> elencoHotel() {
		List<Hotel> hotel = em.createNamedQuery("Hotel.elenco", Hotel.class).getResultList();
		List<HotelDTO> dto = new ArrayList<HotelDTO>();
		for (Hotel h : hotel) {
			dto.add(this.convertiInDTO(h));
		}
		return dto;
	}
	
	@Override
	public List<HotelDTO> elencoHotel(String nomeCitta) {
		Query q = em.createNamedQuery("Hotel.elencoPerCitta", Hotel.class);
		q.setParameter("citta", nomeCitta);
		@SuppressWarnings("unchecked")
		List<Hotel> hotel = q.getResultList();
		List<HotelDTO> dto = new ArrayList<HotelDTO>();
		for (Hotel h : hotel) {
			dto.add(this.convertiInDTO(h));
		}
		return dto;
	}	

	@Override
	public void creaHotel(HotelDTO hotel) throws CittaInesistenteException, EntityExistsException {
		Hotel entity = new Hotel ();
		
		entity.setEmail(hotel.getEmail());
		entity.setIndirizzo(hotel.getIndirizzo());
		entity.setNome(hotel.getNome());
		entity.setPrezzo(hotel.getPrezzo());
		entity.setStelle(hotel.getStelle());
		entity.setTelefono(hotel.getTelefono());
		entity.setWebsite(hotel.getWebsite());
		entity.setCitta(citta.convertiInEntita(hotel.getCitta()));		
		
		em.persist(entity);
	}

	@Override
	public void modificaDatiHotel(HotelDTO hotel) throws CittaInesistenteException, HotelInesistenteException {
		Hotel entity = this.convertiInEntita(hotel);
		
		entity.setEmail(hotel.getEmail());
		entity.setIndirizzo(hotel.getIndirizzo());
		entity.setNome(hotel.getNome());
		entity.setPrezzo(hotel.getPrezzo());
		entity.setStelle(hotel.getStelle());
		entity.setTelefono(hotel.getTelefono());
		entity.setWebsite(hotel.getWebsite());		
		
		em.merge(entity);
	}

	@Override
	public void eliminaHotel(HotelDTO hotel) throws HotelInesistenteException {
		em.remove(this.convertiInEntita(hotel));
	}
	
	@Override
	public Hotel convertiInEntita (HotelDTO hotel) throws HotelInesistenteException {
		Hotel hotelEntity = em.find(Hotel.class, hotel.getId());
		if (hotelEntity != null)
			return hotelEntity;
		else
			throw new HotelInesistenteException ();
	}	
	
	@Override
	public HotelDTO convertiInDTO (Hotel hotel) {
		HotelDTO dto = new HotelDTO ();
		
		dto.setId(hotel.getId());
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