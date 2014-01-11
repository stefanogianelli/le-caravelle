package beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import utils.JsfUtil;
import dtos.PersonaDTO;
import dtos.UtenteDTO;
import eccezioni.AcquistoException;
import eccezioni.PacchettoInesistenteException;
import ejbs.GestorePacchetto;
import ejbs.GestoreProfilo;

@ManagedBean(name="datiPartecipanti")
@ViewScoped
public class DatiPartecipantiBean {
	
	@EJB
	private GestoreProfilo profiloBean;
	
	@EJB
	private GestorePacchetto pacchettoBean;
	
	private int idPacchetto;
	private boolean insertUserData;
	private int numeroPartecipanti;
	private UtenteDTO utente;
	private List<PersonaDTO> dati;
	
	public void setUp () {
		utente = new UtenteDTO();
		dati = new ArrayList<PersonaDTO>();
		for (int i = 0; i < numeroPartecipanti; i++) {
			dati.add(new PersonaDTO());
		}
		if (!this.isInsertUserData() && this.getNumeroPartecipanti() == 0)
			this.acquista();
	}
	
	public int getIdPacchetto() {
		return idPacchetto;
	}

	public void setIdPacchetto(int idPacchetto) {
		this.idPacchetto = idPacchetto;
	}

	public boolean isInsertUserData() {
		return insertUserData;
	}

	public void setInsertUserData(boolean insertUserData) {
		this.insertUserData = insertUserData;
	}

	public int getNumeroPartecipanti() {
		return numeroPartecipanti;
	}
	
	public void setNumeroPartecipanti(int numeroPartecipanti) {
		this.numeroPartecipanti = numeroPartecipanti;
	}
	
	public UtenteDTO getUtente() {
		return utente;
	}
	
	public void setUtente(UtenteDTO utente) {
		this.utente = utente;
	}
	
	public List<PersonaDTO> getDati() {
		return dati;
	}
	
	public void setDati(List<PersonaDTO> dati) {
		this.dati = dati;
	}
	
	/**
	 * Permette l'acquisto di un pacchetto
	 * @return l'indirizzo della pagina personale dell'utente
	 */
	public String acquista () {
		//salvo i dati dell'utente (se necessario)
		if (this.isInsertUserData())
			profiloBean.aggiuntaDatiPersonali(getUtente());
		try {
			pacchettoBean.acquistaPacchetto(getIdPacchetto(), getDati());
			return "areaCliente?faces-redirect=true";
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente");
		} catch (AcquistoException e) {
			JsfUtil.errorMessage(e.getMessage());
		}
		return null;
	}
}
