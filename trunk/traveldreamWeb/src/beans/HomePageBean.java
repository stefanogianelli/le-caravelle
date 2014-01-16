package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name="home")
@ViewScoped
public class HomePageBean {

	private String citta;
	private String cittaPred;
	
	public String getCitta() {
		return citta;
	}
	
	public void setCitta(String citta) {
		this.citta = citta;
	}
	
	public String getCittaPred() {
		return cittaPred;
	}
	
	public void setCittaPred(String cittaPred) {
		this.cittaPred = cittaPred;
	}
	
	public String creaPacchetto () {
		return "/utente/creazionePacchetto.xhtml?citta=" + this.getCitta() + "&faces-redirect=true";
	}
	
	public String cercaPacchettoPred () {
		return "pacchettiPredefiniti.xhtml?citta=" + this.getCittaPred() + "&faces-redirect=true";
	}
	
}
