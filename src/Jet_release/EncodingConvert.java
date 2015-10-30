package Jet_release;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EncodingConvert {
	
	public static void main(String[] args) throws IOException {
		String NewsFile = "./News/News";
		String ConvertedFile = "./News/NewsConverted";
		BufferedReader docListReader = new BufferedReader(new FileReader (NewsFile));	//	input the file
        File writename = new File(ConvertedFile); // path + filename.
        writename.createNewFile(); // construct a new file
        BufferedWriter Converted = new BufferedWriter(new FileWriter(writename)); 
		String news;		// equal to currentDocPath in ACE
		
		while ((news = docListReader.readLine() ) != null)
		{
			String output = news.replaceAll("[^\\p{ASCII}]", "");
			Converted.write(output + "\n");
		}
		docListReader.close();
		Converted.close();
	}

}
