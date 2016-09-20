package mirrg.mpg.sulfur.launcher;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class Logger
{

	public JScrollPane scrollPane;
	private DefaultStyledDocument documentLog;
	public PrintStream out = new PrintStream(new OutputStream() {

		@Override
		public void write(int b) throws IOException
		{
			appendText(String.valueOf((char) b));
		}

		@Override
		public void write(byte[] b, int off, int len) throws IOException
		{
			appendText(new String(b, off, len));
		}

	});
	private ArrayList<Color> currentColorStack = new ArrayList<>();

	public Logger(DefaultStyledDocument documentLog)
	{
		this.documentLog = documentLog;
		currentColorStack.add(Color.black);
	}

	public void appendText(String string)
	{
		appendText(string, currentColorStack.get(currentColorStack.size() - 1));
	}

	public void appendText(String string, Color color)
	{
		boolean sholdScroll = false;
		JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
		if (scrollPane != null) {
			int value = scrollBar.getValue();
			int maximum = scrollBar.getMaximum();
			int visibleAmount = scrollBar.getVisibleAmount();
			if (value >= maximum - visibleAmount - 25) {
				sholdScroll = true;
			}
		}

		SimpleAttributeSet attr = new SimpleAttributeSet();
		attr.addAttribute(StyleConstants.Foreground, color);
		try {
			documentLog.insertString(documentLog.getLength(), string, attr);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

		if (sholdScroll) {
			SwingUtilities.invokeLater(() -> {
				scrollBar.setValue(scrollBar.getMaximum());
			});
		}
	}

	public void pushColor(Color color)
	{
		currentColorStack.add(color);
	}

	public void popColor()
	{
		currentColorStack.remove(currentColorStack.size() - 1);
	}

}
