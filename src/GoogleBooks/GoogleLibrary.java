package GoogleBooks;

import java.io.IOException;

public class GoogleLibrary {

    final static String a_example_in = "data\\a_example.txt";
    final static String a_example_out = "output\\a_exampleOut.txt";

    final static String b_read_on_in = "data\\b_read_on.txt";
    final static String b_read_on_out = "output\\b_read_onOut.txt";
    static Organiser organiser;
    public static void main(String[] args) throws IOException {
        organiser = new Organiser();
        organiser.parseInput(a_example_in);
        organiser.sort();
        organiser.createOutput(a_example_out);
    }
}//END CLASS





