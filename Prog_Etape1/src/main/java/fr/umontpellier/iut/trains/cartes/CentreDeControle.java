package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Bouton;
import fr.umontpellier.iut.trains.Joueur;

import java.util.List;
import java.util.Set;

public class CentreDeControle extends Carte {
    public CentreDeControle() {
        super("Centre de contrôle", Couleur.ROUGE, 0, 3, true, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        joueur.ajouterCarteMain(joueur.piocher());
        Set<String> nomCartes = joueur.getJeu().getListeNomsCartes();
        String choixJ = joueur.choisir("Choisir le nom d'une carte", nomCartes, null, false);
        Carte cartesPioche = joueur.piocher();
        if (cartesPioche != null) {
            if (cartesPioche.getNom().equals(choixJ)) {
                joueur.ajouterCarteMain(cartesPioche);
            } else {
                joueur.remettreDansPioche(cartesPioche);
            }
        }
        joueur.getCartesEnJeu().add(this);
    }
}
