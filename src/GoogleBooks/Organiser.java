package GoogleBooks;

import GoogleBooks.Entities.Library;
import GoogleBooks.Entities.SuperLibrariesDetails;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/*
TODO: USE CONSTRAINTS!!!!!!!!!!!!!
Pen and paper?
Write very specific requirements
Debug

 */
enum NextLine {
    SPECIFICATIONS,
    MORE_BOOKS,
}

public class Organiser {
    //Modification
    private final int ADD_ONE = 0;                                //This is to offset all the books by one //First book is 0 or 1?
    //Global variables

    private ArrayList<Library> totalLibraries = new ArrayList<>();
    private ArrayList<Library> rankedLibraries = new ArrayList<Library>();
    private HashMap<Integer, String> totalPossibleBooks = new HashMap<>();  //Hash with booksID, Score //Change to class next time
    private HashMap<Integer, String> tempBookObject = new HashMap<Integer, String>();   //Temporary Variable

    private SuperLibrariesDetails sd1 = new SuperLibrariesDetails();
    private void handleSuperDetails(int index, String[] numbers){
        if(index == 0){
            for (int i = 0; i < numbers.length; i++) {
                switch (i) {
                    case 0 -> sd1.setTotalNumOfBooks(Integer.parseInt(numbers[i]));
                    case 1 -> sd1.setTotalNumOfLibs(Integer.parseInt(numbers[i]));
                    case 2 -> sd1.setTotalNumOfDays(Integer.parseInt(numbers[i]));
                }
            }
        }
    }

    private void handleBookScores(int index, String[] numbers) {  //TODO: Line one 1 2 3 6 5 4
        if (index == 1) {
            for (int i = 0; i < numbers.length; i++) {
                totalPossibleBooks.put(i + ADD_ONE, numbers[i]);
            }
        }
    }

    private static final int PASSED_MUTEX_ARRAY_LOCATION = 0;
    private static final int PASSED_LIBRARY_ARRAY_LOCATION = 1;
    private void handleLibrarySpecificDetails(String[] numbers, Library library, int[] passedValues){
        if (passedValues[PASSED_MUTEX_ARRAY_LOCATION] == 0) {
            library.setLibraryId(passedValues[PASSED_LIBRARY_ARRAY_LOCATION]);         //Set library Id
            for (int i = 0; i < numbers.length; i++) { //Line three 5 2 2
                switch (i) {                          //TODO: we dont know where the new line starts, too many strings parsing
                    case 0:
                        library.setNumOfBooks(Integer.parseInt(numbers[i]));
                        break;
                    case 1:
                        library.setSignUpDays(Integer.parseInt(numbers[i]));//TODO:THIS IS THE SIGN UP DAYS
                        break;
                    case 2:
                        library.setBookPerDays(Integer.parseInt(numbers[i]));
                        break;
                }
            }
            passedValues[PASSED_MUTEX_ARRAY_LOCATION] = 1;                                    //Next line are books
        } else {
            tempBookObject = new HashMap<Integer, String>();//Empty bookArray
            for (String number : numbers) {                //Line four 0 1 2 3 4
                tempBookObject.put(Integer.parseInt(number) + ADD_ONE, "");
            }
            library.setBookObjects(tempBookObject);
            totalLibraries.add(library);
            library = new Library();                      //Make a new library
            passedValues[PASSED_LIBRARY_ARRAY_LOCATION]++;
            passedValues[PASSED_MUTEX_ARRAY_LOCATION] = 0;                                    // Next line are specifications
        }
    }
    public void parseInput(String fileName) throws FileNotFoundException {
        if(fileName == null){                       //If we have not set a file name
            throw new FileNotFoundException("You are missing a file");
        }

        File file = new File(fileName);
        Scanner scanner = new Scanner(file);   // Read in what file

        int index = 0;                              //This will hold the line number
        int libraryNum=0;                          //Library Num
        int mutex=0;//This is a lock

        int[] passValues={libraryNum, mutex};
        Library library = new Library();           //Need a new library to be created
        while (scanner.hasNextLine()) {             //While there is a next line in the file
            String line = scanner.nextLine();       //Hold each line
            String[] numbers = line.split(" ");  //Number of elements in this line
            System.out.println("index"+index);
            if(index ==0){
                handleSuperDetails(index, numbers);//TODO: Line zero 6 2 7
            }else if (index==1){
                handleBookScores(index, numbers);      //TODO: Line one 1 2 3 6 5 4
            }else{
                handleLibrarySpecificDetails(numbers, library, passValues);   //TODO:  Line two and line 3
            }
            index++;                                   //Next line
        }
        sort();
    }//ENDparseInput

    //Sort the Library Books
    public void sort(){
        fillInTheBooks(totalLibraries);                                   //Fill in the books in the libraries with scores
        System.out.println();

        for (Library l : totalLibraries) {                                 //Sort the book in each of the libraries
            l.setBookObjects(sortBooksInEachLibrary(l.getBookObjects()));
        }
        System.out.println();

        for (Library l : totalLibraries) {                                  //Perform calculate total score for each library.
            l.setScannedScore(calculateTotalScore(l));
            System.out.println("l.getScannedScore()"+l.getScannedScore());
        }
        System.out.println();

        rankTheLibraries(totalLibraries);                                    //Rank all libraries by total score
        for (Library rl : rankedLibraries) {  //TODO: ZERO
            System.out.println("rl.getScannedScore()"+rl.getScannedScore());
        }
    }//ENDsort

    //Fill in all the books
    private void fillInTheBooks(ArrayList<Library> totalLibraries){
        for(Library l: totalLibraries){
            for (Map.Entry<Integer, String> b : totalPossibleBooks.entrySet()) {     //For all the possible books
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
        dayLimit = sd1.getTotalNumOfDays() - l.getSignUpDays();  //Day limit remainder of the week after we finish the sign up days

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
        rankedLibraries = totalLibraries;
    }//ENDrankEachLibrary

    //Create submission file
    public void createOutput(String fileName) throws IOException {
        if(fileName == null){
            throw new IOException("You are missing a file");
        }

        File file = new File(fileName);

        FileWriter myWriter = new FileWriter(file);                          // Read in what file
        myWriter.write(sd1.getTotalNumOfLibs() +"\n");

        for(Library rl : rankedLibraries){                                //For each of the ranked libraries
            myWriter.write(rl.getLibraryId() +" " + rl.getBooksScanned()+"\n");//Output the Library Id and the books successfully scanned     //TODO: book scanned trouble
            Object[] keys = rl.getBookObjects().keySet().toArray();         //Store all the keys in an array
            for(int i=0; i< rl.getBooksScanned(); i++) {                  //For all the keys of the books scanned
                myWriter.write(keys[i] + " ");
            }
            myWriter.write("\n");
        }
        myWriter.flush();                          //just makes sure that any buffered data is written to disk
        myWriter.close();                           //flushes the data and indicates that there isn't any more data.
    }//ENDcreateOutput


}//ENDCLASS
