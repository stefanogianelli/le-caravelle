package eccezioni;

public class HotelInesistenteException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public HotelInesistenteException () {
		super();
	}
	
	public HotelInesistenteException (String messaggio) {
		super(messaggio);
	}

}
