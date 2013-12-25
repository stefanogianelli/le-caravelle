package enums;

public enum TipoCollegamento {
	AEREO("Aereo");
	
	private String label;
	
	private TipoCollegamento (String label) {
		this.label = label;
	}
	
	public String getLabel () {
		return label;
	}
}
