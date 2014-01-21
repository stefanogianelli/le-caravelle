package dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;

import enums.TipoPacchetto;

public class PacchettoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private String nome;

	@Min(value = 1, message="Il numero dei partecipanti deve essere maggiore o uguale a 1")
	private int numPartecipanti;

	private double prezzo;

	private List<DestinazioneDTO> destinazioni;
	
	private List<CollegamentoDTO> collegamenti;

	private CittaDTO citta;

	private PacchettoPredefinitoDTO pacchettoPredefinito;

	private UtenteDTO utente;
	
	private TipoPacchetto tipoPacchetto;
	
	private List<PersonaDTO> datiPartecipanti;
	
	public PacchettoDTO () {
		numPartecipanti = 1;
		destinazioni = new ArrayList<DestinazioneDTO>();
		collegamenti = new ArrayList<CollegamentoDTO>();
		citta = new CittaDTO();
		datiPartecipanti = new ArrayList<PersonaDTO>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getNumPartecipanti() {
		return numPartecipanti;
	}

	public void setNumPartecipanti(int numPartecipanti) {
		this.numPartecipanti = numPartecipanti;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public List<DestinazioneDTO> getDestinazioni() {
		return destinazioni;
	}

	public void setDestinazioni(List<DestinazioneDTO> destinazioni) {
		this.destinazioni = destinazioni;
	}

	public List<CollegamentoDTO> getCollegamenti() {
		return collegamenti;
	}

	public void setCollegamenti(List<CollegamentoDTO> collegamenti) {
		this.collegamenti = collegamenti;
	}

	public CittaDTO getCitta() {
		return citta;
	}

	public void setCitta(CittaDTO citta) {
		this.citta = citta;
	}

	public PacchettoPredefinitoDTO getPacchettoPredefinito() {
		return pacchettoPredefinito;
	}

	public void setPacchettoPredefinito(PacchettoPredefinitoDTO pacchettoPredefinito) {
		this.pacchettoPredefinito = pacchettoPredefinito;
	}

	public UtenteDTO getUtente() {
		return utente;
	}

	public void setUtente(UtenteDTO utente) {
		this.utente = utente;
	}

	public TipoPacchetto getTipoPacchetto() {
		return tipoPacchetto;
	}

	public void setTipoPacchetto(TipoPacchetto tipoPacchetto) {
		this.tipoPacchetto = tipoPacchetto;
	}

	public List<PersonaDTO> getDatiPartecipanti() {
		return datiPartecipanti;
	}

	public void setDatiPartecipanti(List<PersonaDTO> datiPartecipanti) {
		this.datiPartecipanti = datiPartecipanti;
	}
}
