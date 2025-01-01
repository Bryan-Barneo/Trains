package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Bouton;
import fr.umontpellier.iut.trains.Joueur;

import java.util.ArrayList;
import java.util.List;

public class FeuDeSignalisation extends Carte {
    public FeuDeSignalisation() {
        super("Feu de signalisation", Couleur.ROUGE, 0, 2, true, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        joueur.ajouterCarteMain(joueur.piocher());
        Carte carte = joueur.piocher();
        if (carte != null) {
            List<String> listChoix = new ArrayList<>();
            listChoix.add("oui");
            listChoix.add("non");
            List<Bouton> listBoutonChoix = joueur.ListChoixButton(listChoix);
            String choix = joueur.choisir("Voulez-vous défausser la carte ", listChoix, listBoutonChoix, false);
            if (choix.equals("oui")) {
                joueur.remettreDansDefausse(carte);
            } else {
                joueur.remettreDansPioche(carte);
            }
        }
        joueur.getCartesEnJeu().add(this);
    }
}
