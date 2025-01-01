package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

import java.util.List;

public class Echangeur extends Carte {
    public Echangeur() {
        super("Échangeur", Couleur.ROUGE, 1, 3, true, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        joueur.setArgent(joueur.getArgent() + this.getValeur());
        List<String> listCartes = joueur.getCartesEnJeu().stream().filter(carte -> carte.getCouleur() == Couleur.BLEU).map(Carte::getNom).toList();
        if (!listCartes.isEmpty()) {
            String choixJ = joueur.choisir("Choisir Carte a remettre dans pioche", listCartes, null, false);
            Carte carteChoix = joueur.getCartesEnJeu().retirer(choixJ);
            joueur.remettreDansPioche(carteChoix);
        }
        joueur.getCartesEnJeu().add(this);
    }
}
