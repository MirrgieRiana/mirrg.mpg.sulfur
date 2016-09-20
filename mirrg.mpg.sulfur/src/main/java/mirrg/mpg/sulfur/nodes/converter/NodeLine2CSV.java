package mirrg.mpg.sulfur.nodes.converter;

import mirrg.mpg.sulfur.node.Node;
import mirrg.mpg.sulfur.node.pin.PinInput;
import mirrg.mpg.sulfur.node.pin.PinOutput;

public class NodeLine2CSV extends Node
{

	public final PinInput<String> pinInput;
	public final PinOutput<String[]> pinOutput;

	public NodeLine2CSV()
	{
		addPinInput(pinInput = new PinInput<String>() {

			@Override
			protected void onClosed()
			{
				pinOutput.fireClose();
			}

			@Override
			protected void accept(String packet)
			{
				pinOutput.fire(packet.split("\\s*,\\s*"));
			}

		});
		addPinOutput(pinOutput = new PinOutput<>());
	}

}
