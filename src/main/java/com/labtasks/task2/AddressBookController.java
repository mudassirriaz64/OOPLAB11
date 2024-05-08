package com.labtasks.task2;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        phoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhone()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        contactTable.setItems(contacts);

        loadContacts(); // Load contacts when the application starts
    }

    @FXML
    private void handleAddContact()
    {
        String name = nameField.getText();
        String phoneNumber = phoneField.getText();
        String email = emailField.getText();

        if (!name.isEmpty() && !phoneNumber.isEmpty() && !email.isEmpty()) {
            Contact contact = new Contact(name, phoneNumber, email);
            contacts.add(contact);
            saveContacts();
            clearFields();
        } else {
            showAlert("Error", "Please fill in all fields.");
        }
    }

    @FXML
    private void handleEditContact()
    {
        Contact selectedContact = contactTable.getSelectionModel().getSelectedItem();
        if (selectedContact != null)
        {
            String newName = nameField.getText();
            String newPhoneNumber = phoneField.getText();
            String newEmail = emailField.getText();

            if (!newName.isEmpty() && !newPhoneNumber.isEmpty() && !newEmail.isEmpty()) {
                selectedContact.setName(newName);
                selectedContact.setPhone(newPhoneNumber);
                selectedContact.setEmail(newEmail);
                contactTable.refresh();
                saveContacts();
                clearFields();
            } else {
                showAlert("Error", "Please fill in all fields.");
            }
        } else {
            showAlert("Error", "No contact selected.");
        }
    }

    private void showAlert(String title, String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleDeleteContact() {
        Contact selectedContact =contactTable.getSelectionModel().getSelectedItem();
        if (selectedContact != null)
        {
            contacts.remove(selectedContact);
            saveContacts();
            clearFields();
        } else {
            showAlert("Error", "No contact selected.");
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

    private void saveContacts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("contacts.txt"))) {
            for (Contact contact : contacts) {
                String contactInfo = contact.getName() + ", " + contact.getPhone() + ", " + contact.getEmail();
                writer.write(contactInfo);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadContacts() {
        try (BufferedReader reader = new BufferedReader(new FileReader("contacts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] contactInfo = line.split(", ");
                if (contactInfo.length == 3) {
                    String name = contactInfo[0];
                    String phone = contactInfo[1];
                    String email = contactInfo[2];
                    Contact contact = new Contact(name, phone, email);
                    contacts.add(contact);
                }
            }
        } catch (IOException e) {
            // If the file doesn't exist or cannot be read, simply don't load contacts
        }
    }
}
