package mirrg.mpg.sulfur.nodes.converter;

import mirrg.mpg.sulfur.node.Node;
import mirrg.mpg.sulfur.node.pin.InputPin;
import mirrg.mpg.sulfur.node.pin.OutputPin;

public class NodeToString extends Node
{

	public final InputPin<Object> inputPin;
	public final OutputPin<String> outputPin;

	public NodeToString()
	{
		addInputPin(inputPin = new InputPin<Object>() {

			@Override
			protected void onClosed()
			{
				outputPin.fireClose();
			}

			@Override
			protected void accept(Object packet)
			{
				outputPin.fire(packet.toString());
			}

		});
		addOutputPin(outputPin = new OutputPin<>());
	}

}
