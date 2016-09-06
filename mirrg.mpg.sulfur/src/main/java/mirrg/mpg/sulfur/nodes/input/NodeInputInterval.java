package mirrg.mpg.sulfur.nodes.input;

import java.time.LocalDateTime;

import mirrg.mpg.sulfur.node.NodeEntryPoint;
import mirrg.mpg.sulfur.node.pin.PinOutput;

public class NodeInputInterval extends NodeEntryPoint
{

	public final PinOutput<LocalDateTime> pinOutput;

	public NodeInputInterval()
	{
		addPinOutput(pinOutput = new PinOutput<>());
	}

	@Override
	public void run()
	{
		long first = System.currentTimeMillis();

		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(first + 1000 * (i + 1) - System.currentTimeMillis());
			} catch (InterruptedException e) {
				break;
			}
			pinOutput.fire(LocalDateTime.now());
		}

		pinOutput.fireClose();
	}

}
