use pde_testing2;

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
    IN oreMaxim INT
)
BEGIN
    DECLARE lastUserID INT;

    -- Adaugă utilizator în tabela Utilizatori
    INSERT INTO Utilizatori (Tip_Utilizator, CNP, Nume, Prenume, Adresa, Telefon, Email, Cont_IBAN, Numar_Contract)
    VALUES (tipUtilizator, cnp, nume, prenume, adresa, telefon, email, contIBAN, numarContract);

    -- Obține ID-ul utilizatorului adăugat
    SET lastUserID = LAST_INSERT_ID();

    -- Adaugă utilizator în tabela UtilizatoriAutentificare
    INSERT INTO UtilizatoriAutentificare (ID_Utilizator, Parola, Nume_Utilizator)
    VALUES (lastUserID, parola, numeUtilizator);

    -- În funcție de tipul utilizatorului, adaugă în tabela corespunzătoare
    CASE
        WHEN tipUtilizator = 'SuperAdministrator' THEN
            INSERT INTO SuperAdministratori (ID_SuperAdministrator) VALUES (lastUserID);
        WHEN tipUtilizator = 'Administrator' THEN
            INSERT INTO Administratori (ID_Administrator) VALUES (lastUserID);
        WHEN tipUtilizator = 'Profesor' THEN
            INSERT INTO Profesori (ID_Profesor, Departament, Ore_Minim, Ore_Maxim) VALUES (lastUserID, departament, oreMinim, oreMaxim);
        WHEN tipUtilizator = 'Student' THEN
            INSERT INTO Studenti (ID_Student, An_Studiu) VALUES (lastUserID, NULL); -- Poți seta An_Studiu într-un al doilea pas, dacă este necesar
    END CASE;
END //
DELIMITER ;


