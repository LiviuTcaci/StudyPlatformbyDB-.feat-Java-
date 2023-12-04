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
