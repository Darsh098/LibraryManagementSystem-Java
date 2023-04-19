import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//import java.util.List;

class CsvFileHandler {
    public static void writeObjectToCsvFile(String filePath, Student obj) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            // write CSV header
            if (CsvFileHandler.findDataInCsvFile("user.csv", "fullname") != null) {
                writer.append("fullname,username,password,studentId\n");
            }
            // write objects to CSV
            // for (Student obj : objects) {
            writer.append(obj.getFullname()).append(",");
            writer.append(obj.getUsername()).append(",");
            writer.append(obj.getPassword()).append(",");
            writer.append(obj.getStudentId()).append("\n");
            // }

            System.out.println("Objects written to CSV file successfully!");

        } catch (IOException e) {
            System.out.println("Failed to write objects to CSV file: " + e.getMessage());
        }
    }

    public static String[] findDataInCsvFile(String filePath, String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns[1].equals(username)) {
                    // System.out.println("Found matching row: " + line);
                    return columns;
                }
            }

            System.out.println("No matching rows found for name: " + username);
        } catch (IOException e) {
            System.out.println("Failed to read CSV file: " + e.getMessage());
        }
        return null;
    }

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
                System.out.println("Value updated in CSV file: " + newValue);
            } catch (IOException e) {
                System.out.println("Failed to write to CSV file: " + e.getMessage());
            }

        } catch (IOException e) {
            System.out.println("Failed to read CSV file: " + e.getMessage());
        }
    }

    public static int[] findRowColumnNumber(String filePath, String value) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int currentRow = 0;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                for (int i = 0; i < columns.length; i++) {
                    if (columns[i].equals(value)) {
                        System.out.println("Value " + value + " found at row " + currentRow + ", column " + i);
                        return new int[] { currentRow, i };
                    }
                }
                currentRow++;
            }
            System.out.println("Value " + value + " not found in CSV file");
            return new int[] { -1, -1 };
        } catch (IOException e) {
            System.out.println("Failed to read CSV file: " + e.getMessage());
            return new int[] { -1, -1 };
        }
    }

    public static int[] findRowColumnNumber(String filePath, String value, int columnNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int currentRow = 0;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns[columnNumber].equals(value)) {
                    System.out.println("Value " + value + " found at row " + currentRow + ", column " + columnNumber);
                    return new int[] { currentRow, columnNumber };
                }
                currentRow++;
            }
            System.out.println("Value " + value + " not found in CSV file");
            return new int[] { -1, -1 };
        } catch (IOException e) {
            System.out.println("Failed to read CSV file: " + e.getMessage());
            return new int[] { -1, -1 };
        }
    }

    // public static void main(String[] args) {
    // Student obj1 = new Student(1, "Darsh", 20);

    // // CsvWriter.writeObjectToCsvFile("user.csv", obj1);
    // CsvReader.findDataInCsvFile("user.csv", "Darsh");

    // int[] location = CsvReader.findRowColumnNumber("user.csv", "Naitik");
    // int rowNumber = location[0];
    // int columnNumber = location[1];
    // if (rowNumber >= 0 && columnNumber >= 0) {
    // CsvWriter.updateCsvFile("user.csv", rowNumber, columnNumber, "Viplove");
    // } else {
    // System.out.println("Value not found in CSV file");
    // }
    // }
}