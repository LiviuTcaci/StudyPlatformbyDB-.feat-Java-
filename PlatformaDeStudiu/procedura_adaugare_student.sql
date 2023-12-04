DELIMITER //
CREATE PROCEDURE AdaugaStudent(
    IN anStudiu INT,
    IN oreSustinute INT,
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
    DECLARE lastUserID INT;

    -- Adaugă utilizator în tabela Utilizatori
    INSERT INTO Utilizatori (Tip_Utilizator, CNP, Nume, Prenume, Adresa, Telefon, Email, Cont_IBAN, Numar_Contract)
    VALUES ('Student', cnp, nume, prenume, adresa, telefon, email, contIBAN, numarContract);

    -- Obține ID-ul utilizatorului adăugat
    SET lastUserID = LAST_INSERT_ID();

    -- Adaugă utilizator în tabela UtilizatoriAutentificare
    INSERT INTO UtilizatoriAutentificare (ID_Utilizator, Parola, Nume_Utilizator)
    VALUES (lastUserID, parola, numeUtilizator);

    -- Adaugă student în tabela Studenti
    INSERT INTO Studenti (ID_Student, An_Studiu, Ore_Sustinute)
    VALUES (lastUserID, anStudiu, oreSustinute);

    -- Dacă este specificat un ID de grup, adaugă studentul în grup
    IF idGrup IS NOT NULL THEN
        INSERT INTO Membri_Grup (ID_Grup, ID_Student)
        VALUES (idGrup, lastUserID);
    END IF;
END //
DELIMITER ;
