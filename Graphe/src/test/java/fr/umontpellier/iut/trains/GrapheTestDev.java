package fr.umontpellier.iut.trains;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fr.umontpellier.iut.graphes.Sommet;
import org.junit.Test;
import org.junit.jupiter.api.Timeout;

import fr.umontpellier.iut.graphes.Graphe;
import fr.umontpellier.iut.trains.plateau.Plateau;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GrapheTestDev {
    @Test
    public void testColorationGloutonne() {
        Graphe grapheTest = new Graphe(5);
        Sommet a = grapheTest.getSommet(0);
        Sommet b = grapheTest.getSommet(1);
        Sommet c = grapheTest.getSommet(2);
        Sommet d = grapheTest.getSommet(3);
        Sommet e = grapheTest.getSommet(4);

        grapheTest.ajouterArete(a, b);
        grapheTest.ajouterArete(b, c);
        grapheTest.ajouterArete(b, e);
        grapheTest.ajouterArete(b, d);
        grapheTest.ajouterArete(e, d);
        grapheTest.ajouterArete(d, c);

        Map<Integer, Set<Sommet>> coloration = grapheTest.getColorationGloutonne();
        assertEquals(3, coloration.size());
    }

    @Test
    public void testColorationOptimale() {
        Jeu jeu = new Jeu(new String[]{"Batman", "Robin"}, new String[]{}, Plateau.TOKYO);
        Graphe grapheTest = jeu.getGraphe();

        Map<Integer, Set<Sommet>> coloration = grapheTest.getColorationPropreOptimale();
        for (int i = 0; i < coloration.keySet().size(); i++) {
            System.out.println("Couleur " + i + " : " + coloration.get(i).size() + " sommets");
            System.out.println(coloration.get(i));
        }
        assertEquals(3, coloration.size());
    }

    @Test
    public void testfusionnerEnsembleSommets() {
        Graphe grapheTest = new Graphe(5);
        Sommet a = grapheTest.getSommet(0);
        Sommet b = grapheTest.getSommet(1);
        Sommet c = grapheTest.getSommet(2);
        Sommet d = grapheTest.getSommet(3);
        Sommet e = grapheTest.getSommet(4);

        grapheTest.ajouterArete(a, b);
        grapheTest.ajouterArete(b, c);
        grapheTest.ajouterArete(b, e);
        grapheTest.ajouterArete(b, d);
        grapheTest.ajouterArete(e, d);
        grapheTest.ajouterArete(d, c);

        Set<Sommet> sommetsFusionnes = Set.of(a, b, c);
        Graphe grapheFusionneeSommet = Graphe.fusionnerEnsembleSommets(grapheTest, sommetsFusionnes);
        assertEquals(3, grapheFusionneeSommet.getNbSommets());
    }

    @Test
    public void testCopieG() {
        Graphe grapheTest = new Graphe(5);
        Sommet a = grapheTest.getSommet(0);
        Sommet b = grapheTest.getSommet(1);
        Sommet c = grapheTest.getSommet(2);
        Sommet d = grapheTest.getSommet(3);
        Sommet e = grapheTest.getSommet(4);
        grapheTest.ajouterArete(a, b);
        grapheTest.ajouterArete(b, c);
        grapheTest.ajouterArete(b, e);
        grapheTest.ajouterArete(b, d);
        grapheTest.ajouterArete(e, d);
        grapheTest.ajouterArete(d, c);

        for (Sommet s : grapheTest.getSommets()) {
            System.out.println(s);
        }
        Graphe grapheCopie = new Graphe(grapheTest);
        for (Sommet s : grapheCopie.getSommets()) {
            System.out.println(s);
        }
    }
}
