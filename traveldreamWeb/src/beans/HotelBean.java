package beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import utils.JsfUtil;
import dtos.HotelDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.EntitaEsistenteException;
import eccezioni.HotelInesistenteException;
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
	 * Ricerca gli hotel nella città selezionata
	 * @param citta Il nome della città
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
		File file = null;
        OutputStream output = null; 
        
		try {   
			if (getImmagine() != null) {
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				
				String prefix = FilenameUtils.getBaseName(getFilename(getImmagine()));
			    String suffix = FilenameUtils.getExtension(getFilename(getImmagine()));	        
		        
		        String relativeWebPath = "/resources/images/hotel/";
		    	String absoluteDiskPath = externalContext.getRealPath(relativeWebPath);
	            	
				file = File.createTempFile(prefix + "_", "." + suffix, new File(absoluteDiskPath));
				output = new FileOutputStream(file);
				IOUtils.copy(getImmagine().getInputStream(), output);
				getHotel().setImmagine(file.getName());
			}
			return "dettagliHotel?idHotel=" + hotelBean.creaHotel(this.getHotel()) + "&faces-redirect=true";
		} catch (IOException e) {
			if (file != null) file.delete();
			e.printStackTrace();
		} catch (EntitaEsistenteException e) {
			JsfUtil.errorMessage("L'hotel è già presente nel database");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta");
		} finally {
            IOUtils.closeQuietly(output);
		}
		return null;
	}
	
	/**
	 * Permette la modifica di un hotel
	 * @return L'indirizzo della pagina dettagli associata all'hotel
	 */
	public String modificaHotel () {
		try {
			hotelBean.modificaDatiHotel(this.getHotel());			
			return "dettagliHotel?idHotel=" + this.getHotel().getId() + "&faces-redirect=true";
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta");
		} catch (HotelInesistenteException e) {
			JsfUtil.errorMessage("Hotel sconosciuto");
		} catch (EntitaEsistenteException e) {
			JsfUtil.errorMessage("I dati inseriti sono uguali a quelli di un altro hotel");
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
			JsfUtil.errorMessage("Hotel sconosciuta");
		}
		return null;
	}
	
    private static String getFilename(Part part) {  
        for (String cd : part.getHeader("content-disposition").split(";")) {  
            if (cd.trim().startsWith("filename")) {  
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");  
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.  
            }  
        }  
        return null;  
    } 
}
