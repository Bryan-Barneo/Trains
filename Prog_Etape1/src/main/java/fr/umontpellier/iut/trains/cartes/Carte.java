package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public abstract class Carte {
    private final String nom;
    private Couleur couleur;
    private int valeur;
    private int cout;
    private boolean estAction;
    private int nbPoints;

    /**
     * Constructeur simple
     * <p>
     * Important : La classe Carte est actuellement très incomplète. Vous devrez
     * ajouter des attributs et des méthodes et donc probablement définir au moins
     * un autre constructeur plus complet. Les sous-classes de Cartes qui vous sont
     * fournies font appel à ce constructeur simple mais au fur et à mesure que vous
     * les compléterez, elles devront utiliser les autres constructeurs de Carte. Si
     * vous n'utilisez plus ce constructeur, vous pouvez le supprimer.
     *
     * @param nom
     */
    public Carte(String nom) {
        this.nom = nom;
    }

    public Carte(String nom, Couleur couleur, int valeur, int cout, boolean estAction, int nbPoints) {
        this.nom = nom;
        this.couleur = couleur;
        this.valeur = valeur;
        this.cout = cout;
        this.estAction = estAction;
        this.nbPoints = nbPoints;
    }

    public String getNom() {
        return nom;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public int getCout() {
        return cout;
    }

    public int getValeur() {
        return valeur;
    }

    public boolean getEstAction() {
        return estAction;
    }

    public int getNbPoints() {
        return nbPoints;
    }

    /**
     * Cette fonction est exécutée lorsqu'un joueur joue la carte pendant son tour.
     * Toutes les cartes ont une méthode jouer, mais elle ne fait rien par défaut.
     *
     * @param joueur le joueur qui joue la carte
     */
    public void jouer(Joueur joueur) {
    }

        @Override
        public String toString () {
            return nom;
        }
    }
