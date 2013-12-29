package dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

public class PacchettoPredefinitoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	@NotEmpty
	private String nome;

	private double prezzo;
	
	private List<CittaDTO> cittaPartenza;

	private List<Date> datePartenza;

	private List<Integer> durate;

	private HotelDTO hotel;
	
	public PacchettoPredefinitoDTO () {
		cittaPartenza = new ArrayList<CittaDTO>();
		datePartenza = new ArrayList<Date>();
		durate = new ArrayList<Integer>();
		hotel = new HotelDTO();
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

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public List<CittaDTO> getCittaPartenza() {
		return cittaPartenza;
	}

	public void setCittaPartenza(List<CittaDTO> cittaPartenza) {
		this.cittaPartenza = cittaPartenza;
	}

	public List<Date> getDatePartenza() {
		return datePartenza;
	}

	public void setDatePartenza(List<Date> datePartenza) {
		this.datePartenza = datePartenza;
	}

	public List<Integer> getDurate() {
		return durate;
	}

	public void setDurate(List<Integer> durate) {
		this.durate = durate;
	}

	public HotelDTO getHotel() {
		return hotel;
	}

	public void setHotel(HotelDTO hotel) {
		this.hotel = hotel;
	}
}
