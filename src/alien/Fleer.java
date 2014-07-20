package alien; import java.util.ArrayList;

import planet.Move;

public class Fleer extends Alien
{
    public void setAbilityPoints(float[] abilities) {
        abilities[0] = 1; //life
        abilities[1] = 0; //strength
        abilities[2] = 4; //defense
        abilities[3] = 4; //vision
        abilities[4] = 1; //cleverness
    }

    public Move move(char[][]fields) {
        int vision = getVisionFieldsCount(); //count of fields / middle
        char me = fields[vision][vision]; //middle of fields
        int leastDanger = Integer.MAX_VALUE;
        Move bestMove = Move.STAY;
        ArrayList<Integer> dangerOnSides = new ArrayList<Integer>();
        for (Move move : Move.values()) {
            int danger = 0;
            for (int i = 1; i <= vision; i++) { //loop through fields in specific direction
                int fieldX = vision + (i * move.getXOffset());
                int fieldY = vision + (i * move.getYOffset());
                if (fields[fieldX][fieldY] != ' ') {
                    danger++;
                }
            }
            if (danger < leastDanger) {
                bestMove = move;
                leastDanger = danger;
            }
            dangerOnSides.add(danger);
        }

        boolean noDanger = false;
        for (int i : dangerOnSides) {
           if (i == 0) {
              noDanger = true;
           }
           else { noDanger = false; break; }
        }

        if (noDanger) {
              // Hahhhhhhhh.......
              return Move.STAY;
        }

        int prev = -1;
        boolean same = false;
        for (int i : dangerOnSides) {
           if (prev == -1)
           { prev = i; continue; }
           if (i == prev) {
              same = true;
           }
           else { same = false; break; }

           prev = i;
        }

        if (same) {
              // PANIC
              return Move.getRandom();
        }

        return bestMove;
    }

    public boolean wantToFight(int[] enemyAbilities) {
        return false;
    }
}