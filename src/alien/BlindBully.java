package alien;
import planet.Move;
import java.util.Random;

public class BlindBully extends Alien {

    private final int LIFE = 0;
    private final int STRENGTH = 1;
    private final int DEFENSE = 2;
    private final int VISION = 3;
    private final int CLEVERNESS = 4;

    private Random rand = new Random();

    @Override
    public void setAbilityPoints(float[] abilities) {
        abilities[LIFE] = 6;
        abilities[STRENGTH] = 2;
        abilities[DEFENSE] = 2;
        abilities[VISION] = 0;
        abilities[CLEVERNESS] = 0;
    }

    @Override
    public Move move(char[][] fields) {
        // Go west! To meet interesting people, and kill them
        switch (rand.nextInt(3)) {
            case 0:
                return Move.NORTHWEST;
            case 1:
                return Move.SOUTHWEST;
            default:
                return Move.WEST;
        }
    }

    @Override
    public boolean wantToFight(int[] enemyAbilities) {
        int myFightRating = getLifeLvl() + getStrengthLvl() + getDefenseLvl();
        int enemyFightRating = enemyAbilities[LIFE] + enemyAbilities[STRENGTH] + enemyAbilities[DEFENSE];
        return myFightRating >= enemyFightRating;
    }

}