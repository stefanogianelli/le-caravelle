INSERT INTO citta (nome, regione, nazione) VALUES ('Milano', 'Lombardia', 'Italia');
INSERT INTO citta (nome, regione, nazione) VALUES ('Roma', 'Lazio', 'Italia');
INSERT INTO citta (nome, regione, nazione) VALUES ('New York', 'New York', 'USA');
INSERT INTO citta (nome, regione, nazione) VALUES ('Barcellona', 'Barcellona', 'Spagna');
INSERT INTO citta (nome, regione, nazione) VALUES ('L''Avana', 'L''Avana', 'Cuba');
INSERT INTO citta (nome, regione, nazione) VALUES ('Londra', 'Londra', 'Regno Unito');
INSERT INTO citta (nome, regione, nazione) VALUES ('Alberobello', 'Puglia', 'Italia');
INSERT INTO citta (nome, regione, nazione) VALUES ('Rodi', 'Rodi', 'Grecia');
INSERT INTO citta (nome, regione, nazione) VALUES ('Citta del Capo', 'Capo Occidentale', 'Sudafrica');
INSERT INTO citta (nome, regione, nazione) VALUES ('Johannesburg', 'Guateng', 'Sudafrica');
INSERT INTO citta (nome, regione, nazione) VALUES ('Istanbul', 'Marmara', 'Turchia');

INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta) VALUES ('Hotel La Madonnina', 3, 'Via G. Mazzini 10', '+39 02 89096917', 'http://www.hotellamadonninamilano.it/', 'info@hotellamadonninamilano.it', 10.0, 1);
INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta) VALUES ('Hotel Hilton', 5, 'Via Alberto Cadlolo 101', '+39 06 3509 1', 'http://www.romecavalieri.it/', 'info@romecavalieri.it', 50.0, 2);
INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta) VALUES ('Hotel Reina', 3, 'La Rambla 40', '+39 93 317-36-24', 'http://www.hotelreina.es/', 'info@hotelreina.es', 50.0, 4);
INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta) VALUES ('Starhotel Rosa', 4, 'Piazza Fontana 3', '+39 02 88311', 'http://rosagrand.starhotels.com/it/home.aspx', 'rosa.mi@starhotels.it', 50.0, 1);
INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta) VALUES ('Hotel Fenice', 3, 'Corso Buenos Aires 2', '+39 02 29525541', 'http://www.hotelfenice.it/it/', 'info@hotelfenice.it', 50.0, 1);
INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta) VALUES ('Hotel Michelangelo', 4, 'Piazza Luigi di Savoia 6', '+39 0267551', 'http://www.michelangelohotelmilan.com/italiano/', 'michelangelo@milanhotel.it', 50.0, 1);

INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza) VALUES ('AEREO', 'JFK', 'Malpensa', '2013-12-16', '22:20:00', '12:05:00', 50, 1, 3);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza) VALUES ('AEREO', 'JFK', 'Malpensa', '2013-12-16', '16:00:00', '06:30:00', 50, 1, 3);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza) VALUES ('AEREO', 'Linate', 'Fiumicino', '2013-12-23', '06:55:00', '08:10:00', 50, 2, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza) VALUES ('AEREO', 'Fiumicino', 'JFK', '2013-12-29', '11:50:00', '18:05:00', 50, 3, 2);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza) VALUES ('AEREO', 'El Prat', 'Malpensa', '2013-11-24', '20:50:00', '21:40:00', 50, 1, 4);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza) VALUES ('AEREO', 'Malpensa', 'El Prat', '2013-11-22', '14:40:00', '16:15:00', 50, 4, 1);

INSERT INTO utenti (email, password) VALUES ('stefano@gmail.com', '05b9115df05a2a467841772eccc969822d884c9e71841050fe9e0893cce7d11b');
INSERT INTO utenti (email, password) VALUES ('francesca@gmail.com', '5dfd46e27a5e3e8e06fcb92817b0955f7fd28048f5003bfd4e5be8e67bf417db');