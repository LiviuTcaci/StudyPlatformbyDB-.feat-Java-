-- Trigger Modificare Activitati
DELIMITER //
CREATE TRIGGER ModificareActivitati
AFTER UPDATE ON TipActivitate
FOR EACH ROW BEGIN
    UPDATE Calendar SET Ora = NEW.Data_Inceput, Data = NEW.Data_Inceput WHERE ID_Disciplina = NEW.ID_Tip_Activitate;
END //
DELIMITER ;