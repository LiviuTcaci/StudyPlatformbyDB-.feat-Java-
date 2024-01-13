USE PDE_testing2;


INSERT INTO Utilizatori (Tip_Utilizator, CNP, Nume, Prenume, Adresa, Telefon, Email, Cont_IBAN, Numar_Contract)
VALUES 
  
  ('Administrator', '8901234567890', 'Gheorghe', 'Mirela', 'Calea Dorobantilor, nr. 30', '0789012345', 'gheorghe.mirela@example.com', 'RO230567890123456789012345678', 4002),
  ('Administrator', '4567890123456', 'Georgescu', 'Elena', 'Calea Victoriei, nr. 15', '0745678901', 'georgescu.elena@example.com', 'RO321098765432109876543210987', 4001),
  
  ('SuperAdministrator', '7890123456789', 'Mihai', 'George', 'Bd. Decebal, nr. 25', '0778901234', 'mihai.george@example.com', 'RO987654321098765432109876543', 3002),
  ('SuperAdministrator', '3456789012345', 'Dumitrescu', 'Alex', 'Piata Unirii, nr. 5', '0734567890', 'dumitrescu.alex@example.com', 'RO567890123456789012345678901', 3001),
  
  
  ('Profesor', '2345678901234', 'Ionescu', 'Maria', 'Bd. Republicii, nr. 10', '0723456789', 'ionescu.maria@example.com', 'RO987614321098765432109876543', 2001),
  ('Profesor', '5566778899001', 'Munteanu', 'Alina', 'Piata Romana, nr. 11', '0856789012', 'munteanu.alina@example.com', 'RO556677889900112233445566778', 2004),
  ('Profesor', '6677889900112', 'Ivanescu', 'Gabriel', 'Str. Garii de Nord, nr. 7', '0867890123', 'ivanescu.gabriel@example.com', 'RO667788990011223344556677889', 2005),
  ('Profesor', '7788990011223', 'Stanciu', 'Laura', 'Calea Grivitei, nr. 14', '0878901234', 'stanciu.laura@example.com', 'RO778899001122334455667788990', 2006),
  ('Profesor', '6789012345678', 'Popa', 'Ana', 'Splaiul Independentei, nr. 20', '0767890123', 'popa.ana@example.com', 'RO789012345678901234567890123', 2002),
  ('Profesor', '0123456789012', 'Iancu', 'Elena', 'Aleea Mihai Bravu, nr. 8', '0801234567', 'iancu.elena@example.com', 'RO678901234567890983456789012', 2003),
 
  ('Student', '5678901234567', 'Vasilescu', 'Adrian', 'Aleea Florilor, nr. 7', '0756789012', 'vasilescu.adrian@example.com', 'RO654321093765032109876543210', 1002),
  ('Student', '1234567890123', 'Popescu', 'Ion', 'Str. Mihai Viteazu, nr. 1', '0712345698', 'popescu.ion@example.com', 'RO123456759012345618901234567', 1001),
  ('Student', '9012345678901', 'Popescu', 'Cristian', 'Str. Soseaua Nordului, nr. 12', '0790123456', 'popescu.cristian@example.com', 'RO523210987654321098765432109', 1003),
  ('Student', '1122334455667', 'Ionescu', 'Elena', 'Bd. Timisoara, nr. 3', '0812345678', 'ionescu.elena@example.com', 'RO112233445566758899001122334', 1004),
  ('Student', '2233445566778', 'Gheorghe', 'Danut', 'Str. Popa Soare, nr. 17', '0823456789', 'gheorghe.danut@example.com', 'RO223344558677889900112233445', 1005),
  ('Student', '3344556677889', 'Vasile', 'Roxana', 'Calea Mosilor, nr. 22', '0834567890', 'vasile.roxana@example.com', 'RO334455667780990011223344556', 1006),
  ('Student', '4455667788990', 'Radu', 'Marius', 'Aleea Bucuresti, nr. 8', '0845678901', 'radu.marius@example.com', 'RO445566778899005122334455667', 1007),
  ('Student', '5566722899001', 'Dumitrescu', 'Andreea', 'Str. Unirii, nr. 15', '0856719012', 'dumitrescu.andreea@example.com', 'RO556671889900112233445566778', 1008),
  ('Student', '6677888900112', 'Georgescu', 'Alexandru', 'Aleea Rozelor, nr. 11', '0867830123', 'georgescu.alexandru@example.com', 'RO661788990011223344556677889', 1009),
  ('Student', '7731990011223', 'Mihai', 'Diana', 'Bd. Dacia, nr. 5', '0878401234', 'mihai.diana@example.com', 'RO778899001122834455667788990', 1010),
  ('Student', '8899001122334', 'Stanescu', 'Valentin', 'Str. Libertatii, nr. 10', '0885012345', 'stanescu.valentin@example.com', 'RO889950112233445566778899001', 1011),
  ('Student', '9900144233445', 'Gavrila', 'Catalin', 'Aleea Crizantemelor, nr. 3', '0890126456', 'gavrila.catalin@example.com', 'RO990016223344556677889900112', 1012),
  ('Student', '1331223344556', 'Constantinescu', 'Ioana', 'Calea Victoriei, nr. 9', '0901274567', 'constantinescu.ioana@example.com', 'RO160122334455667788990011223', 1013),
  ('Student', '1112233445567', 'Balaceanu', 'Silviu', 'Str. Stefan cel Mare, nr. 6', '0912385678', 'balaceanu.silviu@example.com', 'RO111923344556677889900112233', 1014),
  ('Student', '1223344556678', 'Nistor', 'Maria', 'Bd. Basarabia, nr. 2', '0923450789', 'nistor.maria@example.com', 'RO129334455667788990011223344', 1015),
  ('Student', '1334455661189', 'Cojocaru', 'Valeria', 'Aleea Plopilor, nr. 4', '0934167890', 'cojocaru.valeria@example.com', 'RO130445566778899001122334455', 1016),
  ('Student', '1445500778890', 'Florea', 'Bogdan', 'Str. Stefanesti, nr. 14', '0943678901', 'florea.bogdan@example.com', 'RO144556977889900112233445566', 1017),
  ('Student', '1559977889901', 'Dragomir', 'Gabriela', 'Aleea Carpati, nr. 16', '0956789012', 'dragomir.gabriela@example.com', 'RO155663288990011223344556677', 1018),
  ('Student', '1655788990012', 'Marin', 'Raul', 'Str. Calea Bucurestilor, nr. 19', '0267890123', 'marin.raul@example.com', 'RO166778899201122334455667788', 1019),
  ('Student', '1778119001123', 'Grosu', 'Daniela', 'Bd. Corneliu Coposu, nr. 13', '0978961234', 'grosu.daniela@example.com', 'RO177889900332233445566778899', 1020),
  ('Student', '1889880112234', 'Constantin', 'Andrei', 'Aleea Dobrogei, nr. 18', '0989712345', 'constantin.andrei@example.com', 'RO188990911223344455566677788', 1021);
  
  
INSERT INTO UtilizatoriAutentificare (Nume_Utilizator, Parola, Token_Autentificare, ID_Utilizator)
VALUES 
  ('vasilescu.adrian', 'parola1', 'token1', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'vasilescu.adrian@example.com')),
  ('popescu.ion', 'parola2', 'token2', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'popescu.ion@example.com')),
  ('popescu.cristian', 'parola3', 'token3', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'popescu.cristian@example.com')),
  ('ionescu.elena', 'parola4', 'token4', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'ionescu.elena@example.com')),
  ('gheorghe.danut', 'parola5', 'token5', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'gheorghe.danut@example.com')),
  ('vasile.roxana', 'parola6', 'token6', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'vasile.roxana@example.com')),
  ('radu.marius', 'parola7', 'token7', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'radu.marius@example.com')),
  ('dumitrescu.andreea', 'parola8', 'token8', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'dumitrescu.andreea@example.com')),
  ('georgescu.alexandru', 'parola9', 'token9', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'georgescu.alexandru@example.com')),
  ('mihai.diana', 'parola10', 'token10', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'mihai.diana@example.com')),
  ('stanescu.valentin', 'parola11', 'token11', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'stanescu.valentin@example.com')),
  ('gavrila.catalin', 'parola12', 'token12', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'gavrila.catalin@example.com')),
  ('constantinescu.ioana', 'parola13', 'token13', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'constantinescu.ioana@example.com')),
  ('balaceanu.silviu', 'parola14', 'token14', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'balaceanu.silviu@example.com')),
  ('nistor.maria', 'parola15', 'token15', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'nistor.maria@example.com')),
  ('cojocaru.valeria', 'parola16', 'token16', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'cojocaru.valeria@example.com')),
  ('florea.bogdan', 'parola17', 'token17', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'florea.bogdan@example.com')),
  ('dragomir.gabriela', 'parola18', 'token18', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'dragomir.gabriela@example.com')),
  ('marin.raul', 'parola19', 'token19', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'marin.raul@example.com')),
  ('grosu.daniela', 'parola20', 'token20', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'grosu.daniela@example.com')),
  ('constantin.andrei', 'parola21', 'token21', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'constantin.andrei@example.com')),
  ('gheorghe.mirela', 'parola22', 'token22', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'gheorghe.mirela@example.com')),
  ('georgescu.elena', 'parola23', 'token23', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'georgescu.elena@example.com')),
  ('mihai.george', 'parola24', 'token24', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'mihai.george@example.com')),
  ('dumitrescu.alex', 'parola25', 'token25', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'dumitrescu.alex@example.com')),
  ('ionescu.maria', 'parola26', 'token26', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'ionescu.maria@example.com')),
  ('munteanu.alina', 'parola27', 'token27', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'munteanu.alina@example.com')),
  ('ivanescu.gabriel', 'parola28', 'token28', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'ivanescu.gabriel@example.com')),
  ('stanciu.laura', 'parola29', 'token29', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'stanciu.laura@example.com')),
  ('popa.ana', 'parola30', 'token30', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'popa.ana@example.com')),
  ('iancu.elena', 'parola31', 'token31', (SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'iancu.elena@example.com'));



  
INSERT INTO SuperAdministratori (ID_Utilizator)
VALUES 
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'mihai.george@example.com')),
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'dumitrescu.alex@example.com'));


INSERT INTO Administratori (ID_Utilizator)
VALUES 
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'georgescu.elena@example.com')),
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'gheorghe.mirela@example.com'));


INSERT INTO Profesori (ID_Utilizator, Departament, Ore_Minim, Ore_Maxim)
VALUES 
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'ionescu.maria@example.com'), 'Matematica', 10, 20),
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'munteanu.alina@example.com'), 'Fizica', 15, 25),
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'popa.ana@example.com'), 'Informatica', 12, 22),
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'iancu.elena@example.com'), 'Chimie', 18, 28),
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'ivanescu.gabriel@example.com'), 'Biologie', 14, 24),
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'stanciu.laura@example.com'), 'Istorie', 16, 26);




INSERT INTO Studenti (ID_Utilizator, An_Studiu, Ore_Sustinute)
VALUES 
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'vasilescu.adrian@example.com'), 2, 20),
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'popescu.ion@example.com'), 1, 30),
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'popescu.cristian@example.com'), 3, 40),
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'ionescu.elena@example.com'), 2, 30),
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'gheorghe.danut@example.com'), 1, 20),
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'vasile.roxana@example.com'), 3, 20),
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'radu.marius@example.com'), 2, 30),
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'dumitrescu.andreea@example.com'), 1, 20),
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'georgescu.alexandru@example.com'), 3, 30),
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'mihai.diana@example.com'), 2, 20),
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'stanescu.valentin@example.com'), 1, 30),
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'gavrila.catalin@example.com'), 3, 20),
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'constantinescu.ioana@example.com'), 2, 30),
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'balaceanu.silviu@example.com'), 1, 30),
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'nistor.maria@example.com'), 3, 20),
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'cojocaru.valeria@example.com'), 2, 20),
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'florea.bogdan@example.com'), 1, 30),
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'dragomir.gabriela@example.com'), 3, 20),
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'marin.raul@example.com'), 2, 30),
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'grosu.daniela@example.com'), 1, 30),
  ((SELECT ID_Utilizator FROM Utilizatori WHERE Email = 'constantin.andrei@example.com'), 3, 20);





-- Populare tabela Tip_Activitate
INSERT INTO Tip_Activitate (Nume_Tip_Activitate) VALUES
  ('Curs'),
  ('Seminar'),
  ('Laborator'),
  ('Examen');



INSERT INTO Discipline (Nume_Disciplina, Descriere,Departament)
VALUES
  ('Algebra Liniara', 'Cursul acopera notiuni de algebra liniara si geometrie analitica necesare in diverse domenii ale stiintelor.','Matematica'),
  ('Analiza Matematica', 'Cursul acopera notiuni de analiza matematica, inclusiv limite, derivate, integrale, si aplicatii in diverse domenii.','Matematica'),
  
  ('Mecanica Clasica', 'Cursul acopera legile miscarii pentru corpuri solide si fluide, cu aplicatii in fizica clasica.','Fizica'),
  ('Fizica Cuantica', 'Cursul acopera principiile fizicii cuantice, structura atomica si fenomene cuantice.','Fizica'),
  
  ('Baze de Date', 'Cursul acopera concepte si tehnologii legate de gestionarea bazelor de date.','Informatica'),
  ('Algoritmi Fundamentali', 'Cursul acopera algoritmii fundamentali, structurile de date si metode de rezolvare a problemelor algoritmice.','Informatica'),
  
  ('Chimie Organica', 'Cursul acopera compusii organici si reactiile lor chimice.','Chimie'),
  ('Chimie Anorganica', 'Cursul acopera compusii anorganici si principiile chimiei anorganice.','Chimie'),
  
  ('Biologie Vegetala', 'Cursul acopera structura si functiile plantelor.','Biologie'),
  ('Anatomie', 'Cursul acopera studiul structurii organismului uman.','Biologie'),
  
  ('Istorie Universala', 'Cursul acopera evenimente si procese majore din istoria umanitatii.','Istorie'),
  ('Istoria Europei', 'Cursul acopera istoria Europei de la inceputuri pana in prezent.','Istorie'),
  
  ('Climatologie','Cursul acopera principii fundamentale legate de clima planetei Pamant.','Geografie'),
  ('Geografie Economica','Cursul acopera elemente de baza ale economiei globale.','Geografie');
  
  



-- Obține ID-ul profesorilor corespunzători
SET @id_ionescu_maria = (SELECT ID_Profesor FROM Profesori WHERE ID_Utilizator = (SELECT ID_Utilizator FROM Utilizatori WHERE Nume = 'Ionescu' AND Prenume = 'Maria'));
SET @id_munteanu_alina = (SELECT ID_Profesor FROM Profesori WHERE ID_Utilizator = (SELECT ID_Utilizator FROM Utilizatori WHERE Nume = 'Munteanu' AND Prenume = 'Alina'));
SET @id_popa_ana = (SELECT ID_Profesor FROM Profesori WHERE ID_Utilizator = (SELECT ID_Utilizator FROM Utilizatori WHERE Nume = 'Popa' AND Prenume = 'Ana'));
SET @id_iancu_elena = (SELECT ID_Profesor FROM Profesori WHERE ID_Utilizator = (SELECT ID_Utilizator FROM Utilizatori WHERE Nume = 'Iancu' AND Prenume = 'Elena'));
SET @id_ivanescu_gabriel = (SELECT ID_Profesor FROM Profesori WHERE ID_Utilizator = (SELECT ID_Utilizator FROM Utilizatori WHERE Nume = 'Ivanescu' AND Prenume = 'Gabriel'));
SET @id_stanciu_laura = (SELECT ID_Profesor FROM Profesori WHERE ID_Utilizator = (SELECT ID_Utilizator FROM Utilizatori WHERE Nume = 'Stanciu' AND Prenume = 'Laura'));

-- Asociază disciplinele cu profesorii
INSERT INTO Asociere_Disciplina_Profesor (ID_Disciplina, ID_Profesor)
VALUES
  ((SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Algebra Liniara'), @id_ionescu_maria),
  ((SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Analiza Matematica'), @id_ionescu_maria),
  
  ((SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Mecanica Clasica'), @id_munteanu_alina),
  ((SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Fizica Cuantica'), @id_munteanu_alina),
  
  ((SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Baze de Date'), @id_popa_ana),
  ((SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Algoritmi Fundamentali'), @id_popa_ana),
  
  ((SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Chimie Organica'), @id_iancu_elena),
  ((SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Chimie Anorganica'), @id_iancu_elena),
  
  ((SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Biologie Vegetala'), @id_ivanescu_gabriel),
  ((SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Anatomie'), @id_ivanescu_gabriel),
  
  ((SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Istorie Universala'), @id_stanciu_laura),
  ((SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Istoria Europei'), @id_stanciu_laura);




 -- ID_Disciplina pentru fiecare disciplină
SET @id_matematica_algebra = (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Algebra Liniara');
SET @id_matematica_analiza = (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Analiza Matematica');
SET @id_fizica_clasica = (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Mecanica Clasica');
SET @id_fizica_cuantica = (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Fizica Cuantica');
SET @id_informatica = (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Baze de Date');
SET @id_algoritmi = (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Algoritmi Fundamentali');
SET @id_chimie_organica = (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Chimie Organica');
SET @id_chimie_anorganica = (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Chimie Anorganica');
SET @id_biologie_vegetala = (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Biologie Vegetala');
SET @id_anatomie = (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Anatomie');
SET @id_istorie_universala = (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Istorie Universala');
SET @id_istoria_europei = (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Istoria Europei');
SET @id_climatologie = (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Climatologie');
SET @id_geografie_economica = (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Geografie Economica');

-- ID_Tip_Activitate pentru fiecare tip de activitate
SET @id_curs = (SELECT ID_Tip_Activitate FROM Tip_Activitate WHERE Nume_Tip_Activitate = 'Curs');
SET @id_seminar = (SELECT ID_Tip_Activitate FROM Tip_Activitate WHERE Nume_Tip_Activitate = 'Seminar');
SET @id_laborator = (SELECT ID_Tip_Activitate FROM Tip_Activitate WHERE Nume_Tip_Activitate = 'Laborator');


  
  
INSERT INTO Grupuri_Studiu (ID_Disciplina, Nume_Grup, Descriere)
VALUES
  (@id_matematica_algebra, 'Grup_Algebra', 'Grup pentru a comunica in legatura cu algebra liniara'),
  (@id_matematica_analiza, 'Grup_Analiza', 'Grup pentru a comunica in legatura cu analiza matematica'),
  (@id_fizica_clasica, 'Grup_Mecanica', 'Grup pentru a comunica in legatura cu mecanica clasica'),
  (@id_fizica_cuantica, 'Grup_Fizica_Cuantica', 'Grup pentru a comunica in legatura cu fizica cuantica'),
  (@id_informatica, 'Grup_Baze_de_Date', 'Grup pentru a comunica in legatura cu bazele de date'),
  (@id_algoritmi, 'Grup_Algoritmi', 'Grup pentru a comunica in legatura cu algoritmii fundamentali'),
  (@id_chimie_organica, 'Grup_Chimie_Organica', 'Grup pentru a comunica in legatura cu chimia organica'),
  (@id_chimie_anorganica, 'Grup_Chimie_Anorganica', 'Grup pentru a comunica in legatura cu chimia anorganica'),
  (@id_biologie_vegetala, 'Grup_Biologie_Vegetala', 'Grup pentru a comunica in legatura cu biologia vegetala'),
  (@id_anatomie, 'Grup_Anatomie', 'Grup pentru a comunica in legatura cu anatomia'),
  (@id_istorie_universala, 'Grup_Istorie_Universala', 'Grup pentru a comunica in legatura cu istoria universala'),
  (@id_istoria_europei, 'Grup_Istoria_Europei', 'Grup pentru a comunica in legatura cu istoria Europei'),
  (@id_climatologie, 'Grup_Climatologie', 'Grup pentru a comunica in legatura cu climatologia');

-- Geografie Economica
INSERT INTO Grupuri_Studiu (ID_Disciplina, Nume_Grup, Descriere)
VALUES
  (@id_geografie_economica, 'Grup_Geografie_Economica', 'Grup pentru a comunica in legatura cu geografia economica');



-- Programează activitățile de tip curs pentru fiecare disciplină cu profesorii corespunzători
CALL ProgramareActivitate(@id_ionescu_maria, @id_matematica_algebra, @id_curs, '2024-01-22', '08:00:00', '10:00:00', '2025-01-10', 50);
CALL ProgramareActivitate(@id_ionescu_maria, @id_matematica_analiza, @id_curs, '2024-01-22', '10:00:00', '12:00:00', '2025-01-11', 50);

CALL ProgramareActivitate(@id_munteanu_alina, @id_fizica_clasica, @id_curs, '2024-01-23', '14:00:00', '16:00:00', '2025-01-12', 50);
CALL ProgramareActivitate(@id_munteanu_alina, @id_fizica_cuantica, @id_curs, '2024-01-23', '08:00:00', '10:00:00', '2025-01-13', 50);

CALL ProgramareActivitate(@id_popa_ana, @id_informatica, @id_curs, '2024-01-24', '12:00:00', '14:00:00', '2025-01-14', 50);
CALL ProgramareActivitate(@id_popa_ana, @id_algoritmi, @id_curs, '2024-01-24', '14:00:00', '16:00:00', '2025-01-15', 50);

CALL ProgramareActivitate(@id_iancu_elena, @id_chimie_organica, @id_curs, '2024-01-25', '08:00:00', '10:00:00', '2025-01-16', 50);
CALL ProgramareActivitate(@id_iancu_elena, @id_chimie_anorganica, @id_curs, '2024-01-25', '10:00:00', '12:00:00', '2025-01-17', 50);

CALL ProgramareActivitate(@id_ivanescu_gabriel, @id_biologie_vegetala, @id_curs, '2024-01-26', '14:00:00', '16:00:00', '2025-01-18', 50);
CALL ProgramareActivitate(@id_ivanescu_gabriel, @id_anatomie, @id_curs, '2024-01-26', '08:00:00', '10:00:00', '2025-01-19', 50);

CALL ProgramareActivitate(@id_stanciu_laura, @id_istorie_universala, @id_curs, '2024-01-27', '10:00:00', '12:00:00', '2025-01-20', 50);
CALL ProgramareActivitate(@id_stanciu_laura, @id_istoria_europei, @id_curs, '2024-01-27', '14:00:00', '16:00:00', '2025-01-21', 50);



-- Apelează procedura pentru Algebra Liniara
CALL InscriereStudentDisciplinaProfesor(1, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Algebra Liniara'), @id_ionescu_maria);
CALL InscriereStudentDisciplinaProfesor(2, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Algebra Liniara'), @id_ionescu_maria);
CALL InscriereStudentDisciplinaProfesor(3, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Algebra Liniara'), @id_ionescu_maria);
CALL InscriereStudentDisciplinaProfesor(4, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Algebra Liniara'), @id_ionescu_maria);
CALL InscriereStudentDisciplinaProfesor(5, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Algebra Liniara'), @id_ionescu_maria);
CALL InscriereStudentDisciplinaProfesor(6, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Algebra Liniara'), @id_ionescu_maria);
CALL InscriereStudentDisciplinaProfesor(7, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Algebra Liniara'), @id_ionescu_maria);

-- Apelează procedura pentru Analiza Matematica
CALL InscriereStudentDisciplinaProfesor(2, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Analiza Matematica'), @id_ionescu_maria);
CALL InscriereStudentDisciplinaProfesor(3, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Analiza Matematica'), @id_ionescu_maria);
CALL InscriereStudentDisciplinaProfesor(4, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Analiza Matematica'), @id_ionescu_maria);
CALL InscriereStudentDisciplinaProfesor(5, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Analiza Matematica'), @id_ionescu_maria);
CALL InscriereStudentDisciplinaProfesor(6, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Analiza Matematica'), @id_ionescu_maria);
CALL InscriereStudentDisciplinaProfesor(7, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Analiza Matematica'), @id_ionescu_maria);
CALL InscriereStudentDisciplinaProfesor(8, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Analiza Matematica'), @id_ionescu_maria);

-- Apelează procedura pentru Mecanica Clasica
CALL InscriereStudentDisciplinaProfesor(4, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Mecanica Clasica'), @id_munteanu_alina);
CALL InscriereStudentDisciplinaProfesor(5, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Mecanica Clasica'), @id_munteanu_alina);
CALL InscriereStudentDisciplinaProfesor(10, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Mecanica Clasica'), @id_munteanu_alina);
CALL InscriereStudentDisciplinaProfesor(14, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Mecanica Clasica'), @id_munteanu_alina);
CALL InscriereStudentDisciplinaProfesor(20, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Mecanica Clasica'), @id_munteanu_alina);
CALL InscriereStudentDisciplinaProfesor(13, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Mecanica Clasica'), @id_munteanu_alina);


-- Apelează procedura pentru Fizica Cuantica
CALL InscriereStudentDisciplinaProfesor(2, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Fizica Cuantica'), @id_munteanu_alina);
CALL InscriereStudentDisciplinaProfesor(3, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Fizica Cuantica'), @id_munteanu_alina);
CALL InscriereStudentDisciplinaProfesor(5, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Fizica Cuantica'), @id_munteanu_alina);
CALL InscriereStudentDisciplinaProfesor(6, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Fizica Cuantica'), @id_munteanu_alina);
CALL InscriereStudentDisciplinaProfesor(10, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Fizica Cuantica'), @id_munteanu_alina);
CALL InscriereStudentDisciplinaProfesor(12, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Fizica Cuantica'), @id_munteanu_alina);
CALL InscriereStudentDisciplinaProfesor(14, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Fizica Cuantica'), @id_munteanu_alina);



CALL InscriereStudentDisciplinaProfesor(3, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Baze de Date'), @id_popa_ana);
CALL InscriereStudentDisciplinaProfesor(4, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Baze de Date'), @id_popa_ana);
CALL InscriereStudentDisciplinaProfesor(12, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Baze de Date'), @id_popa_ana);
CALL InscriereStudentDisciplinaProfesor(5, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Baze de Date'), @id_popa_ana);
CALL InscriereStudentDisciplinaProfesor(6, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Baze de Date'), @id_popa_ana);
CALL InscriereStudentDisciplinaProfesor(8, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Baze de Date'), @id_popa_ana);
CALL InscriereStudentDisciplinaProfesor(9, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Baze de Date'), @id_popa_ana);

CALL InscriereStudentDisciplinaProfesor(12, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Algoritmi Fundamentali'), @id_popa_ana);
CALL InscriereStudentDisciplinaProfesor(5, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Algoritmi Fundamentali'), @id_popa_ana);
CALL InscriereStudentDisciplinaProfesor(4, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Algoritmi Fundamentali'), @id_popa_ana);
CALL InscriereStudentDisciplinaProfesor(6, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Algoritmi Fundamentali'), @id_popa_ana);
CALL InscriereStudentDisciplinaProfesor(8, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Algoritmi Fundamentali'), @id_popa_ana);
CALL InscriereStudentDisciplinaProfesor(9, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Algoritmi Fundamentali'), @id_popa_ana);
CALL InscriereStudentDisciplinaProfesor(10, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Algoritmi Fundamentali'), @id_popa_ana);


-- Apelează procedura pentru Chimie Organica
CALL InscriereStudentDisciplinaProfesor(8, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Chimie Organica'), @id_iancu_elena);
CALL InscriereStudentDisciplinaProfesor(12, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Chimie Organica'), @id_iancu_elena);
CALL InscriereStudentDisciplinaProfesor(16, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Chimie Organica'), @id_iancu_elena);
CALL InscriereStudentDisciplinaProfesor(17, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Chimie Organica'), @id_iancu_elena);
CALL InscriereStudentDisciplinaProfesor(18, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Chimie Organica'), @id_iancu_elena);
CALL InscriereStudentDisciplinaProfesor(19, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Chimie Organica'), @id_iancu_elena);

-- Apelează procedura pentru Chimie Anorganica
CALL InscriereStudentDisciplinaProfesor(8, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Chimie Anorganica'), @id_iancu_elena);
CALL InscriereStudentDisciplinaProfesor(13, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Chimie Anorganica'), @id_iancu_elena);
CALL InscriereStudentDisciplinaProfesor(12, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Chimie Anorganica'), @id_iancu_elena);
CALL InscriereStudentDisciplinaProfesor(18, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Chimie Anorganica'), @id_iancu_elena);
CALL InscriereStudentDisciplinaProfesor(20, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Chimie Anorganica'), @id_iancu_elena);
CALL InscriereStudentDisciplinaProfesor(19, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Chimie Anorganica'), @id_iancu_elena);


-- Apelează procedura pentru Biologie Vegetala
CALL InscriereStudentDisciplinaProfesor(1, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Biologie Vegetala'), @id_ivanescu_gabriel);
CALL InscriereStudentDisciplinaProfesor(2, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Biologie Vegetala'), @id_ivanescu_gabriel);
CALL InscriereStudentDisciplinaProfesor(4, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Biologie Vegetala'), @id_ivanescu_gabriel);
CALL InscriereStudentDisciplinaProfesor(6, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Biologie Vegetala'), @id_ivanescu_gabriel);
CALL InscriereStudentDisciplinaProfesor(18, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Biologie Vegetala'), @id_ivanescu_gabriel);
CALL InscriereStudentDisciplinaProfesor(20, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Biologie Vegetala'), @id_ivanescu_gabriel);
CALL InscriereStudentDisciplinaProfesor(13, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Biologie Vegetala'), @id_ivanescu_gabriel);



-- Apelează procedura pentru Anatomie
CALL InscriereStudentDisciplinaProfesor(4, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Anatomie'), @id_ivanescu_gabriel);
CALL InscriereStudentDisciplinaProfesor(5, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Anatomie'), @id_ivanescu_gabriel);
CALL InscriereStudentDisciplinaProfesor(6, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Anatomie'), @id_ivanescu_gabriel);
CALL InscriereStudentDisciplinaProfesor(7, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Anatomie'), @id_ivanescu_gabriel);
CALL InscriereStudentDisciplinaProfesor(8, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Anatomie'), @id_ivanescu_gabriel);
CALL InscriereStudentDisciplinaProfesor(12, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Anatomie'), @id_ivanescu_gabriel);
CALL InscriereStudentDisciplinaProfesor(14, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Anatomie'), @id_ivanescu_gabriel);
CALL InscriereStudentDisciplinaProfesor(16, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Anatomie'), @id_ivanescu_gabriel);


-- Apelează procedura pentru Istorie Universala
CALL InscriereStudentDisciplinaProfesor(13, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Istorie Universala'), @id_stanciu_laura);
CALL InscriereStudentDisciplinaProfesor(17, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Istorie Universala'), @id_stanciu_laura);
CALL InscriereStudentDisciplinaProfesor(1, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Istorie Universala'), @id_stanciu_laura);
CALL InscriereStudentDisciplinaProfesor(19, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Istorie Universala'), @id_stanciu_laura);
CALL InscriereStudentDisciplinaProfesor(18, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Istorie Universala'), @id_stanciu_laura);
CALL InscriereStudentDisciplinaProfesor(2, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Istorie Universala'), @id_stanciu_laura);
CALL InscriereStudentDisciplinaProfesor(13, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Istorie Universala'), @id_stanciu_laura);



-- Apelează procedura pentru Istoria Europei
CALL InscriereStudentDisciplinaProfesor(2, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Istoria Europei'), @id_stanciu_laura);
CALL InscriereStudentDisciplinaProfesor(3, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Istoria Europei'), @id_stanciu_laura);
CALL InscriereStudentDisciplinaProfesor(4, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Istoria Europei'), @id_stanciu_laura);
CALL InscriereStudentDisciplinaProfesor(11, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Istoria Europei'), @id_stanciu_laura);
CALL InscriereStudentDisciplinaProfesor(12, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Istoria Europei'), @id_stanciu_laura);
CALL InscriereStudentDisciplinaProfesor(16, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Istoria Europei'), @id_stanciu_laura);
CALL InscriereStudentDisciplinaProfesor(8, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Istoria Europei'), @id_stanciu_laura);
CALL InscriereStudentDisciplinaProfesor(20, (SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = 'Istoria Europei'), @id_stanciu_laura);


