package alien;

import planet.Move;

public class CropCircleAlien extends Alien {

    private int i = 0;

    @Override
    public void setAbilityPoints(float[] abilities) {
        abilities[0] = 3; // life
        abilities[1] = 7; // strength
        abilities[2] = 0; // defense
        abilities[3] = 0; // vision
        abilities[4] = 0; // cleverness
    }

    @Override
    public Move move(char[][] fields) {
        switch (getI()) {
        case 0:
            setI(getI() + 1);
            return Move.EAST;
        case 1:
            setI(getI() + 1);
            return Move.SOUTHEAST;
        case 2:
            setI(getI() + 1);
            return Move.SOUTH;
        case 3:
            setI(getI() + 1);
            return Move.SOUTHWEST;
        case 4:
            setI(getI() + 1);
            return Move.WEST;
        case 5:
            setI(getI() + 1);
            return Move.NORTHWEST;
        case 6:
            setI(getI() + 1);
            return Move.NORTH;
        case 7:
            setI(getI() + 1);
            return Move.NORTHEAST;
        default:
            return Move.STAY;
        }
    }

    @Override
    public boolean wantToFight(int[] enemyAbilities) {
        return false;
    }

    public void setI(int i) {
        if (i < 8) {
            this.i = i;
        } else {
            this.i = 0;
        }
    }

    public int getI() {
        return this.i;
    }
}