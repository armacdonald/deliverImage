package deliverImage;

import java.nio.file.*;
import java.util.AbstractList;
import java.util.stream.*;

public class InputImages extends AbstractList<Image> {
	private Image[] images;

	public InputImages(String dir) {
		PathMatcher jpg
			= FileSystems.getDefault().getPathMatcher("glob:**/*.jpg");
		try (Stream<Path> files = Files.list(Paths.get(dir))) {
			images = files.filter(jpg::matches)
			              .map(path -> new Image(path))
			              .toArray(Image[]::new);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Image get(int index) { return images[index]; }

	public int size() { return images.length; }
}
