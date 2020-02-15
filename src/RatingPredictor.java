import java.io.*;
import java.util.*;

public class RatingPredictor {

    HashMap<String, int[]> wordFreqMap; 
    
    public RatingPredictor() {
        wordFreqMap = new HashMap<>();
    }
    
    public ArrayList<String> createArrayList (String sentence) {
        
        if (sentence == null || sentence.length() == 0) {
            return null;
        }
        
        ArrayList<String> modifiedArrayList = new ArrayList<String>();
        String[] words = sentence.split("[-\\s]");
        
        for(int i=0; i<words.length; i++) {
            words[i] = words[i].replaceAll("\\p{Punct}","");
            words[i] = words[i].toLowerCase();
            if (words[i].length() >= 1) {
                modifiedArrayList.add(words[i]);                
            }
        }

        return modifiedArrayList;

    }
    
    public ArrayList<String> removeStopWords (ArrayList<String> arrList) {
        
        Scanner sc = null; 
        
        try {
            String stopWordsFile = "C:/Users/winny/eclipse-workspace/cse8b-wi20-pa7/src/stopwords.txt";
            File inputFile = new File(stopWordsFile); 
            sc = new Scanner(inputFile); 

            while(sc.hasNextLine()) {
                String stopword = sc.nextLine(); 
                if (arrList.contains(stopword)) {
                    arrList.remove(stopword);
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
        return arrList;
    }
    
    public void updateHashMap(ArrayList<String> wordList, int rating) {
        
        for (String word: wordList) {
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
    
    public void readCleanData (String inFile) {
        
        Scanner sc = null; 
        ArrayList<String> cleanWords = new ArrayList<String>();
        
        try {
            File inputFile = new File(inFile); 
            sc = new Scanner(inputFile); 

            while(sc.hasNextLine()) {
                int rating = sc.nextInt();
                String line = sc.nextLine(); 
                cleanWords = createArrayList(line);

                updateHashMap(cleanWords, rating);               
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
    
    public void cleanData (String inFile, String outFile, boolean ratingIncluded) {
        
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
                wordList = createArrayList(line);
                wordList = removeStopWords(wordList);
                
                if (ratingIncluded) {
                    pw.print(rating + " ");
                }
                for (String w: wordList) {
                    pw.print(w + " ");
                }
                pw.println();
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
    
    public void predictRatings (String inFile, String outFile) {
        Scanner sc = null; 
        PrintWriter pw = null; 
        ArrayList<String> cleanWords = new ArrayList<String>();
        
        try {
            File inputFile = new File(inFile); 
            sc = new Scanner(inputFile); 
            File outputFile = new File(outFile); 
            pw = new PrintWriter(outputFile); 

            while(sc.hasNextLine()) {
                String line = sc.nextLine(); 
                cleanWords = createArrayList(line);
                float sum = 0;
                int wordCount = 0;
                
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

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        RatingPredictor rp = new RatingPredictor();
        
        String initialFile = "C:/Users/winny/eclipse-workspace/cse8b-wi20-pa7/src/rawReviewRatings.txt";
        String cleanDataFile = "C:/Users/winny/eclipse-workspace/cse8b-wi20-pa7/src/cleanReviewRatings.txt";
        rp.cleanData(initialFile, cleanDataFile, true);
        rp.readCleanData(cleanDataFile);
        
        for (HashMap.Entry<String, int[]> entry : rp.wordFreqMap.entrySet()) {
            System.out.print(entry.getKey() + ":" + Arrays.toString(entry.getValue()));
        }
        
        String reviewsFile = "C:/Users/winny/eclipse-workspace/cse8b-wi20-pa7/src/rawReviews.txt";
        String cleanReviewsFile = "C:/Users/winny/eclipse-workspace/cse8b-wi20-pa7/src/cleanReviews.txt";
        String ratingsFile = "C:/Users/winny/eclipse-workspace/cse8b-wi20-pa7/src/ratings.txt";
        rp.cleanData(reviewsFile, cleanReviewsFile, false);
        rp.predictRatings(cleanReviewsFile, ratingsFile);
    }

}
