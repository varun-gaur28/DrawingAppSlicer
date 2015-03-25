package ca.usask.drawingtoolslicer;

import android.content.Context;

public class ColorFastTapMenu extends FastTapMenu {

	public ColorFastTapMenu(Context context) {
		super(context);
		this.cols = 4;
		this.rows = 4;
		this.menuButton = new MenuItem(ColorItem.Black.name, ColorItem.Black.icon);
		this.menuItems = new MenuItem[ColorItem.all.length+1];
		int i = 0;
		for (ColorItem ci : ColorItem.all) {
			menuItems[i] = new MenuItem(ci.name, ci.icon);
			i += 1;
			if (i == 12) {
				menuItems[i] = this.menuButton;
				i += 1;
			}
		}
		this.selectedItem = menuItems[8];
	}
	
}
