package GoogleBooks;

import GoogleBooks.Entities.Library;
import GoogleBooks.Entities.SuperLibrariesDetails;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
/*
TODO: USE CONSTRAINTS!!!!!!!!!!!!!
Pen and paper?
Write very specific requirements
Debug

 */
public class LibraryInputer {
    //Modification
    private final int ADD_ONE = 0;                                //This is to offset all the books by one //First book is 0 or 1?
    //Global variables


    private ArrayList<Library> totalLibraries = new ArrayList<>();
    private ArrayList<Library> rankedLibraries = new ArrayList<Library>();
    private HashMap<Integer, String> totalPossibleBooks = new HashMap<>();  //Hash with booksID, Score //Change to class next time
    private HashMap<Integer, String> tempBookObject = new HashMap<Integer, String>();   //Temporary Variable

    private SuperLibrariesDetails sd1 = new SuperLibrariesDetails();

    static LibrarySorter librarySorter = new LibrarySorter();


    private void handleSuperDetails(int index, String[] numbers){
        if(index == 0){
            for (int i = 0; i < numbers.length; i++) {
                switch (i) {
                    case 0 -> getSd1().setTotalNumOfBooks(Integer.parseInt(numbers[i]));
                    case 1 -> getSd1().setTotalNumOfLibs(Integer.parseInt(numbers[i]));
                    case 2 -> getSd1().setTotalNumOfDays(Integer.parseInt(numbers[i]));
                }
            }
        }
    }

    private void handleBookScores(int index, String[] numbers) {  //TODO: Line one 1 2 3 6 5 4
        if (index == 1) {
            for (int i = 0; i < numbers.length; i++) {
                getTotalPossibleBooks().put(i + ADD_ONE, numbers[i]);
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
            setTempBookObject(new HashMap<Integer, String>());//Empty bookArray
            for (String number : numbers) {                //Line four 0 1 2 3 4
                getTempBookObject().put(Integer.parseInt(number) + ADD_ONE, "");
            }
            library.setBookObjects(getTempBookObject());
            getTotalLibraries().add(library);
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
        librarySorter.sort();
    }//ENDparseInput






    public SuperLibrariesDetails getSd1() {
        return sd1;
    }

    public void setSd1(SuperLibrariesDetails sd1) {
        this.sd1 = sd1;
    }

    public ArrayList<Library> getRankedLibraries() {
        return rankedLibraries;
    }

    public void setRankedLibraries(ArrayList<Library> rankedLibraries) {
        this.rankedLibraries = rankedLibraries;
    }

    public ArrayList<Library> getTotalLibraries() {
        return totalLibraries;
    }

    public void setTotalLibraries(ArrayList<Library> totalLibraries) {
        this.totalLibraries = totalLibraries;
    }

    public HashMap<Integer, String> getTotalPossibleBooks() {
        return totalPossibleBooks;
    }

    public void setTotalPossibleBooks(HashMap<Integer, String> totalPossibleBooks) {
        this.totalPossibleBooks = totalPossibleBooks;
    }

    public HashMap<Integer, String> getTempBookObject() {
        return tempBookObject;
    }

    public void setTempBookObject(HashMap<Integer, String> tempBookObject) {
        this.tempBookObject = tempBookObject;
    }
}//ENDCLASS
