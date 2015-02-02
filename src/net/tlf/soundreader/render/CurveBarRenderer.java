package net.tlf.soundreader.render;

import net.tlf.soundreader.main.Options;
import net.tlf.soundreader.main.SoundReader;

/**
 * Bar graph made of lines with rounded ends
 * 
 * @author thislooksfun
 */
public class CurveBarRenderer extends SmoothFlattenRenderer
{
	public CurveBarRenderer(SoundReader main)
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
		return "curve_bar";
	}
	
	@Override
	public void render(float[] data)
	{
		stroke(100, 0, 0);
		strokeWeight(Options.barLineSize);
		for (int i = 0; i < data.length-1; i++)
		{
			if (Options.debug && i == data.length - 2) stroke(0, 100, 0);
			line(Options.start + (i * Options.space), height() - data[i+1] - Options.restingSize, Options.start + (i * Options.space), height());
		}
	}
}