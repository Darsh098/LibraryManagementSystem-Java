import java.util.Date;

public class Book
{
    private String bookId;
    private String bookName;
    private String bookAuthorName;
    private  String bookType;
    private Date bookIssuedDate;
    private Date bookReturnedDate;
    private boolean bookStatus;

    public Book(String bookId, String bookName, String bookAuthorName, String bookType, Date bookIssuedDate, Date bookReturnedDate, boolean bookStatus) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookAuthorName = bookAuthorName;
        this.bookType = bookType;
        this.bookIssuedDate = bookIssuedDate;
        this.bookReturnedDate = bookReturnedDate;
        this.bookStatus = bookStatus;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthorName() {
        return bookAuthorName;
    }

    public void setBookAuthorName(String bookAuthorName) {
        this.bookAuthorName = bookAuthorName;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public Date getBookIssuedDate() {
        return bookIssuedDate;
    }

    public void setBookIssuedDate(Date bookIssuedDate) {
        this.bookIssuedDate = bookIssuedDate;
    }

    public Date getBookReturnedDate() {
        return bookReturnedDate;
    }

    public void setBookReturnedDate(Date bookReturnedDate) {
        this.bookReturnedDate = bookReturnedDate;
    }

    public boolean isBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(boolean bookStatus) {
        this.bookStatus = bookStatus;
    }
}
