package ca.usask.drawingtoolslicer;

import android.content.Context;

public class DrawingToolFastTapMenu extends FastTapMenu {

    public DrawingToolFastTapMenu(Context context) {
        super(context);
        this.cols = 5;
        this.rows = 5;
        this.menuButton = new MenuItem(ToolItem.Blogger.name, ToolItem.Blogger.icon);
        this.menuItems = new MenuItem[ToolItem.all.length+1];
        int i = 0;
        for (ToolItem ti : ToolItem.all) {
            menuItems[i] = new MenuItem(ti.name, ti.icon);
            i += 1;
        }
        menuItems[i] = this.menuButton; // menuButton is the only item on the final row

        this.selectedItem = menuItems[0];
    }

}