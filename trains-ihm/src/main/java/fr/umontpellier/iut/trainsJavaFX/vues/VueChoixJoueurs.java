package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.TrainsIHM;
import fr.umontpellier.iut.trainsJavaFX.mecanique.plateau.Plateau;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe correspond à une nouvelle fenêtre permettant de choisir le nombre et les noms des joueurs de la partie.
 * <p>
 * Sa présentation graphique peut automatiquement être actualisée chaque fois que le nombre de joueurs change.
 * Lorsque l'utilisateur a fini de saisir les noms de joueurs, il demandera à démarrer la partie.
 */
public class VueChoixJoueurs extends Stage {
    @FXML
    private CheckBox checkBox2;
    @FXML
    private CheckBox checkBox3;
    @FXML
    private CheckBox checkBox4;
    @FXML
    private VBox Joueursnoms;
    @FXML
    private Button start;
    @FXML
    private ComboBox<String> plateauComboBox;


    private final ObservableList<String> nomsJoueurs;
    private Plateau plateauChoisi;

    public VueChoixJoueurs() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/VueChoixJoueur.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(new VBox());
        try {
            fxmlLoader.load();
            this.setScene(new Scene(fxmlLoader.getRoot()));
            this.setTitle("Choix des Joueurs");
        } catch (IOException e) {
            e.printStackTrace();
        }
        nomsJoueurs = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        checkBox2.setOnAction(e -> SelectionDesJoueurs(checkBox2, 2));
        checkBox3.setOnAction(e -> SelectionDesJoueurs(checkBox3, 3));
        checkBox4.setOnAction(e -> SelectionDesJoueurs(checkBox4, 4));
        start.setOnAction(e -> onStartGame());
        plateauComboBox.getItems().addAll("Tokyo", "Osaka");
        plateauComboBox.setValue("Osaka");
    }

    private void SelectionDesJoueurs(CheckBox selectedCheckBox, int numberOfPlayers) {
        if (selectedCheckBox.isSelected()) {
            checkBox2.setSelected(selectedCheckBox == checkBox2);
            checkBox3.setSelected(selectedCheckBox == checkBox3);
            checkBox4.setSelected(selectedCheckBox == checkBox4);
            ChampsNomsJoueurs(numberOfPlayers);
        } else {
            Joueursnoms.getChildren().clear();
        }
    }

    private void ChampsNomsJoueurs(int numberOfPlayers) {
        Joueursnoms.getChildren().clear();
        for (int i = 1; i <= numberOfPlayers; i++) {
            TextField textField = new TextField();
            textField.setPromptText("Nom du joueur " + i);
            Joueursnoms.getChildren().add(textField);
        }
    }

    @FXML
    private void onStartGame() {
        if (plateauComboBox.getValue().equals("Tokyo")) {
            plateauChoisi = Plateau.TOKYO;
        } else if (plateauComboBox.getValue().equals("Osaka")) {
            plateauChoisi = Plateau.OSAKA;
        }
        setListeDesNomsDeJoueurs();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public List<String> getNomsJoueurs() {
        return nomsJoueurs;
    }

    /**
     * Définit l'action à exécuter lorsque la liste des participants est correctement initialisée
     */
    public void setNomsDesJoueursDefinisListener(ListChangeListener<String> listener) {
        nomsJoueurs.addListener(listener);
    }

    /**
     * Vérifie que tous les noms des participants sont renseignés
     * et affecte la liste définitive des participants
     */
    protected void setListeDesNomsDeJoueurs() {
        ArrayList<String> tempNamesList = new ArrayList<>();
        for (int i = 1; i <= getNombreDeJoueurs(); i++) {
            String name = getJoueurParNumero(i);
            if (name == null || name.equals("")) {
                tempNamesList.clear();
                showAlert("Erreur", "Tous les champs doivent être remplis !");
                break;
            } else {
                tempNamesList.add(name);
            }
        }
        if (!tempNamesList.isEmpty()) {
            if (plateauChoisi == null) {
                showAlert("Erreur", "Veuillez sélectionner un plateau.");
                return;
            }
            nomsJoueurs.clear();
            nomsJoueurs.addAll(tempNamesList);
            hide();
        }
    }

    /**
     * Retourne le nombre de participants à la partie que l'utilisateur a renseigné
     */
    protected int getNombreDeJoueurs() {
        if (checkBox2.isSelected()) {
            return 2;
        } else if (checkBox3.isSelected()) {
            return 3;
        } else if (checkBox4.isSelected()) {
            return 4;
        }
        return 0;
    }

    /**
     * Retourne le nom que l'utilisateur a renseigné pour le ième participant à la partie
     * @param playerNumber : le numéro du participant
     */
    protected String getJoueurParNumero(int playerNumber) {
        if (playerNumber <= Joueursnoms.getChildren().size()) {
            TextField textField = (TextField) Joueursnoms.getChildren().get(playerNumber - 1);
            return textField.getText();
        }
        return null;
    }

    public Plateau getPlateau() {
        return plateauChoisi;
    }
}
