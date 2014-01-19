package eccezioni;

public class UploadException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public UploadException () {
		super();
	}
	
	public UploadException (String messaggio) {
		super(messaggio);
	}

}
