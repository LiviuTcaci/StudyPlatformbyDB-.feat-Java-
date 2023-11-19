CREATE DATABASE IF NOT EXISTS platforma_de_studiu;
USE platforma_de_studiu;

-- Tabela Utilizatori
CREATE TABLE Utilizatori (
    ID_Utilizator INT PRIMARY KEY,
    Tip_Utilizator VARCHAR(20),
    CNP VARCHAR(13),
    Nume VARCHAR(50),
    Prenume VARCHAR(50),
    Adresa VARCHAR(100),
    Telefon VARCHAR(15),
    Email VARCHAR(50),
    Cont_IBAN VARCHAR(30),
    Numar_Contract INT
);

-- Tabela Studenti
CREATE TABLE Studenti (
    ID_Student INT PRIMARY KEY,
    An_Studiu INT,
    Ore_Sustinute INT,
    FOREIGN KEY (ID_Student) REFERENCES Utilizatori(ID_Utilizator)
);

-- Tabela Profesori
CREATE TABLE Profesori (
    ID_Profesor INT PRIMARY KEY,
    Departament VARCHAR(50),
    Ore_Minim INT,
    Ore_Maxim INT,
    FOREIGN KEY (ID_Profesor) REFERENCES Utilizatori(ID_Utilizator)
);

-- Tabela SuperAdministratori
CREATE TABLE SuperAdministratori (
    ID_SuperAdministrator INT PRIMARY KEY,
    FOREIGN KEY (ID_SuperAdministrator) REFERENCES Utilizatori(ID_Utilizator)
);

-- Tabela Administratori
CREATE TABLE Administratori (
    ID_Administrator INT PRIMARY KEY,
    FOREIGN KEY (ID_Administrator) REFERENCES Utilizatori(ID_Utilizator)
);

-- Tabela Cursuri
CREATE TABLE Cursuri (
    ID_Curs INT PRIMARY KEY,
    Nume_Curs VARCHAR(100),
    Descriere TEXT,
    Numar_Maxim_Studenti INT
);

-- Tabela Activitati
CREATE TABLE Activitati (
    ID_Activitate INT PRIMARY KEY,
    Tip_Activitate VARCHAR(20),
    Data_Inceput DATETIME,
    Data_Sfarsit DATETIME,
    Numar_Maxim_Participanti INT
);

-- Tabela Asociere_Curs_Profesor
CREATE TABLE Asociere_Curs_Profesor (
    ID_Asociere INT PRIMARY KEY,
    ID_Curs INT,
    ID_Profesor INT,
    FOREIGN KEY (ID_Curs) REFERENCES Cursuri(ID_Curs),
    FOREIGN KEY (ID_Profesor) REFERENCES Profesori(ID_Profesor)
);

-- Tabela Inscrieri_Studenti_Activitati
CREATE TABLE Inscrieri_Studenti_Activitati (
    ID_Inscriere INT PRIMARY KEY,
    ID_Student INT,
    ID_Activitate INT,
    FOREIGN KEY (ID_Student) REFERENCES Studenti(ID_Student),
    FOREIGN KEY (ID_Activitate) REFERENCES Activitati(ID_Activitate)
);

-- Tabela Note_Studenti
CREATE TABLE Note_Studenti (
    ID_Nota INT PRIMARY KEY,
    ID_Student INT,
    ID_Activitate INT,
    Nota INT,
    FOREIGN KEY (ID_Student) REFERENCES Studenti(ID_Student),
    FOREIGN KEY (ID_Activitate) REFERENCES Activitati(ID_Activitate)
);

-- Tabela Grupuri_Studiu
CREATE TABLE Grupuri_Studiu (
    ID_Grup INT PRIMARY KEY,
    Nume_Grup VARCHAR(50),
    Descriere TEXT
);

-- Tabela Membri_Grup
CREATE TABLE Membri_Grup (
    ID_Membru INT PRIMARY KEY,
    ID_Grup INT,
    ID_Student INT,
    FOREIGN KEY (ID_Grup) REFERENCES Grupuri_Studiu(ID_Grup),
    FOREIGN KEY (ID_Student) REFERENCES Studenti(ID_Student)
);

-- Tabela Mesaje_Grup
CREATE TABLE Mesaje_Grup (
    ID_Mesaj INT PRIMARY KEY,
    ID_Grup INT,
    ID_Student INT,
    Continut_Mesaj TEXT,
    FOREIGN KEY (ID_Grup) REFERENCES Grupuri_Studiu(ID_Grup),
    FOREIGN KEY (ID_Student) REFERENCES Studenti(ID_Student)
);

-- Tabela Activitati_Grup
CREATE TABLE Activitati_Grup (
    ID_Activitate_Grup INT PRIMARY KEY,
    ID_Grup INT,
    Data_Inceput DATETIME,
    Durata INT,
    Numar_Minim_Participanti INT,
    Timp_Expirare DATETIME,
    FOREIGN KEY (ID_Grup) REFERENCES Grupuri_Studiu(ID_Grup)
);
