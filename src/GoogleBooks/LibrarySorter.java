package GoogleBooks;

import GoogleBooks.Entities.Library;

import java.util.*;

import static GoogleBooks.GoogleLibraryMain.libraryInputer;

public class LibrarySorter {
    //Sort the Library Books
    public void sort(){
        fillInTheBooks(libraryInputer.getTotalLibraries());                                   //Fill in the books in the libraries with scores
        System.out.println();

        for (Library l : libraryInputer.getTotalLibraries()) {                                 //Sort the book in each of the libraries
            l.setBookObjects(sortBooksInEachLibrary(l.getBookObjects()));
        }
        System.out.println();

        for (Library l : libraryInputer.getTotalLibraries()) {                                  //Perform calculate total score for each library.
            l.setScannedScore(calculateTotalScore(l));
            System.out.println("l.getScannedScore()"+l.getScannedScore());
        }
        System.out.println();

        rankTheLibraries(libraryInputer.getTotalLibraries());                                    //Rank all libraries by total score
        for (Library rl : libraryInputer.getRankedLibraries()) {  //TODO: ZERO
            System.out.println("rl.getScannedScore()"+rl.getScannedScore());
        }
    }//ENDsort

    //Fill in all the books
    private void fillInTheBooks(ArrayList<Library> totalLibraries){
        for(Library l: totalLibraries){
            for (Map.Entry<Integer, String> b : libraryInputer.getTotalPossibleBooks().entrySet()) {     //For all the possible books
                for (Map.Entry<Integer, String> bi : l.getBookObjects().entrySet()) {   //For each of the books in our library
                    if (bi.getKey().equals(b.getKey())) {                            //If that book match the ID of one of the possible books
                        l.getBookObjects().put(bi.getKey(), b.getValue());          //.. fill in the score by using the possible book score
                    }
                }
            }
        }
    }//ENDfillInAllTheBooks

    //Use to sort all the books in the libraries from biggest to smallest in terms of score
    private LinkedHashMap<Integer, String> sortBooksInEachLibrary(HashMap<Integer, String> libraryBooks){
        ArrayList<String> tempList = new ArrayList<>();                          //This stores all the score values of the books in the library
        LinkedHashMap<Integer, String> sortedLibraryBooks = new LinkedHashMap<>();  //This stores all the values of the books sorted in order of highest
        for (Map.Entry<Integer, String> entry : libraryBooks.entrySet()) {       //For each book in that library
            tempList.add(entry.getValue());                                      //Add the values of the hashmap to an arraylist
        }
        Collections.sort(tempList, Collections.reverseOrder());                     //Sort the values in reverse order
        for (String str : tempList) {                                               //For each of the values in the array list
            for (Map.Entry<Integer, String> entry : libraryBooks.entrySet()) {       //For each of the books in that library
                if (entry.getValue().equals(str)) {                                 //If we find the entry that corresponds to that sorted value in the arraylist
                    sortedLibraryBooks.put(entry.getKey(), str);                    //We add that entry to the sortHashmap as it is already sorted
                }
            }
        }
        return sortedLibraryBooks;
    }//ENDsortBooksInEachLibrary

    //Perform calculate total score for each library.
    private int calculateTotalScore(Library l){
        int runningScoring; // Use store running total score of books
        int dayLimit=0; //Use to store day limit
        int dayCounter =0; // Counts the days that pass
        int booksScanned=0;

        runningScoring=0;                                //For all the possible library //Set to zero
        dayLimit = libraryInputer.getSld1().getTotalNumOfDays() - l.getSignUpDays();  //Day limit remainder of the week after we finish the sign up days

        for (Iterator<Map.Entry<Integer, String>> iterator = l.getBookObjects().entrySet().iterator(); iterator.hasNext(); ) {//For all the books in that library
            for(int i=0; i< l.getBookPerDays(); i++){         //For each book we can send in a day
                if(!iterator.hasNext()){                  //If there is no next node
                    return runningScoring;
                }
                if (dayCounter >= dayLimit) {              //For each of the possible days we can send a book
                    return runningScoring;
                }

                Map.Entry<Integer, String> b = iterator.next(); //Get the next entry
                runningScoring += Integer.parseInt(b.getValue()); //Total up the book scores
                l.setTotalPossibleScannedScore(runningScoring); //Add the book score to that library
                booksScanned++;
                l.setBooksScanned(booksScanned);  //Num of books successfully scanned increase
            }
            ++dayCounter;                             //Another day has passed
        }
        return runningScoring;
    }//ENDcalculateTotalScore

    //Rank Each Library
    private void rankTheLibraries(ArrayList<Library> totalLibraries) {
        boolean isNotFinished=true;//Are we finished sorting?
        Library tempLibrary;      //For swap
        for (int i = 0; i < totalLibraries.size(); i++) {
            Library l = totalLibraries.get(i);
            isNotFinished = false;

            for (int j = 0; j < totalLibraries.size() - 1; j++) {  //For all the books in the library
                Library tl = totalLibraries.get(j);
                if (l.getScannedScore() > tl.getScannedScore()) { //If the library's score is less than the values of the other libraries, move it to the back
                    tempLibrary = totalLibraries.get(j);
                    totalLibraries.set(j, totalLibraries.get(j + 1));
                    totalLibraries.set(j + 1, tempLibrary);
                    isNotFinished = true;
                }
            }
        }
        libraryInputer.setRankedLibraries(totalLibraries);
    }//ENDrankEachLibrary
}
