package com.example.javafx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javafx.geometry.Pos;
import javafx.scene.text.Font;


import java.sql.*;

public class InterfataAutentificare extends Application {

    private StackPane root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        root = new StackPane();
        root.getChildren().addAll(createAutentificareUI(primaryStage), createInregistrareUI(primaryStage));
        root.getChildren().get(1).setVisible(false);  // Începem cu interfața de autentificare vizibilă

        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Opțiuni");
        MenuItem autentificareItem = new MenuItem("Autentificare");
        MenuItem inregistrareItem = new MenuItem("Înregistrare");

        autentificareItem.setOnAction(e -> toggleUI(true));
        inregistrareItem.setOnAction(e -> toggleUI(false));

        menu.getItems().addAll(autentificareItem, inregistrareItem);
        menuBar.getMenus().add(menu);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setCenter(root);



        Scene scene = new Scene(borderPane, 400, 500);
        primaryStage.setTitle("Autentificare-Inregistrare");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    private void toggleUI(boolean showAutentificare) {
        root.getChildren().get(0).setVisible(showAutentificare);
        root.getChildren().get(1).setVisible(!showAutentificare);
    }

    private BorderPane createAutentificareUI(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(25, 25, 25, 25));

        // Adaugă un Label pentru textul "Conectare"
        Label labelConectare = new Label("Conectare");
        labelConectare.setFont(Font.font(16));
        labelConectare.setStyle("-fx-font-weight: bold;");


        // Crează casetele pentru utilizator și parolă și butonul de autentificare
        Label labelNumeUtilizator = new Label("Nume utilizator:");
        TextField textFieldNumeUtilizator = new TextField();
        Label labelParola = new Label("Parola:");
        PasswordField passwordField = new PasswordField();
        Button buttonAutentificare = new Button("Autentificare");

        // Crează un GridPane pentru a aranja elementele
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.addRow(0, labelNumeUtilizator, textFieldNumeUtilizator);
        grid.addRow(1, labelParola, passwordField);
        grid.addRow(2, buttonAutentificare);

        // Adaugă elementele la BorderPane
        borderPane.setTop(labelConectare);
        borderPane.setAlignment(labelConectare, Pos.BASELINE_LEFT);
        borderPane.setCenter(grid);

        // Acțiunea butonului de autentificare
        buttonAutentificare.setOnAction(e -> {
            String numeUtilizator = textFieldNumeUtilizator.getText();
            String parola = passwordField.getText();

            try (Connection conexiune = DriverManager.getConnection("jdbc:mysql://localhost:3306/PDE_testing2", "root", "password")) {
                if (conexiune != null) {
                    System.out.println("Conexiune reușită!");

                    String sqlSelect = "SELECT Utilizatori.Tip_Utilizator FROM Utilizatori JOIN UtilizatoriAutentificare ON Utilizatori.ID_Utilizator = UtilizatoriAutentificare.ID_Utilizator WHERE UtilizatoriAutentificare.Nume_Utilizator = ? AND UtilizatoriAutentificare.Parola = ?";
                    try (PreparedStatement preparedStatement = conexiune.prepareStatement(sqlSelect)) {
                        preparedStatement.setString(1, numeUtilizator);
                        preparedStatement.setString(2, parola);

                        try (ResultSet resultSet = preparedStatement.executeQuery()) {
                            if (resultSet.next()) {
                                String tipUtilizator = resultSet.getString("Tip_Utilizator");
                                UserInterface userInterface = getUserInterfaceByType(tipUtilizator, conexiune);
                                userInterface.displayUI(primaryStage, numeUtilizator);
                            } else {

                                afiseazaEroare("Numele de utilizator sau parola incorecte.");

                            }
                        }
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Eroare de conectare la baza de date.");
                ex.printStackTrace();
            }
        });

        return borderPane;
    }


    private BorderPane createInregistrareUI(Stage primaryStage) {
        InregistrareInterface inregistrareInterface = new InregistrareInterface();
        return inregistrareInterface.createInregistrareUI(primaryStage);
    }

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

    class InregistrareInterface {

        public BorderPane createInregistrareUI(Stage primaryStage) {


            BorderPane borderPane = new BorderPane();
            borderPane.setPadding(new Insets(25, 25, 25, 25));


            // Adaugă un Label pentru textul "Conectare"
            Label labelConectare = new Label("Înregistrare");
            labelConectare.setFont(Font.font(16));
            labelConectare.setStyle("-fx-font-weight: bold;");


            Label labelNume = new Label("Nume:");
            TextField textFieldNume = new TextField();
            Label labelPrenume = new Label("Prenume:");
            TextField textFieldPrenume = new TextField();
            Label labelNumeUtilizator = new Label("Nume utilizator:");
            TextField textFieldNumeUtilizator = new TextField();
            Label labelParola = new Label("Parola:");
            PasswordField passwordField = new PasswordField();
            Label labelCNP = new Label("CNP:");
            TextField textFieldCNP = new TextField();
            Label labelTelefon = new Label("Telefon:");
            TextField textFieldTelefon = new TextField();
            Label labelAdresa = new Label("Adresa:");
            TextField textFieldAdresa = new TextField();
            Label labelEmail = new Label("Email:");
            TextField textFieldEmail = new TextField();
            Label labelIBAN = new Label("Cont_IBAN:");
            TextField textFieldIBAN = new TextField();



            Button buttonInregistrare = new Button("Înregistrare");





            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            grid.addRow(0, labelNume, textFieldNume);
            grid.addRow(1, labelPrenume, textFieldPrenume);
            grid.addRow(2, labelNumeUtilizator, textFieldNumeUtilizator);
            grid.addRow(3, labelParola, passwordField);
            grid.addRow(4, labelCNP, textFieldCNP);
            grid.addRow(5, labelTelefon, textFieldTelefon);
            grid.addRow(6, labelAdresa, textFieldAdresa);
            grid.addRow(7, labelEmail, textFieldEmail);
            grid.addRow(8, labelIBAN, textFieldIBAN);
            grid.addRow(10, buttonInregistrare);

            // Adaugă elementele la BorderPane
            borderPane.setTop(labelConectare);
            borderPane.setAlignment(labelConectare, Pos.BASELINE_LEFT);
            borderPane.setCenter(grid);

            buttonInregistrare.setOnAction(e -> {
                String tipUtilizator = "Student";

                adaugareUtilizator(
                        textFieldNume.getText(), textFieldPrenume.getText(), textFieldNumeUtilizator.getText(),
                        passwordField.getText(), textFieldCNP.getText(), textFieldTelefon.getText(),
                        textFieldAdresa.getText(), textFieldEmail.getText(), textFieldIBAN.getText(),
                        tipUtilizator
                );
            });

            borderPane.setCenter(grid);
            return borderPane;
        }

        private void adaugareUtilizator(String nume, String prenume, String numeUtilizator, String parola, String cnp, String telefon, String adresa, String email, String iban, String tipUtilizator) {
            try (Connection conexiune = DriverManager.getConnection("jdbc:mysql://localhost:3306/PDE_testing2", "root", "password")) {
                String sqlAdaugareUtilizator = "{CALL AdaugareUtilizator(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
                try (CallableStatement callableStatement = conexiune.prepareCall("{CALL AdaugareUtilizator(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {
                    // Setarea parametrilor de intrare
                    callableStatement.setString(1, nume);
                    callableStatement.setString(2, prenume);
                    callableStatement.setString(3, numeUtilizator);
                    callableStatement.setString(4, parola);
                    callableStatement.setString(5, cnp);
                    callableStatement.setString(6, telefon);
                    callableStatement.setString(7, adresa);
                    callableStatement.setString(8, email);
                    callableStatement.setString(9, iban);
                    callableStatement.setString(10, tipUtilizator);

                    // Adăugarea parametrului de ieșire
                    callableStatement.registerOutParameter(11, Types.INTEGER);

                    // Executarea procedurii stocate
                    callableStatement.execute();

                    // Obținerea rezultatului
                    int rezultat = callableStatement.getInt(11);

                    // Verificarea rezultatului
                    if (rezultat == 0) {
                        afiseazaSucces("Înregistrare cu succes!");
                    }
                     else {
                        afiseazaEroare("Acest nume de utilizator este luat. Alegeți alt nume de utilizator.");

                    }
                }
            } catch (SQLException ex) {
                System.out.println("Eroare la adăugarea utilizatorului în baza de date.");
                ex.printStackTrace();
                afiseazaEroare("Acest nume de utilizator este luat. Alegeți alt nume de utilizator.");
            }
        }




    }


    private void afiseazaSucces(String mesaj) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.setContentText(mesaj);
        alert.showAndWait();
    }

    private void afiseazaEroare(String mesaj) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Eroare");
        alert.setHeaderText(null);
        alert.setContentText(mesaj);
        alert.showAndWait();
    }


}
