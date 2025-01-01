package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

import java.util.ArrayList;
import java.util.List;

public class UsineDeWagons extends Carte {
    public UsineDeWagons() {
        super("Usine de wagons", Couleur.ROUGE, 0, 5, true, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        String choixJ = joueur.choisir("Choisir une carte trains", joueur.getMain().stream().filter(carte -> carte.getCouleur() == Couleur.BLEU).map(Carte::getNom).toList(), null, false);
        if (!choixJ.equals("")) {
            Carte carte = joueur.getMain().retirer(choixJ);
            joueur.getJeu().ecarteCarte(carte);
            List<String> cartes = new ArrayList<>();
            List<ListeDeCartes> Deckcartes = joueur.getJeu().getReserve().values().stream().toList();
            if (!Deckcartes.isEmpty()) {
                for (ListeDeCartes deck : Deckcartes) {
                    for (Carte carteDeck : deck) {
                        if (carteDeck.getCouleur() == Couleur.BLEU && carteDeck.getCout() <= carte.getCout() + 3) {
                            cartes.add(carteDeck.getNom());
                            break;
                        }
                        break;
                    }
                }
            }
            List<String> CartesDeck = new ArrayList<>();
            if (!cartes.isEmpty()) {
                for (String Str : cartes) {
                    String choix = "ACHAT:";
                    choix += Str;
                    CartesDeck.add(choix);
                }
            }
            if (!CartesDeck.isEmpty()) {
                String choix = joueur.choisir("Choisir une carte trains dans le deck", CartesDeck, null, false);
                String choixStr = choix.split(":")[1];
                Carte carteDeck = joueur.getJeu().getReserve().get(choixStr).retirer(choixStr);
                joueur.getMain().add(carteDeck);
            }
        }
        joueur.getCartesEnJeu().add(this);
    }
}
