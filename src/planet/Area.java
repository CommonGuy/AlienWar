package planet;

import java.awt.Point;
import java.util.List;

public class Area {
	private final int size;
	private char[][] area;

	public Area(List<Specie> species, int size) {
		this.size = size;
		area = new char[size][size];
		
		//default
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				area[i][j] = ' ';
			}
		}
		
		//set species characters
		for (Specie specie : species) {
			Point pos = specie.getPos();
			area[pos.x][pos.y] = specie.getRepresentedChar();
		}
	}
	
	public char[][] getVision(Point pos, int fieldCount) {
		int totalCount = fieldCount * 2 + 1;
		char[][] vision = new char[totalCount][totalCount];
		int startX = wrap(pos.x - fieldCount);
		int startY = wrap(pos.y - fieldCount);
		
		for (int x = 0; x < totalCount; x++) {
			for (int y = 0; y < totalCount; y++) {
				vision[x][y] = area[wrap(startX + x)][wrap(startY + y)];
			}
		}
		return vision;
	}
	
	public int wrap(int pos) {
		if (pos < 0) {
			return size + pos;
		}
		return pos % size;
	}
}