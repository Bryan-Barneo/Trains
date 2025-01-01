package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

import java.util.List;

public class TrainPostal extends Carte {
    public TrainPostal() {
        super("Train postal", Couleur.BLEU, 1, 4, true, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        joueur.setArgent(joueur.getArgent() + this.getValeur());
        List<String> ListCarte = joueur.getMain().stream().map(Carte::getNom).toList();
        while (!ListCarte.isEmpty()) {
            String choixJoueur = joueur.choisir("Choisir Carte à Défausser", ListCarte, null, true);
            if (!choixJoueur.equals("")) {
                Carte carte = joueur.getMain().stream().filter(c -> c.getNom().equals(choixJoueur)).findFirst().get();
                joueur.getMain().remove(carte);
                joueur.getDefausse().add(carte);
                joueur.setArgent(joueur.getArgent() + 1);
            } else {
                break;
            }
        }
        joueur.getCartesEnJeu().add(this);
    }
}
