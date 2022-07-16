package youmiel.raidlogger;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RaidLoggerMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	private static final Logger LOGGER = LoggerFactory.getLogger("RaidLogger");

	private static int indent = 0;
	private static String indentString = "";

	public static void increaseIndent() {
		indent++;
		indentString = " ".repeat(indent * 4);
	}

	public static void decreaseIndent() {
		if (indent >= 0) {
			indent--;
			indentString = " ".repeat(indent * 4);
		}
	}

	public static Logger getLogger(){
		return LOGGER;
	}

	public static void debug(String str) {
		LOGGER.debug(indentString + str);
	}

	public static void error(String str) {
		LOGGER.error(indentString + str);
	}

	public static void info(String str) {
		LOGGER.info(indentString + str);
	}

	public static void warn(String str) {
		LOGGER.warn(indentString + str);
	}

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
	}
}
