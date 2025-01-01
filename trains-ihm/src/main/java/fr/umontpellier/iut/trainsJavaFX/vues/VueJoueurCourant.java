package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.IJeu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;

import java.io.IOException;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 * <p>
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueJoueurCourant extends VBox {
    @FXML
    private HBox vueJoueurHBox;
    @FXML
    private Label nbArgent;
    @FXML
    private Label nbRails;
    @FXML
    private Label nbPoints;
    @FXML
    private Label nbCube;
    @FXML
    private Label nbCartesMain;
    @FXML
    private Label nbCartesPioche;

    private IJeu jeu;


    public VueJoueurCourant(IJeu jeu) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/JoueurCourant.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.jeu = jeu;
    }

    public void creerBindings() {
        jeu.joueurCourantProperty().addListener((observable, oldValue, newValue) -> {
            vueJoueurHBox.getChildren().clear();
            vueJoueurHBox.getChildren().add(new VueJoueur(newValue));
            nbArgent.textProperty().bind(newValue.argentProperty().asString());
            nbRails.textProperty().bind(newValue.pointsRailsProperty().asString());
            nbPoints.textProperty().bind(newValue.scoreProperty().asString());
            nbCube.textProperty().bind(newValue.nbJetonsRailsProperty().asString());
            nbCartesMain.textProperty().bind(newValue.mainProperty().sizeProperty().asString());
            nbCartesPioche.textProperty().bind(newValue.piocheProperty().sizeProperty().asString());
        });
        setBackground(new Background(new BackgroundImage(new Image("images/Fond/FondJoueur.jpg"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(300, 800, false, false, false, false))));
    }

    public static String getEnglish(String couleur) {
        return VueJoueur.getEnglish(couleur);
    }
}
