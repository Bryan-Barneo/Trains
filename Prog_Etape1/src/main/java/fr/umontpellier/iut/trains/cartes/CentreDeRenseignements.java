package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Bouton;
import fr.umontpellier.iut.trains.Joueur;

import java.util.ArrayList;
import java.util.List;

public class CentreDeRenseignements extends Carte {
    public CentreDeRenseignements() {
        super("Centre de renseignements", Couleur.ROUGE, 1, 4, true, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        joueur.setArgent(joueur.getArgent() + this.getValeur());
        List<Carte> ListCartes = joueur.piocher(4);
        if (!ListCartes.isEmpty()) {
            List<String> nomCartes = new ArrayList<>(ListCartes.stream().map(Carte::getNom).toList());
            List<Bouton> ListBouton = joueur.ListChoixButton(nomCartes);
            String nomCarteGarder = joueur.choisir("Choisir cartes a conserver", nomCartes, ListBouton, true);
            if (!nomCarteGarder.equals("")) {
                for (Carte c : ListCartes) {
                    if (c.getNom().equals(nomCarteGarder)) {
                        joueur.ajouterCarteMain(c);
                        nomCartes.remove(c.getNom());
                        ListCartes.remove(c);
                        break;
                    }
                }
            }

            while (!ListCartes.isEmpty()) {
                String carteDefausser = joueur.choisir("Choisir une carte a remettre dans la pioche", nomCartes, ListBouton, false);
                ListBouton = joueur.ListChoixButton(nomCartes);
                for (Carte c : ListCartes) {
                    if (c.getNom().equals(carteDefausser)) {
                        joueur.remettreDansPioche(c);
                        nomCartes.remove(c.getNom());
                        ListCartes.remove(c);
                        break;
                    }
                }
            }
        }
        joueur.getCartesEnJeu().add(this);
    }
}
