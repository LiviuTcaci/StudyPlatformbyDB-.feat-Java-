package com.example.javafx;


import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import java.time.DayOfWeek;




class ProfesorInterface implements UserInterface {

    private Connection conexiune;

    private int idProfesor;

    public ProfesorInterface(Connection conexiune) {
        this.conexiune = conexiune;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    @Override
    public void displayUI(Stage primaryStage, String utilizator) {

        conexiune=DatabaseConnection.getConnection();

        String sqlQuery = "SELECT U.ID_Utilizator, P.ID_Profesor,U.Nume, U.Prenume, U.cnp, U.adresa, U.telefon, U.email, U.cont_IBAN, U.Numar_Contract , P.Ore_Minim, P.Ore_Maxim, P.Departament " +
                "FROM Utilizatori U " +
                "JOIN UtilizatoriAutentificare UA ON U.ID_Utilizator = UA.ID_Utilizator " +
                "JOIN Profesori P ON U.ID_Utilizator = P.ID_Utilizator " +
                "WHERE UA.Nume_Utilizator = ?";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, utilizator);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet != null && resultSet.next()) {
                    // Obține ID-ul studentului și setează-l în obiectul StudentInterface
                    int idProfesor  = resultSet.getInt("ID_Profesor");
                    setIdProfesor(idProfesor);

                    // Afișează interfața grafică pentru student
                    afisareInterfataProfesor(primaryStage, resultSet);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public void afisareInterfataProfesor(Stage primaryStage, ResultSet resultSet) throws SQLException {
        String numeProfesor = resultSet.getString("Nume");
        String prenumeProfesor = resultSet.getString("Prenume");

        // Crează mesajul de bun venit
        Label bunVenitLabel = new Label("Bine ati venit, " + numeProfesor.toUpperCase() + " " + prenumeProfesor.toUpperCase());
        bunVenitLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Button accesareCatalogButton = new Button("Accesare Catalog");
        accesareCatalogButton.setOnAction(e -> handleAccesareCatalog());

        Button adaugareNotaButton = new Button("Adaugare Nota");
        adaugareNotaButton.setOnAction(e -> handleAdaugareNota());

        Button calculMedieButton = new Button("Calcul Medie");
        calculMedieButton.setOnAction(e -> handleCalculMedie());

        Button vizualizareActivitatiButton = new Button("Vizualizare Activitati");
        vizualizareActivitatiButton.setOnAction(e -> handleVizualizareActivitati());

        Button programareActivitateButton = new Button("Programare Activitate");
        programareActivitateButton.setOnAction(e -> handleProgramareActivitate());

        Button vizualizareDatePersonaleButton = new Button("Vizualizare Date Personale");
        vizualizareDatePersonaleButton.setOnAction(e -> handleVizualizareDatePersonale());

        Button calendarButton =  new Button("Accesare Calendar");
        calendarButton.setOnAction(e-> handleCalendar());

        Button vizualizareGrupuri =  new Button("Vizualizare Grupuri");
        vizualizareGrupuri.setOnAction(e-> handleVizualizareGrupuri());




        Button iesireButton = new Button("Iesire din Cont");
        iesireButton.setOnAction(e -> handleIesireCont(primaryStage));

        VBox bunVenitLayout = new VBox(20, bunVenitLabel);
        bunVenitLayout.setAlignment(Pos.TOP_CENTER);

        // Prima coloană de butoane
        VBox coloana1Layout = new VBox(10,
                accesareCatalogButton,
                adaugareNotaButton,
                calculMedieButton
        );

        // A doua coloană de butoane
        VBox coloana2Layout = new VBox(10,
                vizualizareActivitatiButton,
                vizualizareGrupuri,
                programareActivitateButton,
                calendarButton
        );


        VBox coloana3Layout =  new VBox(10,
                vizualizareDatePersonaleButton,
                iesireButton

        );

        HBox mainLayout = new HBox(20);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20, 20, 20, 20));
        mainLayout.getChildren().addAll(bunVenitLayout, coloana1Layout, coloana2Layout,coloana3Layout);

        // Folosește o nouă instanță de VBox pentru a înfășura mesajul de bun venit în finalLayout
        VBox finalLayout = new VBox(20);
        finalLayout.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
        finalLayout.getChildren().addAll(bunVenitLayout, mainLayout);

        Scene scene = new Scene(finalLayout, 800, 600);
        primaryStage.setTitle("Dashboard Profesor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }




    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void handleVizualizareActivitati() {
        conexiune = DatabaseConnection.getConnection();

        // Creează un nou stage pentru vizualizarea activităților
        Stage vizualizareActivitatiStage = new Stage();
        vizualizareActivitatiStage.setTitle("Vizualizare Activități");

        // Creează un TableView și adaugă coloanele
        TableView<ObservableList<String>> tableView = createActivitatiTableView();

        // Populează TableView cu datele din baza de date
        populeazaTableViewActivitati(tableView);

        // Creează un GridPane și adaugă TableView și butonul Descarcă
        GridPane grid = new GridPane();

        // Aliniază tabelul și butonul în mijlocul ferestrei
        grid.setAlignment(Pos.CENTER);

        grid.add(tableView, 0, 0);

        // Adaugă spațiu între tabel și buton
        grid.setVgap(10);

        Button buttonDescarca = new Button("Descarcă Activități");
        buttonDescarca.setOnAction(e -> handleDescarcaActivitati(tableView));

        // Adaugă butonul și aliniază-l cu marginea stângă a tabelului
        grid.add(buttonDescarca, 0, 1);

        // Adaugă GridPane în fereastra de vizualizare
        Scene vizualizareActivitatiScene = new Scene(grid, 800, 600);
        vizualizareActivitatiStage.setScene(vizualizareActivitatiScene);
        vizualizareActivitatiStage.show();
    }



    private TableView<ObservableList<String>> createActivitatiTableView() {
        // Creare tabel pentru afișarea datelor
        TableView<ObservableList<String>> tableView = new TableView<>();

        // Creare coloane pentru fiecare coloană din tabel
        for (String columnName : Arrays.asList("Nume Disciplina", "Tip Activitate", "Ora Inceput", "Ora Sfarsit", "Data Inceput", "Data Sfarsit","Numar_Maxim_Studenti")) {
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnName);
            column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(
                    tableView.getColumns().indexOf(column))));
            tableView.getColumns().add(column);
        }

        return tableView;
    }

    private void populeazaTableViewActivitati(TableView<ObservableList<String>> tableView) {
        // Implementează logica pentru a obține informațiile despre activități din baza de date
        String sql = "SELECT D.Nume_Disciplina, TA.Nume_Tip_Activitate, C.Ora_Inceput, C.Ora_Sfarsit, C.Data_Inceput, C.Data_Sfarsit, A.Numar_Maxim_Studenti, U.Nume AS Nume_Profesor, U.Prenume AS Prenume_Profesor " +
                "FROM Activitati A " +
                "JOIN Calendar C ON A.ID_Activitate = C.ID_Activitate " +
                "JOIN Discipline D ON A.ID_Disciplina = D.ID_Disciplina " +
                "JOIN Tip_Activitate TA ON TA.ID_Tip_Activitate = A.ID_Tip_Activitate " +
                "JOIN Asociere_Disciplina_Profesor ADP ON ADP.ID_Disciplina = D.ID_Disciplina " +
                "JOIN Profesori P ON P.ID_Profesor = ADP.ID_Profesor " +
                "JOIN Utilizatori U ON U.ID_Utilizator = P.ID_Utilizator "+
                "WHERE P.ID_Profesor = ? AND A.ID_Profesor = P.ID_Profesor";


        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setInt(1, getIdProfesor());
            ResultSet resultSet = preparedStatement.executeQuery();

            // Creează o listă observabilă pentru datele din tabel
            ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

            // Adaugă rânduri în listă
            while (resultSet.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(resultSet.getString("Nume_Disciplina"));
                row.add(resultSet.getString("Nume_Tip_Activitate"));
                row.add(resultSet.getString("Ora_Inceput"));
                row.add(resultSet.getString("Ora_Sfarsit"));
                row.add(resultSet.getString("Data_Inceput"));
                row.add(resultSet.getString("Data_Sfarsit"));
                row.add(resultSet.getString("Numar_Maxim_Studenti"));
                data.add(row);
            }

            // Adaugă datele în TableView
            tableView.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea datelor despre activități.");
        }
    }




    private void handleDescarcaActivitati(TableView<ObservableList<String>> tableView) {
        // Creează un workbook (o carte de lucru) Excel
        Workbook workbook = new XSSFWorkbook();

        // Creează o foaie de lucru în workbook
        Sheet sheet = workbook.createSheet("Activitati");

        // Creează un rând pentru antetul tabelului
        Row headerRow = sheet.createRow(0);

        // Adaugă antetele coloanelor în fișierul Excel
        for (int i = 0; i < tableView.getColumns().size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(tableView.getColumns().get(i).getText());
        }

        // Adaugă datele din tabel în fișierul Excel
        for (int i = 0; i < tableView.getItems().size(); i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < tableView.getColumns().size(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(tableView.getItems().get(i).get(j));
            }
        }

        // Salvează workbook-ul într-un fișier Excel
        try (FileOutputStream fileOut = new FileOutputStream("activitati.xlsx")) {
            workbook.write(fileOut);
            fileOut.close();

            // Afișează un mesaj de tip alert în caz de succes
            afiseazaSucces("Fișierul a fost descărcat cu succes!");
        } catch (IOException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la descărcarea datelor în fișier Excel.");
        } finally {
            // Închide workbook-ul indiferent de rezultatul salvării
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    private void handleProgramareActivitate() {
        conexiune = DatabaseConnection.getConnection();

        // Creare elemente UI
        Label labelDisciplina = new Label("Disciplina:");
        ComboBox<String> comboBoxDiscipline = new ComboBox<>();
        Label labelTipActivitate = new Label("Tip Activitate:");
        ComboBox<String> comboBoxTipActivitate = new ComboBox<>();
        Label labelDataInceput = new Label("Data Început:");
        DatePicker datePickerDataInceput = new DatePicker();
        Label labelOraInceput = new Label("Ora Început:");
        ComboBox<String> comboBoxOraInceput = new ComboBox<>();
        Label labelDataSfarsit = new Label("Data Sfârșit:");
        DatePicker datePickerDataSfarsit = new DatePicker();
        Label labelOraSfarsit = new Label("Ora Sfârșit:");
        ComboBox<String> comboBoxOraSfarsit = new ComboBox<>();
        Label labelNrMaximStudenti = new Label("Număr Maxim Studenți:");
        TextField textFieldNrMaximStudenti = new TextField();
        Button buttonProgramareActivitate = new Button("Programează Activitate");

        // Populare ComboBox cu disciplinele existente
        populeazaComboBoxDiscipline(comboBoxDiscipline);
        populeazaComboBoxTipActivitate(comboBoxTipActivitate);

        // Adăugare ore în ComboBox-uri
        for (int ora = 8; ora <= 20; ora++) {
            comboBoxOraInceput.getItems().add(String.format("%02d:00", ora));
            comboBoxOraInceput.getItems().add(String.format("%02d:30", ora));

            comboBoxOraSfarsit.getItems().add(String.format("%02d:00", ora));
            comboBoxOraSfarsit.getItems().add(String.format("%02d:30", ora));
        }

        // Adăugare acțiune la apăsarea butonului
        buttonProgramareActivitate.setOnAction(e -> handleProgramareActivitateFunction(
                comboBoxDiscipline.getValue(),
                comboBoxTipActivitate.getValue(),
                datePickerDataInceput.getValue(),
                comboBoxOraInceput.getValue(),
                datePickerDataSfarsit.getValue(),
                comboBoxOraSfarsit.getValue(),
                textFieldNrMaximStudenti.getText()
        ));

        // Creare layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.add(labelDisciplina, 0, 0);
        grid.add(comboBoxDiscipline, 1, 0);
        grid.add(labelTipActivitate, 0, 1);
        grid.add(comboBoxTipActivitate, 1, 1);
        grid.add(labelDataInceput, 0, 2);
        grid.add(datePickerDataInceput, 1, 2);
        grid.add(labelOraInceput, 0, 3);
        grid.add(comboBoxOraInceput, 1, 3);
        grid.add(labelDataSfarsit, 0, 4);
        grid.add(datePickerDataSfarsit, 1, 4);
        grid.add(labelOraSfarsit, 0, 5);
        grid.add(comboBoxOraSfarsit, 1, 5);
        grid.add(labelNrMaximStudenti, 0, 6);
        grid.add(textFieldNrMaximStudenti, 1, 6);
        grid.add(buttonProgramareActivitate, 0, 7, 2, 1);

        // Creare scenă
        Scene scene = new Scene(grid, 400, 350);

        // Creare și configurare stage-ul pentru programare activități
        Stage programareActivitatiStage = new Stage();
        programareActivitatiStage.setTitle("Programare Activități");
        programareActivitatiStage.setScene(scene);

        // Afișare fereastră
        programareActivitatiStage.show();
    }

    private void populeazaComboBoxDiscipline(ComboBox<String> comboBox) {
        // Conectare la baza de date și obținere discipline
        conexiune = DatabaseConnection.getConnection();

        String sql = "SELECT D.Nume_Disciplina " +
                "FROM Discipline D " +
                "JOIN Asociere_Disciplina_Profesor ADP ON D.ID_Disciplina = ADP.ID_Disciplina " +
                "WHERE ADP.ID_Profesor = ?";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setInt(1, getIdProfesor());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String numeDisciplina = resultSet.getString("Nume_Disciplina");
                comboBox.getItems().add(numeDisciplina);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea datelor despre discipline.");
        }
    }


    private void handleProgramareActivitateFunction(String disciplina, String tipActivitate, LocalDate dataInceput, String oraInceput, LocalDate dataSfarsit, String oraSfarsit,String nrMaximStudenti) {
        conexiune = DatabaseConnection.getConnection();

        // Verificare dacă datele sunt completate
        if (disciplina.isEmpty() || tipActivitate.isEmpty() || oraInceput.isEmpty()  || oraSfarsit.isEmpty()) {
            afiseazaEroare("Te rog completează toate câmpurile.");
            return;
        }

        // Parsare data și oră
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTimeInceput, dateTimeSfarsit;
        try {
            dateTimeInceput = LocalDateTime.parse(dataInceput + " " + oraInceput, formatter);
            dateTimeSfarsit = LocalDateTime.parse(dataSfarsit + " " + oraSfarsit, formatter);
        } catch (Exception e) {
            afiseazaEroare("Format data și oră invalid. Folosește formatul: YYYY-MM-DD HH:mm");
            return;
        }

        // Apel procedură stocată pentru programare activitate
        String sql = "{CALL ProgramareActivitate(?, ?, ?, ?, ?, ?, ?, ?)}";
        try (CallableStatement callableStatement = conexiune.prepareCall(sql)) {
            callableStatement.setInt(1, getIdProfesor());  // ID_Profesor - trebuie să îl înlocuiești cu id-ul profesorului actual
            callableStatement.setInt(2, getIdDisciplinaByNume(disciplina));
            callableStatement.setString(3, tipActivitate);
            callableStatement.setTimestamp(4, Timestamp.valueOf(dateTimeInceput));
            callableStatement.setTime(5, Time.valueOf(dateTimeInceput.toLocalTime()));  // Ora Inceput
            callableStatement.setTime(6, Time.valueOf(dateTimeSfarsit.toLocalTime()));  // Ora Sfarsit
            callableStatement.setTimestamp(7, Timestamp.valueOf(dateTimeSfarsit));
            callableStatement.setString(8,nrMaximStudenti);

            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                String mesaj = resultSet.getString("Mesaj");
                afiseazaInformatii(mesaj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la programarea activității. Verifică consola pentru detalii.");
        }
    }





    private void afiseazaInformatii(String mesaj) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informație");
        alert.setHeaderText(null);
        alert.setContentText(mesaj);
        alert.showAndWait();
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////











    private void handleAccesareCatalog() {
        conexiune = DatabaseConnection.getConnection();

        // Creează un TableView și adaugă coloanele
        TableView<ObservableList<String>> tableView = createCatalogTableView();
        populeazaTableViewCatalog(tableView);

        // Adaugă acțiune la schimbarea disciplinei în ComboBox

        // Adaugă ComboBox-ul și TableView-ul într-un VBox
        VBox vbox = new VBox(tableView);

        Button buttonDescarcaCatalog = new Button("Descarcă Catalog");
        buttonDescarcaCatalog.setOnAction(e -> handleDescarcaCatalog(tableView));

        // Adaugă butonul în VBox sub tabel
        vbox.getChildren().add(buttonDescarcaCatalog);

        // Aliniază VBox-ul în mijlocul scenei
        vbox.setAlignment(Pos.CENTER);

        // Configurarea scenei și afișarea ferestrei
        Scene catalogScene = new Scene(vbox, 800, 600);
        Stage catalogStage = new Stage();
        catalogStage.setScene(catalogScene);
        catalogStage.setTitle("Catalog");
        catalogStage.show();
    }





    private TableView<ObservableList<String>> createCatalogTableView() {
        // Creare tabel pentru afișarea datelor
        TableView<ObservableList<String>> tableView = new TableView<>();

        // Creare coloane pentru fiecare coloană din tabel
        for (String columnName : Arrays.asList("Nume Student", "Prenume Student", "Disciplina", "Note Curs", "Note Seminar", "Note Laborator", "Nota Finala")) {
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnName);
            column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(
                    tableView.getColumns().indexOf(column))));
            tableView.getColumns().add(column);
        }

        return tableView;
    }

    private void populeazaTableViewCatalog(TableView<ObservableList<String>> tableView) {
        // Implementează logica pentru a obține informațiile despre catalog din baza de date
        String sql = "SELECT " +
                "U.Nume, " +
                "U.Prenume, " +
                "D.Nume_Disciplina, " +
                "GROUP_CONCAT(DISTINCT CASE WHEN N.ID_Tip_Activitate = 1 THEN N.Nota ELSE NULL END) AS NoteCurs, " +
                "GROUP_CONCAT(DISTINCT CASE WHEN N.ID_Tip_Activitate = 2 THEN N.Nota ELSE NULL END) AS NoteSeminar, " +
                "GROUP_CONCAT(DISTINCT CASE WHEN N.ID_Tip_Activitate = 3 THEN N.Nota ELSE NULL END) AS NoteLaborator, " +
                "GROUP_CONCAT(DISTINCT CASE WHEN N.ID_Tip_Activitate = 4 THEN N.Nota ELSE NULL END) AS NotaFinala " +
                "FROM " +
                "Studenti S " +
                "JOIN Utilizatori U ON U.ID_Utilizator = S.ID_Utilizator " +
                "JOIN Note N ON S.ID_Student = N.ID_Student " +
                "JOIN Inscriere_Student_Disciplina ISD ON S.ID_Student = ISD.ID_Student " +
                "JOIN Discipline D ON ISD.ID_Disciplina = D.ID_Disciplina " +
                "JOIN Asociere_Student_Profesor ASP ON ASP.ID_Student = S.ID_Student "+
                "JOIN Profesori P ON P.ID_Profesor = ASP.ID_Profesor "+
                "JOIN Asociere_Disciplina_Profesor ADP ON ISD.ID_Disciplina = ADP.ID_Disciplina AND ADP.ID_Profesor = P.ID_Profesor "+
                "JOIN Activitati A ON A.ID_Profesor = P.ID_Profesor AND D.ID_Disciplina = A.ID_Disciplina "+
                "JOIN Inscriere_Student_Activitate ISA ON ISA.ID_Activitate = A.ID_Activitate AND ISA.ID_Student = S.ID_Student "+
                "WHERE " +
                "D.ID_Disciplina IN (SELECT ID_Disciplina FROM Asociere_Disciplina_Profesor WHERE ID_Profesor = ?) AND " +
                "N.ID_Disciplina = D.ID_Disciplina AND " +
                "ASP.ID_Profesor = ? " +
                "GROUP BY " +
                "U.Nume, U.Prenume, D.Nume_Disciplina";


        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setInt(1, getIdProfesor());
            preparedStatement.setInt(2,getIdProfesor());
            ResultSet resultSet = preparedStatement.executeQuery();

            // Creează o listă observabilă pentru datele din tabel
            ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

            // Adaugă rânduri în listă
            while (resultSet.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(resultSet.getString("Nume"));
                row.add(resultSet.getString("Prenume"));
                row.add(resultSet.getString("Nume_Disciplina"));
                row.add(resultSet.getString("NoteCurs"));
                row.add(resultSet.getString("NoteSeminar"));
                row.add(resultSet.getString("NoteLaborator"));
                row.add(resultSet.getString("NotaFinala"));
                data.add(row);
            }

            // Adaugă datele în TableView
            tableView.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea datelor despre catalog.");
        }
    }


    private void handleDescarcaCatalog(TableView<ObservableList<String>> tableView) {
        // Creează un workbook (o carte de lucru) Excel
        Workbook workbook = new XSSFWorkbook();

        // Creează o foaie de lucru în workbook
        Sheet sheet = workbook.createSheet("Catalog");

        // Creează un rând pentru antetul tabelului
        Row headerRow = sheet.createRow(0);

        // Adaugă antetele coloanelor în fișierul Excel
        for (int i = 0; i < tableView.getColumns().size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(tableView.getColumns().get(i).getText());
        }

        // Adaugă datele din tabel în fișierul Excel
        for (int i = 0; i < tableView.getItems().size(); i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < tableView.getColumns().size(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(tableView.getItems().get(i).get(j));
            }
        }

        // Salvează workbook-ul într-un fișier Excel
        try (FileOutputStream fileOut = new FileOutputStream("catalog.xlsx")) {
            workbook.write(fileOut);
            fileOut.close();

            // Afișează un mesaj de tip alert în caz de succes
            afiseazaSucces("Catalogul a fost descărcat cu succes!");
        } catch (IOException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la descărcarea datelor în fișier Excel.");
        } finally {
            // Închide workbook-ul indiferent de rezultatul salvării
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void handleAdaugareNota() {
        conexiune = DatabaseConnection.getConnection();

        Stage adaugareNotaStage = new Stage();
        adaugareNotaStage.setTitle("Adăugare Notă");

        Label labelDisciplina = new Label("Selectează Disciplina:");
        ComboBox<String> comboBoxDiscipline = new ComboBox<>();

        Label labelTipActivitate = new Label("Selectează Tipul Activității:");
        ComboBox<String> comboBoxTipActivitate = new ComboBox<>();


        Label labelStudent = new Label("Selectează Studentul:");
        ComboBox<String> comboBoxStudenti = new ComboBox<>();



        Label labelNota = new Label("Introdu Nota:");
        TextField textFieldNota = new TextField();

        Button buttonAdaugareNota = new Button("Adaugă Notă");

        populeazaComboBoxDisciplineProfesor(comboBoxDiscipline);

        comboBoxDiscipline.setOnAction(e -> {
            String numeDisciplina = comboBoxDiscipline.getValue();
            populeazaComboBoxTipActivitateExistenta(comboBoxTipActivitate, numeDisciplina);
            populeazaComboBoxStudentiDupaDisciplina(comboBoxStudenti, numeDisciplina);
        });


        populeazaComboBoxTipActivitate(comboBoxTipActivitate);

        comboBoxTipActivitate.setOnAction(e -> {
            String numeDisciplina = comboBoxDiscipline.getValue();

            populeazaComboBoxStudentiDupaDisciplina(comboBoxStudenti, numeDisciplina);
        });

        buttonAdaugareNota.setOnAction(e -> {
            String numeDisciplina = comboBoxDiscipline.getValue();
            String numeStudent = comboBoxStudenti.getValue();
            String numeTipActivitate = comboBoxTipActivitate.getValue();
            String notaText = textFieldNota.getText();

            if (numeDisciplina != null && numeStudent != null && numeTipActivitate != null && !notaText.isEmpty()) {
                try {
                    int idStudent = getIdStudentByNume(numeStudent);
                    int idDisciplina = getIdDisciplinaByNume(numeDisciplina);
                    int idTipActivitate = getIdTipActivitateByNume(numeTipActivitate);
                    int nota = Integer.parseInt(notaText);

                    String rezultatAdaugareNota = adaugareNotaStudent(idStudent, idDisciplina, idTipActivitate, nota);

                    afiseazaSucces(rezultatAdaugareNota);

                    adaugareNotaStage.close();
                } catch (NumberFormatException ex) {
                    afiseazaEroare("Introduceți o valoare validă pentru Notă.");
                }
            } else {
                afiseazaEroare("Completați toate câmpurile.");
            }
        });

        GridPane gridAdaugareNota = new GridPane();
        gridAdaugareNota.setHgap(10);
        gridAdaugareNota.setVgap(10);
        gridAdaugareNota.setPadding(new Insets(25, 25, 25, 25));

        gridAdaugareNota.add(labelDisciplina, 0, 0);
        gridAdaugareNota.add(comboBoxDiscipline, 1, 0);

        gridAdaugareNota.add(labelTipActivitate, 0, 1);
        gridAdaugareNota.add(comboBoxTipActivitate, 1, 1);

        gridAdaugareNota.add(labelStudent, 0, 2);
        gridAdaugareNota.add(comboBoxStudenti, 1, 2);

        gridAdaugareNota.add(labelNota, 0, 3);
        gridAdaugareNota.add(textFieldNota, 1, 3);
        gridAdaugareNota.add(buttonAdaugareNota, 0, 4, 2, 1);

        Scene adaugareNotaScene = new Scene(gridAdaugareNota, 400, 300);
        adaugareNotaStage.setScene(adaugareNotaScene);
        adaugareNotaStage.show();
    }


    private void populeazaComboBoxTipActivitateExistenta(ComboBox<String> comboBox, String numeDisciplina) {

        comboBox.getItems().clear();

        // Implementează logica pentru popularea combobox-ului cu tipurile de activități
        conexiune = DatabaseConnection.getConnection();

        String sql = "SELECT DISTINCT TA.Nume_Tip_Activitate " +
                "FROM Tip_Activitate TA " +
                "JOIN Activitati A ON TA.ID_Tip_Activitate = A.ID_Tip_Activitate " +
                "JOIN Calendar C ON A.ID_Activitate = C.ID_Activitate " +
                "JOIN Discipline D ON A.ID_Disciplina = D.ID_Disciplina " +
                "JOIN Profesori P ON P.ID_Profesor = A.ID_Profesor "+
                "WHERE D.Nume_Disciplina = ? AND P.ID_Profesor = ?";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setString(1, numeDisciplina);
            preparedStatement.setInt(2, getIdProfesor()); // Furnizează ID-ul profesorului
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String numeTipActivitate = resultSet.getString("Nume_Tip_Activitate");
                comboBox.getItems().add(numeTipActivitate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea datelor despre tipurile de activități.");
        }
    }





    private void populeazaComboBoxTipActivitate(ComboBox<String> comboBox) {

        comboBox.getItems().clear();

        // Implementează logica pentru popularea combobox-ului cu tipurile de activități
        conexiune = DatabaseConnection.getConnection();

        String sql = "SELECT Nume_Tip_Activitate FROM Tip_Activitate ";

        try (Statement statement = conexiune.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                String numeTipActivitate = resultSet.getString("Nume_Tip_Activitate");
                comboBox.getItems().add(numeTipActivitate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea datelor despre tipurile de activități.");
        }
    }




    private int getIdTipActivitateByNume(String numeTipActivitate) {
        int idTipActivitate = -1;

        conexiune= DatabaseConnection.getConnection();

        String sql = "SELECT ID_Tip_Activitate FROM Tip_Activitate WHERE Nume_Tip_Activitate = ?";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setString(1, numeTipActivitate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                idTipActivitate = resultSet.getInt("ID_Tip_Activitate");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea ID-ului tipului de activitate.");
        }

        return idTipActivitate;
    }

    private int getIdStudentByNume(String numeStudent) {
        int idStudent = -1;

        conexiune= DatabaseConnection.getConnection();

        String sql = "SELECT S.ID_Student " +
                "FROM Studenti S " +
                "JOIN Utilizatori U ON U.ID_Utilizator = S.ID_Utilizator " +
                "WHERE CONCAT(U.Nume, ' ', U.Prenume) = ?";


        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setString(1, numeStudent);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                idStudent = resultSet.getInt("ID_Student");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea ID-ului studentului.");
        }

        return idStudent;
    }


    private int getIdDisciplinaByNume(String numeDisciplina) {
        int idDisciplina = -1;

        String sql = "SELECT ID_Disciplina FROM Discipline WHERE Nume_Disciplina = ?";
        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setString(1, numeDisciplina);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                idDisciplina = resultSet.getInt("ID_Disciplina");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea ID-ului disciplinei.");
        }

        return idDisciplina;
    }

    private String adaugareNotaStudent(int idStudent, int idDisciplina, int idTipActivitate, int nota) {
        String rezultatAdaugareNota = "";

        conexiune= DatabaseConnection.getConnection();

        String sql = "CALL AdaugareNotaStudent(?, ?, ?, ?, ?)";
        try (CallableStatement callableStatement = conexiune.prepareCall(sql)) {
            callableStatement.setInt(1, idStudent);
            callableStatement.setInt(2, idDisciplina);
            callableStatement.setInt(3, idTipActivitate);
            callableStatement.setInt(4, nota);
            callableStatement.setInt(5,getIdProfesor());

            callableStatement.execute();

            rezultatAdaugareNota = "Nota a fost adăugată cu succes.";

        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la adăugarea notei.");
        }

        return rezultatAdaugareNota;
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


















    private void handleCalculMedie() {
        // Creează un nou stage pentru selecția disciplinei și a studentului
        Stage selectareDisciplinaStudentStage = new Stage();
        selectareDisciplinaStudentStage.setTitle("Selectare Disciplina și Student");

        // Creează elementele UI pentru selecția disciplinei și a studentului
        Label labelDisciplina = new Label("Selectează Disciplina:");
        ComboBox<String> comboBoxDiscipline = new ComboBox<>();
        Label labelStudent = new Label("Selectează Studentul:");
        ComboBox<String> comboBoxStudenti = new ComboBox<>();
        Label labelPondereCurs = new Label("Pondere Curs (%):");
        TextField textFieldPondereCurs = new TextField();
        Label labelPondereSeminar = new Label("Pondere Seminar (%):");
        TextField textFieldPondereSeminar = new TextField();
        Label labelPondereLaborator = new Label("Pondere Laborator (%):");
        TextField textFieldPondereLaborator = new TextField();
        Button buttonCalculeazaMedie = new Button("Calculează Medie");

        // Populează ComboBox-urile cu datele necesare (discipline și studenți)
        populeazaComboBoxDisciplineProfesor(comboBoxDiscipline);

        // Adaugă acțiune la schimbarea disciplinei pentru a popula ComboBox-ul de studenți
        comboBoxDiscipline.setOnAction(e -> {
            String numeDisciplina = comboBoxDiscipline.getValue();
            if (numeDisciplina != null) {
                populeazaComboBoxStudentiDupaDisciplina(comboBoxStudenti, numeDisciplina);
            }
        });

        // Adaugă acțiune la apăsarea butonului "Calculează Medie"
        buttonCalculeazaMedie.setOnAction(e -> handleCalculeazaMedieFunction(
                comboBoxDiscipline.getValue(),
                comboBoxStudenti.getValue(),
                textFieldPondereCurs.getText(),
                textFieldPondereSeminar.getText(),
                textFieldPondereLaborator.getText(),
                selectareDisciplinaStudentStage
        ));

        // Adaugă elementele UI pe grilă
        GridPane gridSelectareDisciplinaStudent = new GridPane();
        gridSelectareDisciplinaStudent.setHgap(10);
        gridSelectareDisciplinaStudent.setVgap(10);
        gridSelectareDisciplinaStudent.setPadding(new Insets(25, 25, 25, 25));
        gridSelectareDisciplinaStudent.add(labelDisciplina, 0, 0);
        gridSelectareDisciplinaStudent.add(comboBoxDiscipline, 1, 0);
        gridSelectareDisciplinaStudent.add(labelStudent, 0, 1);
        gridSelectareDisciplinaStudent.add(comboBoxStudenti, 1, 1);
        gridSelectareDisciplinaStudent.add(labelPondereCurs, 0, 2);
        gridSelectareDisciplinaStudent.add(textFieldPondereCurs, 1, 2);
        gridSelectareDisciplinaStudent.add(labelPondereSeminar, 0, 3);
        gridSelectareDisciplinaStudent.add(textFieldPondereSeminar, 1, 3);
        gridSelectareDisciplinaStudent.add(labelPondereLaborator, 0, 4);
        gridSelectareDisciplinaStudent.add(textFieldPondereLaborator, 1, 4);
        gridSelectareDisciplinaStudent.add(buttonCalculeazaMedie, 0, 5, 2, 1);

        // Configurarea scenei și afișarea ferestrei
        Scene selectareDisciplinaStudentScene = new Scene(gridSelectareDisciplinaStudent, 400, 300);
        selectareDisciplinaStudentStage.setScene(selectareDisciplinaStudentScene);
        selectareDisciplinaStudentStage.show();
    }


    private void populeazaComboBoxDisciplineProfesor(ComboBox<String> comboBox) {
        // Accesează baza de date pentru a obține numele disciplinelor predate de profesor
        conexiune = DatabaseConnection.getConnection();

        String sql = "SELECT D.Nume_Disciplina " +
                "FROM Discipline D " +
                "JOIN Asociere_Disciplina_Profesor ADP ON D.ID_Disciplina = ADP.ID_Disciplina " +
                "WHERE ADP.ID_Profesor = ?";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setInt(1, getIdProfesor());  // Modificare aici pentru a obține ID-ul profesorului curent
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String numeDisciplina = resultSet.getString("Nume_Disciplina");
                comboBox.getItems().add(numeDisciplina);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea datelor despre disciplinele predate de profesor.");
        }
    }


    private void handleCalculeazaMedieFunction(String numeDisciplina, String numeStudent,
                                               String pondereCurs, String pondereSeminar, String pondereLaborator,
                                               Stage selectareDisciplinaStudentStage) {
        // Verifică dacă toate câmpurile sunt completate
        if (numeDisciplina != null && numeStudent != null
                && !pondereCurs.isEmpty() && !pondereSeminar.isEmpty() && !pondereLaborator.isEmpty()) {
            try {
                int idStudent = getIdStudentByNume(numeStudent);
                int idDisciplina = getIdDisciplinaByNume(numeDisciplina);
                int procentCurs = Integer.parseInt(pondereCurs);
                int procentSeminar = Integer.parseInt(pondereSeminar);
                int procentLaborator = Integer.parseInt(pondereLaborator);

                // Verifică dacă procentajul total este 100
                if (procentCurs + procentSeminar + procentLaborator == 100) {
                    // Apelază procedura stocată pentru calculul notei finale
                    String rezultatCalcul = calculNotaFinala(idStudent, idDisciplina, procentCurs, procentSeminar, procentLaborator);

                    // Afișează rezultatul
                    afiseazaSucces(rezultatCalcul);

                    // Închide fereastra de selecție a disciplinei și studentului
                    selectareDisciplinaStudentStage.close();
                } else {
                    afiseazaEroare("Procentajul total trebuie să fie 100.");
                }
            } catch (NumberFormatException ex) {
                afiseazaEroare("Introduceți valori numerice pentru procentaje.");
            }
        } else {
            afiseazaEroare("Completați toate câmpurile.");
        }
    }

    private String calculNotaFinala(int idStudent, int idDisciplina, int procentCurs, int procentSeminar, int procentLaborator) {
        String rezultatCalcul = "";

        conexiune = DatabaseConnection.getConnection();

        String sql = "CALL CalculNotaFinala(?, ?, ?, ?, ?, ?)";
        try (CallableStatement callableStatement = conexiune.prepareCall(sql)) {
            callableStatement.setInt(1, idStudent);
            callableStatement.setInt(2, idDisciplina);
            callableStatement.setInt(3, procentCurs);
            callableStatement.setInt(4, procentSeminar);
            callableStatement.setInt(5, procentLaborator);
            callableStatement.setInt(6, getIdProfesor());

            callableStatement.executeQuery();

            // Nu extragem nota finala de aici

            // Obținem nota finală direct din tabela Note
            String sqlNotaFinala = "SELECT Nota FROM Note WHERE ID_Student = ? AND ID_Disciplina = ? AND ID_Tip_Activitate = 4";
            try (PreparedStatement preparedStatement = conexiune.prepareStatement(sqlNotaFinala)) {
                preparedStatement.setInt(1, idStudent);
                preparedStatement.setInt(2, idDisciplina);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    BigDecimal notaFinala = resultSet.getBigDecimal("Nota");
                    if (notaFinala != null) {
                        rezultatCalcul = notaFinala.toString();
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la calculul notei finale.");
        }

        return rezultatCalcul;
    }


    private void populeazaComboBoxStudentiDupaDisciplina(ComboBox<String> comboBoxStudenti, String numeDisciplina) {
        // Accesează baza de date pentru a obține numele studenților înscrise la disciplina selectată
        conexiune = DatabaseConnection.getConnection();

        // Curăță lista existentă de studenți
        comboBoxStudenti.getItems().clear();

        // Verifică dacă numele disciplinei nu este gol
        if (numeDisciplina != null) {

            int idDisciplina = getIdDisciplinaByNume(numeDisciplina);

            String sql = "SELECT DISTINCT U.Nume, U.Prenume " +
                    "FROM Studenti S " +
                    "JOIN Inscriere_Student_Disciplina ISD ON S.ID_Student = ISD.ID_Student " +
                    "JOIN Discipline D ON ISD.ID_Disciplina = D.ID_Disciplina " +
                    "JOIN Utilizatori U ON U.ID_Utilizator = S.ID_Utilizator " +
                    "JOIN Asociere_Student_Profesor ASP ON ASP.ID_Student = S.ID_Student "+
                    "JOIN Profesori P ON ASP.ID_Profesor = P.ID_Profesor "+
                    "JOIN Asociere_Disciplina_Profesor ADP ON ISD.ID_Disciplina = ADP.ID_Disciplina AND ADP.ID_Profesor = P.ID_Profesor "+
                    "JOIN Activitati A ON A.ID_Profesor = P.ID_Profesor AND D.ID_Disciplina = A.ID_Disciplina "+
                    "JOIN Inscriere_Student_Activitate ISA ON ISA.ID_Activitate = A.ID_Activitate AND ISA.ID_Student = S.ID_Student "+
                    "WHERE D.ID_Disciplina = ? AND P.ID_Profesor = ? ";



            try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
                preparedStatement.setInt(1,idDisciplina);
                preparedStatement.setInt(2, getIdProfesor());
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String numeStudent = resultSet.getString("Nume");
                    String prenumeStudent = resultSet.getString("Prenume");
                    String numeComplet = numeStudent + " " + prenumeStudent;
                    comboBoxStudenti.getItems().add(numeComplet);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                afiseazaEroare("Eroare la obținerea datelor despre studenții înscriși la disciplină și activitatea specificată.");
            }

        }
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////






//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void handleVizualizareDatePersonale() {
        // Creează un nou stage pentru vizualizarea datelor personale
        Stage datePersonaleStage = new Stage();
        datePersonaleStage.setTitle("Vizualizare Date Personale Profesor");

        // Creează VBox pentru a afișa datele personale
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        // Adaugă datele personale în VBox
        adaugaDatePersonaleProfesorInVBox(vbox);

        // Configurarea scenei și afișarea ferestrei
        Scene datePersonaleScene = new Scene(vbox, 400, 400);
        datePersonaleStage.setScene(datePersonaleScene);
        datePersonaleStage.show();
    }

    private void adaugaDatePersonaleProfesorInVBox(VBox vbox) {
        conexiune = DatabaseConnection.getConnection();

        // Obține detaliile profesorului din baza de date
        String sql = "SELECT Tip_Utilizator, Nume, Prenume, Adresa, Email, Telefon, Cont_IBAN, Departament, Ore_Minim, Ore_Maxim " +
                "FROM Profesori P " +
                "JOIN Utilizatori U ON U.ID_Utilizator = P.ID_Utilizator " +
                "WHERE P.ID_Profesor = ?";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setInt(1, getIdProfesor());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                List<String> columnNames = Arrays.asList(
                        "Tip_Utilizator", "Nume", "Prenume", "Adresa", "Email", "Telefon", "Cont_IBAN", "Departament", "Ore_Minim", "Ore_Maxim"
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




    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void handleCalendar() {
        // Crează un GridPane pentru a afișa calendarul
        GridPane calendarGrid = new GridPane();
        calendarGrid.setHgap(10);
        calendarGrid.setVgap(10);
        calendarGrid.setAlignment(Pos.CENTER); // Setează alinierea la mijloc

        // Adaugă un DatePicker pentru a selecta data
        DatePicker datePicker = new DatePicker();
        datePicker.setOnAction(e -> handleDateSelection(datePicker.getValue(), calendarGrid));

        // Adaugă DatePicker la GridPane
        calendarGrid.add(datePicker, 0, 0);

        // Crează fereastra pentru calendar
        Stage calendarStage = new Stage();
        calendarStage.setTitle("Calendar");

        // Adaugă GridPane la scena
        Scene calendarScene = new Scene(calendarGrid, 600, 400);
        calendarStage.setScene(calendarScene);
        calendarStage.show();
    }

    private void handleDateSelection(LocalDate selectedDate, GridPane calendarGrid) {
        // Implementează afișarea activităților pentru data selectată din baza de date pentru profesor

        // Interogare SQL pentru a obține activitățile pentru data selectată și pentru profesor
        String sqlQuery = "SELECT D.Nume_Disciplina, TA.Nume_Tip_Activitate, C.Ora_Inceput, C.Ora_Sfarsit " +
                "FROM Calendar C " +
                "JOIN Activitati A ON C.ID_Activitate = A.ID_Activitate " +
                "JOIN Discipline D ON D.ID_Disciplina = A.ID_Disciplina " +
                "JOIN Asociere_Disciplina_Profesor ADP ON ADP.ID_Disciplina = D.ID_Disciplina " +
                "JOIN Profesori P ON P.ID_Profesor = ADP.ID_Profesor " +
                "JOIN Tip_Activitate TA ON A.ID_Tip_Activitate = TA.ID_Tip_Activitate " +
                "WHERE ? >= C.Data_Inceput AND ?<= C.Data_Sfarsit AND P.ID_Profesor = ? " +
                "AND DAYOFWEEK(?) = DAYOFWEEK(C.Data_Inceput) AND P.ID_Profesor = A.ID_Profesor ";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sqlQuery)) {
            preparedStatement.setDate(1, Date.valueOf(selectedDate));
            preparedStatement.setDate(2, Date.valueOf(selectedDate));
            preparedStatement.setInt(3, getIdProfesor());
            preparedStatement.setDate(4, Date.valueOf(selectedDate));


            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Crează un TableView pentru afișarea datelor
                TableView<ObservableList<String>> tableView = createActivitateTableView();

                // Adaugă coloanele în TableView
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    final int index = i - 1;
                    TableColumn<ObservableList<String>, String> column = new TableColumn<>(resultSet.getMetaData().getColumnName(i));
                    column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(index)));
                    tableView.getColumns().add(column);
                }

                // Adaugă rândurile în TableView
                while (resultSet.next()) {
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                        row.add(resultSet.getString(i));
                    }
                    tableView.getItems().add(row);
                }

                // Adaugă TableView în GridPane
                calendarGrid.add(tableView, 0, 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea datelor despre activitățile planificate pentru profesor.");
        }
    }

    private TableView<ObservableList<String>> createActivitateTableView() {
        // Crează tabel pentru afișarea datelor
        TableView<ObservableList<String>> tableView = new TableView<>();

        // Adaugă coloanele în TableView
        for (TableColumn<ObservableList<String>, ?> column : tableView.getColumns()) {
            column.setStyle("-fx-alignment: CENTER;"); // Setează alinierea la mijloc pentru fiecare coloană
        }

        return tableView;
    }







    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void handleVizualizareGrupuri() {
        System.out.println("Buton - Vizualizare Membri Grup");

        // Creează un nou stage pentru vizualizarea membrilor grupului
        Stage vizualizareMembriGrupStage = new Stage();
        vizualizareMembriGrupStage.setTitle("Vizualizare Membri Grup");

        // Creează un TableView pentru afișarea datelor membrilor grupului
        TableView<ObservableList<String>> tableViewMembri = new TableView<>();
        configureTableView(tableViewMembri, Arrays.asList("Nume", "Prenume"));

        // Creează un TableView pentru afișarea sugestiilor de participanți
        TableView<ObservableList<String>> tableViewSugestii = new TableView<>();
        configureTableView(tableViewSugestii, Arrays.asList("Nume", "Prenume"));

        // Creează un TableView pentru afișarea activităților planificate
        TableView<ObservableList<String>> tableViewActivitati = new TableView<>();
        configureTableView(tableViewActivitati, Arrays.asList("Nume Activitate", "Ora Inceput", "Ora Sfarsit", "Data"));

        // Creează un ComboBox pentru a alege grupul
        ComboBox<String> comboBoxGrupuri = new ComboBox<>();

        // Adaugă acțiune la selectarea unui grup din ComboBox
        comboBoxGrupuri.setOnAction(e -> handleAfisareMembriGrup(
                comboBoxGrupuri.getValue(),
                tableViewMembri,
                tableViewSugestii,
                tableViewActivitati
        ));

        // Populează ComboBox-ul cu numele grupurilor
        populeazaComboBoxGrupuri(comboBoxGrupuri);

        // Adaugă ComboBox și cele două TableView-uri într-un VBox
        VBox vbox = new VBox(10, comboBoxGrupuri, tableViewMembri, tableViewSugestii);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        // Adaugă titlul "Membri Grup" deasupra tabelului
        Label labelMembriGrup = new Label("Membri Grup");
        labelMembriGrup.setStyle("-fx-font-size: 13; -fx-font-weight: bold;");
        vbox.getChildren().add(1, labelMembriGrup);

        // Adaugă titlul "Sugestii de Participanți la Grup" deasupra tabelului de sugestii
        Label labelSugestii = new Label("Sugestii de Participanți la Grup");
        labelSugestii.setStyle("-fx-font-size: 13; -fx-font-weight: bold;");
        vbox.getChildren().add(3, labelSugestii);

        // Adaugă titlul "Activitati Planificate" deasupra tabelului de activități
        Label labelActivitati = new Label("Activitati Planificate");
        labelActivitati.setStyle("-fx-font-size: 13; -fx-font-weight: bold;");
        vbox.getChildren().add(5, labelActivitati);

        // Adaugă TableView-ul pentru activități în VBox
        vbox.getChildren().add(6, tableViewActivitati);

        // Configurarea scenei și afișarea ferestrei
        Scene vizualizareMembriGrupScene = new Scene(vbox, 800, 600);
        vizualizareMembriGrupStage.setScene(vizualizareMembriGrupScene);
        vizualizareMembriGrupStage.show();
    }


    private void handleAfisareMembriGrup(String numeGrup, TableView<ObservableList<String>> tableViewMembri,
                                         TableView<ObservableList<String>> tableViewSugestii,
                                         TableView<ObservableList<String>> tableViewActivitati) {
        // Accesează baza de date pentru a obține membrii grupului și îi adaugă în TableView.
        conexiune = DatabaseConnection.getConnection();

        String sqlMembri = "SELECT U.Nume, U.Prenume " +
                "FROM Membri_Grup MG " +
                "JOIN Studenti S ON MG.ID_Student = S.ID_Student " +
                "JOIN Grupuri_Studiu G ON MG.ID_Grup = G.ID_Grup " +
                "JOIN Utilizatori U ON U.ID_Utilizator = S.ID_Utilizator " +
                "WHERE G.Nume_Grup = ?";

        String sqlSugestii = "SELECT U.Nume, U.Prenume " +
                "FROM Studenti S " +
                "JOIN Inscriere_Student_Disciplina ISD ON S.ID_Student = ISD.ID_Student " +
                "JOIN Utilizatori U ON U.ID_Utilizator = S.ID_Utilizator " +
                "WHERE ISD.ID_Disciplina = (SELECT ID_Disciplina FROM Grupuri_Studiu WHERE Nume_Grup = ?) " +
                "AND S.ID_Student NOT IN (SELECT ID_Student FROM Membri_Grup MG " +
                "                         JOIN Grupuri_Studiu G ON MG.ID_Grup = G.ID_Grup " +
                "                         WHERE G.Nume_Grup = ?)";

        try (PreparedStatement preparedStatementMembri = conexiune.prepareStatement(sqlMembri);
             PreparedStatement preparedStatementSugestii = conexiune.prepareStatement(sqlSugestii)) {
            preparedStatementMembri.setString(1, numeGrup);
            preparedStatementSugestii.setString(1, numeGrup);
            preparedStatementSugestii.setString(2, numeGrup);

            // Șterge conținutul vechi din TableView-uri
            tableViewMembri.getItems().clear();
            tableViewSugestii.getItems().clear();
            tableViewActivitati.getItems().clear();

            // Adaugă membrii în TableView-ul pentru membri grupului
            ResultSet resultSetMembri = preparedStatementMembri.executeQuery();
            while (resultSetMembri.next()) {
                ObservableList<String> rowData = FXCollections.observableArrayList(
                        resultSetMembri.getString("Nume"),
                        resultSetMembri.getString("Prenume")
                );
                tableViewMembri.getItems().add(rowData);
            }

            // Adaugă sugestiile în TableView-ul pentru sugestii
            ResultSet resultSetSugestii = preparedStatementSugestii.executeQuery();
            while (resultSetSugestii.next()) {
                ObservableList<String> rowData = FXCollections.observableArrayList(
                        resultSetSugestii.getString("Nume"),
                        resultSetSugestii.getString("Prenume")
                );
                tableViewSugestii.getItems().add(rowData);
            }

            // Apelează metoda pentru afișarea activităților planificate
            handleAfisareActivitatiPlanificate(numeGrup, tableViewActivitati);
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea datelor despre membrii grupului.");
        }
    }

    private void handleAfisareActivitatiPlanificate(String numeGrup, TableView<ObservableList<String>> tableViewActivitati) {
        // Accesează baza de date pentru a obține activitățile planificate și le adaugă în TableView.
        String sqlActivitati = "SELECT Nume_Activitate, Ora_Inceput, Ora_Sfarsit, Data " +
                "FROM Activitati_Grup AG " +
                "WHERE AG.ID_Grup = (SELECT ID_Grup FROM Grupuri_Studiu WHERE Nume_Grup = ?) " +
                "AND AG.ID_Profesor = ?";

        try (PreparedStatement preparedStatementActivitati = conexiune.prepareStatement(sqlActivitati)) {
            preparedStatementActivitati.setString(1, numeGrup);
            preparedStatementActivitati.setInt(2, getIdProfesor());  // Utilizează ID-ul profesorului logat

            // Șterge conținutul vechi din TableView-ul pentru activități planificate
            tableViewActivitati.getItems().clear();

            // Adaugă activitățile în TableView-ul pentru activități planificate
            ResultSet resultSetActivitati = preparedStatementActivitati.executeQuery();
            while (resultSetActivitati.next()) {
                ObservableList<String> rowData = FXCollections.observableArrayList(
                        resultSetActivitati.getString("Nume_Activitate"),
                        resultSetActivitati.getString("Ora_Inceput"),
                        resultSetActivitati.getString("Ora_Sfarsit"),
                        resultSetActivitati.getString("Data")
                );
                tableViewActivitati.getItems().add(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea datelor despre activitățile planificate.");
        }
    }


    private void populeazaComboBoxGrupuri(ComboBox<String> comboBox) {
        // Accesează baza de date pentru a obține numele grupurilor disponibile pentru profesor
        conexiune = DatabaseConnection.getConnection();

        // Modifică interogarea SQL pentru a include doar grupurile la care profesorul preda
        String sql = "SELECT DISTINCT G.Nume_Grup " +
                "FROM Grupuri_Studiu G " +
                "JOIN Discipline D ON G.ID_Disciplina = D.ID_Disciplina " +
                "JOIN Asociere_Disciplina_Profesor ADP ON ADP.ID_Disciplina = D.ID_Disciplina "+
                "JOIN Profesori P ON ADP.ID_Profesor = P.ID_Profesor " +
                "WHERE P.ID_Profesor = ?";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setInt(1, getIdProfesor());  // Utilizează ID-ul profesorului logat

            ResultSet resultSet = preparedStatement.executeQuery();

            // Șterge conținutul vechi din ComboBox
            comboBox.getItems().clear();

            // Adaugă numele grupurilor în ComboBox
            while (resultSet.next()) {
                String numeGrup = resultSet.getString("Nume_Grup");
                comboBox.getItems().add(numeGrup);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea datelor despre grupuri de studiu.");
        }
    }

    private void configureTableView(TableView<ObservableList<String>> tableView, List<String> columnNames) {
        // Șterge coloanele existente
        tableView.getColumns().clear();

        // Creare coloane pentru fiecare coloană din tabel
        for (String columnName : columnNames) {
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnName);
            column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(
                    tableView.getColumns().indexOf(column))));
            tableView.getColumns().add(column);
        }
    }






    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    private void handleIesireCont(Stage primaryStage) {
        System.out.println("Buton - Iesire din Cont");

        boolean confirmareIesire = confirmaActiune("Ești sigur că vrei să ieși din cont?");

        if (confirmareIesire) {
            afiseazaSucces("Ai ieșit cu succes din cont.");

            // Deschide fereastra de autentificare (revenire la logare)
            InterfataAutentificare autentificare = new InterfataAutentificare();
            autentificare.start(primaryStage);
        }
        else {
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





    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
