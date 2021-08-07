import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;


public class Main {
    /**
     * Propogated {@link IOException} here
     * {@link #readFile} and {@link #writeOutput} methods should be called here
     * A {@link Scheduler} instance must be instantiated here
     */
//    Main Method
    public static void main(String[] args) throws IOException {
        Assignment[] assignmentArray = null;
//        Catches FileNotFoundException
        try {
//            Reads file with gson
          assignmentArray = readFile(args[0]);
        } catch (FileNotFoundException e) {
        }
//        Creates Scheduler class object
        try{
            Scheduler scheduler = new Scheduler(assignmentArray);
//            Writes output to Json file
                writeOutput("solution_dynamic.json",scheduler.scheduleDynamic());
                writeOutput("solution_greedy.json",scheduler.scheduleGreedy());

        }catch (IllegalArgumentException a){
        }
    }

    /**
     * @param filename json filename to read
     * @return Returns a list of {@link Assignment}s obtained by reading the given json file
     * @throws FileNotFoundException If the given file does not exist
     */
//    Reads file from json file using gson and JsonReader
    private static Assignment[] readFile(String filename) throws FileNotFoundException {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(filename));
            Assignment[] assignmentArray = gson.fromJson(reader, Assignment[].class);
            Arrays.sort(assignmentArray);
        return assignmentArray;
    }

    /**
     * @param filename  json filename to write
     * @param arrayList a list of {@link Assignment}s to write into the file
     * @throws IOException If something goes wrong with file creation
     */
//    WritesOutput to Json file using gson and Catches IOException
    private static void writeOutput(String filename, ArrayList<Assignment> arrayList) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(arrayList, writer);

        }

    }
}
