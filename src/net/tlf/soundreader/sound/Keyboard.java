package net.tlf.soundreader.sound;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * @author thislooksfun
 */
public class Keyboard
{
	public static Map<Integer, Boolean> keys = new HashMap<>();
	
	public static boolean isPressed(int key)
	{
		synchronized (Utils.THREAD_LOCK)
		{
			return keys.containsKey(key) ? keys.get(key) : false;
		}
	}
	
	public static void init()
	{
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher()
		{
			@Override
			public boolean dispatchKeyEvent(KeyEvent ke)
			{
				synchronized (Utils.THREAD_LOCK)
				{
					keys.put(ke.getKeyCode(), ke.getID() == KeyEvent.KEY_PRESSED);
					
					return false;
				}
			}
		});
	}
}