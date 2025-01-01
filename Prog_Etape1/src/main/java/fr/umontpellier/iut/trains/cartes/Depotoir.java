package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Depotoir extends Carte {
    public Depotoir() {
        super("Dépotoir", Couleur.ROUGE, 1, 5, true, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        joueur.setArgent(joueur.getArgent() + this.getValeur());
        joueur.getListEffets().add(EffetTour.DEPOTOIR);
        joueur.getCartesEnJeu().add(this);
    }
}
