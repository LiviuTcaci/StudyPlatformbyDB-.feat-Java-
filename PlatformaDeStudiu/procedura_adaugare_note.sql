-- Procedură pentru adăugarea unei note unui student
DELIMITER //
CREATE PROCEDURE AdaugareNota(
    IN p_ID_Nota INT ,
    IN p_ID_Student INT,
    IN p_ID_Disciplina INT,
    IN p_Nota INT,
    OUT p_ResultCode INT
)
BEGIN
    -- Adăugare note în tabel
    INSERT INTO Note(Nota_ID,ID_Student, ID_Disciplina, Nota)
    VALUES (p_ID_Nota, p_ID_Student, p_ID_Disciplina, p_Nota);

    SET p_ResultCode = 1; -- Cod de succes
END //
DELIMITER ;
