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