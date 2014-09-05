package shag.client.game;

import java.awt.Polygon;

public class BattleShip extends Ship {
	public static final Polygon SHAPE = new Polygon(new int[]{0, 20, 40, 20}, new int[]{40, 0, 40, 50}, 4);
	
	public BattleShip(int x, int y){
		super(x, y, SHAPE);
	}
}
