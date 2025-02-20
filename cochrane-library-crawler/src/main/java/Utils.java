import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Utils {
    public static void clearOutputDirectory(String outputFile) {
    java.nio.file.Path outputPath = java.nio.file.Paths.get(outputFile);
    if (java.nio.file.Files.exists(outputPath)) {
        try {
            java.nio.file.Files.delete(outputPath);
        } catch (IOException e) {
            System.err.println("Error deleting file: " + outputPath + " - " + e.getMessage());
        }
    }
    }

    public static void createOutputFile(String outputFile) {
        java.nio.file.Path outputPath = java.nio.file.Paths.get(outputFile).getParent();
        if (outputPath != null && !java.nio.file.Files.exists(outputPath)) {
            try {
            java.nio.file.Files.createDirectories(outputPath);
            } catch (IOException e) {
            System.err.println("Error creating output directory: " + e.getMessage());
            }
        }
    }
    
    public static String formatDate(String date) {
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsedDate = LocalDate.parse(date.trim(), inputFormatter);
            return parsedDate.format(outputFormatter);
        } catch (DateTimeParseException e) {
            System.err.println("Unable to parse date: " + date);
            return date; // Return the original date if parsing fails
        }
    }

    public static String getBaseUrl(String urlString) {
        try {
        URL url = new URI(urlString).toURL();
        return url.getProtocol() + "://" + url.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }
}
