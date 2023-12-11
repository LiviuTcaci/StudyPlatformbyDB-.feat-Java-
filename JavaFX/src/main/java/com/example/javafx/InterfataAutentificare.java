package com.example.javafx;// Importurile necesare pentru JavaFX și JDBC
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.*;

// Clasa principală pentru interfața de autentificare în aplicație
public class InterfataAutentificare extends Application {

    public static void main(String[] args) {
        // Metoda de lansare a aplicației JavaFX
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Configurarea scenei principale
        primaryStage.setTitle("Autentificare");
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Adăugarea elementelor UI pe grilă
        Label labelNumeUtilizator = new Label("Nume utilizator:");
        grid.add(labelNumeUtilizator, 0, 1);

        TextField textFieldNumeUtilizator = new TextField();
        grid.add(textFieldNumeUtilizator, 1, 1);

        Label labelParola = new Label("Parola:");
        grid.add(labelParola, 0, 2);

        PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 2);

        Button buttonAutentificare = new Button("Autentificare");
        grid.add(buttonAutentificare, 1, 4);

        // Acțiunea de la apăsarea butonului de autentificare
        buttonAutentificare.setOnAction(e -> {
            // Obține datele introduse în câmpuri
            String numeUtilizator = textFieldNumeUtilizator.getText();
            String parola = passwordField.getText();

            // Încearcă conectarea la baza de date
            try (Connection conexiune = DriverManager.getConnection("jdbc:mysql://localhost:3306/PDE_testing2", "root", "password")) {
                if (conexiune != null) {
                    System.out.println("Conexiune reușită!");

                    // Construiește query-ul pentru autentificare și obține tipul utilizatorului
                    String sqlSelect = "SELECT Utilizatori.Tip_Utilizator FROM Utilizatori JOIN UtilizatoriAutentificare ON Utilizatori.ID_Utilizator = UtilizatoriAutentificare.ID_Utilizator WHERE UtilizatoriAutentificare.Nume_Utilizator = ? AND UtilizatoriAutentificare.Parola = ?";
                    try (PreparedStatement preparedStatement = conexiune.prepareStatement(sqlSelect)) {
                        preparedStatement.setString(1, numeUtilizator);
                        preparedStatement.setString(2, parola);

                        try (ResultSet resultSet = preparedStatement.executeQuery()) {
                            // În partea unde verifici rezultatul query-ului
                            if (resultSet.next()) {
                                // Obține tipul utilizatorului
                                String tipUtilizator = resultSet.getString("Tip_Utilizator");

                                // Redirecționează către interfața corespunzătoare
                                UserInterface userInterface = getUserInterfaceByType(tipUtilizator, conexiune);
                                userInterface.displayUI(primaryStage, numeUtilizator);
                            } else {
                                System.out.println("Autentificare eșuată. Numele de utilizator sau parola incorecte.");
                            }
                        }
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Eroare de conectare la baza de date.");
                ex.printStackTrace();
            }
        });

        // Configurarea scenei și afișarea ferestrei
        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Metodă pentru obținerea interfeței utilizator corespunzătoare tipului de utilizator
    private UserInterface getUserInterfaceByType(String tipUtilizator, Connection conexiune) {
        switch (tipUtilizator) {
            case "Student":
                return new StudentInterface(conexiune);
            case "Profesor":
                return new ProfesorInterface(conexiune);
            case "Administrator":
                return new AdminInterface(conexiune);
            case "SuperAdministrator":
                return new SuperAdminInterface(conexiune);
            default:
                throw new IllegalArgumentException("Tip de utilizator necunoscut: " + tipUtilizator);
        }
    }
}
