package mirrg.mpg.sulfur.nodes.input;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import mirrg.mpg.sulfur.Main;
import mirrg.mpg.sulfur.node.NodeEntryPoint;
import mirrg.mpg.sulfur.node.pin.PinOutput;

public class NodeInputSerialLine extends NodeEntryPoint
{

	private InputStream in;
	public final PinOutput<String> pinOutput;

	public NodeInputSerialLine(InputStream in)
	{
		this.in = in;
		addPinOutput(pinOutput = new PinOutput<>());
	}

	@Override
	public void run()
	{
		boolean ignoreN = false;
		ArrayList<Byte> list = new ArrayList<>();

		while (true) {
			int b;
			try {
				b = read();
			} catch (IOException e) {
				e.printStackTrace(Main.out);
				pinOutput.fireClose();
				return;
			}

			if (b == '\r') {
				fire(list);
				ignoreN = true;
			} else if (b == '\n') {
				if (!ignoreN) fire(list);
				ignoreN = false;
			} else {
				list.add(Byte.valueOf((byte) b));
				ignoreN = false;
			}
		}
	}

	private void fire(ArrayList<Byte> list)
	{
		byte[] bytes = new byte[list.size()];
		for (int i = 0; i < list.size(); i++) {
			bytes[i] = list.get(i);
		}
		pinOutput.fire(new String(bytes));
		list.clear();
	}

	private int read() throws IOException
	{
		while (true) {
			int b = in.read();

			if (b >= 0) {
				return b;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					return -1;
				}
			}
		}
	}

}
