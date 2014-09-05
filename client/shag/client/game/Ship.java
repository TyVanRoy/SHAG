package shag.client.game;

import java.awt.Polygon;

public class Ship extends Sprite {
	protected Polygon shape;
	
	public Ship(int x, int y, Polygon shape){
		super(x, y);
		this.shape = shape;
	}
	
	public Polygon getShape(){
		return shape;
	}

}
