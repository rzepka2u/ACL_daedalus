package model.enums;

import java.io.Serializable;

/**
 * Enumération pour les ordres de commandement
 */
public enum Ordre implements Serializable {
    ATTAQUE, DEPLACEMENT, OUVRIR, RAMASSER, BOIRE, COMPETENCE
}
