package net.tlf.soundreader.sound;

import javax.sound.sampled.*;

import net.tlf.soundreader.main.SoundReader;

/**
 * @author thislooksfun
 */
public class MicReader implements Runnable
{
	private TargetDataLine targetLine;
	private AudioFormat format;
	
	private boolean initialized = false;
	
	public boolean running = true;
	
	public long lastUpdate = 0;
	public float updatesPerSecond = 0;
	
	@Override
	public void run()
	{
		this.init();
		
		while (this.running)
		{
//			this.read();
			this.isWorking();
		}
	}
	
	private void init()
	{
		this.initialized = false;
		
		try
		{
			//Get and display a list of
			// available mixers.
			Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
			System.out.println("Available mixers:");
			for (int cnt = 0; cnt < mixerInfo.length; cnt++)
			{
				System.out.println(mixerInfo[cnt].getName());
			}
			
			this.format = getAudioFormat();
			
			DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, this.format);
			
			Mixer mixer = AudioSystem.getMixer(mixerInfo[0]);
			
			this.targetLine = (TargetDataLine)mixer.getLine(dataLineInfo);
			this.targetLine.open(this.format);
			this.targetLine.start();
		} catch (Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		
		this.initialized = true;
	}
	
	private AudioFormat getAudioFormat()
	{
		float sampleRate = 8000.0F; //8000,11025,16000,22050,44100
		int sampleSizeInBits = 16; //8,16
		int channels = 1; //1,2
		boolean signed = true; //true,false
		boolean bigEndian = false; //true,false
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}
	
	private void read()
	{
		if (this.initialized)
		{
			int count = this.targetLine.read(SoundReader.window.surface.data, 0, SoundReader.window.surface.data.length-1);
			
//			System.out.println("count = " + count);
		} else
		{
			throw new IllegalStateException("Not initialized!");
		}
	}
	
	private boolean isWorking()
	{
		this.read();
		
		long l = 0;
//		String s = "Data = {";
//		
//		boolean first = true;
//		int biggest = 0;
//		int smallest = 0;
//		for (byte b : SoundReader.window.surface.data)
//		{
//			if (b > biggest || first) {
//				biggest = b;
//			}
//			if (b < smallest || first) {
//				smallest = b;
//			}
//			
//			l += b;
//			s += b+",";
//			
//			first = false;
//		}
//		s += "}\n";
//		
//		System.out.println("l = " + l + "; biggest = " + biggest + "; smallest = " + smallest);
//		System.out.println(s);
		return (l > 0);
	}
}