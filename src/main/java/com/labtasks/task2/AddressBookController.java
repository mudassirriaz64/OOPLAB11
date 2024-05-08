package com.labtasks.task2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AddressBookController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField emailField;
    @FXML
    private TableView<AddressBook.Contact> tableView;

    private ObservableList<AddressBook.Contact> contacts;

    public void initialize() {
        contacts = FXCollections.observableArrayList();
        tableView.setItems(contacts);
    }

    @FXML
    private void addContact() {
        String name = nameField.getText();
        String phoneNumber = phoneNumberField.getText();
        String email = emailField.getText();

        if (!name.isEmpty() && !phoneNumber.isEmpty() && !email.isEmpty()) {
            AddressBook.Contact contact = new AddressBook.Contact(name, phoneNumber, email);
            contacts.add(contact);
            clearFields();
        } else {
            showAlert("Error", "Please fill in all fields.");
        }
    }

    @FXML
    private void editContact() {
        AddressBook.Contact selectedContact = tableView.getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            String newName = nameField.getText();
            String newPhoneNumber = phoneNumberField.getText();
            String newEmail = emailField.getText();

            if (!newName.isEmpty() && !newPhoneNumber.isEmpty() && !newEmail.isEmpty()) {
                selectedContact.setName(newName);
                selectedContact.setPhoneNumber(newPhoneNumber);
                selectedContact.setEmail(newEmail);
                tableView.refresh();
                clearFields();
            } else {
                showAlert("Error", "Please fill in all fields.");
            }
        } else {
            showAlert("Error", "No contact selected.");
        }
    }

    @FXML
    private void deleteContact() {
        AddressBook.Contact selectedContact = tableView.getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            contacts.remove(selectedContact);
            clearFields();
        } else {
            showAlert("Error", "No contact selected.");
        }
    }

    private void clearFields() {
        nameField.clear();
        phoneNumberField.clear();
        emailField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
