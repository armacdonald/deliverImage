package deliverImage;

import static java.lang.System.out;
import java.nio.file.*;
import java.util.*;

public class DeliverImage {
	public interface Action {
		void action(Path input, Path output);
	}

	private final IConfig config;

	public DeliverImage(IConfig config) {
		this.config = config;
	}

	public void perform(Action action) {
		InputImages input = new InputImages(config.getInputDirectory());
		for (Image image : input) {
			for (CustomerConfig customer : config.getCustomerConfigs()) {
				if (customer.getCopyrightPattern()
				            .matcher(image.getCopyright())
				            .matches()) {
					action.action(image.getPath(),
					              customer.getOutputDir()
					                      .resolve(image.getName()));
					break;
				}
			}
		}
	}


	public static void main(String[] args) {
		// The config should be parsed from the command line or a file by
		// creating classes that implement IConfig and load from the appropriate
		// source. For now we will create a config object with hardcoded values.
		IConfig config = new IConfig() {
			public String getInputDirectory() { return "TestImages"; }
			public List<CustomerConfig> getCustomerConfigs() {
				return Arrays.asList(
					new CustomerConfig(".*Editorial Contributor.*", "newspaper"),
					new CustomerConfig(".*Sport Contributor.*", "sport magazine"),
					new CustomerConfig(".*(Singer|Dance|Showbiz Contributor).*", "showbiz website"));
			}
		};

		new DeliverImage(config).perform((input, output) -> {
			out.println("Copying " + input + " to " + output);
			try {
				Files.createDirectories(output.subpath(0, output.getNameCount() - 1));
				// Can also copy from an InputStream if source is remote
				Files.copy(input, output, StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
