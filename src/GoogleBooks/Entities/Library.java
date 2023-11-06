package GoogleBooks.Entities;

import java.util.HashMap;

public class Library {
    private int libraryId;
    private int booksScanned=0;
    private int numOfBooks=0;
    private int signUpDays=0;
    private int bookPerDays=0;
    private int scannedScore=0; // For the maximum score we can get
    HashMap<Integer, String> bookObjects = new HashMap<>();


    public Library() {

    }

    public Library(int libraryId, int booksScanned, int numOfBooks, int signUpDays, int bookPerDays, int scannedScore, HashMap<Integer, String> bookObjects) {
        this.libraryId = libraryId;
        this.booksScanned = booksScanned;
        this.numOfBooks = numOfBooks;
        this.signUpDays = signUpDays;
        this.bookPerDays = bookPerDays;
        this.scannedScore = scannedScore;
        this.bookObjects = bookObjects;
    }

    public int getScannedScore() {
        return scannedScore;
    }

    public void setScannedScore(int scannedScore) {
        this.scannedScore = scannedScore;
    }

    public int getNumOfBooks() {
        return numOfBooks;
    }

    public void setNumOfBooks(int numOfBooks) {
        this.numOfBooks = numOfBooks;
    }

    public int getSignUpDays() {
        return signUpDays;
    }

    public void setSignUpDays(int signUpDays) {
        this.signUpDays = signUpDays;
    }

    public int getBookPerDays() {
        return bookPerDays;
    }

    public void setBookPerDays(int bookPerDays) {
        this.bookPerDays = bookPerDays;
    }

    public HashMap<Integer, String> getBookObjects() {
        return bookObjects;
    }


    public void setBookObjects(HashMap<Integer, String> bookObjects) {//TODO: I think we need to track the book and its score
        this.bookObjects = bookObjects;
    }

    public int getTotalPossibleScannedScore() {
        return scannedScore;
    }

    public void setTotalPossibleScannedScore(int totalPossibleScannedScore) {
        this.scannedScore = totalPossibleScannedScore;
    }

    public int getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(int libraryId) {
        this.libraryId = libraryId;
    }

    public int getBooksScanned() {
        return booksScanned;
    }

    public void setBooksScanned(int booksScanned) {
        this.booksScanned = booksScanned;
    }
}