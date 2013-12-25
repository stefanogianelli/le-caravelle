package enums;

public enum CategoriaEscursione {
	SPORT("Sport"),
	CULTURA("Cultura"),
	RELAX("Relax"),
	MARE("Mare"),
	MONTAGNA("Montagna"),
	ALTRO("Altro");
	
	private String label;
	
	private CategoriaEscursione (String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
