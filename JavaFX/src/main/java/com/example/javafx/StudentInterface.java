package com.example.javafx;


import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.sql.*;


import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.util.*;


import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.geometry.Pos;






// Clasă pentru interfața student
class StudentInterface implements UserInterface {

    private Connection conexiune;
    private int idStudent;


    public StudentInterface(Connection conexiune) {
        this.conexiune = conexiune;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public int getIdStudent() {
        return idStudent;
    }

    @Override
    public void displayUI(Stage primaryStage, String utilizator) {
        // Obține conexiunea la baza de date
        conexiune = DatabaseConnection.getConnection();

        // Interogare pentru a obține detaliile studentului
        String sqlQuery = "SELECT U.ID_Utilizator,S.ID_Student, U.Nume, U.Prenume, U.cnp, U.adresa, U.telefon, U.email, U.cont_IBAN, U.Numar_Contract, S.An_Studiu, S.Ore_Sustinute " +
                "FROM Utilizatori U " +
                "JOIN UtilizatoriAutentificare UA ON U.ID_Utilizator = UA.ID_Utilizator " +
                "JOIN Studenti S ON U.ID_Utilizator = S.ID_Utilizator " +
                "WHERE UA.Nume_Utilizator = ?";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, utilizator);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet != null && resultSet.next()) {
                    // Obține ID-ul studentului și setează-l în obiectul StudentInterface
                    int idStudent = resultSet.getInt("ID_Student");
                    setIdStudent(idStudent);

                    // Afișează interfața grafică pentru student
                    afiseazaInterfataStudent(primaryStage, resultSet);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }




    // Metoda separată pentru afișarea interfeței studentului
    private void afiseazaInterfataStudent(Stage primaryStage, ResultSet resultSet) throws SQLException {

        conexiune = DatabaseConnection.getConnection();

        // Obține detaliile studentului din ResultSet
        // Obține detaliile studentului din ResultSet
        String numeStudent = resultSet.getString("Nume");
        String prenumeStudent = resultSet.getString("Prenume");

        // Crează mesajul de bun venit
        Label bunVenitLabel = new Label("Bine ati venit, " + numeStudent.toUpperCase() + " " + prenumeStudent.toUpperCase());
        bunVenitLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        // Butonul pentru vizualizarea notelor
        Button vizualizareNoteButton = new Button("Vizualizare Note");
        vizualizareNoteButton.setOnAction(e -> handleVizualizareNote());

        // Butonul pentru vizualizarea activităților
        Button vizualizareActivitatiButton = new Button("Vizualizare Activitati");
        vizualizareActivitatiButton.setOnAction(e -> handleVizualizareActivitati());

        // Butonul pentru vizualizarea disciplinelor
        Button vizualizareDisciplineButton = new Button("Vizualizare Discipline");
        vizualizareDisciplineButton.setOnAction(e -> handleVizualizareDiscipline());

        // Butonul pentru inscrierea la discipline
        Button inscriereDisciplineButton = new Button("Inscriere Discipline");
        inscriereDisciplineButton.setOnAction(e -> handleInscriereDiscipline());

        // Butonul pentru renunțarea la activități
        Button renuntareDisciplineButton = new Button("Renuntare Discipline");
        renuntareDisciplineButton.setOnAction(e -> handleRenuntareDiscipline());

        Button renuntareActivitateGrupButton =  new Button("Renuntare Activitate Grup");
        renuntareActivitateGrupButton.setOnAction(e -> handleRenuntareActivitateGrup());

        // Butonul pentru vizualizarea grupurilor de studiu
        Button vizualizareGrupuriButton = new Button("Vizualizare Grupuri Studiu");
        vizualizareGrupuriButton.setOnAction(e -> handleVizualizareGrupuri());

        // Butonul pentru inscrierea la un grup de studiu
        Button inscriereGrupStudiuButton = new Button("Inscriere Grup Studiu");
        inscriereGrupStudiuButton.setOnAction(e -> handleInscriereGrupStudiu());

        // Butonul pentru renunțarea la un grup de studiu
        Button iesireGrupButton = new Button("Iesire din Grup");
        iesireGrupButton.setOnAction(e -> handleIesireGrup());

        // Butonul pentru vizualizarea membrilor unui grup
        Button vizualizareMembriGrupButton = new Button("Vizualizare Membri Grup");
        vizualizareMembriGrupButton.setOnAction(e -> handleVizualizareMembriGrup());


        // Butonul pentru programarea unei activități într-un grup
        Button programeazaActivitateGrupButton = new Button("Programeaza Activitate Grup");
        programeazaActivitateGrupButton.setOnAction(e -> handleProgrameazaActivitateGrup());

        // Butonul pentru inscrierea la o activitate într-un grup
        Button inscriereActivitateGrupButton = new Button("Inscriere Activitate Grup");
        inscriereActivitateGrupButton.setOnAction(e -> handleInscriereActivitateGrup());

        // Butonul pentru trimiterea unui mesaj
        Button trimitereMesajButton = new Button("Trimitere Mesaj");
        trimitereMesajButton.setOnAction(e -> handleTrimitereMesaj());

        // Butonul pentru ieșirea din cont
        Button iesireButton = new Button("Iesire din Cont");
        iesireButton.setOnAction(e -> handleIesireCont(primaryStage));


        Button vizualizareDatePersonaleButton = new Button("Vizualizare Date Personale");
        vizualizareDatePersonaleButton.setOnAction(e -> handleVizualizareDatePersonale());

        Button vizualizareMesajeButton =  new Button("Vizualizare Mesaje");
        vizualizareMesajeButton.setOnAction(e-> handleVizualizareMesaje());


        Button calendarButton =  new Button("Accesare Calendar");
        calendarButton.setOnAction(e-> handleCalendar());

        VBox bunVenitLayout = new VBox(20, bunVenitLabel);
        bunVenitLayout.setAlignment(Pos.TOP_CENTER);

        // Layout pentru butoanele din coloana 1
        VBox coloana1Layout = new VBox(10,
                vizualizareNoteButton,
                vizualizareDisciplineButton,
                inscriereDisciplineButton,
                renuntareDisciplineButton,
                calendarButton
        );

        // Layout pentru butoanele din coloana 2
        VBox coloana2Layout = new VBox(10,
                vizualizareActivitatiButton,
                vizualizareGrupuriButton,
                programeazaActivitateGrupButton,
                inscriereActivitateGrupButton,
                renuntareActivitateGrupButton
        );

        // Layout pentru butoanele din coloana 3
        VBox coloana3Layout = new VBox(10,
                inscriereGrupStudiuButton,
                iesireGrupButton,
                vizualizareMembriGrupButton,
                trimitereMesajButton,
                vizualizareMesajeButton
        );

        VBox coloana4Layout =  new VBox(10,
                vizualizareDatePersonaleButton,
                iesireButton

        );

        // Layout principal pentru interfața studentului
        HBox mainLayout = new HBox(20);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20, 20, 20, 20));
        mainLayout.getChildren().addAll(coloana1Layout, coloana2Layout, coloana3Layout,coloana4Layout);

        // Layout final, adăugând mesajul de bun venit deasupra butoanelor
        VBox finalLayout = new VBox(20);
        finalLayout.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
        finalLayout.getChildren().addAll(bunVenitLayout, mainLayout);

        Scene scene = new Scene(finalLayout, 800, 600);
        primaryStage.setTitle("Dashboard Student");
        primaryStage.setScene(scene);
        primaryStage.show();
    }




    private void handleVizualizareNote() {
        conexiune = DatabaseConnection.getConnection();

        String sql = "SELECT D.Nume_Disciplina, " +
                "GROUP_CONCAT(CASE WHEN TA.Nume_Tip_Activitate = 'Curs' THEN N.Nota END) AS 'Note Curs', " +
                "GROUP_CONCAT(CASE WHEN TA.Nume_Tip_Activitate = 'Seminar' THEN N.Nota END) AS 'Note Seminar', " +
                "GROUP_CONCAT(CASE WHEN TA.Nume_Tip_Activitate = 'Laborator' THEN N.Nota END) AS 'Note Laborator', " +
                "GROUP_CONCAT(CASE WHEN TA.Nume_Tip_Activitate = 'Examen' THEN N.Nota END) AS 'Nota Finala' " +
                "FROM Inscriere_Student_Disciplina ISD " +
                "JOIN Discipline D ON ISD.ID_Disciplina = D.ID_Disciplina " +
                "JOIN Note N ON N.ID_Student = ISD.ID_Student AND N.ID_Disciplina = ISD.ID_Disciplina " +
                "JOIN Tip_Activitate TA ON TA.ID_Tip_Activitate = N.ID_Tip_Activitate " +
                "JOIN Studenti S ON S.ID_Student = ISD.ID_Student " +
                "WHERE S.ID_Student = ? " +
                "GROUP BY D.Nume_Disciplina";



        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setInt(1, getIdStudent());
            ResultSet resultSet = preparedStatement.executeQuery();

            // Crează un TableView pentru afișarea notelor
            TableView<ObservableList<String>> tableView = new TableView<>();

            // Crează coloanele tabelului
            // Crează coloanele tabelului
            TableColumn<ObservableList<String>, String> disciplinaCol = new TableColumn<>("Disciplina");
            disciplinaCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(0)));

            TableColumn<ObservableList<String>, String> cursCol = new TableColumn<>("Note Curs");
            cursCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(1)));

            TableColumn<ObservableList<String>, String> seminarCol = new TableColumn<>("Note Seminar");
            seminarCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(2)));

            TableColumn<ObservableList<String>, String> laboratorCol = new TableColumn<>("Note Laborator");
            laboratorCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(3)));

            TableColumn<ObservableList<String>, String> finalaCol = new TableColumn<>("Nota Finala");
            finalaCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(4)));


            // Adaugă coloanele la TableView
            tableView.getColumns().addAll(disciplinaCol, cursCol, seminarCol, laboratorCol, finalaCol);

            // Adaugă datele în TableView
            while (resultSet.next()) {
                ObservableList<String> rowData = FXCollections.observableArrayList();
                rowData.add(resultSet.getString("Nume_Disciplina"));

                String noteCurs = resultSet.getString("Note Curs");
                rowData.add(noteCurs != null ? noteCurs : "");

                String noteSeminar = resultSet.getString("Note Seminar");
                rowData.add(noteSeminar != null ? noteSeminar : "");

                String noteLaborator = resultSet.getString("Note Laborator");
                rowData.add(noteLaborator != null ? noteLaborator : "");

                String notaFinala = resultSet.getString("Nota Finala");
                rowData.add(notaFinala != null ? notaFinala : "");

                tableView.getItems().add(rowData);
            }


            // Crează fereastra
            Stage noteStage = new Stage();
            noteStage.setTitle("Vizualizare Note");

            // Adaugă TableView la scena
            Scene noteScene = new Scene(tableView, 600, 400);
            noteStage.setScene(noteScene);
            noteStage.show();

        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea datelor despre notele studentului.");
        }
    }




    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void handleVizualizareActivitati() {
        conexiune = DatabaseConnection.getConnection();

        try {
            String sqlQuery = "CALL AfisareActivitatiStudent(?)";

            try (PreparedStatement preparedStatement = conexiune.prepareStatement(sqlQuery)) {
                preparedStatement.setInt(1, idStudent);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet != null) {
                        Stage activitatiStage = new Stage();
                        activitatiStage.setTitle("Vizualizare Activitati");

                        // Creare VBox pentru a împărți fereastra în două părți
                        VBox vboxPrincipal = new VBox(10);

                        // Creare tabel pentru activitățile la care studentul este înscris
                        TableView<ObservableList<String>> tableViewActivitatiInscris = createActivitatiTableView();
                        vboxPrincipal.getChildren().add(tableViewActivitatiInscris);

                        // Creare tabel pentru activitățile de grup
                        TableView<ObservableList<String>> tableViewActivitatiGrup = createActivitatiGrupTableView();
                        vboxPrincipal.getChildren().add(tableViewActivitatiGrup);

                        // Adaugă VBox-ul cu activitățile într-un ScrollPane
                        ScrollPane scrollPane = new ScrollPane(vboxPrincipal);
                        scrollPane.setFitToWidth(true);

                        // Crează fereastra
                        Scene activitatiScene = new Scene(scrollPane, 1200, 600);
                        activitatiStage.setScene(activitatiScene);
                        activitatiStage.show();

                        // Populează tabelele cu date
                        populateActivitatiTables(resultSet, tableViewActivitatiInscris, tableViewActivitatiGrup);
                        populateActivitatiGrupTable(tableViewActivitatiGrup);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private TableView<ObservableList<String>> createActivitatiTableView() {
        // Creare tabel pentru afișarea datelor
        TableView<ObservableList<String>> tableView = new TableView<>();

        // Creare coloane pentru fiecare coloană din tabel
        TableColumn<ObservableList<String>, String> disciplinaColumn = new TableColumn<>("Disciplina");
        TableColumn<ObservableList<String>, String> tipActivitateColumn = new TableColumn<>("Tip Activitate");
        TableColumn<ObservableList<String>, String> dataInceputColumn = new TableColumn<>("Data Inceput");
        TableColumn<ObservableList<String>, String> oraInceputColumn = new TableColumn<>("Ora Inceput");
        TableColumn<ObservableList<String>, String> dataSfarsitColumn = new TableColumn<>("Data Sfarsit");
        TableColumn<ObservableList<String>, String> oraSfarsitColumn = new TableColumn<>("Ora Sfarsit");
        TableColumn<ObservableList<String>, String> numeProfesorColumn = new TableColumn<>("Nume Profesor");
        TableColumn<ObservableList<String>, String> prenumeProfesorColumn = new TableColumn<>("Prenume Profesor");

        disciplinaColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(0)));
        tipActivitateColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(1)));
        dataInceputColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(2)));
        oraInceputColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(3)));
        dataSfarsitColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(4)));
        oraSfarsitColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(5)));
        numeProfesorColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(6)));
        prenumeProfesorColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(7)));

        tableView.getColumns().addAll(
                disciplinaColumn,
                tipActivitateColumn,
                dataInceputColumn,
                oraInceputColumn,
                dataSfarsitColumn,
                oraSfarsitColumn,
                numeProfesorColumn,
                prenumeProfesorColumn
        );

        return tableView;
    }




    private TableView<ObservableList<String>> createActivitatiGrupTableView() {
        // Creare tabel pentru afișarea datelor pentru activitățile de grup
        TableView<ObservableList<String>> tableView = new TableView<>();

        // Creare coloane pentru fiecare coloană din tabel
        for (String columnName : Arrays.asList("Disciplina", "Nume Activitate", "Nume Grup", "Ora Inceput", "Ora Sfarsit", "Data", "Numar Minim Participanti", "Timp Expirare", "Nume Profesor", "Prenume Profesor")) {
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnName);
            column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(
                    tableView.getColumns().indexOf(column))));
            tableView.getColumns().add(column);
        }

        return tableView;
    }

    private void populateActivitatiTables(ResultSet resultSet, TableView<ObservableList<String>> tableViewActivitatiInscris, TableView<ObservableList<String>> tableViewActivitatiGrup) throws SQLException {

        tableViewActivitatiInscris.getItems().clear();


        while (resultSet.next()) {
            int idActivitate = resultSet.getInt("ID_Activitate");
            String numeDisciplina = resultSet.getString("Nume_Disciplina");
            String numeTipActivitate = resultSet.getString("Nume_Tip_Activitate");
            LocalDate dataInceput = resultSet.getDate("Data_Inceput").toLocalDate();
            LocalTime oraInceput = resultSet.getTime("Ora_Inceput").toLocalTime();
            LocalDate dataSfarsit = resultSet.getDate("Data_Sfarsit").toLocalDate();
            LocalTime oraSfarsit = resultSet.getTime("Ora_Sfarsit").toLocalTime();

            // Verifică dacă există nume și prenume de profesor în rezultatele interogării
            String numeProfesor = resultSet.getString("Nume_Profesor");
            String prenumeProfesor = resultSet.getString("Prenume_Profesor");

            // Verifică dacă numeProfesor și prenumeProfesor sunt nule și le setează la "-" în acest caz
            numeProfesor = (numeProfesor != null) ? numeProfesor : "-";
            prenumeProfesor = (prenumeProfesor != null) ? prenumeProfesor : "-";

            ObservableList<String> rowData = FXCollections.observableArrayList(
                    numeDisciplina,
                    numeTipActivitate,
                    dataInceput.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    oraInceput.format(DateTimeFormatter.ofPattern("HH:mm")),
                    dataSfarsit.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    oraSfarsit.format(DateTimeFormatter.ofPattern("HH:mm")),
                    numeProfesor,
                    prenumeProfesor
            );

            if (!tableViewActivitatiInscris.getItems().contains(rowData)) {
                tableViewActivitatiInscris.getItems().add(rowData);
            }
        }
    }




    private void populateActivitatiGrupTable(TableView<ObservableList<String>> tableViewActivitatiGrup) throws SQLException {
        conexiune = DatabaseConnection.getConnection();

        String sqlQuery = "SELECT AG.ID_Activitate_Grup, D.Nume_Disciplina, AG.Nume_Activitate, G.Nume_Grup, AG.Ora_Inceput, AG.Ora_Sfarsit, AG.Data, AG.Numar_Minim_Participanti, AG.Timp_Expirare, " +
                "U.Nume AS Nume_Profesor, U.Prenume AS Prenume_Profesor " +
                "FROM Activitati_Grup AG " +
                "JOIN Grupuri_Studiu G ON G.ID_Grup = AG.ID_Grup " +
                "JOIN Discipline D ON D.ID_Disciplina = G.ID_Disciplina " +
                "LEFT JOIN Profesori P ON P.ID_Profesor = AG.ID_Profesor " +
                "LEFT JOIN Utilizatori U ON U.ID_Utilizator = P.ID_Utilizator " +
                "LEFT JOIN Student_Activitate_Grup SAG ON SAG.ID_Student = ? AND SAG.ID_Activitate_Grup = AG.ID_Activitate_Grup " +
                "WHERE SAG.ID_Activitate_Grup IS NOT NULL";


        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, getIdStudent());
            ResultSet resultSet = preparedStatement.executeQuery();

            Set<Integer> processedActivities = new HashSet<>();

            while (resultSet.next()) {
                int idActivitate = resultSet.getInt("ID_Activitate_Grup");

                // Verifică dacă această activitate a fost deja procesată
                if (!processedActivities.contains(idActivitate)) {
                    String numeDisciplina = resultSet.getString("Nume_Disciplina");
                    String numeActivitate = resultSet.getString("Nume_Activitate");
                    String numeGrup = resultSet.getString("Nume_Grup");
                    String oraInceput = resultSet.getTime("Ora_Inceput").toString();
                    String oraSfarsit = resultSet.getTime("Ora_Sfarsit").toString();
                    LocalDate data = resultSet.getDate("Data").toLocalDate();
                    int numarMinimParticipanti = resultSet.getInt("Numar_Minim_Participanti");
                    LocalDateTime timpExpirare = resultSet.getTimestamp("Timp_Expirare").toLocalDateTime();
                    String numeProfesor = resultSet.getString("Nume_Profesor");
                    String prenumeProfesor = resultSet.getString("Prenume_Profesor");

                    ObservableList<String> rowData = FXCollections.observableArrayList(
                            numeDisciplina,
                            numeActivitate,
                            numeGrup,
                            oraInceput,
                            oraSfarsit,
                            data.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            String.valueOf(numarMinimParticipanti),
                            timpExpirare.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                            numeProfesor != null ? numeProfesor : "-",
                            prenumeProfesor != null ? prenumeProfesor : "-"
                    );

                    if (!rowData.contains("")) {
                        tableViewActivitatiGrup.getItems().add(rowData);
                    }

                    // Marchează activitatea ca procesată
                    processedActivities.add(idActivitate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea datelor despre activitățile de grup.");
        }
    }







///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void handleVizualizareDiscipline() {
        conexiune = DatabaseConnection.getConnection();

        try {
            String sqlQuery = "CALL AfisareDisciplineStudent(?)";

            try (PreparedStatement preparedStatement = conexiune.prepareStatement(sqlQuery)) {
                preparedStatement.setInt(1, idStudent);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet != null) {
                        Stage disciplineStage = new Stage();
                        disciplineStage.setTitle("Vizualizare Discipline");

                        // Creare tabel pentru afișarea datelor
                        TableView<ObservableList<String>> tableView = new TableView<>();

                        // Creare coloane pentru fiecare coloană din tabel
                        for (String columnName : Arrays.asList("Nume Disciplina", "Descriere", "Status")) {
                            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnName);
                            column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(
                                    tableView.getColumns().indexOf(column))));
                            tableView.getColumns().add(column);
                        }


                        // Adaugare date in tabel
                        while (resultSet.next()) {
                            ObservableList<String> rowData = FXCollections.observableArrayList(
                                    resultSet.getString("Nume_Disciplina"),
                                    resultSet.getString("Descriere"),
                                    resultSet.getString("Status")
                            );
                            tableView.getItems().add(rowData);
                        }


                        // Adaugă TableView într-un ScrollPane
                        ScrollPane scrollPane = new ScrollPane(tableView);
                        scrollPane.setFitToWidth(true);

                        // Creare scenă și afișare fereastră
                        Scene disciplineScene = new Scene(scrollPane, 900, 500);
                        disciplineStage.setScene(disciplineScene);
                        disciplineStage.show();
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }











    private void handleRenuntareDiscipline() {
        conexiune = DatabaseConnection.getConnection();

        // Creează un nou stage pentru selecția disciplinei la care să renunțe studentul
        Stage renuntareDisciplinaStage = new Stage();
        renuntareDisciplinaStage.setTitle("Renuntare Disciplina");

        // Creează elementele UI pentru selecția disciplinei
        Label labelDisciplina = new Label("Selectează Disciplina:");
        ComboBox<String> comboBoxDiscipline = new ComboBox<>();
        Button buttonRenuntareDisciplina = new Button("Renuntare Disciplina");

        // Populează ComboBox-ul cu numele disciplinelor la care studentul este înscris
        populeazaComboBoxDiscipline(comboBoxDiscipline);

        // Adaugă acțiune la apăsarea butonului "Renuntare Disciplina"
        buttonRenuntareDisciplina.setOnAction(e -> handleRenuntareDisciplinaFunction(
                comboBoxDiscipline.getValue(),
                renuntareDisciplinaStage
        ));

        // Adaugă elementele UI pe grilă
        GridPane gridRenuntareDisciplina = new GridPane();
        gridRenuntareDisciplina.setHgap(10);
        gridRenuntareDisciplina.setVgap(10);
        gridRenuntareDisciplina.setPadding(new Insets(25, 25, 25, 25));
        gridRenuntareDisciplina.add(labelDisciplina, 0, 0);
        gridRenuntareDisciplina.add(comboBoxDiscipline, 1, 0);
        gridRenuntareDisciplina.add(buttonRenuntareDisciplina, 0, 1);

        // Configurarea scenei și afișarea ferestrei
        Scene renuntareDisciplinaScene = new Scene(gridRenuntareDisciplina, 400, 150);
        renuntareDisciplinaStage.setScene(renuntareDisciplinaScene);
        renuntareDisciplinaStage.show();
    }

    private void handleRenuntareDisciplinaFunction(String numeDisciplina, Stage renuntareDisciplinaStage) {
        conexiune = DatabaseConnection.getConnection();

        // Obține ID-ul disciplinei
        int idDisciplina = getIdDisciplinaByNume(numeDisciplina);

        if (idDisciplina != -1) {
            // Apel procedură stocată pentru renunțarea la disciplină
            String sql = "CALL RenuntaStudentDisciplina(?, ?)";

            try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
                preparedStatement.setInt(1, getIdStudent());
                preparedStatement.setInt(2, idDisciplina);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String mesaj = resultSet.getString("Mesaj");
                    afiseazaSucces(mesaj);
                }

                // Închide fereastra de renunțare la disciplină
                renuntareDisciplinaStage.close();
            } catch (SQLException e) {
                e.printStackTrace();
                afiseazaEroare("Eroare la renunțarea la disciplină.");
            }
        } else {
            afiseazaEroare("ID-ul disciplinei nu este valid.");
        }
    }


    private void populeazaComboBoxDiscipline(ComboBox<String> comboBox) {
        conexiune = DatabaseConnection.getConnection();

        String sql = "SELECT D.Nume_Disciplina " +
                "FROM Inscriere_Student_Disciplina ISD " +
                "JOIN Discipline D ON ISD.ID_Disciplina = D.ID_Disciplina " +
                "WHERE ISD.ID_Student = ?";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setInt(1, getIdStudent());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String numeDisciplina = resultSet.getString("Nume_Disciplina");
                comboBox.getItems().add(numeDisciplina);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea datelor despre disciplinele la care este înscris studentul.");
        }
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









///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




    private void handleInscriereDiscipline() {
        System.out.println("Buton - Înscriere Discipline");

        // Creează un nou stage pentru înscrierea la discipline
        Stage inscriereDisciplineStage = new Stage();
        inscriereDisciplineStage.setTitle("Înscriere Discipline");

        // Creează elementele UI pentru înscrierea la discipline
        Label labelDisciplina = new Label("Selectează Disciplina:");
        ComboBox<String> comboBoxDiscipline = new ComboBox<>();

        // Adăugăm un text în fața combo box-ului pentru profesori
        Label labelProfesori = new Label("Selectează Profesorul:");
        ComboBox<String> comboBoxProfesori = new ComboBox<>();

        Button buttonInscriereDisciplina = new Button("Înscrie");

        // Populează ComboBox-ul cu ID-urile disciplinelor la care studentul nu este încă înscris
        populeazaComboBoxDisciplineDisponibile(comboBoxDiscipline);

        comboBoxDiscipline.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Populează ComboBox-ul pentru profesori cu profesorii disponibili pentru disciplina selectată
            comboBoxProfesori.getItems().clear(); // Curăță lista existentă
            populeazaComboBoxProfesoriDisponibili(newValue, comboBoxProfesori);
        });


        // Adaugă acțiune la apăsarea butonului Înscrie Disciplina
        buttonInscriereDisciplina.setOnAction(e -> handleInscriereDisciplineFunction(
                comboBoxDiscipline.getValue(),
                comboBoxProfesori.getValue(),  // Adăugăm și profesorul selectat
                inscriereDisciplineStage
        ));

        // Adaugă elementele UI pe grilă
        GridPane gridInscriereDiscipline = new GridPane();
        gridInscriereDiscipline.setHgap(10);
        gridInscriereDiscipline.setVgap(10);
        gridInscriereDiscipline.setPadding(new Insets(25, 25, 25, 25));

        // Adăugăm elementele pe grid
        gridInscriereDiscipline.add(labelDisciplina, 0, 0);
        gridInscriereDiscipline.add(comboBoxDiscipline, 1, 0);

        // Adăugăm eticheta și combo box-ul pentru profesori pe un rând nou
        gridInscriereDiscipline.add(labelProfesori, 0, 1);
        gridInscriereDiscipline.add(comboBoxProfesori, 1, 1);

        // Adăugăm butonul de înscriere pe al treilea rând
        gridInscriereDiscipline.add(buttonInscriereDisciplina, 0, 2);

        // Configurarea scenei și afișarea ferestrei
        Scene inscriereDisciplineScene = new Scene(gridInscriereDiscipline, 500, 200);
        inscriereDisciplineStage.setScene(inscriereDisciplineScene);
        inscriereDisciplineStage.show();
    }


    private void populeazaComboBoxDisciplineDisponibile(ComboBox<String> comboBox) {
        conexiune = DatabaseConnection.getConnection();

        // Obține ID-urile disciplinelor la care studentul nu este încă înscris și le adaugă în ComboBox
        String sql = "SELECT D.Nume_Disciplina " +
                "FROM Discipline D " +
                "WHERE D.ID_Disciplina NOT IN (SELECT ID_Disciplina FROM Inscriere_Student_Disciplina WHERE ID_Student = ?)";


        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setInt(1, idStudent);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String numeDiscipline = resultSet.getString("Nume_Disciplina");

                String info = String.format("%s", numeDiscipline);

                comboBox.getItems().add(info);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea datelor despre discipline disponibile.");
        }
    }

    private void populeazaComboBoxProfesoriDisponibili(String numeDisciplina, ComboBox<String> comboBox) {
        conexiune = DatabaseConnection.getConnection();

        // Obține ID-ul disciplinei
        int idDisciplina = getIdDisciplinaByNume(numeDisciplina);



        // Obține profesorii care predau disciplina selectată
        String sql = "SELECT U.Nume, U.Prenume " +
                "FROM Profesori P " +
                "JOIN Asociere_Disciplina_Profesor ADP ON P.ID_Profesor = ADP.ID_Profesor " +
                "JOIN Utilizatori U ON P.ID_Utilizator = U.ID_Utilizator " +
                "WHERE ADP.ID_Disciplina = ?";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setInt(1, idDisciplina);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String numeProfesor = resultSet.getString("Nume");
                String prenumeProfesor = resultSet.getString("Prenume");

                String infoProfesor = String.format("%s %s", numeProfesor, prenumeProfesor);

                comboBox.getItems().add(infoProfesor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea datelor despre profesorii disponibili.");
        }
    }

    private void handleInscriereDisciplineFunction(String numeDisciplina, String numeProfesor, Stage inscriereDisciplineStage) {
        conexiune = DatabaseConnection.getConnection();

        // Obține ID-urile disciplinei și profesorului
        int idDisciplina = getIdDisciplinaByNume(numeDisciplina);
        int idProfesor = getIdProfesorByNume(numeProfesor);

        if (idDisciplina != -1 && idProfesor != -1) {
            // Apel procedură stocată pentru înscrierea la disciplină
            String sql = "CALL InscriereStudentDisciplinaProfesor(?, ?, ?)";

            try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
                preparedStatement.setInt(1, getIdStudent());
                preparedStatement.setInt(2, idDisciplina);
                preparedStatement.setInt(3, idProfesor);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String mesaj = resultSet.getString("Mesaj");
                    afiseazaSucces(mesaj);
                }

                // Închide fereastra de înscriere la disciplină
                inscriereDisciplineStage.close();
            } catch (SQLException e) {
                e.printStackTrace();
                afiseazaEroare("Eroare la înscrierea la disciplină.");
            }
        } else {
            afiseazaEroare("ID-urile disciplinei și/sau profesorului nu sunt valide.");
        }
    }

    private int getIdProfesorByNume(String numeProfesor) {
        conexiune = DatabaseConnection.getConnection();
        String sql = "SELECT P.ID_Profesor " +
                "FROM Profesori P " +
                "JOIN Utilizatori U ON P.ID_Utilizator = U.ID_Utilizator " +
                "WHERE CONCAT(U.Nume, ' ', U.Prenume) = ?";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setString(1, numeProfesor);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("ID_Profesor");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea ID-ului profesorului.");
        }

        return -1;
    }



    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////









    private void handleVizualizareGrupuri() {
        System.out.println("Buton - Vizualizare Grupuri Studiu");

        // Creează un nou stage pentru vizualizarea grupurilor de studiu
        Stage vizualizareGrupuriStage = new Stage();
        vizualizareGrupuriStage.setTitle("Vizualizare Grupuri Studiu");

        // Creează un TableView pentru afișarea datelor
        TableView<ObservableList<String>> tableView = new TableView<>();

        // Creare coloane pentru fiecare coloană din tabel
        for (String columnName : Arrays.asList("Nume Grup", "Descriere")) {
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnName);
            column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(
                    tableView.getColumns().indexOf(column))));
            tableView.getColumns().add(column);
        }

        // Populează TableView cu datele despre grupuri
        populeazaTabelGrupuri(tableView);

        // Adaugă TableView într-un StackPane
        StackPane stackPane = new StackPane(tableView);

        // Adaugă StackPane în fereastra principală
        Scene vizualizareGrupuriScene = new Scene(stackPane, 500, 400);
        vizualizareGrupuriStage.setScene(vizualizareGrupuriScene);
        vizualizareGrupuriStage.show();
    }

    private void populeazaTabelGrupuri(TableView<ObservableList<String>> tableView) {
        conexiune = DatabaseConnection.getConnection();

        // Obține informațiile despre grupurile de studiu și le adaugă în TableView
        String sql = "SELECT Nume_Grup, Descriere " +
                "FROM Grupuri_Studiu G ";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ObservableList<String> rowData = FXCollections.observableArrayList(
                        resultSet.getString("Nume_Grup"),
                        resultSet.getString("Descriere")
                );
                tableView.getItems().add(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea datelor despre grupurile de studiu.");
        }
    }





////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void handleVizualizareMembriGrup() {
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

        // Creează un ComboBox pentru a alege grupul
        ComboBox<String> comboBoxGrupuri = new ComboBox<>();

        // Adaugă acțiune la selectarea unui grup din ComboBox
        comboBoxGrupuri.setOnAction(e -> handleAfisareMembriGrup(
                comboBoxGrupuri.getValue(),
                tableViewMembri,
                tableViewSugestii
        ));

        // Populează ComboBox-ul cu numele grupurilor
        populeazaComboBoxGrupuriStudiuInscrise(comboBoxGrupuri);

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


        // Configurarea scenei și afișarea ferestrei
        Scene vizualizareMembriGrupScene = new Scene(vbox, 600, 400);
        vizualizareMembriGrupStage.setScene(vizualizareMembriGrupScene);
        vizualizareMembriGrupStage.show();
    }

    private void configureTableView(TableView<ObservableList<String>> tableView, List<String> columnNames) {
        // Creare coloane pentru fiecare coloană din tabel
        for (String columnName : columnNames) {
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnName);
            column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(
                    tableView.getColumns().indexOf(column))));
            tableView.getColumns().add(column);
        }
    }

    private void handleAfisareMembriGrup(String numeGrup, TableView<ObservableList<String>> tableViewMembri, TableView<ObservableList<String>> tableViewSugestii) {
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
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea datelor despre membrii grupului.");
        }
    }


    private void populeazaComboBoxGrupuri(ComboBox<String> comboBox) {
        // Accesează baza de date pentru a obține numele grupurilor disponibile și le adaugă în comboBox.
        // Exemplu: comboBox.getItems().addAll("Grup1", "Grup2", "Grup3");
        conexiune = DatabaseConnection.getConnection();

        String sql = "SELECT Nume_Grup FROM Grupuri_Studiu";
        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String numeGrup = resultSet.getString("Nume_Grup");
                comboBox.getItems().add(numeGrup);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea datelor despre grupuri de studiu.");
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




    private void handleInscriereGrupStudiu() {
        conexiune = DatabaseConnection.getConnection();

        // Creează un nou stage pentru înscrierea în grupul de studiu
        Stage inscriereGrupStudiuStage = new Stage();
        inscriereGrupStudiuStage.setTitle("Înscriere în Grupul de Studiu");

        // Creează elementele UI pentru înscrierea în grupul de studiu
        Label labelGrupStudiu = new Label("Selectează Grupul de Studiu:");
        ComboBox<String> comboBoxGrupuriStudiu = new ComboBox<>();
        Button buttonInscriereGrupStudiu = new Button("Înscrie în Grupul de Studiu");

        // Populează ComboBox-ul cu numele grupurilor de studiu la care studentul nu este încă înscris
        populeazaComboBoxGrupuriStudiuDisponibile(comboBoxGrupuriStudiu);

        // Adaugă elementele UI pe grilă
        GridPane gridInscriereGrupStudiu = new GridPane();
        gridInscriereGrupStudiu.setHgap(10);
        gridInscriereGrupStudiu.setVgap(10);
        gridInscriereGrupStudiu.setPadding(new Insets(25, 25, 25, 25));
        gridInscriereGrupStudiu.add(labelGrupStudiu, 0, 0);
        gridInscriereGrupStudiu.add(comboBoxGrupuriStudiu, 1, 0);
        gridInscriereGrupStudiu.add(buttonInscriereGrupStudiu, 0, 1);

        // Adaugă acțiunea la apăsarea butonului Înscrie în Grupul de Studiu
        buttonInscriereGrupStudiu.setOnAction(e -> handleInscriereGrupStudiuFunction(
                comboBoxGrupuriStudiu.getValue(),
                inscriereGrupStudiuStage
        ));

        // Configurarea scenei și afișarea ferestrei
        Scene inscriereGrupStudiuScene = new Scene(gridInscriereGrupStudiu, 400, 150);
        inscriereGrupStudiuStage.setScene(inscriereGrupStudiuScene);
        inscriereGrupStudiuStage.show();
    }

    private void populeazaComboBoxGrupuriStudiuDisponibile(ComboBox<String> comboBox) {
        conexiune = DatabaseConnection.getConnection();

        // Obține informațiile despre grupurile de studiu la care studentul nu este încă înscris și le adaugă în ComboBox
        String sql = "SELECT GS.Nume_Grup " +
                "FROM Grupuri_Studiu GS " +
                "WHERE GS.ID_Grup NOT IN (SELECT ID_Grup FROM Membri_Grup WHERE ID_Student = ?)";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setInt(1, idStudent);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String numeGrupStudiu = resultSet.getString("Nume_Grup");
                comboBox.getItems().add(numeGrupStudiu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea datelor despre grupurile de studiu disponibile.");
        }
    }






    private void handleInscriereGrupStudiuFunction(String numeGrupStudiu, Stage inscriereGrupStudiuStage) {
        conexiune = DatabaseConnection.getConnection();

        int idGrupStudiu = getIdGrupByNume(numeGrupStudiu);

        // Apel procedură stocată pentru înscriere în grupul de studiu
        String sql = "{call InscriereStudentGrupStudiu(?, ?)}";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setInt(1, idStudent);
            preparedStatement.setInt(2, idGrupStudiu);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String mesaj = resultSet.getString("Mesaj");
                afiseazaSucces(mesaj);
            }

            // Închide fereastra de înscriere în grupul de studiu
            inscriereGrupStudiuStage.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            afiseazaEroare("Eroare la înscriere în grupul de studiu.");
        }
    }






///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    private void handleTrimitereMesaj() {
        System.out.println("Buton - Trimitere Mesaj");

        // Creează un nou stage pentru trimiterea mesajului
        Stage trimitereMesajStage = new Stage();
        trimitereMesajStage.setTitle("Trimitere Mesaj");

        // Creează elementele UI pentru trimiterea mesajului
        Label labelGrup = new Label("Selectează Grupul:");
        ComboBox<String> comboBoxGrupuri = new ComboBox<>();
        TextArea textAreaMesaj = new TextArea();
        Button buttonTrimiteMesaj = new Button("Trimite Mesaj");

        // Populează ComboBox-ul cu numele grupurilor
        populeazaComboBoxGrupuriMesaje(comboBoxGrupuri);

        // Adaugă acțiune la apăsarea butonului "Trimite Mesaj"
        buttonTrimiteMesaj.setOnAction(e -> handleTrimitereMesajFunction(
                comboBoxGrupuri.getValue(),
                textAreaMesaj.getText(),
                trimitereMesajStage
        ));

        // Adaugă elementele UI pe grilă
        GridPane gridTrimitereMesaj = new GridPane();
        gridTrimitereMesaj.setHgap(10);
        gridTrimitereMesaj.setVgap(10);
        gridTrimitereMesaj.setPadding(new Insets(25, 25, 25, 25));
        gridTrimitereMesaj.add(labelGrup, 0, 0);
        gridTrimitereMesaj.add(comboBoxGrupuri, 1, 0);
        gridTrimitereMesaj.add(textAreaMesaj, 0, 1, 2, 1);
        gridTrimitereMesaj.add(buttonTrimiteMesaj, 0, 2);

        // Configurarea scenei și afișarea ferestrei
        Scene trimitereMesajScene = new Scene(gridTrimitereMesaj, 400, 300);
        trimitereMesajStage.setScene(trimitereMesajScene);
        trimitereMesajStage.show();
    }


    private void populeazaComboBoxGrupuriMesaje(ComboBox<String> comboBox) {
        // Accesează baza de date pentru a obține numele grupurilor disponibile și le adaugă în comboBox.
        // Exemplu: comboBox.getItems().addAll("Grup1", "Grup2", "Grup3");
        conexiune = DatabaseConnection.getConnection();

        String sql = "SELECT GS.ID_Grup, GS.Nume_Grup, GS.Descriere " +
                "FROM Grupuri_Studiu GS " +
                "JOIN Membri_Grup MG ON GS.ID_Grup = MG.ID_Grup " +
                "WHERE MG.ID_Student = ?";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setInt(1, getIdStudent());  // Modificare aici pentru a obține ID-ul utilizatorului curent
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idGrup = resultSet.getInt("ID_Grup");
                String numeGrup = resultSet.getString("Nume_Grup");
                String descriere = resultSet.getString("Descriere");

                comboBox.getItems().add(numeGrup);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea datelor despre grupurile de studiu.");
        }
    }


    private void handleTrimitereMesajFunction(String numeGrup, String continutMesaj, Stage trimitereMesajStage) {
        // Accesează baza de date pentru a trimite mesajul către grup
        conexiune = DatabaseConnection.getConnection();

        // Obține ID-ul grupului
        int idGrup = getIdGrupByNume(numeGrup);

        // Verifică dacă ID-ul grupului este valid
        if (idGrup != -1) {
            // Verifică dacă studentul este membru al grupului
            if (isStudentInGrup(idGrup)) {
                // Inserează mesajul în baza de date
                boolean rezultatTrimitere = trimiteMesajGrup(idGrup, continutMesaj);

                // Afișează rezultatul trimiterii
                if (rezultatTrimitere) {
                    afiseazaSucces("Mesajul a fost trimis cu succes la grupul " + numeGrup);
                } else {
                    afiseazaEroare("Eroare la trimiterea mesajului la grupul " + numeGrup);
                }

                // Închide fereastra de trimitere mesaj
                trimitereMesajStage.close();
            } else {
                afiseazaEroare("Studentul nu este membru al grupului " + numeGrup);
            }
        } else {
            afiseazaEroare("ID-ul grupului nu este valid.");
        }
    }

    // Metoda pentru a verifica dacă studentul este membru al unui grup
    private boolean isStudentInGrup(int idGrup) {
        boolean isStudentInGrup = false;

        String sql = "SELECT COUNT(*) AS NumarMembri FROM Membri_Grup WHERE ID_Grup = ? AND ID_Student = ?";
        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setInt(1, idGrup);
            preparedStatement.setInt(2, getIdStudent());  // Modificare aici pentru a obține ID-ul utilizatorului curent

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int numarMembri = resultSet.getInt("NumarMembri");
                isStudentInGrup = numarMembri > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la verificarea apartenenței studentului la grup.");
        }

        return isStudentInGrup;
    }


    // Metoda pentru a obține ID-ul grupului după nume
    private int getIdGrupByNume(String numeGrup) {
        int idGrup = -1;

        String sql = "SELECT ID_Grup FROM Grupuri_Studiu WHERE Nume_Grup = ?";
        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setString(1, numeGrup);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                idGrup = resultSet.getInt("ID_Grup");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea ID-ului grupului.");
        }

        return idGrup;
    }

    // Metoda pentru a trimite mesajul către grup în baza de date
    private boolean trimiteMesajGrup(int idGrup, String continutMesaj) {
        boolean rezultatTrimitere = false;

        String sql = "INSERT INTO Mesaje_Grup (ID_Grup, ID_Student, Continut_Mesaj) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setInt(1, idGrup);
            preparedStatement.setInt(2, getIdStudent());  // Modificare aici pentru a obține ID-ul utilizatorului curent
            preparedStatement.setString(3, continutMesaj);

            int rowsAffected = preparedStatement.executeUpdate();
            rezultatTrimitere = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la trimiterea mesajului către grup.");
        }

        return rezultatTrimitere;

    }










    private void handleIesireGrup() {
        conexiune = DatabaseConnection.getConnection();

        // Creează un nou stage pentru ieșirea din grupul de studiu
        Stage iesireGrupStage = new Stage();
        iesireGrupStage.setTitle("Ieșire din Grupul de Studiu");

        // Creează elementele UI pentru ieșirea din grupul de studiu
        Label labelGrupStudiu = new Label("Selectează Grupul de Studiu:");
        ComboBox<String> comboBoxGrupuriStudiu = new ComboBox<>();
        Button buttonIesireGrup = new Button("Ieșire din Grupul de Studiu");

        // Populează ComboBox-ul cu numele grupurilor de studiu la care studentul este înscris
        populeazaComboBoxGrupuriStudiuInscrise(comboBoxGrupuriStudiu);

        // Adaugă acțiunea la apăsarea butonului Ieșire din Grupul de Studiu
        buttonIesireGrup.setOnAction(e -> handleIesireGrupFunction(
                comboBoxGrupuriStudiu.getValue(),
                iesireGrupStage
        ));

        // Adaugă elementele UI pe grilă
        GridPane gridIesireGrup = new GridPane();
        gridIesireGrup.setHgap(10);
        gridIesireGrup.setVgap(10);
        gridIesireGrup.setPadding(new Insets(25, 25, 25, 25));
        gridIesireGrup.add(labelGrupStudiu, 0, 0);
        gridIesireGrup.add(comboBoxGrupuriStudiu, 1, 0);
        gridIesireGrup.add(buttonIesireGrup, 0, 1);

        // Configurarea scenei și afișarea ferestrei
        Scene iesireGrupScene = new Scene(gridIesireGrup, 400, 150);
        iesireGrupStage.setScene(iesireGrupScene);
        iesireGrupStage.show();
    }

    private void populeazaComboBoxGrupuriStudiuInscrise(ComboBox<String> comboBox) {
        conexiune = DatabaseConnection.getConnection();

        // Obține informațiile despre grupurile de studiu la care studentul este înscris și le adaugă în ComboBox
        String sql = "SELECT GS.Nume_Grup " +
                "FROM Grupuri_Studiu GS " +
                "JOIN Membri_Grup MG ON GS.ID_Grup = MG.ID_Grup " +
                "WHERE MG.ID_Student = ?";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setInt(1, idStudent);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String numeGrupStudiu = resultSet.getString("Nume_Grup");
                comboBox.getItems().add(numeGrupStudiu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea datelor despre grupurile de studiu la care este înscris studentul.");
        }
    }

    private void handleIesireGrupFunction(String numeGrupStudiu, Stage iesireGrupStage) {
        conexiune = DatabaseConnection.getConnection();

        int idGrupStudiuSelectat = getIdGrupByNume(numeGrupStudiu);

        // Apel procedură stocată pentru ieșirea din grupul de studiu
        String sql = "{call RenuntaStudentGrupStudiu(?, ?)}";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setInt(1, idStudent);
            preparedStatement.setInt(2, idGrupStudiuSelectat);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String mesaj = resultSet.getString("Mesaj");
                afiseazaSucces(mesaj);
            }

            // Închide fereastra de ieșire din grupul de studiu
            iesireGrupStage.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            afiseazaEroare("Eroare la ieșire din grupul de studiu.");
        }
    }




//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void handleProgrameazaActivitateGrup() {
        conexiune = DatabaseConnection.getConnection();

        // Creează un nou stage pentru programarea activității de grup
        Stage programareActivitateGrupStage = new Stage();
        programareActivitateGrupStage.setTitle("Programare Activitate Grup");

        // Creează elementele UI pentru programarea activității de grup
        Label labelGrup = new Label("Selectează Grupul:");
        ComboBox<String> comboBoxGrupuri = new ComboBox<>();
        Label labelNumeActivitate = new Label("Nume Activitate:");
        TextField textFieldNumeActivitate = new TextField();
        Label labelOraInceput = new Label("Ora Început:");
        ComboBox<String> comboBoxOraInceput = new ComboBox<>();
        Label labelOraSfarsit = new Label("Ora Sfârșit:");
        ComboBox<String> comboBoxOraSfarsit = new ComboBox<>();
        Label labelData = new Label("Data (YYYY-MM-DD):");
        DatePicker datePickerData = new DatePicker();
        Label labelNumarMinimParticipanti = new Label("Număr Minim Participanți:");
        TextField textFieldNumarMinimParticipanti = new TextField();
        Label labelTimpExpirare = new Label("Timp Expirare (YYYY-MM-DD HH:MM):");
        TextField textFieldTimpExpirare = new TextField();
        Label labelProfesor = new Label("Selectează Profesorul:");
        ComboBox<String> comboBoxProfesori = new ComboBox<>(); // Adăugat ComboBox pentru profesori
        Button buttonProgramareActivitateGrup = new Button("Programează Activitate Grup");

        // Populează ComboBox-urile cu date
        populeazaComboBoxGrupuriStudiuInscrise(comboBoxGrupuri);
        populeazaComboBoxOre(comboBoxOraInceput);
        populeazaComboBoxOre(comboBoxOraSfarsit);
        populeazaComboBoxProfesori(comboBoxProfesori); // Populează ComboBox-ul pentru profesori

        // Adaugă acțiunea la apăsarea butonului Programează Activitate Grup
        buttonProgramareActivitateGrup.setOnAction(e -> handleProgrameazaActivitateGrupFunction(
                comboBoxGrupuri.getValue(),
                textFieldNumeActivitate.getText(),
                comboBoxOraInceput.getValue(),
                comboBoxOraSfarsit.getValue(),
                datePickerData.getValue(),
                textFieldNumarMinimParticipanti.getText(),
                textFieldTimpExpirare.getText(),
                comboBoxProfesori.getValue(), // Adăugat pentru a prelua profesorul selectat
                programareActivitateGrupStage
        ));

        // Adaugă elementele UI pe grilă
        GridPane gridProgramareActivitateGrup = new GridPane();
        gridProgramareActivitateGrup.setHgap(10);
        gridProgramareActivitateGrup.setVgap(10);
        gridProgramareActivitateGrup.setPadding(new Insets(25, 25, 25, 25));
        gridProgramareActivitateGrup.add(labelGrup, 0, 0);
        gridProgramareActivitateGrup.add(comboBoxGrupuri, 1, 0);
        gridProgramareActivitateGrup.add(labelNumeActivitate, 0, 1);
        gridProgramareActivitateGrup.add(textFieldNumeActivitate, 1, 1);
        gridProgramareActivitateGrup.add(labelOraInceput, 0, 2);
        gridProgramareActivitateGrup.add(comboBoxOraInceput, 1, 2);
        gridProgramareActivitateGrup.add(labelOraSfarsit, 0, 3);
        gridProgramareActivitateGrup.add(comboBoxOraSfarsit, 1, 3);
        gridProgramareActivitateGrup.add(labelData, 0, 4);
        gridProgramareActivitateGrup.add(datePickerData, 1, 4);
        gridProgramareActivitateGrup.add(labelNumarMinimParticipanti, 0, 5);
        gridProgramareActivitateGrup.add(textFieldNumarMinimParticipanti, 1, 5);
        gridProgramareActivitateGrup.add(labelTimpExpirare, 0, 6);
        gridProgramareActivitateGrup.add(textFieldTimpExpirare, 1, 6);
        gridProgramareActivitateGrup.add(labelProfesor, 0, 7);
        gridProgramareActivitateGrup.add(comboBoxProfesori, 1, 7); // Adăugat ComboBox pentru profesori
        gridProgramareActivitateGrup.add(buttonProgramareActivitateGrup, 0, 8);

        // Configurarea scenei și afișarea ferestrei
        Scene programareActivitateGrupScene = new Scene(gridProgramareActivitateGrup, 700, 400);
        programareActivitateGrupStage.setScene(programareActivitateGrupScene);
        programareActivitateGrupStage.show();
    }


    private void populeazaComboBoxOre(ComboBox<String> comboBoxOre) {
        // Lista cu orele posibile între 8 dimineața și 8 seara, cu incrementare de jumătate de oră
        List<String> orePosibile = new ArrayList<>();
        for (int ora = 8; ora <= 20; ora++) {
            for (int minut = 0; minut <= 30; minut += 30) {
                String oraString = String.format("%02d:%02d", ora, minut);
                orePosibile.add(oraString);
            }
        }

        // Adaugă lista de ore la ComboBox
        comboBoxOre.getItems().addAll(orePosibile);
    }



    private void populeazaComboBoxProfesori(ComboBox<String> comboBox) {
        // Conectare la baza de date și obținere profesori
        conexiune = DatabaseConnection.getConnection();

        String sql = "SELECT Nume, Prenume, Departament FROM Profesori P "+
                     "JOIN Utilizatori U ON U.ID_Utilizator = P.ID_Utilizator ";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String nume = resultSet.getString("Nume");
                String prenume = resultSet.getString("Prenume");
                String departament = resultSet.getString("Departament");
                String profesor = nume + " " + prenume + " - " + departament;
                comboBox.getItems().add(profesor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea datelor despre profesori.");
        }
    }


    private void handleProgrameazaActivitateGrupFunction(String grupSelectat, String numeActivitate, String oraInceput, String oraSfarsit, LocalDate data,
                                                        String numarMinimParticipanti, String timpExpirare, String profesorSelectat, Stage programareActivitateGrupStage) {
        conexiune = DatabaseConnection.getConnection();

        // Extrage ID-ul grupului din șirul selectat
        Integer idGrupSelectat = obtineIdGrupDupaNume(grupSelectat);

        if (idGrupSelectat != null) {
            // Obține ID-ul profesorului folosind numele și prenumele (dacă este selectat un profesor)
            Integer idProfesorSelectat = null;
            if (profesorSelectat != null && !profesorSelectat.isEmpty()) {
                idProfesorSelectat = obtineIdProfesorDupaNumePrenume(profesorSelectat);
            }

            // Apel procedură stocată pentru programarea activității de grup
            String sql = "{call ProgramareActivitateGrup(?, ?, ?, ?, ?, ?, ?, ?, ?)}";

            try (CallableStatement callableStatement = conexiune.prepareCall(sql)) {
                // Setează celelalte parametri
                callableStatement.setInt(1, idGrupSelectat);
                callableStatement.setString(2, numeActivitate);
                callableStatement.setString(3, oraInceput);
                callableStatement.setString(4, oraSfarsit);
                callableStatement.setDate(5, Date.valueOf(data));
                callableStatement.setInt(6, Integer.parseInt(numarMinimParticipanti));

                // Adaugă secundele la timpul de expirare și apoi creează obiectul Timestamp
                LocalDateTime timpExpirareDateTime = LocalDateTime.parse(timpExpirare, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                Timestamp timpExpirareTimestamp = Timestamp.valueOf(timpExpirareDateTime);
                callableStatement.setTimestamp(7, timpExpirareTimestamp);

                // Setează ID_Profesor sau NULL în funcție de selecția utilizatorului
                if (idProfesorSelectat != null) {
                    callableStatement.setInt(8, idProfesorSelectat);
                } else {
                    callableStatement.setNull(8, java.sql.Types.INTEGER);
                }

                // Înregistrează parametrul OUT pentru rezultatul programării
                callableStatement.registerOutParameter(9, java.sql.Types.INTEGER);

                // Execută procedura stocată
                callableStatement.execute();

                // Returnează rezultatul
                int result = callableStatement.getInt(9);

                afiseazaRezultatProgramare(result);
            } catch (SQLException ex) {
                ex.printStackTrace();
                afiseazaEroare("Eroare la programarea activității de grup.");
            }
        } else {
            afiseazaEroare("Eroare la extragerea ID-ului grupului.");
        }

        // În caz de eroare, returnează o valoare negativă
    }



    private void afiseazaRezultatProgramare(int resultCode) {



        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Rezultat Programare Activitate");
        alert.setHeaderText(null);

        switch (resultCode) {
            case 0:
                alert.setContentText("Programare activitate reușită.");
                break;
            case 1:
                alert.setContentText("Nu sunt suficienți membri în grup.");
                break;
            case 2:
                alert.setContentText("Există deja o activitate programată în același interval orar.");
                break;
            case 3:
                alert.setContentText("Profesorul nu este valid.");
                break;
            default:
                alert.setContentText("Eroare necunoscută.");
                break;
        }

        alert.showAndWait();
    }

    // Adaugă metoda pentru obținerea ID-ului profesorului după nume și prenume
    private Integer obtineIdProfesorDupaNumePrenume(String profesorSelectat) {
        if (profesorSelectat != null && !profesorSelectat.isEmpty()) {
            String[] numePrenume = profesorSelectat.split("\\s");
            if (numePrenume.length >= 2) {
                String numeProfesor = numePrenume[0];
                String prenumeProfesor = numePrenume[1];
                return obtineIdProfesorDupaNumePrenume(numeProfesor, prenumeProfesor);
            }
        }
        return null;
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


    private Integer obtineIdGrupDupaNume(String numeGrup) {
        conexiune = DatabaseConnection.getConnection();
        String sql = "SELECT ID_Grup FROM Grupuri_Studiu WHERE Nume_Grup = ?";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setString(1, numeGrup);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("ID_Grup");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea ID-ului grupului.");
        }

        return null;
    }












    private void handleInscriereActivitateGrup() {
        conexiune = DatabaseConnection.getConnection();

        // Creează un nou stage pentru înregistrarea la activitatea de grup
        Stage inscriereActivitateGrupStage = new Stage();
        inscriereActivitateGrupStage.setTitle("Înscriere Activitate Grup");

        // Creează elementele UI pentru înregistrarea la activitatea de grup
        Label labelActivitate = new Label("Selectează Activitatea de Grup:");
        ComboBox<String> comboBoxActivitatiGrup = new ComboBox<>();
        Button buttonInscriereActivitateGrup = new Button("Înscrie-te la Activitatea de Grup");

        // Populează ComboBox-ul cu numele activităților de grup
        populeazaComboBoxActivitatiGrup(comboBoxActivitatiGrup);

        // Adaugă acțiunea la apăsarea butonului Înscrie-te la Activitatea de Grup
        buttonInscriereActivitateGrup.setOnAction(e -> handleInscriereActivitateGrupFunction(
                comboBoxActivitatiGrup.getValue(),
                inscriereActivitateGrupStage
        ));

        // Adaugă elementele UI pe grilă
        GridPane gridInscriereActivitateGrup = new GridPane();
        gridInscriereActivitateGrup.setHgap(10);
        gridInscriereActivitateGrup.setVgap(10);
        gridInscriereActivitateGrup.setPadding(new Insets(25, 25, 25, 25));
        gridInscriereActivitateGrup.add(labelActivitate, 0, 0);
        gridInscriereActivitateGrup.add(comboBoxActivitatiGrup, 1, 0);
        gridInscriereActivitateGrup.add(buttonInscriereActivitateGrup, 0, 1);

        // Configurarea scenei și afișarea ferestrei
        Scene inscriereActivitateGrupScene = new Scene(gridInscriereActivitateGrup, 400, 200);
        inscriereActivitateGrupStage.setScene(inscriereActivitateGrupScene);
        inscriereActivitateGrupStage.show();
    }

    private void handleInscriereActivitateGrupFunction(String activitateGrupSelectata, Stage inscriereActivitateGrupStage) {
        conexiune = DatabaseConnection.getConnection();

        // Extrage ID-ul activității de grup din șirul selectat
        Integer idActivitateGrupSelectata = obtineIdActivitateGrupDupaNume(activitateGrupSelectata);

        if (idActivitateGrupSelectata != null) {
            // Apel procedură stocată pentru înregistrarea la activitatea de grup
            String sql = "{call InscriereStudentActivitateGrup(?, ?)}";

            try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
                preparedStatement.setInt(1, getIdStudent());  // presupunând că idStudent este deja definit
                preparedStatement.setInt(2, idActivitateGrupSelectata);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String mesaj = resultSet.getString("Mesaj");
                    afiseazaSucces(mesaj);
                }

                // Închide fereastra de înregistrare la activitatea de grup
                inscriereActivitateGrupStage.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                afiseazaEroare("Eroare la înregistrarea la activitatea de grup.");
            }
        } else {
            afiseazaEroare("Eroare la extragerea ID-ului activității de grup.");
        }
    }

    private Integer obtineIdActivitateGrupDupaNume(String numeActivitateGrup) {
        conexiune = DatabaseConnection.getConnection();
        String sql = "SELECT ID_Activitate_Grup FROM Activitati_Grup WHERE Nume_Activitate = ?";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setString(1, numeActivitateGrup);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("ID_Activitate_Grup");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea ID-ului activității de grup.");
        }

        return null;
    }


    private void populeazaComboBoxActivitatiGrup(ComboBox<String> comboBox) {
        conexiune = DatabaseConnection.getConnection();

        // Obține informațiile despre activitățile de grup din grupurile la care studentul curent este membru
        String sql = "SELECT AG.Nume_Activitate " +
                "FROM Activitati_Grup AG " +
                "JOIN Membri_Grup MG ON AG.ID_Grup = MG.ID_Grup " +
                "WHERE MG.ID_Student = ?";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setInt(1, getIdStudent());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String numeActivitateGrup = resultSet.getString("Nume_Activitate");
                comboBox.getItems().add(numeActivitateGrup);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea datelor despre activitățile de grup.");
        }
    }

















    private void handleRenuntareActivitateGrup() {
        conexiune = DatabaseConnection.getConnection();

        // Creează un nou stage pentru renunțarea la activitatea de grup
        Stage renuntareActivitateGrupStage = new Stage();
        renuntareActivitateGrupStage.setTitle("Renunțare Activitate Grup");

        // Creează elementele UI pentru renunțarea la activitatea de grup
        Label labelActivitate = new Label("Selectează Activitatea de Grup:");
        ComboBox<String> comboBoxActivitatiGrup = new ComboBox<>();
        Button buttonRenuntareActivitateGrup = new Button("Renunță la Activitatea de Grup");

        // Populează ComboBox-ul cu numele activităților de grup la care studentul este înscris
        populeazaComboBoxActivitatiInscrise(comboBoxActivitatiGrup);

        // Adaugă acțiunea la apăsarea butonului Renunță la Activitatea de Grup
        buttonRenuntareActivitateGrup.setOnAction(e -> handleRenuntareActivitateGrupFunction(
                comboBoxActivitatiGrup.getValue(),
                renuntareActivitateGrupStage
        ));

        // Adaugă elementele UI pe grilă
        GridPane gridRenuntareActivitateGrup = new GridPane();
        gridRenuntareActivitateGrup.setHgap(10);
        gridRenuntareActivitateGrup.setVgap(10);
        gridRenuntareActivitateGrup.setPadding(new Insets(25, 25, 25, 25));
        gridRenuntareActivitateGrup.add(labelActivitate, 0, 0);
        gridRenuntareActivitateGrup.add(comboBoxActivitatiGrup, 1, 0);
        gridRenuntareActivitateGrup.add(buttonRenuntareActivitateGrup, 0, 1);

        // Configurarea scenei și afișarea ferestrei
        Scene renuntareActivitateGrupScene = new Scene(gridRenuntareActivitateGrup, 400, 200);
        renuntareActivitateGrupStage.setScene(renuntareActivitateGrupScene);
        renuntareActivitateGrupStage.show();
    }

    private void handleRenuntareActivitateGrupFunction(String activitateGrupSelectata, Stage renuntareActivitateGrupStage) {
        conexiune = DatabaseConnection.getConnection();

        // Extrage ID-ul activității de grup din șirul selectat
        Integer idActivitateGrupSelectata = obtineIdActivitateGrupDupaNume(activitateGrupSelectata);

        if (idActivitateGrupSelectata != null) {
            // Apel procedură stocată pentru renunțarea la activitatea de grup
            String sql = "{call RenuntareActivitateGrup(?, ?)}";

            try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
                preparedStatement.setInt(1, idStudent);  // presupunând că idStudent este deja definit
                preparedStatement.setInt(2, idActivitateGrupSelectata);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String mesaj = resultSet.getString("Mesaj");
                    afiseazaSucces(mesaj);
                }

                // Închide fereastra de renunțare la activitatea de grup
                renuntareActivitateGrupStage.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                afiseazaEroare("Eroare la renunțarea la activitatea de grup.");
            }
        } else {
            afiseazaEroare("Eroare la extragerea ID-ului activității de grup.");
        }
    }

    private void populeazaComboBoxActivitatiInscrise(ComboBox<String> comboBox) {
        conexiune = DatabaseConnection.getConnection();

        // Obține informațiile despre activitățile de grup la care studentul este înscris și le adaugă în ComboBox
        String sql = "SELECT AG.Nume_Activitate " +
                "FROM Activitati_Grup AG " +
                "JOIN Student_Activitate_Grup SAG ON AG.ID_Activitate_Grup = SAG.ID_Activitate_Grup " +
                "WHERE SAG.ID_Student = ?";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setInt(1, idStudent);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String numeActivitateGrup = resultSet.getString("Nume_Activitate");
                comboBox.getItems().add(numeActivitateGrup);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea datelor despre activitățile de grup la care este înscris studentul.");
        }
    }





    private void handleVizualizareDatePersonale() {
        // Creează un nou stage pentru vizualizarea datelor personale
        Stage datePersonaleStage = new Stage();
        datePersonaleStage.setTitle("Vizualizare Date Personale");

        // Creează VBox pentru a afișa datele personale
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        // Adaugă datele personale în VBox
        adaugaDatePersonaleInVBox(vbox);

        // Configurarea scenei și afișarea ferestrei
        Scene datePersonaleScene = new Scene(vbox, 400, 400);
        datePersonaleStage.setScene(datePersonaleScene);
        datePersonaleStage.show();
    }



    private void adaugaDatePersonaleInVBox(VBox vbox) {
        conexiune = DatabaseConnection.getConnection();

        // Obține detaliile studentului din baza de date
        String sql = "SELECT Tip_Utilizator, Nume, Prenume, Adresa, Email, Telefon, Cont_IBAN, An_Studiu, Ore_Sustinute " +
                "FROM Studenti S " +
                "JOIN Utilizatori U ON U.ID_Utilizator = S.ID_Utilizator " +
                "WHERE S.ID_Student = ?";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setInt(1, getIdStudent());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                List<String> columnNames = Arrays.asList(
                        "Tip_Utilizator", "Nume", "Prenume", "Adresa", "Email", "Telefon", "Cont_IBAN", "An_Studiu", "Ore_Sustinute"
                );

                GridPane gridPane = new GridPane();
                gridPane.setHgap(10);
                gridPane.setVgap(10);

                for (int i = 0; i < columnNames.size(); i++) {
                    String columnName = columnNames.get(i);
                    String value = resultSet.getString(columnName);

                    Label label = new Label(columnName + ": ");
                    TextField textField = new TextField(value);
                    textField.setDisable(true); // Facem TextFields nemodificabile

                    gridPane.add(label, 0, i);
                    gridPane.add(textField, 1, i);
                }

                vbox.getChildren().add(gridPane);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea datelor personale.");
        }
    }



















    private void handleVizualizareMesaje() {
        Stage vizualizareMesajeStage = new Stage();
        vizualizareMesajeStage.setTitle("Vizualizare Mesaje");

        // ComboBox pentru a alege grupul de studiu
        ComboBox<String> comboBoxGrupuri = new ComboBox<>();
        populeazaComboBoxGrupuriStudiuInscrise(comboBoxGrupuri);  // Implementează funcția de populare a ComboBox-ului

        // TextArea pentru afișarea mesajelor
        TextArea textAreaMesaje = new TextArea();
        textAreaMesaje.setEditable(false);
        textAreaMesaje.setWrapText(true);

        // Adaugă acțiune la selectarea unui grup din ComboBox
        comboBoxGrupuri.setOnAction(e -> afiseazaMesajeGrup(comboBoxGrupuri.getValue(), textAreaMesaje));

        // Layout principal pentru interfața de vizualizare mesaje
        VBox mainLayout = new VBox(10, comboBoxGrupuri, textAreaMesaje);
        mainLayout.setPadding(new Insets(20, 20, 20, 20));

        // Configurarea scenei și afișarea ferestrei
        Scene vizualizareMesajeScene = new Scene(mainLayout, 400, 300);
        vizualizareMesajeStage.setScene(vizualizareMesajeScene);
        vizualizareMesajeStage.show();
    }

    private void afiseazaMesajeGrup(String selectedGroup, TextArea textAreaMesaje) {
        conexiune = DatabaseConnection.getConnection();

        int idGrup = getIdGrupByNume(selectedGroup);

        // Șterge textul existent din TextArea
        textAreaMesaje.clear();

        // Obține mesajele pentru grupul specificat din baza de date
        String sql = "SELECT U.Nume, U.Prenume, MG.Continut_Mesaj " +
                "FROM Mesaje_Grup MG " +
                "JOIN Studenti S ON MG.ID_Student = S.ID_Student " +
                "JOIN Utilizatori U ON S.ID_Utilizator = U.ID_Utilizator " +
                "WHERE MG.ID_Grup = ?";

        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sql)) {
            preparedStatement.setInt(1, idGrup);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Afișează mesajele în TextArea
            while (resultSet.next()) {
                String numeStudent = resultSet.getString("Nume") + " " + resultSet.getString("Prenume");
                String textMesaj = resultSet.getString("Continut_Mesaj");

                textAreaMesaje.appendText(numeStudent + ": " + textMesaj + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afiseazaEroare("Eroare la obținerea mesajelor pentru grupul cu ID-ul " + idGrup);
        }
    }







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
        // Implementează afișarea activităților pentru data selectată din baza de date

        // Interogare SQL pentru a obține activitățile pentru data selectată
        String sqlQuery = "SELECT " +
                "D.Nume_Disciplina, " +
                "TA.Nume_Tip_Activitate, " +
                "C.Ora_Inceput, " +
                "C.Ora_Sfarsit " +
                "FROM " +
                "Calendar C " +
                "JOIN " +
                "Activitati A ON C.ID_Activitate = A.ID_Activitate " +
                "JOIN " +
                "Discipline D ON D.ID_Disciplina = A.ID_Disciplina " +
                "JOIN " +
                "Asociere_Disciplina_Profesor ADP ON ADP.ID_Disciplina = D.ID_Disciplina " +
                "JOIN " +
                "Tip_Activitate TA ON A.ID_Tip_Activitate = TA.ID_Tip_Activitate " +
                "JOIN " +
                "Inscriere_Student_Activitate ISA ON A.ID_Activitate = ISA.ID_Activitate " +
                "JOIN " +
                "Studenti S ON ISA.ID_Student = S.ID_Student " +
                "JOIN " +
                "Asociere_Student_Profesor ASP ON ASP.ID_Student = S.ID_Student " +
                "WHERE " +
                "? >= C.Data_Inceput AND " +
                "? <= C.Data_Sfarsit AND " +
                "S.ID_Student = ? AND " +
                "DAYOFWEEK(?) = DAYOFWEEK(C.Data_Inceput) " +
                "GROUP BY " +
                "D.Nume_Disciplina, " +
                "TA.Nume_Tip_Activitate, " +
                "C.Ora_Inceput, " +
                "C.Ora_Sfarsit";




        try (PreparedStatement preparedStatement = conexiune.prepareStatement(sqlQuery)) {
            preparedStatement.setDate(1, Date.valueOf(selectedDate));
            preparedStatement.setDate(2, Date.valueOf(selectedDate));
            preparedStatement.setInt(3, getIdStudent());
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
            afiseazaEroare("Eroare la obținerea datelor despre activitățile planificate.");
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
