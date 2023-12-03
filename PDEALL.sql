CREATE DATABASE IF NOT EXISTS PDE_testing2;
USE PDE_testing2;

CREATE DATABASE IF NOT EXISTS PDE_testing2;
USE PDE_testing2;

-- TabUser
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
    ID_Utilizator INT Primary Key,
    Parola VARCHAR(8),
    Nume_Utilizator VARCHAR(50) UNIQUE NOT NULL,
    FOREIGN KEY (ID_Utilizator) REFERENCES Utilizatori (ID_Utilizator)
);

CREATE TABLE IF NOT EXISTS SuperAdministratori
(
    ID_SuperAdministrator INT PRIMARY KEY,
    FOREIGN KEY (ID_SuperAdministrator) REFERENCES Utilizatori (ID_Utilizator)
);


CREATE TABLE IF NOT EXISTS Administratori
(
    ID_Administrator INT PRIMARY KEY,
    FOREIGN KEY (ID_Administrator) REFERENCES Utilizatori (ID_Utilizator)
);

CREATE TABLE IF NOT EXISTS Profesori
(
    ID_Profesor INT PRIMARY KEY,
    Departament VARCHAR(50),
    Ore_Minim   INT,
    Ore_Maxim   INT,
    FOREIGN KEY (ID_Profesor) REFERENCES Utilizatori (ID_Utilizator)
);

CREATE TABLE IF NOT EXISTS Discipline
(
    ID_Disciplina        INT AUTO_INCREMENT PRIMARY KEY,
    ID_Profesor          INT,
    Nume_Disciplina      VARCHAR(100),
    Descriere            TEXT,
    Numar_Maxim_Studenti INT,
    FOREIGN KEY (ID_Profesor) REFERENCES Profesori (ID_Profesor)

);

-- Tabela Asociere_Curs_Profesor
CREATE TABLE Asociere_Curs_Profesor (
    ID_Asociere INT PRIMARY KEY,
    ID_Disciplina INT,
    ID_Profesor INT,
    FOREIGN KEY (ID_Disciplina) REFERENCES Discipline(ID_Disciplina) ,
    FOREIGN KEY (ID_Profesor) REFERENCES Profesori(ID_Profesor)
);

-- tip activitate
CREATE TABLE IF NOT EXISTS TipActivitate (
    ID_Tip_Activitate INT PRIMARY KEY,
    Nume_Tip_Activitate enum ('Curs','Seminar','Laborator') NOT NULL,
    Descriere TEXT,
    Numar_Maxim_Studenti INT,
    Data_Inceput DATETIME,
    Data_Sfarsit DATETIME,
    FOREIGN KEY (ID_Tip_Activitate) REFERENCES Discipline(ID_Disciplina)
);

CREATE TABLE Disciplina_TipActivitate (
    ID_Disciplina INT,
    ID_Tip_Activitate INT,
    FOREIGN KEY (ID_Disciplina) REFERENCES Discipline(ID_Disciplina),
    FOREIGN KEY (ID_Tip_Activitate) REFERENCES TipActivitate(ID_Tip_Activitate),
    PRIMARY KEY (ID_Disciplina, ID_Tip_Activitate)
);
/* ----------------------------------------------------------------------------------------------------------------- */

CREATE TABLE IF NOT EXISTS Studenti
(
    ID_Student    INT PRIMARY KEY,
    An_Studiu     INT,
    Ore_Sustinute INT,
    FOREIGN KEY (ID_Student) REFERENCES Utilizatori (ID_Utilizator)
);

CREATE TABLE IF NOT EXISTS Inscrieri_Studenti_Disciplina
(
    ID_Inscriere  INT PRIMARY KEY,
    ID_Student    INT,
    ID_Disciplina INT,
    FOREIGN KEY (ID_Student) REFERENCES Studenti (ID_Student),
    FOREIGN KEY (ID_Disciplina) REFERENCES Discipline (ID_Disciplina)
);


CREATE TABLE IF NOT EXISTS Note (
    ID_Nota INT PRIMARY KEY AUTO_INCREMENT,
    ID_Student INT,
    ID_Disciplina INT,
    Nota INT,
    FOREIGN KEY (ID_Student) REFERENCES Studenti (ID_Student),
    FOREIGN KEY (ID_Disciplina) REFERENCES Discipline (ID_Disciplina)
);


CREATE TABLE IF NOT EXISTS Grupuri_Studiu
(
    ID_Grup   INT PRIMARY KEY,
    Nume_Grup VARCHAR(50),
    Descriere TEXT
);


CREATE TABLE IF NOT EXISTS Membri_Grup
(
    ID_Membru  INT PRIMARY KEY,
    ID_Grup    INT,
    ID_Student INT,
    FOREIGN KEY (ID_Grup) REFERENCES Grupuri_Studiu (ID_Grup),
    FOREIGN KEY (ID_Student) REFERENCES Studenti (ID_Student)
);


CREATE TABLE IF NOT EXISTS Mesaje_Grup
(
    ID_Mesaj       INT PRIMARY KEY,
    ID_Grup        INT,
    ID_Student     INT,
    Continut_Mesaj TEXT,
    FOREIGN KEY (ID_Grup) REFERENCES Grupuri_Studiu (ID_Grup),
    FOREIGN KEY (ID_Student) REFERENCES Studenti (ID_Student)
); -- adaugare date si timp


CREATE TABLE IF NOT EXISTS Activitati_Grup
(
    ID_Activitate_Grup       INT PRIMARY KEY,
    ID_Grup                  INT,
    Data_Inceput             DATETIME,
    Durata                   INT,
    Numar_Minim_Participanti INT,
    Timp_Expirare            DATETIME,
    FOREIGN KEY (ID_Grup) REFERENCES Grupuri_Studiu (ID_Grup)
);


CREATE TABLE IF NOT EXISTS Calendar
(
    ID_Calendar   INT PRIMARY KEY,
    Ora           TIME,
    Data          DATE,
    ID_Disciplina INT,
    FOREIGN KEY (ID_Disciplina) REFERENCES Discipline (ID_Disciplina)
);


CREATE INDEX idx_nume ON Utilizatori (Nume);
CREATE INDEX idx_prenume ON Utilizatori (Prenume);

-- procedura Autentificare utilizatori v2
DELIMITER //
CREATE PROCEDURE AutentificareUtilizator(
    IN p_username VARCHAR(50),
    IN p_parola VARCHAR(50)
)
BEGIN
    SELECT * FROM UtilizatoriAutentificare WHERE Nume_Utilizator = p_username AND Parola = p_parola;
END //
DELIMITER ;

UPDATE UtilizatoriAutentificare
SET Parola = '123456'
-- where user id is that was inputed by the user
WHERE ID_Utilizator = 1;

-- procedura deautentificare utilizatori
DELIMITER //
CREATE PROCEDURE DeautentificareUtilizator()
BEGIN
    SELECT * FROM Utilizatori WHERE ID_Utilizator = 0;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS Adaugare_Utilizator;
-- Procedura Adaugare_Utilizator cu tratare cazuri nevalide și coduri de stare
DELIMITER //
CREATE PROCEDURE Adaugare_Utilizator(
    IN p_Tip_Utilizator VARCHAR(20),
    IN p_CNP VARCHAR(13),
    IN p_Nume VARCHAR(50),
    IN p_Prenume VARCHAR(50),
    IN p_Adresa VARCHAR(100),
    IN p_Telefon VARCHAR(15),
    IN p_Email VARCHAR(50),
    IN p_Cont_IBAN VARCHAR(30),
    IN p_Numar_Contract INT,
    OUT p_ResultCode INT
)
BEGIN
    DECLARE userCount INT;

    -- Verificare dacă CNP-ul este unic ???? MAI TREBUIE DACA IAM DAT UNIQUE LA CNP IN TABEL ???
    SELECT COUNT(*) INTO userCount FROM Utilizatori WHERE CNP = p_CNP;
    IF userCount > 0 THEN
        SET p_ResultCode = -1; -- Cod de eroare pentru CNP duplicat
        -- LEAVE Adaugare_Utilizator; -- Ieșire din procedură GIVES ERROR
    END IF;

    -- Alte verificări de validare pot fi adăugate aici...

    -- Adăugare utilizator în tabel
    INSERT INTO Utilizatori(Tip_Utilizator, CNP, Nume, Prenume, Adresa, Telefon, Email, Cont_IBAN, Numar_Contract)
    VALUES (p_Tip_Utilizator, p_CNP, p_Nume, p_Prenume, p_Adresa, p_Telefon, p_Email, p_Cont_IBAN, p_Numar_Contract);

    SET p_ResultCode = 1; -- Cod de succes
END //

DELIMITER ;


 -- modificare informatii utilizator v2
DELIMITER //
CREATE PROCEDURE ModificareInformatiiUtilizator(
    IN p_ID_Utilizator INT,
    IN p_Tip_Utilizator VARCHAR(20),
    IN p_CNP VARCHAR(13),
    IN p_Nume VARCHAR(50),
    IN p_Prenume VARCHAR(50),
    IN p_Adresa VARCHAR(100),
    IN p_Telefon VARCHAR(15),
    IN p_Email VARCHAR(50),
    IN p_Cont_IBAN VARCHAR(30),
    IN p_Numar_Contract INT,
    OUT p_ResultCode INT
)
BEGIN
    DECLARE userCount INT;

    -- Verificare dacă CNP-ul este unic ???? MAI TREBUIE DACA IAM DAT UNIQUE LA CNP IN TABEL ???
    SELECT COUNT(*) INTO userCount FROM Utilizatori WHERE CNP = p_CNP;
    IF userCount > 0 THEN
        SET p_ResultCode = -1; -- Cod de eroare pentru CNP duplicat
        -- LEAVE Adaugare_Utilizator; -- Ieșire din procedură GIVES ERROR
    END IF;

    -- Alte verificări de validare pot fi adăugate aici...

    -- Adăugare utilizator în tabel
    UPDATE Utilizatori SET Tip_Utilizator = p_Tip_Utilizator, CNP = p_CNP, Nume = p_Nume, Prenume = p_Prenume, Adresa = p_Adresa, Telefon = p_Telefon, Email = p_Email, Cont_IBAN = p_Cont_IBAN, Numar_Contract = p_Numar_Contract WHERE ID_Utilizator = p_ID_Utilizator;

    SET p_ResultCode = 1; -- Cod de succes
END //
DELIMITER ;

-- Trigger StergereUtilizator
DELIMITER //
CREATE TRIGGER StergereUtilizator
BEFORE DELETE ON Utilizatori
FOR EACH ROW BEGIN
    DELETE FROM UtilizatoriAutentificare WHERE ID_Utilizator = OLD.ID_Utilizator;
    DELETE FROM SuperAdministratori WHERE ID_SuperAdministrator = OLD.ID_Utilizator;
    DELETE FROM Administratori WHERE ID_Administrator = OLD.ID_Utilizator;
    DELETE FROM Profesori WHERE ID_Profesor = OLD.ID_Utilizator;
    DELETE FROM Studenti WHERE ID_Student = OLD.ID_Utilizator;
END //
DELIMITER ;

-- Trigger ProgramareActivitati
DELIMITER //
CREATE TRIGGER ProgramareActivitati
AFTER INSERT ON TipActivitate
FOR EACH ROW BEGIN
    INSERT INTO Calendar (Ora, Data, ID_Disciplina) VALUES (NEW.Data_Inceput, NEW.Data_Inceput, NEW.ID_Tip_Activitate);
END //
DELIMITER ;

-- Trigger Nota Activitati dupa formula adaptabila de profesor
DELIMITER //
CREATE TRIGGER NotaActivitati
AFTER INSERT ON Note
FOR EACH ROW BEGIN
    UPDATE Note SET Nota = (SELECT AVG(Nota) FROM Note WHERE ID_Student = NEW.ID_Student AND ID_Disciplina = NEW.ID_Disciplina) WHERE ID_Nota = NEW.ID_Nota;
END //
DELIMITER ;

-- TriggerInscriere Disciplina ceia ce implica si crearea unui tip de activitate
DELIMITER //
CREATE TRIGGER InscriereDisciplina
AFTER INSERT ON Inscrieri_Studenti_Disciplina
FOR EACH ROW BEGIN
    INSERT INTO TipActivitate (Nume_Tip_Activitate, Descriere, Numar_Maxim_Studenti, Data_Inceput, Data_Sfarsit) VALUES ('Curs', 'Cursul de la disciplina ' + (SELECT Nume_Disciplina FROM Discipline WHERE ID_Disciplina = NEW.ID_Disciplina), (SELECT Numar_Maxim_Studenti FROM Discipline WHERE ID_Disciplina = NEW.ID_Disciplina), NOW(), NOW() + 7);
END //
DELIMITER ;

-- Trigger_RenuntareCurs
DELIMITER //
CREATE TRIGGER RenuntareCurs
AFTER DELETE ON Inscrieri_Studenti_Disciplina
FOR EACH ROW BEGIN
    DELETE FROM TipActivitate WHERE ID_Tip_Activitate = (SELECT ID_Tip_Activitate FROM Disciplina_TipActivitate WHERE ID_Disciplina = OLD.ID_Disciplina);
END //
DELIMITER ;

-- TriggerInscriere Grup Studiu
DELIMITER //
CREATE TRIGGER InscriereGrupStudiu
AFTER INSERT ON Membri_Grup
FOR EACH ROW BEGIN
    INSERT INTO Mesaje_Grup (ID_Grup, ID_Student, Continut_Mesaj) VALUES (NEW.ID_Grup, NEW.ID_Student, 'Bun venit in grupul de studiu!');
END //
DELIMITER ;

-- Trigger Modificare Activitati
DELIMITER //
CREATE TRIGGER ModificareActivitati
AFTER UPDATE ON TipActivitate
FOR EACH ROW BEGIN
    UPDATE Calendar SET Ora = NEW.Data_Inceput, Data = NEW.Data_Inceput WHERE ID_Disciplina = NEW.ID_Tip_Activitate;
END //
DELIMITER ;

-- Trigger Absente Studenti
DELIMITER //
CREATE TRIGGER AbsenteStudenti
AFTER INSERT ON Note
FOR EACH ROW BEGIN
    UPDATE Studenti SET Ore_Sustinute = Ore_Sustinute + 1 WHERE ID_Student = NEW.ID_Student;
END //
DELIMITER ;

--
-- From Liviu ce mio propus chatu
--

-- Procedură pentru adăugarea unui student nou
DELIMITER //
CREATE PROCEDURE AdaugareStudent(
    IN p_ID_Student INT,
    IN p_An_Studiu INT,
    IN p_Ore_Sustinute INT,
    IN p_Tip_Utilizator VARCHAR(20),
    IN p_CNP VARCHAR(13),
    IN p_Nume VARCHAR(50),
    IN p_Prenume VARCHAR(50),
    IN p_Adresa VARCHAR(100),
    IN p_Telefon VARCHAR(15),
    IN p_Email VARCHAR(50),
    IN p_Cont_IBAN VARCHAR(30),
    IN p_Numar_Contract INT,
    OUT p_ResultCode INT
)
BEGIN
    DECLARE userCount INT;

    -- Verificare dacă CNP-ul este unic
    SELECT COUNT(*) INTO userCount FROM Utilizatori WHERE CNP = p_CNP;
    IF userCount > 0 THEN
        SET p_ResultCode = -1; -- Cod de eroare pentru CNP duplicat
        -- LEAVE Adaugare_Utilizator; -- Ieșire din procedură GIVES ERROR
    END IF;

    -- Alte verificări de validare pot fi adăugate aici...

    -- Adăugare utilizator în tabel
    INSERT INTO Utilizatori(Tip_Utilizator, CNP, Nume, Prenume, Adresa, Telefon, Email, Cont_IBAN, Numar_Contract)
    VALUES (p_Tip_Utilizator, p_CNP, p_Nume, p_Prenume, p_Adresa, p_Telefon, p_Email, p_Cont_IBAN, p_Numar_Contract);

    -- Adăugare student în tabel
    INSERT INTO Studenti(ID_Student, An_Studiu, Ore_Sustinute)
    VALUES (p_ID_Student, p_An_Studiu, p_Ore_Sustinute);

    SET p_ResultCode = 1; -- Cod de succes
END //
DELIMITER ;

-- Procedură pentru adăugarea unei note unui student
DELIMITER //
CREATE PROCEDURE AdaugareNota(
    IN p_ID_Nota INT,
    IN p_ID_Student INT,
    IN p_ID_Disciplina INT,
    IN p_Nota INT,
    OUT p_ResultCode INT
)
BEGIN
    -- Adăugare note în tabel
    INSERT INTO Note(ID_Nota, ID_Student, ID_Disciplina, Nota)
    VALUES (p_ID_Nota, p_ID_Student, p_ID_Disciplina, p_Nota);

    SET p_ResultCode = 1; -- Cod de succes
END //
DELIMITER ;

-- Declanșator care generează un log atunci când se creează un utilizator nou
DELIMITER //
CREATE TRIGGER LogUtilizatori
AFTER INSERT ON Utilizatori
FOR EACH ROW BEGIN
    INSERT INTO LogUtilizatori (ID_Utilizator, Tip_Utilizator, CNP, Nume, Prenume, Adresa, Telefon, Email, Cont_IBAN, Numar_Contract, Data_Creare)
    VALUES (NEW.ID_Utilizator, NEW.Tip_Utilizator, NEW.CNP, NEW.Nume, NEW.Prenume, NEW.Adresa, NEW.Telefon, NEW.Email, NEW.Cont_IBAN, NEW.Numar_Contract, NOW());
END //
DELIMITER ;

-- Declanșator care generează un log când se adaugă o notă
DELIMITER //
CREATE TRIGGER LogNote
AFTER INSERT ON Note
FOR EACH ROW BEGIN
    INSERT INTO LogNote (ID_Nota, ID_Student, ID_Disciplina, Nota, Data_Adaugare)
    VALUES (NEW.ID_Nota, NEW.ID_Student, NEW.ID_Disciplina, NEW.Nota, NOW());
END //
DELIMITER ;

-- Interogare pentru afișarea tuturor studenților înregistrați în baza de date
SELECT * FROM Studenti;

-- Interogare pentru afișarea informațiilor despre un student specific
SELECT * FROM Studenti WHERE ID_Student = ?;

-- Interogare pentru afișarea tuturor cursurilor la care este înscris un student
SELECT d.Nume_Disciplina FROM Discipline d
JOIN Inscrieri_Studenti_Disciplina i ON i.ID_Disciplina = d.ID_Disciplina
WHERE i.ID_Student = ?;

-- Interogare pentru calcularea notei finale a unui student dintr-un anumit curs
SELECT AVG(Nota) FROM Note WHERE ID_Student = ? AND ID_Disciplina = ?;
