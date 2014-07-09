package planet;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import prey.*;
import alien.*;

public class Planet {
    private static final Class[] species = {AimlessWanderer.class, Assassin.class, BananaPeel.class, Bender.class, BlindBully.class, BullyAlien.class, CartographerLongVisionAlien.class, ChooseYourBattles.class, CleverAlien.class, CorporateAlien.class, Coward.class, CropCircleAlien.class, Fleer.class, FunkyBob.class, FunkyJack.class, Gamer.class, GentleGiant.class, Geoffrey.class, Guard.class, Hunter.class, Junkie.class, Manager.class, Morphling.class, NewGuy.class, OkinawaLife.class, PredicatClaw.class, PredicatEyes.class, Predicoward.class, Randomite.class, Rock.class, Rogue.class, SecretWeapon.class, SecretWeapon2.class, SecretWeapon3.class, Sped.class, Stone.class, Survivor.class, VanPelt.class, Warrior.class, WeakestLink.class, Whale.class, Cow.class, Turtle.class, Eagle.class, Human.class};
    private static final int SIZE = (int)Math.ceil(Math.sqrt(species.length * 100 * 2.5)); // 0 inclusive, SIZE exclusive
	private static final int ROUND_COUNT = 1000;
	private static final int SPECIE_COUNT = 100;
	private static final int START_HEALING_FACTOR = 5;
	private static final List<Specie> speciesList = new ArrayList<Specie>();

    public static void main(String[] args) {		
		populate();
		setStartPositions();
        for (int i = 0; i < ROUND_COUNT; i++) {
            moveAndFight();
			removeDeadSpecies();
        }
		
		new Stats(species, speciesList).show();
	}
	
	private static void populate() {
        for(Class<Specie> specie : species) {
			for (int i = 0; i < SPECIE_COUNT; i++) {
				float[] abilities = new float[5];
				try {
					Specie newSpecie = (Specie) specie.newInstance();
					newSpecie.setAbilityPoints(abilities);
					if (checkAbilitesOk(abilities)) {
						newSpecie.upgradeAbilities(abilities);
						newSpecie.heal(Math.round(abilities[0]) * START_HEALING_FACTOR);
						speciesList.add(newSpecie);
					}
				} catch (Exception e) {}
			}
		}
	}
	
	private static void moveAndFight() {
		Map<Point, List<Specie>> positions = new HashMap<Point, List<Specie>>();
		Collections.shuffle(speciesList); // make fighting random if multiple species walk on one field
		Area area = new Area(speciesList, SIZE);
		
		for (Specie specie : speciesList) {
			Move move = Move.STAY;
			//move
			Point pos = specie.getPos();
			char[][] vision = area.getVision(specie.getPos(), specie.getVisionFieldsCount());
			try {
				move = specie.move(vision);
			} catch (Exception e) {}
			int posX = area.wrap(pos.x + move.getXOffset());
			int posY = area.wrap(pos.y + move.getYOffset());
			specie.setPos(posX, posY);
			
			//check fight (random order of species because shuffled before)
			pos = specie.getPos();
			List<Specie> speciesOnField = positions.get(pos);
			if (speciesOnField == null) {
				speciesOnField = new ArrayList<Specie>();
				speciesOnField.add(specie);
				positions.put(pos, speciesOnField);
			} else { //fight! (if they want to)
				for (Specie enemy : speciesOnField) {
					Fight fight = new Fight(specie, enemy);
					if (!fight.bothWantPeace()) {
						fight.start();
						fight.rewardWinner();
					}
				}
			}
		}
	}
	
	private static void removeDeadSpecies() {
		Iterator<Specie> it = speciesList.iterator();
		while (it.hasNext()) {
			Specie currSpecie = it.next();
			if (currSpecie.isDead()) {
				it.remove();
			}
		}
	}
	
	private static void setStartPositions() {
		Random rand = new Random();
		Point pos;
		List<Point> positions = new ArrayList<Point>();
		for (Specie specie : speciesList) {
			do {
				pos = new Point(rand.nextInt(SIZE), rand.nextInt(SIZE));
			} while (positions.contains(pos));
			specie.setPos(pos.x, pos.y);
			positions.add(pos);
		}
	}
	
	private static boolean checkAbilitesOk(float[] abilities) {
		float sum = 0;
		
		for (float ability : abilities) {
			sum += ability;
			if (ability < 0 || ability > 10) { //suck it, cheaters! :P
				return false;
			}
		}
		
		return sum <= 10;
	}
}
