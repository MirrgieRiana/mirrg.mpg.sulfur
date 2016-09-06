package mirrg.mpg.sulfur.nodes.converter;

import mirrg.mpg.sulfur.node.Node;
import mirrg.mpg.sulfur.node.pin.PinInput;
import mirrg.mpg.sulfur.node.pin.PinOutput;

public class NodeToString extends Node
{

	public final PinInput<Object> pinInput;
	public final PinOutput<String> pinOutput;

	public NodeToString()
	{
		addPinInput(pinInput = new PinInput<Object>() {

			@Override
			protected void onClosed()
			{
				pinOutput.fireClose();
			}

			@Override
			protected void accept(Object packet)
			{
				pinOutput.fire(packet.toString());
			}

		});
		addPinOutput(pinOutput = new PinOutput<>());
	}

}
