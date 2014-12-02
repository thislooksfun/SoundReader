package net.tlf.soundreader.main;

/**
 * @author thislooksfun
 */
public class Options
{
	private static boolean gaps = true;
	public static int count = 500;
	
	public static void setGaps(boolean b)
	{
		gaps = b;
		SoundReader.window.display.setCount(count * (b ? 2 : 1));
	}
	
	public static boolean gaps()
	{
		return gaps;
	}
	
	
	/**
	 * Don't instantiate this class
	 */
	private Options()
	{
	}
}