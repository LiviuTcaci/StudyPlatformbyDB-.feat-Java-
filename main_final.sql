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

    ID_Utilizator INT PRIMARY KEY AUTO_INCREMENT,
    Parola VARCHAR(15),
    Nume_Utilizator VARCHAR(50) UNIQUE NOT NULL,
    Token_Autentificare VARCHAR(255),
    FOREIGN KEY (ID_Utilizator) REFERENCES Utilizatori (ID_Utilizator) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS SuperAdministratori
(
    ID_SuperAdministrator INT PRIMARY KEY AUTO_INCREMENT,
    ID_Utilizator INT,
    FOREIGN KEY (ID_Utilizator) REFERENCES Utilizatori (ID_Utilizator) ON DELETE CASCADE 
);

CREATE TABLE IF NOT EXISTS Administratori
(
    ID_Administrator INT PRIMARY KEY AUTO_INCREMENT,
    ID_Utilizator INT,
    FOREIGN KEY (ID_Utilizator) REFERENCES Utilizatori (ID_Utilizator) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Profesori
(
    ID_Profesor INT PRIMARY KEY AUTO_INCREMENT,
    ID_Utilizator INT,
    Departament ENUM ('Matematica','Fizica','Informatica','Chimie','Biologie','Istorie','Geografie') NOT NULL,
    Ore_Minim   INT,
    Ore_Maxim   INT,
    FOREIGN KEY (ID_Utilizator) REFERENCES Utilizatori (ID_Utilizator) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Studenti
(
    ID_Student    INT PRIMARY KEY AUTO_INCREMENT,
    ID_Utilizator INT,
    An_Studiu     INT,
    Ore_Sustinute INT,
    FOREIGN KEY (ID_Utilizator) REFERENCES Utilizatori (ID_Utilizator) ON DELETE CASCADE
);

# ------------------------------------------------------------------------------------------------------------------------------------------


CREATE TABLE IF NOT EXISTS Discipline
(
    ID_Disciplina        INT PRIMARY KEY AUTO_INCREMENT,
    Nume_Disciplina      VARCHAR(100),
    Descriere            TEXT,
    Departament ENUM ('Matematica','Fizica','Informatica','Chimie','Biologie','Istorie','Geografie') NOT NULL

);

CREATE TABLE IF NOT EXISTS Asociere_Disciplina_Profesor (

    ID_Asociere INT PRIMARY KEY AUTO_INCREMENT,
    ID_Disciplina INT,
    ID_Profesor INT,
    FOREIGN KEY (ID_Disciplina) REFERENCES Discipline(ID_Disciplina) ON DELETE CASCADE ,
    FOREIGN KEY (ID_Profesor) REFERENCES Profesori(ID_Profesor) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Inscriere_Student_Disciplina(

	ID_Inscriere INT PRIMARY KEY AUTO_INCREMENT,
    ID_Disciplina INT,
    ID_Student INT,
    FOREIGN KEY (ID_Disciplina) REFERENCES Discipline(ID_Disciplina) ON DELETE CASCADE,
    FOREIGN KEY (ID_Student) REFERENCES Studenti(ID_Student) ON DELETE CASCADE

);


CREATE TABLE IF NOT EXISTS Asociere_Student_Profesor(
	ID_Asociere INT PRIMARY KEY AUTO_INCREMENT,
    ID_Profesor INT,
    ID_Student INT,
    FOREIGN KEY (ID_Profesor) REFERENCES Profesori(ID_Profesor) ON DELETE CASCADE,
    FOREIGN KEY(ID_Student) REFERENCES Studenti(ID_Student) ON DELETE CASCADE,
    UNIQUE KEY unique_association (ID_Profesor, ID_Student)
);
    

CREATE TABLE IF NOT EXISTS Tip_Activitate (

    ID_Tip_Activitate INT PRIMARY KEY AUTO_INCREMENT,
    Nume_Tip_Activitate enum ('Curs','Seminar','Laborator','Examen') NOT NULL
);


CREATE TABLE IF NOT EXISTS Activitati(
	
    ID_Activitate INT PRIMARY KEY AUTO_INCREMENT,
    ID_Disciplina INT,
    ID_Profesor INT,
    ID_Tip_Activitate INT,
    Numar_Maxim_Studenti INT,
    FOREIGN KEY (ID_Disciplina) REFERENCES Discipline(ID_Disciplina) ON DELETE CASCADE,
    FOREIGN KEY (ID_Tip_Activitate) REFERENCES Tip_Activitate(ID_Tip_Activitate) ON DELETE CASCADE,
    FOREIGN KEY (ID_Profesor) REFERENCES Profesori(ID_Profesor) ON DELETE CASCADE

);


CREATE TABLE IF NOT EXISTS Inscriere_Student_Activitate(
    ID_Inscriere  INT PRIMARY KEY AUTO_INCREMENT,
    ID_Student    INT,
    ID_Activitate INT,
    FOREIGN KEY (ID_Student) REFERENCES Studenti (ID_Student) ON DELETE CASCADE,
    FOREIGN KEY (ID_Activitate) REFERENCES Activitati (ID_Activitate) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS Calendar
(
    ID_Calendar   INT PRIMARY KEY auto_increment,
    ID_Activitate INT,
    Ora_Inceput  TIME,
    Ora_Sfarsit  TIME,
    Data_Inceput DATE,
    Data_Sfarsit DATE,
    FOREIGN KEY(ID_Activitate) REFERENCES Activitati(ID_Activitate) ON DELETE CASCADE
    
);

CREATE TABLE IF NOT EXISTS Note (

    ID_Nota INT PRIMARY KEY AUTO_INCREMENT,
    ID_Student INT,
    ID_Disciplina INT,
    ID_Tip_Activitate INT,
    Nota INT,
    FOREIGN KEY (ID_Student) REFERENCES Studenti (ID_Student) ON DELETE CASCADE,
    FOREIGN KEY (ID_Disciplina) REFERENCES Discipline (ID_Disciplina) ON DELETE CASCADE,
    FOREIGN KEY (ID_Tip_Activitate) REFERENCES Tip_Activitate (ID_Tip_Activitate) ON DELETE CASCADE
);


# ----------------------------------------------------------------------------------------------------------------------------------------------


CREATE TABLE IF NOT EXISTS Grupuri_Studiu
(
    ID_Grup   INT PRIMARY KEY AUTO_INCREMENT,
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
    ID_Mesaj       INT PRIMARY KEY AUTO_INCREMENT,
    ID_Grup        INT,
    ID_Student     INT,
    Continut_Mesaj TEXT,
    FOREIGN KEY (ID_Grup) REFERENCES Grupuri_Studiu (ID_Grup) ON DELETE CASCADE,
    FOREIGN KEY (ID_Student) REFERENCES Studenti (ID_Student) ON DELETE CASCADE
); 


CREATE TABLE IF NOT EXISTS Activitati_Grup
(
    ID_Activitate_Grup       INT PRIMARY KEY AUTO_INCREMENT,
    ID_Grup                  INT,
    Nume_Activitate			 VARCHAR(50),
    Ora_Inceput  			 TIME,
    Ora_Sfarsit  			 TIME,
    Data 					 DATE,
    Numar_Minim_Participanti INT,
    Timp_Expirare            DATETIME,
    ID_Profesor				 INT,
    FOREIGN KEY (ID_Grup) REFERENCES Grupuri_Studiu (ID_Grup) ON DELETE CASCADE,
    FOREIGN KEY (ID_Profesor) REFERENCES Profesori (ID_Profesor) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Student_Activitate_Grup
(
    ID_Asociere        INT PRIMARY KEY AUTO_INCREMENT,
	ID_Activitate_Grup INT,
    ID_Student         INT,
    
    FOREIGN KEY (ID_Student) REFERENCES Studenti (ID_Student) ON DELETE CASCADE,
    FOREIGN KEY (ID_Activitate_Grup) REFERENCES Activitati_Grup (ID_Activitate_Grup) ON DELETE CASCADE

);


# ----------------------------------------------------------------------------------------------------------------------------------------------------------


DELIMITER //

CREATE PROCEDURE AdaugareUtilizator(
    IN pNume VARCHAR(255),
    IN pPrenume VARCHAR(255),
    IN pNumeUtilizator VARCHAR(255),
    IN pParola VARCHAR(255),
    IN pCNP VARCHAR(13),
    IN pTelefon VARCHAR(15),
    IN pAdresa VARCHAR(255),
    IN pEmail VARCHAR(255),
    IN pIBAN VARCHAR(34),
    IN pTipUtilizator VARCHAR(50),
    OUT pResultCode INT
)
BEGIN
    DECLARE idUtilizator INT;

    -- Verificăm dacă utilizatorul există deja
    IF (SELECT COUNT(*) FROM UtilizatoriAutentificare WHERE Nume_Utilizator = pNumeUtilizator) > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Utilizatorul există deja. Alegeți alt nume de utilizator.';
        SET pResultCode = 1;
    ELSE
        -- Adăugăm utilizatorul în tabelul Utilizatori
        INSERT INTO Utilizatori (Nume, Prenume, CNP, Telefon, Adresa, Email, Cont_IBAN, Tip_Utilizator, Numar_Contract)
        VALUES (pNume, pPrenume, pCNP, pTelefon, pAdresa, pEmail, pIBAN, pTipUtilizator, GetNextContractNumber());

        -- Obținem ID-ul utilizatorului inserat
        SET idUtilizator = LAST_INSERT_ID();

        -- Adăugăm datele de autentificare în tabelul UtilizatoriAutentificare
        INSERT INTO UtilizatoriAutentificare (ID_Utilizator, Nume_Utilizator, Parola)
        VALUES (idUtilizator, pNumeUtilizator, pParola);

        -- Adăugăm utilizatorul în tabela corespunzătoare tipului
        IF pTipUtilizator = 'Profesor' THEN
            INSERT INTO Profesori (ID_Utilizator) VALUES (idUtilizator);
        ELSEIF pTipUtilizator = 'Student' THEN
            INSERT INTO Studenti (ID_Utilizator) VALUES (idUtilizator);
        END IF;

        SET pResultCode = 0;
    END IF;
END //

DELIMITER ;






DELIMITER //

CREATE FUNCTION GetNextContractNumber() RETURNS INT DETERMINISTIC
BEGIN
    DECLARE nextContractNumber INT;

    -- Obținem cel mai mare număr de contract existent
    SELECT MAX(Numar_Contract) INTO nextContractNumber FROM Utilizatori;

    -- Dacă nu există niciun contract, începem de la 1, altfel incrementăm cu 1
    IF nextContractNumber IS NULL THEN
        SET nextContractNumber = 1;
    ELSE
        SET nextContractNumber = nextContractNumber + 1;
    END IF;

    RETURN nextContractNumber;
END //

DELIMITER ;



  
  
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
CREATE PROCEDURE AsociereCursProfesor(
    IN p_ID_Curs INT,
    IN p_ID_Profesor INT,
    OUT success INT
)
BEGIN
    DECLARE numarAsociere INT;
	DECLARE numarCurs INT;
    DECLARE numarProfesor INT;
	DECLARE departamentCurs VARCHAR(100); 
	DECLARE departamentProfesor VARCHAR(100); 

    -- Verificăm dacă există deja o asociere între disciplină și profesor
    SELECT COUNT(*) INTO numarAsociere
    FROM Asociere_Disciplina_Profesor
    WHERE ID_Disciplina = p_ID_Curs AND ID_Profesor = p_ID_Profesor;

    -- Dacă nu există deja o asociere, o creăm
    IF numarAsociere = 0 THEN
        -- Verificăm existența ID-urilor în tabelul Cursuri și Profesori

        SELECT COUNT(*), MAX(Departament) INTO numarCurs, departamentCurs FROM Discipline WHERE ID_Disciplina = p_ID_Curs;
        SELECT COUNT(*), MAX(Departament) INTO numarProfesor, departamentProfesor FROM Profesori WHERE ID_Profesor = p_ID_Profesor;

        -- Dacă ambele ID-uri există și aparțin aceluiași departament, facem asocierea
        IF numarCurs = 1 AND numarProfesor = 1 AND departamentCurs = departamentProfesor THEN
            -- Inserează o nouă înregistrare în tabelul Asociere_Disciplina_Profesor
            INSERT INTO Asociere_Disciplina_Profesor (ID_Disciplina, ID_Profesor) VALUES (p_ID_Curs, p_ID_Profesor);
            SET success = 1; -- 1 pentru succes
        ELSE
            SET success = 0; -- 0 pentru eșec
        END IF;
    ELSE
        SET success = -1; -- -1 pentru asociere deja existentă
    END IF;
END //
DELIMITER ;






DELIMITER //

CREATE PROCEDURE InscriereStudentDisciplinaProfesor(
    IN p_ID_Student INT,
    IN p_ID_Disciplina INT,
    IN p_ID_Profesor INT
)
BEGIN
    DECLARE existaStudent INT;
    DECLARE existaDisciplina INT;
    DECLARE esteInscris INT;
    DECLARE asociereProfesorDisciplina INT;
    DECLARE existaAsociere INT;

    -- Verificăm dacă există studentul, disciplina și profesorul
    SELECT COUNT(*) INTO existaStudent FROM Studenti WHERE ID_Student = p_ID_Student;
    SELECT COUNT(*) INTO existaDisciplina FROM Discipline WHERE ID_Disciplina = p_ID_Disciplina;
    SELECT COUNT(*) INTO asociereProfesorDisciplina FROM Asociere_Disciplina_Profesor WHERE ID_Disciplina = p_ID_Disciplina AND ID_Profesor = p_ID_Profesor;

    IF existaStudent = 1 AND existaDisciplina = 1 AND asociereProfesorDisciplina = 1 THEN
        -- Verificăm dacă studentul este deja înscris la această disciplină
        SELECT COUNT(*) INTO esteInscris
        FROM Inscriere_Student_Disciplina
        WHERE ID_Student = p_ID_Student AND ID_Disciplina = p_ID_Disciplina;

        IF esteInscris = 0 THEN
            -- Verificăm dacă există deja o asociere între profesor și student
            SELECT COUNT(*) INTO existaAsociere
            FROM Asociere_Student_Profesor
            WHERE ID_Student = p_ID_Student AND ID_Profesor = p_ID_Profesor;

                -- Înserăm în tabela de înregistrare a studenților la discipline
                INSERT INTO Inscriere_Student_Disciplina (ID_Disciplina, ID_Student)
                VALUES (p_ID_Disciplina, p_ID_Student);


                -- Inserăm automat studentul la toate activitățile programate de profesor la acea disciplină
                INSERT INTO Inscriere_Student_Activitate (ID_Student, ID_Activitate)
                SELECT p_ID_Student, A.ID_Activitate
                FROM Activitati A
                JOIN Asociere_Disciplina_Profesor ADP ON ADP.ID_Disciplina = p_ID_Disciplina AND ADP.ID_Profesor = p_ID_Profesor
                WHERE A.ID_Disciplina = p_ID_Disciplina
                  AND A.ID_Profesor = p_ID_Profesor
                  AND (p_ID_Student, A.ID_Activitate) NOT IN (SELECT ID_Student, ID_Activitate FROM Inscriere_Student_Activitate);

                SELECT 'Inscriere reusita' AS Mesaj;
                
			IF existaAsociere = 0 THEN
            
             -- Adăugăm asocierea între profesor și student în tabelul de asociere
                INSERT INTO Asociere_Student_Profesor (ID_Profesor, ID_Student)
                VALUES (p_ID_Profesor, p_ID_Student);
            
            END IF;
            
            
        ELSE
            SELECT 'Studentul este deja inscris la aceasta disciplina' AS Mesaj;
        END IF;
    ELSE
        SELECT 'Studentul sau disciplina sau profesorul nu exista sau nu este asociat cu disciplina' AS Mesaj;
    END IF;
END //

DELIMITER ;









DELIMITER //

CREATE PROCEDURE RenuntaStudentDisciplina(
    IN p_ID_Student INT,
    IN p_ID_Disciplina INT
)
BEGIN
    -- Verificăm existența ID-urilor în tabelul Studenti și Discipline
    IF (SELECT COUNT(*) FROM Studenti WHERE ID_Student = p_ID_Student) = 1 AND
       (SELECT COUNT(*) FROM Discipline WHERE ID_Disciplina = p_ID_Disciplina) = 1 THEN

        -- Dezabonăm studentul de la disciplină
        DELETE FROM Inscriere_Student_Disciplina WHERE ID_Student = p_ID_Student AND ID_Disciplina = p_ID_Disciplina;

        -- Ștergem înregistrările din tabela Inscriere_Student_Activitate pentru activitățile asociate disciplinei
        DELETE IA FROM Inscriere_Student_Activitate IA
        INNER JOIN Activitati A ON IA.ID_Activitate = A.ID_Activitate
        WHERE IA.ID_Student = p_ID_Student AND A.ID_Disciplina = p_ID_Disciplina;

        -- Verificăm dacă studentul mai are înregistrări în tabela Inscriere_Student_Disciplina pentru alte discipline cu același profesor
        IF (SELECT COUNT(*) FROM Inscriere_Student_Disciplina ISD
            INNER JOIN Activitati A ON ISD.ID_Disciplina = A.ID_Disciplina
            WHERE ISD.ID_Student = p_ID_Student AND A.ID_Profesor IN (SELECT ID_Profesor FROM Asociere_Disciplina_Profesor WHERE ID_Disciplina = p_ID_Disciplina)) = 0 THEN
            -- Ștergem înregistrările din tabela Asociere_Student_Profesor doar dacă studentul nu mai este înscris la nicio disciplină asociată aceluiași profesor
            DELETE FROM Asociere_Student_Profesor WHERE ID_Student = p_ID_Student;
        END IF;

        SELECT 'Renuntare reusita' AS Mesaj;
    ELSE
        SELECT 'Renuntare esuata. ID-uri invalide.' AS Mesaj;
    END IF;
END //

DELIMITER ;











DELIMITER //
CREATE PROCEDURE AdaugaStudent(
    IN anStudiu INT,
    IN oreSustinute INT,
    IN cnp VARCHAR(13),
    IN nume VARCHAR(50),
    IN prenume VARCHAR(50),
    IN adresa VARCHAR(100),
    IN telefon VARCHAR(20),
    IN email VARCHAR(50),
    IN contIBAN VARCHAR(30),
    IN parola VARCHAR(15),
    IN numeUtilizator VARCHAR(50)
)
BEGIN
    DECLARE lastUserID INT;

    -- Adaugă utilizator în tabela Utilizatori
    INSERT INTO Utilizatori (Tip_Utilizator, CNP, Nume, Prenume, Adresa, Telefon, Email, Cont_IBAN, Numar_Contract)
    VALUES ('Student', cnp, nume, prenume, adresa, telefon, email, contIBAN, GetNextContractNumber());

    -- Obține ID-ul utilizatorului adăugat
    SET lastUserID = LAST_INSERT_ID();

    -- Adaugă utilizator în tabela UtilizatoriAutentificare
    INSERT INTO UtilizatoriAutentificare (Parola, Nume_Utilizator)
    VALUES (parola, numeUtilizator);

    -- Adaugă student în tabela Studenti
    INSERT INTO Studenti (ID_Utilizator, An_Studiu, Ore_Sustinute)
    VALUES (lastUserID, anStudiu, oreSustinute);
END //
DELIMITER ;



DELIMITER //
CREATE PROCEDURE AdaugaProfesor(
    IN departament ENUM ('Matematica','Fizica','Informatica','Chimie','Biologie','Istorie','Geografie'),
    IN oreMinim INT,
    IN oreMaxim INT,
    IN cnp VARCHAR(13),
    IN nume VARCHAR(50),
    IN prenume VARCHAR(50),
    IN adresa VARCHAR(100),
    IN telefon VARCHAR(15),
    IN email VARCHAR(50),
    IN contIBAN VARCHAR(30),
    IN parola VARCHAR(15),
    IN numeUtilizator VARCHAR(50)
)
BEGIN
    DECLARE lastUserID INT;

    -- Adaugă utilizator în tabela Utilizatori
    INSERT INTO Utilizatori (Tip_Utilizator, CNP, Nume, Prenume, Adresa, Telefon, Email, Cont_IBAN, Numar_Contract)
    VALUES ('Profesor', cnp, nume, prenume, adresa, telefon, email, contIBAN, GetNextContractNumber());

    -- Obține ID-ul utilizatorului adăugat
    SET lastUserID = LAST_INSERT_ID();

    -- Adaugă utilizator în tabela UtilizatoriAutentificare
    INSERT INTO UtilizatoriAutentificare (Parola, Nume_Utilizator)
    VALUES (parola, numeUtilizator);

    -- Adaugă profesor în tabela Profesori
    INSERT INTO Profesori (ID_Utilizator, Departament, Ore_Minim, Ore_Maxim)
    VALUES (lastUserID, departament, oreMinim, oreMaxim);
END //
DELIMITER ;




DELIMITER //

CREATE PROCEDURE StergereUtilizator(IN p_ID_Utilizator INT)
BEGIN
    DECLARE numeUtilizator VARCHAR(50);
    DECLARE tipUtilizator ENUM('Student', 'Profesor', 'SuperAdministrator', 'Administrator');

 -- Șterge utilizatorul din tabela Utilizatori
    DELETE FROM Utilizatori WHERE ID_Utilizator = p_ID_Utilizator;
    
    
    -- Șterge utilizatorul din tabela corespunzătoare tipului său
    CASE
        WHEN tipUtilizator = 'Student' THEN
            DELETE FROM Studenti WHERE ID_Student = p_ID_Utilizator;
        WHEN tipUtilizator = 'Profesor' THEN
            DELETE FROM Profesori WHERE ID_Profesor = p_ID_Utilizator;
        WHEN tipUtilizator = 'SuperAdministrator' THEN
            DELETE FROM SuperAdministratori WHERE ID_SuperAdministrator = p_ID_Utilizator;
        WHEN tipUtilizator = 'Administrator' THEN
            DELETE FROM Administratori WHERE ID_Administrator = p_ID_Utilizator;
        ELSE
            -- Adăugați o clauză ELSE pentru a trata orice alt scenariu neașteptat
            SELECT 'Utilizatorul a fost sters cu succes!' AS Mesaj;
    END CASE;

   

    SELECT 'Utilizatorul a fost sters cu succes.' AS Mesaj;
END //

DELIMITER ;

DELIMITER //
CREATE PROCEDURE AdaugaAdministrator(
    IN cnp VARCHAR(13),
    IN nume VARCHAR(50),
    IN prenume VARCHAR(50),
    IN adresa VARCHAR(100),
    IN telefon VARCHAR(20),
    IN email VARCHAR(50),
    IN contIBAN VARCHAR(30),
    IN parola VARCHAR(15),
    IN numeUtilizator VARCHAR(50)
)
BEGIN
    DECLARE lastUserID INT;
    DECLARE nextContractNumber INT;

    -- Obține următorul număr de contract
    SET nextContractNumber = GetNextContractNumber();

    -- Adaugă utilizator în tabela Utilizatori
    INSERT INTO Utilizatori (Tip_Utilizator, CNP, Nume, Prenume, Adresa, Telefon, Email, Cont_IBAN, Numar_Contract)
    VALUES ('Administrator', cnp, nume, prenume, adresa, telefon, email, contIBAN, nextContractNumber);

    -- Obține ID-ul utilizatorului adăugat
    SET lastUserID = LAST_INSERT_ID();

    -- Adaugă utilizator în tabela UtilizatoriAutentificare
    INSERT INTO UtilizatoriAutentificare (Parola, Nume_Utilizator)
    VALUES (parola, numeUtilizator);

    -- Adaugă administrator în tabela Administratori
    INSERT INTO Administratori (ID_Utilizator)
    VALUES (lastUserID);
END //
DELIMITER ;


DELIMITER //

CREATE PROCEDURE ProgramareActivitate(
    IN p_ID_Profesor INT,
    IN p_ID_Disciplina INT,
    IN p_Nume_Activitate ENUM('Curs', 'Seminar', 'Laborator', 'Examen'),
    IN p_Data_Inceput DATETIME,
    IN p_Ora_Inceput TIME,
    IN p_Ora_Sfarsit TIME,
    IN p_Data_Sfarsit DATETIME,
    IN p_Numar_Maxim_Studenti INT
)
BEGIN
    DECLARE p_ID_Tip_Activitate INT;
    DECLARE p_ID_Activitate INT;

    -- Obține ID-ul tipului de activitate
    SELECT ID_Tip_Activitate INTO p_ID_Tip_Activitate
    FROM Tip_Activitate
    WHERE Nume_Tip_Activitate = p_Nume_Activitate
    LIMIT 1;

    -- Verifică dacă data de început a activității este în viitor
    IF p_Data_Inceput > NOW() THEN
        -- Verifică dacă data de început este mai mică decât data de sfârșit
        IF p_Data_Inceput < p_Data_Sfarsit OR (p_Data_Inceput = p_Data_Sfarsit AND p_Ora_Inceput < p_Ora_Sfarsit) THEN
            -- Verifică dacă există suprapunere cu activitățile existente în aceeași zi și interval orar
            IF NOT EXISTS (
                SELECT 1
                FROM Calendar C
                JOIN Activitati A ON C.ID_Activitate = A.ID_Activitate
                WHERE C.Data_Inceput = p_Data_Inceput
                  AND ((C.Ora_Inceput >= p_Ora_Inceput AND C.Ora_Inceput < p_Ora_Sfarsit) OR (C.Ora_Sfarsit > p_Ora_Inceput AND C.Ora_Sfarsit <= p_Ora_Sfarsit))
            ) THEN
                -- Inserează activitatea în tabela Activitati
                INSERT INTO Activitati (ID_Disciplina, ID_Profesor, ID_Tip_Activitate, Numar_Maxim_Studenti)
                VALUES (p_ID_Disciplina, p_ID_Profesor, p_ID_Tip_Activitate, p_Numar_Maxim_Studenti);

                SET p_ID_Activitate = LAST_INSERT_ID();

                -- Inserează activitatea în tabela Calendar
                INSERT INTO Calendar (ID_Activitate, Ora_Inceput, Ora_Sfarsit, Data_Inceput, Data_Sfarsit)
                VALUES (p_ID_Activitate, p_Ora_Inceput, p_Ora_Sfarsit, p_Data_Inceput, p_Data_Sfarsit);

                SELECT 'Activitatea a fost programată cu succes.' AS Mesaj;
            ELSE
                SELECT 'Suprapunere cu alte activități în aceeași zi și interval orar.' AS Mesaj;
            END IF;
        ELSE
            SELECT 'Data și ora de început trebuie să fie mai devreme decât data și ora de sfârșit.' AS Mesaj;
        END IF;
    ELSE
        SELECT 'Nu se poate programa o activitate în trecut.' AS Mesaj;
    END IF;
END //

DELIMITER ;


















DELIMITER //

CREATE PROCEDURE ModificaInformatiiUtilizator(
    IN idUtilizator INT,
    IN nouaAdresa VARCHAR(100),
    IN noulTelefon VARCHAR(15),
    IN noulEmail VARCHAR(50),
    IN noulContIBAN VARCHAR(30),
    IN oreMinim INT,
    IN oreMaxim INT,
    IN anStudiu INT,
    IN oreSustinute INT
)
BEGIN
    DECLARE tipUtilizator ENUM('Student', 'Profesor', 'Administrator', 'SuperAdministrator') DEFAULT NULL;

    -- Obține tipul utilizatorului
    SELECT Tip_Utilizator INTO tipUtilizator
    FROM Utilizatori
    WHERE ID_Utilizator = idUtilizator;

    IF tipUtilizator IS NOT NULL THEN
       
        -- Modifică detaliile comune utilizatorului
        UPDATE Utilizatori
        SET Adresa = nouaAdresa,
            Telefon = noulTelefon,
            Email = noulEmail,
            Cont_IBAN = noulContIBAN
        WHERE ID_Utilizator = idUtilizator;

        -- Verifică tipul utilizatorului și aplică modificările specifice
        CASE
            WHEN tipUtilizator = 'Profesor' THEN

                -- Actualizează detaliile specifice profesorului
                UPDATE Profesori
                SET Ore_Minim = oreMinim,
                    Ore_Maxim = oreMaxim
                WHERE ID_Utilizator = idUtilizator;
                
                
            WHEN tipUtilizator = 'Student' THEN
            
                UPDATE Studenti
                SET An_Studiu = anStudiu,
                    Ore_Sustinute = oreSustinute
                WHERE ID_Utilizator = idUtilizator;
                
            WHEN tipUtilizator = 'Administrator' THEN
                SELECT 'Modificare reusita' AS Mesaj;
            WHEN tipUtilizator = 'SuperAdministrator' THEN
                SELECT 'Modificare reusita' AS Mesaj;
            ELSE
                SELECT 'Utilizatorul nu exista' AS Mesaj;
        END CASE;

        SELECT 'Modificare reusita' AS Mesaj;
    ELSE
        SELECT 'Utilizatorul nu exista' AS Mesaj;
    END IF;
END //

DELIMITER ;








DELIMITER //

CREATE PROCEDURE ProgramareActivitateGrup(
    IN p_ID_Grup INT,
    IN p_Nume_Activitate VARCHAR(50),
    IN p_Ora_Inceput TIME,
    IN p_Ora_Sfarsit TIME,
    IN p_Data DATE,
    IN p_Numar_Minim_Participanti INT,
    IN p_Timp_Expirare DATETIME,
    IN p_ID_Profesor INT,
    OUT p_ResultCode INT
)
BEGIN
    DECLARE numarMembri INT;
    DECLARE numarActivitati INT;
    DECLARE departamentGrup VARCHAR(50);
    DECLARE disciplinaGrup VARCHAR(50);
    DECLARE departamentProfesor VARCHAR(50);
    DECLARE disciplinaProfesor VARCHAR(50);
    DECLARE numarProfesoriValidi INT;

    -- Obține departamentul și disciplina asociate grupului
    SELECT D.Departament INTO departamentGrup
    FROM Grupuri_Studiu GS
    JOIN Discipline D ON GS.ID_Disciplina = D.ID_Disciplina
    WHERE GS.ID_Grup = p_ID_Grup
    LIMIT 1;

    -- Obține departamentul și disciplina asociate profesorului (dacă este specificat)
    IF p_ID_Profesor IS NOT NULL THEN
        SELECT P.Departament INTO departamentProfesor
        FROM Profesori P
        JOIN Discipline D ON P.Departament = D.Departament
        WHERE P.ID_Profesor = p_ID_Profesor
        LIMIT 1;
    ELSE
        SET departamentProfesor = NULL;
    END IF;

    -- Verifică existența grupului și a membrilor
    SELECT COUNT(*) INTO numarMembri
    FROM Membri_Grup
    WHERE ID_Grup = p_ID_Grup;

    -- Verifică dacă există deja o activitate programată pentru acel grup la acea dată și oră
    SELECT COUNT(*) INTO numarActivitati
    FROM Activitati_Grup
    WHERE ID_Grup = p_ID_Grup AND Data = p_Data AND 
          ((Ora_Inceput BETWEEN p_Ora_Inceput AND p_Ora_Sfarsit) OR 
           (Ora_Sfarsit BETWEEN p_Ora_Inceput AND p_Ora_Sfarsit));

    -- Verifică dacă profesorul (dacă este specificat) aparține departamentului și predă disciplina corespunzătoare
    IF p_ID_Profesor IS NOT NULL THEN
        SELECT COUNT(*) INTO numarProfesoriValidi
        FROM Profesori P
        WHERE P.ID_Profesor = p_ID_Profesor AND P.Departament = departamentGrup;
    ELSE
        SET numarProfesoriValidi = 1; -- Ignorăm verificarea profesorului dacă nu este specificat
    END IF;

    -- Dacă există suficienți membri, nu există o activitate deja programată și profesorul (dacă este specificat) este valid
    IF numarMembri >= p_Numar_Minim_Participanti AND numarActivitati = 0 AND numarProfesoriValidi > 0 THEN
        -- Inserează o nouă înregistrare în tabela Activitati_Grup
        INSERT INTO Activitati_Grup (ID_Grup, Nume_Activitate, Ora_Inceput, Ora_Sfarsit, Data, Numar_Minim_Participanti, Timp_Expirare, ID_Profesor)
        VALUES (p_ID_Grup, p_Nume_Activitate, p_Ora_Inceput, p_Ora_Sfarsit, p_Data, p_Numar_Minim_Participanti, p_Timp_Expirare, p_ID_Profesor);

        SET p_ResultCode = 0; -- Programare activitate reușită
    ELSE
        -- Setează codul de rezultat corespunzător
        IF numarMembri < p_Numar_Minim_Participanti THEN
            SET p_ResultCode = 1; -- Nu sunt suficienți membri
        ELSEIF numarActivitati > 0 THEN
            SET p_ResultCode = 2; -- Există deja o activitate programată în același interval orar
        ELSE
            SET p_ResultCode = 3; -- Profesorul nu este valid
        END IF;
    END IF;
END //

DELIMITER ;










DELIMITER //

CREATE PROCEDURE InscriereStudentGrupStudiu(
    IN p_ID_Student INT,
    IN p_ID_GrupStudiu INT
)
BEGIN
    DECLARE numarInscrisi INT;

    -- Verifică dacă există înregistrări pentru student și disciplină în tabelul Inscriere_Student_Disciplina
    SELECT COUNT(*) INTO numarInscrisi
    FROM Inscriere_Student_Disciplina
    WHERE ID_Student = p_ID_Student AND ID_Disciplina IN (SELECT ID_Disciplina FROM Grupuri_Studiu WHERE ID_Grup = p_ID_GrupStudiu);

    -- Dacă găsim înregistrări, procedăm cu înregistrarea în tabelul Membri_Grup
    IF numarInscrisi > 0 THEN
        -- Verificăm dacă studentul este deja înscris în grupul de studiu
        SELECT COUNT(*) INTO numarInscrisi
        FROM Membri_Grup
        WHERE ID_Student = p_ID_Student AND ID_Grup = p_ID_GrupStudiu;

        -- Dacă studentul nu este deja înscris, îl înregistram în tabelul Membri_Grup
        IF numarInscrisi = 0 THEN
            -- Înscrie studentul în grupul de studiu
            INSERT INTO Membri_Grup (ID_Student, ID_Grup) VALUES (p_ID_Student, p_ID_GrupStudiu);
            SELECT 'Inscriere in grupul de studiu reușită.' AS Mesaj;
        ELSE
            SELECT 'Studentul este deja înscris în acest grup de studiu.' AS Mesaj;
        END IF;
    ELSE
        SELECT 'Studentul nu este înscris la disciplina asociată acestui grup de studiu.' AS Mesaj;
    END IF;
END //

DELIMITER ;



DELIMITER //

CREATE PROCEDURE RenuntaStudentGrupStudiu(
    IN p_ID_Student INT,
    IN p_ID_GrupStudiu INT
)
BEGIN
    DECLARE numarInscrisi INT;

    -- Verifică dacă studentul este înscris în grupul de studiu
    SELECT COUNT(*) INTO numarInscrisi
    FROM Membri_Grup
    WHERE ID_Student = p_ID_Student AND ID_Grup = p_ID_GrupStudiu;

    -- Dacă studentul este înscris, îl scoate din grupul de studiu
    IF numarInscrisi > 0 THEN
        -- Scoate studentul din grupul de studiu
        DELETE FROM Membri_Grup WHERE ID_Student = p_ID_Student AND ID_Grup = p_ID_GrupStudiu;
        SELECT 'Iesire din grupul de studiu reusita.' AS Mesaj;
    ELSE
        SELECT 'Studentul nu este inscris in acest grup de studiu.' AS Mesaj;
    END IF;
END //

DELIMITER ;




DELIMITER //

CREATE PROCEDURE TrimiteMesajGrup(
    IN p_ID_Student INT,
    IN p_ID_GrupStudiu INT,
    IN p_ContinutMesaj TEXT
)
BEGIN
    DECLARE numarInscrisi INT;

    -- Verifică dacă studentul este înscris în grupul de studiu
    SELECT COUNT(*) INTO numarInscrisi
    FROM Membri_Grup
    WHERE ID_Student = p_ID_Student AND ID_Grup = p_ID_GrupStudiu;

    -- Dacă studentul este înscris, trimite mesajul pe grup
    IF numarInscrisi > 0 THEN
        -- Trimite mesajul pe grup
        INSERT INTO Mesaje_Grup (ID_Student, ID_Grup, Continut_Mesaj)
        VALUES (p_ID_Student, p_ID_GrupStudiu, p_ContinutMesaj);

        SELECT 'Mesaj trimis cu succes pe grup.' AS Mesaj;
    ELSE
        SELECT 'Studentul nu este inscris in acest grup de studiu.' AS Mesaj;
    END IF;
END //

DELIMITER ;




DELIMITER //

CREATE PROCEDURE AfisareDisciplineStudent(
    IN p_ID_Student INT
)
BEGIN
    -- Afișează disciplinele în care studentul este înscris
    SELECT D.ID_Disciplina, D.Nume_Disciplina, D.Descriere, 'Inscris' AS Status
    FROM Discipline D
    INNER JOIN Inscriere_Student_Disciplina ISD ON D.ID_Disciplina = ISD.ID_Disciplina
    WHERE ISD.ID_Student = p_ID_Student

    UNION

    -- Afișează disciplinele în care studentul nu este înscris
    SELECT D.ID_Disciplina, D.Nume_Disciplina, D.Descriere, 'Nu este inscris' AS Status
    FROM Discipline D
    WHERE D.ID_Disciplina NOT IN (
        SELECT ID_Disciplina
        FROM Inscriere_Student_Disciplina
        WHERE ID_Student = p_ID_Student
    );
END //

DELIMITER ;




DELIMITER //

CREATE PROCEDURE AfisareActivitatiStudent(
    IN p_ID_Student INT
)
BEGIN
    -- Afișează activitățile la care studentul este înscris, incluzând numele și prenumele profesorului
    SELECT A.ID_Activitate, D.Nume_Disciplina, TA.Nume_Tip_Activitate, C.Data_Inceput, C.Ora_Inceput, C.Data_Sfarsit, C.Ora_Sfarsit, U.Nume AS Nume_Profesor, U.Prenume AS Prenume_Profesor
    FROM Activitati A
    INNER JOIN Inscriere_Student_Activitate ISA ON A.ID_Activitate = ISA.ID_Activitate
    JOIN Discipline D ON D.ID_Disciplina = A.ID_Disciplina
    JOIN Tip_Activitate TA ON TA.ID_Tip_Activitate = A.ID_Tip_Activitate
    JOIN Calendar C ON A.ID_Activitate = C.ID_Activitate
    
    JOIN Asociere_Disciplina_Profesor ADP ON A.ID_Disciplina = ADP.ID_Disciplina
    JOIN Inscriere_Student_Disciplina ISD ON ISD.ID_Disciplina = A.ID_Disciplina
    JOIN Studenti S ON S.ID_Student = ISD.ID_Student
	JOIN Profesori P ON ADP.ID_Profesor = P.ID_Profesor AND P.ID_Profesor = A.ID_Profesor
	JOIN Utilizatori U ON U.ID_Utilizator = P.ID_Utilizator
	JOIN Asociere_Student_Profesor ASP ON p_ID_Student = ASP.ID_Student AND ADP.ID_Profesor = ASP.ID_Profesor

    WHERE ISA.ID_Student = p_ID_Student;
    
END //

DELIMITER ;



DELIMITER //

CREATE PROCEDURE AdaugareNotaStudent(
    IN p_ID_Student INT,
    IN p_ID_Disciplina INT,
    IN p_ID_Tip_Activitate INT,
    IN p_Nota INT,
    IN p_ID_Profesor INT
)
BEGIN
    -- Verificăm dacă studentul este înscris la disciplina specificată
    DECLARE numarInscriere INT;
    DECLARE numarActivitate INT;
    DECLARE numarAsociereProfesor INT;

    SELECT COUNT(*) INTO numarInscriere
    FROM Inscriere_Student_Disciplina ISD
    WHERE ISD.ID_Student = p_ID_Student AND ISD.ID_Disciplina = p_ID_Disciplina;

    IF numarInscriere = 1 THEN
        -- Verificăm dacă există activitatea specificată pentru disciplina și profesorul respectiv
			SELECT COUNT(*) INTO numarActivitate
			FROM Activitati A
			WHERE A.ID_Disciplina = p_ID_Disciplina AND A.ID_Tip_Activitate = p_ID_Tip_Activitate AND A.ID_Profesor = p_ID_Profesor;


        IF numarActivitate = 1 THEN
            -- Verificăm dacă există asociere între profesor și disciplină
            SELECT COUNT(*) INTO numarAsociereProfesor
            FROM Asociere_Disciplina_Profesor ADP
            WHERE ADP.ID_Disciplina = p_ID_Disciplina AND ADP.ID_Profesor = p_ID_Profesor;

            IF numarAsociereProfesor = 1 THEN
                -- Adăugăm o nouă notă pentru student
                INSERT INTO Note (ID_Student, ID_Disciplina, ID_Tip_Activitate, Nota)
                VALUES (p_ID_Student, p_ID_Disciplina, p_ID_Tip_Activitate, p_Nota);

                SELECT 'Nota a fost adaugata cu succes.' AS Mesaj;
            ELSE
                SELECT 'Adaugare nota esuata. Nu exista asociere intre profesor si disciplina.' AS Mesaj;
            END IF;
        ELSE
            SELECT 'Adaugare nota esuata. Activitatea specificata nu exista pentru disciplina data.' AS Mesaj;
        END IF;
    ELSE
        SELECT 'Adaugare nota esuata. Studentul nu este inscris la disciplina specificata.' AS Mesaj;
    END IF;
END //

DELIMITER ;









DELIMITER //

CREATE PROCEDURE CalculNotaFinala(
    IN p_ID_Student INT,
    IN p_ID_Disciplina INT,
    IN p_ProcentCurs INT,
    IN p_ProcentSeminar INT,
    IN p_ProcentLaborator INT,
    IN p_ID_Profesor INT
)
BEGIN
    DECLARE mediaCurs DECIMAL(5,2);
    DECLARE mediaSeminar DECIMAL(5,2);
    DECLARE mediaLaborator DECIMAL(5,2);
    DECLARE notaFinala DECIMAL(5,2);
    DECLARE numarNotaFinala INT;
    DECLARE numarAsociereProfesor INT;

    -- Verificăm dacă studentul este înscris la disciplina specificată
    DECLARE numarInscriere INT;
    SELECT COUNT(*) INTO numarInscriere
    FROM Inscriere_Student_Disciplina
    WHERE ID_Student = p_ID_Student AND ID_Disciplina = p_ID_Disciplina;

    IF numarInscriere = 1 THEN
        -- Verificăm dacă există asociere între profesor și student
        SELECT COUNT(*) INTO numarAsociereProfesor
        FROM Asociere_Student_Profesor ASP
        WHERE ASP.ID_Student = p_ID_Student AND ASP.ID_Profesor = p_ID_Profesor;

        IF numarAsociereProfesor = 1 THEN
            -- Calculăm media notelor pentru curs, seminar și laborator
            SELECT COALESCE(AVG(Nota), 0) INTO mediaCurs
            FROM Note
            WHERE ID_Student = p_ID_Student AND ID_Disciplina = p_ID_Disciplina AND ID_Tip_Activitate = 1; -- ID pentru curs

            SELECT COALESCE(AVG(Nota), 0) INTO mediaSeminar
            FROM Note
            WHERE ID_Student = p_ID_Student AND ID_Disciplina = p_ID_Disciplina AND ID_Tip_Activitate = 2; -- ID pentru seminar

            SELECT COALESCE(AVG(Nota), 0) INTO mediaLaborator
            FROM Note
            WHERE ID_Student = p_ID_Student AND ID_Disciplina = p_ID_Disciplina AND ID_Tip_Activitate = 3; -- ID pentru laborator

            -- Calculăm nota finală
            SET notaFinala = 
                (p_ProcentCurs / 100) * mediaCurs + 
                (p_ProcentSeminar / 100) * mediaSeminar + 
                (p_ProcentLaborator / 100) * mediaLaborator;

            -- Verificăm dacă există deja o înregistrare pentru nota finală în tabelul Note
            SELECT COUNT(*) INTO numarNotaFinala
            FROM Note
            WHERE ID_Student = p_ID_Student AND ID_Disciplina = p_ID_Disciplina AND ID_Tip_Activitate = 4; -- ID pentru examen

            -- Actualizăm sau inserăm nota finală în tabelul Note
            IF numarNotaFinala = 1 THEN
                UPDATE Note
                SET Nota = notaFinala
                WHERE ID_Student = p_ID_Student AND ID_Disciplina = p_ID_Disciplina AND ID_Tip_Activitate = 4; -- ID pentru examen
            ELSE
                INSERT INTO Note (ID_Student, ID_Disciplina, ID_Tip_Activitate, Nota)
                VALUES (p_ID_Student, p_ID_Disciplina, 4, notaFinala); -- 4 reprezintă ID-ul pentru examen
            END IF;

            SELECT notaFinala AS Nota_Finala;
        ELSE
            SELECT 'Calculul notei finale esuat. Nu exista asociere intre profesor si student.' AS Mesaj;
        END IF;
    ELSE
        SELECT 'Calculul notei finale esuat. Studentul nu este inscris la disciplina specificata.' AS Mesaj;
    END IF;
END //

DELIMITER ;






DELIMITER //

CREATE PROCEDURE InscriereStudentActivitateGrup(
    IN p_ID_Student INT,
    IN p_ID_Activitate_Grup INT
)
BEGIN
    DECLARE numarInscrisi INT;
    DECLARE numarActivitatiOcupate INT;
    DECLARE oraInceputActivitate TIME;
    DECLARE oraSfarsitActivitate TIME;
    DECLARE dataActivitate DATE;

    -- Verificăm dacă studentul este deja înscris la acea activitate de grup
    SELECT COUNT(*) INTO numarInscrisi
    FROM Student_Activitate_Grup
    WHERE ID_Student = p_ID_Student AND ID_Activitate_Grup = p_ID_Activitate_Grup;

    -- Obținem detaliile activității de grup
    SELECT Ora_Inceput, Ora_Sfarsit, Data
    INTO oraInceputActivitate, oraSfarsitActivitate, dataActivitate
    FROM Activitati_Grup
    WHERE ID_Activitate_Grup = p_ID_Activitate_Grup;

    -- Verificăm dacă studentul este ocupat cu o altă activitate în același interval de timp
    SELECT COUNT(*) INTO numarActivitatiOcupate
	FROM Calendar
	WHERE ID_Activitate = p_ID_Activitate_Grup
	AND (
		(dataActivitate = Data_Inceput AND WEEKDAY(dataActivitate) = WEEKDAY(Data_Inceput))
	)
	AND (
		(oraInceputActivitate BETWEEN Ora_Inceput AND Ora_Sfarsit) OR
		(oraSfarsitActivitate BETWEEN Ora_Inceput AND Ora_Sfarsit)
	);


    -- Dacă studentul nu este deja înscris și nu este ocupat cu o altă activitate
    IF numarInscrisi = 0 AND numarActivitatiOcupate = 0 THEN
        -- Inserează o nouă înregistrare în tabela Student_Activitate_Grup
        INSERT INTO Student_Activitate_Grup (ID_Student, ID_Activitate_Grup)
        VALUES (p_ID_Student, p_ID_Activitate_Grup);

        SELECT 'Inscriere la activitatea de grup reusita' AS Mesaj;
    ELSE
        SELECT 'Studentul este deja inscris la aceasta activitate de grup sau este ocupat cu o alta activitate in acelasi interval de timp.' AS Mesaj;
    END IF;
END //

DELIMITER ;







DELIMITER //

CREATE PROCEDURE RenuntareActivitateGrup(
    IN p_ID_Student INT,
    IN p_ID_Activitate_Grup INT
)
BEGIN
    DECLARE numarInscrisi INT;

    -- Verificăm dacă studentul este înscris la acea activitate de grup
    SELECT COUNT(*) INTO numarInscrisi
    FROM Student_Activitate_Grup
    WHERE ID_Student = p_ID_Student AND ID_Activitate_Grup = p_ID_Activitate_Grup;

    -- Dacă studentul este înscris, îl eliminăm din tabela Student_Activitate_Grup
    IF numarInscrisi > 0 THEN
        DELETE FROM Student_Activitate_Grup
        WHERE ID_Student = p_ID_Student AND ID_Activitate_Grup = p_ID_Activitate_Grup;

        SELECT 'Renuntare la activitatea de grup reusita' AS Mesaj;
    ELSE
        SELECT 'Studentul nu este inscris la aceasta activitate de grup.' AS Mesaj;
    END IF;
END //

DELIMITER ;



DELIMITER //

CREATE PROCEDURE VizualizareActivitatiGrup(
    IN p_ID_Student INT
)
BEGIN
    -- Selectăm informațiile despre activitățile de grup la care este înscris studentul
    SELECT AG.ID_Activitate_Grup, AG.Nume_Activitate, AG.Ora_Inceput, AG.Ora_Sfarsit, AG.Data, AG.Numar_Minim_Participanti, AG.Timp_Expirare
    FROM Activitati_Grup AG
    INNER JOIN Student_Activitate_Grup SAG ON AG.ID_Activitate_Grup = SAG.ID_Activitate_Grup
    WHERE SAG.ID_Student = p_ID_Student;
END //

DELIMITER ;





DELIMITER //

CREATE TRIGGER InscriereAutomataStudenti
AFTER INSERT ON Activitati
FOR EACH ROW
BEGIN
    DECLARE ID_Activitate INT;
    DECLARE ID_Disciplina INT;
    DECLARE ID_Profesor INT;

    SET ID_Activitate = NEW.ID_Activitate;
    SET ID_Disciplina = NEW.ID_Disciplina;
    SET ID_Profesor = NEW.ID_Profesor;

    -- Inserează studentii inscrisi la disciplina respectiva si la profesorul respectiv in tabela Inscriere_Student_Activitate
    INSERT INTO Inscriere_Student_Activitate (ID_Student, ID_Activitate)
    SELECT ISAD.ID_Student, ID_Activitate
    FROM Inscriere_Student_Disciplina ISAD
    JOIN Asociere_Student_Profesor ASP ON ISAD.ID_Student = ASP.ID_Student
    WHERE ASP.ID_Profesor = ID_Profesor AND ISAD.ID_Disciplina = ID_Disciplina;
END //

DELIMITER ;






DELIMITER //

CREATE TRIGGER RenuntareStudentDisciplina
AFTER DELETE ON Inscriere_Student_Disciplina
FOR EACH ROW
BEGIN
    DECLARE id_student INT;
    DECLARE id_disciplina INT;

    -- Obține ID-urile studentului și disciplinei afectate
    SET id_student = OLD.ID_Student;
    SET id_disciplina = OLD.ID_Disciplina;

    -- Elimină studentul din toate grupurile de studiu asociate disciplinei
    DELETE MG
    FROM Membri_Grup MG
    JOIN Grupuri_Studiu GS ON MG.ID_Grup = GS.ID_Grup
    WHERE GS.ID_Disciplina = id_disciplina AND MG.ID_Student = id_student;

    -- Șterge mesajele trimise de către student în grupurile asociate disciplinei
    DELETE MG
    FROM Mesaje_Grup MG
    WHERE MG.ID_Grup IN (
        SELECT ID_Grup
        FROM Grupuri_Studiu
        WHERE Grupuri_Studiu.ID_Disciplina = id_disciplina
    ) AND MG.ID_Student = id_student;

    -- Șterge notele acelui student la disciplina respectivă
    DELETE N
    FROM Note N
    WHERE N.ID_Student = id_student AND N.ID_Disciplina = id_disciplina;
END;

//

DELIMITER ;







DELIMITER //

CREATE TRIGGER VerificareNumarMinimStudenti
BEFORE INSERT ON Student_Activitate_Grup
FOR EACH ROW
BEGIN
    DECLARE numarInscrisi INT;
    DECLARE numarMinimParticipanti INT;
    DECLARE timpExpirare DATETIME;

    -- Obține numărul curent de studenți înscriși la activitatea de grup
    SELECT COUNT(*) INTO numarInscrisi
    FROM Student_Activitate_Grup
    WHERE ID_Activitate_Grup = NEW.ID_Activitate_Grup;

    -- Obține numărul minim de participanți pentru activitatea de grup
    SELECT Numar_Minim_Participanti, Timp_Expirare
    INTO numarMinimParticipanti, timpExpirare
    FROM Activitati_Grup
    WHERE ID_Activitate_Grup = NEW.ID_Activitate_Grup;

    -- Verifică dacă numărul curent de studenți înscriși este mai mic decât numărul minim necesar
    -- și dacă data curentă nu a depășit data de expirare
    IF numarInscrisi < numarMinimParticipanti AND NOW() >= timpExpirare THEN
        -- Șterge activitatea de grup
        DELETE FROM Activitati_Grup
        WHERE ID_Activitate_Grup = NEW.ID_Activitate_Grup;

        -- Anulează inserarea în Student_Activitate_Grup
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Nu s-a atins numărul minim de participanți. Înscrierea a fost anulată.';
    END IF;
END //

DELIMITER ;





DELIMITER //

CREATE TRIGGER RenuntaLaActivitatiDupaIesireDinGrup
AFTER DELETE ON Membri_Grup
FOR EACH ROW
BEGIN
    DECLARE idStudent INT;
    DECLARE idGrup INT;

    SET idStudent = OLD.ID_Student;
    SET idGrup = OLD.ID_Grup;

    -- Șterge din tabelul Student_Activitate_Grup toate înregistrările asociate studentului și grupului
    DELETE FROM Student_Activitate_Grup
    WHERE ID_Student = idStudent
      AND ID_Activitate_Grup IN (
          SELECT ID_Activitate_Grup
          FROM Activitati_Grup
          WHERE ID_Grup = idGrup
      );


END //

DELIMITER ;