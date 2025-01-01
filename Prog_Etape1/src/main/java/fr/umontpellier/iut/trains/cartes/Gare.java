package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;
import fr.umontpellier.iut.trains.plateau.Tuile;
import fr.umontpellier.iut.trains.plateau.TypeTuile;

import java.util.ArrayList;
import java.util.List;

public class Gare extends Carte {
    public Gare() {
        super("Gare", Couleur.VIOLET, 0, 3, false, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        List<String> choixPossibles = new ArrayList<>();
        for(Tuile tuile:joueur.getJeu().getTuiles()){
            if(tuile.getTypeTuile() == TypeTuile.VILLE){
                if(tuile.getNbGares()< tuile.getNbGaresMax()){
                    choixPossibles.add("TUILE:"+ joueur.getJeu().getTuiles().indexOf(tuile)); //Index de la tuile pour invoquer choisir ensuite
                }
            }
        }
        String choix = joueur.choisir("Choix d'emplacement gare", choixPossibles, null, false);
        String tuileCorrespondante = choix.split(":")[1];
        Tuile tuile = joueur.getJeu().getTuile(Integer.parseInt(tuileCorrespondante));
        tuile.ajouterGare();
        joueur.PiocherFerailles(1);
        joueur.getCartesEnJeu().add(this);
    }
}
