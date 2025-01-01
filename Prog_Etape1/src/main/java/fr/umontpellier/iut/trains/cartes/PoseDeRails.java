package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class PoseDeRails extends Carte {
    public PoseDeRails() {
        super("Pose de rails", Couleur.VERT, 0, 3, false, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        joueur.setPointsRails(joueur.getPointsRails() + 1);
        joueur.PiocherFerailles(1);
        joueur.getCartesEnJeu().add(this);
    }
}
