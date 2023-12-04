-- Trigger_RenuntareCurs
DELIMITER //
CREATE TRIGGER RenuntareCurs
AFTER DELETE ON Inscrieri_Studenti_Disciplina
FOR EACH ROW BEGIN
    DELETE FROM TipActivitate WHERE ID_Tip_Activitate = (SELECT ID_Tip_Activitate FROM Disciplina_TipActivitate WHERE ID_Disciplina = OLD.ID_Disciplina);
END //
DELIMITER ;