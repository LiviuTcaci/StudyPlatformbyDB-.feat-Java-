-- Trigger StergereUtilizator
DELIMITER //
CREATE TRIGGER StergereUtilizator
BEFORE DELETE ON Utilizatori
FOR EACH ROW BEGIN
    DELETE FROM UtilizatoriAutentificare WHERE ID_Utilizator = OLD.ID_Utilizator;
    DELETE FROM SuperAdministratori WHERE ID_SuperAdministrator = OLD.ID_Utilizator;
    DELETE FROM Administratori WHERE ID_Administrator = OLD.ID_Utilizator;
    DELETE FROM Profesori WHERE ID_Profesor = OLD.ID_Utilizator;
    DELETE FROM Studenti WHERE ID_Student = OLD.ID_Utilizator;
END //
DELIMITER ;