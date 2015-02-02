package net.tlf.soundreader.render;

import net.tlf.soundreader.main.SoundReader;
import processing.core.PApplet;

/**
 * @author thislooksfun
 */
public abstract class DataRenderer
{
	protected final SoundReader main;
	
	public DataRenderer(SoundReader main)
	{
		this.main = main;
	}
	
	public abstract void render(float[] data);
	public abstract float[] formatData(float[] rawData);
	protected abstract String author();
	protected abstract String type();
	
	public final String name()
	{
		return author() + ":" + type();
	}
	
	protected float[] cullData(float[] data, int size)
	{
		size += 1;
		float[] out = new float[size];
		float tmp = data.length * 1.0f / size;
		int window = (int)tmp;
		if (tmp - (int)tmp >= 0.5) window++;
		int avg = 0;
		for (int i = 0; i < data.length; i++)
		{
			if (i / window >= size) break;
			avg += data[i];
			if (i % window == 0)
			{
				out[i / window] = avg / window;
				avg = 0;
			}
		}
		return out;
	}
	
	protected float[] avgData(float[] data1, float[] data2)
	{
		float[] out;
		if (data1 == null && data2 == null)
			return new float[0];
		else //noinspection ConstantConditions
			if (data1 == null && data2 != null)
				return data2;
			else //noinspection ConstantConditions
				if (data1 != null && data2 == null)
					return data1;
				else if (data1.length == data2.length)
				{
					out = new float[data1.length];
					for (int i = 0; i < data1.length; i++)
						out[i] = (data1[i] + data2[i]) / 2;
				} else if (data1.length > data2.length)
				{
					out = new float[data1.length];
					for (int i = 0; i < data2.length; i++)
						out[i] = (data1[i] + data2[i]) / 2;
					System.arraycopy(data1, data2.length, out, data2.length, data1.length - data2.length);
				} else if (data1.length < data2.length)
				{
					out = new float[data1.length];
					for (int i = 0; i < data1.length; i++)
						out[i] = (data1[i] + data2[i]) / 2;
				} else
					out = new float[0];
		return out;
	}
	
	protected float[] flattenData(float[] data)
	{
		for (int i = 0; i < data.length; i++)
			data[i] *= PApplet.log(i + 2) / 3;
		
		return data;
	}
	
	protected float[] limitData(float[] data)
	{
		for (int i = 0; i < data.length; i++)
		{
			if (data[i] < 0) data[i] = 0;
			if (data[i] > this.main.height) data[i] = this.main.height;
		}
		
		return data;
	}
	
	protected void stroke(int grey)
	{
		this.main.stroke(grey);
	}
	protected void stroke(int r, int g, int b)
	{
		this.main.stroke(r, g, b);
	}
	protected void fill(int grey)
	{
		this.main.fill(grey);
	}
	protected void fill(int r, int g, int b)
	{
		this.main.fill(r, g, b);
	}
	protected void line(float left, float top, float right, float bottom)
	{
		this.main.line(left, top, right, bottom);
	}
	protected void rect(float left, float top, float width, float height)
	{
		this.main.rect(left, top, width, height);
	}
	protected void strokeWeight(float weight)
	{
		this.main.strokeWeight(weight);
	}
	protected int height()
	{
		return this.main.height;
	}
	protected int width()
	{
		return this.main.width;
	}
}