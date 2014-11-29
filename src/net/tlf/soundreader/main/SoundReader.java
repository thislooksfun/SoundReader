package net.tlf.soundreader.main;

import java.awt.event.KeyEvent;

import net.tlf.soundreader.gfx.Window;
import net.tlf.soundreader.sound.MicReader;
import net.tlf.soundreader.util.Keyboard;
import net.tlf.soundreader.util.Utils;

/**
 * @author thislooksfun
 */
public class SoundReader
{
	public static Window window;
	
	public static boolean running = true;
	
	public static MicReader reader;
	
	private static final int updatesPerSecond = 60;
	private static long millisBetween;
	
	public static void main(String[] args)
	{
		Keyboard.init();
		
		window = new Window();
		window.setVisible(true);
		
		window.display.setCount(100);
		
		reader = new MicReader();
		(new Thread(reader, "MicReader")).start();
		
		millisBetween = Math.round(1000/updatesPerSecond);
		System.out.println("millisBetween = " + millisBetween);
		
		int i = 0;
		while (running)
		{
			Utils.waitmillis(millisBetween);
			window.repaint();
			
			checkKeys();
			
			i++;
			if (i == 0.1 * 100)
			{
				i = 0;
				window.display.update();
			}
		}
	}
	
	public static void checkKeys()
	{
		if (Keyboard.isPressed(KeyEvent.VK_1))
		{
			window.display.setCount(10);
		} else if (Keyboard.isPressed(KeyEvent.VK_2))
		{
			window.display.setCount(50);
		} else if (Keyboard.isPressed(KeyEvent.VK_3))
		{
			window.display.setCount(100);
		} else if (Keyboard.isPressed(KeyEvent.VK_4))
		{
			window.display.setCount(200);
		} else if (Keyboard.isPressed(KeyEvent.VK_5))
		{
			window.display.setCount(400);
		} else if (Keyboard.isPressed(KeyEvent.VK_6))
		{
			window.display.setCount(5000);
		}
	}
}