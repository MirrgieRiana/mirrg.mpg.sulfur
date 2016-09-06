package mirrg.mpg.sulfur.nodes.converter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import mirrg.helium.standard.hydrogen.struct.Tuple3;
import mirrg.mpg.sulfur.node.Node;
import mirrg.mpg.sulfur.node.pin.PinInput;
import mirrg.mpg.sulfur.node.pin.PinOutput;

public class NodeImage2OS extends Node
{

	public final PinInput<BufferedImage> pinInput;
	public final PinOutput<Tuple3<byte[], Integer, Integer>> pinOutput;

	public NodeImage2OS()
	{
		addPinInput(pinInput = new PinInput<BufferedImage>() {

			@Override
			protected void onClosed()
			{
				pinOutput.fireClose();
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
							pinOutput.fire(new Tuple3<>(b, off, len));
						}

					});
				} catch (IOException e) {
					e.printStackTrace();
				}
				pinOutput.fireClose();
			}

		});
		addPinOutput(pinOutput = new PinOutput<>());
	}

}
