package net.tlf.soundreader.render;

import net.tlf.soundreader.main.Options;
import net.tlf.soundreader.main.SoundReader;

/**
 * Bar graph made of fixed horizontal lines
 * 
 * @author thislooksfun
 */
public class BarBarRenderer extends SmoothFlattenRenderer
{
	public BarBarRenderer(SoundReader main)
	{
		super(main);
	}
	
	@Override
	protected String author()
	{
		return "tlf";
	}
	@Override
	protected String type()
	{
		return "bar_bar";
	}
	
	@Override
	public void render(float[] data)
	{
		//TODO: Make work.
		stroke(100, 0, 0);
		fill(100, 0, 0);
		strokeWeight(2);
		for (int i = 0; i < data.length-1; i++)
		{
			if (Options.debug && i == data.length - 2)
			{
				stroke(0, 100, 0);
				fill(0, 100, 0);
			}
			int left = (int)(Options.start / 2.5 + (i * Options.space));
			rect(left, height() - data[i] - Options.restingSize - 5, Options.barSize, height());
		}
	}
}