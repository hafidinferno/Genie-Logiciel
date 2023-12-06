package fr.univ_lyon1.info.m1.elizagpt.model;

/**
 * La classe permet de connaitre le type de recherche actuelle
 * grâce à la fonction toString.
 */
public class TypeRecherche {

    /**
     * Constructeur de la classe.
     */
    public TypeRecherche() {
    }

    /**
     * Fonction permettant d'obtenir sous forme de chaîne de charactères
     * le nom de la classe.
     * @return Le nom de la classe sous forme de chaîne de charactère.
     */
    @Override
    public String toString() {
        String className = this.getClass().getSimpleName();
        return className;
    }
}
