package util;

import model.Student;
import java.io.*;
import java.util.*;

public class FileUtil {

    public static List<Student> readStudents(String path) {
        List<Student> list = new ArrayList<>();
        File f = new File(path);
        if (!f.exists()) return list;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",", -1);
                if (parts.length < 5) continue;
                Integer r = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                String email = parts[2].trim();
                String course = parts[3].trim();
                Double marks = Double.parseDouble(parts[4].trim());
                list.add(new Student(r, name, email, course, marks));
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return list;
    }

    public static void writeStudents(String path, List<Student> students) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (Student s : students) {
                bw.write(s.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    // Example: read a line at a specific byte offset (simple RandomAccessFile demo)
    public static String readAtOffset(String path, long offset) {
        try (RandomAccessFile raf = new RandomAccessFile(path, "r")) {
            raf.seek(offset);
            return raf.readLine();
        } catch (IOException e) {
            return null;
        }
    }
}
