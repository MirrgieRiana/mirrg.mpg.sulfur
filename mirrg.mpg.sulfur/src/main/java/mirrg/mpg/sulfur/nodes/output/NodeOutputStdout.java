package mirrg.mpg.sulfur.nodes.output;

import mirrg.mpg.sulfur.Main;
import mirrg.mpg.sulfur.node.Node;
import mirrg.mpg.sulfur.node.pin.PinInput;

public class NodeOutputStdout extends Node
{

	public final PinInput<String> pinInput;

	public NodeOutputStdout()
	{
		addPinInput(pinInput = new PinInput<String>() {

			@Override
			protected void accept(String packet)
			{
				Main.out.println(packet);
			}

			@Override
			protected void onClosed()
			{

			}

		});
	}

}
