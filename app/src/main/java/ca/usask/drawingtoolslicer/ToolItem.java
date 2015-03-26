package ca.usask.drawingtoolslicer;

public class ToolItem {
   	public String name;
	public int icon;

    public ToolItem(String name, int icon) {
		this.name = name;
		this.icon = icon;
	}

	public static final ToolItem Blogger = new ToolItem("Blogger", R.drawable.blogger);
	public static final ToolItem Calendar = new ToolItem("Calendar", R.drawable.calendar);
	public static final ToolItem Chrome = new ToolItem("Chrome", R.drawable.chrome);
	public static final ToolItem Contacts = new ToolItem("Contacts", R.drawable.contacts);
	public static final ToolItem Docs = new ToolItem("Docs", R.drawable.docs);
	
	public static final ToolItem Drive = new ToolItem("Drive", R.drawable.drive);
	public static final ToolItem Earth = new ToolItem("Earth", R.drawable.earth);
	public static final ToolItem Gmail = new ToolItem("Gmail", R.drawable.gmail);
	public static final ToolItem Goggles = new ToolItem("Goggles", R.drawable.goggles);
	public static final ToolItem GooglePlus = new ToolItem("GooglePlus", R.drawable.googleplus);

	public static final ToolItem Hubble = new ToolItem("Hubble", R.drawable.hubble);
	public static final ToolItem Maps = new ToolItem("Maps", R.drawable.maps);
	public static final ToolItem Music = new ToolItem("Music", R.drawable.music);
	public static final ToolItem Office = new ToolItem("Office", R.drawable.office);
	public static final ToolItem PlayStore = new ToolItem("PlayStore", R.drawable.playstore);
		
	public static final ToolItem Search = new ToolItem("Search", R.drawable.search);
	public static final ToolItem Shopping = new ToolItem("Shopping", R.drawable.shopping);
	public static final ToolItem Videos = new ToolItem("Videos", R.drawable.videos);
	public static final ToolItem Voice = new ToolItem("Voice", R.drawable.voice);
	public static final ToolItem Youtube = new ToolItem("Youtube", R.drawable.youtube);

    public static final ToolItem Pool = new ToolItem("8 Ball Pool", R.drawable.pool);
	public static final ToolItem AngryBird = new ToolItem("Angry Bird", R.drawable.angrybird);
	public static final ToolItem AssasinsCreed = new ToolItem("Assasins Creed", R.drawable.assassinscreed);
	public static final ToolItem CallOfDuty = new ToolItem("Call of Duty", R.drawable.callofduty);
    public static final ToolItem CandyCrush = new ToolItem("Candy Crush", R.drawable.candycrush);

    public static final ToolItem CounterStrike = new ToolItem("Counter Strike", R.drawable.counterstrike);
    public static final ToolItem DeadTrigger = new ToolItem("Dead Trigger 2", R.drawable.deadtrigger);
    public static final ToolItem Fifa = new ToolItem("Fifa 15", R.drawable.fifa15);
    public static final ToolItem FreeFlow = new ToolItem("Free Flow", R.drawable.freeflow);
    public static final ToolItem GTA = new ToolItem("GTA IV", R.drawable.gta4);

    public static final ToolItem GTAVC = new ToolItem("GTA Vice City", R.drawable.gtavicecity);
    public static final ToolItem Mario = new ToolItem("Mario", R.drawable.mario);
    public static final ToolItem Minecraft = new ToolItem("Minecraft", R.drawable.minecraft);
    public static final ToolItem NFS = new ToolItem("Need for Speed", R.drawable.needforspeed);
    public static final ToolItem FruitNinja = new ToolItem("Fruit Ninja", R.drawable.fruitninja);

    public static final ToolItem Quake = new ToolItem("Quake III", R.drawable.quake);
    public static final ToolItem Simcity = new ToolItem("Sim City BuildIt", R.drawable.simcity);
    public static final ToolItem Sonic = new ToolItem("Sonic", R.drawable.sonic);
    public static final ToolItem SubwaySurfers = new ToolItem("Subway Surfers", R.drawable.subwaysurfers);
    public static final ToolItem TempleRun = new ToolItem("Temple Run 2", R.drawable.templerun);

    public static final ToolItem Paintbrush = new ToolItem("Paintbrush", R.drawable.paintbrush);
    public static final ToolItem Line = new ToolItem("Line", R.drawable.line);
    public static final ToolItem Circle = new ToolItem("Circle", R.drawable.circle);
    public static final ToolItem Rectangle = new ToolItem("Rectangle", R.drawable.rectangle);
    public static final ToolItem Fill = new ToolItem("Fill", R.drawable.fill);

    public static final ToolItem Black = new ToolItem("Black", R.drawable.black);
    public static final ToolItem Red = new ToolItem("Red", R.drawable.red);
    public static final ToolItem Green = new ToolItem("Green", R.drawable.green);
    public static final ToolItem Blue = new ToolItem("Blue", R.drawable.blue);
    public static final ToolItem ColorPicker = new ToolItem("Color Picker", R.drawable.colorpicker);

    public static final ToolItem White = new ToolItem("White", R.drawable.white);
    public static final ToolItem Yellow = new ToolItem("Yellow", R.drawable.yellow);
    public static final ToolItem Cyan = new ToolItem("Cyan", R.drawable.cyan);
    public static final ToolItem Magenta = new ToolItem("Magenta", R.drawable.magenta);
    public static final ToolItem CustomColor = new ToolItem("Custom Color", R.drawable.customcolor);

    public static final ToolItem Fine = new ToolItem("Fine", R.drawable.fine);
    public static final ToolItem Thin = new ToolItem("Thin", R.drawable.thin);
    public static final ToolItem Medium = new ToolItem("Medium", R.drawable.medium);
    public static final ToolItem Wide = new ToolItem("Wide", R.drawable.wide);
    public static final ToolItem Undo = new ToolItem("Undo", R.drawable.undo);

    public static ToolItem[] all1 = {Blogger,
                                    Calendar,
                                    Chrome,
                                    Contacts,
                                    Docs,
                                    Drive,
                                    Earth,
                                    Gmail,
                                    Goggles,
                                    GooglePlus,
                                    Hubble,
                                    Maps,
                                    Music,
                                    Office,
                                    PlayStore,
                                    Search,
                                    Shopping,
                                    Videos,
                                    Voice,
                                    Youtube};
    public static ToolItem[] all2 = {Pool,
                                    AngryBird,
                                    AssasinsCreed,
                                    CallOfDuty,
                                    CandyCrush,
                                    CounterStrike,
                                    DeadTrigger,
                                    Fifa,
                                    FreeFlow,
                                    GTA,
                                    GTAVC,
                                    Mario,
                                    Minecraft,
                                    NFS,
                                    FruitNinja,
                                    Quake,
                                    Simcity,
                                    Sonic,
                                    SubwaySurfers,
                                    TempleRun};

    public static ToolItem[] all3 = {Paintbrush,
                                    Line,
                                    Circle,
                                    Rectangle,
                                    Fill,
                                    Black,
                                    Red,
                                    Green,
                                    Blue,
                                    ColorPicker,
                                    White,
                                    Yellow,
                                    Cyan,
                                    Magenta,
                                    CustomColor,
                                    Fine,
                                    Thin,
                                    Medium,
                                    Wide,
                                    Undo};

    public static ToolItem[] enemies1 = {Gmail,
                                        Hubble,
                                        Maps,
                                        Chrome};
    public static ToolItem[] enemies2 = {Gmail,
                                        Hubble,
                                        Maps,
                                        Chrome,
                                        DeadTrigger,
                                        Quake,
                                        AngryBird,
                                        TempleRun};
    public static ToolItem[] enemies3 = {Gmail,
                                        Hubble,
                                        Maps,
                                        Chrome,
                                        DeadTrigger,
                                        Quake,
                                        AngryBird,
                                        TempleRun,
                                        Black,
                                        Magenta,
                                        Thin,
                                        Rectangle};
}