package net.tlf.soundreader.gfx;

import javax.swing.*;

import java.awt.*;
import java.util.Random;

import net.tlf.soundreader.main.SoundReader;

/**
 * @author thislooksfun
 */
public class Display extends JPanel
{
	Random r = new Random();
	
	private int count = 10;
	
//	public byte data[] = new byte[(count*2)+1]; //TODO Wave things
	public byte data[] = new byte[count];       //TODO Actual data
	private float[] heights = new float[count];
	private Color[] colors = new Color[count];
	
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
		
		float pos = ((float)Math.ceil(this.w.getWidth() / (float)this.count-1));
		
		int diff = (int)(this.w.getWidth() - pos*this.count);
		
		g.setColor(Color.BLACK);
		
		g.drawString("" + SoundReader.reader.updatesPerSecond, 10, 15);
		
		int nextStart = 0;
		
		for (int i = 0; i < count; i ++)
		{
//			g.setColor(this.colors[i]);
			
			int height = ((int)(this.w.getHeight() * this.heights[i]));
			g.fillRect(nextStart, this.w.getHeight() - height - 22, (int)pos-(diff >= 0 ? 0 : 1), height);
			
			
			nextStart += pos+(diff >= 0 ? 1 : 0);
			if (diff >= 0) {
				diff--;
			}
		}
	}
	
	public void convertHeights()
	{
		for (int i = 0; i < count; i++) {
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
		for (int i = 0; i < count; i ++)
		{
			this.heights[i] = this.r.nextFloat();
		}
	}
	
	public void setCount(int i)
	{
		if (i <= 0) {
			i = 1;
		} else if (i > (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2)) {
			i = (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2);
		}
		
		System.out.println(i + " : " + Toolkit.getDefaultToolkit().getScreenSize().getWidth());
		
		this.count = i;
		this.data = new byte[(i*2)+1]; //TODO Wave things
//		this.data = new byte[i];       //TODO Actual data
		this.heights = new float[i];
		this.colors = new Color[i];
		
		this.nextColor();
		
		this.w.setMinimumSize(new Dimension(i*2, this.w.getMinimumSize().height));
	}
	
	public int getCount()
	{
		return this.count;
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