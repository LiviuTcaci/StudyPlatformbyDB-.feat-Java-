#aceste interogari sunt doar de proba

-- Interogare pentru afișarea tuturor studenților înregistrați în baza de date
SELECT * FROM Studenti;

-- Interogare pentru afișarea informațiilor despre un student specific
SELECT * FROM Studenti WHERE ID_Student = 5;

-- Interogare pentru afișarea tuturor cursurilor la care este înscris un student
SELECT d.Nume_Disciplina FROM Discipline d
JOIN Inscrieri_Studenti_Disciplina i ON i.ID_Disciplina = d.ID_Disciplina
WHERE i.ID_Student = 6;

-- Interogare pentru calcularea notei finale a unui student dintr-un anumit curs
SELECT AVG(Nota) FROM Note WHERE ID_Student = 9 AND ID_Disciplina = 5;

-- Variabilă pentru a stoca rezultatul procedurii
SET @p_ResultCode = 0;

-- Apelul procedurii
CALL Adaugare_Utilizator ('Student', '1259567800123', 'Popescu', 'Ion', 'Str. Miha Viteazu, nr. 2', '0712345178', 'popescu.on@example.com', 'RO123456789012134578901234567', 1011, @p_ResultCode);

-- Afișarea rezultatului
SELECT @p_ResultCode AS ResultCode;

