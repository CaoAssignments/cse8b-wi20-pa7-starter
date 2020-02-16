/**
 * This file is designed for CSE 8B WI20 PA7. 
 * It contains the solution to this assignment 
 * @author  CSE8B WI20 PA Team
 */

import java.io.*;
import java.util.*;

/**
 * This class is used to predict ratings on a given file of reviews.
 * It also has methods to perform cleaning of data.
 */
public class RatingPredictor {
    
    private HashMap<String, int[]> wordFreqMap; 
    private HashSet<String> set;
    
    /** 
     * Constructor for the Rating Predictor class 
     * Initializes the instance variables.
     */
    public RatingPredictor() {
        wordFreqMap = new HashMap<>();
        set = new HashSet<String>();
    }
    
    /** 
     * Creates a Hash set of all the stop words in a file.
     * Also outputs the hash set to another output file.
     * @param stopWordsInFile - The given file having all stop words 
     * @param stopWordsOutFile - The output file that will contain only 
     * unique stop words
     * @return void
     */
    public void createStopWordsSet (String stopWordsInFile, 
            String stopWordsOutFile) {
        
        Scanner sc = null; 
        PrintWriter pw = null; 
          
        
        try {
            File inputFile = new File(stopWordsInFile); 
            sc = new Scanner(inputFile); 
            File outputFile = new File(stopWordsOutFile); 
            pw = new PrintWriter(outputFile); 
            
            while(sc.hasNextLine()) {
                String line = sc.nextLine(); 
                set.add(line);   
            }
            
            for (String w: set) {
                pw.println(w);
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            if(sc != null) {
                sc.close(); 
            }
            if(pw != null) {
                pw.close(); 
            }
        }       
    }
    
    /** 
     * Method to predict the ratings of the reviews read from a file
     * @param inCleanFile - The cleaned version of the file containing only 
     * reviews.
     * @param outRatingsFile - The output file that will contain the 
     * corresponding ratings for each review.
     * @return void
     */
    public void rateReviews (String inCleanFile, String outRatingsFile) {
        
        Scanner sc = null; 
        PrintWriter pw = null; 
        ArrayList<String> cleanWords = new ArrayList<String>();
        
        try {
            File inputFile = new File(inCleanFile); 
            sc = new Scanner(inputFile); 
            File outputFile = new File(outRatingsFile); 
            pw = new PrintWriter(outputFile); 

            while(sc.hasNextLine()) {
                String line = sc.nextLine(); 
                cleanWords = splitLine(line);
                float default_rating = 0;
                float sum = 0;
                int wordCount = 0;
                
                if (cleanWords == null) {
                    pw.println(default_rating);
                }
                
                else {
                    for (String word: cleanWords) {
                        wordCount++;
                        if (!wordFreqMap.containsKey(word)) {
                            sum += 2;
                        }
                        else {
                            int[] vals = wordFreqMap.get(word);
                            sum += vals[0] / vals[1];
                        }
                    }
                    
                    float rating = sum / wordCount;
                    pw.println(rating);
                }
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            if(sc != null) {
                sc.close(); 
            }
            if(pw != null) {
                pw.close(); 
            }
        }
    }
    
    /** 
     * Method to remove empty words in the given list of words.
     * @param words - The array list of words.
     * @return ArrayList<String> - The array list without any empty words
     */
    public ArrayList<String> removeEmptyWords (ArrayList<String> words) {
        
        ArrayList<String> modifiedArrayList = new ArrayList<String>();
        
        if (words == null || words.size() == 0) {
            return null;
        }
        
        for(String word: words) {
            if (word.length() > 0) {
                modifiedArrayList.add(word);
            }    
        }

        return modifiedArrayList; 
         
    }
    
    /** 
     * Method to convert each word in the list to lower case.
     * @param words - The array list of words.
     * @return ArrayList<String> - The array list containing all lower 
     * case words.
     */
    public ArrayList<String> toLowerCase (ArrayList<String> words) {
        
        ArrayList<String> modifiedArrayList = new ArrayList<String>();
        
        if (words == null || words.size() == 0) {
            return null;
        }
        
        for(String word: words) {
            modifiedArrayList.add(word.toLowerCase());    
        }

        return modifiedArrayList; 
        
    }
    
    /** 
     * Method to remove punctuation marks from the list of words.
     * @param words - The array list of words.
     * @return ArrayList<String> - The array list containing words 
     * without punctuation
     */
    public ArrayList<String> removePunctuation (ArrayList<String> words) {
        
        ArrayList<String> modifiedArrayList = new ArrayList<String>();
        
        if (words == null || words.size() == 0) {
            return null;
        }
        
        for(String word: words) {
            word = word.replaceAll("\\p{Punct}","");
            modifiedArrayList.add(word); 
        }

        return modifiedArrayList;  
        
    }
    
    /** 
     * Method to remove single letter words.
     * @param words - The array list of words.
     * @return ArrayList<String> - The array list containing words of 
     * length greater than 1
     */
    public ArrayList<String> removeSingleLetterWords (ArrayList<String> words){
        
        ArrayList<String> modifiedArrayList = new ArrayList<String>();
        
        if (words == null || words.size() == 0) {
            return null;
        }
        
        for(String word: words) {
            if (word.length() > 1) {
                modifiedArrayList.add(word);
            }    
        }

        return modifiedArrayList; 
          
    }
    
    /** 
     * Method to remove all trailing and leading white spaces from the list 
     * of words.
     * @param words - The array list of words.
     * @return ArrayList<String> - The array list containing words without 
     * any trailing 
     * and leading white spaces.
     */
    public ArrayList<String> removeWhiteSpaces (ArrayList<String> words) {
        
        ArrayList<String> modifiedArrayList = new ArrayList<String>();
        
        if (words == null || words.size() == 0) {
            return null;
        }
        
        for(String word: words) {
            word = word.replaceAll("\\s","");
            modifiedArrayList.add(word); 
        }

        return modifiedArrayList; 
        
    }
    
    /** 
     * Method to split words at hyphens and single quote delimiters.
     * @param words - The array list of words.
     * @return ArrayList<String> - The array list containing words without 
     * hyphens and quotes (words previously containing these two characters 
     * are split into multiple words)
     */
    public ArrayList<String> replaceHyphensQuotes (ArrayList<String> words) {
        
        ArrayList<String> modifiedArrayList = new ArrayList<String>();
        
        if (words == null || words.size() == 0) {
            return null;
        }
        
        for(String word: words) {
            if (word.contains("-")) {
                String[] splitWord = word.split("-"); 
                modifiedArrayList.addAll(Arrays.asList(splitWord));
            }
            else if (word.contains("'")) {
                String[] splitWord = word.split("'"); 
                modifiedArrayList.addAll(Arrays.asList(splitWord));
            }
            else {
                modifiedArrayList.add(word);
            } 
        }

        return modifiedArrayList;    
    }
    
    /** 
     * Method to split the entire sentence at space delimiters
     * @param sentence - A string containing a review.
     * @return ArrayList<String> - The array list containing words of the 
     * review split at spaces
     */
    public ArrayList<String> splitLine (String sentence) {
        
        if (sentence == null || sentence.length() == 0) {
            return null;
        }
        
        ArrayList<String> modifiedArrayList = new ArrayList<String>();
        String[] words = sentence.split("\\s");
        for(String w: words) {
            modifiedArrayList.add(w);
        }
        
        return modifiedArrayList;
    }
    
    /** 
     * Method to remove stop words from the list of words
     * @param arrList - The array list of words.
     * @return ArrayList<String> - The array list containing no stop words
     */
    public ArrayList<String> removeStopWords (ArrayList<String> arrList) {
        
        if (arrList == null || arrList.size() == 0) {
            return null;
        }
        
        for (String stopword: this.set) {
            if (arrList.contains(stopword)) {
                arrList.removeAll(Collections.singleton(stopword));
            }
        }
        
        return arrList;
    }
    
    /** 
     * Method to update the hash map with the appropriate values
     * @param inCleanFile - The file containing cleaned data using 
     * which the hashmap will be updated
     * @return void
     */
    public void updateHashMap(String inCleanFile) {
        
        Scanner sc = null; 
        ArrayList<String> cleanWords = new ArrayList<String>();

        try {
            File inputFile = new File(inCleanFile); 
            sc = new Scanner(inputFile);
            
            while(sc.hasNextLine()) {
                int rating = sc.nextInt();
                String line = sc.nextLine(); 
                cleanWords = splitLine(line);
                cleanWords = removeEmptyWords(cleanWords);
                
                if (cleanWords == null) {
                    continue;
                }
                
                for (String word: cleanWords) {
                    if (!wordFreqMap.containsKey(word)) {
                        int[] arr = new int[] {rating, 1};
                        wordFreqMap.put(word, arr);
                    }
                    else {
                        int[] val = wordFreqMap.get(word);
                        int[] arr = new int[] {val[0]+rating, val[1]+1};
                        wordFreqMap.put(word, arr);
                    }
                }
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            if(sc != null) {
                sc.close(); 
            }
        }
    }
    
    /** 
     * Method to clean the review data in a given file and write the 
     * cleaned data into a new file
     * @param inFile - The file containing the raw reviews (and maybe ratings)
     * @param outFile - The file into which the cleaned reviews (and ratings 
     * if applicable) are written
     * @param ratingIncluded - A flag to indicate whether the input file 
     * contains rating also or not
     * @return void
     */
    public void cleanData (String inFile, String outFile, 
            boolean ratingIncluded) {
        
        Scanner sc = null; 
        PrintWriter pw = null; 
        ArrayList<String> wordList = new ArrayList<String>();
        
        try {
            File inputFile = new File(inFile); 
            sc = new Scanner(inputFile); 
            File outputFile = new File(outFile); 
            pw = new PrintWriter(outputFile); 
            
            while(sc.hasNextLine()) {
                int rating = 0;
                
                if (ratingIncluded) {
                    rating = sc.nextInt();
                }
                
                String line = sc.nextLine(); 
                
                //Split the line of text into individual words.
                wordList = splitLine(line); 
                
                //Replace hyphens and single quotes with spaces.
                wordList = replaceHyphensQuotes(wordList);
                
                //Remove the punctuation marks from the words.
                wordList = removePunctuation(wordList);
                
                //Remove trailing and leading white spaces in each word.
                wordList = removeWhiteSpaces(wordList);
                
                //Remove the empty words.
                wordList = removeEmptyWords(wordList);
                
                //Remove words with just one character in them. 
                wordList = removeSingleLetterWords(wordList);
                
                //Make all words to lowercase.
                wordList = toLowerCase(wordList);
                     
                //Remove stopwords.
                wordList = removeStopWords(wordList);
                
                if (wordList == null) {
                    if (ratingIncluded) {
                        pw.print(rating + " ");
                    }
                    pw.println();
                }
                
                else {
                    if (ratingIncluded) {
                        pw.print(rating + " ");
                    }
                    for (String w: wordList) {
                        pw.print(w + " ");
                    }
                    pw.println();
                }
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            if(sc != null) {
                sc.close(); 
            }
            if(pw != null) {
                pw.close(); 
            }
        }
        
    }    
}
