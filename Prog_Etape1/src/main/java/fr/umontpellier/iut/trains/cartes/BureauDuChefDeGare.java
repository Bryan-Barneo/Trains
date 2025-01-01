package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Bouton;
import fr.umontpellier.iut.trains.Joueur;

import java.util.List;

public class BureauDuChefDeGare extends Carte {
    public BureauDuChefDeGare() {
        super("Bureau du chef de gare", Couleur.ROUGE, 0, 4, true, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        List<String> deckCartesAction = joueur.getMain().stream().filter(Carte::getEstAction).map(Carte::getNom).toList();
        List<Bouton> choixBouton = joueur.ListChoixButton(deckCartesAction);
        String réponse = "";
        if (!deckCartesAction.isEmpty()) {
            do {
                réponse = joueur.choisir("Choisir une carte", deckCartesAction, choixBouton, false);
                Carte carteRéserve = joueur.getMain().getCarte(réponse);
                if (carteRéserve != null) {
                    carteRéserve.jouer(joueur);
                    joueur.getCartesEnJeu().retirer(réponse);
                    if (carteRéserve.getNom().equals("Horaires estivaux")) {
                        joueur.getJeu().getCartesEcartees().retirer("Horaires estivaux");
                        joueur.getJeu().getReserve().get(carteRéserve.getNom()).add(carteRéserve);
                    }
                }
            } while (réponse.isEmpty());
        }
        joueur.getMain().retirer(this.getNom());
        joueur.getCartesEnJeu().add(this);
    }
}
