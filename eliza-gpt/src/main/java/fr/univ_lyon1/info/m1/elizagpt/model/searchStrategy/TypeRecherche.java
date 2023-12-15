package fr.univ_lyon1.info.m1.elizagpt.model.searchStrategy;

import fr.univ_lyon1.info.m1.elizagpt.model.Dao;
import fr.univ_lyon1.info.m1.elizagpt.model.DataMessage;
import fr.univ_lyon1.info.m1.elizagpt.model.SearchFunction;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * La classe permet de connaitre le type de recherche actuelle
 * grâce à la fonction toString. De plus, les classes filles
 * implémenteront les différentes stratégies de recherche.
 */
public abstract class TypeRecherche implements SearchFunction {
    private final Dao dao;

    /**
     * Constructeur de la classe.
     */
    public TypeRecherche() {
        dao = Dao.getInstance();
    }

    /**
     * Fonction qui permet de moduler le texte initial
     * de la recherche afin qu'il corresponde à la stratégie
     * actuellement présente.
     * @param text Chaîne de caractères écrite par l'utilisateur.
     * @return Chaîne de caractères qui sera utilisée lors de la recherche.
     */
    public abstract String getTextForStrategy(String text);

    /**
     * Recherche dans notre base de données les messages qui
     * correspondent à la chaîne de caractères passée en paramètre.
     * La recherche varie en fonction de la stratégie présente.
     * @param text Chaîne de caractères utilisée lors de la recherche.
     * @return Liste de messages qui correspondent à la recherche.
     */
    public ArrayList<DataMessage> search(final String text) {
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(text, Pattern.CASE_INSENSITIVE);
        ArrayList<DataMessage> found = new ArrayList<>();
        ArrayList<DataMessage> messages = dao.getAllMessage();
        for (DataMessage tuple : messages) {
            String message = tuple.getText();
            matcher = pattern.matcher(message);
            if (matcher.find()) {
                // Can delete it right now, we're iterating over the list.
                found.add(tuple);
            }
        }
        return found;
    }

    /**
     * Fonction permettant d'obtenir sous forme de chaîne de caractères
     * le nom de la classe.
     * @return Le nom de la classe sous forme de chaîne de caractères.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    protected Dao getDao() {
        return dao;
    }

}
