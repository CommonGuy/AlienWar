package alien;

import planet.Move;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.*;

public class ChooseYourBattles extends Alien {
    private static final boolean DEBUG = false;
    private static final int LIFE = 0;
    private static final int STRENGTH = 1;
    private static final int DEFENCE = 2;
    private static final int VISION = 3;
    private static final int CLEVERNESS = 4;
    private static final Set<Character> prey = new HashSet<>();
    {
        Collections.addAll(prey, 'H', 'T', 'W', 'C', 'E');
    }

    public void setAbilityPoints(float[] abilities) {
        // Rounding promotes these to 4 and 7 - 11 points!
        abilities[LIFE] = 3.5f;
        abilities[STRENGTH] = 6.5f;
    }

    @Override
    public Move move(char[][] fields) {
        if (DEBUG) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 2 * getVisionFieldsCount() + 1; j++) {
                for (int i = 0; i < 2 * getVisionFieldsCount() + 1; i++) {
                    char chr = fields[i][j];
                    if (chr == ' ') chr = '.';
                    if (i == getVisionFieldsCount() && j == getVisionFieldsCount()) chr = 'X';
                    sb.append(chr);
                }
                sb.append('\n');
            }
            String view = sb.toString();
            System.out.println(this.toString());
            System.out.println(view);
        }

        Move bestMove = null;
        int bestEnemyDistance = Integer.MIN_VALUE;
        int bestPreyDistance = Integer.MAX_VALUE;

        for (Move tryMove: Move.values()) {
            int currentDistanceToEnemy = Integer.MAX_VALUE;
            int currentDistanceToPrey = Integer.MAX_VALUE;
            int x = getVisionFieldsCount() + tryMove.getXOffset();
            int y = getVisionFieldsCount() + tryMove.getYOffset();
            for (int i = 0; i < 2 * getVisionFieldsCount() + 1; i++) {
                for (int j = 0; j < 2 * getVisionFieldsCount() + 1; j++) {
                    char chr = fields[i][j];
                    if (chr == 'A' && (i != getVisionFieldsCount() || j != getVisionFieldsCount())) {
                        // Use L-infinity distance, but fll back to L-1 distance
                        int distance = max(abs(i - x), abs(j - y)) * 100 + abs(i -x) + abs(j - y);
                        if (distance < currentDistanceToEnemy) currentDistanceToEnemy = distance;
                    } else if (prey.contains(chr)) {
                        int distance = max(abs(i - x), abs(j - y)) * 100 + abs(i -x) + abs(j - y);
                        if (distance < currentDistanceToPrey) currentDistanceToPrey = distance;
                    }
                }
            }
            if (currentDistanceToEnemy > bestEnemyDistance
                    || (currentDistanceToEnemy == bestEnemyDistance && currentDistanceToPrey < bestPreyDistance)) { // Prefer to stay put
                bestMove = tryMove;
                bestEnemyDistance = currentDistanceToEnemy;
                bestPreyDistance = currentDistanceToPrey;
            }
        }

        if (DEBUG) {
            System.out.println("Going " + bestMove);
            System.out.println();
        }
        return bestMove;
    }

    @Override
    public boolean wantToFight(int[] enemyAbilities) {
        // Estimate whether likely to survive the encounter - are we at least "twice as hard" as our opponent
        return getCurrentHp() * (50.0 + getDefenseLvl()) / (enemyAbilities[STRENGTH])
                > 2.0 * (enemyAbilities[LIFE] * 5 + 10) * (50.0 + enemyAbilities[DEFENCE])/ (getStrengthLvl());
    }

    @Override
    public String toString() {
        return "ChooseYourBattles" + System.identityHashCode(this)
                + ": HP " + getCurrentHp()
                + ", LFE " + getLifeLvl()
                + ", STR " + getStrengthLvl()
                + ", DEF " + getDefenseLvl()
                + ", VIS " + getVisionLvl()
                + ", CLV " + getClevernessLvl();
    }
}