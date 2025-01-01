package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.ICarte;
import fr.umontpellier.iut.trainsJavaFX.IJeu;
import fr.umontpellier.iut.trainsJavaFX.IJoueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;

public class VueActionJoueurCourant extends VBox {
    private IJeu jeu;
    @FXML
    private Label labelInstruction, nbCartePioche, nbCarteDefausse;
    @FXML
    private TextField textFieldAction;
    @FXML
    private Button boutonPasser, boutonEntrer;
    @FXML
    private HBox listCarteMain, HBoxCartesRecus, HBoxCartesJouées;
    @FXML
    private VBox PiocheVBox, defausseVBox;

    public VueActionJoueurCourant(IJeu j) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/ActionJoueurCourant.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.jeu = j;
    }

    public void creerBindings() {
        boutonPasser.setOnAction(event -> jeu.passerAEteChoisi());

        labelInstruction.textProperty().bind(jeu.instructionProperty());

        jeu.joueurCourantProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                oldValue.mainProperty().removeListener(ecouteurMain);
                oldValue.cartesRecuesProperty().removeListener(ecouteurRecues);
                oldValue.cartesEnJeuProperty().removeListener(ecouteurJouées);
            }
            HBoxCartesJouées.getChildren().clear();
            HBoxCartesRecus.getChildren().clear();
            newValue.mainProperty().addListener(ecouteurMain);
            newValue.cartesRecuesProperty().addListener(ecouteurRecues);
            newValue.cartesEnJeuProperty().addListener(ecouteurJouées);
            initialiserMain(newValue);
            nbCartePioche.textProperty().bind(newValue.piocheProperty().sizeProperty().asString());
            nbCarteDefausse.textProperty().bind(newValue.defausseProperty().sizeProperty().asString());
            PiocheVBox.setStyle("-fx-cursor: hand;");
            PiocheVBox.setOnMouseClicked(event -> newValue.laPiocheAEteChoisie());
            defausseVBox.setStyle("-fx-cursor: hand;");
            defausseVBox.setOnMouseClicked(event -> newValue.laDefausseAEteChoisie());
        });

    }

    public void initialiserMain(IJoueur newValue) {
        listCarteMain.getChildren().clear();
        for (ICarte carte : newValue.mainProperty()) {
            VueCarte boutonCarte = new VueCarte(carte);
            listCarteMain.getChildren().add(boutonCarte);
        }
    }

    private ListChangeListener<ICarte> ecouteurMain = c -> {
        c.next();
        if (c.wasRemoved()) {
            VueCarte boutonCarte = trouverBoutonCarte(c.getRemoved().get(0));
            if (boutonCarte != null) {
                listCarteMain.getChildren().remove(boutonCarte);
            }
        }
        if (c.wasAdded()) {
            VueCarte boutonCarte = new VueCarte(c.getAddedSubList().get(0));
            listCarteMain.getChildren().add(boutonCarte);
        }
    };

    private ListChangeListener<ICarte> ecouteurRecues = c -> {
        c.next();
        if (c.wasAdded()) {
            VueCarte boutonCarte = new VueCarte(c.getAddedSubList().get(0));
            HBoxCartesRecus.getChildren().add(boutonCarte);
        }
    };

    private ListChangeListener<ICarte> ecouteurJouées = c -> {
        c.next();
        if (c.wasAdded()) {
            VueCarte boutonCarte = new VueCarte(c.getAddedSubList().get(0));
            boutonCarte.setOnAction(event -> {
                jeu.joueurCourantProperty().get().uneCarteEnJeuAEteChoisie(boutonCarte.getCarte().getNom());
            });
            HBoxCartesJouées.getChildren().add(boutonCarte);
        }
    };

    private VueCarte trouverBoutonCarte(ICarte carteATrouver) {
        for (Node n : listCarteMain.getChildren()) {
            VueCarte vueCarte = (VueCarte) n;
            if (vueCarte.getCarte().getNom().equals(carteATrouver.getNom())) {
                return (VueCarte) n;
            }
        }
        return null;
    }
}
