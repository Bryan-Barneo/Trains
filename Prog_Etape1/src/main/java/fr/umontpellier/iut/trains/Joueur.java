package fr.umontpellier.iut.trains;

import java.util.*;

import fr.umontpellier.iut.trains.cartes.*;
import fr.umontpellier.iut.trains.plateau.Tuile;
import fr.umontpellier.iut.trains.plateau.TypeTuile;

public class Joueur {
    /**
     * Le jeu auquel le joueur est rattaché
     */
    private Jeu jeu;
    /**
     * Nom du joueur (pour les affichages console et UI)
     */
    private String nom;
    /**
     * Quantité d'argent que le joueur a (remis à zéro entre les tours)
     */
    private int argent;
    /**
     * Nombre de points rails dont le joueur dispose. Ces points sont obtenus en
     * jouant les cartes RAIL (vertes) et remis à zéro entre les tours
     */
    private int pointsRails;
    /**
     * Nombre de jetons rails disponibles (non placés sur le plateau)
     */
    private int nbJetonsRails;
    /**
     * Liste des cartes en main
     */
    private ListeDeCartes main;
    /**
     * Liste des cartes dans la pioche (le début de la liste correspond au haut de
     * la pile)
     */
    private ListeDeCartes pioche;
    /**
     * Liste de cartes dans la défausse
     */
    private ListeDeCartes defausse;
    /**
     * Liste des cartes en jeu (cartes jouées par le joueur pendant le tour)
     */
    private ListeDeCartes cartesEnJeu;
    /**
     * Liste des cartes reçues pendant le tour
     */
    private ListeDeCartes cartesRecues;
    /**
     * Couleur du joueur (utilisé par l'interface graphique)
     */
    private CouleurJoueur couleur;

    /**
     *Liste des Effet que possède le joueur pendant le tour
     */
    private ArrayList<EffetTour> listEffets;

    /**
     * Score courant du joueur obtenu par les effets de cartes jouées
     */
    private int scoreCourant;

    public Joueur(Jeu jeu, String nom, CouleurJoueur couleur) {
        this.jeu = jeu;
        this.nom = nom;
        this.couleur = couleur;
        argent = 0;
        pointsRails = 0;
        nbJetonsRails = 20;
        main = new ListeDeCartes();
        defausse = new ListeDeCartes();
        pioche = new ListeDeCartes();
        cartesEnJeu = new ListeDeCartes();
        cartesRecues = new ListeDeCartes();
        listEffets = new ArrayList<EffetTour>();
        scoreCourant = 0;

        // créer 7 Train omnibus (non disponibles dans la réserve)
        pioche.addAll(FabriqueListeDeCartes.creerListeDeCartes("Train omnibus", 7));
        // prendre 2 Pose de rails de la réserve
        for (int i = 0; i < 2; i++) {
            pioche.add(jeu.prendreDansLaReserve("Pose de rails"));
        }
        // prendre 1 Gare de la réserve
        pioche.add(jeu.prendreDansLaReserve("Gare"));

        // mélanger la pioche
        pioche.melanger();
        // Piocher 5 cartes en main
        // Remarque : on peut aussi appeler piocherEnMain(5) si la méthode est écrite
        for (int i = 0; i < 5; i++) {
            main.add(pioche.remove(0));
        }

    }

    public ListeDeCartes getPioche() {
        return pioche;
    }

    public Jeu getJeu() {
        return jeu;
    }

    public String getNom() {
        return nom;
    }

    public CouleurJoueur getCouleur() {
        return couleur;
    }

    public ListeDeCartes getMain() {
        return main;
    }

    public ListeDeCartes getCartesRecues() {
        return cartesRecues;
    }

    public ListeDeCartes getCartesEnJeu() {
        return cartesEnJeu;
    }

    public ArrayList<EffetTour> getListEffets() {
        return listEffets;
    }

    public int getPointsRails() {
        return pointsRails;
    }

    public int getArgent() {
        return argent;
    }

    public int getNbJetonsRails() {
        return nbJetonsRails;
    }

    public ListeDeCartes getDefausse() {
        return this.defausse;
    }

    public int getScoreCourant() {
        return scoreCourant;
    }

    public void setPointsRails(int i) {
        this.pointsRails = i;
    }

    public void setNbJetonsRails(int i) {
        this.nbJetonsRails = i;
    }

    public void setArgent(int i) {
        this.argent = i;
    }

    public void setListEffets(ArrayList<EffetTour> listEffets) {
        this.listEffets = listEffets;
    }

    public void setScoreCourant(int scoreCourant) {
        this.scoreCourant = scoreCourant;
    }

    /**
     * Renvoie le score total du joueur
     * <p>
     * Le score total est la somme des points obtenus par les effets suivants :
     * <ul>
     * <li>points de rails (villes et lieux éloignés sur lesquels le joueur a posé
     * un rail)
     * <li>points des cartes possédées par le joueur (cartes VICTOIRE jaunes)
     * <li>score courant du joueur (points marqués en jouant des cartes pendant la
     * partie p.ex. Train de tourisme)
     * </ul>
     *
     * @return le score total du joueur
     */
    public int getScoreTotal() {
        // À FAIRE
        int score = 0;
        score += scoreCourant; //score marqué par les cartes
        ArrayList<ListeDeCartes> pileCarteJoueur = new ArrayList<ListeDeCartes>();
        pileCarteJoueur.add(pioche);
        pileCarteJoueur.add(main);
        pileCarteJoueur.add(defausse);
        for (ListeDeCartes pile : pileCarteJoueur) { //score des cartes victoires possédées
            for(Carte carte:pile){
                if(carte.getCouleur() == Couleur.JAUNE){
                    score += carte.getNbPoints();
                }
            }
        }
        for(Tuile tuile:jeu.getTuiles()){ //points bonus ville ou lieu éloigné
            if(tuile.getTypeTuile() == TypeTuile.VILLE){
               if (tuile.hasRail(this)){
                   switch (tuile.getNbGares()){
                       case 1:
                           score+=2;
                           break;
                       case 2:
                           score+=4;
                           break;
                       case 3:
                           score+=8;
                           break;
                   }
               }
            }
            if(tuile.getTypeTuile() == TypeTuile.ETOILE){
                if (tuile.hasRail(this)){
                    score+= tuile.getValeur();
                }
            }
        }
        return score;
    }

    /**
     * Retire et renvoie la première carte de la pioche.
     * <p>
     * Si la pioche est vide, la méthode commence par mélanger toute la défausse
     * dans la pioche.
     *
     * @return la carte piochée ou {@code null} si aucune carte disponible
     */
    public Carte piocher() {
        if (pioche.isEmpty() && defausse.isEmpty()) {
            return null;
        } else {
            if (pioche.isEmpty()) {
                pioche.addAll(defausse);
                defausse.removeAll(pioche);
                pioche.melanger();
            }
            return pioche.remove(0);
        }
    }

    public void remettreDansPioche(Carte c) {
        this.pioche.add(0, c);
    }

    public void PiocherFerailles(int Compteur) {
        if (!listEffets.contains(EffetTour.DEPOTOIR) && !listEffets.contains(EffetTour.COOPERATION)) {
            for (int i = 0; i < Compteur; i++) {
                Carte Ferraille = getJeu().prendreDansLaReserve("Ferraille");
                ajouterCarteReçues(Ferraille);
            }
        }
    }

    /**
     * Retire et renvoie les {@code n} premières cartes de la pioche.
     * <p>
     * Si à un moment il faut encore piocher des cartes et que la pioche est vide,
     * la défausse est mélangée et toutes ses cartes sont déplacées dans la pioche.
     * S'il n'y a plus de cartes à piocher la méthode s'interrompre et les cartes qui
     * ont pu être piochées sont renvoyées.
     *
     * @param n nombre de cartes à piocher
     * @return une liste des cartes piochées (la liste peut contenir moins de n
     * éléments si pas assez de cartes disponibles dans la pioche et la
     * défausse)
     */
    public List<Carte> piocher(int n) {
        List<Carte> cartesPiochees = new ArrayList<Carte>();
        int i = 0;
        while (pioche != null && i < n) {
            cartesPiochees.add(piocher());
            i++;
        }
        return cartesPiochees;
    }

    public void defausser(Carte carte) {

        if (this.main.contains(carte)) {
            this.main.retirer(carte.getNom());
            this.defausse.add(carte);
        } else {
            try {
                throw new Exception("Erreur : La carte n'est pas dans votre main");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void remettreDansDefausse(Carte c) {
        this.defausse.add(c);
    }

    /**
     * Ajoute une carte à la main du joueur
     *
     * @param carte à ajouter à la main du joueur
     */
    public void ajouterCarteMain(Carte carte) {
        main.add(carte);
    }

    public void ajouterCarteReçues(Carte carte) {
        this.cartesRecues.add(carte);
    }

    public List<Bouton> ListChoixButton(List<String> listNom) {
        List<Bouton> ListButton = new ArrayList<Bouton>();
        for (String nom : listNom) {
            Bouton b1 = new Bouton(nom);
            ListButton.add(b1);
        }
        return ListButton;
    }

    /**
     * Joue un tour complet du joueur
     * <p>
     * Le tour du joueur se déroule en plusieurs étapes :
     * <ol>
     * <li>Initialisation
     * <p>
     * Dans ce jeu il n'y a rien de particulier à faire en début de tour à part un
     * éventuel affichage dans le log.
     *
     * <li>Boucle principale
     * <p>
     * C'est le cœur de la fonction. Tant que le tour du joueur n'est pas terminé,
     * il faut préparer la liste de tous les choix valides que le joueur peut faire
     * (jouer des cartes, poser des rails, acheter des cartes, etc.), puis demander
     * au joueur de choisir une action (en appelant la méthode {@code choisir}).
     * <p>
     * Ensuite, en fonction du choix du joueur il faut exécuter l'action demandée et
     * recommencer la boucle si le tour n'est pas terminé.
     * <p>
     * Le tour se termine lorsque le joueur décide de passer (il choisit {@code ""})
     * ou lorsqu'il exécute une action qui termine automatiquement le tour (par
     * exemple s'il choisit de recycler toutes ses cartes Ferraille en début de
     * tour)
     *
     * <li>Finalisation
     * <p>
     * Actions à exécuter à la fin du tour : réinitialiser les attributs
     * du joueur qui sont spécifiques au tour (argent, rails, etc.), défausser
     * toutes les
     * cartes, piocher 5 nouvelles cartes en main, etc.
     * </ol>
     */
    public void jouerTour() {
        // Initialisation
        jeu.log("<div class=\"tour\">Tour de " + toLog() + "</div>");
        // À FAIRE: compléter l'initialisation du tour si nécessaire (mais possiblement
        // rien de spécial à faire)
        List<String> cartesSpeciales = new ArrayList<>();
        cartesSpeciales.add("Appartement");
        cartesSpeciales.add("Immeuble");
        cartesSpeciales.add("GratteCiel");
        cartesSpeciales.add("Ferraille");
        boolean finTour = false;
        // Boucle principale
        while (!finTour) {
            List<String> choixPossibles = new ArrayList<>();
            // À FAIRE: préparer la liste des choix possibles
            for (Carte carte : main) {
                if (!cartesSpeciales.contains(carte.getNom())) { //on s'assure de pas pouvoir jouer des cartes victoires ou ferailles
                    choixPossibles.add(carte.getNom());
                }
            }
            for (String nomCarte : jeu.getReserve().keySet()) {
                // ajoute les noms des cartes dans la réserve préfixés de "ACHAT:"
                // Seulement si le joueur a assez d'argent pour acheter au moins 1 carte et que la carte n'est pas une Ferraille
                if (!nomCarte.equals("Ferraille")) {
                    ListeDeCartes test = jeu.getReserve().get(nomCarte);
                    Carte testfils = test.get(0);
                    if (argent >= testfils.getCout()) {
                        choixPossibles.add("ACHAT:" + nomCarte);
                    }
                }
            }
            if (pointsRails != 0) { //Si le joueur a des points rails à poser
                for (Tuile tuile : jeu.getTuiles()) { // parcours des tuiles du plateau
                    if (tuile.hasRail(this)) { // recherche des tuiles où le joueur à déjà un rail
                        for (Tuile voisin : tuile.getVoisines()) { //parcours des voisins qui sont jouable et qui non pas déjà un rail du joueur
                            if (!voisin.isInjouable() && !voisin.hasRail(this)) {
                                choixPossibles.add("TUILE:" + jeu.getTuiles().indexOf(voisin));
                            }
                        }
                    }
                }
            }

            // Choix de l'action à réaliser
            String choix = choisir("", choixPossibles, null, true);

            // À FAIRE: exécuter l'action demandée par le joueur
            if (choix.startsWith("ACHAT:")) {
                // prendre une carte dans la réserve
                String nomCarte = choix.split(":")[1];
                Carte carte = jeu.prendreDansLaReserve(nomCarte);
                argent -= carte.getCout();
                if (carte != null) {
                    if (this.listEffets.contains(EffetTour.TRAINMATINAL)) {
                        List<String> choixDuJ = List.of("oui", "non");
                        String choixJ = choisir("Voulez-vous placer la carte " + carte.getNom() + "Sur le deck ?", choixDuJ, null, false);
                        if (choixJ.equals("oui")) {
                            log("Met au dessus du deck " + carte);
                            remettreDansPioche(carte);
                        } else {
                            log("Reçoit " + carte); // affichage dans le log
                            cartesRecues.add(carte);
                        }
                    } else {
                        log("Reçoit " + carte); // affichage dans le log
                        cartesRecues.add(carte);
                    }
                }
            } else if (choix.equals("")) {
                // terminer le tour+.
                finTour = true;
            } else if (choix.startsWith("TUILE:")) {
                String tuileChoisie = choix.split(":")[1];
                poserRail(tuileChoisie);
            } else {
                // jouer une carte de la main
                Carte carte = main.retirer(choix);
                log("Joue " + carte); // affichage dans le log
                carte.jouer(this); // exécuter l'action de la carte
            }
        }
        // Finalisation
        // À FAIRE: compléter la finalisation du tour
        // défausser toutes les cartes
        defausse.addAll(main);
        main.clear();
        defausse.addAll(cartesRecues);
        cartesRecues.clear();
        defausse.addAll(cartesEnJeu);
        cartesEnJeu.clear();
        argent = 0;
        pointsRails = 0;
        main.addAll(piocher(5));
    }

    //---------------------------------------------------------------------------

    /**
     * Attend une entrée de la part du joueur (au clavier ou sur la websocket) et
     * renvoie le choix du joueur.
     * <p>
     * Cette méthode lit les entrées du jeu ({@code Jeu.lireligne()}) jusqu'à ce
     * qu'un choix valide (un élément de {@code choix} ou la valeur d'un élément de
     * {@code boutons} ou éventuellement la chaîne vide si l'utilisateur est
     * autorisé à passer) soit reçu.
     * Lorsqu'un choix valide est obtenu, il est renvoyé par la fonction.
     * <p>
     * Exemple d'utilisation pour demander à un joueur de répondre à une question
     * par "oui" ou "non" :
     * <p>
     *
     * <pre>{@code
     * List<String> choix = Arrays.asList("oui", "non");
     * String input = choisir("Voulez-vous faire ceci ?", choix, null, false);
     * }</pre>
     * <p>
     * Si par contre on voulait proposer les réponses à l'aide de boutons, on
     * pourrait utiliser :
     *
     * <pre>{@code
     * List<String> boutons = Arrays.asList(new Bouton("Oui !", "oui"), new Bouton("Non !", "non"));
     * String input = choisir("Voulez-vous faire ceci ?", null, boutons, false);
     * }</pre>
     * <p>
     * (ici le premier bouton a le label "Oui !" et envoie la String "oui" s'il est
     * cliqué, le second a le label "Non !" et envoie la String "non" lorsqu'il est
     * cliqué)
     *
     * <p>
     * <b>Remarque :</b> Normalement, si le paramètre {@code peutPasser} est
     * {@code false} le choix
     * {@code ""} n'est pas valide. Cependant s'il n'y a aucun choix proposé (les
     * listes {@code choix} et {@code boutons} sont vides ou {@code null}), le choix
     * {@code ""} est accepté pour éviter un blocage.
     *
     * @param instruction message à afficher à l'écran pour indiquer au joueur la
     *                    nature du choix qui est attendu
     * @param choix       une collection de chaînes de caractères correspondant aux
     *                    choix valides attendus du joueur (ou {@code null})
     * @param boutons     une liste d'objets de type {@code Bouton} définis par deux
     *                    chaînes de caractères (label, valeur) correspondant aux
     *                    choix valides attendus du joueur qui doivent être
     *                    représentés par des boutons sur l'interface graphique (le
     *                    label est affiché sur le bouton, la valeur est ce qui est
     *                    envoyé au jeu quand le bouton est cliqué) ou {@code null}
     * @param peutPasser  booléen indiquant si le joueur a le droit de passer sans
     *                    faire de choix. S'il est autorisé à passer, c'est la
     *                    chaîne de caractères vide ({@code ""}) qui signifie qu'il
     *                    désire passer.
     * @return le choix de l'utilisateur (un élement de {@code choix}, ou la valeur
     * d'un élément de {@code boutons} ou la chaîne vide)
     */
    public String choisir(
            String instruction,
            Collection<String> choix,
            List<Bouton> boutons,
            boolean peutPasser) {
        if (choix == null)
            choix = new ArrayList<>();
        if (boutons == null)
            boutons = new ArrayList<>();

        HashSet<String> choixDistincts = new HashSet<>(choix);
        choixDistincts.addAll(boutons.stream().map(Bouton::valeur).toList());
        if (peutPasser || choixDistincts.isEmpty()) {
            // si le joueur a le droit de passer ou s'il n'existe aucun choix valide, on
            // ajoute "" à la liste des choix possibles
            choixDistincts.add("");
        }

        String entree;
        log(instruction);
        // Lit l'entrée de l'utilisateur jusqu'à obtenir un choix valide
        while (true) {
            jeu.prompt(instruction, boutons, peutPasser);
            entree = jeu.lireLigne();
            // si une réponse valide est obtenue, elle est renvoyée
            if (choixDistincts.contains(entree)) {
                return entree;
            }
        }
    }

    public void poserRail(String tuileChoisie) {
        if (jeu.isPremierTour() && getNbJetonsRails() >= 20) {
            List<String> choixPossibles = new ArrayList<>();
            for (Tuile tuile : jeu.getTuiles()) {
                if (!tuile.isInjouable1erTour() && tuile.estVide()) {
                    choixPossibles.add("TUILE:" + jeu.getTuiles().indexOf(tuile)); //Index de la tuile pour invoquer choisir ensuite
                }
            }
            log("<div class=\"tour\">Choix départ de " + toLog() + "</div>");
            String choix = choisir(String.format("%s choisi son départ", this.nom), choixPossibles, null, false);
            String tuileCorrespondante = choix.split(":")[1];
            Tuile tuile = jeu.getTuile(Integer.parseInt(tuileCorrespondante));
            tuile.ajouterRail(this);
        } else {
            Tuile tuileCorrespondante = jeu.getTuile(Integer.parseInt(tuileChoisie));
            int SurcoutTuile = surcoutTotal(tuileCorrespondante);
            if (!listEffets.isEmpty()) {
                for (EffetTour effet : listEffets) {
                    int Bonus = effet.EffetSurcout(this, tuileCorrespondante);
                    Bonus -= SurcoutTuile;
                }
            }
            if (SurcoutTuile <= argent && getPointsRails() != 0) {
                argent = argent - surcoutTotal(tuileCorrespondante);
                tuileCorrespondante.ajouterRail(this);
                if (this.listEffets.contains(EffetTour.FERRONERIE)) {
                    EffetTour.FERRONERIE.appliquerEffet(this, tuileCorrespondante);
                }
                pointsRails--;
            }
        }
    }

    public int surcoutTotal(Tuile t) {
        int surcout = 0;
        surcout += t.getSurcout(); //surcout lié à la tuile
        if (t.getTypeTuile() == TypeTuile.VILLE) { //surcout nb gares
            surcout += t.getNbGares();
        }
        surcout += t.getRails().size(); //surcout nb joueurs adverses
        return surcout;
    }

    /**
     * Ajoute un message dans le log du jeu
     *
     * @param message message à ajouter dans le log
     */
    public void log(String message) {
        jeu.log(message);
    }

    @Override
    public String toString() {
        // Vous pouvez modifier cette fonction comme bon vous semble pour afficher
        // d'autres informations si nécessaire
        StringJoiner joiner = new StringJoiner("\n");
        joiner.add(String.format("=== %s (%d pts) ===", nom, getScoreTotal()));
        joiner.add(String.format("  Argent: %d  Rails: %d", argent, pointsRails));
        joiner.add("  Cartes en jeu: " + cartesEnJeu);
        joiner.add("  Cartes reçues: " + cartesRecues);
        joiner.add("  Cartes en main: " + main);
        return joiner.toString();
    }

    /**
     * @return une représentation du joueur pour l'affichage dans le log du jeu
     */
    public String toLog() {
        return String.format("<span class=\"joueur %s\">%s</span>", couleur.toString(), nom);
    }

    /**
     * @return une représentation du joueur sous la forme d'un dictionnaire de
     * valeurs sérialisables (qui sera converti en JSON pour l'envoyer à
     * l'interface graphique)
     */
    Map<String, Object> dataMap() {
        return Map.ofEntries(
                Map.entry("nom", nom),
                Map.entry("couleur", couleur),
                Map.entry("scoreTotal", getScoreTotal()),
                Map.entry("argent", argent),
                Map.entry("rails", pointsRails),
                Map.entry("nbJetonsRails", nbJetonsRails),
                Map.entry("main", main.dataMap()),
                Map.entry("defausse", defausse.dataMap()),
                Map.entry("cartesEnJeu", cartesEnJeu.dataMap()),
                Map.entry("cartesRecues", cartesRecues.dataMap()),
                Map.entry("pioche", pioche.dataMap()),
                Map.entry("actif", jeu.getJoueurCourant() == this));
    }
}
