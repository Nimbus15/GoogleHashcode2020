package GoogleBooks;

import GoogleBooks.Entities.Library;
import GoogleBooks.Entities.SuperLibrariesDetails;
import GoogleBooks.Constants.Constants;

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

    //Global variables

    private ArrayList<Library> totalLibraries = new ArrayList<>();
    private ArrayList<Library> rankedLibraries = new ArrayList<Library>();
    private HashMap<Integer, String> totalPossibleBooks = new HashMap<>();  //Hash with booksID, Score //Change to class next time
    private HashMap<Integer, String> tempBookObject = new HashMap<Integer, String>();   //Temporary Variable

    private SuperLibrariesDetails sld1 = new SuperLibrariesDetails();

    static LibrarySorter librarySorter = new LibrarySorter();


    private void handleSuperDetails(int index, String[] numbers){
        if(index == 0){
            for (int i = 0; i < numbers.length; i++) {
                switch (i) {
                    case 0 -> getSld1().setTotalNumOfBooks(Integer.parseInt(numbers[i]));
                    case 1 -> getSld1().setTotalNumOfLibs(Integer.parseInt(numbers[i]));
                    case 2 -> getSld1().setTotalNumOfDays(Integer.parseInt(numbers[i]));
                }
            }
        }
    }

    private void handleBookScores(int index, String[] numbers) {  //TODO: Line one 1 2 3 6 5 4
        if (index == 1) {
            for (int lineIndex = 0; lineIndex < numbers.length; lineIndex++) {
                getTotalPossibleBooks().put(lineIndex + Constants.ADD_ONE, numbers[lineIndex]);
            }
        }
    }


    private void handleLibrarySpecificDetails(String[] numbers, Library anotherLibrary, int[] passedValues){
        if (passedValues[Constants.PASSED_MUTEX_ARRAY_LOCATION] == 0) {
            anotherLibrary.setLibraryId(passedValues[Constants.PASSED_LIBRARY_ARRAY_LOCATION]);         //Set library Id
            for (int lineIndex = 0; lineIndex < numbers.length; lineIndex++) { //Line three 5 2 2
                switch (lineIndex) {                          //TODO: we dont know where the new line starts, too many strings parsing
                    case 0:
                        anotherLibrary.setNumOfBooks(Integer.parseInt(numbers[lineIndex]));
                        break;
                    case 1:
                        anotherLibrary.setSignUpDays(Integer.parseInt(numbers[lineIndex]));//TODO:THIS IS THE SIGN UP DAYS
                        break;
                    case 2:
                        anotherLibrary.setBookPerDays(Integer.parseInt(numbers[lineIndex]));
                        break;
                }
            }
            passedValues[Constants.PASSED_MUTEX_ARRAY_LOCATION] = 1;                                    //Next line are books
        } else {
            setTempBookObject(new HashMap<Integer, String>());//Empty bookArray
            for (String number : numbers) {                //Line four 0 1 2 3 4
                getTempBookObject().put(Integer.parseInt(number) + Constants.ADD_ONE, "");
            }
            anotherLibrary.setBookObjects(getTempBookObject());
            getTotalLibraries().add(anotherLibrary);
            anotherLibrary = new Library();                      //Make a new library
            passedValues[Constants.PASSED_LIBRARY_ARRAY_LOCATION]++;
            passedValues[Constants.PASSED_MUTEX_ARRAY_LOCATION] = 0;                                    // Next line are specifications
        }
    }
    public void parseInput(String fileName) throws FileNotFoundException {
        if(fileName == null){                       //If we have not set a file name
            throw new FileNotFoundException("You are missing a file");
        }

        File file = new File(fileName);
        Scanner scanner = new Scanner(file);   // Read in what file

        int lineIndex = 0;                              //This will hold the line number
        int libraryNum=0;                          //Library Num
        int mutex=0;//This is a lock

        int[] passValues={libraryNum, mutex};
        Library library = new Library();           //Need a new library to be created
        while (scanner.hasNextLine()) {             //While there is a next line in the file
            String line = scanner.nextLine();       //Hold each line
            String[] numbersSplinted = line.split(" ");  //Number of elements in this line
            System.out.println("index"+lineIndex);
            if(lineIndex ==0){
                handleSuperDetails(lineIndex, numbersSplinted);//TODO: Line zero 6 2 7
            }else if (lineIndex==1){
                handleBookScores(lineIndex, numbersSplinted);      //TODO: Line one 1 2 3 6 5 4
            }else{
                handleLibrarySpecificDetails(numbersSplinted, library, passValues);   //TODO:  Line two and line 3
            }
            lineIndex++;                                   //Next line
        }
        librarySorter.sort();
    }//ENDparseInput

    public SuperLibrariesDetails getSld1() {
        return sld1;
    }

    public void setSld1(SuperLibrariesDetails sld1) {
        this.sld1 = sld1;
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
