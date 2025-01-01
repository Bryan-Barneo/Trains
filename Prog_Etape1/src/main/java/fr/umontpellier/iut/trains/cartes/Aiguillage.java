package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

import java.util.ArrayList;
import java.util.List;

public class Aiguillage extends Carte {
    public Aiguillage() {
        super("Aiguillage", Couleur.ROUGE, 0, 5, true, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        List<Carte> cartes = joueur.piocher(2);
        if (!cartes.isEmpty()) {
            for (Carte carte : cartes) {
            joueur.ajouterCarteMain(carte);
        }
        }
        joueur.getCartesEnJeu().add(this);
    }
}
