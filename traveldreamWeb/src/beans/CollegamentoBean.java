package beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityExistsException;

import utils.JsfUtil;
import dtos.CittaDTO;
import dtos.CollegamentoDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.CollegamentoInesistenteException;
import eccezioni.PacchettoInesistenteException;
import ejbs.GestoreCitta;
import ejbs.GestoreCollegamento;
import ejbs.GestorePacchettoPredefinito;
import enums.TipoCollegamento;

@ManagedBean(name="collegamento")
@ViewScoped
public class CollegamentoBean {

	@EJB
	private GestoreCollegamento collegamentoBean;
	
	@EJB
	private GestoreCitta cittaBean;
	
	@EJB
	private GestorePacchettoPredefinito predefinitoBean;
	
	private CollegamentoDTO collegamento;
	private List<CollegamentoDTO> elenco;
	private int idPacchetto;
	private Date dataPartenza;
	private String cittaPartenza;
	private String cittaArrivo;
	private PaginatorBean paginator;
	
	@PostConstruct
	public void setUp () {
		collegamento = new CollegamentoDTO();
		elenco = new ArrayList<CollegamentoDTO>();
	}
	
	public CollegamentoDTO getCollegamento() {
		return collegamento;
	}

	public void setCollegamento(CollegamentoDTO collegamento) {
		this.collegamento = collegamento;
	}

	public List<CollegamentoDTO> getElenco() {
		return elenco;
	}

	public void setElenco(List<CollegamentoDTO> elenco) {
		this.elenco = elenco;
	}

	public int getIdPacchetto() {
		return idPacchetto;
	}

	public void setIdPacchetto(int idPacchetto) {
		this.idPacchetto = idPacchetto;
	}

	public Date getDataPartenza() {
		return dataPartenza;
	}

	public void setDataPartenza(Date dataPartenza) {
		this.dataPartenza = dataPartenza;
	}

	public String getCittaPartenza() {
		return cittaPartenza;
	}

	public void setCittaPartenza(String cittaPartenza) {
		this.cittaPartenza = cittaPartenza;
	}

	public String getCittaArrivo() {
		return cittaArrivo;
	}

	public void setCittaArrivo(String cittaArrivo) {
		this.cittaArrivo = cittaArrivo;
	}

	public PaginatorBean getPaginator() {
		return paginator;
	}

	/**
	 * Ritorna l'elenco delle tipologie di collegamento
	 * @return Le tipologie di collegamento disponibili
	 */
	public TipoCollegamento [] getTipiCollegamento () {
		return TipoCollegamento.values();
	}
	
	/**
	 * Mostra l'elenco delle origini nella città di partenza
	 * @return L'elenco delle origini
	 */
	public List<String> elencoOrigini () {
		List<String> elenco = new ArrayList<String>();
		elenco.add("Qualsiasi");
		elenco.addAll(collegamentoBean.getOrigini(this.getCittaPartenza()));
		return elenco;
	}	
	
	/**
	 * Mostra l'elenco delle destinazioni nella città di arrivo
	 * @return L'elenco delle destinazioni
	 */
	public List<String> elencoDestinazioni () {
		List<String> elenco = new ArrayList<String>();
		elenco.add("Qualsiasi");
		elenco.addAll(collegamentoBean.getDestinazioni(this.getCittaArrivo()));
		return elenco;
	}
	
	/**
	 * Permette la ricerca di un collegamento tramite il suo codice
	 * @param codiceCollegamento Il codice del collegamento
	 */
	public void getCollegamento (int codiceCollegamento) {
		try {
			this.setCollegamento(collegamentoBean.getCollegamento(codiceCollegamento));
		} catch (CollegamentoInesistenteException e) {
			JsfUtil.errorMessage("Collegamento inesistente");
		}
	}
	
	/**
	 * Elenca tutti i collegamenti presenti nel database
	 * @param force Per forzare l'aggiornamento della lista
	 */
	public void elencoCollegamenti (boolean force) {
		if (this.getPaginator() == null || force == true) {
			paginator = new PaginatorBean(this.collegamentoBean.elencoCollegamenti());
		}
	}
	
	/**
	 * Ricerca i collegamenti di andata e ritorno verso tutte le combinazioni di citta di partenza e arrivo
	 * @param cittaPartenza L'elenco delle città di partenza
	 * @param cittaArrivo La città di arrivo
	 */
	public void elencoCollegamentiDipendente (List<CittaDTO> cittaPartenza, String cittaArrivo) {
		for (CittaDTO c : cittaPartenza) {
			this.getElenco().addAll(this.collegamentoBean.elencoCollegamenti(null, c.getNome(), cittaArrivo, null, null, null));
			this.getElenco().addAll(this.collegamentoBean.elencoCollegamenti(null, cittaArrivo, c.getNome(), null, null, null));
		}
		Collections.sort(this.getElenco());
	}

	/**
	 * Ricerca i collegamenti disponibili tra due destinazioni nella data indicata
	 * @param tipo La tipologia del collegamento
	 * @param origine L'origine del collegamento
	 * @param destinazione La destinazione del collegamento
	 */
	public void cercaCollegamenti (TipoCollegamento tipo, String origine, String destinazione) {
		this.getElenco().clear();
		this.getElenco().addAll(this.collegamentoBean.elencoCollegamenti(this.getDataPartenza(), this.getCittaPartenza(), this.getCittaArrivo(), tipo, origine, destinazione));
		if (this.getElenco().isEmpty())
			JsfUtil.infoMessage("Nessun risultato");
	}
	
	/**
	 * Ricerca i collegamenti disponibili tra le città selezionate
	 * @param partenza La città di partenza
	 * @param arrivo La città di arrivo
	 */
	public void cercaCollegamenti (String partenza, String arrivo) {
		List<CollegamentoDTO> lista = collegamentoBean.elencoCollegamenti(null, partenza, arrivo, null, null, null);
		if (lista.isEmpty())
			JsfUtil.infoMessage("Nessun risultato");
		else
			paginator = new PaginatorBean(lista);
	}
	
	/**
	 * Ricerca i collegamenti inseriti nel pacchetto predefinito tra due destinazioni nella data indicata
	 * @param idPacchetto L'identificativo del pacchetto predefinito nel quale effettuare la ricerca
	 * @param tipo La tipologia del collegamento
	 * @param origine L'origine del collegamento
	 * @param destinazione La destinazione del collegamento
	 */
	public void cercaCollegamentiPred (int idPacchetto, TipoCollegamento tipo, String origine, String destinazione) {
		try {
			List<CollegamentoDTO> col = predefinitoBean.getPacchetto(idPacchetto).getCollegamenti();
			this.getElenco().clear();
			for (CollegamentoDTO c : col) {
				if (c.getDataPartenza().equals(this.getDataPartenza()) && c.getCittaPartenza().getNome().equals(this.getCittaPartenza()) && c.getCittaArrivo().getNome().equals(this.getCittaArrivo()) && c.getTipoCollegamento() == tipo)
					if (origine != null && destinazione != null && !origine.equals("Qualsiasi") && !destinazione.equals("Qualsiasi")) {
						if (c.getOrigine().equals(origine) && c.getDestinazione().equals(destinazione))
							this.getElenco().add(c);
					} else if (origine != null && !origine.equals("Qualsiasi")) {
						if (c.getOrigine().equals(origine))
							this.getElenco().add(c);
					} else if (destinazione != null && !destinazione.equals("Qualsiasi")) {
						if (c.getDestinazione().equals(destinazione))
							this.getElenco().add(c);
					} else
						this.getElenco().add(c);				
			}
			if (this.getElenco().isEmpty())
				JsfUtil.infoMessage("Nessun risultato");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente");
		}
	}
	
	/**
	 * Permette la creazione di un nuovo collegamento
	 * @return L'indirizzo della pagina con i dettagli del collegamento creato
	 */
	public String creaCollegamento () {
		try {
			//controllo che la città di partenza e di arrivo non siano uguali
			if (!this.getCollegamento().getCittaPartenza().equals(this.getCollegamento().getCittaArrivo()))
				return "dettagliCollegamento?codiceCollegamento=" + collegamentoBean.creaCollegamento(this.getCollegamento()) + "&faces-redirect=true";
			else
				JsfUtil.errorMessage("La città di partenza e di arrivo devono essere diverse");
		} catch (EntityExistsException e) {
			JsfUtil.errorMessage("Il collegamento è già presente nel database");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta");
		}
		return null;
	}
	
	/**
	 * Permette la modifica dei dati di un collegamento esistente
	 * @return L'indirizzo della pagina con i dettagli del collegamento
	 */
	public String modificaCollegamento () {
		try {
			collegamentoBean.modificaDatiCollegamento(this.getCollegamento());
			return "dettagliCollegamento?codiceCollegamento=" + this.getCollegamento().getCodice() + "&faces-redirect=true";
		} catch (CollegamentoInesistenteException e) {
			JsfUtil.errorMessage("Collegamento inesistente");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta");
		}
		return null;
	}
	
	/**
	 * Permette l'eliminazione di un collegamento
	 * @param codiceCollegamento Il codice del collegamento da eliminare
	 * @return L'indirizzo della pagina con l'elenco dei collegamenti
	 */
	public String eliminaCollegamento (int codiceCollegamento) {
		try {
			collegamentoBean.eliminaCollegamento(codiceCollegamento);
			return "elencoCollegamenti?faces-redirect=true";
		} catch (CollegamentoInesistenteException e) {
			JsfUtil.errorMessage("Collegamento inesistente");
		}
		return null;
	}	
	
	/**
	 * Verifica se il collegamento è attivo
	 * @return true se il collegamento è attivo, false altrimenti
	 */
	public boolean isAttivo () {
		if (getCollegamento().getAttivo() == 1)
			return true;
		else
			return false;
	}
}
