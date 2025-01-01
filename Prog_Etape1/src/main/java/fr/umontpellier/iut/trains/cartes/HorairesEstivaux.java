package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Bouton;
import fr.umontpellier.iut.trains.Joueur;

import java.util.List;

public class HorairesEstivaux extends Carte {
    public HorairesEstivaux() {
        super("Horaires estivaux", Couleur.ROUGE, 0, 3, true, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        List<String> listChoix = List.of("oui", "non");
        List<Bouton> listBoutonChoix = joueur.ListChoixButton(listChoix);
        String choix = joueur.choisir("Voulez-vous écarter cette carte pour gagner 3$ ?", listChoix, listBoutonChoix, false);
        if (choix.equals("oui")) {
            joueur.getJeu().ecarteCarte(this);
            joueur.setArgent(joueur.getArgent() + 3);
        } else {
            joueur.getCartesEnJeu().add(this);
        }
    }
}
