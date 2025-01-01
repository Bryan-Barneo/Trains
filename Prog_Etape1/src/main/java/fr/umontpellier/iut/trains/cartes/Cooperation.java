package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Cooperation extends Carte {
    public Cooperation() {
        super("Coopération", Couleur.VERT, 0, 5, false, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        joueur.setPointsRails(joueur.getPointsRails() + 1);
        joueur.PiocherFerailles(1);
        joueur.getListEffets().add(EffetTour.COOPERATION);
        joueur.getCartesEnJeu().add(this);
    }
}
