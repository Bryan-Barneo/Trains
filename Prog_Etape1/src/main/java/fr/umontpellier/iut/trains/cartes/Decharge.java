package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

import java.util.List;

public class Decharge extends Carte {
    public Decharge() {
        super("Décharge", Couleur.ROUGE, 4, 2, false, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        List<Carte> cartesFerrailles = joueur.getMain().stream().filter(carte -> carte.getCouleur() == Couleur.GRIS).toList();
        if (cartesFerrailles.isEmpty()) {
            joueur.log("Vous n'avez pas de cartes Ferrailles en main");
            return;
        }
        for (Carte c : cartesFerrailles) {
            joueur.getMain().retirer(c.getNom());
            joueur.getJeu().getReserve().get("Ferraille").add(c);
        }
        joueur.getCartesEnJeu().add(this);
    }
}
