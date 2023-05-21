-- Popunjavanje podataka relacije unajmitelj
INSERT INTO unajmitelj (ime, prezime, oib, email, lozinka, brojTelefona) VALUES ('Rose', 'Roset', '39449920198', 'rroset0@hp.com', '$2a$08$cUu.G35gzr0dL/hWr8nwMuTCwtkNTvuf1mbZwK6i4DW/AxDenLy62', '0922615023'); -- added encoded password
INSERT INTO unajmitelj (ime, prezime, oib, email, lozinka, brojTelefona) VALUES ('Nonie', 'Whisby', '60887600143', 'nwhisby1@howstuffworks.com', 'bx3nn4A6', '0948551484');
INSERT INTO unajmitelj (ime, prezime, oib, email, lozinka, brojTelefona) VALUES ('Nikolaos', 'O''Griffin', '83466288793', 'nogriffin2@ox.ac.uk', 'iBVwn1gvfDX6', '0977424948');
INSERT INTO unajmitelj (ime, prezime, oib, email, lozinka, brojTelefona) VALUES ('Josefa', 'Treppas', '78232476985', 'jtreppas3@tuttocitta.it', 'MN21w4J', '0914090006');
INSERT INTO unajmitelj (ime, prezime, oib, email, lozinka, brojTelefona) VALUES ('Meier', 'Copcutt', '67214083015', 'mcopcutt4@wisc.edu', 'YgEOXKyv', '0907914987');
INSERT INTO unajmitelj (ime, prezime, oib, email, lozinka, brojTelefona) VALUES ('Trumaine', 'Schneidau', '87613903389', 'tschneidau5@webs.com', 'ltSNFSO3I', '0943139756');
INSERT INTO unajmitelj (ime, prezime, oib, email, lozinka, brojTelefona) VALUES ('Derk', 'Sarle', '44666980970', 'dsarle6@rambler.ru', '8F0ItfdJ', '0958993518');
INSERT INTO unajmitelj (ime, prezime, oib, email, lozinka, brojTelefona) VALUES ('Pearce', 'Singyard', '30707875923', 'psingyard7@google.com.hk', 'ZV4K443rzu', '0923854910');
INSERT INTO unajmitelj (ime, prezime, oib, email, lozinka, brojTelefona) VALUES ('Eda', 'Kobiera', '17961114764', 'ekobiera8@1688.com', 'eQgt2SPwya5', '0980362515');
INSERT INTO unajmitelj (ime, prezime, oib, email, lozinka, brojTelefona) VALUES ('Kliment', 'Leathers', '25967999624', 'kleathers9@t-online.de', 'tyHetnuUuVN', '0992951686');

-- Popunjavanje podataka relacije lokacija
INSERT INTO lokacija (adresa, pbr, grad, drzava) VALUES ('Ulica poznatih velikana 16', '10000', 'Zagreb', 'Hrvatska');
INSERT INTO lokacija (adresa, pbr, grad, drzava) VALUES ('Ulica automobilske industrije 38', '10000', 'Zagreb', 'Hrvatska');
INSERT INTO lokacija (adresa, pbr, grad, drzava) VALUES ('Trg poznatih i nepoznatih 22', '31000', 'Osijek', 'Hrvatska');

-- Popunjavanje podataka relacije zaposlenik
INSERT INTO zaposlenik (ime, prezime, oib, idLokacija) VALUES ('Shepperd', 'Ticehurst', '03562536048', 3);
INSERT INTO zaposlenik (ime, prezime, oib, idLokacija) VALUES ('Dora', 'Dorić', '11874406542', 2); -- update to Dora Dorić
INSERT INTO zaposlenik (ime, prezime, oib, idLokacija) VALUES ('Corny', 'Severs', '90690344012', 3);
INSERT INTO zaposlenik (ime, prezime, oib, idLokacija) VALUES ('Royal', 'Shama', '69451823260', 3);
INSERT INTO zaposlenik (ime, prezime, oib, idLokacija) VALUES ('Richard', 'Ashforth', '16415553411', 2);
INSERT INTO zaposlenik (ime, prezime, oib, idLokacija) VALUES ('Bryn', 'Rounsefell', '78204344044', 2);
INSERT INTO zaposlenik (ime, prezime, oib, idLokacija) VALUES ('Doria', 'Stuchberry', '10336802152', 1);
INSERT INTO zaposlenik (ime, prezime, oib, idLokacija) VALUES ('Josiah', 'Leicester', '46652754304', 1);
INSERT INTO zaposlenik (ime, prezime, oib, idLokacija) VALUES ('Vic', 'Discombe', '74165609758', 2);
INSERT INTO zaposlenik (ime, prezime, oib, idLokacija) VALUES ('Jesus', 'Maudlin', '03093351420', 3);
INSERT INTO zaposlenik (ime, prezime, oib, idLokacija) VALUES ('Lilyan', 'Colwell', '57009908198', 2);
INSERT INTO zaposlenik (ime, prezime, oib, idLokacija) VALUES ('Paule', 'Widdison', '81237778891', 2);
INSERT INTO zaposlenik (ime, prezime, oib, idLokacija) VALUES ('Mitzi', 'Lochran', '20915047771', 2);
INSERT INTO zaposlenik (ime, prezime, oib, idLokacija) VALUES ('Marcellus', 'Nicolls', '47983474125', 2);
INSERT INTO zaposlenik (ime, prezime, oib, idLokacija) VALUES ('Renae', 'Langmead', '81897111774', 2);
INSERT INTO zaposlenik (ime, prezime, oib, idLokacija) VALUES ('Trevor', 'Callendar', '08431730515', 3);
INSERT INTO zaposlenik (ime, prezime, oib, idLokacija) VALUES ('Lyndsay', 'de Courcy', '82922435611', 1);
INSERT INTO zaposlenik (ime, prezime, oib, idLokacija) VALUES ('Ly', 'Marlen', '08166655904', 1);
INSERT INTO zaposlenik (ime, prezime, oib, idLokacija) VALUES ('Andria', 'Tollemache', '17660995633', 3);
INSERT INTO zaposlenik (ime, prezime, oib, idLokacija) VALUES ('Gardner', 'Kiossel', '26921835166', 2);

-- Popunjavanje podataka relacije iznajmljivac
INSERT INTO iznajmljivac (idZaposlenik, email, brojTelefona, lozinka) VALUES (1, 'Marja', '09482575647', 'Prin');
INSERT INTO iznajmljivac (idZaposlenik, email, brojTelefona, lozinka) VALUES (2, 'dora@ikasus.com.hr', '09966404008', '$2a$08$bmPgCAXvlltJo9EF3yecoOPilFGSVgCumxJ.XY1kJogmwosmITE3.'); -- added encoded password
INSERT INTO iznajmljivac (idZaposlenik, email, brojTelefona, lozinka) VALUES (3, 'Reilly', '09483665810', 'Rigeby');
INSERT INTO iznajmljivac (idZaposlenik, email, brojTelefona, lozinka) VALUES (4, 'Nick', '09743072606', 'Whatsize');
INSERT INTO iznajmljivac (idZaposlenik, email, brojTelefona, lozinka) VALUES (5, 'Hermia', '09605913206', 'Woodhall');
INSERT INTO iznajmljivac (idZaposlenik, email, brojTelefona, lozinka) VALUES (6, 'Hertha', '09924275226', 'Tarply');
INSERT INTO iznajmljivac (idZaposlenik, email, brojTelefona, lozinka) VALUES (7, 'Eberto', '09701185937', 'Attyeo');
INSERT INTO iznajmljivac (idZaposlenik, email, brojTelefona, lozinka) VALUES (8, 'Wyatt', '09152305272', 'Bazeley');
INSERT INTO iznajmljivac (idZaposlenik, email, brojTelefona, lozinka) VALUES (9, 'Ana', '09390752950', 'Russo');
INSERT INTO iznajmljivac (idZaposlenik, email, brojTelefona, lozinka) VALUES (10, 'Perri', '09362621362', 'Vergo');
INSERT INTO iznajmljivac (idZaposlenik, email, brojTelefona, lozinka) VALUES (11, 'Shellie', '09669018199', 'McCaffery');
INSERT INTO iznajmljivac (idZaposlenik, email, brojTelefona, lozinka) VALUES (12, 'Jeremiah', '09657614932', 'Mowson');
INSERT INTO iznajmljivac (idZaposlenik, email, brojTelefona, lozinka) VALUES (13, 'Eula', '09051302521', 'Stockle');
INSERT INTO iznajmljivac (idZaposlenik, email, brojTelefona, lozinka) VALUES (14, 'Rolland', '09217844284', 'Morman');
INSERT INTO iznajmljivac (idZaposlenik, email, brojTelefona, lozinka) VALUES (15, 'Malorie', '09747117673', 'Godfray');
INSERT INTO iznajmljivac (idZaposlenik, email, brojTelefona, lozinka) VALUES (16, 'Joell', '09206573886', 'Leate');
INSERT INTO iznajmljivac (idZaposlenik, email, brojTelefona, lozinka) VALUES (17, 'Dallis', '09448397720', 'Foord');
INSERT INTO iznajmljivac (idZaposlenik, email, brojTelefona, lozinka) VALUES (18, 'Hunt', '09848631893', 'Abbati');
INSERT INTO iznajmljivac (idZaposlenik, email, brojTelefona, lozinka) VALUES (19, 'Licha', '09415850751', 'Torresi');
INSERT INTO iznajmljivac (idZaposlenik, email, brojTelefona, lozinka) VALUES (20, 'Sabine', '09938782230', 'Moules');

-- Popunjavanje podataka relacije zahtjev_za_najmom
INSERT INTO zahtjev_za_najmom (status, vrijemeZahtjeva, obraden, vrijemeObrada, idIznajmljivac, idUnajmitelj) VALUES ('ODBIJEN', '2022-10-14T13:23:49Z', true, '2022-10-14T15:28:49Z', 17, 9);
INSERT INTO zahtjev_za_najmom (status, vrijemeZahtjeva, obraden, vrijemeObrada, idIznajmljivac, idUnajmitelj) VALUES ('ODBIJEN', '2022-06-17T18:58:51Z', true, '2022-06-18T08:58:51Z', 20, 2);
INSERT INTO zahtjev_za_najmom (status, vrijemeZahtjeva, obraden, vrijemeObrada, idIznajmljivac, idUnajmitelj) VALUES ('ODBIJEN', '2022-05-24T08:33:27Z', true, '2022-05-24T08:33:27Z', 11, 3);
INSERT INTO zahtjev_za_najmom (status, vrijemeZahtjeva, obraden, vrijemeObrada, idIznajmljivac, idUnajmitelj) VALUES ('NA CEKANJU', '2022-04-19T02:32:00Z', false, null, null, 6);
INSERT INTO zahtjev_za_najmom (status, vrijemeZahtjeva, obraden, vrijemeObrada, idIznajmljivac, idUnajmitelj) VALUES ('ODBIJEN', '2022-12-14T18:20:14Z', true, '2022-07-26T04:15:40Z', 14, 6);
INSERT INTO zahtjev_za_najmom (status, vrijemeZahtjeva, obraden, vrijemeObrada, idIznajmljivac, idUnajmitelj) VALUES ('ODBIJEN', '2022-08-02T21:50:02Z', true, '2023-03-09T22:42:44Z', 5, 9);
INSERT INTO zahtjev_za_najmom (status, vrijemeZahtjeva, obraden, vrijemeObrada, idIznajmljivac, idUnajmitelj) VALUES ('NA CEKANJU', '2022-11-08T18:35:17Z', false, null, null, 7);
INSERT INTO zahtjev_za_najmom (status, vrijemeZahtjeva, obraden, vrijemeObrada, idIznajmljivac, idUnajmitelj) VALUES ('POTVRDEN', '2023-02-20T07:56:09Z', true, '2022-08-06T21:13:11Z', 5, 5);
INSERT INTO zahtjev_za_najmom (status, vrijemeZahtjeva, obraden, vrijemeObrada, idIznajmljivac, idUnajmitelj) VALUES ('POTVRDEN', '2023-01-14T05:53:50Z', true, '2022-11-22T06:16:05Z', 7, 3);
INSERT INTO zahtjev_za_najmom (status, vrijemeZahtjeva, obraden, vrijemeObrada, idIznajmljivac, idUnajmitelj) VALUES ('NA CEKANJU', '2022-09-11T05:38:35Z', false, null, null, 9);
INSERT INTO zahtjev_za_najmom (status, vrijemeZahtjeva, obraden, vrijemeObrada, idIznajmljivac, idUnajmitelj) VALUES ('ODBIJEN', '2023-01-05T15:34:30Z', true, '2022-06-19T07:07:42Z', 11, 1);
INSERT INTO zahtjev_za_najmom (status, vrijemeZahtjeva, obraden, vrijemeObrada, idIznajmljivac, idUnajmitelj) VALUES ('POTVRDEN', '2022-07-23T04:40:59Z', true, '2022-12-19T09:19:23Z', 11, 3);
INSERT INTO zahtjev_za_najmom (status, vrijemeZahtjeva, obraden, vrijemeObrada, idIznajmljivac, idUnajmitelj) VALUES ('ODBIJEN', '2022-06-08T01:33:20Z', true, '2022-12-27T22:09:16Z', 18, 4);
INSERT INTO zahtjev_za_najmom (status, vrijemeZahtjeva, obraden, vrijemeObrada, idIznajmljivac, idUnajmitelj) VALUES ('ODBIJEN', '2022-09-09T08:22:01Z', true, '2022-10-13T01:49:13Z', 13, 8);
INSERT INTO zahtjev_za_najmom (status, vrijemeZahtjeva, obraden, vrijemeObrada, idIznajmljivac, idUnajmitelj) VALUES ('ODBIJEN', '2023-03-26T18:47:53Z', true, '2022-04-14T06:54:03Z', 11, 5);
INSERT INTO zahtjev_za_najmom (status, vrijemeZahtjeva, obraden, vrijemeObrada, idIznajmljivac, idUnajmitelj) VALUES ('POTVRDEN', '2023-02-19T03:31:11Z', true, '2023-02-01T14:28:37Z', 7, 3);
INSERT INTO zahtjev_za_najmom (status, vrijemeZahtjeva, obraden, vrijemeObrada, idIznajmljivac, idUnajmitelj) VALUES ('POTVRDEN', '2022-12-25T20:44:15Z', true, '2022-12-22T09:16:30Z', 2, 8);
INSERT INTO zahtjev_za_najmom (status, vrijemeZahtjeva, obraden, vrijemeObrada, idIznajmljivac, idUnajmitelj) VALUES ('POTVRDEN', '2023-01-21T12:12:22Z', true, '2022-06-01T11:42:50Z', 16, 10);
INSERT INTO zahtjev_za_najmom (status, vrijemeZahtjeva, obraden, vrijemeObrada, idIznajmljivac, idUnajmitelj) VALUES ('ODBIJEN', '2022-11-08T20:35:58Z', true, '2022-11-07T14:50:06Z', 16, 1);
INSERT INTO zahtjev_za_najmom (status, vrijemeZahtjeva, obraden, vrijemeObrada, idIznajmljivac, idUnajmitelj) VALUES ('NA CEKANJU', '2022-05-04T06:07:15Z', false, null, null, 7);
INSERT INTO zahtjev_za_najmom (status, vrijemeZahtjeva, obraden, vrijemeObrada, idIznajmljivac, idUnajmitelj) VALUES ('ODBIJEN', '2023-01-30T04:28:59Z', true, '2022-10-18T01:32:32Z', 16, 8);
INSERT INTO zahtjev_za_najmom (status, vrijemeZahtjeva, obraden, vrijemeObrada, idIznajmljivac, idUnajmitelj) VALUES ('ODBIJEN', '2023-03-30T11:12:13Z', true, '2022-12-04T02:52:21Z', 4, 10);
INSERT INTO zahtjev_za_najmom (status, vrijemeZahtjeva, obraden, vrijemeObrada, idIznajmljivac, idUnajmitelj) VALUES ('ODBIJEN', '2022-04-26T00:52:45Z', true, '2022-10-21T06:23:35Z', 4, 5);
INSERT INTO zahtjev_za_najmom (status, vrijemeZahtjeva, obraden, vrijemeObrada, idIznajmljivac, idUnajmitelj) VALUES ('ODBIJEN', '2022-12-15T01:24:50Z', true, '2022-11-01T03:03:48Z', 13, 1);
INSERT INTO zahtjev_za_najmom (status, vrijemeZahtjeva, obraden, vrijemeObrada, idIznajmljivac, idUnajmitelj) VALUES ('ODBIJEN', '2023-01-11T14:57:05Z', true, '2023-01-12T10:48:46Z', 8, 5);
INSERT INTO zahtjev_za_najmom (status, vrijemeZahtjeva, obraden, vrijemeObrada, idIznajmljivac, idUnajmitelj) VALUES ('ODBIJEN', '2022-09-14T22:58:54Z', true, '2022-07-18T19:38:00Z', 11, 5);
INSERT INTO zahtjev_za_najmom (status, vrijemeZahtjeva, obraden, vrijemeObrada, idIznajmljivac, idUnajmitelj) VALUES ('ODBIJEN', '2022-11-21T06:58:38Z', true, '2022-09-13T04:53:43Z', 9, 7);
INSERT INTO zahtjev_za_najmom (status, vrijemeZahtjeva, obraden, vrijemeObrada, idIznajmljivac, idUnajmitelj) VALUES ('ODBIJEN', '2022-09-28T22:38:07Z', true, '2022-10-29T09:50:52Z', 13, 9);
INSERT INTO zahtjev_za_najmom (status, vrijemeZahtjeva, obraden, vrijemeObrada, idIznajmljivac, idUnajmitelj) VALUES ('POTVRDEN', '2022-12-06T04:48:07Z', true, '2023-02-07T20:32:42Z', 13, 9);
INSERT INTO zahtjev_za_najmom (status, vrijemeZahtjeva, obraden, vrijemeObrada, idIznajmljivac, idUnajmitelj) VALUES ('NA CEKANJU', '2022-05-28T04:34:57Z', false, null, null, 1);

-- Popunjavanje podataka relacije upravitelj_poslovnice
INSERT INTO upravitelj_poslovnice (idZaposlenik, korisnickoIme, lozinka, upraviteljOd, upraviteljDo) VALUES (1, 'lsallis0', '36xL5V3I', '2022-08-10', null);
INSERT INTO upravitelj_poslovnice (idZaposlenik, korisnickoIme, lozinka, upraviteljOd, upraviteljDo) VALUES (2, 'bjessep1', '$2a$08$eAHx8oxKWY7sIVCxnXOj8eoOLOtM3PixxtFAfjlbavkz5GHYcljn2', '2022-07-15', null); -- added encoded password
INSERT INTO upravitelj_poslovnice (idZaposlenik, korisnickoIme, lozinka, upraviteljOd, upraviteljDo) VALUES (3, 'vingliss2', 'JaJk4H9Ws7', '2022-06-15', null);
INSERT INTO upravitelj_poslovnice (idZaposlenik, korisnickoIme, lozinka, upraviteljOd, upraviteljDo) VALUES (5, 'hkelwaybamber3', 'Yyho3L3', '2022-09-16', null);
INSERT INTO upravitelj_poslovnice (idZaposlenik, korisnickoIme, lozinka, upraviteljOd, upraviteljDo) VALUES (6, 'jkenway4', 'X33Pgkul0y', '2022-08-25', null);
INSERT INTO upravitelj_poslovnice (idZaposlenik, korisnickoIme, lozinka, upraviteljOd, upraviteljDo) VALUES (7, 'gkerrich5', 'e2YIkW', '2022-07-01', null);

-- Popunjavanje podataka relacije tip_vozila
INSERT INTO tip_vozila (idTip, nazivTip, kategorija) VALUES ('SUV-B-1', 'SUV', 'B');
INSERT INTO tip_vozila (idTip, nazivTip, kategorija) VALUES ('KAM-C-1', 'Kamion', 'C');
INSERT INTO tip_vozila (idTip, nazivTip, kategorija) VALUES ('KAM-CE-1', 'Kamion (prikolica)', 'CE');
INSERT INTO tip_vozila (idTip, nazivTip, kategorija) VALUES ('KOMB-B-2', 'Kombi', 'B');
INSERT INTO tip_vozila (idTip, nazivTip, kategorija) VALUES ('MAUT-B-3', 'Mali automobil', 'B');
INSERT INTO tip_vozila (idTip, nazivTip, kategorija) VALUES ('AUT-B-4', 'Automobil', 'B');
INSERT INTO tip_vozila (idTip, nazivTip, kategorija) VALUES ('MOP-AM-1', 'Moped', 'AM');

-- Popunjavanje podataka relacije vozilo
INSERT INTO vozilo (registracija, naziv, proizvodjac, kilometraza, dnevnaCijena, putDoSlike, idTip, idLokacija) VALUES ('ZG-0022-KM', 'Mercedes Atego II', 'Mercedes-Benz', 108524, 1715, null, 'KAM-C-1', 1);
INSERT INTO vozilo (registracija, naziv, proizvodjac, kilometraza, dnevnaCijena, putDoSlike, idTip, idLokacija) VALUES ('ZG-5824-VL', 'Ford Focus', 'Ford', 60733, 2180, null, 'MAUT-B-3', 1);
INSERT INTO vozilo (registracija, naziv, proizvodjac, kilometraza, dnevnaCijena, putDoSlike, idTip, idLokacija) VALUES ('OS-6117-MF', 'Citroën C-Elysee', 'Citroën', 125365, 1122, null, 'AUT-B-4', 3);
INSERT INTO vozilo (registracija, naziv, proizvodjac, kilometraza, dnevnaCijena, putDoSlike, idTip, idLokacija) VALUES ('OS-7454-KP', 'Peugeot 307', 'Peugeot', 104752, 2031, null, 'AUT-B-4', 3);
INSERT INTO vozilo (registracija, naziv, proizvodjac, kilometraza, dnevnaCijena, putDoSlike, idTip, idLokacija) VALUES ('ZG-4788-ZN', 'Volkswagen Caddy', 'Volkswagen', 95118, 602, null, 'KOMB-B-2', null);
INSERT INTO vozilo (registracija, naziv, proizvodjac, kilometraza, dnevnaCijena, putDoSlike, idTip, idLokacija) VALUES ('ZG-6158-FR', 'Volkswagen Golf MK.VII', 'Volkswagen', 125225, 1774, null, 'MAUT-B-3', null);
INSERT INTO vozilo (registracija, naziv, proizvodjac, kilometraza, dnevnaCijena, putDoSlike, idTip, idLokacija) VALUES ('ZG-1102-AX', 'Mercedes C', 'Mercedes-Benz', 69854, 2347, null, 'MAUT-B-3', 2);
INSERT INTO vozilo (registracija, naziv, proizvodjac, kilometraza, dnevnaCijena, putDoSlike, idTip, idLokacija) VALUES ('ZG-2099-FE', 'Volvo FM7', 'Volvo', 133916, 1230, null, 'KAM-CE-1', 2);

-- Popunjavanje podataka relacije potrazuje
INSERT INTO potrazuje (idZahtjev, idVozilo, potraznjaOd, potraznjaDo) VALUES (21, 3, '2022-09-24', '2022-10-24');
INSERT INTO potrazuje (idZahtjev, idVozilo, potraznjaOd, potraznjaDo) VALUES (24, 5, '2022-08-21', '2022-09-01');
INSERT INTO potrazuje (idZahtjev, idVozilo, potraznjaOd, potraznjaDo) VALUES (29, 3, '2022-08-26', '2022-09-15');
INSERT INTO potrazuje (idZahtjev, idVozilo, potraznjaOd, potraznjaDo) VALUES (8, 1, '2022-12-26', '2023-01-09');
INSERT INTO potrazuje (idZahtjev, idVozilo, potraznjaOd, potraznjaDo) VALUES (15, 8, '2022-11-18', '2022-12-22');
INSERT INTO potrazuje (idZahtjev, idVozilo, potraznjaOd, potraznjaDo) VALUES (1, 2, '2022-09-10', '2022-09-15');
INSERT INTO potrazuje (idZahtjev, idVozilo, potraznjaOd, potraznjaDo) VALUES (26, 2, '2022-09-10', '2022-09-25');
INSERT INTO potrazuje (idZahtjev, idVozilo, potraznjaOd, potraznjaDo) VALUES (29, 8, '2022-09-20', '2022-10-18');
INSERT INTO potrazuje (idZahtjev, idVozilo, potraznjaOd, potraznjaDo) VALUES (11, 5, '2022-11-27', '2023-01-08');
INSERT INTO potrazuje (idZahtjev, idVozilo, potraznjaOd, potraznjaDo) VALUES (18, 3, '2022-06-06', '2022-9-15');
INSERT INTO potrazuje (idZahtjev, idVozilo, potraznjaOd, potraznjaDo) VALUES (20, 3, '2023-03-14', '2022-06-05');
INSERT INTO potrazuje (idZahtjev, idVozilo, potraznjaOd, potraznjaDo) VALUES (2, 5, '2023-01-11', '2023-02-20');
INSERT INTO potrazuje (idZahtjev, idVozilo, potraznjaOd, potraznjaDo) VALUES (13, 6, '2022-11-27', '2022-12-13');
INSERT INTO potrazuje (idZahtjev, idVozilo, potraznjaOd, potraznjaDo) VALUES (11, 7, '2022-10-25', '2022-11-28');
INSERT INTO potrazuje (idZahtjev, idVozilo, potraznjaOd, potraznjaDo) VALUES (12, 2, '2022-11-11', '2023-02-21');
INSERT INTO potrazuje (idZahtjev, idVozilo, potraznjaOd, potraznjaDo) VALUES (6, 3, '2022-09-13', '2022-12-24');
INSERT INTO potrazuje (idZahtjev, idVozilo, potraznjaOd, potraznjaDo) VALUES (3, 7, '2022-10-16', '2022-11-06');
INSERT INTO potrazuje (idZahtjev, idVozilo, potraznjaOd, potraznjaDo) VALUES (1, 5, '2023-02-03', '2023-03-23');
INSERT INTO potrazuje (idZahtjev, idVozilo, potraznjaOd, potraznjaDo) VALUES (27, 1, '2022-08-14', '2022-12-08');
INSERT INTO potrazuje (idZahtjev, idVozilo, potraznjaOd, potraznjaDo) VALUES (5, 8, '2022-08-14', '2022-09-28');

-- Popunjavanje podataka relacije recenzija
INSERT INTO recenzija (sadrzaj, ocjena, kilometraza, vrijeme, idUnajmitelj, idVozilo) VALUES ('In sagittis dui vel nisl. Duis ac nibh. Fusce lacus purus, aliquet at, feugiat non, pretium quis, lectus.', 5, 148351, '2023-01-24T11:26:52Z', 2, 4);
INSERT INTO recenzija (sadrzaj, ocjena, kilometraza, vrijeme, idUnajmitelj, idVozilo) VALUES ('Sed sagittis. Nam congue, risus semper porta volutpat, quam pede lobortis ligula, sit amet eleifend pede libero quis orci. Nullam molestie nibh in lectus.', 1, 145528, '2022-06-30T14:09:55Z', 9, 6);
INSERT INTO recenzija (sadrzaj, ocjena, kilometraza, vrijeme, idUnajmitelj, idVozilo) VALUES ('Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vivamus vestibulum sagittis sapien. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.', 4, 116369, '2022-05-07T00:34:43Z', 4, 6);
INSERT INTO recenzija (sadrzaj, ocjena, kilometraza, vrijeme, idUnajmitelj, idVozilo) VALUES ('Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis. Integer aliquet, massa id lobortis convallis, tortor risus dapibus augue, vel accumsan tellus nisi eu orci. Mauris lacinia sapien quis libero.', 4, 52014, '2023-03-29T09:46:50Z', 9, 7);
INSERT INTO recenzija (sadrzaj, ocjena, kilometraza, vrijeme, idUnajmitelj, idVozilo) VALUES ('In congue. Etiam justo. Etiam pretium iaculis justo.', 1, 135199, '2023-02-16T15:04:11Z', 4, 8);
INSERT INTO recenzija (sadrzaj, ocjena, kilometraza, vrijeme, idUnajmitelj, idVozilo) VALUES ('Fusce consequat. Nulla nisl. Nunc nisl.', 3, 106833, '2022-10-22T01:25:50Z', 1, 6);
INSERT INTO recenzija (sadrzaj, ocjena, kilometraza, vrijeme, idUnajmitelj, idVozilo) VALUES ('Nullam porttitor lacus at turpis. Donec posuere metus vitae ipsum. Aliquam non mauris.', 1, 136575, '2022-08-10T10:09:59Z', 9, 1);
INSERT INTO recenzija (sadrzaj, ocjena, kilometraza, vrijeme, idUnajmitelj, idVozilo) VALUES ('Aenean lectus. Pellentesque eget nunc. Donec quis orci eget orci vehicula condimentum.', 1, 112574, '2022-12-23T17:24:43Z', 5, 1);
INSERT INTO recenzija (sadrzaj, ocjena, kilometraza, vrijeme, idUnajmitelj, idVozilo) VALUES ('In hac habitasse platea dictumst. Morbi vestibulum, velit id pretium iaculis, diam erat fermentum justo, nec condimentum neque sapien placerat ante. Nulla justo.', 2, 58669, '2022-12-29T04:40:59Z', 6, 1);
INSERT INTO recenzija (sadrzaj, ocjena, kilometraza, vrijeme, idUnajmitelj, idVozilo) VALUES ('Curabitur in libero ut massa volutpat convallis. Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo. Maecenas pulvinar lobortis est.', 4, 93928, '2022-10-18T01:16:58Z', 6, 2);
INSERT INTO recenzija (sadrzaj, ocjena, kilometraza, vrijeme, idUnajmitelj, idVozilo) VALUES ('Sed sagittis. Nam congue, risus semper porta volutpat, quam pede lobortis ligula, sit amet eleifend pede libero quis orci. Nullam molestie nibh in lectus.', 4, 67319, '2022-06-09T09:30:52Z', 8, 8);
INSERT INTO recenzija (sadrzaj, ocjena, kilometraza, vrijeme, idUnajmitelj, idVozilo) VALUES ('Maecenas tristique, est et tempus semper, est quam pharetra magna, ac consequat metus sapien ut nunc. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Mauris viverra diam vitae quam. Suspendisse potenti.', 2, 53655, '2022-10-29T00:09:22Z', 10, 2);
INSERT INTO recenzija (sadrzaj, ocjena, kilometraza, vrijeme, idUnajmitelj, idVozilo) VALUES ('Quisque id justo sit amet sapien dignissim vestibulum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nulla dapibus dolor vel est. Donec odio justo, sollicitudin ut, suscipit a, feugiat et, eros.', 1, 80178, '2022-07-27T11:01:18Z', 8, 8);
INSERT INTO recenzija (sadrzaj, ocjena, kilometraza, vrijeme, idUnajmitelj, idVozilo) VALUES ('Cras mi pede, malesuada in, imperdiet et, commodo vulputate, justo. In blandit ultrices enim. Lorem ipsum dolor sit amet, consectetuer adipiscing elit.', 3, 103026, '2023-03-14T12:02:15Z', 1, 8);
INSERT INTO recenzija (sadrzaj, ocjena, kilometraza, vrijeme, idUnajmitelj, idVozilo) VALUES ('Sed ante. Vivamus tortor. Duis mattis egestas metus.', 3, 100124, '2023-01-28T05:16:38Z', 9, 7);

-- Popunjavanje podataka relacije ugovor
INSERT INTO ugovor (oznUgovor, sadrzaj, naslov, putDoPotpisa, vrijemePotpisa, ugovorenaCijena) VALUES ('CR88-13060-897-4', 'Praesent id massa id nisl venenatis lacinia. Aenean sit amet justo. Morbi ut odio.

Cras mi pede, malesuada in, imperdiet et, commodo vulputate, justo. In blandit ultrices enim. Lorem ipsum dolor sit amet, consectetuer adipiscing elit.', 'Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis.', null, null, 5250.16);
INSERT INTO ugovor (oznUgovor, sadrzaj, naslov, putDoPotpisa, vrijemePotpisa, ugovorenaCijena) VALUES ('CN98-94272-523-8', 'Vestibulum ac est lacinia nisi venenatis tristique. Fusce congue, diam id ornare imperdiet, sapien urna pretium nisl, ut volutpat sapien arcu sed augue. Aliquam erat volutpat.

In congue. Etiam justo. Etiam pretium iaculis justo.

In hac habitasse platea dictumst. Etiam faucibus cursus urna. Ut tellus.', 'Donec diam neque, vestibulum eget, vulputate ut, ultrices vel, augue.', null, null, 2424.21);
INSERT INTO ugovor (oznUgovor, sadrzaj, naslov, putDoPotpisa, vrijemePotpisa, ugovorenaCijena) VALUES ('CM58-69440-365-0', 'Praesent id massa id nisl venenatis lacinia. Aenean sit amet justo. Morbi ut odio.', 'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae.', null, null, 9651.52);
INSERT INTO ugovor (oznUgovor, sadrzaj, naslov, putDoPotpisa, vrijemePotpisa, ugovorenaCijena) VALUES ('CT58-52150-811-3', 'In congue. Etiam justo. Etiam pretium iaculis justo.', 'Nulla dapibus dolor vel est. Donec odio justo, sollicitudin ut, suscipit a, feugiat et, eros.', null, null, 6233.01);
INSERT INTO ugovor (oznUgovor, sadrzaj, naslov, putDoPotpisa, vrijemePotpisa, ugovorenaCijena) VALUES ('CP11-72876-559-3', 'Morbi porttitor lorem id ligula. Suspendisse ornare consequat lectus. In est risus, auctor sed, tristique in, tempus sit amet, sem.

Fusce consequat. Nulla nisl. Nunc nisl.

Duis bibendum, felis sed interdum venenatis, turpis enim blandit mi, in porttitor pede justo eu massa. Donec dapibus. Duis at velit eu est congue elementum.', 'Duis aliquam convallis nunc. Proin at turpis a pede posuere nonummy. Integer non velit.', null, null, 1034.21);
INSERT INTO ugovor (oznUgovor, sadrzaj, naslov, putDoPotpisa, vrijemePotpisa, ugovorenaCijena) VALUES ('CN30-22883-772-7', 'Quisque id justo sit amet sapien dignissim vestibulum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nulla dapibus dolor vel est. Donec odio justo, sollicitudin ut, suscipit a, feugiat et, eros.

Vestibulum ac est lacinia nisi venenatis tristique. Fusce congue, diam id ornare imperdiet, sapien urna pretium nisl, ut volutpat sapien arcu sed augue. Aliquam erat volutpat.', 'Aliquam sit amet diam in magna bibendum imperdiet. Nullam orci pede, venenatis non, sodales sed, tincidunt eu, felis.', '/ugovori/potpisi/6', '2022-12-06T22:34:04Z', 4910.01);
INSERT INTO ugovor (oznUgovor, sadrzaj, naslov, putDoPotpisa, vrijemePotpisa, ugovorenaCijena) VALUES ('CW19-96758-455-5', 'Curabitur in libero ut massa volutpat convallis. Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo. Maecenas pulvinar lobortis est.

Phasellus sit amet erat. Nulla tempus. Vivamus in felis eu sapien cursus vestibulum.', 'Maecenas tristique, est et tempus semper, est quam pharetra magna, ac consequat metus sapien ut nunc.', null, null, 9443.19);
INSERT INTO ugovor (oznUgovor, sadrzaj, naslov, putDoPotpisa, vrijemePotpisa, ugovorenaCijena) VALUES ('CL90-46835-388-9', 'Maecenas ut massa quis augue luctus tincidunt. Nulla mollis molestie lorem. Quisque ut erat.', 'Maecenas leo odio, condimentum id, luctus nec, molestie sed, justo. Pellentesque viverra pede ac diam.', '/ugovori/potpisi/8', '2023-03-16T21:16:01Z', 7180.85);
INSERT INTO ugovor (oznUgovor, sadrzaj, naslov, putDoPotpisa, vrijemePotpisa, ugovorenaCijena) VALUES ('CU82-56406-848-0', 'Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis. Integer aliquet, massa id lobortis convallis, tortor risus dapibus augue, vel accumsan tellus nisi eu orci. Mauris lacinia sapien quis libero.

Nullam sit amet turpis elementum ligula vehicula consequat. Morbi a ipsum. Integer a nibh.', 'Aliquam erat volutpat.', null, null, 7119.34);
INSERT INTO ugovor (oznUgovor, sadrzaj, naslov, putDoPotpisa, vrijemePotpisa, ugovorenaCijena) VALUES ('CB95-41702-845-4', 'Fusce posuere felis sed lacus. Morbi sem mauris, laoreet ut, rhoncus aliquet, pulvinar sed, nisl. Nunc rhoncus dui vel sem.

Sed sagittis. Nam congue, risus semper porta volutpat, quam pede lobortis ligula, sit amet eleifend pede libero quis orci. Nullam molestie nibh in lectus.

Pellentesque at nulla. Suspendisse potenti. Cras in purus eu magna vulputate luctus.', 'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Mauris viverra diam vitae quam.', '/ugovori/potpisi/10', '2022-06-10T07:22:54Z', 1056.46);
INSERT INTO ugovor (oznUgovor, sadrzaj, naslov, putDoPotpisa, vrijemePotpisa, ugovorenaCijena) VALUES ('CO14-38430-949-4', 'Maecenas tristique, est et tempus semper, est quam pharetra magna, ac consequat metus sapien ut nunc. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Mauris viverra diam vitae quam. Suspendisse potenti.', 'Etiam vel augue. Vestibulum rutrum rutrum neque.', '/ugovori/potpisi/11', '2022-08-14T13:52:52Z', 3695.27);
INSERT INTO ugovor (oznUgovor, sadrzaj, naslov, putDoPotpisa, vrijemePotpisa, ugovorenaCijena) VALUES ('CE84-66044-038-1', 'Integer tincidunt ante vel ipsum. Praesent blandit lacinia erat. Vestibulum sed magna at nunc commodo placerat.

Praesent blandit. Nam nulla. Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede.

Morbi porttitor lorem id ligula. Suspendisse ornare consequat lectus. In est risus, auctor sed, tristique in, tempus sit amet, sem.', 'Nulla ac enim. In tempor, turpis nec euismod scelerisque, quam turpis adipiscing lorem, vitae mattis nibh ligula nec sem.', null, null, 9748.02);
INSERT INTO ugovor (oznUgovor, sadrzaj, naslov, putDoPotpisa, vrijemePotpisa, ugovorenaCijena) VALUES ('CP73-24686-536-7', 'Sed ante. Vivamus tortor. Duis mattis egestas metus.

Aenean fermentum. Donec ut mauris eget massa tempor convallis. Nulla neque libero, convallis eget, eleifend luctus, ultricies eu, nibh.

Quisque id justo sit amet sapien dignissim vestibulum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nulla dapibus dolor vel est. Donec odio justo, sollicitudin ut, suscipit a, feugiat et, eros.', 'Maecenas ut massa quis augue luctus tincidunt. Nulla mollis molestie lorem. Quisque ut erat.', '/ugovori/potpisi/13', '2022-11-12T06:34:16Z', 6448.5);
INSERT INTO ugovor (oznUgovor, sadrzaj, naslov, putDoPotpisa, vrijemePotpisa, ugovorenaCijena) VALUES ('CF01-95703-395-0', 'Duis aliquam convallis nunc. Proin at turpis a pede posuere nonummy. Integer non velit.', 'Integer tincidunt ante vel ipsum. Praesent blandit lacinia erat. Vestibulum sed magna at nunc commodo placerat.', null, '2023-03-30T03:29:30Z', 3321.27);
INSERT INTO ugovor (oznUgovor, sadrzaj, naslov, putDoPotpisa, vrijemePotpisa, ugovorenaCijena) VALUES ('CD02-15639-341-5', 'Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vivamus vestibulum sagittis sapien. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.', 'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae.', null, '2023-03-13T17:29:48Z', 2530.2);

-- Popunjavanje podataka relacije najam
-- popravljeno zato što vrijemeDo više nije nullable
INSERT INTO najam (vrijemeOd, vrijemeDo, prijedeno, aktivan, idVozilo, idUgovor) VALUES ('2022-06-30', '2022-07-10', 4298, false, 1, 5);
INSERT INTO najam (vrijemeOd, vrijemeDo, prijedeno, aktivan, idVozilo, idUgovor) VALUES ('2023-02-17', '2023-02-28', null, true, 4, 14);
INSERT INTO najam (vrijemeOd, vrijemeDo, prijedeno, aktivan, idVozilo, idUgovor) VALUES ('2023-03-21', '2023-04-21', null, true, 5, 3);
INSERT INTO najam (vrijemeOd, vrijemeDo, prijedeno, aktivan, idVozilo, idUgovor) VALUES ('2022-10-12', '2022-10-30', 802, false, 4, 13);
INSERT INTO najam (vrijemeOd, vrijemeDo, prijedeno, aktivan, idVozilo, idUgovor) VALUES ('2022-10-25', '2022-11-07', 3615, false, 7, 6);
INSERT INTO najam (vrijemeOd, vrijemeDo, prijedeno, aktivan, idVozilo, idUgovor) VALUES ('2022-07-07', '2023-08-05', 1524, false, 2, 15);
INSERT INTO najam (vrijemeOd, vrijemeDo, prijedeno, aktivan, idVozilo, idUgovor) VALUES ('2022-07-01', '2022-08-15', null, true, 3, 9);
INSERT INTO najam (vrijemeOd, vrijemeDo, prijedeno, aktivan, idVozilo, idUgovor) VALUES ('2022-08-28', '2023-09-28', 4223, false, 8, 11);
INSERT INTO najam (vrijemeOd, vrijemeDo, prijedeno, aktivan, idVozilo, idUgovor) VALUES ('2022-09-23', '2022-10-15', 1030, false, 7, 2);
INSERT INTO najam (vrijemeOd, vrijemeDo, prijedeno, aktivan, idVozilo, idUgovor) VALUES ('2023-03-07', '2023-04-14', null, true, 4, 8);
INSERT INTO najam (vrijemeOd, vrijemeDo, prijedeno, aktivan, idVozilo, idUgovor) VALUES ('2022-07-07', '2023-07-28', 1794, false, 5, 15);
INSERT INTO najam (vrijemeOd, vrijemeDo, prijedeno, aktivan, idVozilo, idUgovor) VALUES ('2022-07-13', '2022-07-14', 238, false, 6, 5);
INSERT INTO najam (vrijemeOd, vrijemeDo, prijedeno, aktivan, idVozilo, idUgovor) VALUES ('2022-04-15', '2023-04-28', 1897, false, 8, 1);
INSERT INTO najam (vrijemeOd, vrijemeDo, prijedeno, aktivan, idVozilo, idUgovor) VALUES ('2023-02-25', '2023-03-10', null, true, 8, 11);
INSERT INTO najam (vrijemeOd, vrijemeDo, prijedeno, aktivan, idVozilo, idUgovor) VALUES ('2022-09-10', '2022-10-01', null, true, 7, 9);

-- Popunjavanje podataka relacije obavijest
INSERT INTO obavijest (sadrzaj, vrijeme, videna, obrisana, idNajam) VALUES ('Provjerite Vaše najmove! Uskoro ističe rok za plaćanje najma 2', '2022-06-16T09:09:53Z', false, true, 2);
INSERT INTO obavijest (sadrzaj, vrijeme, videna, obrisana, idNajam) VALUES ('Provjerite Vaše najmove! Uskoro ističe rok za plaćanje najma 13', '2023-01-09T15:15:29Z', false, true, 13);
INSERT INTO obavijest (sadrzaj, vrijeme, videna, obrisana, idNajam) VALUES ('Provjerite Vaše najmove! Uskoro ističe rok za plaćanje najma 13', '2023-02-07T01:48:59Z', true, true, 13);
INSERT INTO obavijest (sadrzaj, vrijeme, videna, obrisana, idNajam) VALUES ('Provjerite Vaše najmove! Uskoro ističe rok za plaćanje najma 3', '2022-05-29T19:29:20Z', true, false, 3);
INSERT INTO obavijest (sadrzaj, vrijeme, videna, obrisana, idNajam) VALUES ('Provjerite Vaše najmove! Uskoro ističe rok za plaćanje najma 2', '2022-04-19T22:59:11Z', false, true, 2);
INSERT INTO obavijest (sadrzaj, vrijeme, videna, obrisana, idNajam) VALUES ('Provjerite Vaše najmove! Uskoro ističe rok za plaćanje najma 6', '2022-06-10T13:23:16Z', true, true, 6);
INSERT INTO obavijest (sadrzaj, vrijeme, videna, obrisana, idNajam) VALUES ('Provjerite Vaše najmove! Uskoro ističe rok za plaćanje najma 3', '2022-10-22T02:59:18Z', true, false, 3);
INSERT INTO obavijest (sadrzaj, vrijeme, videna, obrisana, idNajam) VALUES ('Provjerite Vaše najmove! Uskoro ističe rok za plaćanje najma 8', '2022-11-12T17:20:09Z', false, false, 8);
INSERT INTO obavijest (sadrzaj, vrijeme, videna, obrisana, idNajam) VALUES ('Provjerite Vaše najmove! Uskoro ističe rok za plaćanje najma 4', '2022-06-06T09:44:59Z', true, true, 4);
INSERT INTO obavijest (sadrzaj, vrijeme, videna, obrisana, idNajam) VALUES ('Provjerite Vaše najmove! Uskoro ističe rok za plaćanje najma 1', '2022-08-31T04:09:32Z', true, false, 1);
INSERT INTO obavijest (sadrzaj, vrijeme, videna, obrisana, idNajam) VALUES ('Provjerite Vaše najmove! Uskoro ističe rok za plaćanje najma 15', '2022-12-07T15:17:19Z', true, false, 15);
INSERT INTO obavijest (sadrzaj, vrijeme, videna, obrisana, idNajam) VALUES ('Provjerite Vaše najmove! Uskoro ističe rok za plaćanje najma 2', '2023-01-22T12:40:43Z', false, false, 2);
INSERT INTO obavijest (sadrzaj, vrijeme, videna, obrisana, idNajam) VALUES ('Provjerite Vaše najmove! Uskoro ističe rok za plaćanje najma 3', '2022-06-15T01:26:10Z', true, true, 3);
INSERT INTO obavijest (sadrzaj, vrijeme, videna, obrisana, idNajam) VALUES ('Provjerite Vaše najmove! Uskoro ističe rok za plaćanje najma 12', '2022-10-31T12:23:17Z', true, true, 12);
INSERT INTO obavijest (sadrzaj, vrijeme, videna, obrisana, idNajam) VALUES ('Provjerite Vaše najmove! Uskoro ističe rok za plaćanje najma 2', '2023-03-29T18:15:54Z', false, true, 2);

-- Popunjavanje podataka relacije izdavanje
INSERT INTO izdavanje (idUnajmitelj, idUgovor, datumIzdavanja, idIznajmljivac) VALUES (10, 5, '2023-03-15', 2);
INSERT INTO izdavanje (idUnajmitelj, idUgovor, datumIzdavanja, idIznajmljivac) VALUES (5, 2, '2022-08-19', 19);
INSERT INTO izdavanje (idUnajmitelj, idUgovor, datumIzdavanja, idIznajmljivac) VALUES (6, 1, '2022-06-18', 18);
INSERT INTO izdavanje (idUnajmitelj, idUgovor, datumIzdavanja, idIznajmljivac) VALUES (2, 7, '2022-04-08', 13);
INSERT INTO izdavanje (idUnajmitelj, idUgovor, datumIzdavanja, idIznajmljivac) VALUES (2, 2, '2022-05-08', 7);
INSERT INTO izdavanje (idUnajmitelj, idUgovor, datumIzdavanja, idIznajmljivac) VALUES (4, 11, '2022-08-16', 8);
INSERT INTO izdavanje (idUnajmitelj, idUgovor, datumIzdavanja, idIznajmljivac) VALUES (7, 9, '2022-07-01', 17);
INSERT INTO izdavanje (idUnajmitelj, idUgovor, datumIzdavanja, idIznajmljivac) VALUES (4, 2, '2022-09-06', 14);
INSERT INTO izdavanje (idUnajmitelj, idUgovor, datumIzdavanja, idIznajmljivac) VALUES (5, 3, '2023-01-16', 6);
INSERT INTO izdavanje (idUnajmitelj, idUgovor, datumIzdavanja, idIznajmljivac) VALUES (6, 14, '2022-05-07', 16);
INSERT INTO izdavanje (idUnajmitelj, idUgovor, datumIzdavanja, idIznajmljivac) VALUES (8, 1, '2022-08-26', 8);
INSERT INTO izdavanje (idUnajmitelj, idUgovor, datumIzdavanja, idIznajmljivac) VALUES (7, 1, '2023-01-24', 13);
INSERT INTO izdavanje (idUnajmitelj, idUgovor, datumIzdavanja, idIznajmljivac) VALUES (6, 7, '2022-10-04', 8);
INSERT INTO izdavanje (idUnajmitelj, idUgovor, datumIzdavanja, idIznajmljivac) VALUES (6, 9, '2022-12-14', 16);
INSERT INTO izdavanje (idUnajmitelj, idUgovor, datumIzdavanja, idIznajmljivac) VALUES (8, 9, '2023-03-04', 9);
