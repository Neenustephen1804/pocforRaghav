package BulkRenamer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BulkRenamer {

    public static void main(String[] args) {
        // Step 1: Identify the folder and call the method
        renameScreenshots("/Users/neenustephen/Documents/Screenshots");
    }

    /**
     * Renames all PNG files in a folder to 'yy-MM-dd_screenshot_#'
     * @param folderPath The path to the folder containing screenshots
     */
    public static void renameScreenshots(String folderPath) {
        File folder = new File(folderPath);

        // Step 2: Read all PNG files one by one
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));

        if (files != null && files.length > 0) {
            String datePrefix = new SimpleDateFormat("yy-MM-dd").format(new Date());
            int counter = 1;

            // Step 3: Rename until all of them are complete
            for (File file : files) {
                String newName = datePrefix + "_screenshot_" + counter + ".png";
                File dest = new File(folder, newName);

                if (file.renameTo(dest)) {
                    System.out.println("Renamed: " + file.getName() + " -> " + newName);
                    counter++;
                } else {
                    System.err.println("Failed to rename: " + file.getName());
                }
            }
        } else {
            System.out.println("No PNG files found in the directory.");
        }

        // Step 4: Exit message
        System.out.println("Process complete. Exiting...");
    }
}
