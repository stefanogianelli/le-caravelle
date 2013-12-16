package ejbs;

import java.util.List;

import javax.ejb.Stateless;

import dtos.HotelDTO;

/**
 * Session Bean implementation class GestoreHotelEJB
 */
@Stateless
public class GestoreHotelEJB implements GestoreHotel {

    /**
     * Default constructor. 
     */
    public GestoreHotelEJB() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public List<HotelDTO> elencoHotel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HotelDTO dettagliHotel(int idHotel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void creaHotel(HotelDTO hotel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificaDatiHotel(HotelDTO hotel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminaHotel(int idHotel) {
		// TODO Auto-generated method stub
		
	}

}
