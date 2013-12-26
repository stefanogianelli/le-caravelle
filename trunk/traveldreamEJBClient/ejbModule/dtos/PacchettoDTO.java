package dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PacchettoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private String nome;

	private int numPartecipanti;

	private double prezzo;

	private List<DestinazioneDTO> destinazioni;

	private CittaDTO citta;

	private PacchettoPredefinitoDTO pacchettoPredefinito;

	private UtenteDTO utente;
	
	/*
	 * Utilizzato per la modifica
	 */
	private boolean editable;
	
	public PacchettoDTO () {
		destinazioni = new ArrayList<DestinazioneDTO>();
		citta = new CittaDTO();
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

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
}
