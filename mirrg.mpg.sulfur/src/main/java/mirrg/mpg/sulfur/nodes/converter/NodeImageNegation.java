package mirrg.mpg.sulfur.nodes.converter;

import java.awt.image.BufferedImage;

import mirrg.mpg.sulfur.node.Node;
import mirrg.mpg.sulfur.node.pin.PinInput;
import mirrg.mpg.sulfur.node.pin.PinOutput;

public class NodeImageNegation extends Node
{

	public final PinInput<BufferedImage> pinInput;
	public final PinOutput<BufferedImage> pinOutput;

	public NodeImageNegation()
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
				BufferedImage dest = new BufferedImage(packet.getWidth(), packet.getHeight(), BufferedImage.TYPE_INT_ARGB);

				for (int x = 0; x < packet.getWidth(); x++) {
					for (int y = 0; y < packet.getHeight(); y++) {
						int rgb = packet.getRGB(x, y);
						int a = (rgb >> 24) & 0xff;
						int r = (rgb >> 16) & 0xff;
						int g = (rgb >> 8) & 0xff;
						int b = (rgb >> 0) & 0xff;

						a = 255;
						r = ~r;
						g = ~g;
						b = ~b;

						dest.setRGB(x, y, ((a & 0xff) << 24)
							| ((r & 0xff) << 16)
							| ((g & 0xff) << 8)
							| ((b & 0xff) << 0));
					}
				}

				pinOutput.fire(dest);
			}

		});
		addPinOutput(pinOutput = new PinOutput<>());
	}

}
