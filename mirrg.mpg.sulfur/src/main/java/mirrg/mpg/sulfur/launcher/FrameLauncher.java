package mirrg.mpg.sulfur.launcher;

import static mirrg.helium.swing.nitrogen.util.HSwing.*;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

import mirrg.mpg.sulfur.Main;

public class FrameLauncher extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7534931617759346755L;
	public JList<File> listConfigs;
	public JTextPane textPaneLog;
	public DefaultStyledDocument documentLog;
	public Logger logger;

	public FrameLauncher()
	{
		super("MPG Sulfur");
		try {
			setIconImage(ImageIO.read(new File("icon.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setLayout(new CardLayout());

		add(createSplitPaneHorizontal(
			createBorderPanelDown(
				createScrollPane(get(() -> {
					listConfigs = new JList<>();
					listConfigs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					return listConfigs;
				}), 100, 200),
				createBorderPanelLeft(
					createButton("再読み込み", e -> {
						reloadFileList();
					}),
					createButton("実行", e -> {
						File file = listConfigs.getSelectedValue();
						if (file != null) {
							run(file);
						}
					}),
					null)),
			createBorderPanelDown(
				process(
					createScrollPane(get(() -> {
						documentLog = new DefaultStyledDocument();
						logger = new Logger(documentLog);
						textPaneLog = new JTextPane(documentLog);
						textPaneLog.setFont(new Font("MS Gothic", Font.PLAIN, 11));
						return textPaneLog;
					}), 600, 400),
					s -> {
						logger.scrollPane = s;
					}),
				createBorderPanelRight(
					null,
					createButton("クリア", e -> {
						try {
							documentLog.remove(0, documentLog.getLength());
						} catch (BadLocationException e1) {
							logger.pushColor(Color.red);
							e1.printStackTrace(logger.out);
							logger.popColor();
						}
					})))));

		setLocationByPlatform(true);
		pack();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	public void run(File file)
	{
		new Thread(() -> {
			logger.appendText("[begin]\n", Color.orange);
			try {
				ArrayList<Thread> threads = Main.main(file);
				for (Thread thread : threads) {
					thread.join();
				}
			} catch (Exception e1) {
				logger.pushColor(Color.red);
				e1.printStackTrace(logger.out);
				logger.popColor();
			}
			logger.appendText("[end]\n", Color.orange);
		}).start();
	}

	public void init()
	{
		reloadFileList();
	}

	public void reloadFileList()
	{
		listConfigs.setListData(Stream.of(new File(".").listFiles())
			.filter(f -> f.getName().endsWith(".mpg.json"))
			.toArray(File[]::new));
	}

}
