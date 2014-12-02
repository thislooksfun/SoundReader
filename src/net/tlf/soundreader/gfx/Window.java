package net.tlf.soundreader.gfx;

import javax.swing.*;

import java.awt.*;

/**
 * @author thislooksfun
 */
public class Window extends JFrame
{
	public final Display display = new Display(this);
	
	public Window()
	{
		initUI();
	}
	
	private void initUI()
	{
		this.setTitle("SoundReader");
		
		this.add(this.display);
		
		this.setSize(1000, 800);
		this.setMinimumSize(new Dimension(100, 100));
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		this.getRootPane().putClientProperty("apple.awt.fullscreenable", true);
	}
}