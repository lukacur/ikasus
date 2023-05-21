--
-- PostgreSQL database dump
--

-- Dumped from database version 15.0
-- Dumped by pg_dump version 15.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: izdavanje; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.izdavanje (
    idunajmitelj integer NOT NULL,
    idugovor integer NOT NULL,
    datumizdavanja date NOT NULL,
    idiznajmljivac integer NOT NULL
);


ALTER TABLE public.izdavanje OWNER TO postgres;

--
-- Name: iznajmljivac; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.iznajmljivac (
    idzaposlenik integer NOT NULL,
    email character varying(320) NOT NULL,
    brojtelefona character varying(16) NOT NULL,
    lozinka character varying(512) NOT NULL
);


ALTER TABLE public.iznajmljivac OWNER TO postgres;

--
-- Name: lokacija; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.lokacija (
    idlokacija integer NOT NULL,
    adresa character varying(128),
    pbr character varying(32) NOT NULL,
    grad character varying(64) NOT NULL,
    drzava character varying(64) NOT NULL
);


ALTER TABLE public.lokacija OWNER TO postgres;

--
-- Name: lokacija_idlokacija_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.lokacija_idlokacija_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.lokacija_idlokacija_seq OWNER TO postgres;

--
-- Name: lokacija_idlokacija_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.lokacija_idlokacija_seq OWNED BY public.lokacija.idlokacija;


--
-- Name: najam; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.najam (
    idnajam integer NOT NULL,
    vrijemeod timestamp without time zone NOT NULL,
    vrijemedo timestamp without time zone NOT NULL,
    prijedeno integer,
    aktivan boolean NOT NULL,
    idvozilo integer NOT NULL,
    idugovor integer NOT NULL
);


ALTER TABLE public.najam OWNER TO postgres;

--
-- Name: najam_idnajam_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.najam_idnajam_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.najam_idnajam_seq OWNER TO postgres;

--
-- Name: najam_idnajam_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.najam_idnajam_seq OWNED BY public.najam.idnajam;


--
-- Name: obavijest; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.obavijest (
    idobavijest integer NOT NULL,
    sadrzaj character varying(128) NOT NULL,
    vrijeme timestamp without time zone NOT NULL,
    videna boolean NOT NULL,
    obrisana boolean NOT NULL,
    idnajam integer NOT NULL
);


ALTER TABLE public.obavijest OWNER TO postgres;

--
-- Name: obavijest_idobavijest_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.obavijest_idobavijest_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.obavijest_idobavijest_seq OWNER TO postgres;

--
-- Name: obavijest_idobavijest_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.obavijest_idobavijest_seq OWNED BY public.obavijest.idobavijest;


--
-- Name: potrazuje; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.potrazuje (
    idpotraznja integer NOT NULL,
    idzahtjev integer NOT NULL,
    idvozilo integer NOT NULL,
    potraznjaod date NOT NULL,
    potraznjado date NOT NULL
);


ALTER TABLE public.potrazuje OWNER TO postgres;

--
-- Name: potrazuje_idpotraznja_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.potrazuje_idpotraznja_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.potrazuje_idpotraznja_seq OWNER TO postgres;

--
-- Name: potrazuje_idpotraznja_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.potrazuje_idpotraznja_seq OWNED BY public.potrazuje.idpotraznja;


--
-- Name: recenzija; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.recenzija (
    idrecenzija integer NOT NULL,
    sadrzaj character varying(256) NOT NULL,
    ocjena integer NOT NULL,
    kilometraza integer NOT NULL,
    vrijeme timestamp without time zone NOT NULL,
    idunajmitelj integer NOT NULL,
    idvozilo integer NOT NULL,
    CONSTRAINT rec_chk_ocjena CHECK ((ocjena = ANY (ARRAY[1, 2, 3, 4, 5])))
);


ALTER TABLE public.recenzija OWNER TO postgres;

--
-- Name: recenzija_idrecenzija_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.recenzija_idrecenzija_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.recenzija_idrecenzija_seq OWNER TO postgres;

--
-- Name: recenzija_idrecenzija_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.recenzija_idrecenzija_seq OWNED BY public.recenzija.idrecenzija;


--
-- Name: tip_vozila; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tip_vozila (
    idtip character varying(16) NOT NULL,
    nazivtip character varying(128) NOT NULL,
    kategorija character varying(8) NOT NULL
);


ALTER TABLE public.tip_vozila OWNER TO postgres;

--
-- Name: ugovor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ugovor (
    idugovor integer NOT NULL,
    oznugovor character varying(64) NOT NULL,
    sadrzaj text NOT NULL,
    naslov character varying(128) NOT NULL,
    putdopotpisa character varying(1024),
    vrijemepotpisa timestamp without time zone,
    ugovorenacijena numeric(7,2),
    idzahtjev integer
);


ALTER TABLE public.ugovor OWNER TO postgres;

--
-- Name: ugovor_idugovor_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.ugovor_idugovor_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.ugovor_idugovor_seq OWNER TO postgres;

--
-- Name: ugovor_idugovor_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.ugovor_idugovor_seq OWNED BY public.ugovor.idugovor;


--
-- Name: unajmitelj; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.unajmitelj (
    idunajmitelj integer NOT NULL,
    ime character varying(32) NOT NULL,
    prezime character varying(32) NOT NULL,
    oib character varying(16) NOT NULL,
    email character varying(320) NOT NULL,
    lozinka character varying(512) NOT NULL,
    brojtelefona character varying(16) NOT NULL
);


ALTER TABLE public.unajmitelj OWNER TO postgres;

--
-- Name: unajmitelj_idunajmitelj_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.unajmitelj_idunajmitelj_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.unajmitelj_idunajmitelj_seq OWNER TO postgres;

--
-- Name: unajmitelj_idunajmitelj_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.unajmitelj_idunajmitelj_seq OWNED BY public.unajmitelj.idunajmitelj;


--
-- Name: upravitelj_poslovnice; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.upravitelj_poslovnice (
    idzaposlenik integer NOT NULL,
    korisnickoime character varying(16) NOT NULL,
    lozinka character varying(512) NOT NULL,
    upraviteljod date NOT NULL,
    upraviteljdo date
);


ALTER TABLE public.upravitelj_poslovnice OWNER TO postgres;

--
-- Name: vozilo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vozilo (
    idvozilo integer NOT NULL,
    registracija character varying(16) NOT NULL,
    naziv character varying(32) NOT NULL,
    proizvodjac character varying(32) NOT NULL,
    kilometraza integer NOT NULL,
    dnevnacijena numeric(7,2) NOT NULL,
    putdoslike character varying(1024),
    idtip character varying(16) NOT NULL,
    idlokacija integer
);


ALTER TABLE public.vozilo OWNER TO postgres;

--
-- Name: vozilo_idvozilo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.vozilo_idvozilo_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.vozilo_idvozilo_seq OWNER TO postgres;

--
-- Name: vozilo_idvozilo_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.vozilo_idvozilo_seq OWNED BY public.vozilo.idvozilo;


--
-- Name: zahtjev_za_najmom; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.zahtjev_za_najmom (
    idzahtjev integer NOT NULL,
    status character varying(16) NOT NULL,
    vrijemezahtjeva timestamp without time zone NOT NULL,
    obraden boolean NOT NULL,
    vrijemeobrada timestamp without time zone,
    idiznajmljivac integer,
    idunajmitelj integer NOT NULL,
    CONSTRAINT zzn_chk_status CHECK (((status)::text = ANY ((ARRAY['POTVRDEN'::character varying, 'NA CEKANJU'::character varying, 'ODBIJEN'::character varying])::text[])))
);


ALTER TABLE public.zahtjev_za_najmom OWNER TO postgres;

--
-- Name: zahtjev_za_najmom_idzahtjev_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.zahtjev_za_najmom_idzahtjev_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.zahtjev_za_najmom_idzahtjev_seq OWNER TO postgres;

--
-- Name: zahtjev_za_najmom_idzahtjev_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.zahtjev_za_najmom_idzahtjev_seq OWNED BY public.zahtjev_za_najmom.idzahtjev;


--
-- Name: zaposlenik; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.zaposlenik (
    idzaposlenik integer NOT NULL,
    oib character varying(16) NOT NULL,
    ime character varying(32) NOT NULL,
    prezime character varying(32) NOT NULL,
    idlokacija integer
);


ALTER TABLE public.zaposlenik OWNER TO postgres;

--
-- Name: zaposlenik_idzaposlenik_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.zaposlenik_idzaposlenik_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.zaposlenik_idzaposlenik_seq OWNER TO postgres;

--
-- Name: zaposlenik_idzaposlenik_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.zaposlenik_idzaposlenik_seq OWNED BY public.zaposlenik.idzaposlenik;


--
-- Name: lokacija idlokacija; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lokacija ALTER COLUMN idlokacija SET DEFAULT nextval('public.lokacija_idlokacija_seq'::regclass);


--
-- Name: najam idnajam; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.najam ALTER COLUMN idnajam SET DEFAULT nextval('public.najam_idnajam_seq'::regclass);


--
-- Name: obavijest idobavijest; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.obavijest ALTER COLUMN idobavijest SET DEFAULT nextval('public.obavijest_idobavijest_seq'::regclass);


--
-- Name: potrazuje idpotraznja; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.potrazuje ALTER COLUMN idpotraznja SET DEFAULT nextval('public.potrazuje_idpotraznja_seq'::regclass);


--
-- Name: recenzija idrecenzija; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recenzija ALTER COLUMN idrecenzija SET DEFAULT nextval('public.recenzija_idrecenzija_seq'::regclass);


--
-- Name: ugovor idugovor; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ugovor ALTER COLUMN idugovor SET DEFAULT nextval('public.ugovor_idugovor_seq'::regclass);


--
-- Name: unajmitelj idunajmitelj; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unajmitelj ALTER COLUMN idunajmitelj SET DEFAULT nextval('public.unajmitelj_idunajmitelj_seq'::regclass);


--
-- Name: vozilo idvozilo; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vozilo ALTER COLUMN idvozilo SET DEFAULT nextval('public.vozilo_idvozilo_seq'::regclass);


--
-- Name: zahtjev_za_najmom idzahtjev; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.zahtjev_za_najmom ALTER COLUMN idzahtjev SET DEFAULT nextval('public.zahtjev_za_najmom_idzahtjev_seq'::regclass);


--
-- Name: zaposlenik idzaposlenik; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.zaposlenik ALTER COLUMN idzaposlenik SET DEFAULT nextval('public.zaposlenik_idzaposlenik_seq'::regclass);


--
-- Data for Name: izdavanje; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.izdavanje VALUES (10, 5, '2023-03-15', 2);
INSERT INTO public.izdavanje VALUES (5, 2, '2022-08-19', 19);
INSERT INTO public.izdavanje VALUES (6, 1, '2022-06-18', 18);
INSERT INTO public.izdavanje VALUES (2, 7, '2022-04-08', 13);
INSERT INTO public.izdavanje VALUES (2, 2, '2022-05-08', 7);
INSERT INTO public.izdavanje VALUES (4, 11, '2022-08-16', 8);
INSERT INTO public.izdavanje VALUES (7, 9, '2022-07-01', 17);
INSERT INTO public.izdavanje VALUES (4, 2, '2022-09-06', 14);
INSERT INTO public.izdavanje VALUES (5, 3, '2023-01-16', 6);
INSERT INTO public.izdavanje VALUES (6, 14, '2022-05-07', 16);
INSERT INTO public.izdavanje VALUES (8, 1, '2022-08-26', 8);
INSERT INTO public.izdavanje VALUES (7, 1, '2023-01-24', 13);
INSERT INTO public.izdavanje VALUES (6, 7, '2022-10-04', 8);
INSERT INTO public.izdavanje VALUES (6, 9, '2022-12-14', 16);
INSERT INTO public.izdavanje VALUES (8, 9, '2023-03-04', 9);
INSERT INTO public.izdavanje VALUES (1, 18, '2023-05-21', 21);
INSERT INTO public.izdavanje VALUES (1, 19, '2023-05-21', 21);


--
-- Data for Name: iznajmljivac; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.iznajmljivac VALUES (1, 'Marja', '09482575647', 'Prin');
INSERT INTO public.iznajmljivac VALUES (2, 'Karena', '09966404008', 'Pamphilon');
INSERT INTO public.iznajmljivac VALUES (3, 'Reilly', '09483665810', 'Rigeby');
INSERT INTO public.iznajmljivac VALUES (4, 'Nick', '09743072606', 'Whatsize');
INSERT INTO public.iznajmljivac VALUES (5, 'Hermia', '09605913206', 'Woodhall');
INSERT INTO public.iznajmljivac VALUES (6, 'Hertha', '09924275226', 'Tarply');
INSERT INTO public.iznajmljivac VALUES (7, 'Eberto', '09701185937', 'Attyeo');
INSERT INTO public.iznajmljivac VALUES (8, 'Wyatt', '09152305272', 'Bazeley');
INSERT INTO public.iznajmljivac VALUES (9, 'Ana', '09390752950', 'Russo');
INSERT INTO public.iznajmljivac VALUES (10, 'Perri', '09362621362', 'Vergo');
INSERT INTO public.iznajmljivac VALUES (11, 'Shellie', '09669018199', 'McCaffery');
INSERT INTO public.iznajmljivac VALUES (12, 'Jeremiah', '09657614932', 'Mowson');
INSERT INTO public.iznajmljivac VALUES (13, 'Eula', '09051302521', 'Stockle');
INSERT INTO public.iznajmljivac VALUES (14, 'Rolland', '09217844284', 'Morman');
INSERT INTO public.iznajmljivac VALUES (15, 'Malorie', '09747117673', 'Godfray');
INSERT INTO public.iznajmljivac VALUES (16, 'Joell', '09206573886', 'Leate');
INSERT INTO public.iznajmljivac VALUES (17, 'Dallis', '09448397720', 'Foord');
INSERT INTO public.iznajmljivac VALUES (18, 'Hunt', '09848631893', 'Abbati');
INSERT INTO public.iznajmljivac VALUES (19, 'Licha', '09415850751', 'Torresi');
INSERT INTO public.iznajmljivac VALUES (20, 'Sabine', '09938782230', 'Moules');
INSERT INTO public.iznajmljivac VALUES (21, 'perica@ikasus.com.hr', '0918854431', '$2a$08$TaHR4qz56gPU8c6Pv7jSO.JD.Lu5swcyHtqJCQflI1mMs1kBYoDtu');


--
-- Data for Name: lokacija; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.lokacija VALUES (1, 'Ulica poznatih velikana 16', '10000', 'Zagreb', 'Hrvatska');
INSERT INTO public.lokacija VALUES (2, 'Ulica automobilske industrije 38', '10000', 'Zagreb', 'Hrvatska');
INSERT INTO public.lokacija VALUES (3, 'Trg poznatih i nepoznatih 22', '31000', 'Osijek', 'Hrvatska');


--
-- Data for Name: najam; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.najam VALUES (1, '2022-06-30 00:00:00', '2022-07-10 00:00:00', 4298, false, 1, 5);
INSERT INTO public.najam VALUES (3, '2023-03-21 00:00:00', '2023-04-21 00:00:00', NULL, true, 5, 3);
INSERT INTO public.najam VALUES (5, '2022-10-25 00:00:00', '2022-11-07 00:00:00', 3615, false, 7, 6);
INSERT INTO public.najam VALUES (8, '2022-08-28 00:00:00', '2023-09-28 00:00:00', 4223, false, 8, 11);
INSERT INTO public.najam VALUES (9, '2022-09-23 00:00:00', '2022-10-15 00:00:00', 1030, false, 7, 2);
INSERT INTO public.najam VALUES (11, '2022-07-07 00:00:00', '2023-07-28 00:00:00', 1794, false, 5, 15);
INSERT INTO public.najam VALUES (12, '2022-07-13 00:00:00', '2022-07-14 00:00:00', 238, false, 6, 5);
INSERT INTO public.najam VALUES (13, '2022-04-15 00:00:00', '2023-04-28 00:00:00', 1897, false, 8, 1);
INSERT INTO public.najam VALUES (14, '2023-02-25 00:00:00', '2023-03-10 00:00:00', NULL, true, 8, 11);
INSERT INTO public.najam VALUES (15, '2022-09-10 00:00:00', '2022-10-01 00:00:00', NULL, true, 7, 9);
INSERT INTO public.najam VALUES (6, '2022-07-07 02:00:00', '2023-08-05 02:00:00', 11, false, 2, 15);
INSERT INTO public.najam VALUES (10, '2023-03-07 01:00:00', '2023-04-14 02:00:00', 11, true, 4, 6);
INSERT INTO public.najam VALUES (2, '2023-02-17 01:00:00', '2023-02-28 01:00:00', NULL, false, 4, 6);
INSERT INTO public.najam VALUES (4, '2022-10-12 02:00:00', '2022-10-30 02:00:00', 802, false, 4, 6);
INSERT INTO public.najam VALUES (16, '2023-05-16 02:00:00', '2023-05-18 02:00:00', NULL, false, 3, 4);
INSERT INTO public.najam VALUES (17, '2023-05-24 02:00:00', '2023-06-01 02:00:00', NULL, false, 3, 11);
INSERT INTO public.najam VALUES (18, '2023-05-01 02:00:00', '2023-05-02 02:00:00', NULL, false, 3, 19);
INSERT INTO public.najam VALUES (7, '2022-07-01 04:00:00', '2022-08-15 04:00:00', 112, false, 3, 9);
INSERT INTO public.najam VALUES (19, '2023-05-09 00:00:00', '2023-05-25 00:00:00', NULL, true, 1, 18);


--
-- Data for Name: obavijest; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.obavijest VALUES (1, 'Provjerite Va┼íe najmove! Uskoro isti─ìe rok za pla─çanje najma 2', '2022-06-16 09:09:53', false, true, 2);
INSERT INTO public.obavijest VALUES (2, 'Provjerite Va┼íe najmove! Uskoro isti─ìe rok za pla─çanje najma 13', '2023-01-09 15:15:29', false, true, 13);
INSERT INTO public.obavijest VALUES (3, 'Provjerite Va┼íe najmove! Uskoro isti─ìe rok za pla─çanje najma 13', '2023-02-07 01:48:59', true, true, 13);
INSERT INTO public.obavijest VALUES (4, 'Provjerite Va┼íe najmove! Uskoro isti─ìe rok za pla─çanje najma 3', '2022-05-29 19:29:20', true, false, 3);
INSERT INTO public.obavijest VALUES (5, 'Provjerite Va┼íe najmove! Uskoro isti─ìe rok za pla─çanje najma 2', '2022-04-19 22:59:11', false, true, 2);
INSERT INTO public.obavijest VALUES (6, 'Provjerite Va┼íe najmove! Uskoro isti─ìe rok za pla─çanje najma 6', '2022-06-10 13:23:16', true, true, 6);
INSERT INTO public.obavijest VALUES (7, 'Provjerite Va┼íe najmove! Uskoro isti─ìe rok za pla─çanje najma 3', '2022-10-22 02:59:18', true, false, 3);
INSERT INTO public.obavijest VALUES (8, 'Provjerite Va┼íe najmove! Uskoro isti─ìe rok za pla─çanje najma 8', '2022-11-12 17:20:09', false, false, 8);
INSERT INTO public.obavijest VALUES (9, 'Provjerite Va┼íe najmove! Uskoro isti─ìe rok za pla─çanje najma 4', '2022-06-06 09:44:59', true, true, 4);
INSERT INTO public.obavijest VALUES (10, 'Provjerite Va┼íe najmove! Uskoro isti─ìe rok za pla─çanje najma 1', '2022-08-31 04:09:32', true, false, 1);
INSERT INTO public.obavijest VALUES (11, 'Provjerite Va┼íe najmove! Uskoro isti─ìe rok za pla─çanje najma 15', '2022-12-07 15:17:19', true, false, 15);
INSERT INTO public.obavijest VALUES (12, 'Provjerite Va┼íe najmove! Uskoro isti─ìe rok za pla─çanje najma 2', '2023-01-22 12:40:43', false, false, 2);
INSERT INTO public.obavijest VALUES (13, 'Provjerite Va┼íe najmove! Uskoro isti─ìe rok za pla─çanje najma 3', '2022-06-15 01:26:10', true, true, 3);
INSERT INTO public.obavijest VALUES (14, 'Provjerite Va┼íe najmove! Uskoro isti─ìe rok za pla─çanje najma 12', '2022-10-31 12:23:17', true, true, 12);
INSERT INTO public.obavijest VALUES (15, 'Provjerite Va┼íe najmove! Uskoro isti─ìe rok za pla─çanje najma 2', '2023-03-29 18:15:54', false, true, 2);


--
-- Data for Name: potrazuje; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.potrazuje VALUES (1, 21, 3, '2022-09-24', '2022-10-24');
INSERT INTO public.potrazuje VALUES (2, 24, 5, '2022-08-21', '2022-09-01');
INSERT INTO public.potrazuje VALUES (3, 29, 3, '2022-08-26', '2022-09-15');
INSERT INTO public.potrazuje VALUES (4, 8, 1, '2022-12-26', '2023-01-09');
INSERT INTO public.potrazuje VALUES (5, 15, 8, '2022-11-18', '2022-12-22');
INSERT INTO public.potrazuje VALUES (6, 1, 2, '2022-09-10', '2022-09-15');
INSERT INTO public.potrazuje VALUES (7, 26, 2, '2022-09-10', '2022-09-25');
INSERT INTO public.potrazuje VALUES (8, 29, 8, '2022-09-20', '2022-10-18');
INSERT INTO public.potrazuje VALUES (9, 11, 5, '2022-11-27', '2023-01-08');
INSERT INTO public.potrazuje VALUES (10, 18, 3, '2022-06-06', '2022-09-15');
INSERT INTO public.potrazuje VALUES (12, 2, 5, '2023-01-11', '2023-02-20');
INSERT INTO public.potrazuje VALUES (13, 13, 6, '2022-11-27', '2022-12-13');
INSERT INTO public.potrazuje VALUES (14, 11, 7, '2022-10-25', '2022-11-28');
INSERT INTO public.potrazuje VALUES (15, 12, 2, '2022-11-11', '2023-02-21');
INSERT INTO public.potrazuje VALUES (16, 6, 3, '2022-09-13', '2022-12-24');
INSERT INTO public.potrazuje VALUES (17, 3, 7, '2022-10-16', '2022-11-06');
INSERT INTO public.potrazuje VALUES (18, 1, 5, '2023-02-03', '2023-03-23');
INSERT INTO public.potrazuje VALUES (19, 27, 1, '2022-08-14', '2022-12-08');
INSERT INTO public.potrazuje VALUES (20, 5, 8, '2022-08-14', '2022-09-28');
INSERT INTO public.potrazuje VALUES (21, 31, 1, '2024-07-25', '2024-07-26');
INSERT INTO public.potrazuje VALUES (22, 31, 5, '2024-07-25', '2024-07-26');
INSERT INTO public.potrazuje VALUES (23, 32, 1, '2024-07-25', '2024-07-26');
INSERT INTO public.potrazuje VALUES (24, 32, 5, '2024-07-25', '2024-07-26');
INSERT INTO public.potrazuje VALUES (25, 33, 1, '2023-05-09', '2023-05-25');
INSERT INTO public.potrazuje VALUES (26, 33, 5, '2023-05-09', '2023-05-25');
INSERT INTO public.potrazuje VALUES (27, 34, 5, '2023-05-01', '2023-05-02');
INSERT INTO public.potrazuje VALUES (28, 34, 3, '2023-05-01', '2023-05-02');


--
-- Data for Name: recenzija; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.recenzija VALUES (1, 'In sagittis dui vel nisl. Duis ac nibh. Fusce lacus purus, aliquet at, feugiat non, pretium quis, lectus.', 5, 148351, '2023-01-24 11:26:52', 2, 4);
INSERT INTO public.recenzija VALUES (2, 'Sed sagittis. Nam congue, risus semper porta volutpat, quam pede lobortis ligula, sit amet eleifend pede libero quis orci. Nullam molestie nibh in lectus.', 1, 145528, '2022-06-30 14:09:55', 9, 6);
INSERT INTO public.recenzija VALUES (3, 'Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vivamus vestibulum sagittis sapien. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.', 4, 116369, '2022-05-07 00:34:43', 4, 6);
INSERT INTO public.recenzija VALUES (4, 'Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis. Integer aliquet, massa id lobortis convallis, tortor risus dapibus augue, vel accumsan tellus nisi eu orci. Mauris lacinia sapien quis libero.', 4, 52014, '2023-03-29 09:46:50', 9, 7);
INSERT INTO public.recenzija VALUES (5, 'In congue. Etiam justo. Etiam pretium iaculis justo.', 1, 135199, '2023-02-16 15:04:11', 4, 8);
INSERT INTO public.recenzija VALUES (6, 'Fusce consequat. Nulla nisl. Nunc nisl.', 3, 106833, '2022-10-22 01:25:50', 1, 6);
INSERT INTO public.recenzija VALUES (7, 'Nullam porttitor lacus at turpis. Donec posuere metus vitae ipsum. Aliquam non mauris.', 1, 136575, '2022-08-10 10:09:59', 9, 1);
INSERT INTO public.recenzija VALUES (8, 'Aenean lectus. Pellentesque eget nunc. Donec quis orci eget orci vehicula condimentum.', 1, 112574, '2022-12-23 17:24:43', 5, 1);
INSERT INTO public.recenzija VALUES (9, 'In hac habitasse platea dictumst. Morbi vestibulum, velit id pretium iaculis, diam erat fermentum justo, nec condimentum neque sapien placerat ante. Nulla justo.', 2, 58669, '2022-12-29 04:40:59', 6, 1);
INSERT INTO public.recenzija VALUES (10, 'Curabitur in libero ut massa volutpat convallis. Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo. Maecenas pulvinar lobortis est.', 4, 93928, '2022-10-18 01:16:58', 6, 2);
INSERT INTO public.recenzija VALUES (11, 'Sed sagittis. Nam congue, risus semper porta volutpat, quam pede lobortis ligula, sit amet eleifend pede libero quis orci. Nullam molestie nibh in lectus.', 4, 67319, '2022-06-09 09:30:52', 8, 8);
INSERT INTO public.recenzija VALUES (12, 'Maecenas tristique, est et tempus semper, est quam pharetra magna, ac consequat metus sapien ut nunc. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Mauris viverra diam vitae quam. Suspendisse potenti.', 2, 53655, '2022-10-29 00:09:22', 10, 2);
INSERT INTO public.recenzija VALUES (13, 'Quisque id justo sit amet sapien dignissim vestibulum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nulla dapibus dolor vel est. Donec odio justo, sollicitudin ut, suscipit a, feugiat et, eros.', 1, 80178, '2022-07-27 11:01:18', 8, 8);
INSERT INTO public.recenzija VALUES (14, 'Cras mi pede, malesuada in, imperdiet et, commodo vulputate, justo. In blandit ultrices enim. Lorem ipsum dolor sit amet, consectetuer adipiscing elit.', 3, 103026, '2023-03-14 12:02:15', 1, 8);
INSERT INTO public.recenzija VALUES (15, 'Sed ante. Vivamus tortor. Duis mattis egestas metus.', 3, 100124, '2023-01-28 05:16:38', 9, 7);


--
-- Data for Name: tip_vozila; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tip_vozila VALUES ('KAM-C-1', 'Kamion', 'C');
INSERT INTO public.tip_vozila VALUES ('KAM-CE-1', 'Kamion (prikolica)', 'CE');
INSERT INTO public.tip_vozila VALUES ('KOMB-B-2', 'Kombi', 'B');
INSERT INTO public.tip_vozila VALUES ('MAUT-B-3', 'Mali automobil', 'B');
INSERT INTO public.tip_vozila VALUES ('AUT-B-4', 'Automobil', 'B');
INSERT INTO public.tip_vozila VALUES ('MOP-AM-1', 'Moped', 'AM');
INSERT INTO public.tip_vozila VALUES ('SUV-B-1', 'SUV', 'B');


--
-- Data for Name: ugovor; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.ugovor VALUES (2, 'CN98-94272-523-8', 'Vestibulum ac est lacinia nisi venenatis tristique. Fusce congue, diam id ornare imperdiet, sapien urna pretium nisl, ut volutpat sapien arcu sed augue. Aliquam erat volutpat.

In congue. Etiam justo. Etiam pretium iaculis justo.

In hac habitasse platea dictumst. Etiam faucibus cursus urna. Ut tellus.', 'Donec diam neque, vestibulum eget, vulputate ut, ultrices vel, augue.', NULL, NULL, 2424.21, NULL);
INSERT INTO public.ugovor VALUES (3, 'CM58-69440-365-0', 'Praesent id massa id nisl venenatis lacinia. Aenean sit amet justo. Morbi ut odio.', 'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae.', NULL, NULL, 9651.52, NULL);
INSERT INTO public.ugovor VALUES (4, 'CT58-52150-811-3', 'In congue. Etiam justo. Etiam pretium iaculis justo.', 'Nulla dapibus dolor vel est. Donec odio justo, sollicitudin ut, suscipit a, feugiat et, eros.', NULL, NULL, 6233.01, NULL);
INSERT INTO public.ugovor VALUES (5, 'CP11-72876-559-3', 'Morbi porttitor lorem id ligula. Suspendisse ornare consequat lectus. In est risus, auctor sed, tristique in, tempus sit amet, sem.

Fusce consequat. Nulla nisl. Nunc nisl.

Duis bibendum, felis sed interdum venenatis, turpis enim blandit mi, in porttitor pede justo eu massa. Donec dapibus. Duis at velit eu est congue elementum.', 'Duis aliquam convallis nunc. Proin at turpis a pede posuere nonummy. Integer non velit.', NULL, NULL, 1034.21, NULL);
INSERT INTO public.ugovor VALUES (6, 'CN30-22883-772-7', 'Quisque id justo sit amet sapien dignissim vestibulum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nulla dapibus dolor vel est. Donec odio justo, sollicitudin ut, suscipit a, feugiat et, eros.

Vestibulum ac est lacinia nisi venenatis tristique. Fusce congue, diam id ornare imperdiet, sapien urna pretium nisl, ut volutpat sapien arcu sed augue. Aliquam erat volutpat.', 'Aliquam sit amet diam in magna bibendum imperdiet. Nullam orci pede, venenatis non, sodales sed, tincidunt eu, felis.', '/ugovori/potpisi/6', '2022-12-06 22:34:04', 4910.01, NULL);
INSERT INTO public.ugovor VALUES (7, 'CW19-96758-455-5', 'Curabitur in libero ut massa volutpat convallis. Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo. Maecenas pulvinar lobortis est.

Phasellus sit amet erat. Nulla tempus. Vivamus in felis eu sapien cursus vestibulum.', 'Maecenas tristique, est et tempus semper, est quam pharetra magna, ac consequat metus sapien ut nunc.', NULL, NULL, 9443.19, NULL);
INSERT INTO public.ugovor VALUES (8, 'CL90-46835-388-9', 'Maecenas ut massa quis augue luctus tincidunt. Nulla mollis molestie lorem. Quisque ut erat.', 'Maecenas leo odio, condimentum id, luctus nec, molestie sed, justo. Pellentesque viverra pede ac diam.', '/ugovori/potpisi/8', '2023-03-16 21:16:01', 7180.85, NULL);
INSERT INTO public.ugovor VALUES (9, 'CU82-56406-848-0', 'Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis. Integer aliquet, massa id lobortis convallis, tortor risus dapibus augue, vel accumsan tellus nisi eu orci. Mauris lacinia sapien quis libero.

Nullam sit amet turpis elementum ligula vehicula consequat. Morbi a ipsum. Integer a nibh.', 'Aliquam erat volutpat.', NULL, NULL, 7119.34, NULL);
INSERT INTO public.ugovor VALUES (10, 'CB95-41702-845-4', 'Fusce posuere felis sed lacus. Morbi sem mauris, laoreet ut, rhoncus aliquet, pulvinar sed, nisl. Nunc rhoncus dui vel sem.

Sed sagittis. Nam congue, risus semper porta volutpat, quam pede lobortis ligula, sit amet eleifend pede libero quis orci. Nullam molestie nibh in lectus.

Pellentesque at nulla. Suspendisse potenti. Cras in purus eu magna vulputate luctus.', 'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Mauris viverra diam vitae quam.', '/ugovori/potpisi/10', '2022-06-10 07:22:54', 1056.46, NULL);
INSERT INTO public.ugovor VALUES (11, 'CO14-38430-949-4', 'Maecenas tristique, est et tempus semper, est quam pharetra magna, ac consequat metus sapien ut nunc. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Mauris viverra diam vitae quam. Suspendisse potenti.', 'Etiam vel augue. Vestibulum rutrum rutrum neque.', '/ugovori/potpisi/11', '2022-08-14 13:52:52', 3695.27, NULL);
INSERT INTO public.ugovor VALUES (13, 'CP73-24686-536-7', 'Sed ante. Vivamus tortor. Duis mattis egestas metus.

Aenean fermentum. Donec ut mauris eget massa tempor convallis. Nulla neque libero, convallis eget, eleifend luctus, ultricies eu, nibh.

Quisque id justo sit amet sapien dignissim vestibulum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nulla dapibus dolor vel est. Donec odio justo, sollicitudin ut, suscipit a, feugiat et, eros.', 'Maecenas ut massa quis augue luctus tincidunt. Nulla mollis molestie lorem. Quisque ut erat.', '/ugovori/potpisi/13', '2022-11-12 06:34:16', 6448.50, NULL);
INSERT INTO public.ugovor VALUES (14, 'CF01-95703-395-0', 'Duis aliquam convallis nunc. Proin at turpis a pede posuere nonummy. Integer non velit.', 'Integer tincidunt ante vel ipsum. Praesent blandit lacinia erat. Vestibulum sed magna at nunc commodo placerat.', NULL, '2023-03-30 03:29:30', 3321.27, NULL);
INSERT INTO public.ugovor VALUES (15, 'CD02-15639-341-5', 'Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vivamus vestibulum sagittis sapien. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.', 'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae.', NULL, '2023-03-13 17:29:48', 2530.20, NULL);
INSERT INTO public.ugovor VALUES (1, 'CR88-13060-887-4', 'Praesent id massa id nisl venenatis lacinia. Aenean sit amet justo. Morbi ut odio.

Cras mi pede, malesuada in, imperdiet et, commodo vulputate, justo. In blandit ultrices enim. Lorem ipsum dolor sit amet, consectetuer adipiscing elit.', 'Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis.', NULL, NULL, 5900.00, NULL);
INSERT INTO public.ugovor VALUES (12, 'CE84-66044-038-1', 'Integer tincidunt ante vel ipsum. Praesent blandit lacinia erat. Vestibulum sed magna at nunc commodo placerat.

Praesent blandit. Nam nulla. Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede.

Morbi porttitor lorem id ligula. Suspendisse ornare consequat lectus. In est risus, auctor sed, tristique in, tempus sit amet, sem.', 'Nulla ac enim. In tempor, turpis nec euismod scelerisque, quam turpis adipiscing lorem, vitae mattis nibh ligula nec sem.', NULL, NULL, 9748.02, 16);
INSERT INTO public.ugovor VALUES (19, 'NVUG1-222', 'Novi ugovor', 'Novi ugovorcek', './static/static-content/images/private/signatures/signature_19_1.png', '2023-05-21 19:42:09.788182', 1724.00, 34);
INSERT INTO public.ugovor VALUES (18, 'sfasdasfsa', 'dfsadfa', 'sadfsaf', './static/static-content/images/private/signatures/signature_18_1.png', '2023-05-21 20:43:27.795479', 37072.00, 33);


--
-- Data for Name: unajmitelj; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.unajmitelj VALUES (2, 'Nonie', 'Whisby', '60887600143', 'nwhisby1@howstuffworks.com', 'bx3nn4A6', '0948551484');
INSERT INTO public.unajmitelj VALUES (3, 'Nikolaos', 'O''Griffin', '83466288793', 'nogriffin2@ox.ac.uk', 'iBVwn1gvfDX6', '0977424948');
INSERT INTO public.unajmitelj VALUES (4, 'Josefa', 'Treppas', '78232476985', 'jtreppas3@tuttocitta.it', 'MN21w4J', '0914090006');
INSERT INTO public.unajmitelj VALUES (5, 'Meier', 'Copcutt', '67214083015', 'mcopcutt4@wisc.edu', 'YgEOXKyv', '0907914987');
INSERT INTO public.unajmitelj VALUES (6, 'Trumaine', 'Schneidau', '87613903389', 'tschneidau5@webs.com', 'ltSNFSO3I', '0943139756');
INSERT INTO public.unajmitelj VALUES (7, 'Derk', 'Sarle', '44666980970', 'dsarle6@rambler.ru', '8F0ItfdJ', '0958993518');
INSERT INTO public.unajmitelj VALUES (8, 'Pearce', 'Singyard', '30707875923', 'psingyard7@google.com.hk', 'ZV4K443rzu', '0923854910');
INSERT INTO public.unajmitelj VALUES (9, 'Eda', 'Kobiera', '17961114764', 'ekobiera8@1688.com', 'eQgt2SPwya5', '0980362515');
INSERT INTO public.unajmitelj VALUES (10, 'Kliment', 'Leathers', '25967999624', 'kleathers9@t-online.de', 'tyHetnuUuVN', '0992951686');
INSERT INTO public.unajmitelj VALUES (1, 'Rose', 'Roset', '39449920198', 'rroset0@hp.com', '$2a$08$JE4pWQ.cuaqYbP0tNM5NU.hMCIKo94MWiYApYYF77sz.O0XqqihlS', '0922615023');


--
-- Data for Name: upravitelj_poslovnice; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.upravitelj_poslovnice VALUES (1, 'lsallis0', '36xL5V3I', '2022-08-10', NULL);
INSERT INTO public.upravitelj_poslovnice VALUES (2, 'bjessep1', '$2a$08$eAHx8oxKWY7sIVCxnXOj8eoOLOtM3PixxtFAfjlbavkz5GHYcljn2', '2022-07-15', NULL);
INSERT INTO public.upravitelj_poslovnice VALUES (3, 'vingliss2', 'JaJk4H9Ws7', '2022-06-15', NULL);
INSERT INTO public.upravitelj_poslovnice VALUES (5, 'hkelwaybamber3', 'Yyho3L3', '2022-09-16', NULL);
INSERT INTO public.upravitelj_poslovnice VALUES (6, 'jkenway4', 'X33Pgkul0y', '2022-08-25', NULL);
INSERT INTO public.upravitelj_poslovnice VALUES (7, 'gkerrich5', 'e2YIkW', '2022-07-01', NULL);


--
-- Data for Name: vozilo; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.vozilo VALUES (3, 'OS-6117-MF', 'Citro├½n C-Elysee', 'Citro├½n', 125365, 1122.00, NULL, 'AUT-B-4', 3);
INSERT INTO public.vozilo VALUES (5, 'ZG-4788-ZN', 'Volkswagen Caddy', 'Volkswagen', 95118, 602.00, NULL, 'KOMB-B-2', NULL);
INSERT INTO public.vozilo VALUES (6, 'ZG-6158-FR', 'Volkswagen Golf MK.VII', 'Volkswagen', 125225, 1774.00, NULL, 'MAUT-B-3', NULL);
INSERT INTO public.vozilo VALUES (8, 'ZG-2099-FE', 'Volvo FM7', 'Volvo', 133916, 1230.00, NULL, 'KAM-CE-1', 2);
INSERT INTO public.vozilo VALUES (1, 'ZG-0022-KM', 'Mercedes Atego II', 'Mercedes-Benz', 108524, 1715.00, 'https://www.njuskalo.hr/image-w920x690/vucne-sluzba/mercedes-benz-atego-2-1322-car-tp-crane-hydroplate-winch-slika-173725575.jpg', 'KAM-C-1', 1);
INSERT INTO public.vozilo VALUES (2, 'ZG-5824-VL', 'Ford Focus', 'Ford', 60733, 2180.00, './static/static-content/images/vehicles/vehicle_Mali automobil2.jpeg', 'MAUT-B-3', 1);
INSERT INTO public.vozilo VALUES (4, 'OS-7454-KP', 'Peugeot 307', 'Peugeot', 104752, 2031.00, './static/static-content/images/vehicles/vehicle_Kamion (prikolica)4.jpeg', 'KAM-CE-1', 3);
INSERT INTO public.vozilo VALUES (7, 'ZG-1102-AX', 'Mercedes C', 'Mercedes-Benz', 69854, 2347.00, 'https://azneostarplatformprod.blob.core.windows.net/images-360/16943_129.jpg', 'MAUT-B-3', 2);


--
-- Data for Name: zahtjev_za_najmom; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.zahtjev_za_najmom VALUES (1, 'ODBIJEN', '2022-10-14 13:23:49', true, '2022-10-14 15:28:49', 17, 9);
INSERT INTO public.zahtjev_za_najmom VALUES (2, 'ODBIJEN', '2022-06-17 18:58:51', true, '2022-06-18 08:58:51', 20, 2);
INSERT INTO public.zahtjev_za_najmom VALUES (3, 'ODBIJEN', '2022-05-24 08:33:27', true, '2022-05-24 08:33:27', 11, 3);
INSERT INTO public.zahtjev_za_najmom VALUES (5, 'ODBIJEN', '2022-12-14 18:20:14', true, '2022-07-26 04:15:40', 14, 6);
INSERT INTO public.zahtjev_za_najmom VALUES (6, 'ODBIJEN', '2022-08-02 21:50:02', true, '2023-03-09 22:42:44', 5, 9);
INSERT INTO public.zahtjev_za_najmom VALUES (8, 'POTVRDEN', '2023-02-20 07:56:09', true, '2022-08-06 21:13:11', 5, 5);
INSERT INTO public.zahtjev_za_najmom VALUES (9, 'POTVRDEN', '2023-01-14 05:53:50', true, '2022-11-22 06:16:05', 7, 3);
INSERT INTO public.zahtjev_za_najmom VALUES (11, 'ODBIJEN', '2023-01-05 15:34:30', true, '2022-06-19 07:07:42', 11, 1);
INSERT INTO public.zahtjev_za_najmom VALUES (12, 'POTVRDEN', '2022-07-23 04:40:59', true, '2022-12-19 09:19:23', 11, 3);
INSERT INTO public.zahtjev_za_najmom VALUES (13, 'ODBIJEN', '2022-06-08 01:33:20', true, '2022-12-27 22:09:16', 18, 4);
INSERT INTO public.zahtjev_za_najmom VALUES (14, 'ODBIJEN', '2022-09-09 08:22:01', true, '2022-10-13 01:49:13', 13, 8);
INSERT INTO public.zahtjev_za_najmom VALUES (15, 'ODBIJEN', '2023-03-26 18:47:53', true, '2022-04-14 06:54:03', 11, 5);
INSERT INTO public.zahtjev_za_najmom VALUES (16, 'POTVRDEN', '2023-02-19 03:31:11', true, '2023-02-01 14:28:37', 7, 3);
INSERT INTO public.zahtjev_za_najmom VALUES (17, 'POTVRDEN', '2022-12-25 20:44:15', true, '2022-12-22 09:16:30', 2, 8);
INSERT INTO public.zahtjev_za_najmom VALUES (18, 'POTVRDEN', '2023-01-21 12:12:22', true, '2022-06-01 11:42:50', 16, 10);
INSERT INTO public.zahtjev_za_najmom VALUES (19, 'ODBIJEN', '2022-11-08 20:35:58', true, '2022-11-07 14:50:06', 16, 1);
INSERT INTO public.zahtjev_za_najmom VALUES (21, 'ODBIJEN', '2023-01-30 04:28:59', true, '2022-10-18 01:32:32', 16, 8);
INSERT INTO public.zahtjev_za_najmom VALUES (22, 'ODBIJEN', '2023-03-30 11:12:13', true, '2022-12-04 02:52:21', 4, 10);
INSERT INTO public.zahtjev_za_najmom VALUES (23, 'ODBIJEN', '2022-04-26 00:52:45', true, '2022-10-21 06:23:35', 4, 5);
INSERT INTO public.zahtjev_za_najmom VALUES (24, 'ODBIJEN', '2022-12-15 01:24:50', true, '2022-11-01 03:03:48', 13, 1);
INSERT INTO public.zahtjev_za_najmom VALUES (25, 'ODBIJEN', '2023-01-11 14:57:05', true, '2023-01-12 10:48:46', 8, 5);
INSERT INTO public.zahtjev_za_najmom VALUES (26, 'ODBIJEN', '2022-09-14 22:58:54', true, '2022-07-18 19:38:00', 11, 5);
INSERT INTO public.zahtjev_za_najmom VALUES (27, 'ODBIJEN', '2022-11-21 06:58:38', true, '2022-09-13 04:53:43', 9, 7);
INSERT INTO public.zahtjev_za_najmom VALUES (28, 'ODBIJEN', '2022-09-28 22:38:07', true, '2022-10-29 09:50:52', 13, 9);
INSERT INTO public.zahtjev_za_najmom VALUES (29, 'POTVRDEN', '2022-12-06 04:48:07', true, '2023-02-07 20:32:42', 13, 9);
INSERT INTO public.zahtjev_za_najmom VALUES (30, 'NA CEKANJU', '2022-05-28 04:34:57', false, NULL, NULL, 1);
INSERT INTO public.zahtjev_za_najmom VALUES (32, 'NA CEKANJU', '2023-05-20 22:09:20.559', false, NULL, NULL, 1);
INSERT INTO public.zahtjev_za_najmom VALUES (31, 'POTVRDEN', '2023-05-21 00:06:01.514', false, NULL, NULL, 1);
INSERT INTO public.zahtjev_za_najmom VALUES (33, 'POTVRDEN', '2023-05-21 00:41:08.052', false, NULL, NULL, 1);
INSERT INTO public.zahtjev_za_najmom VALUES (34, 'POTVRDEN', '2023-05-21 21:33:50.496', false, NULL, NULL, 1);


--
-- Data for Name: zaposlenik; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.zaposlenik VALUES (1, '03562536048', 'Shepperd', 'Ticehurst', 3);
INSERT INTO public.zaposlenik VALUES (2, '04456090183', 'Dorie', 'Haslum', 1);
INSERT INTO public.zaposlenik VALUES (3, '90690344012', 'Corny', 'Severs', 3);
INSERT INTO public.zaposlenik VALUES (4, '69451823260', 'Royal', 'Shama', 3);
INSERT INTO public.zaposlenik VALUES (5, '16415553411', 'Richard', 'Ashforth', 2);
INSERT INTO public.zaposlenik VALUES (6, '78204344044', 'Bryn', 'Rounsefell', 2);
INSERT INTO public.zaposlenik VALUES (7, '10336802152', 'Doria', 'Stuchberry', 1);
INSERT INTO public.zaposlenik VALUES (8, '46652754304', 'Josiah', 'Leicester', 1);
INSERT INTO public.zaposlenik VALUES (9, '74165609758', 'Vic', 'Discombe', 2);
INSERT INTO public.zaposlenik VALUES (10, '03093351420', 'Jesus', 'Maudlin', 3);
INSERT INTO public.zaposlenik VALUES (11, '57009908198', 'Lilyan', 'Colwell', 2);
INSERT INTO public.zaposlenik VALUES (12, '81237778891', 'Paule', 'Widdison', 2);
INSERT INTO public.zaposlenik VALUES (13, '20915047771', 'Mitzi', 'Lochran', 2);
INSERT INTO public.zaposlenik VALUES (14, '47983474125', 'Marcellus', 'Nicolls', 2);
INSERT INTO public.zaposlenik VALUES (15, '81897111774', 'Renae', 'Langmead', 2);
INSERT INTO public.zaposlenik VALUES (16, '08431730515', 'Trevor', 'Callendar', 3);
INSERT INTO public.zaposlenik VALUES (17, '82922435611', 'Lyndsay', 'de Courcy', 1);
INSERT INTO public.zaposlenik VALUES (18, '08166655904', 'Ly', 'Marlen', 1);
INSERT INTO public.zaposlenik VALUES (19, '17660995633', 'Andria', 'Tollemache', 3);
INSERT INTO public.zaposlenik VALUES (20, '26921835166', 'Gardner', 'Kiossel', 2);
INSERT INTO public.zaposlenik VALUES (21, '11874406542', 'Perica', 'Perici─ç', 2);


--
-- Name: lokacija_idlokacija_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.lokacija_idlokacija_seq', 3, true);


--
-- Name: najam_idnajam_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.najam_idnajam_seq', 19, true);


--
-- Name: obavijest_idobavijest_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.obavijest_idobavijest_seq', 15, true);


--
-- Name: potrazuje_idpotraznja_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.potrazuje_idpotraznja_seq', 28, true);


--
-- Name: recenzija_idrecenzija_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.recenzija_idrecenzija_seq', 15, true);


--
-- Name: ugovor_idugovor_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.ugovor_idugovor_seq', 19, true);


--
-- Name: unajmitelj_idunajmitelj_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.unajmitelj_idunajmitelj_seq', 10, true);


--
-- Name: vozilo_idvozilo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.vozilo_idvozilo_seq', 8, true);


--
-- Name: zahtjev_za_najmom_idzahtjev_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.zahtjev_za_najmom_idzahtjev_seq', 34, true);


--
-- Name: zaposlenik_idzaposlenik_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.zaposlenik_idzaposlenik_seq', 21, true);


--
-- Name: izdavanje izdavanje_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.izdavanje
    ADD CONSTRAINT izdavanje_pkey PRIMARY KEY (idunajmitelj, idugovor);


--
-- Name: iznajmljivac iznajmljivac_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.iznajmljivac
    ADD CONSTRAINT iznajmljivac_pkey PRIMARY KEY (idzaposlenik);


--
-- Name: lokacija lokacija_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lokacija
    ADD CONSTRAINT lokacija_pkey PRIMARY KEY (idlokacija);


--
-- Name: najam najam_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.najam
    ADD CONSTRAINT najam_pkey PRIMARY KEY (idnajam);


--
-- Name: obavijest obavijest_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.obavijest
    ADD CONSTRAINT obavijest_pkey PRIMARY KEY (idobavijest, idnajam);


--
-- Name: potrazuje pot_uq_zn_vz; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.potrazuje
    ADD CONSTRAINT pot_uq_zn_vz UNIQUE (idzahtjev, idvozilo);


--
-- Name: potrazuje potrazuje_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.potrazuje
    ADD CONSTRAINT potrazuje_pkey PRIMARY KEY (idpotraznja);


--
-- Name: recenzija recenzija_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recenzija
    ADD CONSTRAINT recenzija_pkey PRIMARY KEY (idrecenzija);


--
-- Name: tip_vozila tip_vozila_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tip_vozila
    ADD CONSTRAINT tip_vozila_pkey PRIMARY KEY (idtip);


--
-- Name: ugovor ug_uq_oznug; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ugovor
    ADD CONSTRAINT ug_uq_oznug UNIQUE (oznugovor);


--
-- Name: ugovor ugovor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ugovor
    ADD CONSTRAINT ugovor_pkey PRIMARY KEY (idugovor);


--
-- Name: unajmitelj un_uq_eml; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unajmitelj
    ADD CONSTRAINT un_uq_eml UNIQUE (email);


--
-- Name: unajmitelj un_uq_oib; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unajmitelj
    ADD CONSTRAINT un_uq_oib UNIQUE (oib);


--
-- Name: unajmitelj unajmitelj_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unajmitelj
    ADD CONSTRAINT unajmitelj_pkey PRIMARY KEY (idunajmitelj);


--
-- Name: upravitelj_poslovnice upravitelj_poslovnice_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.upravitelj_poslovnice
    ADD CONSTRAINT upravitelj_poslovnice_pkey PRIMARY KEY (idzaposlenik);


--
-- Name: vozilo voz_uq_reg; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vozilo
    ADD CONSTRAINT voz_uq_reg UNIQUE (registracija);


--
-- Name: vozilo vozilo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vozilo
    ADD CONSTRAINT vozilo_pkey PRIMARY KEY (idvozilo);


--
-- Name: zahtjev_za_najmom zahtjev_za_najmom_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.zahtjev_za_najmom
    ADD CONSTRAINT zahtjev_za_najmom_pkey PRIMARY KEY (idzahtjev);


--
-- Name: zaposlenik zap_uq_oib; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.zaposlenik
    ADD CONSTRAINT zap_uq_oib UNIQUE (oib);


--
-- Name: zaposlenik zaposlenik_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.zaposlenik
    ADD CONSTRAINT zaposlenik_pkey PRIMARY KEY (idzaposlenik);


--
-- Name: izdavanje izdavanje_idiznajmljivac_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.izdavanje
    ADD CONSTRAINT izdavanje_idiznajmljivac_fkey FOREIGN KEY (idiznajmljivac) REFERENCES public.iznajmljivac(idzaposlenik);


--
-- Name: izdavanje izdavanje_idugovor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.izdavanje
    ADD CONSTRAINT izdavanje_idugovor_fkey FOREIGN KEY (idugovor) REFERENCES public.ugovor(idugovor) ON DELETE CASCADE;


--
-- Name: izdavanje izdavanje_idunajmitelj_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.izdavanje
    ADD CONSTRAINT izdavanje_idunajmitelj_fkey FOREIGN KEY (idunajmitelj) REFERENCES public.unajmitelj(idunajmitelj);


--
-- Name: iznajmljivac iznajmljivac_idzaposlenik_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.iznajmljivac
    ADD CONSTRAINT iznajmljivac_idzaposlenik_fkey FOREIGN KEY (idzaposlenik) REFERENCES public.zaposlenik(idzaposlenik);


--
-- Name: najam najam_idugovor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.najam
    ADD CONSTRAINT najam_idugovor_fkey FOREIGN KEY (idugovor) REFERENCES public.ugovor(idugovor) ON DELETE CASCADE;


--
-- Name: najam najam_idvozilo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.najam
    ADD CONSTRAINT najam_idvozilo_fkey FOREIGN KEY (idvozilo) REFERENCES public.vozilo(idvozilo) ON DELETE CASCADE;


--
-- Name: obavijest obavijest_idnajam_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.obavijest
    ADD CONSTRAINT obavijest_idnajam_fkey FOREIGN KEY (idnajam) REFERENCES public.najam(idnajam);


--
-- Name: potrazuje potrazuje_idvozilo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.potrazuje
    ADD CONSTRAINT potrazuje_idvozilo_fkey FOREIGN KEY (idvozilo) REFERENCES public.vozilo(idvozilo) ON DELETE CASCADE;


--
-- Name: potrazuje potrazuje_idzahtjev_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.potrazuje
    ADD CONSTRAINT potrazuje_idzahtjev_fkey FOREIGN KEY (idzahtjev) REFERENCES public.zahtjev_za_najmom(idzahtjev);


--
-- Name: recenzija recenzija_idunajmitelj_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recenzija
    ADD CONSTRAINT recenzija_idunajmitelj_fkey FOREIGN KEY (idunajmitelj) REFERENCES public.unajmitelj(idunajmitelj);


--
-- Name: recenzija recenzija_idvozilo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recenzija
    ADD CONSTRAINT recenzija_idvozilo_fkey FOREIGN KEY (idvozilo) REFERENCES public.vozilo(idvozilo) ON DELETE CASCADE;


--
-- Name: ugovor ugovor_idzahtjev_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ugovor
    ADD CONSTRAINT ugovor_idzahtjev_fkey FOREIGN KEY (idzahtjev) REFERENCES public.zahtjev_za_najmom(idzahtjev);


--
-- Name: upravitelj_poslovnice upravitelj_poslovnice_idzaposlenik_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.upravitelj_poslovnice
    ADD CONSTRAINT upravitelj_poslovnice_idzaposlenik_fkey FOREIGN KEY (idzaposlenik) REFERENCES public.zaposlenik(idzaposlenik);


--
-- Name: vozilo vozilo_idlokacija_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vozilo
    ADD CONSTRAINT vozilo_idlokacija_fkey FOREIGN KEY (idlokacija) REFERENCES public.lokacija(idlokacija);


--
-- Name: vozilo vozilo_idtip_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vozilo
    ADD CONSTRAINT vozilo_idtip_fkey FOREIGN KEY (idtip) REFERENCES public.tip_vozila(idtip) ON UPDATE CASCADE;


--
-- Name: zahtjev_za_najmom zahtjev_za_najmom_idiznajmljivac_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.zahtjev_za_najmom
    ADD CONSTRAINT zahtjev_za_najmom_idiznajmljivac_fkey FOREIGN KEY (idiznajmljivac) REFERENCES public.iznajmljivac(idzaposlenik);


--
-- Name: zahtjev_za_najmom zahtjev_za_najmom_idunajmitelj_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.zahtjev_za_najmom
    ADD CONSTRAINT zahtjev_za_najmom_idunajmitelj_fkey FOREIGN KEY (idunajmitelj) REFERENCES public.unajmitelj(idunajmitelj);


--
-- Name: zaposlenik zaposlenik_idlokacija_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.zaposlenik
    ADD CONSTRAINT zaposlenik_idlokacija_fkey FOREIGN KEY (idlokacija) REFERENCES public.lokacija(idlokacija);


--
-- PostgreSQL database dump complete
--

