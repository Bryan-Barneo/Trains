package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.IJeu;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.ListeDeCartes;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
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
public class VueReserve extends VBox {
    private IJeu jeu;
    @FXML
    private HBox ligne1;
    @FXML
    private HBox ligne2;



    public VueReserve(IJeu jeu) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/Reserve.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.jeu = jeu;
    }

    public void creerBindings() {
        creerBoutique();
    }
    private void creerBoutique() {
        ListeDeCartes cartes = jeu.getReserve();
        for (int i = 0; i < cartes.size()/2; i++) {
            Carte carte = cartes.get(i);
            VueCarte vueCarte = new VueCarte(carte);
            vueCarte.setCarteChoisieListener(mouseEvent -> {
                System.out.println(carte.getNom()+" a été acheté");
                jeu.uneCarteDeLaReserveEstAchetee(carte.getNom());
            }); // Définir l'action par défaut ou une autre action
            ligne1.getChildren().add(vueCarte);
        }
        for (int i = cartes.size()/2; i < cartes.size(); i++) {
            Carte carte = cartes.get(i);
            VueCarte vueCarte = new VueCarte(carte);
            vueCarte.setCarteChoisieListener(mouseEvent -> {
                System.out.println(carte.getNom() + " a été choisi");
                jeu.uneCarteDeLaReserveEstAchetee(carte.getNom());
            }); // Définir l'action par défaut ou une autre action
            ligne2.getChildren().add(vueCarte);
        }
        ligne1.setAlignment(Pos.CENTER);
        ligne2.setAlignment(Pos.CENTER);
        ligne1.setSpacing(5);
        ligne2.setSpacing(5);
    }
}
