package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

import java.util.List;

public class Depot extends Carte {
    public Depot() {
        super("Dépôt", Couleur.ROUGE, 1, 3, true, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        joueur.setArgent(joueur.getArgent() + this.getValeur());
        List<Carte> ListCarte = joueur.piocher(2);
        if (!ListCarte.isEmpty()){
            for (Carte carte : ListCarte) {
                joueur.ajouterCarteMain(carte);
            }
            int nbCarteDefausser = 0;
            List<String> listNomCarte = joueur.getMain().stream().map(Carte::getNom).toList();
            if (!listNomCarte.isEmpty()) {
                while (nbCarteDefausser < 2) {
                    String choix = joueur.choisir("Choisir cartes a défausser", listNomCarte, null, true);
                    if (choix != "") {
                        Carte carteDefausser = joueur.getMain().getCarte(choix);
                        joueur.defausser(carteDefausser);
                        nbCarteDefausser++;
                    }
                }
            }
        }
        joueur.getCartesEnJeu().add(this);
    }
}
