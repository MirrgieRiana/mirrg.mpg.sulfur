package mirrg.mpg.sulfur.nodes.converter;

import mirrg.mpg.sulfur.node.Node;
import mirrg.mpg.sulfur.node.pin.PinInput;
import mirrg.mpg.sulfur.node.pin.PinOutput;

public class NodeFlatMap<T> extends Node
{

	public final PinInput<T[]> pinInput;
	public final PinOutput<T> pinOutput;

	public NodeFlatMap()
	{
		addPinInput(pinInput = new PinInput<T[]>() {

			@Override
			protected void onClosed()
			{
				pinOutput.fireClose();
			}

			@Override
			protected void accept(T[] packet)
			{
				for (T t : packet) {
					pinOutput.fire(t);
				}
			}

		});
		addPinOutput(pinOutput = new PinOutput<>());
	}

}
