package com.example.javafx;// Importurile necesare pentru lucrul cu JDBC și SQL
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Clasa pentru gestionarea conexiunii la baza de date
public class ConexiuneBazaDate {

    // Detalii de conectare la baza de date
    private static final String URL = "jdbc:mysql://localhost:3306/PDE_testing2";
    private static final String UTILIZATOR = "root";
    private static final String PAROLA = "password";

    // Variabilă statică pentru stocarea conexiunii
    private static Connection conexiune;

    // Bloc static care se execută o singură dată la încărcarea clasei
    static {
        try {
            // Încarcă driverul JDBC pentru MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Creează conexiunea la baza de date folosind detaliile de conectare
            conexiune = DriverManager.getConnection(URL, UTILIZATOR, PAROLA);
        } catch (ClassNotFoundException | SQLException e) {
            // Afișează detalii despre orice excepție care apare
            e.printStackTrace();
        }
    }

    // Metodă pentru a obține conexiunea la baza de date
    public static Connection obtineConexiune() {
        return conexiune;
    }
}
