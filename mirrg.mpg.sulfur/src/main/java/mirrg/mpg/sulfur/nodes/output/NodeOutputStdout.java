package mirrg.mpg.sulfur.nodes.output;

import mirrg.mpg.sulfur.Main;
import mirrg.mpg.sulfur.node.Node;
import mirrg.mpg.sulfur.node.pin.InputPin;

public class NodeOutputStdout extends Node
{

	public final InputPin<String> inputPin;

	public NodeOutputStdout()
	{
		addInputPin(inputPin = new InputPin<String>() {

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
