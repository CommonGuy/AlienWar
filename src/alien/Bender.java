package alien;

import planet.Move;
import java.util.HashMap;

/// The Bender "Kill All Humans" Alien
///
/// This alien operates on the principle that killing puny
/// Humans is easy and inflates your perceived strength,
/// which will intimidate other aliens into steering clear.
///
/// Whales and Eagles are also bendable. =)
public class Bender extends Alien {

    private final int LIF = 0, STR = 1, DEF = 2, VIS = 3, CLV = 4;
    protected static HashMap<Character, Integer> preyDesirability = new HashMap<Character, Integer>() {{
        put('A', -9);
        put('W',  5);
        put('C',  0); // Originally -2, but Cows don't attack
        put('T',  1);
        put('E',  2);
        put('H',  4);
    }};
    private static final int bananaTweak = -2;

    private static final HashMap<Character, Move> preyMovement = new HashMap<Character, Move>() {{
        put('W', Move.STAY);
        put('T', Move.SOUTHWEST);
        put('H', Move.NORTHEAST);
    }};

    public void setAbilityPoints(float[] abilities) {
        abilities[LIF] = 2.5f; // Shiny metal ass
        abilities[STR] = 7.5f; // Bending strength
        abilities[DEF] = 0;
        abilities[VIS] = 0;    // Binocular eyes
        abilities[CLV] = 0;
    }

    /// Looks for humans to intercept!
    ///
    /// Generally speaking, move either west or south
    /// unless you have found something interesting to
    /// attack:
    /// - For the prey whose movement is known, we add
    ///   their desirability to the index at which they
    ///   will be next turn.
    /// - For those we do not, we divide their desirability
    ///   equally among the squares that they may move to
    ///   next turn. Aliens get a bonus negative to their
    ///   existing square because of banana peels.
    public Move move(char[][] fields) {

        int vision = getVisionFieldsCount();
        // I am at fields[vision][vision]

        float[][] scoringMap = new float[fields.length][fields.length];
        for (int x = 0; x < fields.length; x++) {
            for (int y = 0; y < fields.length; y++) {

                // Ignore my square and blanks
                if (x == vision && y == vision ||
                    fields[x][y] == ' ') {
                    break;
                }

                // Check out the prey 8^]
                char organism = fields[x][y];
                float desirability = preyDesirability.get(organism);

                // If we know where it's going, score tiles accordingly...
                if (preyMovement.containsKey(organism)) {
                    Move preyMove = preyMovement.get(organism);
                    scoringMap[x + preyMove.getXOffset()][y + preyMove.getYOffset()] += desirability;
                }
                // ...otherwise, divide its score between its
                //    available moves...
                else {
                    for (int i = x - 1; i < x + 1; i++) {
                        for (int j = y - 1; j < y + 1; j++) {
                            scoringMap[i][j] += desirability / 9.;
                        }
                    }
                }
                // ...and if it is an alien, add a handicap
                //    for bananas and rocks.
                if (organism == 'A') {
                    scoringMap[x][y] += bananaTweak;
                }
            }
        }

        // Evaluate immediate surroundings 8^|
        //
        // +-----------+
        // |           |
        // |   # # #   |
        // |   # B #   |
        // |   # # #   |
        // |           |
        // +-----------+
        float bestScore = -10;
        int[] bestXY = new int[2];
        for (int x = vision - 1; x < vision + 1; x++) {
            for (int y = vision - 1; y < vision + 1; y++) {

                // Look for the best score, but if scores
                // are tied, try for most southwest high score
                if (scoringMap[x][y] > bestScore ||
                    scoringMap[x][y] == bestScore && (
                        x <= bestXY[0] && y > bestXY[1] ||
                        y >= bestXY[1] && x < bestXY[0])
                    ) {
                    bestScore = scoringMap[x][y];
                    bestXY[0] = x;
                    bestXY[1] = y;
                }
            }
        }

        // If something looks tasty, go for it :^F
        if (bestScore > 1) {
            for (Move m : Move.values()) {
                if (m.getXOffset() == bestXY[0] - vision &&
                    m.getYOffset() == bestXY[1] - vision) {
                    return m;
                }
            }
        }

        // If nothing looks good, do a lookahead to
        // the south, west, and southwest to guess
        // which is best. 8^[
        //
        // There is potential in recursively applying our
        // vision data with updated score rankings, but
        // that would be hard. :P
        float westScore = 0, southScore = 0, southWestScore = 0;
        for (int x = 0; x < vision; x++) {
            for (int y = vision - 1; y < vision + 1; y++) {
                // +-----------+
                // |           |
                // | # # #     |
                // | # # B     |
                // | # # #     |
                // |           |
                // +-----------+
                westScore += scoringMap[x][y] / (vision - x);
            }
        }
        for (int x = vision - 1; x < vision + 1; x++) {
            for (int y = vision; y < fields.length; y++) {
                // +-----------+
                // |           |
                // |           |
                // |   # B #   |
                // |   # # #   |
                // |   # # #   |
                // +-----------+
                southScore += scoringMap[x][y] / (y - vision);
            }
        }
        for (int x = 0; x < vision; x++) {
            for (int y = vision; y < fields.length; y++) {
                // +-----------+
                // |           |
                // |           |
                // | # # B     |
                // | # # #     |
                // | # # #     |
                // +-----------+
                southWestScore += scoringMap[x][y] / Math.sqrt((y - vision) + (vision - x));
            }
        }
        if (southScore > westScore && southScore > southWestScore) {
            return Move.SOUTH;
        }
        if (westScore > southScore && westScore > southWestScore) {
            return Move.WEST;
        }
        return Move.SOUTHWEST;
    }

    public boolean wantToFight(int[] enemyAbilities) {

        // Be afraid...
        if (enemyAbilities[STR] > 4) {

            // ...unless you suspect you are being lied to
            if (enemyAbilities[DEF] + enemyAbilities[VIS] > 4 * sumMyAbilities() / 5.) {

                // Enemy has more than expected attribute levels of unhelpful
                // abilities. Assume you're dealing with a clever bastard.
                return true;
            }

            return false;
        }
        return true;
    }

    int sumAbilities(int[] abilities){
        int sum = 0;
        for (int ability : abilities){
            sum += ability;
        }
        return sum;
    }

    int sumMyAbilities(){
        return sumAbilities(new int[]{
            getLifeLvl(),
            getStrengthLvl(),
            getDefenseLvl(),
            getVisionLvl(),
            getClevernessLvl()
        });
    }
}