package mirrg.mpg.sulfur.nodes.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import mirrg.mpg.sulfur.node.NodeEntryPoint;
import mirrg.mpg.sulfur.node.pin.OutputPin;

public class NodeInputCSV extends NodeEntryPoint
{

	private File file;
	public final OutputPin<String[]> outputPin;

	public NodeInputCSV(File file)
	{
		this.file = file;
		addOutputPin(outputPin = new OutputPin<>());
	}

	@Override
	public void run()
	{
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();

			outputPin.fireClose();
			return;
		}

		in.lines()
			.map(l -> l.split("\\s*,\\s*"))
			.forEach(outputPin::fire);

		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		outputPin.fireClose();
	}

}
