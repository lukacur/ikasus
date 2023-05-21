# Upute za pokretanje - backend

### Potrebno je imati instalirano:
  - Java 17
  - PostgreSQL server (14+)

### Default očekivanja:
  - baza podataka s imenom 'ikadb' na localhost:5433:
    - connection string jdbc:postgresql://localhost:5433/ikadb
    - korisnik 'postgres'
    - lozinka 'bazepodataka'

### Default postavke:
  - omogućen CORS pristup s:
    - http://localhost:3000
    - http://localhost:5500
    - http://127.0.0.1:3000
    - http://127.0.0.1:5500
    - http://localhost:4200
    - http://127.0.0.1:4200
    - http://10.129.0.243:4200
  - JWT ključ za potpisivanje
<hr>

## 1. Inicijalizacija baze podataka
Baza podataka se može inicijalizirati koristeći tri metode:
1. obnoviti iz backupa baze
    - otvoriti terminal
    - pokrenuti naredbu:
      
      `psql --username postgres -d ikadb < ika_dump.sql`
    - upisati lozinku
    - pričekati da alat završi s obnovom

2. inicijalizirati bez testnih podataka
    - stvoriti novu bazu naziva 'ikadb'
    - izvesti skriptu 'create_script.sql'
    - ili preko terminala:

      `psql --username postgres -d ikadb < create_script.sql`

3. inicijalizirati s testnim podatcima
    - provesti prethodni korak
    - izvesti skriptu 'fill_script.sql'
    - ili preko terminala:
      
      `psql --username postgres -d ikadb < fill_script.sql`

<br>

## 2. Konfiguracija poslužitelja
 - prije prevođenja pokretanja potrebno je konfigurirati poslužitelj
 - ovo je moguće kroz .properties datoteke koje se nalaze u 'src/main/resources'
 - ako se aplikacija treba spojiti na neku drugu bazu podataka (ili drugi
   poslužitelj) potrebno je izmijeniti ključeve u datoteci application.properties:
   - spring.datasource.url - "connection string" za bazu podataka
     - postaviti na oblik jdbc:postgresql://&lt;host&gt;:&lt;port&gt;/&lt;naziv_baze_podataka&gt;

<br>

 - ako je neka druga baza podataka osim PostgreSQL baze podataka u pitanju, tada
   je potrebno u 'pom.xml' dodati upravljač (driver) za tu bazu podataka i
   izmijeniti da ključ spring.jpa.properties.hibernate.dialect u datoteci
   application.properties pokazuje na novi driver

<br>

 - ako je potrebno koristiti drugog korisnika za spajanje na bazu podataka,
   potrebno je izmijeniti ključeve u datoteci application.properties:
   - 'spring.datasource.username' - korisničko ime
   - 'spring.datasource.password' - lozinka
   
<br>

 - ako se poslužitelj prezentacijskog sloja (Angular aplikacija) pokreće na
   vratima različitim od 4200, potrebno je taj poslužitelj dodati u
   application-dev.properties pod ključ 'ikasus.config.cors.origins.allow'
   tako da se od ostalih zapisa odvoji s ';'
 - ostale postavke se ne smiju dirati (inače poslužitelj možda neće raditi
   ispravno)

<br>

## 3. Pokretanje definiranih testova
 - pokrenuti skriptu 'test.bat' (Windows) ili 'test.sh' (Linux/Unix)
 - napomena: integracijski testovi će mijenjati bazu podataka na koju je
             poslužitelj spojen pa bi prije pokretanja testova bilo dobro
             promijeniti bazu podataka

<br>

## 4. Pokretanje aplikacijskog web poslužitelja
 - pozicionirati se u direktorij u kojem se nalazi izvorni kod poslužitelja
 - pokrenuti skriptu 'run.bat' (Windows) ili 'run.sh' (Linux/Unix)
 - nakon prevođenja i pokretanja poslužitelj će biti pokrenut na vratima 8080

<hr>
