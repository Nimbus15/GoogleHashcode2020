package GoogleBooks;

import java.io.IOException;

public class GoogleLibraryMain {

    final static String a_example_in = "data\\a_example.txt";
    final static String a_example_out = "output\\a_exampleOut.txt";

    final static String b_read_on_in = "data\\b_read_on.txt";
    final static String b_read_on_out = "output\\b_read_onOut.txt";

    final static String document_example_in="data\\document_example.txt";
    final static String document_example_out="output\\document_exampleOut.txt";
    static LibraryInputer libraryInputer;
    static LibraryOutputer libraryOutputer;
    public static void main(String[] args) throws IOException {
        libraryInputer = new LibraryInputer();
        libraryInputer.parseInput(document_example_in);
        libraryOutputer = new LibraryOutputer();
        libraryOutputer.createOutput(document_example_out);//PROBLEM
    }
}//END CLASS





