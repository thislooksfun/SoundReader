package net.tlf.soundreader.gfx;

import javax.swing.*;

import java.awt.*;
import java.util.Random;

import net.tlf.soundreader.main.Options;
import net.tlf.soundreader.main.SoundReader;
import net.tlf.soundreader.util.Utils;

/**
 * @author thislooksfun
 */
public class Display extends JPanel
{
	Random r = new Random();
	
//	public byte data[] = new byte[(count*2)+1]; //TODO Wave things
	public byte data[] = new byte[Options.count];       //TODO Actual data
	private float[] heights = new float[Options.count];
	private Color[] colors = new Color[Options.count];
	
	protected Window w;
	
	public Display(Window window)
	{
		this.w = window;
		for (int i = 0; i < heights.length; i++)
		{
			heights[i] = 0.5F;
		}
		
		this.nextColor();
	}
	
	private void draw(Graphics2D g)
	{
		this.convertHeights();
		
		float pos = ((float)Math.ceil(this.w.getWidth() / (float)Options.count-1));
		
		int diff = (int)(this.w.getWidth() - pos * Options.count);
		
		g.setColor(Color.BLACK);
		
		g.drawString("" + SoundReader.reader.updatesPerSecond, 10, 15);
		
		int nextStart = 0;
		
		for (int i = 0; i < Options.count; i ++)
		{
//			g.setColor(this.colors[i]);
			
			int height = ((int)(this.getHeight() * this.heights[i]));
			g.fillRect(nextStart, this.getHeight() - height, (int)pos-((diff >= 0 ? 0 : 1) - (Options.gaps() ? 0 : 1)), height);
			
			nextStart += pos+(diff >= 0 ? 1 : 0);
			if (diff >= 0) {
				diff--;
			}
		}
	}
	
	public void convertHeights()
	{
		for (int i = 0; i < Options.count; i++) {
			this.heights[i] = (data[(i*2)+1] + 200F)/400F; //TODO Wave things
//			this.heights[i] = (data[i] + 200F)/400F;       //TODO Actual data
		}
	}
	
	public void update()
	{
//		this.nextHeight();
	}
	
	public void nextHeight()
	{
		for (int i = 0; i < Options.count; i ++)
		{
			this.heights[i] = this.r.nextFloat();
		}
	}
	
	public void setCount(int i) //TODO
	{
		int screenWidth = Utils.getScreenWidth();
		if (i <= 0) {
			i = 1;
		} else if (i > (int)(screenWidth / (Options.gaps() ? 2 : 1))) {
			i = (int)(screenWidth / (Options.gaps() ? 2 : 1));
		}
		
		System.out.println(i + " : " + screenWidth);
		
		Options.count = i;
		this.data = new byte[(i*2)+1]; //TODO Wave things
//		this.data = new byte[i];       //TODO Actual data
		this.heights = new float[i];
		this.colors = new Color[i];
		
		this.nextColor();
		this.convertHeights();
		
		this.setMinSizes(i, this.w.getMinimumSize().height);
	}
	
	private void setMinSizes(int width, int height)
	{
		if (width > Utils.getScreenWidth()) {
			width = Utils.getScreenWidth();
		}
		this.w.setMinimumSize(new Dimension(width*(Options.gaps() ? 2 : 1), height));
		
		if (this.w.getX() + this.w.getWidth() > Utils.getScreenWidth())
		{
			this.w.setLocation(Utils.getScreenWidth() - this.w.getWidth(), this.w.getY());
		}
	}
	
	private void nextColor()
	{
		for (int i = 0; i < colors.length; i++)
		{
			colors[i] = new Color(this.r.nextInt(255), this.r.nextInt(255), this.r.nextInt(255));
//			colors[i] = new Color(0, 0, 0);
		}
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		draw(((Graphics2D)g));
	}
}