-- Declanșator care generează un log când se adaugă o notă
DELIMITER //
CREATE TRIGGER LogNote
AFTER INSERT ON Note
FOR EACH ROW BEGIN
    INSERT INTO LogNote (ID_Nota, ID_Student, ID_Disciplina, Nota, Data_Adaugare)
    VALUES (NEW.ID_Nota, NEW.ID_Student, NEW.ID_Disciplina, NEW.Nota, NOW());
END //
DELIMITER ;