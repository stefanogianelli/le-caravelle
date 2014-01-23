INSERT INTO citta (id, nome, regione, nazione, lat, lon, attivo) VALUES (1, 'Milano', 'Lombardia', 'Italia', 45.464161, 9.190336, 1);
INSERT INTO citta (id, nome, regione, nazione, lat, lon, attivo) VALUES (2, 'Roma', 'Lazio', 'Italia', 41.893056, 12.482778, 1);
INSERT INTO citta (id, nome, regione, nazione, lat, lon, attivo) VALUES (3, 'New York', 'New York', 'USA', 40.716667, -74, 1);
INSERT INTO citta (id, nome, regione, nazione, lat, lon, attivo) VALUES (4, 'Barcellona', 'Barcellona', 'Spagna', 41.383333, 2.166667, 1);
INSERT INTO citta (id, nome, regione, nazione, lat, lon, attivo) VALUES (5, 'L''Avana', 'L''Avana', 'Cuba', 23.133333, -82.383333, 1);
INSERT INTO citta (id, nome, regione, nazione, lat, lon, attivo) VALUES (8, 'Rodi', 'Rodi', 'Grecia', 36.4, 28.216667, 1);
INSERT INTO citta (id, nome, regione, nazione, lat, lon, attivo) VALUES (9, 'Citta del Capo', 'Capo Occidentale', 'Sudafrica', -33.9264, 18.4227, 1);
INSERT INTO citta (id, nome, regione, nazione, lat, lon, attivo) VALUES (12, 'Parigi', 'Île-de-France', 'Francia', 48.856667, 2.351944, 1);

INSERT INTO immagini_citta (idCitta, immagine) VALUES (1, 'milano1.JPG');
INSERT INTO immagini_citta (idCitta, immagine) VALUES (1, 'milano2.jpg');
INSERT INTO immagini_citta (idCitta, immagine) VALUES (1, 'milano3.jpg');
INSERT INTO immagini_citta (idCitta, immagine) VALUES (2, 'roma1.jpg');
INSERT INTO immagini_citta (idCitta, immagine) VALUES (2, 'roma2.jpg');
INSERT INTO immagini_citta (idCitta, immagine) VALUES (2, 'roma3.jpg');
INSERT INTO immagini_citta (idCitta, immagine) VALUES (3, 'newYork1.jpg');
INSERT INTO immagini_citta (idCitta, immagine) VALUES (3, 'newYork2.jpg');
INSERT INTO immagini_citta (idCitta, immagine) VALUES (3, 'newYork3.jpg');
INSERT INTO immagini_citta (idCitta, immagine) VALUES (4, 'barcellona1.jpg');
INSERT INTO immagini_citta (idCitta, immagine) VALUES (4, 'barcellona2.jpg');
INSERT INTO immagini_citta (idCitta, immagine) VALUES (4, 'barcellona3.jpg');
INSERT INTO immagini_citta (idCitta, immagine) VALUES (5, 'avana1.jpg');
INSERT INTO immagini_citta (idCitta, immagine) VALUES (5, 'avana2.jpg');
INSERT INTO immagini_citta (idCitta, immagine) VALUES (5, 'avana3.jpg');
INSERT INTO immagini_citta (idCitta, immagine) VALUES (8, 'rodi1.jpg');
INSERT INTO immagini_citta (idCitta, immagine) VALUES (8, 'rodi2.jpg');
INSERT INTO immagini_citta (idCitta, immagine) VALUES (8, 'rodi3.jpg');
INSERT INTO immagini_citta (idCitta, immagine) VALUES (9, 'cittaCapo1.jpg');
INSERT INTO immagini_citta (idCitta, immagine) VALUES (9, 'cittaCapo2.jpg');
INSERT INTO immagini_citta (idCitta, immagine) VALUES (9, 'cittaCapo3.jpg');
INSERT INTO immagini_citta (idCitta, immagine) VALUES (12, 'parigi1.jpg');
INSERT INTO immagini_citta (idCitta, immagine) VALUES (12, 'parigi2.jpg');
INSERT INTO immagini_citta (idCitta, immagine) VALUES (12, 'parigi2.jpg');

INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta, immagine, attivo) VALUES ('Hotel La Madonnina', 3, 'Via G. Mazzini 10', '+39 02 89096917', 'http://www.hotellamadonninamilano.it/', 'info@hotellamadonninamilano.it', 160.0, 1, 'madonnina.jpg', 1);
INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta, immagine, attivo) VALUES ('Starhotel Rosa', 4, 'Piazza Fontana 3', '+39 02 88311', 'http://rosagrand.starhotels.com/it/home.aspx', 'rosa.mi@starhotels.it', 85.0, 1, 'starhotel.jpg', 1);
INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta, immagine, attivo) VALUES ('Hotel Fenice', 3, 'Corso Buenos Aires 2', '+39 02 29525541', 'http://www.hotelfenice.it/it/', 'info@hotelfenice.it', 97.65, 1, 'fenice.jpg', 1);
INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta, immagine, attivo) VALUES ('Hotel Michelangelo', 4, 'Piazza Luigi di Savoia 6', '+39 0267551', 'http://www.michelangelohotelmilan.com/italiano/', 'michelangelo@milanhotel.it', 123.25, 1, 'michelangelo.jpg', 1);

INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta, immagine, attivo) VALUES ('Hotel Hilton', 5, 'Via Alberto Cadlolo 101', '+39 06 3509 1', 'http://www.romecavalieri.it/', 'info@romecavalieri.it', 200.0, 2, 'hiltonRoma.jpg', 1);
INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta, immagine, attivo) VALUES ('Piram Hotel', 4, 'Via Giovanni Amendola 7', '+39 06 48901248', 'http://www.welcomepiramhotel.com/it/', 'info@welcomepiramhotel.com', 79.0, 2, 'welcomePiramHotel.jpg', 1);
INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta, immagine, attivo) VALUES ('Hotel Sonya', 3, 'Via Del Viminale 58', '+39 06 4819911', 'http://www.hotelsonya.it/it/', 'info@hotelsonya.it', 84.0, 2, 'hotelSonya.jpg', 1);

INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta, immagine, attivo) VALUES ('Waldorf Hotel', 4, '301 Park Avenue', '(800) 925-3673', 'http://www.waldorfnewyork.com/', 'reservations@waldorfnewyork.com', 230.0, 3, 'Waldorf.jpg', 1);
INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta, immagine, attivo) VALUES ('Hotel Pennsylvania', 2, '401 7th Avenue', '212 736 5000', 'http://www.hotelpenn.com/', 'reservations@pennhotel.com', 80.0, 3, 'Pennsylvania.jpg', 1);
INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta, immagine, attivo) VALUES ('Pod 39 Hotel', 3, '145 East 39th Street', '212 736 5000', 'http://thepodhotel.roomstobook.net/', 'info@pod39.com', 105.0, 3, 'pod39.jpg', 1);

INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta, immagine, attivo) VALUES ('Hotel Reina', 3, 'La Rambla 40', '+39 93 317-36-24', 'http://www.hotelreina.es/', 'info@hotelreina.es', 78.0, 4, 'hotelReina.jpg', 1);
INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta, immagine, attivo) VALUES ('Hotel Turin', 3, 'Pintor Fortuny 9', '+34 933024812', 'http://www.hotelturin.com/it/', 'reservas@hotelturin.com', 99.75, 4, 'hotelTurin.jpg', 1);
INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta, immagine, attivo) VALUES ('Ohla Hotel', 5, 'Via Laietana 49', '+34 933 415 050', 'http://www.ohlahotel.com/en/', 'info@ohlahotel.com', 231.0, 4, 'ohlaHotel.jpg', 1);

INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta, immagine, attivo) VALUES ('Hotel Los Frailes', 3, ' Calle Teniente Rey No. 8', '+53 9 877 1210', 'http://www.hotellosfrailescuba.com/', 'info@losfrailes.com', 85.0, 5, 'frailes.jpg', 1);
INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta, immagine, attivo) VALUES ('Hotel Saratoga', 5, 'Prado 603, esq. a Dragones', '+53 7 868 1000', 'http://www.hotel-saratoga.com/', 'info@saratoga.co.cu', 255.0, 5, 'saratoga.jpg', 1);
INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta, immagine, attivo) VALUES ('Iberostar Hotel', 5, 'Neptuno Prado y Zulueta', '+53 7 860 6627', 'http://www.iberostar.com/en/hotels/la-habana/iberostar-parque-central', 'reservations3@hotelparquecentral.cu', 200.0, 5, 'Iberostar.jpg', 1);

INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta, immagine, attivo) VALUES ('Plaza Hotel', 4, 'Ierou Lochou Str.', '+30 22410-2250', 'http://www.rhodesplazahotel.com/', 'info@rhodesplazahotel.com', 80, 8, 'plaza.jpg', 1);
INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta, immagine, attivo) VALUES ('Parthenon Hotel', 2, '4 Anthoulas Zerbou Str.', '+30 22410-22351', 'http://www.parthenon-hotel.eu/home/en', ' kaludistas@yahoo.com', 45, 8, 'parthenon.jpg', 1);
INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta, immagine, attivo) VALUES ('City Center Hotel', 3, ' Iroon Polytechniou str.,', '+30 22410 36612', 'http://www.citycenterhotel.eu/', 'info@citycenterhotel.eu', 55, 8, 'cityCenter.jpg', 1);

INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta, immagine, attivo) VALUES ('The Table Bay Hotel', 5, 'Quay 6', '+27 21 406 5000', 'http://www.hoteltablebay.co.za/', 'info@tablebay.com', 350, 9, 'tableBay.jpg', 1);
INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta, immagine, attivo) VALUES ('Holiday Inn Hotel', 3, '101 St George Mall', '+27 21 480 8300', 'http://www.ihg.com/holidayinnexpress', 'info@holidayinn.com', 90, 9, 'holidayInn.jpg', 1);
INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta, immagine, attivo) VALUES ('Protea Hotel', 4, 'Cnr Somerset & Ebenezer Roads', '+27 21 418 1234', 'http://www.proteahotels.com/protea-hotel', 'info@proteahotel.com', 170, 9, 'protea.jpg', 1);

INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta, immagine, attivo) VALUES ('Hotel Abbatial', 3, '46 Bld St Germain', '+33 (0)1 46 34 02 1', 'http://www.hotelabbatial.com/', 'contact@hotelabbatial.com', 160.0, 12, 'abbatial.jpg', 1);
INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta, immagine, attivo) VALUES ('Hotel Alhambra', 2, '13 Rue De Malte', '+33 01 47 00 35 52', 'http://www.hotelalhambra.fr/', 'info@hotelalhambra.fr', 183.0, 12, 'alhambra.jpg', 1);
INSERT INTO hotel (nome, stelle, indirizzo, telefono, website, email, prezzo, citta, immagine, attivo) VALUES ('Novotel Tour Eiffel', 4, '61 Quai De Grenelle', '+33 1 40582000', 'http://www.novotel.com/it/hotel-3546-novotel-paris-tour-eiffel/index.shtml', 'H3546-RE4@accor.com', 215.0, 12, 'novotel.jpg', 1);

-- Partenze il 01/03/2014
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'JFK', 'Malpensa', '2014-03-01', '22:20:00', '12:05:00', 550, 1, 3, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'JFK', 'Malpensa', '2014-03-01', '16:00:00', '02:45:00', 480, 1, 3, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'JFK', 'Fiumicino', '2014-03-01', '21:20:00', '11:05:00', 550, 2, 3, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'JFK', 'Fiumicino', '2014-03-01', '17:00:00', '03:45:00', 480, 2, 3, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Malpensa', 'El Prat', '2014-03-01', '14:40:00', '16:15:00', 50, 4, 1, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Malpensa', 'El Prat', '2014-03-01', '09:35:00', '11:05:00', 35, 4, 1, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Malpensa', 'El Prat', '2014-03-01', '11:40:00', '13:15:00',44 , 4, 1, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Ciampino', 'El Prat', '2014-03-01', '08:15:00', '10:00:00', 29, 4, 2, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Fiumicino', 'El Prat', '2014-03-01', '07:15:00', '09:00:00', 45, 4, 2, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Ciampino', 'El Prat', '2014-03-01', '08:15:00', '10:00:00', 41, 4, 2, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Malpensa', 'Orly', '2014-03-01', '07:00:00', '08:20:00', 45, 12, 1, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Linate', 'Orly', '2014-03-01', '08:00:00', '09:20:00', 50, 12, 1, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Malpensa', 'Beauvais', '2014-03-01', '10:15:00', '11:35:00', 30, 12, 1, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'JFK', 'Jose Marti', '2014-03-01', '08:00:00', '21:15:00', 320, 5, 3, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'JFK', 'Jose Marti', '2014-03-01', '12:00:00', '02:15:00', 380, 5, 3, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Fiumicino', 'Orly', '2014-03-01', '07:00:00', '08:20:00', 45, 12, 2, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Ciampino', 'Orly', '2014-03-01', '08:00:00', '09:20:00', 50, 12, 2, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Fiumicino', 'Beauvais', '2014-03-01', '10:15:00', '11:35:00', 30, 12, 2, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Fiumicino', 'JFK', '2014-03-01', '08:05:00', '18:50:00', 450, 3, 2, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Ciampino', 'JFK', '2014-03-01', '12:50:00', '23:35:00', 500, 3, 2, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Linate', 'JFK', '2014-03-01', '12:50:00', '23:35:00', 500, 3, 1, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Malpensa', 'JFK', '2014-03-01', '08:05:00', '18:50:00', 450, 3, 1, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Malpensa', 'Jose Marti', '2014-03-01', '08:00:00', '21:15:00', 620, 5, 1, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Malpensa', 'Jose Marti', '2014-03-01', '12:00:00', '02:15:00', 780, 5, 1, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Malpensa', 'CPT', '2014-03-01', '15:10:00', '09:25:00', 450, 9, 1, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Malpensa', 'RHO', '2014-03-01', '10:50:00', '17:00:00', 195, 8, 1, 1);


-- Partenze il 04/03/2014
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'El Prat', 'Malpensa', '2014-03-04', '20:50:00', '21:40:00', 50, 1, 4, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'El Prat', 'Orio al Serio', '2014-03-04', '20:00:00', '21:45:00', 23, 1, 4, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'El Prat', 'Malpensa', '2014-03-04', '13:50:00', '15:30:00', 29, 1, 4, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Girona', 'Malpensa', '2014-03-04', '10:50:00', '12:30:00', 37, 1, 4, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'El Prat', 'Ciampino', '2014-03-04', '15:55:00', '17:40:00', 29, 2, 4, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Girona', 'Fiumicino', '2014-03-04', '14:35:00', '16:15:00', 45, 2, 4, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Orly', 'Malpensa', '2014-03-04', '18:25:00', '19:45:00', 70, 1, 12, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Orly', 'Linate', '2014-03-04', '19:30:00', '20:50:00', 90, 1, 12, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Beauvais', 'Orio al Serio', '2014-03-04', '20:00:00', '21:20:00', 34, 1, 12, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Orly', 'Fiumicino', '2014-03-04', '18:25:00', '19:45:00', 70, 2, 12, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Orly', 'Fiumicino', '2014-03-04', '19:30:00', '20:50:00', 90, 2, 12, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Beauvais', 'Ciampino', '2014-03-04', '20:00:00', '21:20:00', 34, 2, 12, 1);

-- Partenze il 08/03/2014
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Linate', 'Fiumicino', '2014-03-08', '06:55:00', '08:10:00', 50, 2, 1, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Linate', 'Ciampino', '2014-03-08', '10:00:00', '12:15:00', 50, 2, 1, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Fiumicino', 'Linate', '2014-03-08', '06:55:00', '08:10:00', 50, 1, 2, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Ciampino', 'Malpensa', '2014-03-08', '10:00:00', '12:15:00', 50, 1, 2, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Girona', 'Malpensa', '2014-03-08', '18:25:00', '20:00:00', 55, 1, 4, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Girona', 'Orio al Serio', '2014-03-08', '20:00:00', '21:45:00', 23, 1, 4, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'El Prat', 'Malpensa', '2014-03-08', '21:55:00', '23:35:00', 29, 1, 4, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'El Prat', 'Ciampino', '2014-03-08', '15:55:00', '17:40:00', 53, 2, 4, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'El Prat', 'Fiumicino', '2014-03-08', '18:15:00', '19:55:00', 65, 2, 4, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Malpensa', 'JFK', '2014-03-08', '08:05:00', '18:50:00', 450, 3, 1, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Linate', 'JFK', '2014-03-08', '12:50:00', '23:35:00', 500, 3, 1, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Fiumicino', 'JFK', '2014-03-08', '08:05:00', '18:50:00', 450, 3, 2, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Ciampino', 'JFK', '2014-03-08', '12:50:00', '23:35:00', 500, 3, 2, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Jose Marti', 'JFK', '2014-03-08', '07:00:00', '20:15:00', 295, 3, 5, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Jose Marti', 'JFK', '2014-03-08', '09:00:00', '22:15:00', 295, 3, 5, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'JFK', 'Malpensa', '2014-03-08', '22:20:00', '12:05:00', 550, 1, 3, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'JFK', 'Fiumicino', '2014-03-08', '21:20:00', '11:05:00', 550, 2, 3, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Jose Marti', 'Malpensa', '2014-03-08', '07:00:00', '20:15:00', 595, 1, 5, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Jose Marti', 'Linate', '2014-03-08', '09:00:00', '22:15:00', 605, 1, 5, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Jose Marti', 'JFK', '2014-03-08', '07:00:00', '20:15:00', 295, 3, 5, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'CPT', 'Malpensa', '2014-03-08', '13:30:00', '09:45:00', 510, 1, 9, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'RHO', 'Malpensa', '2014-03-08', '10:50:00', '17:00:00', 195, 1, 8, 1);

-- Partenze il 15/03/2014
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Fiumicino', 'JFK', '2014-03-15', '11:50:00', '22:35:00', 650, 3, 2, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Girona', 'Malpensa', '2014-03-15', '13:50:00', '15:30:00', 25, 1, 4, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'El Prat', 'Malpensa', '2014-03-15', '13:50:00', '15:30:00', 20, 1, 4, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Jose Marti', 'JFK', '2014-03-15', '07:00:00', '20:15:00', 295, 3, 5, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Jose Marti', 'JFK', '2014-03-15', '09:00:00', '22:15:00', 295, 3, 5, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Jose Marti', 'Malpensa', '2014-03-15', '09:00:00', '22:15:00', 795, 1, 5, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Malpensa', 'JFK', '2014-03-15', '08:05:00', '18:50:00', 450, 3, 1, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Linate', 'JFK', '2014-03-15', '12:50:00', '23:35:00', 500, 3, 1, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Fiumicino', 'JFK', '2014-03-15', '08:05:00', '18:50:00', 450, 3, 2, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'JFK', 'Malpensa', '2014-03-15', '16:00:00', '02:45:00', 480, 1, 3, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'JFK', 'Ciampino', '2014-03-15', '17:00:00', '03:45:00', 480, 2, 3, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'JFK', 'Ciampino', '2014-03-15', '10:10:00', '20:55:00', 480, 2, 3, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Girona', 'Fiumicino', '2014-03-15', '13:50:00', '15:30:00', 30, 2, 4, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'El Prat', 'Fiumicino', '2014-03-15', '13:50:00', '15:30:00', 55, 2, 4, 1);

-- Partenze il 22/03/2014
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'JFK', 'Malpensa', '2014-03-22', '16:00:00', '02:45:00', 415, 1, 3, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'JFK', 'Ciampino', '2014-03-22', '17:00:00', '03:45:00', 480, 2, 3, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'JFK', 'Ciampino', '2014-03-22', '10:10:00', '20:55:00', 480, 2, 3, 1);

-- Vecchi
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Malpensa', 'CPT', '2013-12-01', '15:10:00', '09:25:00', 450, 9, 1, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'CPT', 'Malpensa', '2013-12-15', '13:30:00', '09:45:00', 510, 1, 9, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Malpensa', 'RHO', '2013-08-08', '10:50:00', '17:00:00', 195, 8, 1, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'RHO', 'Malpensa', '2013-08-15', '17:30:00', '10:00:00', 212, 1, 8, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Malpensa', 'Orly', '2013-12-30', '07:00:00', '08:20:00', 45, 12, 1, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Orly', 'Linate', '2014-01-03', '19:30:00', '20:50:00', 90, 1, 12, 1);


INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Linate', 'Ciampino', '2014-03-22', '10:00:00', '12:15:00', 50, 2, 1, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Fiumicino', 'Linate', '2014-03-22', '06:55:00', '08:10:00', 50, 1, 2, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Jose Marti', 'Malpensa', '2014-03-22', '09:00:00', '22:15:00', 795, 1, 5, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Girona', 'Malpensa', '2014-03-22', '13:50:00', '15:30:00', 25, 1, 4, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'JFK', 'Jose Marti', '2014-03-15', '12:00:00', '02:15:00', 380, 5, 3, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'JFK', 'Jose Marti', '2014-03-01', '12:00:00', '02:15:00', 380, 5, 3, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Linate', 'Fiumicino', '2014-03-01', '06:55:00', '08:10:00', 50, 2, 1, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Linate', 'Ciampino', '2014-03-01', '10:00:00', '12:15:00', 50, 2, 1, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Fiumicino', 'Linate', '2014-03-01', '06:55:00', '08:10:00', 50, 1, 2, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Ciampino', 'Malpensa', '2014-03-01', '10:00:00', '12:15:00', 50, 1, 2, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Jose Marti', 'Fiumicino', '2014-03-15', '09:00:00', '22:15:00', 795, 2, 5, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Jose Marti', 'Fiumicino', '2014-03-08', '09:00:00', '22:15:00', 795, 2, 5, 1);
INSERT INTO collegamenti (tipoCollegamento, origine, destinazione, dataPartenza, oraPartenza, oraArrivo, prezzo, cittaArrivo, cittaPartenza, attivo) VALUES ('AEREO', 'Jose Marti', 'Fiumicino', '2014-03-22', '09:00:00', '22:15:00', 795, 2, 5, 1);

-- Escursioni del 02/03
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Castello Sforzesco', '2014-03-02', '10:00:00','3', 'CULTURA', 15, 1, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Navigli', '2014-03-02', '17:00:00','5', 'RELAX', 8, 1, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Colosseo', '2014-03-02', '10:30:00','2', 'CULTURA', 12, 2, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Parque Guell', '2014-03-02', '11:00:00','3', 'CULTURA', 15, 4, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Museo del Louvre', '2014-03-02', '09:30:00','4', 'CULTURA', 0, 12, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Museo Rodin', '2014-03-02', '16:00:00','2', 'CULTURA', 8, 12, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Tour Eiffel', '2014-03-02', '19:00:00','2', 'RELAX', 12, 12, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Auditorium della musica', '2014-03-02', '21:00:00','3', 'RELAX', 30, 2, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Ostia', '2014-03-02', '10:00:00','8', 'MARE', 60, 2, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Il mercato della Boqueria', '2014-03-02', '12:00:00','3', 'RELAX', 0, 4, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('La Barceloneta', '2014-03-02', '15:00:00','3', 'MARE', 0, 4, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Museo Picasso', '2014-03-02', '10:00:00','3', 'CULTURA', 12, 4, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Central Park in bicicletta', '2014-03-02', '15:00:00','3', 'SPORT', 9, 3, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('The Metropolitan Museum of Art', '2014-03-02', '09:00:00','4', 'CULTURA', 25, 3, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Giro città guidato', '2014-03-02', '14:00:00','3', 'RELAX', 50, 3, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Gran teatro de La Havana', '2014-03-02', '14:00:00','3', 'RELAX', 60, 5, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Playas de Este', '2014-03-02', '10:00:00','6', 'MARE', 6, 5, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Castillo De Los Tres Reyes Magos', '2014-03-02', '17:00:00','2', 'CULTURA', 10, 5, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Table Mountain', '2014-03-02', '10:00:00','6', 'SPORT', 0, 9, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Kirstenbosch National Gardens', '2014-03-02', '15:00:00','3', 'RELAX', 6, 9, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Robben Island', '2014-03-02', '09:30:00','5', 'CULTURA', 30, 9, 1);

-- Escursioni del 05/03
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Castello Sforzesco', '2014-03-05', '10:00:00','3', 'CULTURA', 15, 1, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Navigli', '2014-03-05', '17:00:00','5', 'RELAX', 8, 1, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Colosseo', '2014-03-05', '10:30:00','2', 'CULTURA', 12, 2, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Parque Guell', '2014-03-05', '11:00:00','3', 'CULTURA', 15, 4, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Museo del Louvre', '2014-03-05', '09:30:00','4', 'CULTURA', 0, 12, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Museo Rodin', '2014-03-05', '16:00:00','2', 'CULTURA', 8, 12, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Tour Eiffel', '2014-03-05', '19:00:00','2', 'RELAX', 12, 12, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Auditorium della musica', '2014-03-05', '21:00:00','3', 'RELAX', 30, 2, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Ostia', '2014-03-05', '10:00:00','8', 'MARE', 60, 2, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Il mercato della Boqueria', '2014-03-05', '12:00:00','3', 'RELAX', 0, 4, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('La Barceloneta', '2014-03-05', '15:00:00','3', 'MARE', 0, 4, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Museo Picasso', '2014-03-05', '10:00:00','3', 'CULTURA', 12, 4, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Central Park in bicicletta', '2014-03-05', '15:00:00','3', 'SPORT', 9, 3, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('The Metropolitan Museum of Art', '2014-03-05', '09:00:00','4', 'CULTURA', 25, 3, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Giro città guidato', '2014-03-05', '14:00:00','3', 'RELAX', 50, 3, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Gran teatro de La Havana', '2014-03-05', '14:00:00','3', 'RELAX', 60, 5, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Playas de Este', '2014-03-05', '10:00:00','6', 'MARE', 6, 5, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Castillo De Los Tres Reyes Magos', '2014-03-05', '17:00:00','2', 'CULTURA', 10, 5, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Table Mountain', '2014-03-05', '10:00:00','6', 'SPORT', 0, 9, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Kirstenbosch National Gardens', '2014-03-05', '15:00:00','3', 'RELAX', 6, 9, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Robben Island', '2014-03-05', '09:30:00','5', 'CULTURA', 30, 9, 1);

-- Escursioni del 09/03
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Castello Sforzesco', '2014-03-09', '10:00:00','3', 'CULTURA', 15, 1, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Navigli', '2014-03-09', '17:00:00','5', 'RELAX', 8, 1, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Colosseo', '2014-03-09', '10:30:00','2', 'CULTURA', 12, 2, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Parque Guell', '2014-03-09', '11:00:00','3', 'CULTURA', 15, 4, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Museo del Louvre', '2014-03-09', '09:30:00','4', 'CULTURA', 0, 12, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Museo Rodin', '2014-03-09', '16:00:00','2', 'CULTURA', 8, 12, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Tour Eiffel', '2014-03-09', '19:00:00','2', 'RELAX', 12, 12, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Auditorium della musica', '2014-03-09', '21:00:00','3', 'RELAX', 30, 2, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Ostia', '2014-03-09', '10:00:00','8', 'MARE', 60, 2, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Il mercato della Boqueria', '2014-03-09', '12:00:00','3', 'RELAX', 0, 4, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('La Barceloneta', '2014-03-09', '15:00:00','3', 'MARE', 0, 4, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Museo Picasso', '2014-03-09', '10:00:00','3', 'CULTURA', 12, 4, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Central Park in bicicletta', '2014-03-09', '15:00:00','3', 'SPORT', 9, 3, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('The Metropolitan Museum of Art', '2014-03-09', '09:00:00','4', 'CULTURA', 25, 3, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Giro città guidato', '2014-03-09', '10:00:00','3', 'RELAX', 50, 3, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Gran teatro de La Havana', '2014-03-09', '14:00:00','3', 'RELAX', 60, 5, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Playas de Este', '2014-03-09', '10:00:00','6', 'MARE', 6, 5, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Castillo De Los Tres Reyes Magos', '2014-03-09', '17:00:00','2', 'CULTURA', 10, 5, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Table Mountain', '2014-03-09', '10:00:00','6', 'SPORT', 0, 9, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Kirstenbosch National Gardens', '2014-03-09', '15:00:00','3', 'RELAX', 6, 9, 1);
INSERT INTO escursioni (nome, data, ora, durata, categoria, prezzo, idCitta, attivo) VALUES ('Robben Island', '2014-03-09', '09:30:00','5', 'CULTURA', 30, 9, 1);

-- emai: stefano@gmail.com - password: stefano
INSERT INTO utenti (email, password) VALUES ('stefano@gmail.com', '05b9115df05a2a467841772eccc969822d884c9e71841050fe9e0893cce7d11b');
-- emai: francesca@gmail.com - password: francesca
INSERT INTO utenti (email, password) VALUES ('francesca@gmail.com', '5dfd46e27a5e3e8e06fcb92817b0955f7fd28048f5003bfd4e5be8e67bf417db');
-- emai: dipendente@gmail.com - password: dipendente
INSERT INTO utenti (email, password) VALUES ('dipendente@gmail.com', '24d331432e0ea5ffa4664d35d57e993224f3c08aa7034b3468010c63b14e0cdd');

INSERT INTO gruppi VALUES ('stefano@gmail.com' , 'UTENTE');
INSERT INTO gruppi VALUES ('francesca@gmail.com' , 'UTENTE');
INSERT INTO gruppi VALUES ('dipendente@gmail.com' , 'DIPENDENTE');

-- 1 Pacchetto Parigi
INSERT INTO pacchetti_predefiniti (nome, prezzo, idHotel, attivo) VALUES ('From Paris with love', 98, 23, 1);

INSERT INTO citta_origine_pred (idPacchettoPredefinito, idCitta) VALUES (1, 1);
INSERT INTO citta_origine_pred (idPacchettoPredefinito, idCitta) VALUES (1, 2);

INSERT INTO date_partenza (data, idPacchettoPredefinito) VALUES ('2014-03-01', 1);

INSERT INTO durate (durata, idPacchettoPredefinito) VALUES (3, 1);

INSERT INTO mezzi_trasporto_pred (idCollegamento, idPacchettoPredefinito) VALUES (11, 1);
INSERT INTO mezzi_trasporto_pred (idCollegamento, idPacchettoPredefinito) VALUES (12, 1);
INSERT INTO mezzi_trasporto_pred (idCollegamento, idPacchettoPredefinito) VALUES (16, 1);
INSERT INTO mezzi_trasporto_pred (idCollegamento, idPacchettoPredefinito) VALUES (17, 1);
INSERT INTO mezzi_trasporto_pred (idCollegamento, idPacchettoPredefinito) VALUES (18, 1);
INSERT INTO mezzi_trasporto_pred (idCollegamento, idPacchettoPredefinito) VALUES (33, 1);
INSERT INTO mezzi_trasporto_pred (idCollegamento, idPacchettoPredefinito) VALUES (34, 1);
INSERT INTO mezzi_trasporto_pred (idCollegamento, idPacchettoPredefinito) VALUES (35, 1);
INSERT INTO mezzi_trasporto_pred (idCollegamento, idPacchettoPredefinito) VALUES (36, 1);
INSERT INTO mezzi_trasporto_pred (idCollegamento, idPacchettoPredefinito) VALUES (37, 1);
INSERT INTO mezzi_trasporto_pred (idCollegamento, idPacchettoPredefinito) VALUES (38, 1);

-- 2 Pacchetto Parigi
INSERT INTO pacchetti_predefiniti (nome, prezzo, idHotel, attivo) VALUES ('Capodanno', 132, 25, 1);

INSERT INTO citta_origine_pred (idPacchettoPredefinito, idCitta) VALUES (2, 1);

INSERT INTO date_partenza (data, idPacchettoPredefinito) VALUES ('2013-12-30', 2);

INSERT INTO durate (durata, idPacchettoPredefinito) VALUES (4, 2);

INSERT INTO mezzi_trasporto_pred (idCollegamento, idPacchettoPredefinito) VALUES (82, 2);
INSERT INTO mezzi_trasporto_pred (idCollegamento, idPacchettoPredefinito) VALUES (83, 2);

-- 3 Pacchetto Waka waka
INSERT INTO pacchetti VALUES (1,'Waka waka',4,8880,'ACQUISTATO',1,NULL,'francesca@gmail.com');

INSERT INTO `mezzi_trasporto` VALUES (1,78), (1,79);

INSERT INTO `destinazioni` VALUES (1,'2013-12-01','2013-12-15',1,21,9);

-- 4 Pacchetto Rodi

INSERT INTO pacchetti VALUES (2,'Rodi',3,2901,'ACQUISTATO',1,NULL,'francesca@gmail.com');

INSERT INTO `mezzi_trasporto` VALUES (2,80), (2,81);

INSERT INTO `destinazioni` VALUES (2,'2013-08-08','2013-08-15',2,17,8);

-- 5 Pacchetto Barcellona
INSERT INTO pacchetti_predefiniti (nome, prezzo, idHotel, attivo) VALUES ('La Rambla', 65, 11, 1);

INSERT INTO citta_origine_pred (idPacchettoPredefinito, idCitta) VALUES (3, 1), (3, 2);

INSERT INTO date_partenza (data, idPacchettoPredefinito) VALUES ('2014-03-01', 3);

INSERT INTO durate (durata, idPacchettoPredefinito) VALUES (3, 3), (7, 3), (14, 3);

INSERT INTO mezzi_trasporto_pred (idCollegamento, idPacchettoPredefinito) VALUES (5, 3), (6, 3), (7, 3);
INSERT INTO mezzi_trasporto_pred (idCollegamento, idPacchettoPredefinito) VALUES (27, 3), (28, 3), (29, 3), (30, 3), (43, 3), (44, 3), (45, 3), (62, 3), (63, 3);
INSERT INTO mezzi_trasporto_pred (idCollegamento, idPacchettoPredefinito) VALUES (8, 3), (9, 3), (10, 3);
INSERT INTO mezzi_trasporto_pred (idCollegamento, idPacchettoPredefinito) VALUES (31, 3), (32, 3), (46, 3), (47, 3), (73, 3), (74, 3);

-- 6 Pacchetto New York
INSERT INTO pacchetti_predefiniti (nome, prezzo, idHotel, attivo) VALUES ('The big Apple', 153, 10, 1);

INSERT INTO citta_origine_pred (idPacchettoPredefinito, idCitta) VALUES (4, 1), (4, 2);

INSERT INTO date_partenza (data, idPacchettoPredefinito) VALUES ('2014-03-01', 4), ('2014-03-08', 4);

INSERT INTO durate (durata, idPacchettoPredefinito) VALUES (7, 4), (14, 4);

INSERT INTO mezzi_trasporto_pred (idCollegamento, idPacchettoPredefinito) VALUES (21, 4), (22, 4), (48, 4), (49, 4);
INSERT INTO mezzi_trasporto_pred (idCollegamento, idPacchettoPredefinito) VALUES (19, 4), (20, 4), (50, 4), (51, 4);
INSERT INTO mezzi_trasporto_pred (idCollegamento, idPacchettoPredefinito) VALUES (54, 4), (70, 4), (75, 4);
INSERT INTO mezzi_trasporto_pred (idCollegamento, idPacchettoPredefinito) VALUES (55, 4), (71, 4), (72, 4), (76, 4), (77, 4);

-- 7 Pacchetto L'Avana
INSERT INTO pacchetti_predefiniti (nome, prezzo, idHotel, attivo) VALUES ('Cuba', 127, 14, 1);

INSERT INTO citta_origine_pred (idPacchettoPredefinito, idCitta) VALUES (5, 1), (5, 3);

INSERT INTO date_partenza (data, idPacchettoPredefinito) VALUES ('2014-03-01', 5);

INSERT INTO durate (durata, idPacchettoPredefinito) VALUES (7, 5), (14, 5);

INSERT INTO mezzi_trasporto_pred (idCollegamento, idPacchettoPredefinito) VALUES (23, 5), (24, 5);
INSERT INTO mezzi_trasporto_pred (idCollegamento, idPacchettoPredefinito) VALUES (56, 5), (57, 5), (66, 5);
INSERT INTO mezzi_trasporto_pred (idCollegamento, idPacchettoPredefinito) VALUES (14, 5), (15, 5);
INSERT INTO mezzi_trasporto_pred (idCollegamento, idPacchettoPredefinito) VALUES (52, 5), (53, 5), (58, 5), (64, 5), (65, 5);