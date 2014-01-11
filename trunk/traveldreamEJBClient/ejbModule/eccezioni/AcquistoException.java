package eccezioni;

public class AcquistoException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public AcquistoException () {
		super();
	}
	
	public AcquistoException (String messaggio) {
		super(messaggio);
	}

}
