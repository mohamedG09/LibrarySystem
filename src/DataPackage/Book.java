package DataPackage;

public class Book {

    private String title,author,picLink,studentBor;
    private int isbn;

    public Book() {
        this.title = "";
        this.author = "";
        this.picLink = "";
        this.studentBor = "";
        this.isbn = 0;
    }

    public Book(int isbn,String title, String author,String studentBor) {
        this.title = title;
        this.author = author;
        this.picLink = picLink;
        this.studentBor = studentBor;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPicLink() {
        return picLink;
    }

    public void setPicLink(String picLink) {
        this.picLink = picLink;
    }

    public String getStudentBor() {
        return studentBor;
    }

    public void setStudentBor(String studentBor) {
        this.studentBor = studentBor;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }
}
