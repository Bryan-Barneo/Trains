package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;
import fr.umontpellier.iut.trains.plateau.*;

public enum EffetTour {

    COOPERATION, DEPOTOIR, FERRONERIE, PONTACIER, TRAINMATINAL, TUNNEL, VIADUC, VOIESOUTERRAINE;

    public void appliquerEffet(Joueur joueur, Tuile tuile) {
        switch (this) {
            case COOPERATION:
                EffetSurcout(joueur, tuile);
                break;
            case FERRONERIE:
                joueur.setArgent(joueur.getArgent() + 2);
                break;
            case PONTACIER, TUNNEL, VIADUC, VOIESOUTERRAINE:
                appliquerEffet(joueur, tuile);
                break;
        }
    }

    public int EffetSurcout(Joueur j, Tuile Tuile) {
        int Bonus = Tuile.getSurcout();
        if (this.equals(COOPERATION) && Tuile.getRails().size() > 1) {
            Bonus = Tuile.getRails().size();
            return Bonus;
        } else if (this.equals(VOIESOUTERRAINE)) {
            Bonus = j.surcoutTotal(Tuile);
            return Bonus;
        } else if (this.equals(VIADUC) && Tuile.getTypeTuile().equals(TypeTuile.VILLE)) {
            Bonus = Tuile.getNbGares();
            return Bonus;
        } else if (this.equals(TUNNEL) && Tuile.getTypeTuile().equals(TypeTuile.TERRAIN)) {
            TuileTerrain tuileTerrain = (TuileTerrain) Tuile;
            if (tuileTerrain.getTypeTerrain().equals(TypeTerrain.MONTAGNE)) {
                Bonus = 2;
                return Bonus;
            }
        } else if (this.equals(PONTACIER) && Tuile.getTypeTuile().equals(TypeTuile.TERRAIN)) {
            TuileTerrain tuileTerrain = (TuileTerrain) Tuile;
            if (tuileTerrain.getTypeTerrain().equals(TypeTerrain.FLEUVE)) {
                Bonus = 1;
                return Bonus;
            }
        }
        return 0;
    }
}
