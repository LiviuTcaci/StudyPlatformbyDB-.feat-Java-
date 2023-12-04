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
    Parola VARCHAR(15),
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
    ID_Profesor INT PRIMARY KEY auto_increment,
    Departament VARCHAR(50),
    Ore_Minim   INT,
    Ore_Maxim   INT,
    FOREIGN KEY (ID_Profesor) REFERENCES Utilizatori (ID_Utilizator)
);

CREATE TABLE IF NOT EXISTS Discipline
(
    ID_Disciplina        INT PRIMARY KEY AUTO_INCREMENT,
    ID_Profesor          INT,
    Nume_Disciplina      VARCHAR(100),
    Descriere            TEXT,
    Numar_Maxim_Studenti INT,
    FOREIGN KEY (ID_Profesor) REFERENCES Profesori (ID_Profesor)

);

-- Tabela Asociere_Curs_Profesor
CREATE TABLE IF NOT EXISTS Asociere_Curs_Profesor (
    ID_Asociere INT PRIMARY KEY AUTO_INCREMENT,
    ID_Disciplina INT,
    ID_Profesor INT,
    FOREIGN KEY (ID_Disciplina) REFERENCES Discipline(ID_Disciplina) ,
    FOREIGN KEY (ID_Profesor) REFERENCES Profesori(ID_Profesor)
);

-- tip activitate
CREATE TABLE IF NOT EXISTS TipActivitate (
    ID_Tip_Activitate INT PRIMARY KEY AUTO_INCREMENT,
    Nume_Tip_Activitate enum ('Curs','Seminar','Laborator') NOT NULL,
    Descriere TEXT,
    Numar_Maxim_Studenti INT,
    Data_Inceput DATETIME,
    Data_Sfarsit DATETIME,
    FOREIGN KEY (ID_Tip_Activitate) REFERENCES Discipline(ID_Disciplina)
);

CREATE TABLE IF NOT EXISTS Disciplina_TipActivitate (
    ID_Disciplina INT,
    ID_Tip_Activitate INT,
    FOREIGN KEY (ID_Disciplina) REFERENCES Discipline(ID_Disciplina),
    FOREIGN KEY (ID_Tip_Activitate) REFERENCES TipActivitate(ID_Tip_Activitate),
    PRIMARY KEY (ID_Disciplina, ID_Tip_Activitate)
);
/* ----------------------------------------------------------------------------------------------------------------- */

CREATE TABLE IF NOT EXISTS Studenti
(
    ID_Student    INT PRIMARY KEY auto_increment,
    An_Studiu     INT,
    Ore_Sustinute INT,
    FOREIGN KEY (ID_Student) REFERENCES Utilizatori (ID_Utilizator)
);

CREATE TABLE IF NOT EXISTS Inscrieri_Studenti_Disciplina
(
    ID_Inscriere  INT PRIMARY KEY AUTO_INCREMENT,
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
    ID_Grup   INT PRIMARY KEY auto_increment,
    Nume_Grup VARCHAR(50),
    Descriere TEXT
);


CREATE TABLE IF NOT EXISTS Membri_Grup
(
    ID_Membru  INT PRIMARY KEY AUTO_INCREMENT,
    ID_Grup    INT,
    ID_Student INT,
    FOREIGN KEY (ID_Grup) REFERENCES Grupuri_Studiu (ID_Grup),
    FOREIGN KEY (ID_Student) REFERENCES Studenti (ID_Student)
);


CREATE TABLE IF NOT EXISTS Mesaje_Grup
(
    ID_Mesaj       INT PRIMARY KEY auto_increment,
    ID_Grup        INT,
    ID_Student     INT,
    Continut_Mesaj TEXT,
    FOREIGN KEY (ID_Grup) REFERENCES Grupuri_Studiu (ID_Grup),
    FOREIGN KEY (ID_Student) REFERENCES Studenti (ID_Student)
); -- adaugare date si timp


CREATE TABLE IF NOT EXISTS Activitati_Grup
(
    ID_Activitate_Grup       INT PRIMARY KEY auto_increment,
    ID_Grup                  INT,
    Data_Inceput             DATETIME,
    Durata                   INT,
    Numar_Minim_Participanti INT,
    Timp_Expirare            DATETIME,
    FOREIGN KEY (ID_Grup) REFERENCES Grupuri_Studiu (ID_Grup)
);


CREATE TABLE IF NOT EXISTS Calendar
(
    ID_Calendar   INT PRIMARY KEY auto_increment,
    Ora           TIME,
    Data          DATE,
    ID_Disciplina INT,
    FOREIGN KEY (ID_Disciplina) REFERENCES Discipline (ID_Disciplina)
);

