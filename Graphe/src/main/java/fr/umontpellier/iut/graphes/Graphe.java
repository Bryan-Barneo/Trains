package fr.umontpellier.iut.graphes;

import fr.umontpellier.iut.trains.Joueur;

import java.util.*;

/**
 * Graphe simple non-orienté pondéré représentant le plateau du jeu.
 * Pour simplifier, on supposera que le graphe sans sommets est le graphe vide.
 * Le poids de chaque sommet correspond au coût de pose d'un rail sur la tuile correspondante.
 * Les sommets sont indexés par des entiers (pas nécessairement consécutifs).
 */

public class Graphe {
    private final Set<Sommet> sommets;

    public Graphe(Set<Sommet> sommets) {
        this.sommets = sommets;
    }

    /**
     * Construit un graphe à n sommets 0..n-1 sans arêtes
     */
    public Graphe(int n) {
        sommets = new HashSet<Sommet>();
        for (int i = 0; i < n; i++) {
            Sommet.SommetBuilder s1 = new Sommet.SommetBuilder();
            s1.setIndice(i);
            Sommet sommet1 = s1.createSommet();
            sommets.add(sommet1);
        }
    }

    /**
     * Construit un graphe vide
     */
    public Graphe() {
        sommets = new HashSet<Sommet>();
    }

    /**
     * Construit un sous-graphe induit par un ensemble de sommets
     * sans modifier le graphe donné
     *
     * @param g le graphe à partir duquel on construit le sous-graphe
     * @param X les sommets à considérer (on peut supposer que X est inclus dans l'ensemble des sommets de g,
     *          même si en principe ce n'est pas obligatoire)
     */
    public Graphe(Graphe g, Set<Sommet> X) {
        this.sommets = new HashSet<Sommet>();
        for (Sommet s : g.sommets) {
            if (X.contains(s)) {
                this.sommets.add(new Sommet(s));
            }
        }
        for (Sommet s : this.sommets) {
            s.getVoisins().removeIf(voisin -> !X.contains(voisin));
        }
    }

    /*public Graphe(Graphe g) {
        this.sommets = g.getSommets();
    }*/

    public Graphe(Graphe g) {
        this.sommets = new HashSet<Sommet>(g.sommets);
        for (Sommet s : g.sommets) {
            this.sommets.add(new Sommet(s));
        }
    }

    /**
     * @return true si et seulement si la séquence d'entiers passée en paramètre
     * correspond à un graphe simple valide dont les degrés correspondent aux éléments de la liste.
     * Pré-requis : on peut supposer que la séquence est triée dans l'ordre croissant.
     */
    public static boolean sequenceEstGraphe(List<Integer> sequence) {
        int somme = 0;
        for (Integer i : sequence) {
            somme += i;
        }
        somme = somme / 2;
        return somme == sequence.size();
    }

    /**
     * @param g        le graphe source, qui ne doit pas être modifié
     * @param ensemble un ensemble de sommets
     *                 pré-requis : l'ensemble donné est inclus dans l'ensemble des sommets de {@code g}
     * @return un nouveau graph obtenu en fusionnant les sommets de l'ensemble donné.
     * On remplacera l'ensemble de sommets par un seul sommet qui aura comme indice
     * le minimum des indices des sommets de l'ensemble. Le surcout du nouveau sommet sera
     * la somme des surcouts des sommets fusionnés. Le nombre de points de victoire du nouveau sommet
     * sera la somme des nombres de points de victoire des sommets fusionnés.
     * L'ensemble de joueurs du nouveau sommet sera l'union des ensembles de joueurs des sommets fusionnés.
     */
    public static Graphe fusionnerEnsembleSommets(Graphe g, Set<Sommet> ensemble) {
        Graphe copieG = new Graphe(g);
        Sommet.SommetBuilder sommetFusionne = new Sommet.SommetBuilder();
        Sommet premierSommetList = ensemble.iterator().next();
        int sommeSurcout = premierSommetList.getSurcout();
        int pointsVictoire = premierSommetList.getNbPointsVictoire();
        Set<Integer> joueursSommetFusionne = new HashSet<>();


        for (Sommet s : ensemble) {
            if (s.getIndice() < premierSommetList.getIndice()) {
                premierSommetList = s;
            }
            sommeSurcout += s.getSurcout();
            pointsVictoire += s.getNbPointsVictoire();
            joueursSommetFusionne.addAll(s.getJoueurs());
            copieG.supprimerSommet(s);
        }

        sommetFusionne.setIndice(premierSommetList.getIndice());
        sommetFusionne.setSurcout(sommeSurcout);
        sommetFusionne.setNbPointsVictoire(pointsVictoire);
        sommetFusionne.setJoueurs(joueursSommetFusionne);

        Sommet sommetFusionneFinal = sommetFusionne.createSommet();
        for (Sommet s : ensemble) {
            for (Sommet voisinsS : s.getVoisins()) {
                if (copieG.getSommet(voisinsS.getIndice()) != null) {
                    sommetFusionneFinal.ajouterVoisin(voisinsS);
                }
            }
        }
        copieG.ajouterSommet(sommetFusionneFinal);
        return copieG;
    }

    /**
     * @param i un entier
     * @return le sommet d'indice {@code i} dans le graphe ou null si le sommet d'indice {@code i} n'existe pas dans this
     */
    public Sommet getSommet(int i) {
        for (Sommet s : sommets) {
            if (s.getIndice() == i) {
                return s;
            }
        }
        return null;
    }

    /**
     * @return l'ensemble des sommets du graphe
     */
    public Set<Sommet> getSommets() {
        return sommets;
    }

    /**
     * @return l'ordre du graphe, c'est-à-dire le nombre de sommets
     */
    public int getNbSommets() {
        return sommets.size();
    }

    /**
     * @return l'ensemble d'arêtes du graphe sous forme d'ensemble de paires de sommets
     */
    public Set<Set<Sommet>> getAretes() {
        Set<Set<Sommet>> arêtes = new HashSet<>();
        for (Sommet s : sommets) {
            for (Sommet s2 : s.getVoisins()) {
                arêtes.add(Set.of(s, s2));
            }
        }
        return arêtes;
    }

    /**
     * @return le nombre d'arêtes du graphe
     */
    public int getNbAretes() {
        Set<Set<Sommet>> arêtes = getAretes();
        return arêtes.size();
    }

    /**
     * Ajoute un sommet d'indice i au graphe s'il n'est pas déjà présent
     *
     * @param i l'entier correspondant à l'indice du sommet à ajouter dans le graphe
     */
    public boolean ajouterSommet(int i) {
        boolean ajout = true;
        for (Sommet s : sommets) {
            if (s.getIndice() == i) {
                ajout = false;
            }
        }
        if (ajout) {
            sommets.add(new Sommet.SommetBuilder().setIndice(i).createSommet());
        }
        return ajout;
    }

    /**
     * Ajoute un sommet au graphe s'il n'est pas déjà présent
     *
     * @param s le sommet à ajouter
     * @return true si le sommet a été ajouté, false sinon
     */
    public boolean ajouterSommet(Sommet s) {
        return sommets.add(s);
    }


    public void supprimerSommet(Sommet s) {
        sommets.remove(s);
        for (Sommet voisin : s.getVoisins()) {
            voisin.getVoisins().remove(s);
        }
    }

    /**
     * @param s le sommet dont on veut connaître le degré
     *          pré-requis : {@code s} est un sommet de this
     * @return le degré du sommet {@code s}
     */
    public int degre(Sommet s) {
        return s.getVoisins().size();
    }

    /**
     * @return true si et seulement si this est complet.
     */
    public boolean estComplet() {
        int n = sommets.size();
        int mComplet = (n * (n - 1)) / 2;
        if (mComplet == getNbAretes()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return true si et seulement si this est une chaîne. On considère que le graphe vide est une chaîne.
     */
    public boolean estChaine() {
        boolean estChaine = false;
        if (sommets.isEmpty()) {
            estChaine = true;
        } else if (estConnexe()) {
            if (degreMax() <= 2) {
                if (degre(getSommet(1)) == 1 && degre(getSommet(sommets.size() - 1)) == 1) {
                    estChaine = false;
                }
            }
        }
        return estChaine;
    }

    /**
     * @return true si et seulement si this est un cycle. On considère que le graphe vide n'est pas un cycle.
     */
    public boolean estCycle() {
        boolean estCycle = false;
        if (sommets.isEmpty()) {
            return estCycle;
        } else if (estConnexe()) {
            estCycle = true;
            for (Sommet s : sommets) {
                if (degre(s) != 2) {
                    estCycle = false;
                }
            }
        }
        return estCycle;
    }

    /**
     * @return true si et seulement si this est une forêt. On considère qu'un arbre est une forêt
     * et que le graphe vide est un arbre.
     */
    public boolean estForet() {
        return estConnexe() && !possedeUnCycle();
    }

    /**
     * @return true si et seulement si this a au moins un cycle. On considère que le graphe vide n'est pas un cycle.
     */
    public boolean possedeUnCycle() {
        boolean possedeUnCycle = false;
        if (sommets.isEmpty()) {
            return possedeUnCycle;
        } else{
            Graphe copie  = new Graphe(this);
            for (Sommet s: copie.sommets){
                for (Sommet voisin: s.getVoisins()){
                    supprimerArete(voisin,s);
                    if(copie.getEnsembleClassesConnexite().equals(getEnsembleClassesConnexite())){
                        return true;
                    }
                }
            }
        }
        return possedeUnCycle;
    }

    /**
     * @return true si et seulement si this a un isthme
     */
    public boolean possedeUnIsthme() {
        boolean possedeUnIsthme = false;
        Graphe copie = new Graphe(this);
        Set<Set<Sommet>> ListeArete = copie.getAretes();
        for (Set<Sommet> Arete : ListeArete) {
            ArrayList<Sommet> SommetArete = new ArrayList<>(Arete);
            copie.supprimerArete(SommetArete.get(0), SommetArete.get(1));
            if (!copie.estConnexe()) {
                possedeUnIsthme = true;
            }
            copie.ajouterArete(SommetArete.get(0), SommetArete.get(1));
        }
        return possedeUnIsthme;
    }

    public void ajouterArete(Sommet s, Sommet t) {
        if (sommets.contains(s) && sommets.contains(t)) {
            s.ajouterVoisin(t);
        }
    }

    public void supprimerArete(Sommet s, Sommet t) {
        if (sommets.contains(s) && sommets.contains(t)) {
            s.getVoisins().remove(t);
            t.getVoisins().remove(s);
        }
    }

    /**
     * @return une coloration gloutonne du graphe sous forme d'une Map d'ensemble indépendants de sommets.
     * L'ordre de coloration des sommets est suivant l'ordre décroissant des degrés des sommets
     * (si deux sommets ont le même degré, alors on les ordonne par indice croissant).
     */
    public Map<Integer, Set<Sommet>> getColorationGloutonne() {
        Map<Integer, Set<Sommet>> coloration = new HashMap<>();
        List<Sommet> listSommets = new ArrayList<>(this.sommets.stream().toList());

        listSommets.sort(new Comparator<Sommet>() {
            @Override
            public int compare(Sommet o1, Sommet o2) {
                if (degre(o1) == degre(o2)) {
                    return o1.getIndice() - o2.getIndice();
                } else {
                    return degre(o2) - degre(o1);
                }
            }
        });

        int couleurInt = 0;
        while (!listSommets.isEmpty()) {
            Sommet s = listSommets.get(0);
            Set<Sommet> couleur = new HashSet<>(listSommets);

            couleur.removeAll(s.getVoisins());
            listSommets.remove(s);

            if (couleurInt > 0) {
                for (Sommet DejaVu : coloration.get(couleurInt - 1)) {
                    listSommets.remove(DejaVu);
                    couleur.remove(DejaVu);
                }
            }

            if (!couleur.isEmpty()) {
                coloration.put(couleurInt, couleur);
            }

            couleurInt++;
        }

        return coloration;
    }

    /**
     * @param depart  - ensemble non-vide de sommets
     * @param arrivee
     * @return le surcout total minimal du parcours entre l'ensemble de depart et le sommet d'arrivée
     * pré-requis : l'ensemble de départ et le sommet d'arrivée sont inclus dans l'ensemble des sommets de this
     */
    public int getDistance(Set<Sommet> depart, Sommet arrivee) {
        int minValue = Integer.MAX_VALUE;
        // Itérer sur les entrées du HashMap
        for (Sommet sommet : depart) {
            if (getDistance(sommet,arrivee) < minValue) {
                minValue = getDistance(sommet,arrivee);
            }
        }
        return minValue;
    }

    /**
     * @return le surcout total minimal du parcours entre le sommet de depart et le sommet d'arrivée
     */
    public int getDistance(Sommet depart, Sommet arrivee) {
        Map<Sommet, Integer> distances = new HashMap<>();
        PriorityQueue<Sommet> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        Set<Sommet> DejaVu = new HashSet<>();

        for (Sommet sommet : sommets)
            distances.put(sommet, Integer.MAX_VALUE);

        distances.put(depart, 0);
        pq.add(depart);

        while (!pq.isEmpty()) {
            Sommet actuel = pq.poll();

            if (actuel.equals(arrivee))
                return distances.get(actuel);

            DejaVu.add(actuel);

            for (Sommet voisin : actuel.getVoisins()) {
                if (!DejaVu.contains(voisin)) {
                    int newDistance = distances.get(actuel) + voisin.getSurcout();

                    if (newDistance < distances.get(voisin)) {
                        distances.put(voisin, newDistance);
                        pq.add(voisin);
                    }
                }
            }
        }

        return Integer.MAX_VALUE;
    }

    private Sommet VoisinPlusProche(HashMap<Sommet,Integer> distance, Sommet depart){
        // Initialiser les variables pour stocker la clé avec la plus petite valeur
        Sommet PlusProche = null;
        int minValue = Integer.MAX_VALUE;
        // Itérer sur les entrées du HashMap
        for (Map.Entry<Sommet, Integer> sommet : distance.entrySet()) {
            if (depart.estVoisin(sommet.getKey())){
                if (sommet.getValue() < minValue) {
                    minValue = sommet.getValue();
                    PlusProche = sommet.getKey();
                }
            }
        }
        return PlusProche;
    }

    /**
     * @return l'ensemble des classes de connexité du graphe sous forme d'un ensemble d'ensembles de sommets.
     */
    public Set<Set<Sommet>> getEnsembleClassesConnexite() {
        Set<Set<Sommet>> ensembleClassesConnexite = new HashSet<>();
        if (sommets.isEmpty())
            return ensembleClassesConnexite;
        Set<Sommet> sommets = new HashSet<>(this.sommets);
        while (!sommets.isEmpty()) {
            Sommet v = sommets.iterator().next();
            Set<Sommet> classe = getClasseConnexite(v);
            sommets.removeAll(classe);
            ensembleClassesConnexite.add(classe);
        }
        return ensembleClassesConnexite;
    }

    /**
     * @param v un sommet du graphe this
     * @return la classe de connexité du sommet {@code v} sous forme d'un ensemble de sommets.
     */
    public Set<Sommet> getClasseConnexite(Sommet v) {
        if (!sommets.contains(v))
            return new HashSet<>();
        Set<Sommet> classe = new HashSet<>();
        calculerClasseConnexite(v, classe);
        return classe;
    }

    private void calculerClasseConnexite(Sommet v, Set<Sommet> dejaVus) {
        dejaVus.add(v);
        Set<Sommet> voisins = v.getVoisins();

        for (Sommet voisin : voisins) {
            if (dejaVus.add(voisin))
                calculerClasseConnexite(voisin, dejaVus);
        }
    }

    /**
     * @return true si et seulement si this est connexe.
     */
    public boolean estConnexe() {
        if (getEnsembleClassesConnexite().size() == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return le degré maximum des sommets du graphe
     */
    public int degreMax() {
        int max = 0;
        for (Sommet s : sommets) {
            if (degre(s) > max) {
                max = degre(s);
            }
        }
        return max;
    }

    private Map<Integer, Set<Sommet>> listLignePlateau() {
        Map<Integer, Set<Sommet>> listLigne = new HashMap<>();
        int compteur = 0;
        int ligne = 0;
        Set<Sommet> sommetsLigne = new HashSet<>();
        while (compteur < sommets.size()) {
            for (int i = 0; i < 10; i++) {
                Sommet sommet = getSommet(compteur + i);
                sommetsLigne.add(sommet);
            }
            compteur += 10;
            listLigne.put(ligne, sommetsLigne);
            sommetsLigne.clear();
            ligne++;

            for (int j = 0; j < 9; j++) {
                Sommet sommet = getSommet(compteur + j);
                sommetsLigne.add(sommet);
            }
            compteur += 9;
            listLigne.put(ligne, sommetsLigne);
            sommetsLigne.clear();
            ligne++;
        }
        return listLigne;
    }

    /**
     * @return une coloration propre optimale du graphe sous forme d'une Map d'ensemble indépendants de sommets.
     * Chaque classe de couleur est représentée par un entier (la clé de la Map).
     * Pré-requis : le graphe est issu du plateau du jeu Train (entre autres, il est planaire).
     */
    public Map<Integer, Set<Sommet>> getColorationPropreOptimale() {
        throw new RuntimeException("Méthode à implémenter");
    }

    /**
     * @return true si et seulement si this possède un sous-graphe complet d'ordre {@code k}
     */
    public boolean possedeSousGrapheComplet(int k) {
        throw new RuntimeException("Méthode à implémenter");
    }

    /**
     * @param g un graphe
     * @return true si et seulement si this possède un sous-graphe isomorphe à {@code g}
     */
    public boolean possedeSousGrapheIsomorphe(Graphe g) {
        throw new RuntimeException("Méthode à implémenter");
    }

    /**
     * @param s
     * @param t
     * @return un ensemble de sommets qui forme un ensemble critique de plus petite taille entre {@code s} et {@code t}
     */
    public Set<Sommet> getEnsembleCritique(Sommet s, Sommet t) {
        throw new RuntimeException("Méthode à implémenter");
    }
}