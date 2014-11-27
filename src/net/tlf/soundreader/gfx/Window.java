package net.tlf.soundreader.gfx;

import javax.swing.*;

import java.awt.*;

/**
 * @author thislooksfun
 */
public class Window extends JFrame
{
	public final Surface surface = new Surface(this);
	
	public Window()
	{
		initUI();
	}
	
	private void initUI()
	{
		setTitle("Hello!");
		
		add(this.surface);
		
		setSize(1000, 800);
		setMinimumSize(new Dimension(100, 100));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
}