package net.tlf.soundreader.render;

import net.tlf.soundreader.main.Options;
import net.tlf.soundreader.main.SoundReader;

/**
 * @author thislooksfun
 */
public abstract class FlattenedRenderer extends DataRenderer
{
	public FlattenedRenderer(SoundReader main)
	{
		super(main);
	}
	
	@Override
	public float[] formatData(float[] rawData)
	{
		return flattenData(cullData(rawData, Options.barNumber));
	}
}