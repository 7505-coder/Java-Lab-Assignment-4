package service;

import model.Student;
import util.FileUtil;
import java.util.*;

public class StudentManager {
    private List<Student> students = new ArrayList<>();
    private Map<Integer, Student> map = new HashMap<>();
    private final String dataPath;

    public StudentManager(String dataPath) {
        this.dataPath = dataPath;
        loadFromFile();
    }

    private void loadFromFile() {
        List<Student> fromFile = FileUtil.readStudents(dataPath);
        for (Student s : fromFile) {
            addToCollections(s);
        }
        if (!students.isEmpty()) {
            System.out.println("Loaded students from file:");
            for (Student s : students) {
                s.display();
                System.out.println();
            }
        } else {
            System.out.println("No students loaded from file.");
        }
    }

    private void addToCollections(Student s) {
        students.add(s);
        map.put(s.rollNo, s);
    }

    public boolean addStudent(Student s) {
        if (map.containsKey(s.rollNo)) {
            System.out.println("Duplicate roll number. Cannot add.");
            return false;
        }
        addToCollections(s);
        return true;
    }

    public void viewAll() {
        Iterator<Student> it = students.iterator();
        while (it.hasNext()) {
            Student s = it.next();
            s.display();
            System.out.println("-------------------");
        }
    }

    public Student searchByName(String name) {
        for (Student s : students) if (s.name.equalsIgnoreCase(name)) return s;
        return null;
    }

    public boolean deleteByName(String name) {
        Iterator<Student> it = students.iterator();
        boolean removed = false;
        while (it.hasNext()) {
            Student s = it.next();
            if (s.name.equalsIgnoreCase(name)) {
                it.remove();
                map.remove(s.rollNo);
                removed = true;
            }
        }
        return removed;
    }

    public void sortByMarksDesc() {
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student a, Student b) {
                return Double.compare(b.marks, a.marks);
            }
        });
    }

    public void saveAndExit() {
        FileUtil.writeStudents(dataPath, students);
    }

    // example: return raw file line at offset using FileUtil
    public String readAtOffset(long offset) {
        return FileUtil.readAtOffset(dataPath, offset);
    }
}
