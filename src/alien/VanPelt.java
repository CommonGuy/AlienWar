package alien;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import planet.Move;

public class VanPelt extends Alien {

    private static final int LIFE = 0;
    private static final int STRENGTH = 1;
    private static final int DEFENSE = 2;
    private static final int VISION = 3;
    private static final int CLEVER = 4;

    // we all agreed before starting to move a certain direction if we're not
    // hunting or avoiding, helps avoid self-collisions... since we can't tell 
    // who we're fighting
    private static Move randomMove = Move.getRandom();

    @Override
    public void setAbilityPoints(float[] abilities) {
        abilities[LIFE] = 3.5f;
        abilities[STRENGTH] = 6f;
        abilities[VISION] = 0.5f;
    }

    @Override
    public Move move(char[][] fields) {
        int curHealth = this.getCurrentHp();
        List<Target> targets = new LinkedList<Target>();
        int vision = getVisionFieldsCount();
        boolean foundMe = false;
        for (int x = 0; x < fields.length; x++) {
            for (int y = 0; y < fields[x].length; y++) {
                switch (fields[x][y]) {
                case ' ' :
                    continue;
                case 'A' :
                    if (!foundMe && x == vision && y == vision) {
                        foundMe = true;
                        continue;
                    }
                    break;
                }
                targets.add(new Target(-vision + x, -vision + y, fields[x][y]));
            }
        }
        // if we're low health we need to move away from danger
        double desiredX = 0;
        double desiredY = 0;
        if (curHealth < this.getLifeLvl() * 7) {
            for (Target t : targets) {
                if (t.id == 'A') {
                    // closer aliens are more dangerous
                    desiredX -= vision / t.x;
                    desiredY -= vision / t.y;
                }
            }
        } else {
            // seek and destroy closest prey
            Collections.sort(targets);
            for (Target t : targets) {
                if (t.id != 'A') {
                    desiredX = t.x;
                    desiredY = t.y;
                    break;
                }
            }
        }
        desiredX = (int)Math.signum(desiredX);
        desiredY = (int)Math.signum(desiredY);
        for (Move m : Move.values()) {
            if (m.getXOffset() == desiredX && m.getYOffset() == desiredY) {
                return m;
            }
        }

        return randomMove;
    }

    @Override
    public boolean wantToFight(int[] enemyAbilities) {
        // we don't want to fight if we're hurt
        int curHealth = this.getCurrentHp();
        if (curHealth < this.getLifeLvl() * 4) {
            return false;
        }
        // determine if we're fighting prey
        int count = 0;
        int abilityMaxed = 0;
        int total = 0;
        for (int i = 0; i < enemyAbilities.length; i++) {
            total += enemyAbilities[i];
            if (enemyAbilities[i] == 11) {
                count++;
                abilityMaxed = i;
            }
        }
        // very likely to be prey, ignoring cows... they're dangerous
        if (abilityMaxed != STRENGTH && (count == 1 || total < 10)) {
            return true;
        }

        // else use a scoring system with tinkered constants
        double score = enemyAbilities[LIFE] * 4.0 
                + enemyAbilities[DEFENSE] * 0.5 
                + enemyAbilities[STRENGTH] * 5.0;

        double myScore = this.getDefenseLvl() * 0.5 +
                this.getStrengthLvl() * 5.0 +
                this.getCurrentHp() * 2.5;

        return myScore > score * 2;
    }



    private class Target implements Comparable<Target> {
        public int x, y;
        public char id;
        public int distanceSq;

        public Target(int x, int y, char id) {
            // adjust for known movement patterns
            switch(id) {
            case 'T' :
                x += Move.SOUTHWEST.getXOffset();
                y += Move.SOUTHWEST.getYOffset();
                break;
            case 'H' :
                x += Move.NORTHEAST.getXOffset();
                y += Move.NORTHEAST.getYOffset();
                break;
            }
            this.x = x;
            this.y = y;
            this.id = id;

            distanceSq = x * x + y * y;
        }

        @Override
        public int compareTo(Target other) {
            return distanceSq - other.distanceSq;
        }
    }

}