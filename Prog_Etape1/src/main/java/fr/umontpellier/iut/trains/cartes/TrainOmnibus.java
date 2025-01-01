package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class TrainOmnibus extends Carte {
    public TrainOmnibus() {
        super("Train omnibus", Couleur.BLEU, 1, 1, false, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        joueur.setArgent(joueur.getArgent() + this.getValeur());
        joueur.getCartesEnJeu().add(this);
    }
}
