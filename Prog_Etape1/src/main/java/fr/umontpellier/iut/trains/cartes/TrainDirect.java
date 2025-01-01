package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class TrainDirect extends Carte {
    public TrainDirect() {
        super("Train direct", Couleur.BLEU, 3, 6, false, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        joueur.setArgent(joueur.getArgent() + this.getValeur());
        joueur.getCartesEnJeu().add(this);
    }
}
