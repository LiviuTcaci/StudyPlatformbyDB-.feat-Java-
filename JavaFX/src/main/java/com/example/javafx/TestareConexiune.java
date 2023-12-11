package com.example.javafx;// Importurile necesare pentru lucrul cu JDBC și SQL
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Clasa principală pentru testarea conexiunii la baza de date
public class TestareConexiune {

    public static void main(String[] args) {
        // Inițializare variabilă pentru conexiune
        Connection conexiune = null;

        try {
            // Încarcă driverul JDBC pentru MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Detalii de conectare la baza de date
            String url = "jdbc:mysql://localhost:3306/PDE_testing2";
            String utilizator = "root"; // Înlocuiește cu numele utilizatorului tău
            String parola = "password"; // Înlocuiește cu parola ta

            // Încearcă să stabilească conexiunea
            conexiune = DriverManager.getConnection(url, utilizator, parola);

            // Verifică dacă conexiunea a fost reușită
            if (conexiune != null) {
                System.out.println("Conexiune reușită!");

                // Exemplu de interogare SELECT pentru tabela UtilizatoriAutentificare
                String sqlSelect = "SELECT * FROM UtilizatoriAutentificare";

                // Începe o interogare SQL folosind PreparedStatement
                try (PreparedStatement preparedStatement = conexiune.prepareStatement(sqlSelect);
                     ResultSet resultSet = preparedStatement.executeQuery()) {

                    // Parcurge rezultatul interogării și afișează informațiile
                    while (resultSet.next()) {
                        int idUtilizator = resultSet.getInt("ID_Utilizator");
                        String numeUtilizator = resultSet.getString("Nume_Utilizator");
                        String parolaUtilizator = resultSet.getString("Parola");
                        String tokenAutentificare = resultSet.getString("Token_Autentificare");

                        // Afișează detalii despre fiecare utilizator
                        System.out.println("ID_Utilizator: " + idUtilizator +  ", Nume_Utilizator: " + numeUtilizator +
                                ", Parola: " + parolaUtilizator + ", Token_Autentificare: " + tokenAutentificare);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("Conexiune eșuată!");
            }
        } catch (ClassNotFoundException e) {
            // Tratează excepția dacă driverul nu a fost găsit
            System.out.println("Driverul MySQL nu a fost găsit.");
            e.printStackTrace();
        } catch (SQLException e) {
            // Tratează excepția dacă există o eroare de conectare la bază de date
            System.out.println("Eroare de conectare la baza de date.");
            e.printStackTrace();
        } finally {
            // Închide conexiunea indiferent de rezultat
            if (conexiune != null) {
                try {
                    conexiune.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
