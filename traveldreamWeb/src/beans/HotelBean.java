package beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;

import utils.FileUtils;
import utils.JsfUtil;
import dtos.HotelDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.EntitaEsistenteException;
import eccezioni.HotelInesistenteException;
import eccezioni.UploadException;
import ejbs.GestoreCitta;
import ejbs.GestoreHotel;

@ManagedBean(name="hotel")
@ViewScoped
public class HotelBean {

	@EJB
	private GestoreHotel hotelBean;
	
	@EJB
	private GestoreCitta cittaBean;
	
	private HotelDTO hotel;
	private List<HotelDTO> elenco;
	private PaginatorBean paginator;
    private Part immagine;
	
	@PostConstruct
	public void setUp () {
		hotel = new HotelDTO();
		elenco = new ArrayList<HotelDTO>();
	}

	public HotelDTO getHotel() {
		return hotel;
	}

	public void setHotel(HotelDTO hotel) {
		this.hotel = hotel;
	}
	
	public List<HotelDTO> getElenco() {
		return elenco;
	}

	public void setElenco(List<HotelDTO> elenco) {
		this.elenco = elenco;
	}
	
	public PaginatorBean getPaginator() {
		return paginator;
	}

	public Part getImmagine() {
		return immagine;
	}

	public void setImmagine(Part immagine) {
		this.immagine = immagine;
	}

	/**
	 * Permette la ricerca di un hotel tramite il suo identificativo
	 * @param idHotel L'identificativo dell'hotel
	 */
	public void getHotel (int idHotel) {
		try {
			this.setHotel(hotelBean.getHotel(idHotel));
		} catch (HotelInesistenteException e) {
			JsfUtil.errorMessage("Hotel sconosciuto");
		}
	}

	/**
	 * Mostra tutti gli hotel presenti nel database
	 * @param force Per forzare la generazione di un nuovo elenco
	 */
	public void elencoHotel (boolean force) {
		if (paginator == null || force == true)
			paginator = new PaginatorBean(hotelBean.elencoHotel());
	}

	/**
	 * Ricerca gli hotel nella citt� selezionata
	 * @param citta Il nome della citt�
	 */
	public void cercaHotel (String citta) {
		List<HotelDTO> lista = hotelBean.elencoHotel(citta);
		if (lista.isEmpty())
			JsfUtil.infoMessage("Nessun risultato");
		else {
			paginator = new PaginatorBean(lista);
			this.setElenco(lista);
		}
	}

	/**
	 * Permette di creare un nuovo hotel
	 * @return L'indirizzo della pagina dettagli dell'hotel creato
	 */
	public String creaHotel () {
		try {
			if (getImmagine().getSize() != 0) {
				FileUtils file = new FileUtils();
				getHotel().setImmagine(file.upload(getImmagine(), "hotel"));
			}
			return "dettagliHotel?idHotel=" + hotelBean.creaHotel(this.getHotel()) + "&faces-redirect=true";
		} catch (EntitaEsistenteException e) {
			JsfUtil.errorMessage("L'hotel � gi� presente nel database");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Citt� sconosciuta");
		} catch (UploadException e) {
			JsfUtil.errorMessage("Si � verificato un errore durante il caricamento dell'immagine");
		}
		return null;
	}
	
	/**
	 * Permette la modifica di un hotel
	 * @return L'indirizzo della pagina dettagli associata all'hotel
	 */
	public String modificaHotel () {
		try {
			if (getImmagine().getSize() != 0) {
				FileUtils file = new FileUtils();
				getHotel().setImmagine(file.upload(getImmagine(), "hotel"));
			}
			hotelBean.modificaDatiHotel(this.getHotel());			
			return "dettagliHotel?idHotel=" + this.getHotel().getId() + "&faces-redirect=true";
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Citt� sconosciuta");
		} catch (HotelInesistenteException e) {
			JsfUtil.errorMessage("Hotel sconosciuto");
		} catch (EntitaEsistenteException e) {
			JsfUtil.errorMessage("I dati inseriti sono uguali a quelli di un altro hotel");
		} catch (UploadException e) {
			JsfUtil.errorMessage("Si � verificato un errore durante il caricamento dell'immagine");
		}
		return null;
	}
	
	/**
	 * Permette l'eliminazione di un hotel
	 * @param idHotel L'identificativo dell'hotel da eliminare
	 * @return L'indirizzo della pagina con l'elenco degli hotel
	 */
	public String eliminaHotel (int idHotel) {
		try {
			hotelBean.eliminaHotel(idHotel);
			return "elencoHotel?faces-redirect=true";
		} catch (HotelInesistenteException e) {
			JsfUtil.errorMessage("Hotel sconosciuto");
		}
		return null;
	}
}
