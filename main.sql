CREATE DATABASE IF NOT EXISTS PDE_testing2;
USE PDE_testing2;




CREATE TABLE IF NOT EXISTS Utilizatori (

    ID_Utilizator  INT PRIMARY KEY AUTO_INCREMENT,
    Tip_Utilizator ENUM ('Student', 'Profesor', 'SuperAdministrator', 'Administrator') NOT NULL,
    CNP            VARCHAR(13) UNIQUE NOT NULL,
    Nume           VARCHAR(50) NOT NULL,
    Prenume        VARCHAR(50) NOT NULL,
    Adresa         VARCHAR(100) UNIQUE NOT NULL,
    Telefon        VARCHAR(15) UNIQUE NOT NULL,
    Email          VARCHAR(50) UNIQUE NOT NULL,
    Cont_IBAN      VARCHAR(30) UNIQUE NOT NULL,
    Numar_Contract INT UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS UtilizatoriAutentificare (

    ID_Utilizator INT Primary Key AUTO_INCREMENT,
	Tip_Utilizator ENUM ('Student', 'Profesor', 'SuperAdministrator', 'Administrator') NOT NULL,
    Parola VARCHAR(15),
    Nume_Utilizator VARCHAR(50) UNIQUE NOT NULL,
    Token_Autentificare VARCHAR(255),
    FOREIGN KEY (ID_Utilizator) REFERENCES Utilizatori (ID_Utilizator) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS SuperAdministratori
(
    ID_SuperAdministrator INT PRIMARY KEY,
    FOREIGN KEY (ID_SuperAdministrator) REFERENCES Utilizatori (ID_Utilizator) ON DELETE CASCADE 
);

CREATE TABLE IF NOT EXISTS Administratori
(
    ID_Administrator INT PRIMARY KEY,
    FOREIGN KEY (ID_Administrator) REFERENCES Utilizatori (ID_Utilizator) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Profesori
(
    ID_Profesor INT PRIMARY KEY auto_increment,
    Departament VARCHAR(50),
    Ore_Minim   INT,
    Ore_Maxim   INT,
    FOREIGN KEY (ID_Profesor) REFERENCES Utilizatori (ID_Utilizator) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Discipline
(
    ID_Disciplina        INT PRIMARY KEY AUTO_INCREMENT,
    ID_Profesor          INT,
    Nume_Disciplina      VARCHAR(100),
    Descriere            TEXT,
    Numar_Maxim_Studenti INT,
    FOREIGN KEY (ID_Profesor) REFERENCES Profesori (ID_Profesor) ON DELETE CASCADE

);

CREATE TABLE IF NOT EXISTS Asociere_Curs_Profesor (

    ID_Asociere INT PRIMARY KEY AUTO_INCREMENT,
    ID_Disciplina INT,
    ID_Profesor INT,
    FOREIGN KEY (ID_Disciplina) REFERENCES Discipline(ID_Disciplina) ON DELETE CASCADE ,
    FOREIGN KEY (ID_Profesor) REFERENCES Profesori(ID_Profesor) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS TipActivitate (

    ID_Tip_Activitate INT PRIMARY KEY AUTO_INCREMENT,
    Nume_Tip_Activitate enum ('Curs','Seminar','Laborator','Colocviu','Examen') NOT NULL,
    Descriere TEXT,
    Numar_Maxim_Studenti INT,
    Data_Inceput DATETIME,
    Data_Sfarsit DATETIME
);

CREATE TABLE IF NOT EXISTS Disciplina_TipActivitate (
    
    ID_Asociere_Disciplina INT PRIMARY KEY AUTO_INCREMENT,
    ID_Disciplina INT,
    ID_Tip_Activitate INT,
    FOREIGN KEY (ID_Disciplina) REFERENCES Discipline(ID_Disciplina) ON DELETE CASCADE,
    FOREIGN KEY (ID_Tip_Activitate) REFERENCES TipActivitate(ID_Tip_Activitate) ON DELETE CASCADE

);

CREATE TABLE IF NOT EXISTS Studenti
(
    ID_Student    INT PRIMARY KEY auto_increment,
    An_Studiu     INT,
    Ore_Sustinute INT,
    Nr_Absente INT,
    FOREIGN KEY (ID_Student) REFERENCES Utilizatori (ID_Utilizator) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Inscrieri_Studenti_Activitate
(
    ID_Inscriere  INT PRIMARY KEY AUTO_INCREMENT,
    ID_Student    INT,
    ID_Activitate INT,
    FOREIGN KEY (ID_Student) REFERENCES Studenti (ID_Student) ON DELETE CASCADE,
    FOREIGN KEY (ID_Activitate) REFERENCES Disciplina_TipActivitate (ID_Asociere_Disciplina) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Note (

    ID_Nota INT PRIMARY KEY AUTO_INCREMENT,
    ID_Student INT,
    ID_Disciplina INT,
    Nota_Curs INT,
    Nota_Seminar INT,
    Nota_Laborator INT,
    Nota_Finala INT,
    FOREIGN KEY (ID_Student) REFERENCES Studenti (ID_Student) ON DELETE CASCADE,
    FOREIGN KEY (ID_Disciplina) REFERENCES Discipline (ID_Disciplina) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS Grupuri_Studiu
(
    ID_Grup   INT PRIMARY KEY auto_increment,
    ID_Disciplina INT,
    Nume_Grup VARCHAR(50),
    Descriere TEXT,
    FOREIGN KEY(ID_Disciplina) REFERENCES Discipline (ID_Disciplina) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Membri_Grup
(
    ID_Membru  INT PRIMARY KEY AUTO_INCREMENT,
    ID_Grup    INT,
    ID_Student INT,
    FOREIGN KEY (ID_Grup) REFERENCES Grupuri_Studiu (ID_Grup) ON DELETE CASCADE,
    FOREIGN KEY (ID_Student) REFERENCES Studenti (ID_Student) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Mesaje_Grup
(
    ID_Mesaj       INT PRIMARY KEY auto_increment,
    ID_Grup        INT,
    ID_Student     INT,
    Continut_Mesaj TEXT,
    FOREIGN KEY (ID_Grup) REFERENCES Grupuri_Studiu (ID_Grup) ON DELETE CASCADE,
    FOREIGN KEY (ID_Student) REFERENCES Studenti (ID_Student) ON DELETE CASCADE
); 

CREATE TABLE IF NOT EXISTS Activitati_Grup
(
    ID_Activitate_Grup       INT PRIMARY KEY auto_increment,
    ID_Grup                  INT,
    Data_Inceput             DATETIME,
    Durata                   INT,
    Numar_Minim_Participanti INT,
    Timp_Expirare            DATETIME,
    FOREIGN KEY (ID_Grup) REFERENCES Grupuri_Studiu (ID_Grup) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS Calendar
(
    ID_Calendar   INT PRIMARY KEY auto_increment,
    Ora           TIME,
    Data          DATE,
    ID_Calendar_Activitate INT,
    FOREIGN KEY (ID_Calendar_Activitate) REFERENCES Disciplina_TipActivitate (ID_Asociere_Disciplina) ON DELETE CASCADE
);



-- ----------------------------------------------------------------------------------------------------------------------------------------


USE PDE_testing2;

#inserarile au fost facute



-- Inserări pentru tabela Utilizatori
INSERT INTO Utilizatori (Tip_Utilizator, CNP, Nume, Prenume, Adresa, Telefon, Email, Cont_IBAN, Numar_Contract)
VALUES 
  ('Student', '1234567890123', 'Popescu', 'Ion', 'Str. Mihai Viteazu, nr. 1', '0712345698', 'popescu.ion@example.com', 'RO123456759012345678901234567', 1001),
  ('Profesor', '2345678901234', 'Ionescu', 'Maria', 'Bd. Republicii, nr. 10', '0723456789', 'ionescu.maria@example.com', 'RO987614321098765432109876543', 2001),
  ('SuperAdministrator', '3456789012345', 'Dumitrescu', 'Alex', 'Piata Unirii, nr. 5', '0734567890', 'dumitrescu.alex@example.com', 'RO567890123456789012345678901', 3001),
  ('Administrator', '4567890123456', 'Georgescu', 'Elena', 'Calea Victoriei, nr. 15', '0745678901', 'georgescu.elena@example.com', 'RO321098765432109876543210987', 4001),
  ('Student', '5678901234567', 'Vasilescu', 'Adrian', 'Aleea Florilor, nr. 7', '0756789012', 'vasilescu.adrian@example.com', 'RO654321098765032109876543210', 1002),
  ('Profesor', '6789012345678', 'Popa', 'Ana', 'Splaiul Independentei, nr. 20', '0767890123', 'popa.ana@example.com', 'RO789012345678901234567890123', 2002),
  ('SuperAdministrator', '7890123456789', 'Mihai', 'George', 'Bd. Decebal, nr. 25', '0778901234', 'mihai.george@example.com', 'RO987654321098765432109876543', 3002),
  ('Administrator', '8901234567890', 'Gheorghe', 'Mirela', 'Calea Dorobantilor, nr. 30', '0789012345', 'gheorghe.mirela@example.com', 'RO230567890123456789012345678', 4002),
  ('Student', '9012345678901', 'Popescu', 'Cristian', 'Str. Soseaua Nordului, nr. 12', '0790123456', 'popescu.cristian@example.com', 'RO543210987654321098765432109', 1003),
  ('Profesor', '0123456789012', 'Iancu', 'Elena', 'Aleea Mihai Bravu, nr. 8', '0801234567', 'iancu.elena@example.com', 'RO678901234567890983456789012', 2003);


-- Inserări pentru tabela UtilizatoriAutentificare
INSERT INTO UtilizatoriAutentificare (Parola, Nume_Utilizator)
VALUES 
  ('parola123', 'popescu_ion'),
  ('parola456', 'ionescu_maria'),
  ('parola789', 'dumitrescu_alex'),
  ('parola012', 'georgescu_elena'),
  ('parola345', 'vasilescu_adrian'),
  ('parola678', 'popa_ana'),
  ('parola901', 'mihai_george'),
  ('parola234', 'gheorghe_mirela'),
  ('parola567', 'popescu_cristian'),
  ('parola890', 'iancu_elena');


-- Inserări pentru tabela SuperAdministratori
INSERT INTO SuperAdministratori (ID_SuperAdministrator)
VALUES (1), (2), (3), (4), (5), (6), (7), (8), (9), (10);


-- Inserări pentru tabela Administratori
INSERT INTO Administratori (ID_Administrator)
VALUES (1), (2), (3), (4), (5), (6), (7), (8), (9), (10);


-- Inserări pentru tabela Profesori
INSERT INTO Profesori (Departament, Ore_Minim, Ore_Maxim)
VALUES 
  ('Informatica', 20, 30),
  ('Matematica', 15, 25),
  ('Fizica', 18, 28),
  ('Chimie', 16, 26),
  ('Biologie', 22, 32),
  ('Istorie', 12, 22),
  ('Geografie', 14, 24),
  ('Economie', 25, 35),
  ('Arte', 10, 20),
  ('Limbi Straine', 30, 40);


INSERT INTO Discipline (ID_Profesor, Nume_Disciplina, Descriere, Numar_Maxim_Studenti)
VALUES 
  (1, 'Baze de Date', 'Introducere în bazele de date relaționale pentru studenții de anul I', 50),
  (2, 'Algebră Liniară', 'Studiul spațiilor vectoriale și transformărilor liniare pentru studenții de matematică', 40),
  (3, 'Mecanică Clasică', 'Principiile mecanicii clasice newtoniene pentru studenții de fizică', 30),
  (4, 'Chimie Organica', 'Structura și reactivitatea compușilor organici pentru studenții de chimie', 35),
  (5, 'Genetica', 'Studiul eredității și variației la nivel genetic pentru studenții de biologie', 45),
  (6, 'Istoria Antică', 'Evolutia umanitatii in perioada antica pentru studenții de istorie', 20),
  (7, 'Geografia Economica', 'Relația dintre geografie și economie pentru studenții de geografie', 25),
  (8, 'Microeconomie', 'Studiul comportamentului agentilor economici individuali pentru studenții de economie', 30),
  (9, 'Arta Moderna', 'Explorarea mișcărilor artistice moderne pentru studenții de arte', 15),
  (10, 'Limba Engleza Avansata', 'Dezvoltarea abilităților avansate de limbă engleză pentru studenții cu studii lingvistice', 40);


-- Inserări pentru tabela Asociere_Curs_Profesor
INSERT INTO Asociere_Curs_Profesor (ID_Disciplina, ID_Profesor)
VALUES 
  (1, 1),
  (2, 2),
  (3, 3),
  (4, 4),
  (5, 5),
  (6, 6),
  (7, 7),
  (8, 8),
  (9, 9),
  (10, 10);


-- Inserări pentru tabela TipActivitate
INSERT INTO TipActivitate (Nume_Tip_Activitate, Descriere, Numar_Maxim_Studenti, Data_Inceput, Data_Sfarsit)
VALUES 
  ('Curs', 'Curs introductiv pentru disciplina Baze de Date', 100, '2023-01-10 09:00:00', '2023-01-10 12:00:00'),
  ('Seminar', 'Seminar de discuții și exerciții pentru Algebră Liniară', 50, '2023-01-15 14:00:00', '2023-01-15 16:00:00'),
  ('Laborator', 'Laborator de experimente pentru Chimie Organica', 40, '2023-01-20 10:00:00', '2023-01-20 13:00:00'),
  ('Curs', 'Curs de Istoria Antică', 80, '2023-01-25 11:00:00', '2023-01-25 14:00:00'),
  ('Seminar', 'Seminar de analiză economică pentru Microeconomie', 60, '2023-02-01 15:00:00', '2023-02-01 17:00:00'),
  ('Laborator', 'Laborator de desen artistic pentru Arta Moderna', 30, '2023-02-05 13:00:00', '2023-02-05 16:00:00'),
  ('Curs', 'Curs avansat de Limba Engleza', 90, '2023-02-10 09:30:00', '2023-02-10 12:30:00'),
  ('Seminar', 'Seminar de proiectare pentru Limba Engleza Avansata', 70, '2023-02-15 14:30:00', '2023-02-15 16:30:00'),
  ('Laborator', 'Laborator de genetică pentru Biologie', 35, '2023-02-20 10:30:00', '2023-02-20 13:30:00'),
  ('Curs', 'Curs de introducere în programare pentru Informatica', 120, '2023-02-25 11:30:00', '2023-02-25 14:30:00');


-- Inserări pentru tabela Disciplina_TipActivitate
INSERT INTO Disciplina_TipActivitate (ID_Disciplina, ID_Tip_Activitate)
VALUES 
  (1, 1),
  (2, 2),
  (3, 3),
  (6, 1),
  (8, 2),
  (9, 3),
  (10, 1),
  (4, 2),
  (5, 3),
  (7, 1);


-- Inserări pentru tabela Studenti
INSERT INTO Studenti (An_Studiu, Ore_Sustinute,Nr_Absente)
VALUES 
  (2, 40,0),
  (3, 35,0),
  (1, 25,0),
  (4, 30,0),
  (2, 28,0),
  (3, 22,0),
  (1, 18,0),
  (4, 20,0),
  (2, 15,0),
  (3, 10,0);


/*
-- Inserări pentru tabela Inscrieri_Studenti_Disciplina
INSERT INTO Inscrieri_Studenti_Disciplina (ID_Student, ID_Disciplina)
VALUES 
  (1, 1),
  (2, 2),
  (3, 3),
  (4, 6),
  (5, 8),
  (6, 9),
  (7, 10),
  (8, 4),
  (9, 5),
  (10, 7);

-- Inserări pentru tabela Note
-- Exemplu de inserare 10 tuple în tabela Note
INSERT INTO Note (ID_Student, ID_Disciplina, Nota_Curs, Nota_Seminar, Nota_Laborator, Nota_Finala)
VALUES
  (1, 1, 8, 9, 7, NULL),
  (2, 1, 7, 8, 9, NULL),
  (3, 2, 9, 8, 7, NULL),
  (4, 2, 6, 7, 8, NULL),
  (5, 3, 8, 9, 7, NULL),
  (6, 3, 7, 8, 9, NULL),
  (7, 4, 9, 8, 7, NULL),
  (8, 4, 6, 7, 8, NULL),
  (9, 5, 8, 9, 7, NULL),
  (10, 5, 7, 8, 9, NULL);
*/


-- Inserări pentru tabela Grupuri_Studiu
INSERT INTO Grupuri_Studiu (Nume_Grup, Descriere)
VALUES 
  ('Grup_1', 'Grup de studiu pentru Baze de Date'),
  ('Grup_2', 'Grup de discuții pentru Limba Engleza Avansata'),
  ('Grup_3', 'Grup de proiectare pentru Arta Moderna'),
  ('Grup_4', 'Grup de laborator pentru Microeconomie'),
  ('Grup_5', 'Grup de cercetare pentru Genetica'),
  ('Grup_6', 'Grup de lectii pentru Informatica'),
  ('Grup_7', 'Grup de studiu pentru Algebră Liniară'),
  ('Grup_8', 'Grup de laborator pentru Chimie Organica'),
  ('Grup_9', 'Grup de discuții pentru Istoria Antică'),
  ('Grup_10', 'Grup de proiectare pentru Limba Engleza');


-- Inserări pentru tabela Membri_Grup
INSERT INTO Membri_Grup (ID_Grup, ID_Student)
VALUES 
  (1, 1),
  (2, 2),
  (3, 3),
  (4, 6),
  (5, 8),
  (6, 10),
  (7, 4),
  (8, 5),
  (9, 7),
  (10, 9);


-- Inserări pentru tabela Mesaje_Grup
INSERT INTO Mesaje_Grup (ID_Grup, ID_Student, Continut_Mesaj)
VALUES 
  (1, 1, 'Bun venit în Grupul 1!'),
  (2, 2, 'Salutare! Sper că vă veți bucura de discuțiile noastre.'),
  (3, 3, 'Grupul 3 este dedicat discuțiilor despre proiectele de artă modernă.'),
  (4, 6, 'Bună! Când avem următorul laborator în Grupul 4?'),
  (5, 8, 'Salut tuturor! Avem vreo ședință programată pentru Grupul 5?'),
  (6, 10, 'Vă rog să propuneți subiecte interesante pentru discuțiile din Grupul 6.'),
  (7, 4, 'Grupul 7 va avea o sesiune de studiu pentru algebră liniară săptămâna viitoare.'),
  (8, 5, 'Cine participă la laboratorul de Chimie Organica în Grupul 8?'),
  (9, 7, 'Salutare! Care sunt temele discutate în Grupul 9?'),
  (10, 9, 'Propunem să organizăm o ședință de proiectare în Grupul 10.');

/*
-- Inserări pentru tabela Activitati_Grup
INSERT INTO Activitati_Grup (ID_Grup, Data_Inceput, Durata, Numar_Minim_Participanti, Timp_Expirare)
VALUES 
  (1, '2023-03-01 14:00:00', 2, 5, '2023-03-01 16:00:00'),
  (2, '2023-03-05 10:30:00', 1, 3, '2023-03-05 11:30:00'),
  (3, '2023-03-10 15:15:00', 3, 8, '2023-03-10 18:15:00'),
  (4, '2023-03-15 13:30:00', 2, 6, '2023-03-15 15:30:00'),
  (5, '2023-03-20 09:45:00', 1, 4, '2023-03-20 10:45:00'),
  (6, '2023-03-25 16:00:00', 2, 7, '2023-03-25 18:00:00'),
  (7, '2023-03-30 11:45:00', 1, 4, '2023-03-30 12:45:00'),
  (8, '2023-04-05 14:30:00', 3, 9, '2023-04-05 17:30:00'),
  (9, '2023-04-10 10:00:00', 2, 5, '2023-04-10 11:00:00'),
  (10, '2023-04-15 15:45:00', 2, 6, '2023-04-15 17:45:00');

-- Inserări pentru tabela Calendar
INSERT INTO Calendar (Ora, Data, ID_Disciplina)
VALUES 
  ('14:00:00', '2023-03-01', 1),
  ('10:30:00', '2023-03-05', 2),
  ('15:15:00', '2023-03-10', 3),
  ('13:30:00', '2023-03-15', 6),
  ('09:45:00', '2023-03-20', 8),
  ('16:00:00', '2023-03-25', 9),
  ('11:45:00', '2023-03-30', 10),
  ('14:30:00', '2023-04-05', 4),
  ('10:00:00', '2023-04-10', 5),
  ('15:45:00', '2023-04-15', 7);
*/


-- ----------------------------------------------------------------------------------------------------------------------------------------



DELIMITER //

CREATE PROCEDURE AutentificareUtilizator(
    IN p_username VARCHAR(50),
    IN p_parola VARCHAR(50)
)
BEGIN
    DECLARE is_authenticated INT;

    -- Verifică dacă utilizatorul este autentificat cu succes
    SELECT COUNT(*)
    INTO is_authenticated
    FROM UtilizatoriAutentificare
    WHERE Nume_Utilizator = p_username AND Parola = p_parola;

    IF is_authenticated > 0 THEN
        -- Utilizatorul este autentificat cu succes
        SELECT 'Autentificare reușită. Bine ai venit';
        
        UPDATE UtilizatoriAutentificare
        SET Token_Autentificare = 'success'
        WHERE Nume_Utilizator = p_username;
    ELSE
        -- Utilizatorul nu a putut fi autentificat
        SELECT 'Autentificare eșuată. Numele de utilizator sau parola incorecte.' AS Mesaj;
    END IF;
END //

DELIMITER ;



DELIMITER //

CREATE PROCEDURE DeautentificareUtilizator(
    IN p_username VARCHAR(50)
)
BEGIN
    DECLARE is_authenticated INT;

    -- Verifică dacă utilizatorul este autentificat și are un token valid
    SELECT COUNT(*)
    INTO is_authenticated
    FROM UtilizatoriAutentificare
    WHERE Nume_Utilizator = p_username AND Token_Autentificare IS NOT NULL;

    IF is_authenticated > 0 THEN
        -- Utilizatorul este autentificat, poți invalida sau șterge tokenul
        UPDATE UtilizatoriAutentificare
        SET Token_Autentificare = NULL
        WHERE Nume_Utilizator = p_username;

        SELECT 'Deautentificare reușită. La revedere';
    ELSE
        -- Utilizatorul nu este autentificat, afișează un mesaj corespunzător
        SELECT 'Utilizatorul nu este autentificat' AS Mesaj;
    END IF;
END //

DELIMITER ;




DELIMITER //
CREATE PROCEDURE AdaugaUtilizator(
    IN tipUtilizator ENUM('Student', 'Profesor', 'SuperAdministrator', 'Administrator'),
    IN cnp VARCHAR(13),
    IN nume VARCHAR(50),
    IN prenume VARCHAR(50),
    IN adresa VARCHAR(100),
    IN telefon VARCHAR(15),
    IN email VARCHAR(50),
    IN contIBAN VARCHAR(30),
    IN numarContract INT,
    IN parola VARCHAR(15),
    IN numeUtilizator VARCHAR(50),
    IN departament VARCHAR(50),
    IN oreMinim INT,
    IN oreMaxim INT,
    IN anStudiu INT,
    IN oreSustinute INT,
    IN nrAbsente INT
)
BEGIN
    DECLARE lastUserID INT;

    -- Adaugă utilizator în tabela Utilizatori
    INSERT INTO Utilizatori (Tip_Utilizator, CNP, Nume, Prenume, Adresa, Telefon, Email, Cont_IBAN, Numar_Contract)
    VALUES (tipUtilizator, cnp, nume, prenume, adresa, telefon, email, contIBAN, numarContract);

    -- Obține ID-ul utilizatorului adăugat
    SET lastUserID = LAST_INSERT_ID();

    -- Adaugă utilizator în tabela UtilizatoriAutentificare
    INSERT INTO UtilizatoriAutentificare (tipUtilizator,Parola, Nume_Utilizator)
    VALUES (tipUtilizator,parola, numeUtilizator);

    -- În funcție de tipul utilizatorului, adaugă în tabela corespunzătoare
    CASE
        WHEN tipUtilizator = 'SuperAdministrator' THEN
            INSERT INTO SuperAdministratori (ID_SuperAdministrator) VALUES (lastUserID);
        WHEN tipUtilizator = 'Administrator' THEN
            INSERT INTO Administratori (ID_Administrator) VALUES (lastUserID);
        WHEN tipUtilizator = 'Profesor' THEN
            INSERT INTO Profesori (Departament, Ore_Minim, Ore_Maxim) VALUES (departament, oreMinim, oreMaxim);
        WHEN tipUtilizator = 'Student' THEN
            INSERT INTO Studenti (An_Studiu,Ore_Sustinute,Nr_Absente) VALUES (anStudiu,oreSustinute,nrAbsente); 
    END CASE;
END //
DELIMITER ;




DELIMITER //
-- Procedura pentru cautarea unui utilizator dupa nume si prenume
CREATE PROCEDURE CautareUtilizator(
    IN p_nume VARCHAR(50),
    IN p_prenume VARCHAR(50)
)
BEGIN
    SELECT *
    FROM Utilizatori
    WHERE Nume = p_nume AND Prenume = p_prenume;
END //







DELIMITER //
-- Procedura pentru filtrarea utilizatorilor dupa tip
CREATE PROCEDURE FiltrareUtilizatori(
    IN p_tip ENUM('Student', 'Profesor', 'SuperAdministrator', 'Administrator')
)
BEGIN
    SELECT *
    FROM Utilizatori
    WHERE Tip_Utilizator = p_tip;
END //

DELIMITER ;






DELIMITER //
CREATE PROCEDURE AdaugaNota(
    IN idStudent INT,
    IN idDisciplina INT,
    IN notaCurs INT,
    IN notaSeminar INT,
    IN notaLaborator INT
)
BEGIN
    INSERT INTO Note (ID_Student, ID_Disciplina, Nota_Curs, Nota_Seminar, Nota_Laborator)
    VALUES (idStudent, idDisciplina, notaCurs, notaSeminar, notaLaborator);
END //
DELIMITER ;






DELIMITER //

CREATE PROCEDURE CalculNotaFinala(
    IN idStudent INT,
    IN idDisciplina INT
)
BEGIN
    DECLARE notaSeminar INT;
    DECLARE notaLaborator INT;
    DECLARE notaCurs INT;
    DECLARE notaFinala INT;

    -- Obține notele pentru seminar, laborator și curs pentru studentul și disciplina specificate
    SELECT Nota_Seminar, Nota_Laborator, Nota_Curs
    INTO notaSeminar, notaLaborator, notaCurs
    FROM Note
    WHERE ID_Student = idStudent AND ID_Disciplina = idDisciplina;

    -- Calculează nota finală conform formulei
    SET notaFinala = ROUND((0.2 * notaSeminar) + (0.35 * notaLaborator) + (0.45 * notaCurs));

    -- Actualizează sau introdu în tabela Note valoarea notei finale și afișează mesajul corespunzător
    UPDATE Note
    SET Nota_Finala = notaFinala
    WHERE ID_Student = idStudent AND ID_Disciplina = idDisciplina;

    -- Afișează mesajul corespunzător
    IF notaFinala < 5 THEN
        SELECT 'Studentul a picat disciplina.' AS Mesaj;
    ELSE
        SELECT 'Studentul a promovat disciplina.' AS Mesaj;
    END IF;
END //

DELIMITER ;






DELIMITER //

CREATE PROCEDURE AsigneazaProfesorLaCurs(
    IN p_idProfesor INT,
    IN p_idDisciplina INT,
    IN p_idTipActivitate INT
)
BEGIN
    -- Verifică dacă există deja o asociere între profesor și curs
    DECLARE existsAsociere INT;
    SELECT COUNT(*)
    INTO existsAsociere
    FROM Asociere_Curs_Profesor
    WHERE ID_Profesor = p_idProfesor AND ID_Disciplina = p_idDisciplina;

    -- În cazul în care nu există asociere, adaugă una nouă
    IF existsAsociere = 0 THEN
        -- Adaugă o nouă asociere între profesor și curs
        INSERT INTO Asociere_Curs_Profesor (ID_Disciplina, ID_Profesor)
        VALUES (p_idDisciplina, p_idProfesor);

        -- Adaugă o nouă asociere între tipul de activitate și disciplina respectivă
        INSERT INTO Disciplina_TipActivitate (ID_Disciplina, ID_Tip_Activitate)
        VALUES (p_idDisciplina, p_idTipActivitate);

        -- Returnează un mesaj de succes
        SELECT 'Asignare profesor la curs reușită.' AS Mesaj;
    ELSE
        -- Returnează un mesaj dacă profesorul este deja asignat la această disciplină
        SELECT 'Profesorul este deja asignat la această disciplină.' AS Mesaj;
    END IF;
END //

DELIMITER ;








DELIMITER //


CREATE PROCEDURE InregistrareStudentActivitate(
    IN p_idStudent INT,
    IN p_idActivitate INT
)
BEGIN
    -- Verifică dacă studentul este deja înscris la această activitate
    DECLARE inscriereExista INT;
    SELECT COUNT(*)
    INTO inscriereExista
    FROM Inscrieri_Studenti_Activitate
    WHERE ID_Student = p_idStudent AND ID_Activitate = p_idActivitate;

    -- În cazul în care nu există încă o înregistrare, adaugă înregistrarea
    IF inscriereExista = 0 THEN
        INSERT INTO Inscrieri_Studenti_Activitate (ID_Student, ID_Activitate)
        VALUES (p_idStudent, p_idActivitate);

        SELECT 'Inscriere reușită.' AS Mesaj;
    ELSE
        -- Returnează un mesaj dacă studentul este deja înscris
        SELECT 'Studentul este deja înscris la această activitate.' AS Mesaj;
    END IF;
END //

DELIMITER ;













DELIMITER //

CREATE PROCEDURE AdaugaStudent(
    IN anStudiu INT,
    IN oreSustinute INT,
    IN nrAbsente INT,
    IN cnp VARCHAR(13),
    IN nume VARCHAR(50),
    IN prenume VARCHAR(50),
    IN adresa VARCHAR(100),
    IN telefon VARCHAR(15),
    IN email VARCHAR(50),
    IN contIBAN VARCHAR(30),
    IN numarContract INT,
    IN parola VARCHAR(15),
    IN numeUtilizator VARCHAR(50),
    IN idGrup INT
)
BEGIN

    -- Adaugă utilizator în tabela Utilizatori
    INSERT INTO Utilizatori (Tip_Utilizator, CNP, Nume, Prenume, Adresa, Telefon, Email, Cont_IBAN, Numar_Contract)
    VALUES ('Student', cnp, nume, prenume, adresa, telefon, email, contIBAN, numarContract);


    -- Adaugă utilizator în tabela UtilizatoriAutentificare
    INSERT INTO UtilizatoriAutentificare (Parola, Nume_Utilizator)
    VALUES (parola, numeUtilizator);

    -- Adaugă student în tabela Studenti
    INSERT INTO Studenti (An_Studiu, Ore_Sustinute,Nr_Absente)
    VALUES (anStudiu, oreSustinute,nrAbsente);

    -- Dacă este specificat un ID de grup, adaugă studentul în grup
    IF idGrup IS NOT NULL THEN
        INSERT INTO Membri_Grup (ID_Grup, ID_Student)
        VALUES (idGrup, lastUserID);
    END IF;
END //
DELIMITER ;













DELIMITER //

CREATE PROCEDURE StergeUtilizatorDupaTip(
    IN p_idUtilizator INT,
    IN p_tipUtilizator ENUM('Student', 'Profesor', 'SuperAdministrator', 'Administrator')
)
BEGIN
    DECLARE tipUtilizator VARCHAR(50);

    -- Obține tipul utilizatorului din tabela Utilizatori
    SELECT Tip_Utilizator INTO tipUtilizator
    FROM Utilizatori
    WHERE ID_Utilizator = p_idUtilizator;

    -- Verifică dacă tipul utilizatorului corespunde cu cel primit ca parametru
    IF tipUtilizator = p_tipUtilizator THEN
        -- Actualizează câmpurile relevante cu "-" sau 0 în celelalte tabele
        CASE
			WHEN p_tipUtilizator = 'SuperAdministrator' THEN
                UPDATE SuperAdministratori SET ID_SuperAdministrator = 0 WHERE ID_SuperAdministrator = p_idUtilizator;
                
            WHEN p_tipUtilizator = 'Administrator' THEN
                UPDATE Administratori SET ID_Administrator = 0 WHERE ID_Administrator = p_idUtilizator;

            WHEN p_tipUtilizator = 'Profesor' THEN
                UPDATE Profesori SET Departament = '-', Ore_Minim = 0, Ore_Maxim = 0 WHERE ID_Profesor = p_idUtilizator;
                
            WHEN p_tipUtilizator = 'Student' THEN
                UPDATE Studenti SET An_Studiu = 0, Ore_Sustinute = 0, Nr_Absente = 0 WHERE ID_Student = p_idUtilizator;
        END CASE;

        -- Actualizează câmpurile de tip utilizator la "-" în tabela Utilizatori
        UPDATE Utilizatori SET
            CNP = '-',
            Nume = '-',
            Prenume = '-',
            Adresa = '-',
            Telefon = '-',
            Email = '-',
            Cont_IBAN = '-',
            Numar_Contract = 0
        WHERE ID_Utilizator = p_idUtilizator;

        -- Actualizează câmpurile relevante cu "-" sau 0 în tabela UtilizatoriAutentificare
        UPDATE UtilizatoriAutentificare SET
            Parola = '-',
            Nume_Utilizator = '-',
            Token_Autentificare = '-'
        WHERE ID_Utilizator = p_idUtilizator;

        SELECT 'Utilizatorul a fost marcat cu succes.';
    ELSE
        SELECT 'Tipul utilizatorului nu corespunde. Nu s-a efectuat nicio modificare.';
    END IF;
END //

DELIMITER ;










DELIMITER //

CREATE PROCEDURE AdaugaProfesor(
    IN departament VARCHAR(50),
    IN oreMinim INT,
    IN oreMaxim INT,
    IN cnp VARCHAR(13),
    IN nume VARCHAR(50),
    IN prenume VARCHAR(50),
    IN adresa VARCHAR(100),
    IN telefon VARCHAR(15),
    IN email VARCHAR(50),
    IN contIBAN VARCHAR(30),
    IN numarContract INT,
    IN parola VARCHAR(15),
    IN numeUtilizator VARCHAR(50)
)
BEGIN

    -- Adaugă utilizator în tabela Utilizatori
    INSERT INTO Utilizatori (Tip_Utilizator, CNP, Nume, Prenume, Adresa, Telefon, Email, Cont_IBAN, Numar_Contract)
    VALUES ('Profesor', cnp, nume, prenume, adresa, telefon, email, contIBAN, numarContract);


    -- Adaugă utilizator în tabela UtilizatoriAutentificare
    INSERT INTO UtilizatoriAutentificare (Parola, Nume_Utilizator)
    VALUES (parola, numeUtilizator);

    -- Adaugă profesor în tabela Profesori
    INSERT INTO Profesori (Departament, Ore_Minim, Ore_Maxim)
    VALUES (departament, oreMinim, oreMaxim);
END //
DELIMITER ;












DELIMITER //

CREATE PROCEDURE ProgramareActivitate(
    IN p_ID_Profesor INT,
    IN p_ID_Disciplina INT,
    IN p_Nume_Activitate enum('Curs','Seminar','Laborator'),
    IN p_Data_Inceput DATETIME,
    IN p_Data_Sfarsit DATETIME,
    IN p_Numar_Maxim_Participanti INT
)
BEGIN
    DECLARE currentDate DATETIME;
    SET currentDate = NOW();

    -- Verifică dacă data de început și data de sfârșit sunt în viitor
    IF p_Data_Inceput > currentDate AND p_Data_Sfarsit > currentDate AND p_Data_Sfarsit > p_Data_Inceput THEN
        -- Verifică dacă profesorul și disciplina există și sunt asociate
        IF EXISTS (SELECT * FROM Profesori WHERE ID_Profesor = p_ID_Profesor) AND
           EXISTS (SELECT * FROM Discipline WHERE ID_Disciplina = p_ID_Disciplina AND ID_Profesor = p_ID_Profesor) THEN
           
            -- Inserează activitatea în tabela TipActivitate
            INSERT INTO TipActivitate (Nume_Tip_Activitate, Data_Inceput, Data_Sfarsit, Numar_Maxim_Studenti)
            VALUES (p_Nume_Activitate, p_Data_Inceput, p_Data_Sfarsit, p_Numar_Maxim_Participanti);

            SET @lastID = LAST_INSERT_ID();

            -- Inserează asocierea disciplină-tip activitate în tabela Disciplina_TipActivitate
            INSERT INTO Disciplina_TipActivitate (ID_Disciplina, ID_Tip_Activitate)
            VALUES (p_ID_Disciplina, @lastID);

            -- Inserează activitatea în tabela Calendar
            INSERT INTO Calendar (Ora, Data, ID_Calendar_Activitate)
            VALUES (TIME(p_Data_Inceput), DATE(p_Data_Inceput), @lastID);

            SELECT 'Activitatea a fost programată cu succes.' AS Mesaj;
        ELSE
            SELECT 'Profesorul sau disciplina nu există sau nu sunt asociate.' AS Mesaj;
        END IF;
    ELSE
        SELECT 'Datele introduse nu sunt valide pentru programarea activității.' AS Mesaj;
    END IF;
END //

DELIMITER ;










DELIMITER //

CREATE PROCEDURE ModificaInformatiiUtilizator(
    IN idUtilizator INT,
    IN nouaAdresa VARCHAR(100),
    IN noulTelefon VARCHAR(15),
    IN noulEmail VARCHAR(50),
    IN noulContIBAN VARCHAR(30)
)
BEGIN
    UPDATE Utilizatori
    SET Adresa = nouaAdresa,
        Telefon = noulTelefon,
        Email = noulEmail,
        Cont_IBAN = noulContIBAN
    WHERE ID_Utilizator = idUtilizator;
END //
DELIMITER ;












DELIMITER //

CREATE PROCEDURE RenuntareStudentActivitate(
    IN p_idStudent INT,
    IN p_idActivitate INT
)
BEGIN
    -- Verifică dacă studentul este înscris la această disciplină
    DECLARE inscriereExista INT;
    SELECT COUNT(*)
    INTO inscriereExista
    FROM Inscrieri_Studenti_Activitate
    WHERE ID_Student = p_idStudent AND ID_Activitate = p_idActivitate;

    -- În cazul în care există înregistrare, șterge înregistrarea
    IF inscriereExista > 0 THEN
        DELETE FROM Inscrieri_Studenti_Activitate
        WHERE ID_Student = p_idStudent AND ID_Activitate = p_idActivitate;

        SELECT 'Renunțare reușită.' AS Mesaj;
    ELSE
        -- Returnează un mesaj dacă studentul nu este înscris la disciplină
        SELECT 'Studentul nu este înscris la această disciplină.' AS Mesaj;
    END IF;
END //

DELIMITER ;




-- ---------------------------------------------------------------------------------------------------------------------------------------

-- TriggerInscriere Grup Studiu
DELIMITER //

CREATE TRIGGER InscriereGrupStudiu
AFTER INSERT ON Membri_Grup
FOR EACH ROW BEGIN
    INSERT INTO Mesaje_Grup (ID_Grup, ID_Student, Continut_Mesaj) VALUES (NEW.ID_Grup, NEW.ID_Student, 'Bun venit in grupul de studiu!');
END //
DELIMITER ;






DELIMITER //

CREATE TRIGGER TrimitereMesajGrup
BEFORE INSERT ON Mesaje_Grup
FOR EACH ROW
BEGIN
    DECLARE isStudentInGroup INT;

    -- Verifică dacă studentul este membru al grupului pentru care este destinat mesajul
    SELECT COUNT(*) INTO isStudentInGroup
    FROM Membri_Grup
    WHERE ID_Student = NEW.ID_Student AND ID_Grup = NEW.ID_Grup;

    -- Dacă studentul nu este membru al grupului, anulează inserarea
    IF isStudentInGroup = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Studentul nu este membru al grupului.';
    END IF;
END //

DELIMITER ;


-- ---------------------------------------------------------------------------------------------------------------------------------------











