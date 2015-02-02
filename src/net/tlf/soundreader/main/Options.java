package net.tlf.soundreader.main;

/**
 * @author thislooksfun
 */
public class Options
{
	/** The time (in milliseconds) between renders */
	public static int timeBetween = 10;
	/** The number of bars to display on the screen */
	public static int barNumber = 80;
	/** How large the bars are<br>Leave -1 for auto */
	public static int barSize = -1;
	/** How large the curved bars are<br>Leave -1 for auto */
	public static int barLineSize = -1;
	/** How far from the left of the screen the middle of the first bar is<br>Leave -1 for auto */
	public static int start = -1;
	/** How far between the centers of the lines<br>Leave -1 for auto */
	public static int space = -1;
	/** How much to multiply the incoming signal by */
	public static int scaleFactor = 50;
	/** How tall the bars should be when there is no signal */
	public static int restingSize = 3;
	/** The type of graph to display<br>0: Line graph with curves<br>1: Line graph without curves<br>2: Bar graph*/
	public static String graphType = "tlf:curve_bar";
	
	/** Whether or not to preform various debug actions */
	public static boolean debug = false;
	
	public static void setNumBars(int numBars, boolean overrideData, int width)
	{
		setData(numBars, overrideData ? -1 : barSize, overrideData ? -1 : barLineSize, overrideData ? -1 : start, overrideData ? -1 : space, width);
	}
	public static void setData(int numBars, int sizeBar, int sizeLineBar, int newStart, int newSpace, int width)
	{
		barNumber = numBars;
		barSize = sizeBar;
		barLineSize = sizeLineBar;
		start = newStart;
		space = newSpace;
		
		updateData(width);
	}
	
	public static void updateData(int width)
	{
		if (space == -1)
		{
			while ((barNumber * space) < width)
				space++;
			while (barNumber * space > width)
				space--;
		}
		if (barSize == -1) barSize = space - 5;
		if (barLineSize == -1) barLineSize = space - 3;
		if (start == -1) start = barLineSize / 2 + 3;
		
//		if () //Check if # bars is too much
	}
	
	/**
	 * Don't instantiate this class
	 */
	private Options()
	{
	}
}