package metrics.synthetic;

import java.time.LocalDate;
import java.util.*;

public class LibraryManagementService {

    private List<Book> books;
    private Map<String, Integer> borrowStatistics;

    public LibraryManagementService() {
        books = new ArrayList<>();
        borrowStatistics = new HashMap<>();
    }

    public void addBook(Book book) {
        if (book != null) {
            books.add(book);
        }
    }

    public boolean removeBook(String isbn) {
        Iterator<Book> iterator = books.iterator();

        while (iterator.hasNext()) {
            Book book = iterator.next();

            if (book.getIsbn().equals(isbn)) {
                iterator.remove();
                return true;
            }
        }

        return false;
    }

    public Book findBookByIsbn(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }

        return null;
    }

    public List<Book> findBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();

        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                result.add(book);
            }
        }

        return result;
    }

    public boolean borrowBook(String isbn, String userId) {
        Book book = findBookByIsbn(isbn);

        if (book == null || book.isBorrowed()) {
            return false;
        }

        book.setBorrowed(true);

        borrowStatistics.merge(userId, 1, Integer::sum);

        return true;
    }

    public boolean returnBook(String isbn) {
        Book book = findBookByIsbn(isbn);

        if (book == null) {
            return false;
        }

        book.setBorrowed(false);

        return true;
    }

    public int countAvailableBooks() {
        int count = 0;

        for (Book book : books) {
            if (!book.isBorrowed()) {
                count++;
            }
        }

        return count;
    }

    public int countBorrowedBooks() {
        int count = 0;

        for (Book book : books) {
            if (book.isBorrowed()) {
                count++;
            }
        }

        return count;
    }

    public Map<String, Integer> getBorrowStatistics() {
        return new HashMap<>(borrowStatistics);
    }

    public List<Book> getRecentBooks(int years) {
        List<Book> result = new ArrayList<>();
        int limitYear = LocalDate.now().getYear() - years;

        for (Book book : books) {
            if (book.getPublicationYear() >= limitYear) {
                result.add(book);
            }
        }

        return result;
    }

    public double calculateBorrowRate() {
        if (books.isEmpty()) {
            return 0;
        }

        return (double) countBorrowedBooks() / books.size();
    }

    public void clearCatalog() {
        books.clear();
        borrowStatistics.clear();
    }
}