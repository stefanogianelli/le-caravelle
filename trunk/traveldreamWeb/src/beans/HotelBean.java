package beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityExistsException;

import dtos.HotelDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.HotelInesistenteException;
import ejbs.GestoreHotel;

@ManagedBean(name="hotel")
@RequestScoped
public class HotelBean {

	@EJB
	private GestoreHotel hotelBean;
	
	private HotelDTO hotel;
	
	@PostConstruct
	public void setUp () {
		hotel = new HotelDTO();
	}

	public HotelDTO getHotel() {
		return hotel;
	}

	public void setHotel(HotelDTO hotel) {
		this.hotel = hotel;
	}
	
	/**
	 * Ricerca gli hotel nella città selezionata
	 * @param citta Il nome della città
	 * @return L'elenco degli hotel trovati
	 */
	public List<HotelDTO> cercaHotel (String citta) {
		return hotelBean.elencoHotel(citta);
	}

	/**
	 * Permette di creare un nuovo hotel
	 */
	public void creaHotel () {
		try {
			hotelBean.creaHotel(hotel);
		} catch (EntityExistsException e) {
			FacesMessage messaggio = new FacesMessage(FacesMessage.SEVERITY_ERROR, "L'hotel è già presente nel database!", "L'hotel è già presente nel database!");
			FacesContext.getCurrentInstance().addMessage(null, messaggio);
		} catch (CittaInesistenteException e) {
			FacesMessage messaggio = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Città sconosciuta!", "Città sconosciuta!");
			FacesContext.getCurrentInstance().addMessage(null, messaggio);
		}
	}
	
	/**
	 * Permette la modifica di un hotel
	 * @param I dati dell'hotel
	 */
	public void modificaHotel (HotelDTO hotel) {
		try {
			hotelBean.modificaDatiHotel(hotel);
		} catch (CittaInesistenteException e) {
			FacesMessage messaggio = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Città sconosciuta!", "Città sconosciuta!");
			FacesContext.getCurrentInstance().addMessage(null, messaggio);
		} catch (HotelInesistenteException e) {
			FacesMessage messaggio = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hotel inesistente!", "Hotel inesistente!");
			FacesContext.getCurrentInstance().addMessage(null, messaggio);
		}
	}
	
	/**
	 * Permette l'eliminazione di un hotel
	 * @param hotel L'hotel da eliminare
	 */
	public void eliminaHotel (HotelDTO hotel) {
		try {
			hotelBean.eliminaHotel(hotel);
		} catch (HotelInesistenteException e) {
			FacesMessage messaggio = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hotel inesistente!", "Hotel inesistente!");
			FacesContext.getCurrentInstance().addMessage(null, messaggio);
		}
	}
}
