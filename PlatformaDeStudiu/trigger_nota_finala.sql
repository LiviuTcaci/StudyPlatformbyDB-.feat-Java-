-- Trigger Nota Activitati dupa formula adaptabila de profesor
DELIMITER //
CREATE TRIGGER NotaActivitati
AFTER INSERT ON Note
FOR EACH ROW BEGIN
    UPDATE Note SET Nota = (SELECT AVG(Nota) FROM Note WHERE ID_Student = NEW.ID_Student AND ID_Disciplina = NEW.ID_Disciplina) WHERE ID_Nota = NEW.ID_Nota;
END //
DELIMITER ;