package com.example.javafxhelloworld;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PersonOverviewController {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;
    @FXML
    private Button deleteButton;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;

    private MainApp mainApp;

    public PersonOverviewController() {
    }

    private void showPersonDetails(Person person) {
        if (person != null) {
            deleteButton.disableProperty().set(false);
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            cityLabel.setText(person.getCity());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
        } else {
            deleteButton.disableProperty().set(true);
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            cityLabel.setText("");
            postalCodeLabel.setText("");
            birthdayLabel.setText("");
        }
    }

    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
        } else { // пример использования Alert'a. в данном случае не нужен, т.к. кнопка активна только при выбранной строке
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewPerson() {
        Person tempPerson = new Person();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if (okClicked) {
            mainApp.getPersonData().add(tempPerson);
        }
    }

    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void initialize() {
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        showPersonDetails(null);
        personTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> showPersonDetails(newValue)));
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        personTable.setItems(mainApp.getPersonData());
    }
}
