package mirrg.mpg.sulfur.nodes.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import mirrg.mpg.sulfur.node.NodeEntryPoint;
import mirrg.mpg.sulfur.node.pin.PinOutput;

public class NodeInputLines extends NodeEntryPoint
{

	private File file;
	public final PinOutput<String> pinOutput;

	public NodeInputLines(File file)
	{
		this.file = file;
		addPinOutput(pinOutput = new PinOutput<>());
	}

	@Override
	public void run()
	{
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();

			pinOutput.fireClose();
			return;
		}

		in.lines()
			.forEach(pinOutput::fire);

		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		pinOutput.fireClose();
	}

}
