package deliverImage;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import deliverImage.Image;

public class ImageTest {
	@Test
	public void testNameMatches() {
		assertThat(new Image("TestImages/test1.jpg").getName().toString(), is("test1.jpg"));
		assertThat(new Image("TestImages/test2.jpg").getName().toString(), is("test2.jpg"));
		assertThat(new Image("TestImages/test3.jpg").getName().toString(), is("test3.jpg"));
		assertThat(new Image("TestImages/test4.jpg").getName().toString(), is("test4.jpg"));
		assertThat(new Image("TestImages/test5.jpg").getName().toString(), is("test5.jpg"));
		assertThat(new Image("TestImages/test6.jpg").getName().toString(), is("test6.jpg"));
		assertThat(new Image("TestImages/test7.jpg").getName().toString(), is("test7.jpg"));
		assertThat(new Image("TestImages/test8.jpg").getName().toString(), is("test8.jpg"));
	}

	@Test
	public void testCopyrightMatches() {
		assertThat(new Image("TestImages/test1.jpg").getCopyright(), is("Sport Contributor"));
		assertThat(new Image("TestImages/test2.jpg").getCopyright(), is("Sport Contributor"));
		assertThat(new Image("TestImages/test3.jpg").getCopyright(), is("Showbiz Contributor"));
		assertThat(new Image("TestImages/test4.jpg").getCopyright(), is("Singer and Dance Contributor"));
		assertThat(new Image("TestImages/test5.jpg").getCopyright(), is("Singer Contributor"));
		assertThat(new Image("TestImages/test6.jpg").getCopyright(), is("Editorial Contributor"));
		assertThat(new Image("TestImages/test7.jpg").getCopyright(), is("Editorial Contributor"));
		assertThat(new Image("TestImages/test8.jpg").getCopyright(), is("Editorial Contributor"));
	}
}
