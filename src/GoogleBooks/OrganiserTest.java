package GoogleBooks;

import org.junit.jupiter.api.*;

class OrganiserTest {

    Organiser organiser = new Organiser();

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
        Assertions.assertFalse(organiser.getTotalNumOfBooks() > 0);
        //Assertions.assertEquals(1, organiser.getTotalNumOfDays());
        Assertions.assertTrue(organiser.getTotalPossibleBooks().size() >= 0);

    }

    @Test
    @DisplayName("Sort the books in the library")
    void sortBooksInEachLibrary(){
        Assertions.assertFalse(organiser.getTotalNumOfBooks() < 0);
        Assertions.assertEquals(1, organiser.getTotalNumOfDays());
        Assertions.assertTrue(organiser.getTotalPossibleBooks().size() > 0);


    }

    @Test
    void calculateTotalScore(){
        Assertions.assertFalse(organiser.getTotalNumOfBooks() < 0);
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