package com.labtasks.task1;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

public class StudentRegistrationController
{

    @FXML
    private TextField nameField;

    @FXML
    private TextField idField;

    @FXML
    private TextField emailField;

    @FXML
    private ListView<String> courseList;

    // ObservableList for holding courses
    private ObservableList<String> courses = FXCollections.observableArrayList(
            "Discrete Structures",
            "Object Oriented Programming",
            "Linear Algebra",
            "Introduction to Software Engineering",
            "Islamic Studies"
    );

    @FXML
    public void initialize() {
        // Populate the course list
        courseList.setItems(courses);

        // Set the selection mode to MULTIPLE
        MultipleSelectionModel<String> selectionModel = courseList.getSelectionModel();
        selectionModel.setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
    }

    @FXML
    public void saveRegistration() {
        String name = nameField.getText();
        String id = idField.getText();
        String email = emailField.getText();
        List<String> selectedCourses = courseList.getSelectionModel().getSelectedItems();
        String selectedCoursesText = selectedCourses.stream().collect(Collectors.joining(", "));

        // Save the registration details to a file
        try (PrintWriter writer = new PrintWriter(new FileWriter("student_registration.txt", true))) {
            writer.println("Name: " + name);
            writer.println("ID: " + id);
            writer.println("Email: " + email);
            writer.println("Selected Courses: " + selectedCoursesText);
            writer.println("-----------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Show success message popup
        showSuccessMessage();

        // Clear the fields after saving
        clearFields();
    }

    @FXML
    public void clearFields() {
        nameField.clear();
        idField.clear();
        emailField.clear();
        courseList.getSelectionModel().clearSelection();
    }

    private void showSuccessMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Registration saved successfully!");
        alert.showAndWait();
    }
}
