package alien;

import planet.Move;

public class Rogue extends Alien {

    private int threatPresent = 0;
    private int turnNorth = 0;

    public void setAbilityPoints(float[] abilities) {
        abilities[0] = 3;
        abilities[1] = 6;
        abilities[2] = 0;
        abilities[3] = 1;
        abilities[4] = 0;
    }

    public Move move(char[][] fields) {
        int vision = getVisionFieldsCount();
        char me = fields[vision][vision];
        int humanPresent = 0;            
        //This way, if there is no alien near, the current threat will not trigger attacking
        int isThereCurrThreat = 0;
        Move bestMove = Move.STAY;
        for (Move move : Move.values()) {
            for (int i = 1; i <= vision; i++) {
                int fieldX = vision + (i * move.getXOffset());
                int fieldY = vision + (i * move.getYOffset());
                if (fields[fieldX][fieldY] == 'A') {
                    isThereCurrThreat = 1;
                }

                if (fields[fieldX][fieldY] == 'T') {
                    humanPresent = 1;
                    //Turtles are basically dumb humans, right?
                }

                if (fields[fieldX][fieldY] == 'H') {
                    humanPresent = 1;
                }
            }

            //Alway follow that filthy human
            if (humanPresent == 1) {
                bestMove = move;
            }

           }

         if(humanPresent == 0) {
             //Alternate moving north and east towards the human
             //If I hit the edge of world, I search for the turtle as well
             if(turnNorth == 1) {
                bestMove = Move.NORTH;
                turnNorth = 0;
             }

             else {
                bestMove = Move.EAST;
                turnNorth = 1;
             }
         }

      //If a threat was found, attack accordingly.
      threatPresent = isThereCurrThreat;
      return bestMove;

    }

  public boolean wantToFight(int[] enemyAbilities) {
      //Only fight if another enemey is near
      if (threatPresent == 1) {
        return true;
        }

      else {
        return false;
      }
   }
}