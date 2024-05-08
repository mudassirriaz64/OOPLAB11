package com.labtasks.task2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AddressBookController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;

    @FXML
    private TableView<Contact> contactTable;
    @FXML
    private TableColumn<Contact, String> nameColumn;
    @FXML
    private TableColumn<Contact, String> phoneColumn;
    @FXML
    private TableColumn<Contact, String> emailColumn;

    private ObservableList<Contact> contacts = FXCollections.observableArrayList();
    private Contact selectedContact;

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        contactTable.setItems(contacts);
    }

    @FXML
    private void handleAddContact() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        if (!name.isEmpty() && !phone.isEmpty() && !email.isEmpty()) {
            Contact newContact = new Contact(name, phone, email);
            contacts.add(newContact);
            clearFields();
        }
    }

    @FXML
    private void handleEditContact() {
        if (selectedContact != null) {
            selectedContact.setName(nameField.getText());
            selectedContact.setPhone(phoneField.getText());
            selectedContact.setEmail(emailField.getText());
            contactTable.refresh();
            clearFields();
        }
    }

    @FXML
    private void handleDeleteContact() {
        int selectedIndex = contactTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            contactTable.getItems().remove(selectedIndex);
        }
    }

    @FXML
    private void handleContactSelected() {
        selectedContact = contactTable.getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            nameField.setText(selectedContact.getName());
            phoneField.setText(selectedContact.getPhone());
            emailField.setText(selectedContact.getEmail());
        }
    }

    private void clearFields() {
        nameField.clear();
        phoneField.clear();
        emailField.clear();
    }
}
