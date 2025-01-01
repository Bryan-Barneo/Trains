package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.IJeu;
import fr.umontpellier.iut.trainsJavaFX.IJoueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.ListeDeCartes;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;

/**
 * Cette classe correspond à la fenêtre principale de l'application.
 *
 * Elle est initialisée avec une référence sur la partie en cours (Jeu).
 * <p>
 * On y définit les bindings sur les éléments internes qui peuvent changer
 * (le joueur courant, ses cartes en main, son score, ...)
 * ainsi que les listeners à exécuter lorsque ces éléments changent
 */
public class VueDuJeu extends GridPane {

    private final IJeu jeu;
    private VuePlateau plateau;
    private VueJoueurCourant joueurCourant;
    private VueActionJoueurCourant actionJoueurCourant;

    private VueReserve vueReserve;
    @FXML
    private VBox joueurActuel;
    @FXML
    private VBox plateauVBox;
    @FXML
    private VBox vueAutresJoueursHBox;
    @FXML
    private VBox reserve;
    @FXML
    private VBox VBoxActionJoueurCourant;

    public VueDuJeu(IJeu jeu) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/Jeu.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.jeu = jeu;
        plateau = new VuePlateau();
        plateauVBox.getChildren().add(plateau);

        joueurCourant = new VueJoueurCourant(this.jeu);
        joueurActuel.getChildren().add(joueurCourant);

        VueAutresJoueurs autresJoueurs = new VueAutresJoueurs(this.jeu);
        vueAutresJoueursHBox.getChildren().add(autresJoueurs);

        actionJoueurCourant = new VueActionJoueurCourant(this.jeu);
        VBoxActionJoueurCourant.getChildren().add(actionJoueurCourant);

        vueReserve = new VueReserve(this.jeu);
        reserve.getChildren().add(vueReserve);
    }



    public void creerBindings() {
        plateau.prefWidthProperty().bind(getScene().widthProperty());
        plateau.prefHeightProperty().bind(getScene().heightProperty());
        plateau.creerBindings();
        joueurCourant.creerBindings();
        actionJoueurCourant.creerBindings();
        vueReserve.creerBindings();
        jeu.joueurCourantProperty().addListener((observable, oldValue, newValue) -> {
            setBackground(new Background(new BackgroundImage(new Image("images/Fond/fond-" + newValue.getCouleur() + ".jpg"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        });
    }

    public IJeu getJeu() {
        return jeu;
    }

    EventHandler<? super MouseEvent> actionPasserParDefaut = (mouseEvent -> System.out.println("Passer a été demandé"));

}
