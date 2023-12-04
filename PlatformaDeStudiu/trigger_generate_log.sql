-- Declanșator care generează un log atunci când se creează un utilizator nou
DELIMITER //
CREATE TRIGGER LogUtilizatori
AFTER INSERT ON Utilizatori
FOR EACH ROW BEGIN
    INSERT INTO LogUtilizatori (ID_Utilizator, Tip_Utilizator, CNP, Nume, Prenume, Adresa, Telefon, Email, Cont_IBAN, Numar_Contract, Data_Creare)
    VALUES (NEW.ID_Utilizator, NEW.Tip_Utilizator, NEW.CNP, NEW.Nume, NEW.Prenume, NEW.Adresa, NEW.Telefon, NEW.Email, NEW.Cont_IBAN, NEW.Numar_Contract, NOW());
END //
DELIMITER ;