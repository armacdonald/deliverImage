package deliverImage;

import java.nio.file.*;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import deliverImage.Image;
import deliverImage.InputImages;

public class InputImagesTest {
	@Test
	@SuppressWarnings({"unchecked"})
	public void testImagesFound() {
		for (Image image : new InputImages("TestImages"))
			assertThat(image.getName(), anyOf(
				equalTo(Paths.get("test1.jpg")),
				equalTo(Paths.get("test2.jpg")),
				equalTo(Paths.get("test3.jpg")),
				equalTo(Paths.get("test4.jpg")),
				equalTo(Paths.get("test5.jpg")),
				equalTo(Paths.get("test6.jpg")),
				equalTo(Paths.get("test7.jpg")),
				equalTo(Paths.get("test8.jpg"))));
	}
}
