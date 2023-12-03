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
    OUT p_ResultCode INT,
    OUT p_ErrorMessage VARCHAR(255)
)
BEGIN
    DECLARE userCount INT;

    -- Verificare dacă CNP-ul este unic
    SELECT COUNT(*) INTO userCount FROM Utilizatori WHERE CNP = p_CNP;
    IF userCount > 0 THEN
        SET p_ResultCode = 0; -- Cod de eroare pentru CNP duplicat
        SET p_ErrorMessage = 'CNP duplicat.';

    END IF;

    -- Verificare alte constrângeri, tipuri de date, etc...

    -- Adăugare utilizator în tabel
    INSERT INTO Utilizatori(Tip_Utilizator, CNP, Nume, Prenume, Adresa, Telefon, Email, Cont_IBAN, Numar_Contract)
    VALUES (p_Tip_Utilizator, p_CNP, p_Nume, p_Prenume, p_Adresa, p_Telefon, p_Email, p_Cont_IBAN, p_Numar_Contract);

    SET p_ResultCode = 1; -- Cod de succes
    SET p_ErrorMessage = ''; -- Mesaj gol pentru succes
END //
DELIMITER ;

