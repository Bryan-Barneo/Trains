package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class GratteCiel extends Carte {
    public GratteCiel() {
        super("Gratte-Ciel", Couleur.JAUNE, 0, 8, false, 4);
    }

    @Override
    public void jouer(Joueur joueur) {
        joueur.PiocherFerailles(1);
        joueur.setScoreCourant(joueur.getScoreCourant() + 4);
    }
}