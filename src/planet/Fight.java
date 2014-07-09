package planet;

import java.util.Random;

public class Fight {
	private final Specie one;
	private final Specie two;
	private final Random rand = new Random();
	private static final int FIGHT_HEALING_FACTOR = 2;

	public Fight(Specie one, Specie two) {
		this.one = one;
		this.two = two;
	}
	
	public boolean bothWantPeace() {
		try {
			boolean wantFightOne = one.wantToFight(getBlurredAbilities(two));
			boolean wantFightTwo = two.wantToFight(getBlurredAbilities(one));
			
			return !wantFightOne && !wantFightTwo;
		} catch (Exception e) {
			return false; //always fight if one alien has an error
		}
	}
	
	public void start() {		
		while ( !(one.isDead() || two.isDead()) ) {
			if (!canDodge(one)) {
				one.hit(getHitStrength(two));
			}
			if (!canDodge(two)) {
				two.hit(getHitStrength(one));
			}
		}
	}
	
	public void rewardWinner() {
		if (!one.isDead()) {
			rewardWinner(one, two);
		} else if (!two.isDead()) {
			rewardWinner(two, one);
		}
	}
	
	private void rewardWinner(Specie specie, Specie enemy) {
		float abilities[] = new float[5];
		abilities[0] = (float)enemy.getLifeLvl() / 5;
		abilities[1] = (float)enemy.getStrengthLvl() / 5;
		abilities[2] = (float)enemy.getDefenseLvl() / 5;
		abilities[3] = (float)enemy.getVisionLvl() / 5;
		abilities[4] = (float)enemy.getClevernessLvl() / 5;
		specie.upgradeAbilities(abilities);
		specie.heal(enemy.getLifeLvl() * FIGHT_HEALING_FACTOR);
	}
	
	private int[] getBlurredAbilities(Specie specie) {
		int[] abilities = new int[4];
		int blur = specie.getClevernessBlur();
		
		abilities[0] = specie.getLifeLvl();
		abilities[1] = specie.getStrengthLvl();
		abilities[2] = specie.getDefenseLvl();
		abilities[3] = specie.getVisionLvl();
		
		for (int i = 0; i < abilities.length; i++) {
			abilities[i] += rand.nextInt(blur);
		}
		
		return abilities;
	}
	
	private int getHitStrength(Specie specie) {
		return rand.nextInt(specie.getStrengthLvl()) + 1;
	}
	
	private boolean canDodge(Specie specie) {
		int defenseLvl = specie.getDefenseLvl();
		if (defenseLvl >= 50) {
			defenseLvl = 50;
		}
		return rand.nextInt(50 / defenseLvl + 1) == 0;
	}
}