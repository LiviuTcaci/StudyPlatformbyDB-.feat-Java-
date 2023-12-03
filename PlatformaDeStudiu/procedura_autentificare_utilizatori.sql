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
