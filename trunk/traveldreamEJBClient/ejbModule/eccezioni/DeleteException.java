package eccezioni;

public class DeleteException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public DeleteException () {
		super();
	}
	
	public DeleteException (String messaggio) {
		super(messaggio);
	}

}
