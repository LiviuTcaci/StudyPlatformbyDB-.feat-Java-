USE PDE_testing2;


/*
-- Creare roluri
CREATE ROLE Student;
CREATE ROLE Profesor;
CREATE ROLE Administrator;
CREATE ROLE SuperAdministrator;


*/

/*
-- Administratori
CREATE USER 'gheorghe.mirela'@'%' IDENTIFIED BY 'parola22';
CREATE USER 'georgescu.elena'@'%' IDENTIFIED BY 'parola23';
CREATE USER 'mihai.george'@'%' IDENTIFIED BY 'parola24';
CREATE USER 'dumitrescu.alex'@'%' IDENTIFIED BY 'parola25';

-- Profesori
CREATE USER 'ionescu.maria'@'%' IDENTIFIED BY 'parola26';
CREATE USER 'munteanu.alina'@'%' IDENTIFIED BY 'parola27';
CREATE USER 'ivanescu.gabriel'@'%' IDENTIFIED BY 'parola28';
CREATE USER 'stanciu.laura'@'%' IDENTIFIED BY 'parola29';
CREATE USER 'popa.ana'@'%' IDENTIFIED BY 'parola30';
CREATE USER 'iancu.elena'@'%' IDENTIFIED BY 'parola31';

-- Studenți
CREATE USER 'vasilescu.adrian'@'%' IDENTIFIED BY 'parola1';
CREATE USER 'popescu.ion'@'%' IDENTIFIED BY 'parola2';
CREATE USER 'popescu.cristian'@'%' IDENTIFIED BY 'parola3';
CREATE USER 'ionescu.elena'@'%' IDENTIFIED BY 'parola4';
CREATE USER 'gheorghe.danut'@'%' IDENTIFIED BY 'parola5';
CREATE USER 'vasile.roxana'@'%' IDENTIFIED BY 'parola6';
CREATE USER 'radu.marius'@'%' IDENTIFIED BY 'parola7';
CREATE USER 'dumitrescu.andreea'@'%' IDENTIFIED BY 'parola8';
CREATE USER 'georgescu.alexandru'@'%' IDENTIFIED BY 'parola9';
CREATE USER 'mihai.diana'@'%' IDENTIFIED BY 'parola10';
CREATE USER 'stanescu.valentin'@'%' IDENTIFIED BY 'parola11';
CREATE USER 'gavrila.catalin'@'%' IDENTIFIED BY 'parola12';
CREATE USER 'constantinescu.ioana'@'%' IDENTIFIED BY 'parola13';
CREATE USER 'balaceanu.silviu'@'%' IDENTIFIED BY 'parola14';
CREATE USER 'nistor.maria'@'%' IDENTIFIED BY 'parola15';
CREATE USER 'cojocaru.valeria'@'%' IDENTIFIED BY 'parola16';
CREATE USER 'florea.bogdan'@'%' IDENTIFIED BY 'parola17';
CREATE USER 'dragomir.gabriela'@'%' IDENTIFIED BY 'parola18';
CREATE USER 'marin.raul'@'%' IDENTIFIED BY 'parola19';
CREATE USER 'grosu.daniela'@'%' IDENTIFIED BY 'parola20';
CREATE USER 'constantin.andrei'@'%' IDENTIFIED BY 'parola21';

*/

GRANT ALL PRIVILEGES ON *.* TO 'gheorghe.mirela'@'%';
GRANT ALL PRIVILEGES ON *.* TO 'georgescu.elena'@'%';
GRANT ALL PRIVILEGES ON *.* TO 'mihai.george'@'%';
GRANT ALL PRIVILEGES ON *.* TO 'dumitrescu.alex'@'%';

-- Grant pentru Profesori
GRANT SELECT, INSERT, UPDATE, DELETE ON baza_de_date.* TO 'ionescu.maria'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON baza_de_date.* TO 'munteanu.alina'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON baza_de_date.* TO 'ivanescu.gabriel'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON baza_de_date.* TO 'stanciu.laura'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON baza_de_date.* TO 'popa.ana'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON baza_de_date.* TO 'iancu.elena'@'%';


-- Grant pentru Studenți
GRANT SELECT, INSERT, UPDATE, DELETE ON baza_de_date.* TO 'vasilescu.adrian'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON baza_de_date.* TO 'popescu.ion'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON baza_de_date.* TO 'popescu.cristian'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON baza_de_date.* TO 'ionescu.elena'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON baza_de_date.* TO 'gheorghe.danut'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON baza_de_date.* TO 'vasile.roxana'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON baza_de_date.* TO 'radu.marius'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON baza_de_date.* TO 'dumitrescu.andreea'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON baza_de_date.* TO 'georgescu.alexandru'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON baza_de_date.* TO 'mihai.diana'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON baza_de_date.* TO 'stanescu.valentin'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON baza_de_date.* TO 'gavrila.catalin'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON baza_de_date.* TO 'constantinescu.ioana'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON baza_de_date.* TO 'balaceanu.silviu'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON baza_de_date.* TO 'nistor.maria'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON baza_de_date.* TO 'cojocaru.valeria'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON baza_de_date.* TO 'florea.bogdan'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON baza_de_date.* TO 'dragomir.gabriela'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON baza_de_date.* TO 'marin.raul'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON baza_de_date.* TO 'grosu.daniela'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON baza_de_date.* TO 'constantin.andrei'@'%';

