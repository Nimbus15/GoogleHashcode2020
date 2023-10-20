package GoogleBooks;

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
    specifications,
    moreBooks,
}

public class Organiser {
    //Modification
    private final int addOne = 0; //This is to offset all the books by one //First book is 0 or 1?

    //Global variables
    private int totalNumOfBooks = 0;
    private int totalNumOfLibs = 0;
    private int totalNumOfDays = 0;
    private ArrayList<Library> totalLibraries = new ArrayList<>();
    private ArrayList<Library> rankedLibraries = new ArrayList<Library>();

    //Hash with booksID, Score
    private HashMap<Integer, String> totalPossibleBooks = new HashMap<>();//Change to class next time

    //Temporary Variable
    private HashMap<Integer, String> tempBookObject = new HashMap<Integer, String>();

    public void parseInput(String fileName) throws FileNotFoundException {

        //If we have not set a file name
        if(fileName == null){
            throw new FileNotFoundException("You are missing a file");
        }

        File file = new File(fileName);

        // Read in what file
        Scanner scanner = new Scanner(file);

        int index = 0;//This will hold the line number
        int libraryNum=0;//Library Num
        int mutex=0;//This is a lock

        //Need a new library to be created
        Library library = new Library();

        //While there is a next line in the file
        while (scanner.hasNextLine()) {
            //Hold each line
            String line = scanner.nextLine();

            //Number of elements in this line
            String[] numbers = line.split(" ");


            //Line one 6 2 7
            System.out.println(index);
            if(index == 0){
                for (int i = 0; i < numbers.length; i++) {
                    //                        if(!numbers[i].equals("")) {
//                        }
                    switch (i) {
                        case 0:
                            totalNumOfBooks = Integer.parseInt(numbers[i]);
                            break;
                        case 1:
                            totalNumOfLibs = Integer.parseInt(numbers[i]);
                            break;
                        case 2:
                            totalNumOfDays = Integer.parseInt(numbers[i]);
                            break;
                    }

                }
            }
            //Line two 1 2 3 6 5 4
            else if (index == 1) {
                for (int i = 0; i < numbers.length; i++) {
                    totalPossibleBooks.put(i+addOne, numbers[i]);
                }
            }
            else {
                if(mutex == 0){
                    //Set library Id
                    library.setLibraryId(libraryNum);
                    //Line three 5 2 2
                    for (int i = 0; i < numbers.length; i++) {
                        switch (i) {
                            //we dont know where the new line starts
                            //Too many strings parsing
                            case 0:
                                library.setNumOfBooks(Integer.parseInt(numbers[i]));
                                break;
                            case 1:
                                library.setSignUpDays(Integer.parseInt(numbers[i]));
                                break;
                            case 2:
                                library.setBookPerDays(Integer.parseInt(numbers[i]));
                                break;
                        }
                    }
                    mutex = 1;//Next line are books
                } else {
                    //Empty bookArray
                    tempBookObject = new HashMap<Integer, String>();

                    //Line four 0 1 2 3 4
                    for (String number : numbers) {
                        tempBookObject.put(Integer.parseInt(number)+addOne, "");
                    }
                    library.setBookObjects(tempBookObject);
                    totalLibraries.add(library);
                    //Make a new library
                    library = new Library();
                    libraryNum++;
                    mutex = 0;// Next line are specifications
                }
            }
            index++;//Next line
            //System.out.println("");
        }
    }//ENDparseInput

    //Sort the Library Books
    public void sort(){
        //Fill in the books in the libraries with scores
        fillInTheBooks(totalLibraries);

        System.out.println();

        //Sort the book in each of the libraries
        for (Library l : totalLibraries) {
            l.setBookObjects(sortBooksInEachLibrary(l.getBookObjects()));
            //System.out.println(l.getBookObjects());
        }

        System.out.println();

        //Perform calculate total score for each library.
        for (Library l : totalLibraries) {
            l.setScannedScore(calculateTotalScore(l));
            System.out.println(l.getScannedScore());
        }

        System.out.println();

        //Rank all libraries by total score
        rankTheLibraries(totalLibraries);

        for (Library rl : rankedLibraries) {//zero
            //System.out.println("hio");
            System.out.println(rl.getScannedScore());
        }
    }//ENDsort()

    //Fill in all the books
    private void fillInTheBooks(ArrayList<Library> totalLibraries){
        for(Library l: totalLibraries){
            //For all the possible books
            for (Map.Entry<Integer, String> b : totalPossibleBooks.entrySet()) {
                //For each of the books in our library
                for (Map.Entry<Integer, String> bi : l.getBookObjects().entrySet()) {
                    //If that book match the ID of one of the possible books
                    if (bi.getKey().equals(b.getKey())) {
                        //.. fill in the score by using the possible book score
                        l.getBookObjects().put(bi.getKey(), b.getValue());
                    }
                }
            }
        }
        //System.out.println(l.getBookObjects());
    }//ENDfillInAllTheBooks

    //Use to sort all the books in the libraries from biggest to smallest
    private LinkedHashMap<Integer, String> sortBooksInEachLibrary(HashMap<Integer, String> libraryBooks){
        //This stores all the values of the books in the library
        ArrayList<String> tempList = new ArrayList<>();

        //This stores all the values of the books sorted in order of highest
        LinkedHashMap<Integer, String> sortedLibraryBooks = new LinkedHashMap<>();;

        //For each book in that library
        for (Map.Entry<Integer, String> entry : libraryBooks.entrySet()) {
            //Add the values of the hashmap to an arraylist
            tempList.add(entry.getValue());
        }

        //Sort the values in reverse order
        Collections.sort(tempList, Collections.reverseOrder());
        //For each of the values in the array list
        for (String str : tempList) {
            //For each of the books in that library
            for (Map.Entry<Integer, String> entry : libraryBooks.entrySet()) {
                //If we find the entry that corresponds to that sorted value in the arraylist
                if (entry.getValue().equals(str)) {
                    //We add that entry to the sortHashmap as it is already sorted
                    sortedLibraryBooks.put(entry.getKey(), str);
                }
            }
            //System.out.println(sortedLibraryBooks);
        }

        //System.out.println("Didnt work");
        return sortedLibraryBooks;
    }//ENDsortBooksInEachLibrary

    //Perform calculate total score for each library.
    private int calculateTotalScore(Library l){
        int runningScoring; // Use store running total score of books
        int dayLimit=0; //Use to store day limit
        int dayCounter =0; // Counts the days that pass
        int booksScanned=0;

        //For all the possible library
        runningScoring=0;//Set to zero
        //Day limit remainder of the week after we finish the sign up days
        dayLimit = totalNumOfDays - l.getSignUpDays();

        //For all the books in that library
        for (Iterator<Map.Entry<Integer, String>> iterator = l.getBookObjects().entrySet().iterator(); iterator.hasNext(); ) {
            //For each book we can send in a day
            for(int i=0; i< l.getBookPerDays(); i++){
                //If there is no next node
                    if(!iterator.hasNext()){
                        return runningScoring;
                    }

                    //For each of the possible days we can send a book
                    if (dayCounter >= dayLimit) {
                        return runningScoring;
                    }

                    //Get the next entry
                    Map.Entry<Integer, String> b = iterator.next();
                    //Total up the book scores
                    runningScoring += Integer.parseInt(b.getValue());
                    //Add the book score to that library
                    l.setTotalPossibleScannedScore(runningScoring);
                    //Num of books successfully scanned increase
                    booksScanned++;//bs++
                    l.setBooksScanned(booksScanned);
            }
            //Another day has passed
            ++dayCounter;
        }
        return runningScoring;
    }//ENDcalculateTotalScore

    //Rank Each Library
    private void rankTheLibraries(ArrayList<Library> totalLibraries) {
        boolean isNotFinished=true;//Are we finished sorting?
        Library tempLibrary; //For swap
        for (int i = 0; i < totalLibraries.size(); i++) {
            Library l = totalLibraries.get(i);
            isNotFinished = false;
            //For all the books in the library
            for (int j = 0; j < totalLibraries.size() - 1; j++) {
                Library tl = totalLibraries.get(j);
                //If the library's score is less than the values of the other libraries, move it to the back
                if (l.getScannedScore() > tl.getScannedScore()) {
                    tempLibrary = totalLibraries.get(j);
                    totalLibraries.set(j, totalLibraries.get(j + 1));
                    //totalLibraries.add(tempLibrary);
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

        // Read in what file
        FileWriter myWriter = new FileWriter(file);
        myWriter.write(totalNumOfLibs +"\n");

        //For each of the ranked libraries
        for(Library rl : rankedLibraries){
            //Output the Library Id and the books successfully scanned
            //book scanned trouble
            myWriter.write(rl.getLibraryId() +" " + rl.getBooksScanned()+"\n");
            //System.out.println("\t");
            //Store all the keys in an array
            Object[] keys = rl.getBookObjects().keySet().toArray();
            //For all the keys of the books scanned
            for(int i=0; i< rl.getBooksScanned(); i++) {
                myWriter.write(keys[i] + " ");
            }
            myWriter.write("\n");
        }

        myWriter.flush(); //just makes sure that any buffered data is written to disk
        myWriter.close(); //flushes the data and indicates that there isn't any more data.
    }//ENDcreateOutput


    public int getTotalNumOfBooks() {
        return totalNumOfBooks;
    }

    public void setTotalNumOfBooks(int totalNumOfBooks) {
        this.totalNumOfBooks = totalNumOfBooks;
    }

    public int getTotalNumOfLibs() {
        return totalNumOfLibs;
    }

    public void setTotalNumOfLibs(int totalNumOfLibs) {
        this.totalNumOfLibs = totalNumOfLibs;
    }

    public int getTotalNumOfDays() {
        return totalNumOfDays;
    }

    public void setTotalNumOfDays(int totalNumOfDays) {
        this.totalNumOfDays = totalNumOfDays;
    }

    public ArrayList<Library> getRankedLibraries() {
        return rankedLibraries;
    }

    public void setRankedLibraries(ArrayList<Library> rankedLibraries) {
        this.rankedLibraries = rankedLibraries;
    }

    public HashMap<Integer, String> getTotalPossibleBooks() {
        return totalPossibleBooks;
    }

    public void setTotalPossibleBooks(HashMap<Integer, String> totalPossibleBooks) {
        this.totalPossibleBooks = totalPossibleBooks;
    }

}//ENDCLASS
