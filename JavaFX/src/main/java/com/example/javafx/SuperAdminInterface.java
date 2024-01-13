package com.example.javafx;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.geometry.Pos;
import java.time.format.DateTimeFormatter;

import static java.sql.Types.NULL;


// Clasă pentru interfața super administrator
class SuperAdminInterface implements UserInterface {

    private Connection conexiune;
    private int id;

    private Map<String, Integer> mapUtilizatori = new HashMap<>();


    public int getIdSuperAdmin() {
        return id;
    }

    public void setIdSuperAdmin(int id) {
        this.id = id;
    }


    public SuperAdminInterface(Connection conexiune) {
        this.conexiune = conexiune;
    }

    @Override
    public void displayUI(Stage primaryStage, String utilizator) {

        conexiune=DatabaseConnection.getConnection();

        String sqlQuery = "SELECT U.ID_Utilizator, SA.ID_SuperAdministrator,U.Nume, U.Prenume, U.cnp, U.adresa, U.telefon, U.email, U.cont_IBAN, U.Numar_Contract " +
                "FROM Utilizatori U " +
                "JOIN UtilizatoriAutentificare UA ON U.ID_Utilizator = UA.ID_Utilizator " +
                "JOIN SuperAdministratori SA ON U.ID_Utilizator = SA.ID_Utilizator " +
                "WHERE UA.Nume_Utilizator = ?";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, utilizator);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet != null && resultSet.next()) {
                    int idSuperAdmin  = resultSet.getInt("ID_SuperAdministrator");
                    setIdSuperAdmin(idSuperAdmin);

                    afisareInterfataSuperAdmin(primaryStage, resultSet);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void afisareInterfataSuperAdmin(Stage primaryStage, ResultSet resultSet) {
        try {
            // Obțineți informațiile despre Super Admin din ResultSet
            String numeAdmin = resultSet.getString("Nume");
            String prenumeAdmin = resultSet.getString("Prenume");

            // Crearea unui mesaj de bun venit
            Label bunVenitLabel = new Label("Bine ati venit, " + numeAdmin.toUpperCase() + " " + prenumeAdmin.toUpperCase());
            bunVenitLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

            // Crearea butoanelor pentru Super Admin
            Button afisareUtilizatoriButton = new Button("Afisare Utilizatori");
            Button filtrareUtilizatoriButton = new Button("Filtrare Utilizatori");
            Button cautareUtilizatoriButton = new Button("Cautare Utilizatori");
            Button modificareDateButton = new Button("Modificare Date Utilizatori");
            Button adaugareProfesorButton = new Button("Adaugare Profesor");
            Button adaugareStudentButton = new Button("Adaugare Student");
            Button stergereUtilizatorButton = new Button("Stergere Utilizator");
            Button adaugareAdministratorButton = new Button("Adaugare Admin");
            Button asociereCursProfesorButton = new Button("Asociere Disciplina Profesor");
            Button cautareCursButton = new Button("Cautare Disciplina");
            Button vizualizareDatePersonale = new Button("Vizualizare Date Personale");
            Button iesireButton = new Button("Iesire din Cont");

            // Adăugați acțiuni pentru butoane
            afisareUtilizatoriButton.setOnAction(e -> handleAfisareUtilizatori());
            filtrareUtilizatoriButton.setOnAction(e -> handleFiltrareUtilizatori());
            cautareUtilizatoriButton.setOnAction(e -> handleCautareUtilizatori());
            modificareDateButton.setOnAction(e -> handleModificareDate());
            adaugareStudentButton.setOnAction(e -> handleAdaugareStudent());
            stergereUtilizatorButton.setOnAction(e -> handleStergereUtilizator());
            adaugareAdministratorButton.setOnAction(e -> handleAdaugareAdministrator());
            adaugareProfesorButton.setOnAction(e -> handleAdaugareProfesor());
            asociereCursProfesorButton.setOnAction(e -> handleAsociereCursProfesor());
            cautareCursButton.setOnAction(e -> handleCautareCurs());
            vizualizareDatePersonale.setOnAction(e -> handleVizualizareDatePersonale());
            iesireButton.setOnAction(e -> handleIesireCont(primaryStage));

            // Organizați butoanele într-un layout (GridPane)
            GridPane butoaneLayout = new GridPane();
            butoaneLayout.add(afisareUtilizatoriButton, 0, 0);
            butoaneLayout.add(filtrareUtilizatoriButton, 0, 1);
            butoaneLayout.add(cautareUtilizatoriButton, 0, 2);
            butoaneLayout.add(modificareDateButton, 0, 3);

            butoaneLayout.add(adaugareStudentButton, 1, 0);
            butoaneLayout.add(stergereUtilizatorButton, 1, 2);
            butoaneLayout.add(adaugareAdministratorButton, 1, 1);

            butoaneLayout.add(adaugareProfesorButton, 2, 0);
            butoaneLayout.add(asociereCursProfesorButton, 2, 1);
            butoaneLayout.add(cautareCursButton, 2, 2);

            butoaneLayout.add(vizualizareDatePersonale, 3, 0);
            butoaneLayout.add(iesireButton, 3, 1);

            butoaneLayout.setAlignment(Pos.CENTER);
            butoaneLayout.setHgap(20);
            butoaneLayout.setVgap(20);

            // Crearea layout-ului principal cu un BorderPane
            BorderPane mainLayout = new BorderPane();
            mainLayout.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));

            // Adăugați bunVenitLabel în partea de sus a layout-ului
            mainLayout.setTop(bunVenitLabel);
            BorderPane.setAlignment(bunVenitLabel, Pos.TOP_CENTER);

            // Adăugați butoaneLayout în partea de jos a layout-ului
            mainLayout.setCenter(butoaneLayout);

            // Crearea scene și setare pentru primaryStage
            Scene scene = new Scene(mainLayout, 800, 600);
            primaryStage.setTitle("Dashboard Super Administrator");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }




    private void handleAfisareUtilizatori() {
        System.out.println("Utilizatori");

        conexiune = DatabaseConnection.getConnection();

        // Definește query-ul pentru a obține toți utilizatorii cu detalii complete
        String sqlQuery = "SELECT * FROM Utilizatori WHERE Tip_Utilizator <> 'SuperAdministrator'";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sqlQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Verifică dacă există rezultate
            if (resultSet != null) {
                Stage utilizatoriStage = new Stage();
                utilizatoriStage.setTitle("Vizualizare Utilizatori");

                // Creare tabel pentru afișarea datelor
                TableView<ObservableList<String>> tableView = new TableView<>();

                // Creare coloane pentru fiecare coloană din tabel

                for (String columnName : Arrays.asList("Tip_Utilizator", "CNP", "Nume", "Prenume",
                        "Adresa", "Telefon", "Email", "Cont_IBAN", "Numar_Contract")) {
                    TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnName);
                    column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(
                            tableView.getColumns().indexOf(column))));
                    tableView.getColumns().add(column);
                }


                // Adaugare date in tabel
                while (resultSet.next()) {
                    ObservableList<String> rowData = FXCollections.observableArrayList();
                    for (String columnName : Arrays.asList("Tip_Utilizator", "CNP", "Nume", "Prenume",
                            "Adresa", "Telefon", "Email", "Cont_IBAN", "Numar_Contract")) {
                        rowData.add(resultSet.getString(columnName));
                    }
                    tableView.getItems().add(rowData);
                }

                // Adaugare tabel intr-un layout
                VBox utilizatoriLayout = new VBox(10, tableView);
                utilizatoriLayout.setPadding(new Insets(20, 20, 20, 20));

                // Creare scene si afisare fereastra
                Scene utilizatoriScene = new Scene(utilizatoriLayout, 800, 600);
                utilizatoriStage.setScene(utilizatoriScene);
                utilizatoriStage.show();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            // În caz de eroare, afișează un mesaj de eroare
            afiseazaEroare("Eroare la afisarea utilizatorilor.");
        }
    }



///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void handleAdaugareStudent() {
        System.out.println("Buton - Adaugare Student");

        // Creează o nouă fereastră pentru adăugarea studentului
        Stage adaugareStudentStage = new Stage();
        adaugareStudentStage.setTitle("Adaugare Student");

        // Creează elementele UI pentru adăugarea studentului
        Label labelAnStudiu = new Label("An Studiu:");
        TextField textFieldAnStudiu = new TextField();
        Label labelOreSustinute = new Label("Ore Sustinute:");
        TextField textFieldOreSustinute = new TextField();
        Label labelCNP = new Label("CNP:");
        TextField textFieldCNP = new TextField();
        Label labelNume = new Label("Nume:");
        TextField textFieldNume = new TextField();
        Label labelPrenume = new Label("Prenume:");
        TextField textFieldPrenume = new TextField();
        Label labelAdresa = new Label("Adresa:");
        TextField textFieldAdresa = new TextField();
        Label labelTelefon = new Label("Telefon:");
        TextField textFieldTelefon = new TextField();
        Label labelEmail = new Label("Email:");
        TextField textFieldEmail = new TextField();
        Label labelContIBAN = new Label("Cont IBAN:");
        TextField textFieldContIBAN = new TextField();

        Label labelParola = new Label("Parola:");
        PasswordField passwordFieldParola = new PasswordField();
        Label labelNumeUtilizator = new Label("Nume Utilizator:");
        TextField textFieldNumeUtilizator = new TextField();

        Button buttonAdauga = new Button("Adauga");

        // Adaugă elementele UI pe grilă
        GridPane gridAdaugare = new GridPane();
        gridAdaugare.setHgap(10);
        gridAdaugare.setVgap(10);
        gridAdaugare.setPadding(new Insets(25, 25, 25, 25));
        gridAdaugare.add(labelAnStudiu, 0, 0);
        gridAdaugare.add(textFieldAnStudiu, 1, 0);
        gridAdaugare.add(labelOreSustinute, 0, 1);
        gridAdaugare.add(textFieldOreSustinute, 1, 1);
        gridAdaugare.add(labelCNP, 0, 2);
        gridAdaugare.add(textFieldCNP, 1, 2);
        gridAdaugare.add(labelNume, 0, 3);
        gridAdaugare.add(textFieldNume, 1, 3);
        gridAdaugare.add(labelPrenume, 0, 4);
        gridAdaugare.add(textFieldPrenume, 1, 4);
        gridAdaugare.add(labelAdresa, 0, 5);
        gridAdaugare.add(textFieldAdresa, 1, 5);
        gridAdaugare.add(labelTelefon, 0, 6);
        gridAdaugare.add(textFieldTelefon, 1, 6);
        gridAdaugare.add(labelEmail, 0, 7);
        gridAdaugare.add(textFieldEmail, 1, 7);
        gridAdaugare.add(labelContIBAN, 0, 8);
        gridAdaugare.add(textFieldContIBAN, 1, 8);
        gridAdaugare.add(labelParola, 0, 10);
        gridAdaugare.add(passwordFieldParola, 1, 10);
        gridAdaugare.add(labelNumeUtilizator, 0, 11);
        gridAdaugare.add(textFieldNumeUtilizator, 1, 11);
        gridAdaugare.add(buttonAdauga, 0, 12);

        // Adaugă acțiunea de la apăsarea butonului Adauga
        buttonAdauga.setOnAction(e -> handleAdaugareStudentFunction(
                textFieldAnStudiu.getText(),
                textFieldOreSustinute.getText(),
                textFieldCNP.getText(),
                textFieldNume.getText(),
                textFieldPrenume.getText(),
                textFieldAdresa.getText(),
                textFieldTelefon.getText(),
                textFieldEmail.getText(),
                textFieldContIBAN.getText(),
                passwordFieldParola.getText(),
                textFieldNumeUtilizator.getText(),
                adaugareStudentStage
        ));

        // Configurarea scenei și afișarea ferestrei
        Scene adaugareStudentScene = new Scene(gridAdaugare, 400, 600);
        adaugareStudentStage.setScene(adaugareStudentScene);
        adaugareStudentStage.show();
    }


    private void handleAdaugareStudentFunction(String anStudiu, String oreSustinute, String cnp, String nume, String prenume,
                                               String adresa, String telefon, String email, String contIBAN,
                                               String parola, String numeUtilizator, Stage adaugareStudentStage) {

        conexiune = DatabaseConnection.getConnection();


        String sql = "CALL AdaugaStudent(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setInt(1, Integer.parseInt(anStudiu));
            preparedStatement.setInt(2, Integer.parseInt(oreSustinute));
            preparedStatement.setString(3, cnp);
            preparedStatement.setString(4, nume);
            preparedStatement.setString(5, prenume);
            preparedStatement.setString(6, adresa);
            preparedStatement.setString(7, telefon);
            preparedStatement.setString(8, email);
            preparedStatement.setString(9, contIBAN);
            preparedStatement.setString(10, parola);
            preparedStatement.setString(11, numeUtilizator);

            preparedStatement.executeUpdate();

            afiseazaSucces("Studentul a fost adăugat cu succes!");

            // Închide fereastra de adăugare student
            adaugareStudentStage.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            afiseazaEroare("Eroare la adăugarea studentului.");
        }
    }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void handleAdaugareProfesor() {
        System.out.println("Buton - Adaugare Profesor");

        // Creează o nouă fereastră pentru adăugarea profesorului
        Stage adaugareProfesorStage = new Stage();
        adaugareProfesorStage.setTitle("Adaugare Profesor");

        // Creează elementele UI pentru adăugarea profesorului
        Label labelDepartament = new Label("Departament:");
        ComboBox<String> comboBoxDepartament = new ComboBox<>();
        comboBoxDepartament.getItems().addAll("Matematica", "Fizica", "Informatica", "Chimie", "Biologie", "Istorie", "Geografie");
        Label labelOreMinim = new Label("Ore Minim:");
        TextField textFieldOreMinim = new TextField();
        Label labelOreMaxim = new Label("Ore Maxim:");
        TextField textFieldOreMaxim = new TextField();
        Label labelCNP = new Label("CNP:");
        TextField textFieldCNP = new TextField();
        Label labelNume = new Label("Nume:");
        TextField textFieldNume = new TextField();
        Label labelPrenume = new Label("Prenume:");
        TextField textFieldPrenume = new TextField();
        Label labelAdresa = new Label("Adresa:");
        TextField textFieldAdresa = new TextField();
        Label labelTelefon = new Label("Telefon:");
        TextField textFieldTelefon = new TextField();
        Label labelEmail = new Label("Email:");
        TextField textFieldEmail = new TextField();
        Label labelContIBAN = new Label("Cont IBAN:");
        TextField textFieldContIBAN = new TextField();
        Label labelParola = new Label("Parola:");
        PasswordField passwordFieldParola = new PasswordField();
        Label labelNumeUtilizator = new Label("Nume Utilizator:");
        TextField textFieldNumeUtilizator = new TextField();

        Button buttonAdauga = new Button("Adauga");

        // Adaugă elementele UI pe grilă
        GridPane gridAdaugare = new GridPane();
        gridAdaugare.setHgap(10);
        gridAdaugare.setVgap(10);
        gridAdaugare.setPadding(new Insets(25, 25, 25, 25));
        gridAdaugare.add(labelDepartament, 0, 0);
        gridAdaugare.add(comboBoxDepartament, 1, 0);
        gridAdaugare.add(labelOreMinim, 0, 1);
        gridAdaugare.add(textFieldOreMinim, 1, 1);
        gridAdaugare.add(labelOreMaxim, 0, 2);
        gridAdaugare.add(textFieldOreMaxim, 1, 2);
        gridAdaugare.add(labelCNP, 0, 3);
        gridAdaugare.add(textFieldCNP, 1, 3);
        gridAdaugare.add(labelNume, 0, 4);
        gridAdaugare.add(textFieldNume, 1, 4);
        gridAdaugare.add(labelPrenume, 0, 5);
        gridAdaugare.add(textFieldPrenume, 1, 5);
        gridAdaugare.add(labelAdresa, 0, 6);
        gridAdaugare.add(textFieldAdresa, 1, 6);
        gridAdaugare.add(labelTelefon, 0, 7);
        gridAdaugare.add(textFieldTelefon, 1, 7);
        gridAdaugare.add(labelEmail, 0, 8);
        gridAdaugare.add(textFieldEmail, 1, 8);
        gridAdaugare.add(labelContIBAN, 0, 9);
        gridAdaugare.add(textFieldContIBAN, 1, 9);
        gridAdaugare.add(labelParola, 0, 11);
        gridAdaugare.add(passwordFieldParola, 1, 11);
        gridAdaugare.add(labelNumeUtilizator, 0, 12);
        gridAdaugare.add(textFieldNumeUtilizator, 1, 12);
        gridAdaugare.add(buttonAdauga, 0, 13);

        // Adaugă acțiunea de la apăsarea butonului Adauga
        buttonAdauga.setOnAction(e -> handleAdaugareProfesorFunction(
                comboBoxDepartament.getValue(),
                textFieldOreMinim.getText(),
                textFieldOreMaxim.getText(),
                textFieldCNP.getText(),
                textFieldNume.getText(),
                textFieldPrenume.getText(),
                textFieldAdresa.getText(),
                textFieldTelefon.getText(),
                textFieldEmail.getText(),
                textFieldContIBAN.getText(),
                passwordFieldParola.getText(),
                textFieldNumeUtilizator.getText(),
                adaugareProfesorStage
        ));

        // Configurarea scenei și afișarea ferestrei
        Scene adaugareProfesorScene = new Scene(gridAdaugare, 400, 600);
        adaugareProfesorStage.setScene(adaugareProfesorScene);
        adaugareProfesorStage.show();
    }









    private void handleAdaugareProfesorFunction(String departament, String oreMinim, String oreMaxim, String cnp,
                                                String nume, String prenume, String adresa, String telefon, String email, String contIBAN,
                                                String parola, String numeUtilizator, Stage adaugareProfesorStage) {
        conexiune = DatabaseConnection.getConnection();

        String sql = "CALL AdaugaProfesor(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setString(1, departament);
            preparedStatement.setInt(2, Integer.parseInt(oreMinim));
            preparedStatement.setInt(3, Integer.parseInt(oreMaxim));
            preparedStatement.setString(4, cnp);
            preparedStatement.setString(5, nume);
            preparedStatement.setString(6, prenume);
            preparedStatement.setString(7, adresa);
            preparedStatement.setString(8, telefon);
            preparedStatement.setString(9, email);
            preparedStatement.setString(10, contIBAN);
            preparedStatement.setString(11, parola);
            preparedStatement.setString(12, numeUtilizator);

            preparedStatement.executeUpdate();

            afiseazaSucces("Profesorul a fost adăugat cu succes!");

            // Închide fereastra de adăugare profesor
            adaugareProfesorStage.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            afiseazaEroare("Eroare la adăugarea profesorului.");
        }
    }




    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void handleAdaugareAdministrator() {
        // Creează o nouă fereastră pentru adăugarea administratorului
        Stage adaugareAdministratorStage = new Stage();
        adaugareAdministratorStage.setTitle("Adăugare Administrator");

        // Creează elementele UI pentru adăugarea administratorului
        Label labelCNP = new Label("CNP:");
        TextField textFieldCNP = new TextField();
        Label labelNume = new Label("Nume:");
        TextField textFieldNume = new TextField();
        Label labelPrenume = new Label("Prenume:");
        TextField textFieldPrenume = new TextField();
        Label labelAdresa = new Label("Adresa:");
        TextField textFieldAdresa = new TextField();
        Label labelTelefon = new Label("Telefon:");
        TextField textFieldTelefon = new TextField();
        Label labelEmail = new Label("Email:");
        TextField textFieldEmail = new TextField();
        Label labelContIBAN = new Label("Cont IBAN:");
        TextField textFieldContIBAN = new TextField();
        Label labelNumarContract = new Label("Număr Contract:");
        TextField textFieldNumarContract = new TextField();
        Label labelParola = new Label("Parola:");
        PasswordField passwordFieldParola = new PasswordField();
        Label labelNumeUtilizator = new Label("Nume Utilizator:");
        TextField textFieldNumeUtilizator = new TextField();

        Button buttonAdauga = new Button("Adaugă");

        // Adaugă elementele UI pe grilă
        GridPane gridAdaugare = new GridPane();
        gridAdaugare.setHgap(10);
        gridAdaugare.setVgap(10);
        gridAdaugare.setPadding(new Insets(25, 25, 25, 25));
        gridAdaugare.add(labelCNP, 0, 0);
        gridAdaugare.add(textFieldCNP, 1, 0);
        gridAdaugare.add(labelNume, 0, 1);
        gridAdaugare.add(textFieldNume, 1, 1);
        gridAdaugare.add(labelPrenume, 0, 2);
        gridAdaugare.add(textFieldPrenume, 1, 2);
        gridAdaugare.add(labelAdresa, 0, 3);
        gridAdaugare.add(textFieldAdresa, 1, 3);
        gridAdaugare.add(labelTelefon, 0, 4);
        gridAdaugare.add(textFieldTelefon, 1, 4);
        gridAdaugare.add(labelEmail, 0, 5);
        gridAdaugare.add(textFieldEmail, 1, 5);
        gridAdaugare.add(labelContIBAN, 0, 6);
        gridAdaugare.add(textFieldContIBAN, 1, 6);

        gridAdaugare.add(labelParola, 0, 8);
        gridAdaugare.add(passwordFieldParola, 1, 8);
        gridAdaugare.add(labelNumeUtilizator, 0, 9);
        gridAdaugare.add(textFieldNumeUtilizator, 1, 9);
        gridAdaugare.add(buttonAdauga, 0, 10);

        // Adaugă acțiunea de la apăsarea butonului Adaugă
        buttonAdauga.setOnAction(e -> handleAdaugareAdministratorFunction(
                textFieldCNP.getText(),
                textFieldNume.getText(),
                textFieldPrenume.getText(),
                textFieldAdresa.getText(),
                textFieldTelefon.getText(),
                textFieldEmail.getText(),
                textFieldContIBAN.getText(),
                passwordFieldParola.getText(),
                textFieldNumeUtilizator.getText(),
                adaugareAdministratorStage
        ));

        // Configurarea scenei și afișarea ferestrei
        Scene adaugareAdministratorScene = new Scene(gridAdaugare, 400, 400);
        adaugareAdministratorStage.setScene(adaugareAdministratorScene);
        adaugareAdministratorStage.show();
    }


    private void handleAdaugareAdministratorFunction(String cnp, String nume, String prenume, String adresa, String telefon,
                                                     String email, String contIBAN, String parola, String numeUtilizator, Stage adaugareAdministratorStage) {
        conexiune = DatabaseConnection.getConnection();

        String sql = "CALL AdaugaAdministrator(?, ?, ?, ?, ?, ?, ?, ?,?)";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setString(1, cnp);
            preparedStatement.setString(2, nume);
            preparedStatement.setString(3, prenume);
            preparedStatement.setString(4, adresa);
            preparedStatement.setString(5, telefon);
            preparedStatement.setString(6, email);
            preparedStatement.setString(7, contIBAN);
            preparedStatement.setString(8, parola);
            preparedStatement.setString(9, numeUtilizator);

            preparedStatement.executeUpdate();

            afiseazaSucces("Administratorul a fost adăugat cu succes!");

            // Închide fereastra de adăugare administrator
            adaugareAdministratorStage.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            afiseazaEroare("Eroare la adăugarea administratorului.");
        }
    }



//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void handleStergereUtilizator() {
        System.out.println("Buton - Ștergere Utilizator");

        // Creează un nou stage pentru ștergerea utilizatorului
        Stage stergereStage = new Stage();
        stergereStage.setTitle("Ștergere Utilizator");

        // Creează elementele UI pentru ștergerea utilizatorului
        Label labelUtilizator = new Label("Selectează Utilizatorul:");
        ComboBox<String> comboBoxUtilizatori = new ComboBox<>();
        Button buttonSterge = new Button("Șterge");

        // Populează ComboBox-ul cu utilizatorii (doar studenți și profesori)
        populeazaComboBoxStudentiProfesoriAdmini(comboBoxUtilizatori);

        // Adaugă elementele UI pe grilă
        GridPane gridStergere = new GridPane();
        gridStergere.setHgap(10);
        gridStergere.setVgap(10);
        gridStergere.setPadding(new Insets(25, 25, 25, 25));
        gridStergere.add(labelUtilizator, 0, 0);
        gridStergere.add(comboBoxUtilizatori, 1, 0);
        gridStergere.add(buttonSterge, 0, 1);

        // Adaugă acțiunea la apăsarea butonului Șterge
        buttonSterge.setOnAction(e -> handleStergereUtilizatorFunction(
                comboBoxUtilizatori.getValue(),
                stergereStage
        ));

        // Configurarea scenei și afișarea ferestrei
        Scene stergereScene = new Scene(gridStergere, 400, 150);
        stergereStage.setScene(stergereScene);
        stergereStage.show();
    }

    private void populeazaComboBoxStudentiProfesoriAdmini(ComboBox<String> comboBox) {
        conexiune = DatabaseConnection.getConnection();

        // Obține datele despre studenți și profesori și le adaugă în ComboBox
        String sql = "SELECT ID_Utilizator, Nume, Prenume, Tip_Utilizator FROM Utilizatori " +
                "WHERE Tip_Utilizator = 'Student' OR Tip_Utilizator = 'Profesor' OR Tip_Utilizator = 'Administrator'";
        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Adaugă numele, prenumele și tipul în combobox
                String item = resultSet.getString("Nume") + " " +
                        resultSet.getString("Prenume") + " - " +
                        resultSet.getString("Tip_Utilizator");
                comboBox.getItems().add(item);
                // Salvăm relația numeUtilizator - idUtilizator
                mapUtilizatori.put(item, resultSet.getInt("ID_Utilizator"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea datelor despre utilizatori.");
        }
    }


    private String extrageTipUtilizator(String textComboBox) {
        // Verifică dacă șirul conține liniuța "-"
        if (textComboBox.contains("-")) {
            // Extrage tipul utilizatorului (după liniuță) și elimină spațiile
            return textComboBox.substring(textComboBox.lastIndexOf("-") + 1).trim();
        } else {
            // Returnează un șir gol sau poți gestiona altfel cazurile în care nu există liniuță
            return "";
        }
    }



    private void handleStergereUtilizatorFunction(String utilizatorSelectat, Stage stergereStage) {
        conexiune = DatabaseConnection.getConnection();

        // Parsarea ID-ului utilizatorului selectat din ComboBox
        int idUtilizator = mapUtilizatori.get(utilizatorSelectat);


        // Apel procedură stocată pentru ștergere
        String sql = "{call StergereUtilizator(?)}";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setInt(1, idUtilizator);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String mesaj = resultSet.getString("Mesaj");
                afiseazaSucces(mesaj);
            }

            // Închide fereastra de ștergere
            stergereStage.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            afiseazaEroare("Eroare la ștergere.");
        }
    }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    private void handleAsociereCursProfesor() {
        conexiune = DatabaseConnection.getConnection();

        // Creează o nouă fereastră pentru asocierea cursului și profesorului
        Stage asociereStage = new Stage();
        asociereStage.setTitle("Asociere Disciplina Profesor");

        // Creează elementele UI pentru asociere
        Label labelIdDisciplina = new Label("Disciplina:");
        ComboBox<String> comboBoxDiscipline = new ComboBox<>();
        Label labelIdProfesor = new Label("Profesor:");
        ComboBox<String> comboBoxProfesori = new ComboBox<>();
        Button buttonAsociaza = new Button("Asociază");

        // Populează ComboBox-urile cu datele din baza de date
        populeazaComboBoxDiscipline(comboBoxDiscipline);
        populeazaComboBoxProfesori(comboBoxProfesori);

        // Adaugă elementele UI pe grilă
        GridPane gridAsociere = new GridPane();
        gridAsociere.setHgap(10);
        gridAsociere.setVgap(10);
        gridAsociere.setPadding(new Insets(25, 25, 25, 25));
        gridAsociere.add(labelIdDisciplina, 0, 0);
        gridAsociere.add(comboBoxDiscipline, 1, 0);
        gridAsociere.add(labelIdProfesor, 0, 1);
        gridAsociere.add(comboBoxProfesori, 1, 1);
        gridAsociere.add(buttonAsociaza, 0, 2);

        // Adaugă acțiunea la apăsarea butonului Asociază
        buttonAsociaza.setOnAction(e -> handleAsociereCursProfesorFunction(
                extrageNumeProfesor(comboBoxProfesori.getValue()),
                extragePrenumeProfesor(comboBoxProfesori.getValue()),
                comboBoxDiscipline.getValue(),
                asociereStage
        ));


        // Configurarea scenei și afișarea ferestrei
        Scene asociereScene = new Scene(gridAsociere, 400, 150);
        asociereStage.setScene(asociereScene);
        asociereStage.show();
    }

    private void populeazaComboBoxDiscipline(ComboBox<String> comboBox) {
        conexiune = DatabaseConnection.getConnection();

        // Obține datele despre discipline din baza de date și le adaugă în ComboBox
        String sql = "SELECT Nume_Disciplina, Departament FROM Discipline";
        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String item = resultSet.getString("Nume_Disciplina") + "-"+
                              resultSet.getString("Departament");
                comboBox.getItems().add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea datelor despre discipline.");
        }
    }

    private void populeazaComboBoxProfesori(ComboBox<String> comboBox) {
        conexiune = DatabaseConnection.getConnection();

        // Obține datele despre profesori din baza de date și le adaugă în ComboBox
        String sql = "SELECT U.Nume, U.Prenume, P.Departament FROM Profesori P " +
                "JOIN Utilizatori U ON U.ID_Utilizator = P.ID_Utilizator";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String item = resultSet.getString("Nume") + " " +
                        resultSet.getString("Prenume") + " - " +
                        resultSet.getString("Departament");
                comboBox.getItems().add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea datelor despre profesori.");
        }
    }

    private String extrageNumeProfesor(String selectedItem) {
        String[] parts = selectedItem.split(" ");
        if (parts.length >= 2) {
            return parts[0];
        }
        return "";
    }

    private String extragePrenumeProfesor(String selectedItem) {
        String[] parts = selectedItem.split(" ");
        if (parts.length >= 2) {
            return parts[1];
        }
        return "";
    }






    private void handleAsociereCursProfesorFunction(String numeProfesor, String prenumeProfesor, String selectedDisciplina, Stage asociereStage) {
        conexiune = DatabaseConnection.getConnection();

        // Obține id-ul disciplinei și profesorului
        int idDisciplina = obtineIdDisciplinaDupaNume(selectedDisciplina);
        int idProfesor = obtineIdProfesorDupaNumePrenume(numeProfesor, prenumeProfesor);

        if (idDisciplina != -1 && idProfesor != -1) {
            // Apel procedură stocată pentru asociere
            String sql = "{call AsociereCursProfesor(?, ?, ?)}";

            try (CallableStatement callableStatement = conexiune.prepareCall(sql)) {
                callableStatement.setInt(1, idDisciplina);
                callableStatement.setInt(2, idProfesor);

                // Înregistrarea parametrului de ieșire
                callableStatement.registerOutParameter(3, Types.INTEGER);

                // Execută procedura stocată
                callableStatement.execute();

                // Obține rezultatul procedurii
                int rezultatProcedura = callableStatement.getInt(3);

                if (rezultatProcedura == 1) {
                    afiseazaSucces("Asociere reușită.");
                    // Închide fereastra de asociere
                    asociereStage.close();
                } else if (rezultatProcedura == 0) {
                    afiseazaEroare("Asociere eșuată.Departamente diferite.");
                }
                else {
                    afiseazaEroare("Profesorul selectat este deja asignat la aceasta disciplina");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                afiseazaEroare("Eroare la asociere.");
            }
        } else {
            afiseazaEroare("Disciplina sau profesorul nu au fost găsite.");
        }
    }


    private int obtineIdProfesorDupaNumePrenume(String numeProfesor, String prenumeProfesor) {
        conexiune = DatabaseConnection.getConnection();
        String sql = "SELECT P.ID_Profesor FROM Profesori P " +
                "JOIN Utilizatori U ON U.ID_Utilizator = P.ID_Utilizator " +
                "WHERE U.Nume = ? AND U.Prenume = ?";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setString(1, numeProfesor);
            preparedStatement.setString(2, prenumeProfesor);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("ID_Profesor");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea ID-ului profesorului.");
        }

        return -1; // Returnează -1 în caz de eroare sau dacă profesorul nu a fost găsit
    }





    private int obtineIdDisciplinaDupaNume(String numeDisciplina) {
        conexiune = DatabaseConnection.getConnection();

        // Desparte numele disciplinei și departamentul
        int index = numeDisciplina.indexOf('-');
        String doarNumeDisciplina = (index != -1) ? numeDisciplina.substring(0, index) : numeDisciplina;

        String sql = "SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = ?";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setString(1, doarNumeDisciplina);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("ID_Disciplina");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea ID-ului disciplinei.");
        }

        return -1; // Returnează -1 în caz de eroare sau dacă disciplina nu a fost găsită
    }








    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////






    private void handleCautareCurs() {
        conexiune = DatabaseConnection.getConnection();

        System.out.println("Buton - Cautare Curs");

        // Creează o nouă fereastră pentru căutarea cursului
        Stage cautareCursStage = new Stage();
        cautareCursStage.setTitle("Cautare Curs");

        // Creează elementele UI pentru căutare
        Label labelNumeDisciplina = new Label("Nume Disciplina:");
        ComboBox<String> comboBoxDiscipline = new ComboBox<>();
        Button buttonCauta = new Button("Cauta");

        // Populează ComboBox cu denumirile disciplinelor disponibile
        populeazaComboBoxDiscipline(comboBoxDiscipline);

        // Adaugă elementele UI pe grilă
        GridPane gridCautare = new GridPane();
        gridCautare.setHgap(10);
        gridCautare.setVgap(10);
        gridCautare.setPadding(new Insets(25, 25, 25, 25));
        gridCautare.add(labelNumeDisciplina, 0, 0);
        gridCautare.add(comboBoxDiscipline, 1, 0);
        gridCautare.add(buttonCauta, 2, 0);

        // Adaugă acțiunea la apăsarea butonului Cauta
        buttonCauta.setOnAction(e -> handleAfisareDetaliiCurs(extrageNumeDisciplina(comboBoxDiscipline.getValue()), cautareCursStage));

        // Configurarea scenei și afișarea ferestrei
        Scene cautareCursScene = new Scene(gridCautare, 500, 100);
        cautareCursStage.setScene(cautareCursScene);
        cautareCursStage.show();
    }

    // Extrage numele disciplinei dintr-o valoare de combobox (format: "ID Nume")
    private String extrageNumeDisciplina(String comboBoxValue) {
        if (comboBoxValue != null && comboBoxValue.contains("-")) {
            // Extrage partea nume a disciplinei
            return comboBoxValue.substring(0, comboBoxValue.indexOf("-"));
        }
        return "";
    }






    // Metoda pentru afișarea detaliilor despre un curs
    private void handleAfisareDetaliiCurs(String numeDisciplina, Stage cautareCursStage) {
        conexiune = DatabaseConnection.getConnection();

        // Creează o nouă fereastră pentru afișarea detaliilor cursului
        Stage detaliiCursStage = new Stage();
        detaliiCursStage.setTitle("Detalii Curs - " + numeDisciplina);

        // Layout pentru fereastra detaliiCursStage
        VBox detaliiCursLayout = new VBox();
        detaliiCursLayout.setSpacing(10);

        // Afiseaza numele disciplinei in partea de sus mijloc
        detaliiCursLayout.getChildren().add(new Label("Disciplina: " + numeDisciplina));

        // Definește query-ul pentru a obține detalii despre profesorii cursului
        String sqlQueryProfesori = "SELECT UProf.Nume AS Nume, UProf.Prenume AS Prenume, P.Departament, P.Ore_Minim, P.Ore_Maxim " +
                "FROM Discipline D " +
                "JOIN Asociere_Disciplina_Profesor ADP ON D.ID_Disciplina = ADP.ID_Disciplina " +
                "JOIN Profesori P ON ADP.ID_Profesor = P.ID_Profesor  AND P.Departament = D.Departament " +
                "JOIN Utilizatori UProf ON P.ID_Utilizator = UProf.ID_Utilizator " +
                "WHERE D.Nume_Disciplina = ?";

        // Definește query-ul pentru a obține activitățile programate aferente disciplinei
        String sqlQueryActivitati = "SELECT TA.Nume_Tip_Activitate, C.Data_Inceput, C.Data_Sfarsit, C.Ora_Inceput, C.Ora_Sfarsit " +
                "FROM Discipline D " +
                "JOIN Activitati A ON D.ID_Disciplina = A.ID_Disciplina " +
                "JOIN Calendar C ON C.ID_Activitate = A.ID_Activitate " +
                "JOIN Tip_Activitate TA ON A.ID_Tip_Activitate = TA.ID_Tip_Activitate " +
                "WHERE D.Nume_Disciplina = ?";

        // Definește query-ul pentru a obține studenții înscriși la disciplină
        String sqlQueryStudenti = "SELECT UStud.Nume AS Nume, UStud.Prenume AS Prenume, S.An_Studiu, S.Ore_Sustinute " +
                "FROM Discipline D " +
                "LEFT JOIN Inscriere_Student_Disciplina ISD ON D.ID_Disciplina = ISD.ID_Disciplina " +
                "LEFT JOIN Studenti S ON ISD.ID_Student = S.ID_Student " +
                "JOIN Utilizatori UStud ON S.ID_Utilizator = UStud.ID_Utilizator " +
                "WHERE D.Nume_Disciplina = ?";

        // Execută interogarea pentru profesori
        executaInterogareSiPopuleazaTabel(detaliiCursLayout, sqlQueryProfesori, numeDisciplina, "Profesori");

        // Execută interogarea pentru activități
        executaInterogareSiPopuleazaTabel(detaliiCursLayout, sqlQueryActivitati, numeDisciplina, "Activități");

        // Execută interogarea pentru studenți
        executaInterogareSiPopuleazaTabel(detaliiCursLayout, sqlQueryStudenti, numeDisciplina, "Studenți inscrisi");

        // Configurarea scenei și afișarea ferestrei detaliiCursStage
        Scene detaliiCursScene = new Scene(detaliiCursLayout, 800, 600);
        detaliiCursStage.setScene(detaliiCursScene);
        detaliiCursStage.show();

        // Închide fereastra de căutare a cursului
        cautareCursStage.close();
    }

    private void executaInterogareSiPopuleazaTabel(VBox detaliiCursLayout, String sqlQuery, String numeDisciplina, String tipTabel) {
        conexiune = DatabaseConnection.getConnection();

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, numeDisciplina);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    detaliiCursLayout.getChildren().add(new Label(tipTabel + ":"));

                    // Creează tabelul
                    TableView<Map<String, Object>> tabel = new TableView<>();
                    tabel.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    detaliiCursLayout.getChildren().add(tabel);

                    // Adaugă coloanele
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        String numeColoana = metaData.getColumnName(i);
                        TableColumn<Map<String, Object>, Object> coloana = new TableColumn<>(numeColoana);
                        coloana.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().get(numeColoana)));

                        // Ajustează afișarea pentru datele de tip data și oră
                        if (metaData.getColumnType(i) == Types.TIMESTAMP || metaData.getColumnType(i) == Types.DATE) {
                            coloana.setCellFactory(column -> new TableCell<>() {
                                private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                                @Override
                                protected void updateItem(Object item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (empty || item == null) {
                                        setText(null);
                                    } else {
                                        if (item instanceof Timestamp) {
                                            LocalDate date = ((Timestamp) item).toLocalDateTime().toLocalDate();
                                            setText(formatter.format(date));
                                        } else if (item instanceof LocalDate) {
                                            setText(formatter.format((LocalDate) item));
                                        } else {
                                            setText(item.toString());
                                        }
                                    }
                                }
                            });
                        }


                            tabel.getColumns().add(coloana);
                    }

                    // Populează tabelul cu date
                    do {
                        Map<String, Object> rand = new HashMap<>();
                        for (int i = 1; i <= metaData.getColumnCount(); i++) {
                            String numeColoana = metaData.getColumnName(i);
                            rand.put(numeColoana, resultSet.getObject(numeColoana));
                        }
                        tabel.getItems().add(rand);
                    } while (resultSet.next());

                    // Setează înălțimea rândurilor în funcție de numărul de rânduri populate
                    tabel.setFixedCellSize(25);  // Puteți ajusta înălțimea după preferințe
                    tabel.prefHeightProperty().bind(Bindings.size(tabel.getItems()).multiply(tabel.getFixedCellSize()).add(30));  // 30 este spațiul pentru antetul tabelului
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            afiseazaEroare("Eroare la afișarea informațiilor pentru " + tipTabel + ".");
        }
    }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    private void handleFiltrareUtilizatori() {
        System.out.println("Buton - Filtrare Utilizatori");

        // Definește tipurile de utilizatori posibile
        String[] tipuriUtilizatori = {"Student", "Profesor", "Administrator"};

        // Creează o nouă fereastră pentru alegerea tipului de utilizator
        Stage filtrareUtilizatoriStage = new Stage();
        filtrareUtilizatoriStage.setTitle("Filtrare Utilizatori");

        // Creează elementele UI pentru alegerea tipului de utilizator
        Label labelTipUtilizator = new Label("Alege tipul de utilizator:");
        ChoiceBox<String> choiceBoxTipUtilizator = new ChoiceBox<>(FXCollections.observableArrayList(tipuriUtilizatori));
        Button buttonFiltreaza = new Button("Filtrează");

        // Adaugă elementele UI pe grilă
        GridPane gridFiltrare = new GridPane();
        gridFiltrare.setHgap(10);
        gridFiltrare.setVgap(10);
        gridFiltrare.setPadding(new Insets(25, 25, 25, 25));
        gridFiltrare.add(labelTipUtilizator, 0, 0);
        gridFiltrare.add(choiceBoxTipUtilizator, 1, 0);
        gridFiltrare.add(buttonFiltreaza, 2, 0);

        // Adaugă acțiunea de la apăsarea butonului Filtrează
        buttonFiltreaza.setOnAction(e -> handleAfisareUtilizatoriFiltrati(choiceBoxTipUtilizator.getValue(), filtrareUtilizatoriStage));

        // Configurarea scenei și afișarea ferestrei
        Scene filtrareUtilizatoriScene = new Scene(gridFiltrare, 400, 100);
        filtrareUtilizatoriStage.setScene(filtrareUtilizatoriScene);
        filtrareUtilizatoriStage.show();
    }

    private void handleAfisareUtilizatoriFiltrati(String tipUtilizator, Stage filtrareUtilizatoriStage) {
        System.out.println("Afiseaza utilizatorii de tip: " + tipUtilizator);

        conexiune = DatabaseConnection.getConnection();

        String sqlQuery = "";
        switch (tipUtilizator) {
            case "Student":
                sqlQuery = "SELECT U.Nume, U.Prenume, U.CNP, U.Adresa, U.Telefon, U.Email, U.Cont_IBAN, U.Numar_Contract, S.An_Studiu, S.Ore_Sustinute " +
                        "FROM Utilizatori U JOIN Studenti S ON U.ID_Utilizator = S.ID_Utilizator";
                break;
            case "Profesor":
                sqlQuery = "SELECT U.Nume, U.Prenume, U.CNP, U.Adresa, U.Telefon, U.Email, U.Cont_IBAN, U.Numar_Contract, P.Departament, P.Ore_Minim, P.Ore_Maxim " +
                        "FROM Utilizatori U JOIN Profesori P ON U.ID_Utilizator = P.ID_Utilizator";
                break;
            default:
                sqlQuery = "SELECT Nume, Prenume, CNP, Adresa, Telefon, Email, Cont_IBAN, Numar_Contract FROM Utilizatori WHERE Tip_Utilizator = ?";
                break;
        }

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sqlQuery)) {
            if (!tipUtilizator.equals("Student") && !tipUtilizator.equals("Profesor")) {
                preparedStatement.setString(1, tipUtilizator);
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet != null) {
                    Stage utilizatoriFiltratiStage = new Stage();
                    utilizatoriFiltratiStage.setTitle("Utilizatori Filtrați");

                    // Creare tabel pentru afișarea datelor
                    TableView<ObservableList<String>> tableView = new TableView<>();

                    // Creare coloane pentru fiecare coloană din tabel
                    for (String columnName : Arrays.asList("Nume", "Prenume", "CNP", "Adresa", "Telefon", "Email", "Cont_IBAN", "Numar_Contract")) {
                        TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnName);
                        column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(
                                tableView.getColumns().indexOf(column))));
                        tableView.getColumns().add(column);
                    }

                    // Adaugare coloane specifice pentru tipul de utilizator
                    // Adaugare coloane specifice pentru tipul de utilizator
                    switch (tipUtilizator) {
                        case "Student":
                            TableColumn<ObservableList<String>, String> anStudiuColumn = new TableColumn<>("An Studiu");
                            TableColumn<ObservableList<String>, String> oreSustinuteColumn = new TableColumn<>("Ore Sustinute");
                            anStudiuColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(8)));  // Aceasta este poziția absolută a coloanei An_Studiu în rândul ResultSet
                            oreSustinuteColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(9)));  // Aceasta este poziția absolută a coloanei Ore_Sustinute în rândul ResultSet
                            tableView.getColumns().addAll(anStudiuColumn, oreSustinuteColumn);
                            break;
                        case "Profesor":
                            TableColumn<ObservableList<String>, String> departamentColumn = new TableColumn<>("Departament");
                            TableColumn<ObservableList<String>, String> oreMinimColumn = new TableColumn<>("Ore Minim");
                            TableColumn<ObservableList<String>, String> oreMaximColumn = new TableColumn<>("Ore Maxim");
                            departamentColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(8)));  // Aceasta este poziția absolută a coloanei Departament în rândul ResultSet
                            oreMinimColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(9)));  // Aceasta este poziția absolută a coloanei Ore_Minim în rândul ResultSet
                            oreMaximColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(10)));  // Aceasta este poziția absolută a coloanei Ore_Maxim în rândul ResultSet
                            tableView.getColumns().addAll(departamentColumn, oreMinimColumn, oreMaximColumn);
                            break;
                        default:
                            // Adaugă cazuri pentru alți tipi de utilizatori, dacă este necesar
                    }


                    // Adaugare date in tabel
                    while (resultSet.next()) {
                        ObservableList<String> rowData = FXCollections.observableArrayList();
                        for (String columnName : Arrays.asList("Nume", "Prenume", "CNP", "Adresa", "Telefon", "Email", "Cont_IBAN", "Numar_Contract")) {
                            rowData.add(resultSet.getString(columnName));
                        }
                        // Adaugare valori specifice pentru tipul de utilizator
                        switch (tipUtilizator) {
                            case "Student":
                                rowData.add(resultSet.getString("An_Studiu"));
                                rowData.add(resultSet.getString("Ore_Sustinute"));
                                break;
                            case "Profesor":
                                rowData.add(resultSet.getString("Departament"));
                                rowData.add(resultSet.getString("Ore_Minim"));
                                rowData.add(resultSet.getString("Ore_Maxim"));
                                break;
                            default:
                                // Adaugă cazuri pentru alți tipi de utilizatori, dacă este necesar
                        }
                        tableView.getItems().add(rowData);
                    }

                    // Adaugare tabel intr-un layout
                    VBox utilizatoriLayout = new VBox(10, tableView);
                    utilizatoriLayout.setPadding(new Insets(20, 20, 20, 20));

                    // Creare scene si afisare fereastra
                    Scene utilizatoriScene = new Scene(utilizatoriLayout, 800, 600);
                    utilizatoriFiltratiStage.setScene(utilizatoriScene);
                    utilizatoriFiltratiStage.show();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            afiseazaEroare("Eroare la afișarea utilizatorilor.");
        }

        filtrareUtilizatoriStage.close();  // Închide fereastra de filtrare
    }







//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




    private void handleCautareUtilizatori() {

        conexiune = DatabaseConnection.getConnection();
        System.out.println("Buton - Cautare Utilizatori");

        // Creează un nou stage pentru afișarea rezultatelor căutării
        Stage rezultateStage = new Stage();
        rezultateStage.setTitle("Rezultate Cautare Utilizatori");

        // Creează elementele UI pentru afișarea rezultatelor


        // Adaugă elementele UI pe o nouă grilă
        GridPane gridRezultate = new GridPane();
        gridRezultate.setHgap(10);
        gridRezultate.setVgap(10);
        gridRezultate.setPadding(new Insets(25, 25, 25, 25));


        // Configurarea scenei și afișarea ferestrei
        Scene rezultateScene = new Scene(gridRezultate, 400, 200);
        rezultateStage.setScene(rezultateScene);
        rezultateStage.show();

        // Crează elementele pentru căutarea utilizatorilor
        Label labelNume = new Label("Nume:");
        TextField textFieldNume = new TextField();
        Label labelPrenume = new Label("Prenume:");
        TextField textFieldPrenume = new TextField();
        Button buttonCauta = new Button("Cauta");

        // Adaugă acțiunea la apăsarea butonului Cauta
        buttonCauta.setOnAction(e -> {
            // Implementează logica pentru căutarea utilizatorilor și afișarea rezultatelor într-un tabel
            afiseazaUtilizatoriCautati(
                    textFieldNume.getText(),
                    textFieldPrenume.getText()
            );
        });

        // Adaugă elementele UI pe grilă
        GridPane gridCautare = new GridPane();
        gridCautare.setHgap(10);
        gridCautare.setVgap(10);
        gridCautare.setPadding(new Insets(25, 25, 25, 25));
        gridCautare.add(labelNume, 0, 0);
        gridCautare.add(textFieldNume, 1, 0);
        gridCautare.add(labelPrenume, 0, 1);
        gridCautare.add(textFieldPrenume, 1, 1);
        gridCautare.add(buttonCauta, 0, 2);

        // Adaugă grila de căutare la scena rezultatelor
        gridRezultate.add(gridCautare, 0, 1);
    }

    private void afiseazaUtilizatoriCautati(String nume, String prenume) {
        conexiune = DatabaseConnection.getConnection();

        // Definește query-ul pentru a obține utilizatorii cu un anumit nume și prenume
        String sqlQuery = "SELECT * FROM Utilizatori WHERE Nume = ? AND Prenume = ?";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, nume);
            preparedStatement.setString(2, prenume);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet != null) {
                    // Creare tabel pentru afișarea datelor
                    TableView<ObservableList<String>> tableView = new TableView<>();

                    // Creare coloane pentru fiecare coloană din tabel, EXCEPTÂND "ID_Utilizator"
                    for (String columnName : Arrays.asList("Tip_Utilizator", "CNP", "Nume", "Prenume",
                            "Adresa", "Telefon", "Email", "Cont_IBAN", "Numar_Contract")) {
                        TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnName);
                        column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(
                                tableView.getColumns().indexOf(column))));
                        tableView.getColumns().add(column);
                    }

                    // Adaugare date in tabel
                    while (resultSet.next()) {
                        ObservableList<String> rowData = FXCollections.observableArrayList();
                        for (String columnName : Arrays.asList("Tip_Utilizator", "CNP", "Nume", "Prenume",
                                "Adresa", "Telefon", "Email", "Cont_IBAN", "Numar_Contract")) {
                            rowData.add(resultSet.getString(columnName));
                        }
                        tableView.getItems().add(rowData);
                    }

                    // Afișează tabelul într-o fereastră nouă
                    Stage utilizatoriCautatiStage = new Stage();
                    utilizatoriCautatiStage.setTitle("Utilizatori Cautati");
                    VBox utilizatoriLayout = new VBox(10, tableView);
                    utilizatoriLayout.setPadding(new Insets(20, 20, 20, 20));
                    Scene utilizatoriScene = new Scene(utilizatoriLayout, 800, 600);
                    utilizatoriCautatiStage.setScene(utilizatoriScene);
                    utilizatoriCautatiStage.show();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            afiseazaEroare("Eroare la afișarea utilizatorilor.");
        }
    }



///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    private void handleModificareDate() {
        Stage modificareStage = new Stage();
        modificareStage.setTitle("Modificare Detalii Personale");

        Label labelUtilizator = new Label("Selectează Utilizatorul:");
        ComboBox<String> comboBoxUtilizatori = new ComboBox<>();

        Label labelAdresa = new Label("Noua Adresa:");
        TextField textFieldAdresa = new TextField();
        Label labelTelefon = new Label("Noul Telefon:");
        TextField textFieldTelefon = new TextField();
        Label labelEmail = new Label("Noul Email:");
        TextField textFieldEmail = new TextField();
        Label labelContIBAN = new Label("Noul Cont IBAN:");
        TextField textFieldContIBAN = new TextField();

        Label labelOreMinim = new Label("Ore Minime:");
        TextField textFieldOreMinim = new TextField();

        Label labelOreMaxim = new Label("Ore Maxime:");
        TextField textFieldOreMaxim = new TextField();

        Label labelAnStudiu = new Label("An Studiu:");
        TextField textFieldAnStudiu = new TextField();
        Label labelOreSustinute = new Label("Ore Sustinute:");
        TextField textFieldOreSustinute = new TextField();

        Button buttonModifica = new Button("Modifică");

        populeazaComboBoxStudentiProfesoriAdmini(comboBoxUtilizatori);

        GridPane gridModificare = new GridPane();
        gridModificare.setHgap(10);
        gridModificare.setVgap(10);
        gridModificare.setPadding(new Insets(25, 25, 25, 25));
        gridModificare.add(labelUtilizator, 0, 0);
        gridModificare.add(comboBoxUtilizatori, 1, 0);
        gridModificare.add(labelAdresa, 0, 1);
        gridModificare.add(textFieldAdresa, 1, 1);
        gridModificare.add(labelTelefon, 0, 2);
        gridModificare.add(textFieldTelefon, 1, 2);
        gridModificare.add(labelEmail, 0, 3);
        gridModificare.add(textFieldEmail, 1, 3);
        gridModificare.add(labelContIBAN, 0, 4);
        gridModificare.add(textFieldContIBAN, 1, 4);

        gridModificare.add(labelOreMinim, 0, 5);
        gridModificare.add(textFieldOreMinim, 1, 5);
        gridModificare.add(labelOreMaxim, 0, 6);
        gridModificare.add(textFieldOreMaxim, 1, 6);

        gridModificare.add(labelAnStudiu, 0, 8);
        gridModificare.add(textFieldAnStudiu, 1, 8);
        gridModificare.add(labelOreSustinute, 0, 9);
        gridModificare.add(textFieldOreSustinute, 1, 9);

        gridModificare.add(buttonModifica, 0, 10);

        // Adaugă acțiune pentru afișarea/ascunderea elementelor în funcție de tipul de utilizator
        comboBoxUtilizatori.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String tipUtilizator = extrageTipUtilizator(newValue);

            // Elimină toate elementele adăugate anterior
            gridModificare.getChildren().removeAll(labelOreMinim, textFieldOreMinim, labelOreMaxim, textFieldOreMaxim,
                    labelAnStudiu, textFieldAnStudiu, labelOreSustinute, textFieldOreSustinute);

            // Adaugă elementele specifice tipului de utilizator
            if ("Profesor".equals(tipUtilizator)) {
                gridModificare.add(labelOreMinim, 0, 5);
                gridModificare.add(textFieldOreMinim, 1, 5);
                gridModificare.add(labelOreMaxim, 0, 6);
                gridModificare.add(textFieldOreMaxim, 1, 6);

            } else if ("Student".equals(tipUtilizator)) {
                gridModificare.add(labelAnStudiu, 0, 7);
                gridModificare.add(textFieldAnStudiu, 1, 7);
                gridModificare.add(labelOreSustinute, 0, 8);
                gridModificare.add(textFieldOreSustinute, 1, 8);
            }
        });

        buttonModifica.setOnAction(e -> handleModificareDateFunction(
                comboBoxUtilizatori.getValue(),
                textFieldAdresa.getText(),
                textFieldTelefon.getText(),
                textFieldEmail.getText(),
                textFieldContIBAN.getText(),
                !textFieldOreMinim.getText().isEmpty() ? Integer.parseInt(textFieldOreMinim.getText()) : 0,
                !textFieldOreMaxim.getText().isEmpty() ? Integer.parseInt(textFieldOreMaxim.getText()) : 0,
                !textFieldAnStudiu.getText().isEmpty() ? Integer.parseInt(textFieldAnStudiu.getText()) : 0,
                !textFieldOreSustinute.getText().isEmpty() ? Integer.parseInt(textFieldOreSustinute.getText()) : 0,
                modificareStage
        ));




        Scene modificareScene = new Scene(gridModificare, 400, 500);
        modificareStage.setScene(modificareScene);
        modificareStage.show();
    }




    private void handleModificareDateFunction(String utilizatorSelectat, String adresa, String telefon,
                                              String email, String contIBAN, int oreMinim, int oreMaxim,
                                              int anStudiu, int oreSustinute,
                                              Stage modificareStage) {
        conexiune = DatabaseConnection.getConnection();

        // Parsarea ID-ului utilizatorului selectat din ComboBox
        int idUtilizator = mapUtilizatori.get(utilizatorSelectat);

        // Apel procedură stocată pentru modificare
        String sql = "{call ModificaInformatiiUtilizator(?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setInt(1, idUtilizator);
            preparedStatement.setString(2, adresa);
            preparedStatement.setString(3, telefon);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, contIBAN);
            preparedStatement.setInt(6, oreMinim);
            preparedStatement.setInt(7, oreMaxim);
            preparedStatement.setInt(8, anStudiu);
            preparedStatement.setInt(9, oreSustinute);

            preparedStatement.execute();
            afiseazaSucces("Modificare reușită.");

            // Închide fereastra de modificare
            modificareStage.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            afiseazaEroare("Eroare la modificare.");
        }
    }






//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void handleVizualizareDatePersonale() {
        // Creează un nou stage pentru vizualizarea datelor personale
        Stage datePersonaleStage = new Stage();
        datePersonaleStage.setTitle("Vizualizare Date Personale");

        // Creează un VBox pentru a afișa datele personale
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        // Adaugă datele personale ale administratorului în VBox
        adaugaDatePersonaleAdminInVBox(vbox);

        // Configurarea scenei și afișarea ferestrei
        Scene datePersonaleScene = new Scene(vbox, 400, 300);
        datePersonaleStage.setScene(datePersonaleScene);
        datePersonaleStage.show();
    }



    private void adaugaDatePersonaleAdminInVBox(VBox vbox) {
        conexiune = DatabaseConnection.getConnection();

        // Obține detaliile administratorului din baza de date
        String sql = "SELECT Tip_Utilizator, Nume, Prenume, Adresa, Email, Telefon, Cont_IBAN, Numar_Contract " +
                "FROM SuperAdministratori SA " +
                "JOIN Utilizatori U ON U.ID_Utilizator = SA.ID_Utilizator " +
                "WHERE SA.ID_SuperAdministrator = ?";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setInt(1, getIdSuperAdmin());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                List<String> columnNames = Arrays.asList(
                        "Tip_Utilizator", "Nume", "Prenume", "Adresa", "Email", "Telefon", "Cont_IBAN", "Numar_Contract"
                );

                GridPane gridPane = new GridPane();
                gridPane.setHgap(10);
                gridPane.setVgap(10);

                int row = 0;
                for (String columnName : columnNames) {
                    String value = resultSet.getString(columnName);
                    Label label = new Label(columnName + ": ");
                    TextField textField = new TextField(value);
                    textField.setDisable(true); // Facem TextFields nemodificabile

                    gridPane.add(label, 0, row);
                    gridPane.add(textField, 1, row);

                    row++;
                }

                vbox.getChildren().add(gridPane);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea datelor personale.");
        }
    }



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////







    private void handleIesireCont(Stage primaryStage) {
        System.out.println("Buton - Iesire din Cont");

        boolean confirmareIesire = confirmaActiune("Ești sigur că vrei să ieși din cont?");

        if (confirmareIesire) {
            afiseazaSucces("Ai ieșit cu succes din cont.");

            // Deschide fereastra de autentificare (revenire la logare)
            InterfataAutentificare autentificare = new InterfataAutentificare();
            autentificare.start(primaryStage);
        } else {
            afiseazaEroare("Ieșirea din cont a fost anulată.");
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
    private boolean confirmaActiune(String mesaj) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmare");
        alert.setHeaderText(null);
        alert.setContentText(mesaj);

        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }

}
