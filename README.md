AlienWar
========

Control program for AlienWar (a king of the hill challenge) on codegolf.stackexchange.com.
http://codegolf.stackexchange.com/questions/32782/survival-game-alienwar

Alien-Template:
```
package alien;

import planet.Move;

public class YourUniqueNameHere extends Alien {

	public void setAbilityPoints(float[] abilities) {
		abilities[0] = 2; //life
		abilities[1] = 2; //strength
		abilities[2] = 2; //defense
		abilities[3] = 2; //vision
		abilities[4] = 2; //cleverness
	}
	
	public Move move(char[][] fields) {
	  //you are in the middle of the fields, say fields[getVisionFieldsCount()][getVisionFieldsCount()]
		return Move.STAY;
	}
	
	public boolean wantToFight(int[] enemyAbilities) {
	  //same order of array as in setAbilityPoints, but without cleverness
		return true;
	}

}
```
