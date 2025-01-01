package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Bouton;
import fr.umontpellier.iut.trains.Joueur;

import java.util.ArrayList;
import java.util.List;

public class PersonnelDeGare extends Carte {
    public PersonnelDeGare() {
        super("Personnel de gare", Couleur.ROUGE, 0, 2, true, 0);
    }

    @Override
    public void jouer(Joueur joueur) {
        List<String> ListChoix = new ArrayList<>();
        ListChoix.add("piocher");
        ListChoix.add("argent");
        ListChoix.add("ferraille");
        List<Bouton> ListBouton = joueur.ListChoixButton(ListChoix);

        switch (joueur.choisir("Choisir L'action Effectuer", ListChoix, ListBouton, false)) {
            case "piocher" -> {
                joueur.getMain().add(joueur.piocher());
                break;
            }
            case "argent" -> {
                joueur.setArgent(joueur.getArgent() + 1);
                break;
            }
            case "ferraille" -> {
                Carte c = joueur.getMain().retirer("Ferraille");
                joueur.getJeu().getReserve().get("Ferraille").add(c);
            }
        }
        joueur.getCartesEnJeu().add(this);
    }
}
