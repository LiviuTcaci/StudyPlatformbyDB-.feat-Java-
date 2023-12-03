-- procedura deautentificare utilizatori
DELIMITER //
CREATE PROCEDURE DeautentificareUtilizator()
BEGIN
    SELECT * FROM Utilizatori WHERE ID_Utilizator = 0;
END //
DELIMITER ;

