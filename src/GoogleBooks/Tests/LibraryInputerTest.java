package GoogleBooks.Tests;

import GoogleBooks.LibraryInputer;
import org.junit.jupiter.api.*;

class LibraryInputerTest {

    LibraryInputer libraryInputer = new LibraryInputer();

//    @BeforeAll
//    public static void setupAll(){
//        System.out.println("");
//    }

//    @BeforeEach
//    public void setup(){
//
//    }

    @Test
    @DisplayName("Checking the parsing of input")
    void parseInput() {
        Assertions.assertFalse(libraryInputer.getSld1().getTotalNumOfBooks() > 0);
        //Assertions.assertEquals(1, organiser.getTotalNumOfDays());
        Assertions.assertTrue(libraryInputer.getSld1().getTotalPossibleBooks().size() >= 0);

    }

    @Test
    @DisplayName("Sort the books in the library")
    void sortBooksInEachLibrary(){
        Assertions.assertFalse(libraryInputer.getSld1().getTotalNumOfBooks() < 0);
        Assertions.assertEquals(1, libraryInputer.getSld1().getTotalNumOfDays());
        Assertions.assertTrue(libraryInputer.getSld1().getTotalPossibleBooks().size() > 0);


    }

    @Test
    void calculateTotalScore(){
        Assertions.assertFalse(libraryInputer.getSld1().getTotalNumOfBooks() < 0);
    }



//    @Test
//    void rankTheLibraries(){
//
//
//
//    }

//    @Test
//    void createOutput() {
//
//
//
//    }

//    @AfterAll
//    void tearDown(){
//
//    }
}