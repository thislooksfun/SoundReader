package net.tlf.soundreader.Main;

import java.awt.event.KeyEvent;

import net.tlf.soundreader.Util.Keyboard;
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
		Keyboard.init();
		
		window = new Window();
		window.setVisible(true);
		
		window.surface.setCount(100);
		
		int i = 0;
		while (running)
		{
			Utils.waitmillis(10);
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
		if (Keyboard.isPressed(KeyEvent.VK_1))
		{
			window.surface.setCount(10);
		} else if (Keyboard.isPressed(KeyEvent.VK_2))
		{
			window.surface.setCount(50);
		} else if (Keyboard.isPressed(KeyEvent.VK_3))
		{
			window.surface.setCount(100);
		} else if (Keyboard.isPressed(KeyEvent.VK_4))
		{
			window.surface.setCount(200);
		} else if (Keyboard.isPressed(KeyEvent.VK_5))
		{
			window.surface.setCount(400);
		}  else if (Keyboard.isPressed(KeyEvent.VK_6))
		{
			window.surface.setCount(5000);
		}
	}
}