package dtos;

import java.util.List;

public class PacchettoDTO {

	private String nome;

	private int numPartecipanti;

	private double prezzo;

	private String tipoPacchetto;

	private List<DestinazioneDTO> destinazioni;

	private CittaDTO citta;

	private PacchettoPredefinitoDTO pacchettoPredefinito;

	private UtenteDTO utente;

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

	public String getTipoPacchetto() {
		return tipoPacchetto;
	}

	public void setTipoPacchetto(String tipoPacchetto) {
		this.tipoPacchetto = tipoPacchetto;
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
}
