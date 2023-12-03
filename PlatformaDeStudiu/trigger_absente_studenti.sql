-- Trigger Absente Studenti
DELIMITER //
CREATE TRIGGER AbsenteStudenti
AFTER INSERT ON Note
FOR EACH ROW BEGIN
    UPDATE Studenti SET Ore_Sustinute = Ore_Sustinute + 1 WHERE ID_Student = NEW.ID_Student;
END //
DELIMITER ;