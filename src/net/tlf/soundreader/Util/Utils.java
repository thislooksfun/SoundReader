package net.tlf.soundreader.Util;

/**
 * @author thislooksfun
 */
public class Utils
{
	/**
	 * The object used in <code>synchronized</code> blocks
	 */
	public static final Object THREAD_LOCK = new Object();
	/**
	 * The object used for <code>wait</code> calls<br>Interrupt this to stop the timer
	 */
	private static final Object TIMER = new Object();
	
	/**
	 * Waits for some number of seconds
	 *
	 * @param seconds The number of seconds to wait for
	 * @return <code>true</code> if waiting times out<br><code>false</code> if waiting is interrupted
	 */
	public synchronized static boolean wait(float seconds)
	{
		return waitmillis((long)(seconds * 1000));
	}
	
	/**
	 * Waits for some number of milliseconds
	 *
	 * @param mills The number of milliseconds to wait for
	 * @return <code>true</code> if waiting times out<br><code>false</code> if waiting is interrupted
	 */
	public synchronized static boolean waitmillis(long mills)
	{
		synchronized (TIMER)
		{
			try
			{
				TIMER.wait(mills);
			} catch (InterruptedException e)
			{
				return false;
			}
		}
		
		return true;
	}
}