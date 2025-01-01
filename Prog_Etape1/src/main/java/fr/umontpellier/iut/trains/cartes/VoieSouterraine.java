package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class VoieSouterraine extends Carte {
    public VoieSouterraine() {
        super("Voie souterraine", Couleur.VERT, 0, 7, false, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        joueur.setPointsRails(joueur.getPointsRails() + 1);
        joueur.PiocherFerailles(1);
        joueur.getListEffets().add(EffetTour.VOIESOUTERRAINE);
        joueur.getCartesEnJeu().add(this);
    }
}
