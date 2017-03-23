package model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Image {
	private String imageID;
	private byte[] data;


	public String getImageID() {
		return imageID;
	}

	public void setImageID(String imageID) {
		this.imageID = imageID;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public void setData(InputStream inputStream) {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();

		try {
			byte[] temp = new byte[1024];
			int size = 0;

			while (size >= 0) {
				size = inputStream.read(temp);

				if (size >= 0) {
					byteOut.write(temp, 0, size);
				}
			}
			inputStream.close();
			inputStream = null;
			byteOut.close();
			data = byteOut.toByteArray();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
