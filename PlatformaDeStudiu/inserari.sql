USE PDE_testing2;

#inserarile au fost facute


/*
-- Inserări pentru tabela Utilizatori
INSERT INTO Utilizatori (Tip_Utilizator, CNP, Nume, Prenume, Adresa, Telefon, Email, Cont_IBAN, Numar_Contract)
VALUES 
  ('Student', '1234567890123', 'Popescu', 'Ion', 'Str. Mihai Viteazu, nr. 1', '0712345678', 'popescu.ion@example.com', 'RO123456789012345678901234567', 1001),
  ('Profesor', '2345678901234', 'Ionescu', 'Maria', 'Bd. Republicii, nr. 10', '0723456789', 'ionescu.maria@example.com', 'RO987614321098765432109876543', 2001),
  ('SuperAdministrator', '3456789012345', 'Dumitrescu', 'Alex', 'Piata Unirii, nr. 5', '0734567890', 'dumitrescu.alex@example.com', 'RO567890123456789012345678901', 3001),
  ('Administrator', '4567890123456', 'Georgescu', 'Elena', 'Calea Victoriei, nr. 15', '0745678901', 'georgescu.elena@example.com', 'RO321098765432109876543210987', 4001),
  ('Student', '5678901234567', 'Vasilescu', 'Adrian', 'Aleea Florilor, nr. 7', '0756789012', 'vasilescu.adrian@example.com', 'RO654321098765032109876543210', 1002),
  ('Profesor', '6789012345678', 'Popa', 'Ana', 'Splaiul Independentei, nr. 20', '0767890123', 'popa.ana@example.com', 'RO789012345678901234567890123', 2002),
  ('SuperAdministrator', '7890123456789', 'Mihai', 'George', 'Bd. Decebal, nr. 25', '0778901234', 'mihai.george@example.com', 'RO987654321098765432109876543', 3002),
  ('Administrator', '8901234567890', 'Gheorghe', 'Mirela', 'Calea Dorobantilor, nr. 30', '0789012345', 'gheorghe.mirela@example.com', 'RO230567890123456789012345678', 4002),
  ('Student', '9012345678901', 'Popescu', 'Cristian', 'Str. Soseaua Nordului, nr. 12', '0790123456', 'popescu.cristian@example.com', 'RO543210987654321098765432109', 1003),
  ('Profesor', '0123456789012', 'Iancu', 'Elena', 'Aleea Mihai Bravu, nr. 8', '0801234567', 'iancu.elena@example.com', 'RO678901234567890983456789012', 2003);


-- Inserări pentru tabela UtilizatoriAutentificare
INSERT INTO UtilizatoriAutentificare (ID_Utilizator, Parola, Nume_Utilizator)
VALUES 
  (1, 'parola123', 'popescu_ion'),
  (2, 'parola456', 'ionescu_maria'),
  (3, 'parola789', 'dumitrescu_alex'),
  (4, 'parola012', 'georgescu_elena'),
  (5, 'parola345', 'vasilescu_adrian'),
  (6, 'parola678', 'popa_ana'),
  (7, 'parola901', 'mihai_george'),
  (8, 'parola234', 'gheorghe_mirela'),
  (9, 'parola567', 'popescu_cristian'),
  (10, 'parola890', 'iancu_elena');


-- Inserări pentru tabela SuperAdministratori
INSERT INTO SuperAdministratori (ID_SuperAdministrator)
VALUES (1), (2), (3), (4), (5), (6), (7), (8), (9), (10);


-- Inserări pentru tabela Administratori
INSERT INTO Administratori (ID_Administrator)
VALUES (1), (2), (3), (4), (5), (6), (7), (8), (9), (10);


-- Inserări pentru tabela Profesori
INSERT INTO Profesori (Departament, Ore_Minim, Ore_Maxim)
VALUES 
  ('Informatica', 20, 30),
  ('Matematica', 15, 25),
  ('Fizica', 18, 28),
  ('Chimie', 16, 26),
  ('Biologie', 22, 32),
  ('Istorie', 12, 22),
  ('Geografie', 14, 24),
  ('Economie', 25, 35),
  ('Arte', 10, 20),
  ('Limbi Straine', 30, 40);


INSERT INTO Discipline (ID_Profesor, Nume_Disciplina, Descriere, Numar_Maxim_Studenti)
VALUES 
  (1, 'Baze de Date', 'Introducere în bazele de date relaționale pentru studenții de anul I', 50),
  (2, 'Algebră Liniară', 'Studiul spațiilor vectoriale și transformărilor liniare pentru studenții de matematică', 40),
  (3, 'Mecanică Clasică', 'Principiile mecanicii clasice newtoniene pentru studenții de fizică', 30),
  (4, 'Chimie Organica', 'Structura și reactivitatea compușilor organici pentru studenții de chimie', 35),
  (5, 'Genetica', 'Studiul eredității și variației la nivel genetic pentru studenții de biologie', 45),
  (6, 'Istoria Antică', 'Evolutia umanitatii in perioada antica pentru studenții de istorie', 20),
  (7, 'Geografia Economica', 'Relația dintre geografie și economie pentru studenții de geografie', 25),
  (8, 'Microeconomie', 'Studiul comportamentului agentilor economici individuali pentru studenții de economie', 30),
  (9, 'Arta Moderna', 'Explorarea mișcărilor artistice moderne pentru studenții de arte', 15),
  (10, 'Limba Engleza Avansata', 'Dezvoltarea abilităților avansate de limbă engleză pentru studenții cu studii lingvistice', 40);


-- Inserări pentru tabela Asociere_Curs_Profesor
INSERT INTO Asociere_Curs_Profesor (ID_Disciplina, ID_Profesor)
VALUES 
  (1, 1),
  (2, 2),
  (3, 3),
  (4, 4),
  (5, 5),
  (6, 6),
  (7, 7),
  (8, 8),
  (9, 9),
  (10, 10);


-- Inserări pentru tabela TipActivitate
INSERT INTO TipActivitate (Nume_Tip_Activitate, Descriere, Numar_Maxim_Studenti, Data_Inceput, Data_Sfarsit)
VALUES 
  ('Curs', 'Curs introductiv pentru disciplina Baze de Date', 100, '2023-01-10 09:00:00', '2023-01-10 12:00:00'),
  ('Seminar', 'Seminar de discuții și exerciții pentru Algebră Liniară', 50, '2023-01-15 14:00:00', '2023-01-15 16:00:00'),
  ('Laborator', 'Laborator de experimente pentru Chimie Organica', 40, '2023-01-20 10:00:00', '2023-01-20 13:00:00'),
  ('Curs', 'Curs de Istoria Antică', 80, '2023-01-25 11:00:00', '2023-01-25 14:00:00'),
  ('Seminar', 'Seminar de analiză economică pentru Microeconomie', 60, '2023-02-01 15:00:00', '2023-02-01 17:00:00'),
  ('Laborator', 'Laborator de desen artistic pentru Arta Moderna', 30, '2023-02-05 13:00:00', '2023-02-05 16:00:00'),
  ('Curs', 'Curs avansat de Limba Engleza', 90, '2023-02-10 09:30:00', '2023-02-10 12:30:00'),
  ('Seminar', 'Seminar de proiectare pentru Limba Engleza Avansata', 70, '2023-02-15 14:30:00', '2023-02-15 16:30:00'),
  ('Laborator', 'Laborator de genetică pentru Biologie', 35, '2023-02-20 10:30:00', '2023-02-20 13:30:00'),
  ('Curs', 'Curs de introducere în programare pentru Informatica', 120, '2023-02-25 11:30:00', '2023-02-25 14:30:00');


-- Inserări pentru tabela Disciplina_TipActivitate
INSERT INTO Disciplina_TipActivitate (ID_Disciplina, ID_Tip_Activitate)
VALUES 
  (1, 1),
  (2, 2),
  (3, 3),
  (6, 1),
  (8, 2),
  (9, 3),
  (10, 1),
  (4, 2),
  (5, 3),
  (7, 1);


-- Inserări pentru tabela Studenti
INSERT INTO Studenti (An_Studiu, Ore_Sustinute)
VALUES 
  (2, 40),
  (3, 35),
  (1, 25),
  (4, 30),
  (2, 28),
  (3, 22),
  (1, 18),
  (4, 20),
  (2, 15),
  (3, 10);

-- Inserări pentru tabela Inscrieri_Studenti_Disciplina
INSERT INTO Inscrieri_Studenti_Disciplina (ID_Student, ID_Disciplina)
VALUES 
  (1, 1),
  (2, 2),
  (3, 3),
  (4, 6),
  (5, 8),
  (6, 9),
  (7, 10),
  (8, 4),
  (9, 5),
  (10, 7);

-- Inserări pentru tabela Note
INSERT INTO Note (ID_Student, ID_Disciplina, Nota)
VALUES 
  (1, 1, 8),
  (2, 2, 9),
  (3, 3, 7),
  (4, 6, 10),
  (5, 8, 8),
  (6, 9, 9),
  (7, 10, 7),
  (8, 4, 10),
  (9, 5, 8),
  (10, 7, 9);


-- Inserări pentru tabela Grupuri_Studiu
INSERT INTO Grupuri_Studiu (Nume_Grup, Descriere)
VALUES 
  ('Grup_1', 'Grup de studiu pentru Baze de Date'),
  ('Grup_2', 'Grup de discuții pentru Limba Engleza Avansata'),
  ('Grup_3', 'Grup de proiectare pentru Arta Moderna'),
  ('Grup_4', 'Grup de laborator pentru Microeconomie'),
  ('Grup_5', 'Grup de cercetare pentru Genetica'),
  ('Grup_6', 'Grup de lectii pentru Informatica'),
  ('Grup_7', 'Grup de studiu pentru Algebră Liniară'),
  ('Grup_8', 'Grup de laborator pentru Chimie Organica'),
  ('Grup_9', 'Grup de discuții pentru Istoria Antică'),
  ('Grup_10', 'Grup de proiectare pentru Limba Engleza');


-- Inserări pentru tabela Membri_Grup
INSERT INTO Membri_Grup (ID_Grup, ID_Student)
VALUES 
  (1, 1),
  (2, 2),
  (3, 3),
  (4, 6),
  (5, 8),
  (6, 10),
  (7, 4),
  (8, 5),
  (9, 7),
  (10, 9);


-- Inserări pentru tabela Mesaje_Grup
INSERT INTO Mesaje_Grup (ID_Grup, ID_Student, Continut_Mesaj)
VALUES 
  (1, 1, 'Bun venit în Grupul 1!'),
  (2, 2, 'Salutare! Sper că vă veți bucura de discuțiile noastre.'),
  (3, 3, 'Grupul 3 este dedicat discuțiilor despre proiectele de artă modernă.'),
  (4, 6, 'Bună! Când avem următorul laborator în Grupul 4?'),
  (5, 8, 'Salut tuturor! Avem vreo ședință programată pentru Grupul 5?'),
  (6, 10, 'Vă rog să propuneți subiecte interesante pentru discuțiile din Grupul 6.'),
  (7, 4, 'Grupul 7 va avea o sesiune de studiu pentru algebră liniară săptămâna viitoare.'),
  (8, 5, 'Cine participă la laboratorul de Chimie Organica în Grupul 8?'),
  (9, 7, 'Salutare! Care sunt temele discutate în Grupul 9?'),
  (10, 9, 'Propunem să organizăm o ședință de proiectare în Grupul 10.');


-- Inserări pentru tabela Activitati_Grup
INSERT INTO Activitati_Grup (ID_Grup, Data_Inceput, Durata, Numar_Minim_Participanti, Timp_Expirare)
VALUES 
  (1, '2023-03-01 14:00:00', 2, 5, '2023-03-01 16:00:00'),
  (2, '2023-03-05 10:30:00', 1, 3, '2023-03-05 11:30:00'),
  (3, '2023-03-10 15:15:00', 3, 8, '2023-03-10 18:15:00'),
  (4, '2023-03-15 13:30:00', 2, 6, '2023-03-15 15:30:00'),
  (5, '2023-03-20 09:45:00', 1, 4, '2023-03-20 10:45:00'),
  (6, '2023-03-25 16:00:00', 2, 7, '2023-03-25 18:00:00'),
  (7, '2023-03-30 11:45:00', 1, 4, '2023-03-30 12:45:00'),
  (8, '2023-04-05 14:30:00', 3, 9, '2023-04-05 17:30:00'),
  (9, '2023-04-10 10:00:00', 2, 5, '2023-04-10 11:00:00'),
  (10, '2023-04-15 15:45:00', 2, 6, '2023-04-15 17:45:00');

-- Inserări pentru tabela Calendar
INSERT INTO Calendar (Ora, Data, ID_Disciplina)
VALUES 
  ('14:00:00', '2023-03-01', 1),
  ('10:30:00', '2023-03-05', 2),
  ('15:15:00', '2023-03-10', 3),
  ('13:30:00', '2023-03-15', 6),
  ('09:45:00', '2023-03-20', 8),
  ('16:00:00', '2023-03-25', 9),
  ('11:45:00', '2023-03-30', 10),
  ('14:30:00', '2023-04-05', 4),
  ('10:00:00', '2023-04-10', 5),
  ('15:45:00', '2023-04-15', 7);

*/