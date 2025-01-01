package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class PassageEnGare extends Carte {
    public PassageEnGare() {
        super("Passage en gare", Couleur.ROUGE, 1, 3, true, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        joueur.setArgent(joueur.getArgent() + this.getValeur());
        Carte carte = joueur.piocher();
        if (carte != null) {
            joueur.getMain().add(carte);
        }
        joueur.getCartesEnJeu().add(this);
    }
}
