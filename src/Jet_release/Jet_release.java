package Jet_release;

import java.io.IOException;
import AceJet.Ace;
import AceJet.EventScorer;
import AceJet.TrainEventTagger;

public class Jet_release {

    /**
     * @param args the command line arguments
     * @args[0]: train or test
     * @args[1]: property file
     * @args[2]: the list of test file
     * @args[3]: the folder of test files
     * @args[4]: the output folder
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
//        args = new String[5];
//        args[0] = "-train";
//        args[1] = "D:\\GitHub\\Jet\\jet\\props\\ace11chunker.properties";
//        args[2] = "D:\\Corpus\\LDC2006D06\\Data\\LDC2006T06_Original\\data\\English\\new_filelist_ACE_training";;
//        args[3] = "D:\\Corpus\\LDC2006D06\\Data\\LDC2006T06_Original\\data\\English\\";
//        args[4] = "D:\\GitHub\\Jet\\jet\\Trainout\\";

    	//@AureDi args[2] specify the list of test file, we found that the name of file should include extension and not include 
    	// any path. the args[3] should specify the full path of test file.

    		//for jet
//        args = new String[5];
//        args[0] = "-test";
//        args[1] = "D:\\GitHub\\Jet\\jet\\props\\ace11chunker.properties";
//        args[2] = "D:\\Corpus\\LDC2006D06\\Data\\LDC2006T06_Original\\data\\English\\new_filelist_ACE_test-test";
//        args[3] = "D:\\Corpus\\LDC2006D06\\Data\\LDC2006T06_Original\\data\\English\\nw\\timex2norm\\";
//        args[4] = "D:/GitHub/News Extraction/News_Extraction/output/";
    	
		//for news
	      args = new String[5];
	      args[0] = "-test";
	      args[1] = "D:\\GitHub\\Jet\\jet\\props\\ace11chunker.properties";
	      args[2] = "D:\\Corpus\\LDC2006D06\\Data\\LDC2006T06_Original\\data\\English\\newstest";	// Extention is specified by doc name in the test file. 
	      args[3] = "D:\\Corpus\\LDC2006D06\\Data\\LDC2006T06_Original\\data\\English\\";
	      args[4] = "D:/GitHub/News Extraction/News_Extraction/output-news/";
			
//			int number = 8;
//			args = new String[number];
//			args[0] = "-score";
//			args[1] = "D:\\Corpus\\LDC2006D06\\Data\\LDC2006T06_Original\\data\\English\\new_filelist_ACE_test-score";
//			args[2] = "D:\\Corpus\\LDC2006D06\\Data\\LDC2006T06_Original\\data\\English\\nw\\timex2norm\\";
//			args[3] = "sgm";
//			args[4] = "D:/GitHub/News Extraction/News_Extraction/output";		//You need add apf.v5.1.1.dtd in this folder.
//			args[5] = "sgm.apf";      
//			args[6] = "D:\\Corpus\\LDC2006D06\\Data\\LDC2006T06_Original\\data\\English\\nw\\timex2norm\\";
//			args[7] = "apf.xml";
    	
        
        if (args.length != 5 && args.length != 6 && args.length != 8 && args.length != 7) {
            PrintErrMsg();
            System.exit(1);
        }
        switch (args[0]) {
            case "-train":
                //Ace.Testing = false;
                String[] train_args = new String[4];
                for (int i = 0; i < 4; i++) {
                    train_args[i] = args[i + 1];
                }
                TrainEventTagger.main(train_args);
                break;
            case "-test":
                String[] test_args = new String[4];
                for (int i = 0; i < 4; i++) {
                    test_args[i] = args[i + 1];
                }
                Ace.main(test_args);
                break;    
            case "-score":
                String[] score_args = new String[7];
                for (int i = 0; i < 7; i++) {
                    score_args[i] = args[i + 1];
                }
                EventScorer.main(score_args);
                break;
            default:
                PrintErrMsg();
                break;
        }
        
    }
    
    private static void PrintErrMsg() {
        System.err.print("Input format error!\n"
                + "\n"
                + "Correct format:\n"
                + "	JET_release -train properties trainfilelist traindocumentDir ModeloutputDir\n"
                + "or\n"
                + "	JET_release -test properties testfilelist testdocumentDir testoutputDir\n");

    }

}
