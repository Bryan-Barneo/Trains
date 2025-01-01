package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Ferraille extends Carte {
    public Ferraille() {
        super("Ferraille", Couleur.GRIS, 0, 0, false, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
    }
}
