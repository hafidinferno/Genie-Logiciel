package fr.univ_lyon1.info.m1.tp_test;

import java.util.Objects;

public class CharManipulator implements ICharManipulator {

    @Override
    public String invertOrder(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    @Override
    public String invertCase(String s) {
        StringBuilder result = new StringBuilder();

        for (char c : s.toCharArray()) {
            if (Character.isUpperCase(c)) {
                result.append(Character.toLowerCase(c));
            } else {
                result.append(Character.toUpperCase(c));
            }
        }

        return result.toString();
    }

    @Override
    public String removePattern(String s, String p) {
        return s.replace(p, "");
    }


}
