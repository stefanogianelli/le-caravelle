package eccezioni;

public class InsertException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InsertException () {
		super();
	}
	
	public InsertException (String messaggio) {
		super(messaggio);
	}

}
