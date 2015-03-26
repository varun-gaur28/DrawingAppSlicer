package ca.usask.drawingtoolslicer;

import android.content.Context;

public class DrawingToolFastTapMenu2 extends FastTapMenu {

    public DrawingToolFastTapMenu2(Context context) {
        super(context);
        this.cols = 5;
        this.rows = 5;
        this.menuButton = new MenuItem(ToolItem.Pool.name, ToolItem.Pool.icon);
        this.menuItems = new MenuItem[ToolItem.all2.length+2];
        int i = 0;
        for (ToolItem ti : ToolItem.all2) {
            menuItems[i] = new MenuItem(ti.name, ti.icon);
            i += 1;
        }
        menuItems[i] = null;
        menuItems[i+1] = this.menuButton; // menuButton is the only item on the final row

        this.selectedItem = menuItems[0];
    }

}