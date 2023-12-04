DELIMITER //
CREATE PROCEDURE ModificaInformatiiUtilizator(
    IN idUtilizator INT,
    IN nouaAdresa VARCHAR(100),
    IN noulTelefon VARCHAR(15),
    IN noulEmail VARCHAR(50),
    IN noulContIBAN VARCHAR(30)
)
BEGIN
    UPDATE Utilizatori
    SET Adresa = nouaAdresa,
        Telefon = noulTelefon,
        Email = noulEmail,
        Cont_IBAN = noulContIBAN
    WHERE ID_Utilizator = idUtilizator;
END //
DELIMITER ;
