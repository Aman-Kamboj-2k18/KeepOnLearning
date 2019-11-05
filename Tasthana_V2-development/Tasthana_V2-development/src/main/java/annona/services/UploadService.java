package annona.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService {

	public void saveImage(String string, MultipartFile image)
			throws RuntimeException, IOException {
		try {
			File file = new File(string);

			FileUtils.writeByteArrayToFile(file, image.getBytes());
			System.out
					.println("Go to the location:  "
							+ file.toString()
							+ " on your computer and verify that the image has been stored.");
		} catch (IOException e) {
			throw e;
		}
	}
	
	/**
	 * Method to convert image to byte array
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 */
	public static byte [] ImageToByte(File file) throws FileNotFoundException{

		FileInputStream fis = new FileInputStream(file);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		byte[] buf = new byte[1024];
		try {

			for (int readNum; (readNum = fis.read(buf)) != -1;) {

				bos.write(buf, 0, readNum);     

				System.out.println("read " + readNum + " bytes,");

			}

		} catch (IOException ex) {

		}

		byte[] bytes = bos.toByteArray();

		return bytes;
	}


}
