-- TriggerInscriere Disciplina ceia ce implica si crearea unui tip de activitate
DELIMITER //
CREATE TRIGGER InscriereDisciplina
AFTER INSERT ON Inscrieri_Studenti_Disciplina
FOR EACH ROW BEGIN
    INSERT INTO TipActivitate (Nume_Tip_Activitate, Descriere, Numar_Maxim_Studenti, Data_Inceput, Data_Sfarsit) VALUES ('Curs', 'Cursul de la disciplina ' + (SELECT Nume_Disciplina FROM Discipline WHERE ID_Disciplina = NEW.ID_Disciplina), (SELECT Numar_Maxim_Studenti FROM Discipline WHERE ID_Disciplina = NEW.ID_Disciplina), NOW(), NOW() + 7);
END //
DELIMITER ;