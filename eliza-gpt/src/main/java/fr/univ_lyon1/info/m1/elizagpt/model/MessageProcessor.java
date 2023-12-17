package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Logic to process a message (and probably reply to it).
 */
public class MessageProcessor {
    private final Random random = new Random();
    /**
     * Normalise le texte en enlevant les espace inutile et
     * en ajoutant un point à la fin.
     * @param text Message de l'utilisateur.
     * @return normalized text.
     */
    public String normalize(final String text) {
        return text.replaceAll("\\s+", " ")
                .replaceAll("^\\s+", "")
                .replaceAll("\\s+$", "")
                .replaceAll("[^\\.!?:]$", "$0.");
    }

    /**
     * Information à propos de la conjugaison des verbes.
     */
    public static class Verb {
        private final String firstSingular;
        private final String secondPlural;

        public String getFirstSingular() {
            return firstSingular;
        }

        public String getSecondPlural() {
            return secondPlural;
        }

        Verb(final String firstSingular, final String secondPlural) {
            this.firstSingular = firstSingular;
            this.secondPlural = secondPlural;
        }
    }

    /**
     * Liste de verbes du troisième groupe et à la première personne
     * du singulier et leur à la deuxième personne du pluriel.
     */
    protected static final List<Verb> VERBS = Arrays.asList(
            new Verb("suis", "êtes"),
            new Verb("vais", "allez"),
            new Verb("dis", "dites"),
            new Verb("ai", "avez"),
            new Verb("fais", "faites"),
            new Verb("sais", "savez"),
            new Verb("dois", "devez"),
            new Verb("peux", "pouvez"),
            new Verb("pense", "pensez"));


    /**
     * Transforme les phrases de la première personne du
     * singulier à la deuxième personne du pluriel.
     * @param text Message envoyé par l'utilisateur
     * @return Phrase à la seconde personne du pluriel.
     */
    public String firstToSecondPerson(final String text) {
        String processedText = text
                .replaceAll("[Jj]e ([a-z]*)e ", "vous $1ez ");
        for (Verb v : VERBS) {
            processedText = processedText.replaceAll(
                    "[Jj]e " + v.getFirstSingular(),
                    "vous " + v.getSecondPlural());
        }
        processedText = processedText
                .replaceAll("[Jj]e ([a-z]*)s ", "vous $1ssez ")
                .replace("mon ", "votre ")
                .replace("ma ", "votre ")
                .replace("mes ", "vos ")
                .replace("moi", "vous");
        return processedText;
    }

    /**
     *  Récupère aléatoirement un élément dans la liste.
     */
    public <T> T pickRandom(final T[] array) {
        return array[random.nextInt(array.length)];
    }
}
