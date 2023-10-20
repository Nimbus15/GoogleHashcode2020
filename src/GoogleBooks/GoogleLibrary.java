package GoogleBooks;

import java.io.IOException;

public class GoogleLibrary {

    final static String in = "data\\a_example.txt";
    final static String out = "output\\a_exampleOut.txt";
    static Organiser organiser;
    public static void main(String[] args) throws IOException {
        organiser = new Organiser();
        organiser.parseInput(in);
        organiser.sort();
        organiser.createOutput(out);
    }
}//END CLASS





