package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

import java.util.List;

public class HorairesTemporaires extends Carte {
    public HorairesTemporaires() {
        super("Horaires temporaires", Couleur.ROUGE, 0, 5, true, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        List<Carte> nouvelleMain = joueur.getPioche();
        int nbCartesTrain = 0;
        while (nbCartesTrain < 2 && !nouvelleMain.isEmpty()) {
            Carte carte = nouvelleMain.remove(0);
            if (carte.getCouleur() == Couleur.BLEU) {
                joueur.getMain().add(carte);
                nbCartesTrain++;
            } else joueur.remettreDansDefausse(carte);
        }
        joueur.getCartesEnJeu().add(this);
    }
}
