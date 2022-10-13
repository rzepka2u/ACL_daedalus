import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Classe de test des fonctions du la classe EXEMPLE
 */
public class TestExemple {

    /**
     * Test verifiant qu'un exemple ne peut pas faire cela
     */
    @Test
    public void test_peutFaireCela() {
        // preparation des donnees

        // methodes testées

        // verifications des donnees
        assertEquals("Ceci devrait être comme cela", 1, 1);

    }


    /**
     * Test verifiant qu'un exemple peut faire cela
     */
    @Test
    public void test_nonCela() {
        // preparation des donnees

        // methodes testées

        // verifications des donnees
        assertEquals("Ceci devrait être comme cela", 1, 1);

    }

}
