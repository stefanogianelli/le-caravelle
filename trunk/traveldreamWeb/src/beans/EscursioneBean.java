package beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import utils.JsfUtil;
import dtos.EscursioneDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.EntitaEsistenteException;
import eccezioni.EscursioneInesistenteException;
import ejbs.GestoreCitta;
import ejbs.GestoreEscursione;
import enums.CategoriaEscursione;

@ManagedBean(name="escursione")
@ViewScoped
public class EscursioneBean {
	
	@EJB
	private GestoreEscursione escursioneBean;
	
	@EJB
	private GestoreCitta cittaBean;
	
	private EscursioneDTO escursione;
	private List<EscursioneDTO> elenco;
	private Date data;
	private String regione;
	private String categoria;
	private PaginatorBean paginator;
	
	@PostConstruct
	public void setUp () {
		escursione = new EscursioneDTO();
		elenco = new ArrayList<EscursioneDTO>();
	}
	
	public EscursioneDTO getEscursione() {
		return escursione;
	}

	public void setEscursione(EscursioneDTO escursione) {
		this.escursione = escursione;
	}

	public List<EscursioneDTO> getElenco() {
		return elenco;
	}

	public void setElenco(List<EscursioneDTO> elenco) {
		this.elenco = elenco;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getRegione() {
		return regione;
	}

	public void setRegione(String regione) {
		this.regione = regione;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public PaginatorBean getPaginator() {
		return paginator;
	}

	/**
	 * Fornisce l'elenco di categorie disponibili
	 * @return Le categorie di un'escursione
	 */
	public List<String> getCategoriePerRicerca () {
		List<String> categorie = new ArrayList<String>();
		categorie.add("Qualsiasi");
		for (CategoriaEscursione c : CategoriaEscursione.values()) {
			categorie.add(c.getLabel());
		}
		return categorie;
	}
	
	public CategoriaEscursione [] getCategorie () {
		return CategoriaEscursione.values();
	}
	
	/**
	 * Permette la ricerca di una escursione tramite il suo identificativo
	 * @param idEscursione L'identificativo dell'escursione
	 */
	public void getEscursione (int idEscursione) {
		try {
			this.setEscursione(escursioneBean.getEscursione(idEscursione));
		} catch (EscursioneInesistenteException e) {
			JsfUtil.errorMessage("Escursione inesistente!");
		}
	}
	
	/**
	 * Elenca tutte le escursioni presenti nel database
	 * @param force Per forzare la generazione di un nuovo elenco
	 */
	public void elencoEscursioni (boolean force) {
		if (paginator == null || force == true)
			paginator = new PaginatorBean(escursioneBean.elencoEscursioni());
	}

	/**
	 * Ricerca le escursioni nella regione selezionata
	 */
	public void cercaEscursioni () {
		List<EscursioneDTO> lista = escursioneBean.elencoEscursioni(this.getData(), this.getRegione(), this.getCategoria());
		if (lista.isEmpty())
			JsfUtil.infoMessage("Nessun risultato");
		else
			this.setElenco(lista);
	}
	
	public void cercaPerNomeCitta (String nome) {
		try {
			this.setRegione(cittaBean.cercaCitta(nome).getRegione());
			List<EscursioneDTO> lista = escursioneBean.elencoEscursioni(null, this.getRegione(), null);
			if (lista.isEmpty())
				JsfUtil.infoMessage("Nessun risultato");
			else {
				paginator = new PaginatorBean (lista);
			}			
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città inesistente");
		}	
	}
	
	/**
	 * Permette la creazione di una nuova escursione
	 * @return L'indirizzo della pagina con i dettagli dell'escursione creata
	 */
	public String creaEscursione () {
		try {
			return "dettagliEscursione?idEscursione=" + escursioneBean.creaEscursione(this.getEscursione()) + "&faces-redirect=true";
		} catch (EntitaEsistenteException e) {
			JsfUtil.errorMessage("L'escursione è già presente nel database!");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Regione sconosciuta!");
		}
		return null;
	}
	
	/**
	 * Permette la modifica dei dati di una escursione
	 * @return L'indirizzo della pagina con i dettagli dell'escursione
	 */
	public String modificaEscursione () {
		try {
			escursioneBean.modificaDatiEscursione(this.getEscursione());
			return "dettagliEscursione?idEscursione=" + this.getEscursione().getId() + "&faces-redirect=true";
		} catch (EscursioneInesistenteException e) {
			JsfUtil.errorMessage("Escursione inesistente!");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Regione sconosciuta!");
		}
		return null;
	}
	
	/**
	 * Permette l'eliminazione di un'escursione
	 * @param idEscursione L'identificativo dell'escursione da eliminare
	 * @return L'indirizzo della pagina con l'elenco delle escursioni
	 */
	public String eliminaEscursione (int idEscursione) {
		try {
			escursioneBean.eliminaEscursione(idEscursione);
			return "elencoEscursioni?faces-redirect=true";
		} catch (EscursioneInesistenteException e) {
			JsfUtil.errorMessage("Escursione inesistente!");
		}
		return null;
	}

}
