package net.tlf.soundreader.util;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * @author thislooksfun
 */
public class Keyboard
{
	public static Map<Integer, Long> keys = new HashMap<>();
	
	public static boolean isPressed(int key, boolean allowRepeat)
	{
		synchronized (Utils.THREAD_LOCK)
		{
			boolean result = keys.containsKey(key);
			if (result)
			{
				if (allowRepeat)
				{
					result = keys.get(key) < System.currentTimeMillis();
					if (result)
						keys.put(key, System.currentTimeMillis() + 100);
				} else
					keys.remove(key);
			}
			return result;
		}
	}
	
	public static boolean isPressed(int key)
	{
		synchronized (Utils.THREAD_LOCK)
		{
			return isPressed(key, false);
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
					if (ke.getID() == KeyEvent.KEY_PRESSED)
						keys.put(ke.getKeyCode(), System.currentTimeMillis());
					else if (ke.getID() == KeyEvent.KEY_RELEASED)
						keys.remove(ke.getKeyCode());
					
					return false;
				}
			}
		});
	}
}