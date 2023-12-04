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





-- Testare procedura pentru adaugat utilizatori
#CALL AdaugaUtilizator('Student', '1234567899123', 'Iorga', 'Ion', 'Str. Secundara 123', '0720443456', 'iorga.ion@example.com', 'RO1234567890123316789012', 12344, 'parola133', 'yorga', NULL, NULL, NULL);

SELECT * FROM Utilizatori;


-- Testat procedura pentru adaugat note
#CALL AdaugaNota(1, 1, 9); -- Exemplu de adăugare a unei note pentru studentul cu ID 1 la disciplina cu ID 1 și nota 9

SELECT * FROM Note;


-- Testat procedura pentru adaugat studenti
#CALL AdaugaStudent(1, 45, '9876543910123', 'Aurelian', 'Mircea', 'Bd. Unirii 245', '0715345678', 'aur.mircea@example.com', 'RO987654321098769432109876543', 54321, 'parolamaria', 'ionescumaria', 2);

SELECT * FROM Studenti;


-- Testat procedura pentru a modifica informatiile despre un utilizator
#CALL ModificaInformatiiUtilizator(1, 'Str. Noua 123', '0720999888', 'nou.email@example.com', 'RO1122334455667788990011');

SELECT * FROM Utilizatori;


