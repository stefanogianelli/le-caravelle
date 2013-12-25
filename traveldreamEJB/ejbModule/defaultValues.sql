INSERT INTO citta (nome, regione, nazione) VALUES ('Milano', 'Lombardia', 'Italia');
INSERT INTO citta (nome, regione, nazione) VALUES ('Roma', 'Lazio', 'Italia');
INSERT INTO citta (nome, regione, nazione) VALUES ('New York', 'New York', 'USA');

INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta) VALUES ('Hotel La Madonnina', 3, 'Via G. Mazzini 10', '+39 02 89096917', 'http://www.hotellamadonninamilano.it/', 'info@hotellamadonninamilano.it', 10.0, 1);
INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta) VALUES ('Hotel Hilton', 5, 'Via Alberto Cadlolo 101', '+39 06 3509 1', 'http://www.romecavalieri.it/', 'info@romecavalieri.it', 50.0, 2);

INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza) VALUES ('AEREO', 'JFK', 'Malpensa', '2013-12-16', '22:20:00', '12:05:00', 50, 1, 3);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza) VALUES ('AEREO', 'Linate', 'Fiumicino', '2013-12-23', '06:55:00', '08:10:00', 50, 2, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza) VALUES ('AEREO', 'Fiumicino', 'JFK', '2013-12-29', '11:50:00', '18:05:00', 50, 3, 2);