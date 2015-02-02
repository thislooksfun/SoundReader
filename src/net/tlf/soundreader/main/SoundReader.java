package net.tlf.soundreader.main;

import java.awt.event.KeyEvent;
import java.util.HashMap;

import ddf.minim.AudioInput;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import ddf.minim.analysis.FourierTransform;
import net.tlf.soundreader.render.BarBarRenderer;
import net.tlf.soundreader.render.CurveBarRenderer;
import net.tlf.soundreader.render.DataRenderer;
import net.tlf.soundreader.render.FlatBarRenderer;
import net.tlf.soundreader.util.Keyboard;
import processing.core.PApplet;
import processing.core.PFont;

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
	PFont century;
	
	private HashMap<String, DataRenderer> renderers = new HashMap<>();
	
	@Override
	public void setup()
	{
		size(1680, 1001, P2D);
		if (frame != null) frame.setResizable(true);
		
		Options.updateData(this.width);
		
		century = this.loadFont("HelveticaNeue-Light-15.vlw");
		
		//barSize = (barNumber);
		//start = barSize/2 + 2;
		//space = barSize+2;
		
		minim = new Minim(this);
		//  minim.debugOn();
		
		in = minim.getLineIn(Minim.MONO, 512, 44100);
		fft = new FFT(in.mix.size(), 44100);
		
		fft.window(FourierTransform.HAMMING);
		
		Keyboard.init();
		
		this.addRenderer(new CurveBarRenderer(this));
		this.addRenderer(new FlatBarRenderer(this));
		this.addRenderer(new BarBarRenderer(this));
	}
	
	public void addRenderer(DataRenderer r)
	{
		if (r == null || r.name() == null)
		{
			System.err.println("[addRenderer] ############# ERROR #############");
			System.err.println("[addRenderer] Renderer or name is null!");
			System.err.println("[addRenderer] Renderer: " + r);
			if (r != null)
				System.err.println("[addRenderer] Name: " + r.name());
			System.err.println("[addRenderer] ############# ERROR #############");
			System.err.println();
			return;
		}
		renderers.put(r.name(), r);
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
		float[] rawData = new float[fft.specSize()];
		for (int i = 0; i < fft.specSize(); i++)
			rawData[i] = fft.getBand(i) * Options.scaleFactor;
		
		
		DataRenderer r = this.renderers.get(Options.graphType);
		if (r == null)
		{
			//Just in case the renderer is ever null, default back to the curved renderer
			Options.graphType = "tlf:curve_bar";
			r = this.renderers.get(Options.graphType);
		}
		r.render(r.formatData(rawData));
		
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
			textFont(century);
			this.text("Renderer: " + Options.graphType, 10, 25);
		}
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
			Options.graphType = "tlf:curved_bar";
		} else if (Keyboard.isPressed(KeyEvent.VK_2))
		{
			Options.graphType = "tlf:flat_bar";
		} else if (Keyboard.isPressed(KeyEvent.VK_3))
		{
			Options.graphType = "tlf:bar_bar";
		} else if (Keyboard.isPressed(KeyEvent.VK_0))
		{
			Options.debug = !Options.debug;
		}
	}
}