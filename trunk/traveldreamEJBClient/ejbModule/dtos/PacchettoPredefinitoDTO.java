package dtos;

import java.util.Date;
import java.util.List;

public class PacchettoPredefinitoDTO {

	private String nome;

	private double prezzo;

	private List<Date> datePartenza;

	private List<Integer> durate;

	private HotelDTO hotel;

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
