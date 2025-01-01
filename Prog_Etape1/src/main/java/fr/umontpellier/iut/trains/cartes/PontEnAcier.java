package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class PontEnAcier extends Carte {
    public PontEnAcier() {
        super("Pont en acier", Couleur.VERT, 0, 4, false, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        joueur.setPointsRails(joueur.getPointsRails() + 1);
        joueur.PiocherFerailles(1);
        joueur.getListEffets().add(EffetTour.PONTACIER);
        joueur.getCartesEnJeu().add(this);
    }
}
