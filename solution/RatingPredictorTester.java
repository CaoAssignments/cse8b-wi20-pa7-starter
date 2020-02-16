/**
 * This file is designed for CSE 8B WI20 PA7. 
 * It contains few basic testing for this assignment
 * @author  CSE8B WI20 PA Team
 */
import java.util.ArrayList;

/**
 * This class is used to test the functions in RatingPredictor class.
 */
public class RatingPredictorTester {
    
    /** 
     * Main method containing the test cases for the RatingPredictor class
     * @param String[] args - command line arguments that are unused
     * @return void
     */ 
    public static void main(String[] args) {

        RatingPredictor rp = new RatingPredictor();
        
        // Setting the path for input and output files
        String inputPath = "C:/Users/winny/eclipse-workspace/cse8b-wi20-pa7/starter/";
        String outputPath = "C:/Users/winny/eclipse-workspace/cse8b-wi20-pa7/solution/outputFiles/";
     
        String inputFile = inputPath + "rawReviewRatings.txt";       
        String cleanInputFile = outputPath + "cleanReviewRatings.txt";
        String stopwordsFile = inputPath + "stopwords.txt";
        String uniqueStopwordsFile = outputPath + "uniqueStopwords.txt";
        String testFile = inputPath + "rawReviews.txt";
        String cleanTestFile = outputPath + "cleanReviews.txt";
        String ratingsFile = outputPath + "ratings.txt";
        
        String inputFileBig = inputPath + "rawReviewRatingsBig.txt";       
        String cleanInputFileBig = outputPath + "cleanReviewRatingsBig.txt";
        String testFileBig = inputPath + "rawReviewsBig.txt";
        String cleanTestFileBig = outputPath + "cleanReviewsBig.txt";
        String ratingsFileBig = outputPath + "ratingsBig.txt";
        
        // A sample sentence to test the functions
        String testSentence = "The Jungle-Book is     a fantastic movie! It's the best!!";
        ArrayList<String> alist;
        
        System.out.println("Testing splitLine:");
        alist = rp.splitLine(testSentence);
        System.out.println(alist);
        
        System.out.println("Testing replaceHyphensQuotes:");
        alist = rp.replaceHyphensQuotes(alist);
        System.out.println(alist);
        
        System.out.println("Testing removePunctuation:");
        alist = rp.removePunctuation(alist);
        System.out.println(alist);
        
        System.out.println("Testing removeWhiteSpaces:");
        alist = rp.removeWhiteSpaces(alist);
        System.out.println(alist);
        
        System.out.println("Testing removeEmptyWords:");
        alist = rp.removeEmptyWords(alist);
        System.out.println(alist);
        
        System.out.println("Testing removeSingleLetterWords:");
        alist = rp.removeSingleLetterWords(alist);
        System.out.println(alist);
        
        System.out.println("Testing toLowerCase:");
        alist = rp.toLowerCase(alist);
        System.out.println(alist);
        
        // Testing creation of unique stop words / hash set.
        rp.createStopWordsSet(stopwordsFile, uniqueStopwordsFile);
        
        System.out.println("Testing removeStopWords:");
        alist = rp.removeStopWords(alist);
        System.out.println(alist);
        
        // Cleaning the input file and also the test file
        rp.cleanData(inputFile, cleanInputFile, true);
        rp.cleanData(testFile, cleanTestFile, false);
        
        // Updating Hash map
        rp.updateHashMap(cleanInputFile);
        
        // Predicting ratings for the test file
        rp.rateReviews(cleanTestFile, ratingsFile);
        
        // Testing on the Bigger Data set
        RatingPredictor rpBig = new RatingPredictor();
        
        // Testing creation of unique stop words / hash set.
        rpBig.createStopWordsSet(stopwordsFile, uniqueStopwordsFile);
        
        // Cleaning the big input file and also the big test file
        rpBig.cleanData(inputFileBig, cleanInputFileBig, true);
        rpBig.cleanData(testFileBig, cleanTestFileBig, false);
        
        // Updating Hash map for the bigger data set
        rpBig.updateHashMap(cleanInputFileBig);
        
        // Predicting ratings for the big test file
        rpBig.rateReviews(cleanTestFileBig, ratingsFileBig);
    }

}
