package ma.enset.javafx_basics;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.stream.Collectors;

public class GestionTache extends Application {

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 920, 440);

        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());


        stage.setTitle("Gestion des tâches");

        Label labelName = new Label("Nom de la tâche :");
        TextField textFieldName = new TextField();

        Label labelDesc = new Label("Description :");
        TextArea textFieldDesc = new TextArea();

        VBox textContainer = new VBox(labelName, textFieldName, labelDesc, textFieldDesc);
        textContainer.setSpacing(10);

        Label labelPriority = new Label("Priorité :");
        ComboBox<String> comboBoxPriority = new ComboBox<>();
        comboBoxPriority.getItems().addAll("Basse", "Moyenne", "Elevée");

        Label labelDeadline = new Label("Échéance :");
        DatePicker datePicker = new DatePicker();

        VBox selectContainer = new VBox(labelPriority, comboBoxPriority, labelDeadline, datePicker);
        selectContainer.setSpacing(10);

        Button buttonAdd = new Button("Ajouter");
        Button buttonRemove = new Button("Supprimer");
        Button buttonUpdate = new Button("Modifier");
        Button buttonFilter = new Button("Rechercher");

        VBox buttonsContainer = new VBox(buttonAdd, buttonRemove, buttonUpdate, buttonFilter);
        buttonsContainer.setSpacing(10);

        HBox inputContainer = new HBox(textContainer, selectContainer, buttonsContainer);
        inputContainer.setSpacing(10);

        Button confirmButton = new Button("Confirmer");
        Button cancelButton = new Button("Annuler");

        HBox modifyButtons = new HBox(confirmButton, cancelButton);
        modifyButtons.setSpacing(10);



        ObservableList<HBox> taskData = FXCollections.observableArrayList();
        ListView<HBox> listView = new ListView<>(taskData);

        VBox mainContainer = new VBox(inputContainer, listView);
        mainContainer.setSpacing(10);
        mainContainer.setPadding(new Insets(10));

        buttonAdd.setOnAction(actionEvent -> {
            if (textFieldName.getText().isEmpty() || textFieldDesc.getText().isEmpty()
                    || comboBoxPriority.getSelectionModel().isEmpty() || datePicker.getValue() == null) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
                return;
            }

            HBox taskRow = new HBox();
            taskRow.setSpacing(10);
            taskRow.setPadding(new Insets(5));

            Label nameLabel = createLabel(textFieldName.getText(), 150);
            Label descLabel = createLabel(textFieldDesc.getText(), 150);
            Label priorityLabel = createLabel(comboBoxPriority.getSelectionModel().getSelectedItem(), 100);
            String priority = comboBoxPriority.getSelectionModel().getSelectedItem();
            if (priority.equals("Basse")) {
                taskRow.getStyleClass().add("low-priority");
            } else if (priority.equals("Moyenne")) {
                taskRow.getStyleClass().add("medium-priority");
            } else if (priority.equals("Elevée")) {
                taskRow.getStyleClass().add("high-priority");
            }
            Label deadlineLabel = createLabel(datePicker.getValue().toString(), 100);

            taskRow.getChildren().addAll(nameLabel, descLabel, priorityLabel, deadlineLabel);
            taskData.add(taskRow);

            textFieldName.clear();
            textFieldDesc.clear();
            comboBoxPriority.getSelectionModel().clearSelection();
            datePicker.setValue(null);
        });

        buttonRemove.setOnAction(actionEvent -> {
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                taskData.remove(selectedIndex);
                listView.setFocusTraversable(false);
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner une tâche à supprimer.");
            }
        });

        buttonUpdate.setOnAction(actionEvent -> {
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner une tâche à modifier.");
                return;
            }

            HBox selectedTask = taskData.get(selectedIndex);
            Label nameLabel = (Label) selectedTask.getChildren().get(0);
            Label descLabel = (Label) selectedTask.getChildren().get(1);
            Label priorityLabel = (Label) selectedTask.getChildren().get(2);
            Label deadlineLabel = (Label) selectedTask.getChildren().get(3);

            textFieldName.setText(nameLabel.getText());
            textFieldDesc.setText(descLabel.getText());
            comboBoxPriority.getSelectionModel().select(priorityLabel.getText());
            datePicker.setValue(java.time.LocalDate.parse(deadlineLabel.getText()));

            buttonsContainer.getChildren().setAll(modifyButtons);

            confirmButton.setOnAction(confirmEvent -> {
                nameLabel.setText(textFieldName.getText());
                descLabel.setText(textFieldDesc.getText());
                priorityLabel.setText(comboBoxPriority.getSelectionModel().getSelectedItem());
                deadlineLabel.setText(datePicker.getValue().toString());

                buttonsContainer.getChildren().setAll(buttonAdd, buttonRemove, buttonUpdate, buttonFilter);
                clearInputFields(textFieldName, textFieldDesc, comboBoxPriority, datePicker);
            });

            cancelButton.setOnAction(cancelEvent -> {
                buttonsContainer.getChildren().setAll(buttonAdd, buttonRemove, buttonUpdate, buttonFilter);
                clearInputFields(textFieldName, textFieldDesc, comboBoxPriority, datePicker);
            });
        });

        buttonFilter.setOnAction(actionEvent -> {
            ChoiceDialog<String> filterDialog = new ChoiceDialog<>("Lister toutes", "Lister toutes", "Avec Priorité", "Échéance passée", "Nom ou Description");
            filterDialog.setTitle("Filtrer les tâches");
            filterDialog.setHeaderText(null);
            filterDialog.setContentText("Choisissez un filtre :");

            filterDialog.showAndWait().ifPresent(filterChoice -> {
                switch (filterChoice) {
                    case "Lister toutes":
                        listView.setItems(taskData);
                        break;
                    case "Avec Priorité":
                        ChoiceDialog<String> priorityDialog = new ChoiceDialog<>("Basse", "Basse", "Moyenne", "Elevée");
                        priorityDialog.setTitle("Filtrer par Priorité");
                        priorityDialog.setHeaderText(null);
                        priorityDialog.setContentText("Choisissez une priorité :");

                        priorityDialog.showAndWait().ifPresent(selectedPriority -> {
                            ObservableList<HBox> filteredByPriority = taskData.stream()
                                    .filter(task -> {
                                        Label priorityLabel = (Label) task.getChildren().get(2);
                                        return priorityLabel.getText().equals(selectedPriority);
                                    })
                                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
                            listView.setItems(filteredByPriority);
                        });
                        break;
                    case "Échéance passée":
                        LocalDate today = LocalDate.now();
                        ObservableList<HBox> filteredByDeadline = taskData.stream()
                                .filter(task -> {
                                    Label deadlineLabel = (Label) task.getChildren().get(3);
                                    LocalDate deadline = LocalDate.parse(deadlineLabel.getText());
                                    return deadline.isBefore(today);
                                })
                                .collect(Collectors.toCollection(FXCollections::observableArrayList));
                        listView.setItems(filteredByDeadline);
                        break;
                    case "Nom ou Description":
                        TextInputDialog searchDialog = new TextInputDialog();
                        searchDialog.setTitle("Search");
                        searchDialog.setHeaderText(null);
                        searchDialog.setContentText("Search With name Or Description :");

                        searchDialog.showAndWait().ifPresent(keyword -> {
                            ObservableList<HBox> filteredByKeyword = taskData.stream()
                                    .filter(task -> {
                                        Label nameLabel = (Label) task.getChildren().get(0);
                                        Label descLabel = (Label) task.getChildren().get(1);
                                        return nameLabel.getText().contains(keyword) || descLabel.getText().contains(keyword);
                                    })
                                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
                            listView.setItems(filteredByKeyword);
                        });
                        break;
                }
            });
        });



        root.setCenter(mainContainer);
        stage.setScene(scene);
        stage.show();
    }

    private Label createLabel(String text, double width) {
        Label label = new Label(text);
        label.setMinWidth(width);
        label.setMaxWidth(width);
        label.setWrapText(true);
        return label;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearInputFields(TextField textFieldName, TextArea textFieldDesc, ComboBox<String> comboBoxPriority, DatePicker datePicker) {
        textFieldName.clear();
        textFieldDesc.clear();
        comboBoxPriority.getSelectionModel().clearSelection();
        datePicker.setValue(null);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
