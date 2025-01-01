package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Bouton;
import fr.umontpellier.iut.trains.Joueur;

import java.util.ArrayList;
import java.util.List;

public class CabineDuConducteur extends Carte {
    public CabineDuConducteur() {
        super("Cabine du conducteur", Couleur.ROUGE, 0, 2, true, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        int nbCarteDefausse = 0;
        List<String> choixDefausse = joueur.getMain().stream().map(Carte::getNom).toList();
        if (!choixDefausse.isEmpty()) {
            while (!joueur.getMain().isEmpty()) {
                String choix = joueur.choisir("Choisir une cartes a défausser ou Passer pour arrêter", choixDefausse, null, true);
                if (choix == "") {
                    break;
                } else {
                    Carte cartesDefausser = joueur.getMain().stream().filter(carte -> carte.getNom().equals(choix)).findFirst().orElse(null);
                    joueur.defausser(cartesDefausser);
                    nbCarteDefausse++;
                }
            }
            List<Carte> ListCartePioche = joueur.piocher(nbCarteDefausse);
            for (Carte c : ListCartePioche) {
                joueur.ajouterCarteMain(c);
            }
        }
        joueur.getCartesEnJeu().add(this);
    }
}
