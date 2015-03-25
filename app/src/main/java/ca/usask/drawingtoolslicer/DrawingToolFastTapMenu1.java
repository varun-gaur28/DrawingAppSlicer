package ca.usask.drawingtoolslicer;

import android.content.Context;

public class DrawingToolFastTapMenu1 extends FastTapMenu {

    public DrawingToolFastTapMenu1(Context context) {
        super(context);
        this.cols = 5;
        this.rows = 5;
        this.menuButton = new MenuItem(ToolItem.Adsense.name, ToolItem.Adsense.icon);
        this.menuItems = new MenuItem[ToolItem.all1.length+2];
        int i = 0;
        for (ToolItem ti : ToolItem.all1) {
            menuItems[i] = new MenuItem(ti.name, ti.icon);
            i += 1;
        }
        menuItems[i] = null;
        menuItems[i+1] = this.menuButton; // menuButton is the only item on the final row

        this.selectedItem = menuItems[0];
    }

}