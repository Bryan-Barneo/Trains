package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

import java.util.List;

public class TGV extends Carte {
    public TGV() {
        super("TGV", Couleur.BLEU, 1, 2, true, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        joueur.setArgent(joueur.getArgent() + this.getValeur());
        List<Carte> ListCarte = joueur.getCartesEnJeu();
        if (!ListCarte.isEmpty()) {
            for (Carte c : ListCarte) {
                if (c.getNom().equals("Train omnibus")) {
                    joueur.setArgent(joueur.getArgent() + 1);
                    break;
                }
            }
        }
        joueur.getCartesEnJeu().add(this);
    }
}
