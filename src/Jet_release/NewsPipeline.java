package Jet_release;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import AceJet.Ace;
import AceJet.AceDocument;
import AceJet.AceEvent;
import AceJet.AceEventMention;
import AceJet.AceEventMentionArgument;
import Jet.Tipster.ExternalDocument;
import Jet.Zoner.SpecialZoner;

public class NewsPipeline {
	static String NewsFile;
	static String sgmFile;
	static BufferedWriter fileout;// used to write data to file.
	static BufferedWriter annoout;
	static String docListFile;
	static String textDirectory;
	static String textExtension;
	static String keyApfDirectory;
	static String keyApfExtension;
	static String annotationFile;
	
	public static void main(String[] args) throws IOException {
		
//		NewsFile = "D:/Corpus/LDC2006D06/Data/LDC2006T06_Original/data/English/NewsConverted";//The string is stored in this file.
//		sgmFile = "D:/Corpus/LDC2006D06/Data/LDC2006T06_Original/data/English/News.sgm";
//		docListFile = "D:\\Corpus\\LDC2006D06\\Data\\LDC2006T06_Original\\data\\English\\NewsList";
//		textDirectory = "D:/Corpus/LDC2006D06/Data/LDC2006T06_Original/data/English";
//		textExtension = "sgm";
//		keyApfDirectory = "D:/GitHub/News Extraction/News_Extraction/output-news";  
//		keyApfExtension = "sgm.apf";
//		annotationFile = "D:/Corpus/LDC2006D06/Data/LDC2006T06_Original/data/English/NewsAnno";
		NewsFile = "./News/NewsConverted";//The string is stored in this file.
		sgmFile = "./News/News.sgm";
		docListFile = "./News/NewsList";
		textDirectory = "./News";
		textExtension = "sgm";
		keyApfDirectory = "./News";  
		keyApfExtension = "sgm.apf";
		annotationFile = "./News/NewsAnno";
        File writename = new File(annotationFile); // path + filename.
        writename.createNewFile(); // construct a new file
        annoout = new BufferedWriter(new FileWriter(writename)); 
		BufferedReader docListReader = new BufferedReader(new FileReader (NewsFile));	//	input the file
		String news;		// equal to currentDocPath in ACE
		String headline = "";
		String number = "";
		int flag = 1;
		while ((news = docListReader.readLine()) != null)
		{
			if (flag%3 == 1){
				System.out.println(news);
				number = news;
			}
			if (flag%3 == 2){
				CreatOut(sgmFile);
				headline = news;
				fileout.write("<HEADLINE>" + '\n');
				fileout.write(news + '\n');
				fileout.write("</HEADLINE>" + '\n');
			}
			if (flag%3 == 0){	
				fileout.write("<TEXT>" + '\n');
				fileout.write(news + "\n\n");
				fileout.write("</TEXT>");
				CloseOut();
				NewsExtraction();
				annoout.write(number + '\n');
				processDocument("News");
				annoout.write(headline + '\n');
				annoout.write(news + '\n');
			}
			flag ++;
		}
		annoout.flush(); // write the buffer to the file.
		annoout.close(); // close the file  
		docListReader.close();	
	}
	
	//Utilize Jet to process sgm file.
	public static void NewsExtraction () throws IOException {
		String[] args = new String[4];
		args[0] = "./props/ace11chunker.properties";
	    args[1] = docListFile;	// Extention is specified by doc name in the test file. 
	    args[2] = textDirectory + "/";
	    args[3] = keyApfDirectory + "/";
	    Ace.main(args);
	}
	
	public static void CreatOut(String ouput_file) throws IOException{
        File writename = new File(ouput_file); // path + filename.
        writename.createNewFile(); // construct a new file
        fileout = new BufferedWriter(new FileWriter(writename));  
	}
	
	public static void CloseOut() throws IOException{
        fileout.flush(); // write the buffer to the file.
        fileout.close(); // close the file  
	}
		
	/**
	 *  This function calculate the number of triggers in each sentence.
	 *  v-mingdi September 1th, 2015
	 * @throws IOException 
	 */
	public static void processDocument (String docName) throws IOException {
		String textFileName = textDirectory + "/" + docName + "." + textExtension;
		String keyApfFileName = keyApfDirectory + "/" + docName + "." + keyApfExtension;
		AceDocument aceDoc = new AceDocument(textFileName, keyApfFileName);	
		for (AceEvent event : aceDoc.events) {	
			for (AceEventMention mention : event.mentions) {
				
				String org = "" , pos = "" , out = "" , in = "" , date = "";
				
				if (event.subtype.equals("Start-Position")){
					for(AceEventMentionArgument argument : mention.arguments){
						if (argument.role.equals("Entity"))
							org = argument.value.text;
						if (argument.role.equals("Position"))
							pos = argument.value.text;

						if (argument.role.equals("Person"))
							in = argument.value.text;
						if (argument.role.substring(0,4).equals("Time"))
							date = argument.value.text;
					}
					annoout.write("<org>");
					annoout.write(org);
					annoout.write("</org>" + "\n");
					
					annoout.write("<pos>");
					annoout.write(pos);
					annoout.write("</pos>" + "\n");
					
					annoout.write("<out>");
					annoout.write(out);
					annoout.write("</out>" + "\n");
					
					annoout.write("<in>");
					annoout.write(in);
					annoout.write("</in>" + "\n");
					
					annoout.write("<date>");
					annoout.write(date);
					annoout.write("</date>" + "\n");
				}
				
				if (event.subtype.equals("End-Position")){
					for(AceEventMentionArgument argument : mention.arguments){
						if (argument.role.equals("Entity"))
							org = argument.value.text;
						if (argument.role.equals("Position"))
							pos = argument.value.text;
						if (argument.role.equals("Person"))
							out = argument.value.text;
						if (argument.role.substring(0,4).equals("Time"))
							date = argument.value.text;
					}
					annoout.write("<org>");
					annoout.write(org);
					annoout.write("</org>" + "\n");
					
					annoout.write("<pos>");
					annoout.write(pos);
					annoout.write("</pos>" + "\n");
					
					annoout.write("<out>");
					annoout.write(out);
					annoout.write("</out>" + "\n");
					
					annoout.write("<in>");
					annoout.write(in);
					annoout.write("</in>" + "\n");
					
					annoout.write("<date>");
					annoout.write(date);
					annoout.write("</date>" + "\n");
				}
				
				if (event.subtype.equals("Nominate")){
					for(AceEventMentionArgument argument : mention.arguments){
						if (argument.role.equals("Entity"))
							org = argument.value.text;
						if (argument.role.equals("Position"))
							pos = argument.value.text;
						if (argument.role.equals("Person"))
							in = argument.value.text;
						if (argument.role.substring(0,4).equals("Time"))
							date = argument.value.text;
					}
					annoout.write("<org>");
					annoout.write(org);
					annoout.write("</org>" + "\n");
					
					annoout.write("<pos>");
					annoout.write(pos);
					annoout.write("</pos>" + "\n");
					
					annoout.write("<out>");
					annoout.write(out);
					annoout.write("</out>" + "\n");
					
					annoout.write("<in>");
					annoout.write(in);
					annoout.write("</in>" + "\n");
					
					annoout.write("<date>");
					annoout.write(date);
					annoout.write("</date>" + "\n");
				}
				
				if (event.subtype.equals("Merge-Org")){
					int org_flag = 0;
					for(AceEventMentionArgument argument : mention.arguments){
						if (argument.role.equals("Org") && (org_flag == 0) ){
							org = argument.value.text;
							org_flag++;
							continue;
						}
						if (argument.role.equals("Place"))
							pos = argument.value.text;
						if (argument.role.equals("Org") && (org_flag == 1 )){
							out = argument.value.text;
							org_flag++;
							continue;
						}
						if (argument.role.equals("Org") && (org_flag == 2) ){
							in = argument.value.text;
							org_flag++;
							continue;
						}
						if (argument.role.substring(0,4).equals("Time"))
							date = argument.value.text;
					}
					annoout.write("<org>");
					annoout.write(org);
					annoout.write("</org>" + "\n");
					
					annoout.write("<pos>");
					annoout.write(pos);
					annoout.write("</pos>" + "\n");
					
					annoout.write("<out>");
					annoout.write(out);
					annoout.write("</out>" + "\n");
					
					annoout.write("<in>");
					annoout.write(in);
					annoout.write("</in>" + "\n");
					
					annoout.write("<date>");
					annoout.write(date);
					annoout.write("</date>" + "\n");
				}
				
				if (event.subtype.equals("Transfer-Ownership")){
					for(AceEventMentionArgument argument : mention.arguments){
						if (argument.role.equals("Artifact"))
							org = argument.value.text;
						if (argument.role.equals("Price"))
							pos = argument.value.text;
						if (argument.role.equals("Seller"))
							out = argument.value.text;
						if (argument.role.equals("Buyer"))
							in = argument.value.text;
						if (argument.role.substring(0,4).equals("Time"))
							date = argument.value.text;
					}
					annoout.write("<org>");
					annoout.write(org);
					annoout.write("</org>" + "\n");
					
					annoout.write("<pos>");
					annoout.write(pos);
					annoout.write("</pos>" + "\n");
					
					annoout.write("<out>");
					annoout.write(out);
					annoout.write("</out>" + "\n");
					
					annoout.write("<in>");
					annoout.write(in);
					annoout.write("</in>" + "\n");
					
					annoout.write("<date>");
					annoout.write(date);
					annoout.write("</date>" + "\n");
				}
			}
		}
	}
	
}
