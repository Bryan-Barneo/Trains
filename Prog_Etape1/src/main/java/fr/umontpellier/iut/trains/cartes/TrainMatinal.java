package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class TrainMatinal extends Carte {
    public TrainMatinal() {
        super("Train matinal", Couleur.BLEU, 2, 5, true, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        joueur.setArgent(joueur.getArgent() + this.getValeur());
        joueur.getListEffets().add(EffetTour.TRAINMATINAL);
        joueur.getCartesEnJeu().add(this);
    }
}
