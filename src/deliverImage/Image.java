package deliverImage;

import com.adobe.xmp.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Image {
	private static final int M_SOI = 0xd8;	// JPEG File start marker
	private static final int M_APP1 = 0xe1;	// JPEG Marker for Exif or XMP
	private static final int M_SOS = 0xda;	// JPEG Image data marker
	private static final byte[] XMP_HEADER
		= "http://ns.adobe.com/xap/1.0/\0".getBytes();
	private static final String COPYRIGHT_NS
		= "http://purl.org/dc/elements/1.1/";
	private static final String COPYRIGHT = "dc:rights";

	private static byte[] header = new byte[XMP_HEADER.length];

	private final Path path;
	private String copyright;

	public Image(String path) { this(Paths.get(path)); }

	public Image(Path path) {
		this.path = path;
		copyright = "";

		try (InputStream file = Files.newInputStream(path);
		     InputStream in = new BufferedInputStream(file)) {
			copyright = XMPMetaFactory
							.parseFromBuffer(readJpegXMP(in))
							.getArrayItem(COPYRIGHT_NS, COPYRIGHT, 1)
							.getValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Path getName() { return path.getFileName(); }

	public Path getPath() { return path; }

	public String getCopyright() { return copyright; }

	// Manually find and read the XMP section in a JPEG file.
	// This should be replaced by a generic image parsing library to support
	// more than one file type and extended XMP sections.
	private byte[] readJpegXMP(InputStream is) throws IOException {
		// Check JPEG start of file marker
		if (is.read() != 0xff || is.read() != M_SOI)
			throw new IOException("Failed to find XMP metadata in " + path);

		int c;
		while ((c = is.read()) != -1) {
			if (c != 0xff)
				throw new IOException("Failed to find XMP metadata in " + path);

			while ((c = is.read()) == 0xff);	// Skip padding bytes
			if (c == -1)
				throw new IOException("Failed to find XMP metadata in " + path);

			// M_SOS indicates the image data with no following metadata
			if (c == M_SOS)
				throw new IOException("Failed to find XMP metadata in " + path);

			int lenMSB = is.read();
			int lenLSB = is.read();
			if (lenMSB == -1 || lenLSB == -1)
				throw new IOException("Failed to find XMP metadata in " + path);
			int length = (lenMSB << 8 | lenLSB) - 2;

			if (c == M_APP1 && length > XMP_HEADER.length) {
				length -= is.read(header, 0, XMP_HEADER.length);
				if (Arrays.equals(XMP_HEADER, header)) {	// Found XMP section
					byte[] data = new byte[length];
					is.read(data, 0, length);
					return data;
				}
			}

			is.skip(length);	// Skip this section
		}

		throw new IOException("Failed to find XMP metadata in " + path);
	}
}
