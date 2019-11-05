package annona.services;

import org.springframework.stereotype.Service;

@Service
public class ImageService {
	
	public static String getImageType(String imageName) {
		String[] image = imageName.split("\\.");
		String imageType = image[image.length-1];
		
		return imageType;
	}

}
