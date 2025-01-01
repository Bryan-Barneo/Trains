package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Bouton;
import fr.umontpellier.iut.trains.Joueur;

import java.util.List;

public class AtelierDeMaintenance extends Carte {
    public AtelierDeMaintenance() {
        super("Atelier de maintenance", Couleur.ROUGE, 0, 5, true, 0);
    }


    @Override
    public void jouer(Joueur joueur) {
        List<String> deckCartesTrain = joueur.getMain().stream().filter(carte -> carte.getCouleur() == Couleur.BLEU).map(Carte::getNom).toList();
        List<Bouton> choixBouton = joueur.ListChoixButton(deckCartesTrain);
        String réponse = "";
        if (!deckCartesTrain.isEmpty()) {
            réponse = joueur.choisir("Choisir une carte", deckCartesTrain, choixBouton, false);
            Carte carteRéserve = joueur.getJeu().prendreDansLaReserve(réponse);
            if (carteRéserve != null) {
                joueur.ajouterCarteReçues(carteRéserve);
            }
        }
        joueur.getCartesEnJeu().add(this);
    }
}
