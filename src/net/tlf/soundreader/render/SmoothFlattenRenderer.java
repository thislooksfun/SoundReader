package net.tlf.soundreader.render;

import net.tlf.soundreader.main.SoundReader;

/**
 * @author thislooksfun
 */
public abstract class SmoothFlattenRenderer extends FlattenedRenderer
{
	private float[] prevData = new float[0];
	
	public SmoothFlattenRenderer(SoundReader main)
	{
		super(main);
	}
	
	@Override
	public float[] formatData(float[] rawData)
	{
		float[] data = avgData(super.formatData(rawData), this.prevData);
		this.prevData = data;
		return limitData(data);
	}
}