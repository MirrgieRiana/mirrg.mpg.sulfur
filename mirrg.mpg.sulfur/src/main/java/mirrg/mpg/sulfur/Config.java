package mirrg.mpg.sulfur;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;

public class Config
{

	public ConfigNode[] nodes;
	public ConfigConnection[] connections;

	public static class ConfigNode
	{

		public String name;
		public String id;
		public Object argument;

	}

	public static class ConfigConnection
	{

		public ConfigDestination from;
		public ConfigDestination to;

	}

	public static class ConfigDestination
	{

		public String node;
		public int pin;

	}

	public static Config load(File fileJson) throws JSONException, FileNotFoundException, IOException
	{
		return JSON.decode(new FileInputStream(fileJson), Config.class);
	}

}
