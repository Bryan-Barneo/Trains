package fr.umontpellier.iut.trains.plateau;

/**
 * Classe représentant une tuile plaine, fleuve ou montagne.
 */
public class TuileTerrain extends Tuile {
    /**
     * Type de terrain de la tuile ({@code PLAINE}, {@code FLEUVE} ou {@code MONTAGNE})
     */
    private TypeTerrain type;

    public TuileTerrain(TypeTerrain type) {
        super(TypeTuile.TERRAIN);
        this.type = type;
        /*Définition des surcouts*/
        if(type == TypeTerrain.FLEUVE){
            setSurcout(1);
        } else if (type == TypeTerrain.MONTAGNE) {
            setSurcout(2);
        }
    }

    public TypeTerrain getTypeTerrain(){return type;}
}
