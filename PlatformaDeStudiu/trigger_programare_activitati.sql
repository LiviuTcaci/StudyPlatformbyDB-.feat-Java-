-- Trigger ProgramareActivitati
DELIMITER //
CREATE TRIGGER ProgramareActivitati
AFTER INSERT ON TipActivitate
FOR EACH ROW BEGIN
    INSERT INTO Calendar (Ora, Data, ID_Disciplina) VALUES (NEW.Data_Inceput, NEW.Data_Inceput, NEW.ID_Tip_Activitate);
END //
DELIMITER ;