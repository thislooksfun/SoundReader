package net.tlf.soundreader.Main;

import net.tlf.soundreader.Util.Utils;
import net.tlf.soundreader.gfx.Window;

/**
 * @author thislooksfun
 */
public class SoundReader
{
	public static Window window;
	
	public static boolean running = true;
	
	public static void main(String[] args)
	{
		window = new Window();
		window.setVisible(true);
		
		window.surface.setCount(100);
		
		int i = 0;
		while (running)
		{
			Utils.waitmills(10);
			window.repaint();
			
			checkKeys();
			
			i++;
			if (i == 0.1 * 100)
			{
				i = 0;
				window.surface.update();
			}
		}
	}
	
	public static void checkKeys()
	{
//		Keyboard
	}
}