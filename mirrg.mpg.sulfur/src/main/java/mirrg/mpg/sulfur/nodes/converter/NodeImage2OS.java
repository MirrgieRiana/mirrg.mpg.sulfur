package mirrg.mpg.sulfur.nodes.converter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import mirrg.helium.standard.hydrogen.struct.Tuple3;
import mirrg.mpg.sulfur.node.Node;
import mirrg.mpg.sulfur.node.pin.InputPin;
import mirrg.mpg.sulfur.node.pin.OutputPin;

public class NodeImage2OS extends Node
{

	public final InputPin<BufferedImage> inputPin;
	public final OutputPin<Tuple3<byte[], Integer, Integer>> outputPin;

	public NodeImage2OS()
	{
		addInputPin(inputPin = new InputPin<BufferedImage>() {

			@Override
			protected void onClosed()
			{
				outputPin.fireClose();
			}

			@Override
			protected void accept(BufferedImage packet)
			{
				try {
					ImageIO.write(packet, "png", new OutputStream() {

						@Override
						public void write(int b) throws IOException
						{
							write(new byte[] {
								(byte) b
							}, 0, 1);
						}

						@Override
						public void write(byte[] b, int off, int len) throws IOException
						{
							outputPin.fire(new Tuple3<>(b, off, len));
						}

					});
				} catch (IOException e) {
					e.printStackTrace();
				}
				outputPin.fireClose();
			}

		});
		addOutputPin(outputPin = new OutputPin<>());
	}

}
