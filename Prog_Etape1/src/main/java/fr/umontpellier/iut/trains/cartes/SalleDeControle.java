package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

import java.util.List;

public class SalleDeControle extends Carte {
    public SalleDeControle() {
        super("Salle de contrôle", Couleur.ROUGE, 0, 7, true, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        List<Carte> cartes = joueur.piocher(3);
        if (!cartes.isEmpty()) {
            for (Carte c : cartes) {
                joueur.getMain().add(c);
            }
        }
        joueur.getCartesEnJeu().add(this);
    }
}
