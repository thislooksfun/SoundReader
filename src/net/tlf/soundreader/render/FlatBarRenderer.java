package net.tlf.soundreader.render;

import net.tlf.soundreader.main.Options;
import net.tlf.soundreader.main.SoundReader;

/**
 * Bar graph made of lines with straight ends
 *
 * @author thislooksfun
 */
public class FlatBarRenderer extends SmoothFlattenRenderer
{
	public FlatBarRenderer(SoundReader main)
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
		return "flat_bar";
	}
	
	@Override
	public void render(float[] data)
	{
		stroke(100, 0, 0);
		fill(100, 0, 0);
		strokeWeight(2);
		for (int i = 0; i < data.length - 1; i++)
		{
			if (Options.debug && i == data.length - 2)
			{
				stroke(0, 100, 0);
				fill(0, 100, 0);
			}
			int left = (int)(Options.start / 2.5 + (i * Options.space));
			rect(left, height() - data[i + 1] - Options.restingSize - 5, Options.barSize, height());
		}
	}
}