package alien; import planet.Move;

public class Gamer extends Alien {
        public void setAbilityPoints(float[] abilities) {
            abilities[0] = 1; //life
            abilities[1] = 1; //strength
            abilities[2] = 8; //defense

            // Not paying attention to surroundings
            abilities[3] = 0; //vision
            abilities[4] = 0; //cleverness
        }

        public Move move(char[][] fields) {
            return Move.STAY;
        }

        public boolean wantToFight(int[] enemyAbilities) {
            return false;
        }
}