package mirrg.mpg.sulfur.nodes.converter;

import mirrg.mpg.sulfur.node.Node;
import mirrg.mpg.sulfur.node.pin.InputPin;
import mirrg.mpg.sulfur.node.pin.OutputPin;

public class NodeFlatMap<T> extends Node
{

	public final InputPin<T[]> inputPin;
	public final OutputPin<T> outputPin;

	public NodeFlatMap()
	{
		addInputPin(inputPin = new InputPin<T[]>() {

			@Override
			protected void onClosed()
			{
				outputPin.fireClose();
			}

			@Override
			protected void accept(T[] packet)
			{
				for (T t : packet) {
					outputPin.fire(t);
				}
			}

		});
		addOutputPin(outputPin = new OutputPin<>());
	}

}
