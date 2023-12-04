-- TriggerInscriere Grup Studiu
DELIMITER //
CREATE TRIGGER InscriereGrupStudiu
AFTER INSERT ON Membri_Grup
FOR EACH ROW BEGIN
    INSERT INTO Mesaje_Grup (ID_Grup, ID_Student, Continut_Mesaj) VALUES (NEW.ID_Grup, NEW.ID_Student, 'Bun venit in grupul de studiu!');
END //
DELIMITER ;