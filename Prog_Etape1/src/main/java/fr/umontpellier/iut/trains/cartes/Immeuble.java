package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Immeuble extends Carte {
    public Immeuble() {
        super("Immeuble", Couleur.JAUNE, 0, 5, false, 2);
    }

    @Override
    public void jouer(Joueur joueur) {
        joueur.PiocherFerailles(1);
        joueur.setScoreCourant(joueur.getScoreCourant() + 2);
    }
}
