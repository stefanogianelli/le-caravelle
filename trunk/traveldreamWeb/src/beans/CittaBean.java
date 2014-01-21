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
import dtos.CittaDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.InsertException;
import eccezioni.UploadException;
import ejbs.GestoreCitta;

@ManagedBean(name="citta")
@ViewScoped
public class CittaBean {

	@EJB
	private GestoreCitta cittaBean;
	
	private PaginatorBean paginator;
	private CittaDTO citta;
	private Part immagine;
	
	@PostConstruct
	public void setUp () {
		citta = new CittaDTO();
	}
	
	public PaginatorBean getPaginator() {
		return paginator;
	}

	public CittaDTO getCitta() {
		return citta;
	}

	public void setCitta(CittaDTO citta) {
		this.citta = citta;
	}

	public Part getImmagine() {
		return immagine;
	}

	public void setImmagine(Part immagine) {
		this.immagine = immagine;
	}
	
	/**
	 * Carica i dati di una citt�
	 * @param idCitta L'identificativo della citt�
	 */
	public void getCitta(int idCitta){
		try {
			this.setCitta(cittaBean.getCitta(idCitta));
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Citt� inesistente");
		}
	}

	/**
	 * Mostra l'elenco di citt� presenti nel database
	 * @return I nomi delle citt�
	 */
	public List<String> getNomiCitta () {
		List<CittaDTO> citta = cittaBean.elencoCitta();
		List<String> nomi = new ArrayList<String>();
		for (CittaDTO c : citta) {
			nomi.add(c.getNome());
		}
		return nomi;
	}
	
	/**
	 * Mostra l'elenco di citt� presenti nel database pi� il campo "Qualsiasi" per i campi di ricerca
	 * @return I nomi delle citt�
	 */
	public List<String> getNomiCittaPerRicerca () {
		List<CittaDTO> citta = cittaBean.elencoCitta();
		List<String> nomi = new ArrayList<String>();
		nomi.add("Qualsiasi");
		for (CittaDTO c : citta) {
			nomi.add(c.getNome());
		}
		return nomi;
	}	
	
	/**
	 * Mostra tutte le citt� presenti nel database
	 * @param force Per forzare la generazione di un nuovo elenco
	 */
	public void elencoCitta (boolean force) {
		if (paginator == null || force == true)
			paginator = new PaginatorBean(cittaBean.elencoCitta());
	}
	
	/**
	 * Permette di inserire una nuova citt� nel database
	 */
	public void nuovaCitta () {
		try {
			if (getImmagine().getSize() != 0) {
				FileUtils file = new FileUtils();
				getCitta().addImmagine(file.upload(getImmagine(), "citta"));
			}
			cittaBean.nuovaCitta(getCitta());
			JsfUtil.infoMessage("Citt� aggiunta con successo");
		} catch (UploadException e) {
			JsfUtil.errorMessage("Si � verificato un errore durante il caricamento dell'immagine");
		} catch (InsertException e) {
			JsfUtil.errorMessage("Citt� gi� presente nel database");
		}
	}	

	/**
	 * Permette di modificare una citt� nel database
	 */
	public void modificaCitta () {
		try {
			cittaBean.modificaCitta(getCitta());
			JsfUtil.infoMessage("Citt� modificata");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Citt� sconosciuta");
		} catch (InsertException e) {
			JsfUtil.errorMessage("Esiste un'altra citt� con lo stesso nome");
		}
	}
	
	/**
	 * Permette di aggiungere una nuova immagine della citt�
	 */
	public void aggiuntaImmagine () {
		try {
			cittaBean.aggiuntaImmagine(getCitta().getId(), getImmagine());
			JsfUtil.infoMessage("Immagine caricata con successo");
		} catch (UploadException e) {
			JsfUtil.errorMessage("Si � verificato un errore durante il caricamento dell'immagine");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Citt� sconosciuta");
		} catch (InsertException e) {
			JsfUtil.errorMessage("Nessuna immagine selezionata");
		}
	}
	
	/**
	 * Permette di rimuovere un'immagine
	 * @param nomeImmagine Il nome dell'immagine da rimuovere
	 */
	public void rimuoviImmagine (String nomeImmagine) {
		try {
			cittaBean.rimuoviImmagine(getCitta().getId(), nomeImmagine);
			JsfUtil.infoMessage("Immagine rimossa");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Citt� sconosciuta");
		}
	}
	
	/**
	 * Permette di eliminare una citt� nel database
	 * @param idCitta L'identificativo della citt�
	 */
	public void eliminaCitta (int idCitta) {
		try {
			cittaBean.eliminaCitta(idCitta);
			JsfUtil.infoMessage("Citt� eliminata");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Citt� sconosciuta");
		}
	}
}
