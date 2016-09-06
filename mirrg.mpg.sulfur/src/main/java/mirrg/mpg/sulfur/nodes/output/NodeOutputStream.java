package mirrg.mpg.sulfur.nodes.output;

import java.io.IOException;
import java.io.OutputStream;

import mirrg.helium.standard.hydrogen.struct.Tuple3;
import mirrg.mpg.sulfur.node.Node;
import mirrg.mpg.sulfur.node.pin.InputPin;

public class NodeOutputStream extends Node
{

	public final InputPin<Tuple3<byte[], Integer, Integer>> inputPin;

	public NodeOutputStream(OutputStream out)
	{
		addInputPin(inputPin = new InputPin<Tuple3<byte[], Integer, Integer>>() {

			@Override
			protected void accept(Tuple3<byte[], Integer, Integer> packet)
			{
				try {
					out.write(packet.getX(), packet.getY(), packet.getZ());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			protected void onClosed()
			{
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		});
	}

}
