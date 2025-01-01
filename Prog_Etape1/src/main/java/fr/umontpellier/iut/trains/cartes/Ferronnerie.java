package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Ferronnerie extends Carte {
    public Ferronnerie() {
        super("Ferronnerie", Couleur.ROUGE, 1, 4, true, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        joueur.setArgent(joueur.getArgent() + this.getValeur());
        joueur.getListEffets().add(EffetTour.FERRONERIE);
        joueur.getCartesEnJeu().add(this);
    }
}
