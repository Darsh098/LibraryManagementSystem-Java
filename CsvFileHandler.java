import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

class CsvFileHandler extends Book {
    // Method for writing student object to csv file
    public static void writeObjectToCsvFile(String filePath, Student obj) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            if (CsvFileHandler.findDataInCsvFile("student.csv", "User Name", 1) == null) {
                writer.append("Full Name,User Name,Password,Student Id\n");
            }
            writer.append(obj.getFullname()).append(",");
            writer.append(obj.getUsername()).append(",");
            writer.append(obj.getPassword()).append(",");
            writer.append(obj.getStudentId()).append("\n");

        } catch (IOException e) {
            System.out.println("Failed to write objects to CSV file: " + e.getMessage());
        }
    }

    // Overloaded method for writing admin object to csv file
    public static void writeObjectToCsvFile(String filePath, Admin obj) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            if (CsvFileHandler.findDataInCsvFile("Admin.csv", "User Name", 1) == null) {
                writer.append("Full Name,User Name,Password\n");
            }
            writer.append(obj.getFullname()).append(",");
            writer.append(obj.getUsername()).append(",");
            writer.append(obj.getPassword()).append("\n");

        } catch (IOException e) {
            System.out.println("Failed to write objects to CSV file: " + e.getMessage());
        }
    }

    // Overloaded method for writing book object to csv file
    public static void writeObjectToCsvFile(String filePath, Book obj) throws ParseException {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            if (CsvFileHandler.findDataInCsvFile("Book.csv", "Book Name", 1) == null) {
                writer.append(
                        "Book Id,Book Name,Author Name,Type,Issue Date,Return Date,Status,Student Id,Due Amount\n");
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            writer.append(obj.getBookId()).append(",");
            writer.append(obj.getBookName()).append(",");
            writer.append(obj.getBookAuthorName()).append(",");
            writer.append(obj.getBookType()).append(",");
            writer.append(dateFormat.format(obj.getBookIssuedDate())).append(",");
            writer.append(dateFormat.format(obj.getBookReturnedDate())).append(",");
            writer.append(Boolean.toString(obj.isBookStatus())).append(",");
            writer.append(obj.getStudentId()).append(",");
            writer.append(Double.toString(obj.getDueAmount())).append("\n");

        } catch (IOException e) {
            System.out.println("Failed to write objects to CSV file: " + e.getMessage());
        }
    }

    // Method for Reading object from csv file
    public static String[] findDataInCsvFile(String filePath, String value, int col) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns[col].equals(value)) {
                    return columns;
                }
            }
            return null;
        } catch (IOException e) {
            System.out.println("Failed to read CSV file: " + e.getMessage());
            return null;
        }
    }

    // change code for late fees
    public static void UpdateLateFees(String filePath, String studId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length > 2 && columns[7].equals(studId)) {
                    long daysDiff = Main.calculateLateFees(columns[5]);
                    double amount = 0;
                    if (daysDiff > 0) {
                        amount = daysDiff * 5;
                    }
                    CsvFileHandler.updateCsvFile("Book.csv", i, 8, Double.toString(amount));
                }
                i++;
            }
        } catch (IOException e) {
            System.out.println("Failed to read CSV file: " + e.getMessage());
            // return null;
        }
    }

    // Method for Displaying Books from csv file
    public static void displayBookCsvFile(String filePath, boolean displayAll) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            if (displayAll) {
                int i = 0;
                while ((line = reader.readLine()) != null) {

                    String[] columns = line.split(",");
                    System.out.print(columns[0]);
                    for (int j = 0; j < 10 - columns[0].length(); j++)
                        System.out.print(" ");

                    System.out.print(columns[1]);
                    for (int j = 0; j < 20 - columns[1].length(); j++)
                        System.out.print(" ");

                    System.out.print(columns[2]);
                    for (int j = 0; j < 15 - columns[2].length(); j++)
                        System.out.print(" ");

                    System.out.print(columns[3]);
                    for (int j = 0; j < 9 - columns[3].length(); j++)
                        System.out.print(" ");

                    if (columns[4].equals("0002-11-30"))
                        columns[4] = "-";
                    System.out.print(columns[4]);
                    for (int j = 0; j < 12 - columns[4].length(); j++)
                        System.out.print(" ");

                    if (columns[5].equals("0002-11-30"))
                        columns[5] = "-";
                    System.out.print(columns[5]);
                    for (int j = 0; j < 13 - columns[5].length(); j++)
                        System.out.print(" ");

                    if (columns[6].equals("false"))
                        columns[6] = "N/A";
                    else if (columns[6].equals("true"))
                        columns[6] = "A";
                    System.out.print(columns[6]);
                    for (int j = 0; j < 8 - columns[6].length(); j++)
                        System.out.print(" ");

                    if (columns[7].equals("null"))
                        columns[7] = "-";
                    System.out.print(columns[7]);
                    for (int j = 0; j < 12 - columns[7].length(); j++)
                        System.out.print(" ");

                    System.out.print(columns[8]);
                    System.out.println();
                    if (i == 0)
                        System.out.println();
                    i++;
                }
            } else {
                int i = 0;
                while ((line = reader.readLine()) != null) {
                    String[] columns = line.split(",");
                    if (!columns[6].equals("false")) {
                        System.out.print(columns[0]);
                        for (int j = 0; j < 10 - columns[0].length(); j++)
                            System.out.print(" ");

                        System.out.print(columns[1]);
                        for (int j = 0; j < 20 - columns[1].length(); j++)
                            System.out.print(" ");

                        System.out.print(columns[2]);
                        for (int j = 0; j < 15 - columns[2].length(); j++)
                            System.out.print(" ");

                        System.out.print(columns[3]);
                        for (int j = 0; j < 9 - columns[3].length(); j++)
                            System.out.print(" ");
                        System.out.println();
                        if (i == 0)
                            System.out.println();
                        i++;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read CSV file: " + e.getMessage());
        }
    }

    public static void displayBookCsvFile(String filePath, String studId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if (!columns[6].equals("true")) {
                    if (columns[7].equals(studId) || i == 0) {
                        System.out.print(columns[0]);
                        for (int j = 0; j < 10 - columns[0].length(); j++)
                            System.out.print(" ");

                        System.out.print(columns[1]);
                        for (int j = 0; j < 20 - columns[1].length(); j++)
                            System.out.print(" ");

                        System.out.print(columns[2]);
                        for (int j = 0; j < 15 - columns[2].length(); j++)
                            System.out.print(" ");

                        System.out.print(columns[3]);
                        for (int j = 0; j < 9 - columns[3].length(); j++)
                            System.out.print(" ");

                        System.out.print(columns[4]);
                        for (int j = 0; j < 12 - columns[4].length(); j++)
                            System.out.print(" ");

                        System.out.print(columns[5]);
                        for (int j = 0; j < 13 - columns[5].length(); j++)
                            System.out.print(" ");

                        System.out.print(columns[7]);
                        for (int j = 0; j < 12 - columns[7].length(); j++)
                            System.out.print(" ");

                        System.out.print(columns[8]);
                        System.out.println();
                        if (i == 0)
                            System.out.println();
                        i++;
                    }
                }
            }
            if (i == 1)
                System.out.println("No Books Issued...");
        } catch (IOException e) {
            System.out.println("Failed to read CSV file: " + e.getMessage());
        }
    }

    // Method for updating csv file
    public static void updateCsvFile(String filePath, int rowNumber, int columnNumber, String newValue) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int currentRow = 0;
            StringBuilder updatedCsv = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (currentRow == rowNumber) {
                    String[] columns = line.split(",");
                    if (columnNumber >= 0 && columnNumber < columns.length) {
                        columns[columnNumber] = newValue;
                    }
                    updatedCsv.append(String.join(",", columns)).append("\n");
                } else {
                    updatedCsv.append(line).append("\n");
                }
                currentRow++;
            }

            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(updatedCsv.toString());
            } catch (IOException e) {
                System.out.println("Failed to write to CSV file: " + e.getMessage());
            }

        } catch (IOException e) {
            System.out.println("Failed to read CSV file: " + e.getMessage());
        }
    }

    // Method for finding row and column number of a particular value from csv file
    public static int[] findRowColumnNumber(String filePath, String value) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int currentRow = 0;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                for (int i = 0; i < columns.length; i++) {
                    if (columns[i].equals(value)) {
                        return new int[] { currentRow, i };
                    }
                }
                currentRow++;
            }
            return new int[] { -1, -1 };
        } catch (IOException e) {
            System.out.println("Failed to read CSV file: " + e.getMessage());
            return new int[] { -1, -1 };
        }
    }

    // Overloaded method for finding row and column number of a particular value in
    // a particular column from csv file
    public static int[] findRowColumnNumber(String filePath, String value, int columnNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int currentRow = 0;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns[columnNumber].equals(value)) {
                    return new int[] { currentRow, columnNumber };
                }
                currentRow++;
            }
            return new int[] { -1, -1 };
        } catch (IOException e) {
            System.out.println("Failed to read CSV file: " + e.getMessage());
            return new int[] { -1, -1 };
        }
    }
}
