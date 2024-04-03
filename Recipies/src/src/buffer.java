package src;

import java.io.IOException;
import java.nio.file.*;

@SuppressWarnings("unused")
public class buffer {
	private static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return fileName.substring(lastDotIndex + 1);
        }
        return ""; // No file extension found
    }
	
	public static void main(String[] args) {
//		
		String blah = "15Minutes";
		System.out.println(blah.endsWith("Minutes"));
    }
}
