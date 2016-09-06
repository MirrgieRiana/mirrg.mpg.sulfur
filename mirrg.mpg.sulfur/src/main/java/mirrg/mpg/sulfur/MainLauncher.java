package mirrg.mpg.sulfur;

import mirrg.helium.swing.nitrogen.util.HSwing;
import mirrg.mpg.sulfur.launcher.FrameLauncher;

public class MainLauncher
{

	public static void main(String[] args)
	{
		HSwing.setWindowsLookAndFeel();

		FrameLauncher frame = new FrameLauncher();

		Main.out = frame.logger.out;

		frame.init();
		frame.setVisible(true);
	}

}
