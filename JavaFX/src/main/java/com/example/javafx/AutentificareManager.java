package com.example.javafx;// Importurile necesare pentru lucrul cu JDBC și SQL
import java.sql.*;

// Clasa pentru gestionarea autentificării utilizatorilor
public class AutentificareManager {

    // Metodă pentru autentificarea unui utilizator
    public static String autentificareUtilizator(String numeUtilizator, String parola) {
        try (Connection conexiune = ConexiuneBazaDate.obtineConexiune();
             CallableStatement statement = conexiune.prepareCall("{CALL AutentificareUtilizator(?, ?)}")) {

            // Setează parametrii pentru procedura stocată
            statement.setString(1, numeUtilizator);
            statement.setString(2, parola);

            // Execută procedura stocată și primește rezultatele
            ResultSet resultSet = statement.executeQuery();

            // Verifică dacă există rezultate și returnează primul rezultat
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            // Afișează detalii despre orice excepție care apare în timpul executării
            e.printStackTrace();
        }

        // Returnează null în cazul în care autentificarea nu reușește
        return null;
    }

    // Metodă de test pentru a exemplifica folosirea metodei de autentificare
    public static void main(String[] args) {
        // Realizează o autentificare cu un nume de utilizator și o parolă date
        String rezultatAutentificare = autentificareUtilizator("popescu_ion", "parola123");

        // Afișează rezultatul autentificării
        System.out.println(rezultatAutentificare);
    }
}
