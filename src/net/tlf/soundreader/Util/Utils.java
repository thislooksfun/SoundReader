package net.tlf.soundreader.Util;

/**
 * @author thislooksfun
 */
public class Utils
{
	public static final Object THREAD_LOCK = new Object();

	public static boolean wait(int seconds)
	{
		synchronized (THREAD_LOCK) {
			try
			{
				THREAD_LOCK.wait(seconds * 1000);
			} catch (InterruptedException e)
			{
				return false;
			}
		}

		return true;
	}
}
