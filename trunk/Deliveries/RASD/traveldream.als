module TravelDram

/**
 * TIPI DI DATO
*/

//Stringa
abstract sig Stringa { }

//Città
abstract sig Citta { }

//Data
abstract sig Data { }

//Telefono
abstract sig Telefono { }

//Prezzo
abstract sig Prezzo { }

//Ora
abstract sig Ora { }

/**
 * ENUMERAZIONI
*/

//Tipo del Collegamento
enum TipoCollegamento
{
	aereo
}

//Stelle (riferito alla categoria di hotel)
enum Stelle
{
	una, due, tre, quattro, cinque
}

/**
 * ENTITA'
*/

//Persona
abstract sig Persona
{
	nome: one Stringa,
	cognome: one Stringa,
	documentoIdentita: one Stringa,
	dataNascita: one Data,
	telefono: one Telefono
}

//Utente
sig Utente extends Persona
{
	mail: one Stringa,
	password: one Stringa,
	pacchettiPersonalizzati: set PacchettoPersonalizzato,
	pacchettiPredefiniti: set PacchettoPredefinitoUtente,
	pacchettiCondivisi: set PacchettoCondiviso,
	pacchettiAcquistati: set PacchettoAcquistato
}

//Amico
sig Amico
{
	mail: one Stringa,
	pacchettiCondivisi: some PacchettoCondiviso
}

//Dipendente
sig Dipendente extends Persona
{
	nomeUtente: one Stringa,
	password: one Stringa,
	pacchetti: set PacchettoPredefinito,
	collegamenti: set Collegamento,
	hotel: set Hotel,
	escursioni: set Escursione	
}

//DatiPartecipante
sig DatiPartecipante extends Persona { }

//Pacchetto
abstract sig Pacchetto
{
	nome: one Stringa,
	prezzo: one Prezzo,
	numeroPartecipanti: one Int,
	cittaPartenza: one Citta,
	partecipanti: some DatiPartecipante,
	destinazioni: some Destinazione,
	collegamenti: some Collegamento	
}
{
	numeroPartecipanti > 0
	//la cardinalità dei dati dei partecipanti al viaggio deve essere pari al numero di partecipanti meno uno (l'utente, di cui conosciamo già i dati)
	#partecipanti = sub[numeroPartecipanti, 1]
}

//Pacchetto Personalizzato
sig PacchettoPersonalizzato extends Pacchetto { }

//Pacchetto Predefinito Utente
sig PacchettoPredefinitoUtente extends Pacchetto { }

//Pacchetto Acquistato
sig PacchettoAcquistato extends Pacchetto { }

//Pacchetto Condiviso
sig PacchettoCondiviso extends Pacchetto
{
	condivisoCon: some Amico
}

//Pacchetto Predefinito
sig PacchettoPredefinito
{
	nome: one Stringa,
	datePartenza: some Int,
	durate: some Int,
	prezzo: one Prezzo,
	cittaPartenza: some Citta,
	hotel: one Hotel,
	collegamenti: some Collegamento,
	escursioni: set Escursione
}
{
	all i : Int |
		i in durate implies i > 0
	all i : Int |
		i in datePartenza implies i > 0
}

//Destinazione
sig Destinazione
{
	nome: one Citta,
	dataArrivo: one Int,
	dataPartenza: one Int,
	hotel: one Hotel,
	escursioni: set PrenotazioneEscursione
}
{
	dataArrivo > 0
	dataPartenza > 0
	//la data di arrivo deve precedere la data di partenza
	dataArrivo < dataPartenza
}

//Hotel
sig Hotel
{
	nome: one Stringa,
	stelle: one Stelle,
	indirizzo: one Stringa,
	citta: one Citta,
	telefono: one Telefono,
	website: one Stringa,
	mail: one Stringa,
	prezzo: one Prezzo	
}

//Collegamento
sig Collegamento
{
	codice: one Stringa,
	cittaPartenza: one Citta,
	cittaArrivo: one Citta,
	dataPartenza: one Int,
	oraPartenza: one Ora,
	oraArrivo: one Ora,
	prezzo: one Prezzo,	
	tipoCollegamento: one TipoCollegamento
}
{
	dataPartenza > 0
	//la città di partenza non può coincidere con la città di arrivo
	cittaPartenza != cittaArrivo
}

//PrenotazioneEscursione
sig PrenotazioneEscursione
{
	numeroPartecipanti: one Int,
	escursione: one Escursione
}
{
	numeroPartecipanti > 0
}

//Escursione
sig Escursione
{
	nome: one Stringa,
	luogo: one Citta,
	data: one Int,
	ora: one Ora,
	durata: one Ora,
	categoria: one Stringa,
	prezzo: one Prezzo	
}
{
	data > 0
}

/**
 * FATTI
*/

fact rimozioneDuplicati
{
	no disj p, p1 : Persona | p.nome = p1.nome and p.cognome = p1.cognome and p.documentoIdentita = p1.documentoIdentita
	no disj u, u1 : Utente | u.mail = u1.mail
	no disj a, a1 : Amico | a.mail = a1.mail
	no disj d, d1 : Dipendente | d.nomeUtente = d1.nomeUtente
	no disj p, p1 : Pacchetto | p.nome = p1.nome
	no disj p, p1 : PacchettoPredefinito | p.nome = p1.nome
	no disj h, h1 : Hotel | h.indirizzo = h1.indirizzo and h.citta = h1.citta
	no disj c, c1 : Collegamento | c.codice = c1.codice
	no disj e, e1 : Escursione | e.nome = e1.nome
}

fact pacchetto
{
	//la città di partenza del pacchetto deve essere diversa da tutte le destinazioni inserite nel pacchetto
	all p : Pacchetto |
		no d : Destinazione |
			d in p.destinazioni and p.cittaPartenza = d.nome

	//il numero di collegamenti deve essere uguale alla somma delle destinazioni del pacchetto più il viaggio di ritorno
	all p : Pacchetto |
		#p.collegamenti = add[#p.destinazioni, 1]
}

fact pacchettoPredefinito
{
	//un pacchetto predefinto esiste solo se creato / modificato da almeno un dipendente
	all p : PacchettoPredefinito |
		some d, d1 : Dipendente |
			gestiscePacchetto[d, d1, p]

	//ogni hotel deve essere avere un collegamento
	all p : PacchettoPredefinito, h : Hotel |
		some c : Collegamento |
			h in p.hotel implies c in p.collegamenti and c.cittaArrivo = h.citta and c.cittaPartenza in p.cittaPartenza

	all p : PacchettoPredefinito, h : Hotel |
		some c : Collegamento |
			h in p.hotel implies c in p.collegamenti and c.cittaPartenza = h.citta and c.cittaArrivo in p.cittaPartenza

	//i collegamenti possono andare da una citta di partenza all'hotel e viceversa
	all p : PacchettoPredefinito, c : Collegamento |
		c in p.collegamenti and c.cittaPartenza in p.cittaPartenza implies c.cittaArrivo = p.hotel.citta

	all p : PacchettoPredefinito, c : Collegamento |
		c in p.collegamenti and c.cittaPartenza = p.hotel.citta implies c.cittaArrivo in p.cittaPartenza

	//le citta' di partenza del pacchetto non possono combaciare con la citta' dell'hotel
	all p : PacchettoPredefinito |
		no h : Hotel |
			h in p.hotel implies h.citta in p.cittaPartenza

	//la data del viaggio di ritorno deve essere pari alla somma di una data di partenza e di una durata
	all p : PacchettoPredefinito |
		some c : Collegamento |
			c in p.collegamenti and c.cittaArrivo in p.cittaPartenza implies 
				some i, i1 : Int |
					i in p.datePartenza and i1 in p.durate and c.dataPartenza = add[i, i1]

	//la localita' dell'escursione deve essere uguale alla citta' dell'hotel
	all p : PacchettoPredefinito, e : Escursione |
		e in p.escursioni implies e.luogo = p.hotel.citta

	//le escursioni devono restare entro l'intervallo delle possibili durate del viaggio
	all p : PacchettoPredefinito, e : Escursione |
		e in p.escursioni implies some i, i1 : Int |
			i in p.datePartenza and i1 in p.durate and e.data >= i and e.data <= add[i, i1]
}

fact pacchettoPredefinitoUtente
{
	//un pacchetto predefinito utente deve avere le stesse destinazioni di un pacchetto predefinito
	all p : PacchettoPredefinitoUtente |
		one u, u1 : Utente, p1 : PacchettoPredefinito |
			salvaPredefinito[u, u1, p, p1]
}

fact pacchettoPersonalizzato
{
	/*
	 * un pacchetto personalizzato non può esistere se non viene creato da un utente
	 * un pacchetto personalizzato può appartenere ad un solo utente
	*/
	all p : PacchettoPersonalizzato |
		one u, u1 : Utente |
			creaPacchettoPersonalizzato[u, u1, p]
}

fact pacchettoAcquistato
{
	/*
	 * un pacchetto acquistato non può esistere se non viene acquisto da un utente
	 * un pacchetto acquistato può appartenere ad un solo utente
	*/
	all p : PacchettoAcquistato |
		one u : Utente |
			p in u.pacchettiAcquistati
}

fact pacchettoCondiviso
{
	/* 
	 * un pacchetto condiviso non può esistere se non viene creato da un utente
	 * un pacchetto condiviso può appartenere ad un solo utente
	 * un pacchetto condiviso deve essere creato a partire da un pacchetto personalizzato o predefinito
	*/
	all p : PacchettoCondiviso |
		one u, u1 : Utente, p1 : Pacchetto |
			(p1 in u.pacchettiPersonalizzati or p1 in u.pacchettiPredefiniti) and condividePacchetto[u, u1, p, p1]

	all p : PacchettoCondiviso |
		one u : Utente |
			p in u.pacchettiCondivisi

	//se un pacchetto è condiviso con un amico allora quell'amico deve avere in condivisione quel pacchetto
	all p : PacchettoCondiviso, a : Amico |
		a in p.condivisoCon and p in a.pacchettiCondivisi
}

fact destinazione
{
	//una destinazione può appartenere ad un solo pacchetto
	all d : Destinazione |
		one p : Pacchetto |
			d in p.destinazioni

	//la data di partenza da una destinazione deve coincidere con la data di arrivo alla destinazione successiva
	all p : Pacchetto, d : Destinazione |
		d in p.destinazioni and not isUltimaDestinazione[d] implies one d1 : Destinazione |
			d.dataPartenza = d1.dataArrivo

	//più destinazioni non possono avere le stesse date
	all p : Pacchetto, d : Destinazione |
		no d1 : Destinazione |
			d != d1 and d in p.destinazioni and d1 in p.destinazioni and d.dataArrivo = d1.dataArrivo and d.dataPartenza = d1.dataPartenza

	//le destinazioni non possono avere date sovrapposte
	all p : Pacchetto, d : Destinazione |
		no d1 : Destinazione |
			d in p.destinazioni and d1 in p.destinazioni and 
				(d1.dataArrivo > d.dataArrivo and d1.dataArrivo < d.dataPartenza) or (d1.dataPartenza > d.dataArrivo and d1.dataPartenza < d.dataPartenza)
}

fact hotel
{
	//un hotel esiste solo se creato da almeno un dipendente
	all h : Hotel |
		some d, d1 : Dipendente |
			gestisceHotel[d, d1, h]

	//la città dell'hotel deve coincidere con con la città della destinazione
	all d : Destinazione, h : Hotel |
		h in d.hotel implies h.citta = d.nome
}

fact prenotazioneEscursione
{
	//una PrenotazioneEscursione non esiste se non legata ad una destinazione
	all p : PrenotazioneEscursione |
		one d : Destinazione |
			p in d.escursioni

	//diverse prenotazioni non possono fare riferimento alla stessa escursione
	all d : Destinazione, p : PrenotazioneEscursione |
		no p1 : PrenotazioneEscursione |
			p != p1 and p in d.escursioni and p1 in d.escursioni and p.escursione = p1.escursione

	//il numero di partecipanti ad una escursione deve essere minore del numero partecipanti al viaggio
	all p : Pacchetto, d : Destinazione, pe : PrenotazioneEscursione |
		d in p.destinazioni and pe in d.escursioni implies pe.numeroPartecipanti <= p.numeroPartecipanti
}

fact escursione
{
	//una escursione esiste solo se creata da almeno un dipendente
	all e : Escursione |
		some d, d1 : Dipendente |
			gestisceEscursione[d, d1, e]

	//la data dell'escursione deve essere compresa tra la data di arrivo e la data di partenza della destinazione
	all d : Destinazione,  e : Escursione |
		e in d.escursioni.escursione implies (e.data >= d.dataArrivo and e.data <= d.dataPartenza)

	//una escursione deve essere nei dintorni della stessa città di destinazione
	all d : Destinazione, e : Escursione |
		e in d.escursioni.escursione implies d.nome = e.luogo
}

fact collegamento
{
	//un collegamento esiste solo se creato da almeno un dipendente
	all c : Collegamento |
		some d, d1 : Dipendente |
			gestisceCollegamento[d, d1, c]

	//il collegamento di andata deve avere come città di partenza la citta segnata nel pacchetto
	all p : Pacchetto, d : Destinazione |
		d in p.destinazioni and isPrimaDestinazione[d] implies one c : Collegamento |
			c in p.collegamenti and c.dataPartenza = d.dataArrivo and c.cittaPartenza = p.cittaPartenza

	//il collegamento di ritorno deve avere come città di arrivo la città segnata nel pacchetto
	all p : Pacchetto, d : Destinazione |
		d in p.destinazioni and isUltimaDestinazione[d] implies one c : Collegamento |
			c in p.collegamenti and c.dataPartenza = d.dataPartenza and c.cittaArrivo = p.cittaPartenza

	/*
	 * se la data del collegamento coincide con la data di arrivo in una destinazione allora la città di arrivo del
	 * collegamento deve essere uguale alla destinazione
	*/
	all p : Pacchetto, d : Destinazione |
		d in p.destinazioni implies one c : Collegamento |
			c in p.collegamenti and c.dataPartenza = d.dataArrivo and c.cittaArrivo = d.nome

	/*
	 * se la data del collegamento coincide con la data di partenza da una destinazione allora la città di partenza
	 * del collegamento deve essere uguale alla destinazione
	*/
	all p : Pacchetto, d : Destinazione |
		d in p.destinazioni implies one c : Collegamento |
			c in p.collegamenti and c.dataPartenza = d.dataPartenza and c.cittaPartenza = d.nome
}

/**
 * ASSERZIONI
*/

//Pacchetto

assert nonEsistonoPacchettiConLaStessaCittaDiPartenzaDiUnaDestinazione
{
	no p : Pacchetto |
		some d : Destinazione |
			d in p.destinazioni and p.cittaPartenza = d.nome
}

check nonEsistonoPacchettiConLaStessaCittaDiPartenzaDiUnaDestinazione for 4

//Pacchetto Predefinito

assert nonEsistonoPacchettiPredefinitiNonCreatiDaUnDipendente
{
	no p : PacchettoPredefinito |
		all d : Dipendente |
			p not in d.pacchetti
}

check nonEsistonoPacchettiPredefinitiNonCreatiDaUnDipendente

assert esistonoCollegamentiVersoHotel
{
	all p : PacchettoPredefinito, h : Hotel|
		some c : Collegamento |
			c in p.collegamenti and h in p.hotel and c.cittaArrivo = h.citta
}

check esistonoCollegamentiVersoHotel

assert nonEsistonoPacchettiConLaStessaCittaDellHotel
{
	all p : PacchettoPredefinito |
		no h : Hotel |
			h in p.hotel and h.citta in p.cittaPartenza
}

check nonEsistonoPacchettiConLaStessaCittaDellHotel

assert nonEsistonoEscursioniInLocalitaDiverseDallHotel
{
	all p : PacchettoPredefinito |
		no e : Escursione |
			e in p.escursioni and e.luogo != p.hotel.citta
}

check nonEsistonoEscursioniInLocalitaDiverseDallHotel

//Pacchetto Predefinito Utente

assert unPacchettoPredefinitoUtenteDeveAvereLeStesseDestinazioniDiUnPacchettoPredefinito
{
	all p : PacchettoPredefinitoUtente, d : Destinazione |
		d in p.destinazioni implies some p1 : PacchettoPredefinito, h : Hotel |
			h in p1.hotel and d.nome = h.citta
}

check unPacchettoPredefinitoUtenteDeveAvereLeStesseDestinazioniDiUnPacchettoPredefinito

//Pacchetto Personalizzato

assert nonEsistonoPacchettiPersonalizzatiSenzaUtente
{
	no p : PacchettoPersonalizzato |
		all u : Utente |
			p not in u.pacchettiPersonalizzati
}

check nonEsistonoPacchettiPersonalizzatiSenzaUtente

//Pacchetto Condiviso

assert nonEsistonoPacchettiCondivisiSenzaUtente
{
	no p : PacchettoCondiviso |
		all u : Utente |
			p not in u.pacchettiCondivisi
}

check nonEsistonoPacchettiCondivisiSenzaUtente
 
assert relazioneTraAmicoEPacchettoCondiviso
{
	all p : PacchettoCondiviso |
		no a : Amico |
			p in a.pacchettiCondivisi and a not in p.condivisoCon

	all a : Amico |
		no p : PacchettoCondiviso |
			a in p.condivisoCon and p not in a.pacchettiCondivisi
}

check relazioneTraAmicoEPacchettoCondiviso

//Pacchetto Acquistato

assert nonEsistonoPacchettiAcquistatiSenzaUtente
{
	no p : PacchettoAcquistato |
		all u : Utente |
			p not in u.pacchettiAcquistati
}

check nonEsistonoPacchettiAcquistatiSenzaUtente

//Destinazione

assert nonEsistonoPacchettiDiversiConLaMedesimaDestinazione
{
	all d : Destinazione |
		no disj p, p1 : Pacchetto |
			d in p.destinazioni and d in p1.destinazioni
}

check nonEsistonoPacchettiDiversiConLaMedesimaDestinazione

assert dateDestinazioniCollegate
{
	all p : Pacchetto, d : Destinazione |
		d in p.destinazioni and not isUltimaDestinazione[d] implies one d1 : Destinazione |
			d.dataPartenza = d1.dataArrivo
}

check dateDestinazioniCollegate

assert nonEsistonoDestinazioniDelloStessoPacchettoNelleStesseDate
{
	all p : Pacchetto |
		no disj d, d1 : Destinazione |
			d in p.destinazioni and d1 in p.destinazioni and d.dataArrivo = d1.dataArrivo and d.dataPartenza = d1.dataPartenza
}

check nonEsistonoDestinazioniDelloStessoPacchettoNelleStesseDate

assert nonEsistonoDueDestinazioniDelloStessoPacchettoNelloStessoPeriodo
{
	all p : Pacchetto, d: Destinazione |
		no d1 : Destinazione |
			d != d1 and d in p.destinazioni and d1 in p.destinazioni and d1.dataArrivo > d.dataArrivo and d1.dataArrivo < d.dataPartenza

	all p : Pacchetto, d: Destinazione |
		no d1 : Destinazione |
			d != d1 and d in p.destinazioni and d1 in p.destinazioni and d1.dataPartenza > d.dataArrivo and d1.dataPartenza< d.dataPartenza
}

check nonEsistonoDueDestinazioniDelloStessoPacchettoNelloStessoPeriodo

//Hotel

assert nonEsistonoHotelCheNonSianoStatiCreatiDaUnDipendente
{
	no h : Hotel |
		all d : Dipendente |
			h not in d.hotel
}

check nonEsistonoHotelCheNonSianoStatiCreatiDaUnDipendente

assert nonEsistonoHotelInCittaDiversaDallaDestinazione
{
	no h : Hotel |
		some d : Destinazione |
			h in d.hotel and h.citta != d.nome
}

check nonEsistonoHotelInCittaDiversaDallaDestinazione

//PrenotazioneEscursione

assert nonEsistonoPrenotazioniEscursioniNonCollegateAdUnaDestinazione
{
	no p : PrenotazioneEscursione |
		no d : Destinazione |
			p in d.escursioni
}

check nonEsistonoPrenotazioniEscursioniNonCollegateAdUnaDestinazione

assert diversePrenotazioniNonHannoRiferimentoVersoLaStessaEscursione
{
	all d : Destinazione |
		no disj p, p1 : PrenotazioneEscursione |
			p in d.escursioni and p1 in d.escursioni and p.escursione = p1.escursione
}

check diversePrenotazioniNonHannoRiferimentoVersoLaStessaEscursione

//Escursione

assert nonEsistonoEscursioniCheNonSianoStatiCreatiDaUnDipendente
{
	no e : Escursione |
		all d : Dipendente |
			e not in d.escursioni
}

check nonEsistonoEscursioniCheNonSianoStatiCreatiDaUnDipendente

assert nonEsistonoEscursioniFuoriDalPeriodoDiVacanza
{
	no e : Escursione |
		some d : Destinazione |
			e in d.escursioni.escursione and (e.data < d.dataArrivo or e.data > d.dataPartenza)
}

check nonEsistonoEscursioniFuoriDalPeriodoDiVacanza

assert nonEsistonoEscursioniInPostiDiversiDallaLocalitaDiViaggio
{
	no e : Escursione |
		some d : Destinazione |
			e in d.escursioni.escursione and d.nome != e.luogo
}

check nonEsistonoEscursioniInPostiDiversiDallaLocalitaDiViaggio

//Collegamento

assert nonEsistonoCollegamentiCheNonSianoStatiCreatiDaUnDipendente
{
	no c : Collegamento |
		all d : Dipendente |
			c not in d.collegamenti
}

check nonEsistonoCollegamentiCheNonSianoStatiCreatiDaUnDipendente

assert nonEsistonoCollegamentiConDateNonAssociateAdAlmenoUnaDestinazione
{
	all p : Pacchetto, c : Collegamento |
		some d : Destinazione |
			c in p.collegamenti and d in p.destinazioni implies (c.dataPartenza = d.dataArrivo or c.dataPartenza = d.dataPartenza)
}

check  nonEsistonoCollegamentiConDateNonAssociateAdAlmenoUnaDestinazione

assert nonEsistonoCollegamentiDiAndataChePartonoDaUnaCittaDiversaDaQuellaDelPacchetto
{
	all p : Pacchetto |
		some d : Destinazione |
			no c : Collegamento |
				d in p.destinazioni and c in p.collegamenti and isPrimaDestinazione[d] and c.dataPartenza = d.dataArrivo and c.cittaPartenza != p.cittaPartenza
}

check nonEsistonoCollegamentiDiAndataChePartonoDaUnaCittaDiversaDaQuellaDelPacchetto

assert nonEsistonoCollegamentiDiRitornoCheArrivanoInUnaCittaDiversaDaQuellaDelPacchetto
{
	all p : Pacchetto |
		some d : Destinazione |
			no c : Collegamento |
				d in p.destinazioni and c in p.collegamenti and isUltimaDestinazione[d] and c.dataPartenza = d.dataPartenza and c.cittaArrivo != p.cittaPartenza
}

check nonEsistonoCollegamentiDiRitornoCheArrivanoInUnaCittaDiversaDaQuellaDelPacchetto

/**
 *PREDICATI
*/

pred creaPacchettoPersonalizzato [u, u1 : Utente, p : PacchettoPersonalizzato]
{
	p not in u.pacchettiPersonalizzati implies u1.pacchettiPersonalizzati = u.pacchettiPersonalizzati + p
}

pred condividePacchetto [u, u1 : Utente, p : PacchettoCondiviso, p1 : Pacchetto]
{
	p not in u.pacchettiCondivisi implies u1.pacchettiCondivisi = u.pacchettiCondivisi + p and p1.cittaPartenza = p.cittaPartenza

	all c : Collegamento |
		c in p1.collegamenti implies c in p.collegamenti

	all d : Destinazione, h : Hotel |
		some d1 : Destinazione |
			d in p1.destinazioni and h in d.hotel implies d1 in p.destinazioni and h in d1.hotel and d1.dataArrivo = d.dataArrivo 
			and d1.dataPartenza = d.dataPartenza and #d.escursioni = #d1.escursioni

	all d : Destinazione, pe : PrenotazioneEscursione, e : Escursione |
		some d1 : Destinazione, pe1 : PrenotazioneEscursione |
			d in p1.destinazioni and pe in d.escursioni and e in pe.escursione implies d1 in p.destinazioni and pe1 in d1.escursioni and e in pe1.escursione
}

pred salvaPredefinito [u, u1 : Utente, p : PacchettoPredefinitoUtente, p1 : PacchettoPredefinito]
{
	p not in u.pacchettiPredefiniti implies u1.pacchettiPredefiniti = u.pacchettiPredefiniti + p
		and p.cittaPartenza in p1.cittaPartenza

	all d : Destinazione |
		d in p.destinazioni implies d.dataArrivo in p1.datePartenza and one i : Int |
			i in p1.durate and d.dataPartenza = add[d.dataArrivo, i]

	all  h : Hotel |
		some d1 : Destinazione |
			h in p1.hotel implies d1 in p.destinazioni and d1.nome = h.citta and h in d1.hotel

	#p.destinazioni = #p1.hotel

	all e : Escursione |
		e in p1.escursioni implies some d : Destinazione, pe : PrenotazioneEscursione |
			d in p.destinazioni and pe in d.escursioni and e in pe.escursione
}

//Ritorna true se la destinazione selezionata è la prima destinazione del pacchetto
pred isUltimaDestinazione [d : Destinazione]
{
	all p : Pacchetto |
		no d1 : Destinazione |
			d != d1 and d in p.destinazioni and d1 in p.destinazioni and d.dataPartenza < d1.dataPartenza
}

//Ritorna true se la destinazione selezionata è l'ultima destinazione del pacchetto
pred isPrimaDestinazione [d : Destinazione]
{
	all p : Pacchetto |
		no d1 : Destinazione |
			d != d1 and d in p.destinazioni and d1 in p.destinazioni and d.dataArrivo > d1.dataArrivo
}

pred gestiscePacchetto[d, d1 : Dipendente, p : PacchettoPredefinito]
{
	p not in d.pacchetti implies d1.pacchetti = d.pacchetti + p
}

pred gestisceCollegamento [d, d1 : Dipendente, c : Collegamento]
{
	c not in d.collegamenti implies d1.collegamenti = d.collegamenti + c
}

pred gestisceHotel [d, d1 : Dipendente, h : Hotel]
{
	h not in d.hotel implies d1.hotel = d.hotel + h
}

pred gestisceEscursione [d, d1 : Dipendente, e : Escursione]
{
	e not in d.escursioni implies d1.escursioni = d.escursioni + e
}

/*
 * Mostra
*/

pred mostraViaggioConAlmenoDueDestinazioni
{
	#PacchettoPredefinitoUtente = 0
	#PacchettoPredefinito = 0
	#PacchettoCondiviso = 0
	#PacchettoPersonalizzato = 1
	#PacchettoAcquistato = 0
	#PrenotazioneEscursione = 0

	some p : Pacchetto |
		#p.destinazioni > 1
}

run mostraViaggioConAlmenoDueDestinazioni

pred mostraViaggioConAlmenoDueDestinazioniNellaStessaCitta
{
	#PacchettoCondiviso = 0
	#PacchettoPredefinitoUtente = 0
	#PacchettoPersonalizzato = 1
	#PrenotazioneEscursione = 0

	some p : Pacchetto, d, d1 : Destinazione |
		d in p.destinazioni and d1 in p.destinazioni and d != d1 and d.nome = d1.nome
}

run mostraViaggioConAlmenoDueDestinazioniNellaStessaCitta for 4

pred mostraViaggioDoveEsisteAlmenoUnaDestinazioneConEscursioni
{
	#PacchettoPredefinitoUtente = 0
	#PacchettoPredefinito = 0
	#PacchettoCondiviso = 0
	#PacchettoPersonalizzato = 0
	#PacchettoAcquistato = 1

	some d : Destinazione |
		#d.escursioni = 1

	some e : Escursione |
		no d : Destinazione |
			e.data = d.dataArrivo and e.data = d.dataPartenza
}

run mostraViaggioDoveEsisteAlmenoUnaDestinazioneConEscursioni

pred utenteCondividePacchetto
{
	#PacchettoPredefinitoUtente = 0
	#PacchettoPredefinito = 0
	#PacchettoCondiviso = 1
	#PacchettoPersonalizzato = 1
	#PacchettoAcquistato = 0

	some p : PrenotazioneEscursione, e : Escursione |
		no d : Destinazione |
			e in p.escursione and e.data = d.dataArrivo and e.data = d.dataPartenza
}

run utenteCondividePacchetto

pred utenteScegliePacchettoPredefinito
{
	#PacchettoPredefinitoUtente = 1
	#PacchettoPredefinito = 1
	#PacchettoCondiviso = 0
	#PacchettoPersonalizzato = 0
	#PacchettoAcquistato = 0

	some p : PacchettoPredefinito |
		#p.durate > 1 and #p.datePartenza > 1 and #p.cittaPartenza > 1 and #p.escursioni = 1 and #p.collegamenti > 2
}

run utenteScegliePacchettoPredefinito for 3 but 1 Escursione

pred legameProdottiDipendenti
{
	#Pacchetto = 0
	#Hotel > 0
	#Collegamento > 0
	#Escursione > 0
}

run legameProdottiDipendenti

pred mostraPacchettoSemplice
{
	#PacchettoPredefinitoUtente = 0
	#PacchettoPredefinito = 0
	#PacchettoCondiviso = 0
	#PacchettoPersonalizzato = 1
	#PacchettoAcquistato = 0
	#PrenotazioneEscursione = 0
}

run mostraPacchettoSemplice for 3 but 1 Destinazione

pred utenteConPiuPacchetti
{
	#PacchettoPredefinitoUtente = 1
	#PacchettoPersonalizzato = 1
	#PacchettoAcquistato = 1
	#PacchettoCondiviso = 1
}
 
run utenteConPiuPacchetti for 5
