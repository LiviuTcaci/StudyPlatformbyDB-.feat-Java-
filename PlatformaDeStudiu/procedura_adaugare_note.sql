DELIMITER //
CREATE PROCEDURE AdaugaNota(
    IN idStudent INT,
    IN idDisciplina INT,
    IN nota INT
)
BEGIN
    INSERT INTO Note (ID_Student, ID_Disciplina, Nota)
    VALUES (idStudent, idDisciplina, nota);
END //
DELIMITER ;



