package ca.usask.drawingtoolslicer;

import android.graphics.Color;

public class ColorItem {

	public String name;
	public int color;
	public int icon;
	
	public ColorItem(String name, int color, int icon) {
		this.name = name;
		this.color = color;
		this.icon = icon;
	}

	public static final ColorItem Red = new ColorItem("Red", Color.RED, R.drawable.redsquiggle);
	public static final ColorItem Blue = new ColorItem("Blue", Color.BLUE, R.drawable.bluesquiggle);
	public static final ColorItem Yellow = new ColorItem("Yellow", Color.YELLOW, R.drawable.yellowsquiggle);
	public static final ColorItem Green = new ColorItem("Green", Color.GREEN, R.drawable.greensquiggle);
	public static final ColorItem Pink = new ColorItem("Pink", 0xffff81c0, R.drawable.pinksquiggle);
	public static final ColorItem Purple = new ColorItem("Purple", 0xff7e1e9c, R.drawable.purplesquiggle );
	public static final ColorItem Orange = new ColorItem("Orange", 0xfff97306, R.drawable.orangesquiggle );
	public static final ColorItem Cyan = new ColorItem("Cyan", Color.CYAN, R.drawable.cyansquiggle );
	public static final ColorItem Black = new ColorItem("Black", Color.BLACK, R.drawable.blacksquiggle );
	public static final ColorItem Gray = new ColorItem("Gray", Color.GRAY, R.drawable.graysquiggle );
	public static final ColorItem Brown = new ColorItem("Brown", 0xff653700, R.drawable.brownsquiggle );
	public static final ColorItem Beige = new ColorItem("Beige", 0xffe6daa6, R.drawable.beigesquiggle );
	
	public static ColorItem[] all = {Red,
									Blue,
									Yellow,
									Green,
									Pink,
									Purple,
									Orange,
									Cyan,
									Black,
									Gray,
									Brown,
									Beige};

	public static ColorItem[] enemies = all;
	
}
