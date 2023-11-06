package GoogleBooks;

import GoogleBooks.Entities.Library;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static GoogleBooks.GoogleLibraryMain.libraryInputer;

public class LibraryOutputer {
    //Create submission file
    public void createOutput(String fileName) throws IOException {
        if(fileName == null){
            throw new IOException("You are missing a file");
        }

        File file = new File(fileName);

        FileWriter myWriter = new FileWriter(file);                          // Read in what file
        myWriter.write(libraryInputer.getSld1().getTotalNumOfLibs() +"\n");

        for(Library rl : libraryInputer.getRankedLibraries()){                                //For each of the ranked libraries
            myWriter.write(rl.getLibraryId() +" " + rl.getBooksScanned()+"\n");//Output the Library Id and the books successfully scanned     //TODO: book scanned trouble
            Object[] keys = rl.getBookObjects().keySet().toArray();         //Store all the keys in an array
            for(int booksScannedIndex=0; booksScannedIndex< rl.getBooksScanned(); booksScannedIndex++) {                  //For all the keys of the books scanned
                myWriter.write(keys[booksScannedIndex] + " ");
            }
            myWriter.write("\n");
        }
        myWriter.flush();                          //just makes sure that any buffered data is written to disk
        myWriter.close();                           //flushes the data and indicates that there isn't any more data.
    }//ENDcreateOutput
}
