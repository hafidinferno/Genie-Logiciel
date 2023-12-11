package fr.univ_lyon1.info.m1.tp_test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CharManipulatorTest {
    private CharManipulator manipulator;

    @BeforeEach
    public void setUp() {
         manipulator = new CharManipulator();
    }

    @Test
    void testInvertOrder() {
        String invert1 = manipulator.invertOrder("ABCD");
        String invert2 =manipulator.invertOrder("3ABCD4");
        String vide = manipulator.invertOrder("");
        assertThat(invert1, is("DCBA"));
        assertThat(invert2, is("4DCBA3"));
        assertThat(vide, is(""));

    }
    @Test
    void testInvertCase() {
        String invert1 = manipulator.invertCase("ABcd");
        String invert2 = manipulator.invertCase("AbCd");
        String vide = manipulator.invertCase("");
        assertThat(invert1, is("abCD"));
        assertThat(invert2, is("aBcD"));
        assertThat(vide, is(""));

    }



    @Test
    void testRemovePattern() {
        String insert = manipulator.removePattern("", "");
        String insert2 = manipulator.removePattern("aabb","ab");
        String vide = manipulator.removePattern("coucou","ou");
        assertThat(insert2,is("ab"));
        assertThat(vide,is("cc"));
        assertThat(insert, is(""));
    }


}
