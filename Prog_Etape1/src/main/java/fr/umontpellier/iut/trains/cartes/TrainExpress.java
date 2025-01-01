package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class TrainExpress extends Carte {
    public TrainExpress() {
        super("Train express", Couleur.BLEU, 2, 3, false, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        joueur.setArgent(joueur.getArgent() + this.getValeur());
        joueur.getCartesEnJeu().add(this);
    }
}
