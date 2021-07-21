# UNO

Gruppenarbeit für Projekt I+II.<br>
52 Detail Anforderungen.
UNO mit Datenbank Einbindung programmiert in Java.<br>
Aufgaben: Programmieren, Testen, Dokumentieren.<br>
<br>

Struktur:
- Main (startet App)
- App (initialisiert das Game, beinhaltet eine Game Loop, read User Input, Update State and Check Winner)
- Game (Game-Logik und Aufsetzen des Spiels)
- Card (selbsterklärend - eine Klasse für die Karten-Objekte)
- Deck (für Ziehstapel und Ablegestapel)
- Player (abstrakte Klasse)
- Human und Bot (abgeleitete Klassen von Player)
- Printer (weil zu viele Ausgaben die anderen Klassen zu unübersichtlich gemacht haben...)
- DBManager für das Verbinden mit der Datenbank und die Hanhabung der SQL Statements)

Das Projekt beinhaltet weiters eine help.txt Datei, es wird im Root Verzeichnis des Projekts die Datenbank angelegt und es gibt einen Test-Folder (für Gruppe zum Kennenlernen, Tests nicht vollständig).

## Roadmap:

### 15.4.
- [x] Erzeugen Kartendeck
- [x] Shuffle, Austeilen
- [x] 4 fixe menschliche Spieler

### 26.4.
- [x] Spielen noch ohne Gültigkeitscheck funktioniert
- [x] Karten können ohne Beachtung der Regeln gespielt werden
- [x] Einlesen des Kommandos

### 11.5.
- [x] Karten können nur nach Regeln gespielt werden
- [x] Es fehlt noch: Strafen, Heben, Uno, Datenbank

### 1.7.
- [x] Heben, +2, Reverse, Stopp usw. funktionieren und die Effekte werden angewendet

### 5.7.
- [x] Alle Aktionskarten funktionierne nun und die Effekte werden angewendet (+4, Farbwechsel)
- [x] Auswahl Anzahl menschlicher Spieler und Botspieler sind vorhanden

### 6.7.
- [x] Strafen für Uno, +4 usw. sind vorhanden, entsprechende Kommandos wurden implementiert

### 20.7.
- [x] Punktezählung und Datenbank sind vorhanden
- [x] Kommandos für Hilfe, Punkteabfrage

## Dokumentation

### Run program from command line:
(Prerequisites: installed Java; tested with JDK-11 and JDK-13)
- Download project
- if no out folder is to be found:
    - create folder out
    - in folder src execute following command in cmd: javac -d ../out *.java (-d sets the destination directory for class files, the destination directory must already exist, https://stackoverflow.com/questions/36682472/how-to-set-the-output-of-a-javac-compiled-file/36682516)
- if there are already class files (e.g., like in our intellij project structure in directory out/production/uno), or you just compiled it with the above command:
    - navigate to the output folder containing the class files
    - needed command in cmd: java main.Main

### Programmierdokumentation

See code comments and Doxygen file ().

### Testdokumentation

See Testreport ().

### Benutzerdokumentation

This game is based on the official UNO rules. For more information see: https://www.uno-kartenspiel.de/spielregeln/

There are however a few modifications, e.g. the rule for noticing another player has forgotten to shout UNO is not included (if you forget to shout UNO, you automatically get 1 penalty card).

This game is made for exactly 4 players. You can choose how many of them shall be bots and how many human.

Available commands:
- help
- draw
- skip
- exit
- status
- CardColor + CardValue (e.g. R8)
- CardColor (e.g. B)
- UNO

All commands are case insensitive.

For more information see help.txt

