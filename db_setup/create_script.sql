DROP TABLE IF EXISTS unajmitelj CASCADE;
DROP TABLE IF EXISTS lokacija CASCADE;
DROP TABLE IF EXISTS ugovor CASCADE;
DROP TABLE IF EXISTS tip_vozila CASCADE;
DROP TABLE IF EXISTS zaposlenik CASCADE;
DROP TABLE IF EXISTS iznajmljivac CASCADE;
DROP TABLE IF EXISTS upravitelj_poslovnice CASCADE;
DROP TABLE IF EXISTS zahtjev_za_najmom CASCADE;
DROP TABLE IF EXISTS vozilo CASCADE;
DROP TABLE IF EXISTS potrazuje CASCADE;
DROP TABLE IF EXISTS recenzija CASCADE;
DROP TABLE IF EXISTS izdavanje CASCADE;
DROP TABLE IF EXISTS najam CASCADE;
DROP TABLE IF EXISTS obavijest CASCADE;

CREATE TABLE unajmitelj
(
  idUnajmitelj SERIAL NOT NULL,
  ime VARCHAR(32) NOT NULL,
  prezime VARCHAR(32) NOT NULL,
  oib VARCHAR(16) NOT NULL,
  email VARCHAR(320) NOT NULL,
  lozinka VARCHAR(512) NOT NULL,
  brojTelefona VARCHAR(16) NOT NULL,
  PRIMARY KEY (idUnajmitelj),
  CONSTRAINT un_uq_oib UNIQUE(oib),
  CONSTRAINT un_uq_eml UNIQUE(email)
);

CREATE TABLE lokacija
(
  idLokacija SERIAL NOT NULL,
  adresa VARCHAR(128),
  pbr VARCHAR(32) NOT NULL,
  grad VARCHAR(64) NOT NULL,
  drzava VARCHAR(64) NOT NULL,
  PRIMARY KEY (idLokacija)
);

CREATE TABLE tip_vozila -- sifarnik za tip vozila
(
  idTip VARCHAR(16) NOT NULL,
  nazivTip VARCHAR(128) NOT NULL,
  kategorija VARCHAR(8) NOT NULL, -- A, A1, A2, B, C, ...
  PRIMARY KEY (idTip)
);

CREATE TABLE zaposlenik
(
  idZaposlenik SERIAL NOT NULL,
  oib VARCHAR(16) NOT NULL,
  ime VARCHAR(32) NOT NULL,
  prezime VARCHAR(32) NOT NULL,
  idLokacija INT,
  PRIMARY KEY (idZaposlenik),
  FOREIGN KEY (idLokacija) REFERENCES lokacija(idLokacija),
  CONSTRAINT zap_uq_oib UNIQUE(oib)
);

CREATE TABLE iznajmljivac
(
  idZaposlenik INT NOT NULL,
  email VARCHAR(320) NOT NULL,
  brojTelefona VARCHAR(16) NOT NULL,
  lozinka VARCHAR(512) NOT NULL,
  PRIMARY KEY (idZaposlenik),
  FOREIGN KEY (idZaposlenik) REFERENCES zaposlenik(idZaposlenik)
);

CREATE TABLE upravitelj_poslovnice
(
  idZaposlenik INT NOT NULL,
  korisnickoIme VARCHAR(16) NOT NULL,
  lozinka VARCHAR(512) NOT NULL,
  upraviteljOd DATE NOT NULL,
  upraviteljDo DATE,
  PRIMARY KEY (idZaposlenik),
  FOREIGN KEY (idZaposlenik) REFERENCES zaposlenik(idZaposlenik)
);

CREATE TABLE zahtjev_za_najmom
(
  idZahtjev SERIAL NOT NULL,
  status VARCHAR(16) NOT NULL,
  vrijemeZahtjeva TIMESTAMP NOT NULL,
  obraden BOOLEAN NOT NULL,
  vrijemeObrada TIMESTAMP,
  idIznajmljivac INT,
  idUnajmitelj INT NOT NULL,
  PRIMARY KEY (idZahtjev),
  FOREIGN KEY (idIznajmljivac) REFERENCES iznajmljivac(idZaposlenik),
  FOREIGN KEY (idUnajmitelj) REFERENCES unajmitelj(idUnajmitelj),
  CONSTRAINT zzn_chk_status CHECK (status IN ('POTVRDEN', 'NA CEKANJU', 'ODBIJEN'))
);

CREATE TABLE ugovor
(
  idUgovor SERIAL NOT NULL,
  oznUgovor VARCHAR(64) NOT NULL,
  sadrzaj TEXT NOT NULL,
  naslov VARCHAR(128) NOT NULL,
  putDoPotpisa VARCHAR(1024),
  vrijemePotpisa TIMESTAMP,
  ugovorenaCijena NUMERIC(7,2),
  idzahtjev INT, -- dodano kasnije da se zna prema kojem zahtjevu za najmom je
                 -- ugovor napravljen
  PRIMARY KEY (idUgovor),
  -- dodano uz idzahtjev
  FOREIGN KEY (idzahtjev) REFERENCES zahtjev_za_najmom(idzahtjev),
  CONSTRAINT ug_uq_oznug UNIQUE(oznUgovor)
);

CREATE TABLE vozilo
(
  idVozilo SERIAL NOT NULL,
  registracija VARCHAR(16) NOT NULL,
  naziv VARCHAR(32) NOT NULL,
  proizvodjac VARCHAR(32) NOT NULL,
  kilometraza INT NOT NULL,
  dnevnaCijena NUMERIC(7,2) NOT NULL,
  putDoSlike VARCHAR(1024),
  idTip VARCHAR(16) NOT NULL,
  idLokacija INT,
  PRIMARY KEY (idVozilo),
  FOREIGN KEY (idTip) REFERENCES tip_vozila(idTip)
    ON UPDATE CASCADE,
  FOREIGN KEY (idLokacija) REFERENCES lokacija(idLokacija),
  CONSTRAINT voz_uq_reg UNIQUE(registracija)
);

CREATE TABLE potrazuje (
  idPotraznja SERIAL NOT NULL,
  idZahtjev INT NOT NULL,
  idVozilo INT NOT NULL,
  potraznjaOd DATE NOT NULL,
  potraznjaDo DATE NOT NULL,
  PRIMARY KEY (idPotraznja),
  FOREIGN KEY (idZahtjev) REFERENCES zahtjev_za_najmom(idZahtjev),
  FOREIGN KEY (idVozilo) REFERENCES vozilo(idVozilo)
    ON DELETE CASCADE, -- dodano da kada se briše vozilo brišu se sve potražnje 
  CONSTRAINT pot_uq_zn_vz UNIQUE(idZahtjev, idVozilo)
);

CREATE TABLE recenzija
(
  idRecenzija SERIAL NOT NULL,
  sadrzaj VARCHAR(256) NOT NULL,
  ocjena INT NOT NULL,
  kilometraza INT NOT NULL,
  vrijeme TIMESTAMP NOT NULL,
  idUnajmitelj INT NOT NULL,
  idVozilo INT NOT NULL,
  PRIMARY KEY (idRecenzija),
  FOREIGN KEY (idUnajmitelj) REFERENCES unajmitelj(idUnajmitelj),
  FOREIGN KEY (idVozilo) REFERENCES vozilo(idVozilo)
    ON DELETE CASCADE, -- dodano da kada se briše vozilo brišu se svi najmovi
  CONSTRAINT rec_chk_ocjena CHECK (ocjena IN (1, 2, 3, 4, 5))
);

CREATE TABLE izdavanje
(
  idUnajmitelj INT NOT NULL,
  idUgovor INT NOT NULL,
  datumIzdavanja DATE NOT NULL,
  idIznajmljivac INT NOT NULL,
  PRIMARY KEY (idUnajmitelj, idUgovor),
  FOREIGN KEY (idUnajmitelj) REFERENCES unajmitelj(idUnajmitelj),
  FOREIGN KEY (idUgovor) REFERENCES ugovor(idUgovor)
    ON DELETE CASCADE, -- dodano kako bi se izdavanje izbrisalo ako se obriše
                       -- dodijeljeni ugovor
  FOREIGN KEY (idIznajmljivac) REFERENCES iznajmljivac(idZaposlenik)
);

CREATE TABLE najam
(
  idNajam SERIAL NOT NULL,
  vrijemeOd TIMESTAMP NOT NULL,
  vrijemeDo TIMESTAMP NOT NULL, -- izmjenjen zbog nove logike za najmove
  prijedeno INT,
  aktivan BOOLEAN NOT NULL,
  idVozilo INT NOT NULL,
  idUgovor INT NOT NULL,
  PRIMARY KEY (idNajam),
  FOREIGN KEY (idVozilo) REFERENCES vozilo(idVozilo)
    ON DELETE CASCADE, -- dodano da kada se briše vozilo brišu se svi najmovi
  FOREIGN KEY (idUgovor) REFERENCES ugovor(idUgovor)
    ON DELETE CASCADE -- dodano da kada se briše ugovor brišu se svi najmovi
);

CREATE TABLE obavijest
(
  idObavijest SERIAL NOT NULL,
  sadrzaj VARCHAR(128) NOT NULL,
  vrijeme TIMESTAMP NOT NULL,
  videna BOOLEAN NOT NULL,
  obrisana BOOLEAN NOT NULL,
  idNajam INT NOT NULL,
  PRIMARY KEY (idObavijest, idNajam),
  FOREIGN KEY (idNajam) REFERENCES najam(idNajam)
);
