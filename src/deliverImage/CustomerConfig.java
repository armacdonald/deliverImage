package deliverImage;

import java.nio.file.*;
import java.util.regex.*;

public class CustomerConfig {
	private Pattern copyrightPattern;
	private Path outputDir;

	public CustomerConfig(String copyrightPattern, String outputDir) {
		this.copyrightPattern = Pattern.compile(copyrightPattern);
		this.outputDir = Paths.get(outputDir);
	}

	public Pattern getCopyrightPattern() { return copyrightPattern; }

	public Path getOutputDir() { return outputDir; }
}
