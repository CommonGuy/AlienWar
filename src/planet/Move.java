package planet;

import java.util.Random;

public enum Move {
	NORTH(0,-1),
	NORTHEAST(1,-1),
	EAST(1,0),
	SOUTHEAST(1,1),
	SOUTH(0,1),
	SOUTHWEST(-1,1),
	WEST(-1,0),
	NORTHWEST(-1,-1),
	STAY(0,0);
	
	private int xOffset;
	private int yOffset;
	public static final Random random = new Random();
	
	private Move(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public int getXOffset() {
		return xOffset;
	}
	
	public int getYOffset() {
		return yOffset;
	}
	
	public static Move getRandom() {
        return values()[random.nextInt((int)values().length)];
    }
}