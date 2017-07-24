package deliverImage;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import java.nio.file.*;
import java.util.*;
import deliverImage.DeliverImage;

public class DeliverImageTest {
	@Test
	public void testMatches() {
		Map<Path, Path> result = new HashMap<>();

		IConfig config = new IConfig() {
			public String getInputDirectory() { return "TestImages"; }
			public List<CustomerConfig> getCustomerConfigs() {
				return Arrays.asList(
					new CustomerConfig(".*Editorial Contributor.*", "newspaper"),
					new CustomerConfig(".*Sport Contributor.*", "sport magazine"),
					new CustomerConfig(".*(Singer|Dance|Showbiz Contributor).*", "showbiz website"));
			}
		};

		new DeliverImage(config).perform((input, output) -> result.put(input, output));

		assertThat(result.get(Paths.get("TestImages/test1.jpg")), is(Paths.get("sport magazine/test1.jpg")));
		assertThat(result.get(Paths.get("TestImages/test2.jpg")), is(Paths.get("sport magazine/test2.jpg")));
		assertThat(result.get(Paths.get("TestImages/test3.jpg")), is(Paths.get("showbiz website/test3.jpg")));
		assertThat(result.get(Paths.get("TestImages/test4.jpg")), is(Paths.get("showbiz website/test4.jpg")));
		assertThat(result.get(Paths.get("TestImages/test5.jpg")), is(Paths.get("showbiz website/test5.jpg")));
		assertThat(result.get(Paths.get("TestImages/test6.jpg")), is(Paths.get("newspaper/test6.jpg")));
		assertThat(result.get(Paths.get("TestImages/test7.jpg")), is(Paths.get("newspaper/test7.jpg")));
		assertThat(result.get(Paths.get("TestImages/test8.jpg")), is(Paths.get("newspaper/test8.jpg")));
	}
}
