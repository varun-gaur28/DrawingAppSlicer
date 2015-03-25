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

    public static final ToolItem Adsense = new ToolItem("Adsense", R.drawable.adsense);
	public static final ToolItem Authenticator = new ToolItem("Authenticator", R.drawable.authenticator);
	public static final ToolItem Books = new ToolItem("Books", R.drawable.books);
	public static final ToolItem Catalogs = new ToolItem("Catalogs", R.drawable.catalogs);
    public static final ToolItem Cities = new ToolItem("Cities", R.drawable.cities);

    public static final ToolItem CloudPrint = new ToolItem("CloudPrint", R.drawable.cloudprint);
    public static final ToolItem Coordinate = new ToolItem("Coordinate", R.drawable.coordinate);
    public static final ToolItem Dictionary = new ToolItem("Dictionary", R.drawable.dictionary);
    public static final ToolItem Directions = new ToolItem("Directions", R.drawable.directions);
    public static final ToolItem Drawings = new ToolItem("Drawings", R.drawable.drawings);

    public static final ToolItem Ebooks = new ToolItem("Ebooks", R.drawable.ebooks);
    public static final ToolItem Fonts = new ToolItem("Fonts", R.drawable.fonts);
    public static final ToolItem Igoogle = new ToolItem("Igoogle", R.drawable.igoogle);
    public static final ToolItem Images = new ToolItem("Images", R.drawable.images);
    public static final ToolItem MapImagery = new ToolItem("MapImagery", R.drawable.imagery);

    public static final ToolItem Presentations = new ToolItem("Presentations", R.drawable.presentations);
    public static final ToolItem QnA = new ToolItem("QnA", R.drawable.qna);
    public static final ToolItem Research = new ToolItem("Research", R.drawable.research);
    public static final ToolItem Scholar = new ToolItem("Scholar", R.drawable.scholar);
    public static final ToolItem ScratchPad = new ToolItem("ScratchPad", R.drawable.scratchpad);

    public static ToolItem[] all = {Blogger,
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
    public static ToolItem[] all1 = {Adsense,
                                    Authenticator,
                                    Books,
                                    Catalogs,
                                    Cities,
                                    CloudPrint,
                                    Coordinate,
                                    Dictionary,
                                    Directions,
                                    Drawings,
                                    Ebooks,
                                    Fonts,
                                    Igoogle,
                                    Images,
                                    MapImagery,
                                    Presentations,
                                    QnA,
                                    Research,
                                    Scholar,
                                    ScratchPad};

	public static ToolItem[] enemies = {Gmail,
                                        Hubble,
                                        Contacts,
                                        Maps,
                                        Chrome,
                                        Authenticator,
                                        CloudPrint,
                                        Dictionary};
	
}
