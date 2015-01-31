package net.tlf.soundreader.main;

import java.awt.event.KeyEvent;

import ddf.minim.AudioInput;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import ddf.minim.analysis.FourierTransform;
import net.tlf.soundreader.util.Keyboard;
import processing.core.PApplet;

/**
 * @author thislooksfun
 */
public class SoundReader extends PApplet
{
	//Program variables
	long lastUpdate = -1;
	Minim minim;
	AudioInput in;
	FFT fft;
	float[] lastData = new float[0];
	
	public static void main(String[] args)
	{
		PApplet.main(new String[]{"net.tlf.soundreader.main.SoundReader"});
	}
	
	@Override
	public void setup()
	{
		size(1680, 1001, P2D);
		if (frame != null) frame.setResizable(true);
		
		Options.updateData(this.width);
		
		//barSize = (barNumber);
		//start = barSize/2 + 2;
		//space = barSize+2;
		
		minim = new Minim(this);
		//  minim.debugOn();
		
		in = minim.getLineIn(Minim.MONO, 512, 44100);
		fft = new FFT(in.mix.size(), 44100);
		
		fft.window(FourierTransform.HAMMING);
		
		Keyboard.init();
	}
	
	@Override
	public void draw()
	{
		this.checkKeys();
		
		long time = System.currentTimeMillis();
		if (time < lastUpdate + Options.timeBetween) return;
		lastUpdate = time;
		
		background(0, 0, 0);
		
		fft.forward(in.mix);
		float[] tempData = new float[fft.specSize()];
		for (int i = 0; i < fft.specSize(); i++)
			tempData[i] = fft.getBand(i) * Options.scaleFactor;
		
		float[] data = limitData(flattenData(avgData(cullData(tempData, Options.barNumber), lastData)));
		lastData = data;
		
		stroke(100, 0, 0);
		switch (Options.graphType)
		{
			case 0:
				this.renderCurveBar(data);
				break;
			case 1:
				this.renderStraightBar(data);
				break;
			case 2:
				this.renderBarBar(data);
				break;
		}
		
		//println(fft.getFreq(110));
		// draw the waveforms
 	 	/*for (int i = 0; i < in.bufferSize() - 1; i++)
  		{
			line(i, 50 + in.left.get(i)*50, i+1, 50 + in.left.get(i+1)*50);
			line(i, 150 + in.right.get(i)*50, i+1, 150 + in.right.get(i+1)*50);
		}*/
		
		if (Options.debug)
		{
			stroke(100);
			fill(255);
			this.text(Options.graphType + " : " + (Options.graphType == 0 ? "Curved bar graph" : (Options.graphType == 1 ? "Straight bar graph" : "Bar bar graph")), 10, 20);
		}
	}
	
	/** Bar graph made of lines with rounded ends */
	private void renderCurveBar(float[] data)
	{
		strokeWeight(Options.barLineSize);
		for (int i = 0; i < data.length-1; i++)
		{
			if (Options.debug && i == data.length - 2) stroke(0, 100, 0);
			line(Options.start + (i * Options.space), height - data[i+1] - Options.restingSize, Options.start + (i * Options.space), height);
		}
	}
	
	/** Bar graph made of lines with straight ends */
	private void renderStraightBar(float[] data)
	{
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
			rect(left, height - data[i+1] - Options.restingSize - 5, Options.barSize, height);
		}
	}
	
	/** Bar graph made of fixed horizontal lines */
	private void renderBarBar(float[] data)
	{
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
			rect(left, height - data[i] - Options.restingSize - 5, Options.barSize, height);
		}
	}
	
	float[] cullData(float[] data, int size)
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
	
	float[] avgData(float[] data1, float[] data2)
	{
		float[] out;
		if (data1.length == data2.length)
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
	
	float[] flattenData(float[] data)
	{
		for (int i = 0; i < data.length; i++)
			data[i] *= (float)Math.log(i + 2) / 3;
		
		return data;
	}
	
	float[] limitData(float[] data)
	{
		for (int i = 0; i < data.length; i++)
		{
			if (data[i] < 0) data[i] = 0;
			if (data[i] > height) data[i] = height;
		}
		
		return data;
	}
	
	@Override
	public void stop()
	{
		// always close Minim audio classes when you are done with them
		in.close();
		minim.stop();
		
		super.stop();
	}
	
	public void checkKeys()
	{
		if (Keyboard.isPressed(KeyEvent.VK_R))
		{
			Options.setNumBars(80, true, this.width);
		} else if (Keyboard.isPressed(KeyEvent.VK_1))
		{
			Options.graphType = 0;
		} else if (Keyboard.isPressed(KeyEvent.VK_2))
		{
			Options.graphType = 1;
		} else if (Keyboard.isPressed(KeyEvent.VK_3))
		{
			Options.graphType = 2;
		} else if (Keyboard.isPressed(KeyEvent.VK_0))
		{
			Options.debug = !Options.debug;
		}
	}
}