import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BookFileHandler {
    public static void writeBookDataToFile(String filename, String bookData) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(bookData);
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    public static String readBookDataFromFile(String filename) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        return content.toString();
    }

    public static void updateBookStatus(String filename, String bookId, String newStatus) {
        String fileContents = readBookDataFromFile(filename);
        String[] lines = fileContents.split("\n");
        StringBuilder updatedContents = new StringBuilder();

        for (String line : lines) {
            String[] fields = line.split(",");
            if (fields[0].equals(bookId)) {
                fields[2] = newStatus;
                line = String.join(",", fields);
            }
            updatedContents.append(line).append("\n");
        }

        writeBookDataToFile(filename, updatedContents.toString());
    }


    public static void main(String[] args) {
        String filename = "books.csv";
        StringBuilder bookData = new StringBuilder();
        bookData.append("Book Id,Book Name,Book Status\n");
        bookData.append("1,To Kill a Mockingbird,Available\n");
        bookData.append("2,The Great Gatsby,Checked Out\n");
        bookData.append("3,1984,Available\n");
        bookData.append("4,Pride and Prejudice,Checked Out\n");

        // write the initial book data to the file
        writeBookDataToFile(filename, bookData.toString());

        // read the book data from the file
        String fileContents = readBookDataFromFile(filename);
        System.out.println(fileContents);

        // update the status of book with ID 2 to "Available"
        updateBookStatus(filename, "3", "Checked Out");

        // read the book data from the file again to verify the update
        fileContents = readBookDataFromFile(filename);
        System.out.println(fileContents);
    }

}