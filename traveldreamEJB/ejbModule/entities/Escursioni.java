package entities;

import java.io.Serializable;

import javax.persistence.*;

import enums.CategoriaEscursione;

import java.sql.Time;
import java.util.Date;


/**
 * The persistent class for the escursioni database table.
 * 
 */
@Entity
@NamedQueries ({
	@NamedQuery(name = "Escursioni.elenco", query = "SELECT e FROM Escursioni e"),
	@NamedQuery(name = "Escursioni.getEscursione", query = "SELECT e FROM Escursioni e WHERE e.id = :id"),
	@NamedQuery(name = "Escursioni.elencoPerRegione", query = "SELECT e FROM Escursioni e WHERE e.citta.regione = :regione"),
	@NamedQuery(name = "Escursioni.elencoPerPeriodo", query = "SELECT e FROM Escursioni e WHERE e.data >= :dataArrivo AND e.data <= :dataPartenza AND e.citta.regione = :regione")
})
public class Escursioni implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String nome;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	
	private Time ora;
	
	private int durata;

    @Enumerated(EnumType.STRING)
    private CategoriaEscursione categoria;

    private double prezzo;	

    @ManyToOne
    @JoinColumn(name="idCitta")
	private Citta citta;		

	public Escursioni() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

    public CategoriaEscursione getCategoria() {
        return this.categoria;
    }

    public void setCategoria(CategoriaEscursione categoria) {
        this.categoria = categoria;
    }

	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getDurata() {
		return this.durata;
	}

	public void setDurata(int durata) {
		this.durata = durata;
	}

	public Citta getCitta() {
		return this.citta;
	}

	public void setCitta(Citta citta) {
		this.citta = citta;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Time getOra() {
		return this.ora;
	}

	public void setOra(Time ora) {
		this.ora = ora;
	}

	public double getPrezzo() {
		return this.prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}
	
}