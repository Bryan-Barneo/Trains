package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class TrainDeTourisme extends Carte {
    public TrainDeTourisme() {
        super("Train de tourisme", Couleur.BLEU, 1, 4, true, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        joueur.setArgent(joueur.getArgent() + this.getValeur());
        joueur.getCartesEnJeu().add(this);
        joueur.setScoreCourant(joueur.getScoreCourant() + 1);
    }
}
