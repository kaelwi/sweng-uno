# uno

## Run program from command line:
(Prerequisites: installed Java; tested with JDK-11 and JDK-13)
- Download project
- if no out folder is to be found:
    - create folder out
    - in folder src execute following command in cmd: javac -d ../out *.java (-d sets the destination directory for class files, the destination directory must already exist, https://stackoverflow.com/questions/36682472/how-to-set-the-output-of-a-javac-compiled-file/36682516)
- if there are already class files (e.g., like in our intellij project structure in directory out/production/uno), or you just compiled it with the above command:
    - navigate to the output folder containing the class files
    - needed command in cmd: java Main

## Found errors:

- [ ] User name allows empty string 

## Roadmap:

### 15.4.
- [x] Erzeugen Kartendeck
- [x] Shuffle, Austeilen
- [x] 4 fixe menschliche Spieler

### 26.4.
- [x] Spielen noch ohne Gültigkeitscheck funktioniert
- [x] Karten können ohne Beachtung der Regeln gespielt werden
- [ ] Einlesen des Kommandos

### 11.5.
- [ ] Karten können nur nach Regeln gespielt werden
- [ ] Es fehlt noch: Strafen, Heben, Uno, Datenbank

### 1.7.
- [ ] Heben, +2, Reverse, Stopp usw. funktionieren und die Effekte werden angewendet

### 5.7.
- [ ] Alle Aktionskarten funktionierne nun und die Effekte werden angewendet (+4, Farbwechsel)
- [ ] Auswahl Anzahl menschlicher Spieler und Botspieler sind vorhanden

### 6.7.
- [ ] Strafen für Uno, +4 usw. sind vorhanden, entsprechende Kommandos wurden implementiert

### 20.7.
- [ ] Punktezählung und Datenbank sind vorhanden
- [ ] Kommandos für Hilfe, Punkteabfrage

## Dokumentation
https://de.wikipedia.org/wiki/Softwaredokumentation
https://de.wikipedia.org/wiki/Softwaredokumentation#Programmiererdokumentation
- [ ] Programmierdokumentation 
- Beschreibung des Quellcodes
- Namen von Variablen und Funktionen sollen für Menschen intuitiv verständlich sein
- Die Dokumentation soll so weit wie möglich in den Quellcode eingearbeitet werden (Kommentare; was bereits durch die formale Programmiersprache selbst erklärt wurde, darf nicht Inhalt eines zusätzlichen Kommentars sein)
- Kommentar-Header Bsp. für Klassen und Methoden: https://de.wikipedia.org/wiki/Javadoc
- Doxygen?

- [ ] Installationsdokumentation
- siehe oben (Programm in Kommandozeile ausführen)

- [ ] Testdokumentation
- gefundene Bugs mit Datum (bzw. Version) versehen
- aus Wikipedia: Nachweis von Testfällen, mit denen die ordnungsgemäße Funktion jeder Version des Produkts getestet werden können, sowie Verfahren und Szenarien, mit denen in der Vergangenheit erfolgreich die Richtigkeit überprüft wurde

- [ ] Benutzerdokumentation
- Regeln?
- Hilfe?

https://www.altexsoft.com/blog/business/technical-documentation-in-software-development-types-best-practices-and-tools/

