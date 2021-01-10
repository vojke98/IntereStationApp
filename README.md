# IntereStation

Člana ekipe:

63180387 Dejan Vojinovic
63180357 Dino Celikovic

Z informacijskim sistemom IntereStation smo naredili enostavno verzijo družbenega omrežja, v katerem registrirani uporabniki lahko objavljajo karkoli znotraj njihovega interesnega področja, in tudi lahko berejo objave ki so jih objavili drugi uporabniki ki so tudi v njihovem območju interesa. Družbeno omrežje je nekaj podobnega kot so danes Reddit in Pinterest oz. neki hibridni tip teh dveh omrežij. Sistem omogoča prijave uporabnikov kot tudi registracijo novih. Uporabniki imajo možnost izbire interesov, in s tem tudi objav ki se jim prikazujejo na glavni strani. Znotraj objave uporabnik lahko doda tako kot besedilo tudi fotografije. Za vsako objavo obstaja tudi rating sistem, kjer lahko ostali uporabniki ocenijo njeno vsebino.

Zaslonske slike grafičnega vmesnika:
<br><br>
![](images/WebApp1.png) <br>
Slika 1 Začetna stran (Web App)
<br><br><br>
![](images/WebApp2.png) <br>
Slika 2 Iskanje uporabnikov (Web App)
<br><br><br>
![](images/WebApp3.png) <br>
Slika 3 Prošnje za prijateljstvo (Web App)
<br><br><br>
![](images/WebApp4.png) <br>
Slika 4 Delitev objave (Web App)
<br><br><br>
![](images/AndroidClient5.png) <br>
Slika 5 Začetna stran (Android Client) - Like
<br><br><br><br>
Podatkovni model:
<br><br>
![](images/DbDiagram.png) <br>


Mobilna aplikacija se navezuje na web API spletne aplikacije ki smo ga izdelali za Interests, Posts, Likes in Users, ki je tudi dokumentiran v Swaggerju. Kot GET zahtevek smo naredili da aplikacija prikazuje vse objave na glavni strani web aplikacije, skupaj z imenom lastnika objave, številom like-ov ter datumom objave. Objava je lahko tekstovna ali slikovna. POST zahtevek je pa dodajanje like-ov na posamezno objavo.

