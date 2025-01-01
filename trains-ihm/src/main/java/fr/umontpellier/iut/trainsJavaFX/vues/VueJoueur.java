package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.IJoueur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class VueJoueur extends HBox {
    private IJoueur joueur;
    @FXML
    private Circle couleurJoueur;
    @FXML
    private Label nomJoueur;

    public VueJoueur(IJoueur j) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/Joueur.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.joueur = j;
        nomJoueur.setText(joueur.getNom());
        nomJoueur.setStyle("-fx-text-fill: white");
        couleurJoueur.setFill(Color.web(getEnglish(joueur.getCouleur().name())));
    }

    public static String getEnglish(String couleur) {
        if (couleur == "BLEU") return "BLUE";
        if (couleur == "VERT") return "GREEN";
        if (couleur == "ROUGE") return "RED";
        if (couleur == "JAUNE") return "YELLOW";
        return null;

    }
}
