package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

import java.util.List;

public class ParcDAttractions extends Carte {
    public ParcDAttractions() {
        super("Parc d'attractions", Couleur.ROUGE, 1, 4, true, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        joueur.setArgent(joueur.getArgent() + this.getValeur());
        List<String> ListChoix = joueur.getCartesEnJeu().stream().map(Carte::getNom).toList();
        while (true) {
            String choixDuJ = joueur.choisir("Choisir Carte Trains ", ListChoix, null, false);
            if (choixDuJ != "") {
                int Valeur = joueur.getCartesEnJeu().getCarte(choixDuJ).getValeur();
                joueur.setArgent(joueur.getArgent() + Valeur);
                break;
            }
            if (choixDuJ == "") {
                break;
            }
        }
        joueur.getCartesEnJeu().add(this);
    }
}
