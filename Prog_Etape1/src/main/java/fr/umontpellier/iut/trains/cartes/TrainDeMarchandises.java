package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

import java.util.List;

public class TrainDeMarchandises extends Carte {
    public TrainDeMarchandises() {
        super("Train de marchandises", Couleur.BLEU, 1, 4, true, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        joueur.setArgent(joueur.getArgent() + this.getValeur());
        List<String> ListCarte = joueur.getMain().stream().filter(carte -> carte.getCouleur() == Couleur.GRIS).map(Carte::getNom).toList();
        while (!joueur.getMain().isEmpty()) {
            String choixJoueur = joueur.choisir("Choisir Carte Ferraille à remettre en réserve", ListCarte, null, true);
            if (choixJoueur.equals("")) {
                break;
            } else {
                Carte carte = joueur.getMain().stream().filter(c -> c.getNom().equals(choixJoueur)).findFirst().get();
                joueur.getMain().remove(carte);
                joueur.getJeu().getReserve().get(choixJoueur).add(carte);
                joueur.setArgent(joueur.getArgent() + 1);
            }
        }
        joueur.getCartesEnJeu().add(this);
    }
}
