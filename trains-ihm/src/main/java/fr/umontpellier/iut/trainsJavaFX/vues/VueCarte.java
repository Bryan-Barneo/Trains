package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.ICarte;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * Cette classe représente la vue d'une carte.
 * <p>
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueCarte extends Button {

    private final ICarte carte;

    public VueCarte(ICarte carte) {
        this.carte = carte;

        String chemin = ("images/cartes/" + carte.getNom() + ".jpg")
                .toLowerCase()
                .replace(" ", "_")
                .replace("é", "e")
                .replace("ô", "o");
        ImageView image = new ImageView(chemin);
        image.setFitHeight(150);
        image.setPreserveRatio(true);
        this.setGraphic(image);
        this.setStyle("-fx-background-color: transparent;");
        this.setCursor(Cursor.HAND);
        this.setOnAction(quandCarteEstChoisie);
    }

    private EventHandler<ActionEvent> quandCarteEstChoisie = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            ((VueDuJeu) getScene().getRoot()).getJeu().joueurCourantProperty().get().uneCarteDeLaMainAEteChoisie(carte.getNom());
        }
    };

    public EventHandler<ActionEvent> getQuandCarteEstChoisie() {
        return quandCarteEstChoisie;
    }

    public void setCarteChoisieListener(EventHandler<MouseEvent> quandCarteEstChoisie) {
        setOnMouseClicked(quandCarteEstChoisie);
    }

    public ICarte getCarte() {
        return carte;
    }
}
