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
		setTitle("SoundReader");
		
		add(this.display);
		
		setSize(1000, 800);
		setMinimumSize(new Dimension(100, 100));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
}