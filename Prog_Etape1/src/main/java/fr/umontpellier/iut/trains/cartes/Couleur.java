package fr.umontpellier.iut.trains.cartes;

public enum Couleur {

    /**
     * Représente la couleur d'une Carte : jaune, rouge ...
     * En plus de donner une liste énumérative des couleurs possibles,
     * cette enumération doit permettre à la méthode toString d'une Carte de réaliser un affichage en couleur.
     */
    ROUGE("\u001B[31m"),
    BLEU("\u001B[34m"),
    VERT("\u001B[32m"),
    VIOLET("\u001B[35m"),
    JAUNE("\u001B[33m"),
    GRIS("\u001B[37m"),
    ;

    private String codeCouleur;

    private Couleur(String a) {
        this.codeCouleur = a;
    }

    public String getCode() {
        return this.codeCouleur;
    }
}
