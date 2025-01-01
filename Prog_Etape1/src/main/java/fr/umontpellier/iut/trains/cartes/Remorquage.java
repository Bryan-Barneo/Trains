package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

import java.util.ArrayList;
import java.util.List;

public class Remorquage extends Carte {
    public Remorquage() {
        super("Remorquage", Couleur.ROUGE, 0, 3, true, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        List<String> ListCarte = joueur.getDefausse().stream().map(Carte::getNom).toList();
        if (!ListCarte.isEmpty()) {
            String choixJoueur = joueur.choisir("Choisir carte train defausse", ListCarte, null, false);
            joueur.getMain().add(joueur.getDefausse().retirer(choixJoueur));
        } else throw new RuntimeException("Defausse ne contient pas de carte trains");
        joueur.getCartesEnJeu().add(this);
    }
}
