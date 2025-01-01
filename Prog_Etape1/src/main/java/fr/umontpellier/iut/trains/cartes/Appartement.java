package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

import java.util.List;

public class Appartement extends Carte {
    public Appartement() {
        super("Appartement", Couleur.JAUNE, 0, 3, false, 1);
    }

    @Override
    public void jouer(Joueur joueur) {
        joueur.PiocherFerailles(1);
        joueur.setScoreCourant(joueur.getScoreCourant() + 1);
    }
}
