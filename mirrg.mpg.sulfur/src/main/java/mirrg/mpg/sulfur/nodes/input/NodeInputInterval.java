package mirrg.mpg.sulfur.nodes.input;

import java.time.LocalDateTime;

import mirrg.mpg.sulfur.node.NodeEntryPoint;
import mirrg.mpg.sulfur.node.pin.OutputPin;

public class NodeInputInterval extends NodeEntryPoint
{

	public final OutputPin<LocalDateTime> outputPin;

	public NodeInputInterval()
	{
		addOutputPin(outputPin = new OutputPin<>());
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
			outputPin.fire(LocalDateTime.now());
		}

		outputPin.fireClose();
	}

}
