package mirrg.mpg.sulfur.nodes.input;

import java.io.IOException;
import java.io.InputStream;

import mirrg.helium.standard.hydrogen.struct.Tuple3;
import mirrg.mpg.sulfur.node.NodeEntryPoint;
import mirrg.mpg.sulfur.node.pin.OutputPin;

public class NodeInputStream extends NodeEntryPoint
{

	private InputStream in;
	private int bufferSize;
	public final OutputPin<Tuple3<byte[], Integer, Integer>> outputPin;

	public NodeInputStream(InputStream in, int bufferSize)
	{
		this.in = in;
		this.bufferSize = bufferSize;
		addOutputPin(outputPin = new OutputPin<>());
	}

	@Override
	public void run()
	{

		while (true) {
			byte[] buffer = new byte[bufferSize];
			int length;
			try {
				length = in.read(buffer);
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
			if (length == -1) break;

			outputPin.fire(new Tuple3<>(buffer, 0, length));
		}

		outputPin.fireClose();
	}

}
