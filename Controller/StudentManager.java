
package Controller;

import Common.Library;
import Model.Course;
import Model.Report;
import Model.Student;
import View.Menu;
import java.util.ArrayList;
import java.util.Collections;


public class StudentManager extends Menu<String> {
    static String[] mc = {"Create", "Find and Sort", "Update/Delete", "Report", "Exit"};
    Library l;
    ArrayList<Student> studentsList;
    ArrayList<Course> coursesList;
    Student s;

    public StudentManager() {
        super("\nStudent Manager:", mc);
        l = new Library();
        studentsList = new ArrayList<>();
        coursesList = new ArrayList<>();
        s = new Student();
    }

    public void execute(int n) {
        switch (n) {
            case 1:
                createStudent();
                break;
            case 2:
                findSort();
                break;
            case 3:
                updateDelete();
                break;
            case 4:
                report();
                break;
            case 5:
                System.exit(0);
        }
    }

    public void createStudent() {
//        String name;
//        int id = l.getInt("Enter Student Id", 1, 1000);
//        while (checkID(studentsList, id)) {
//            System.out.println("The ID you entered was already taken!!!");
//            id = l.getInt("Re-enter Student Id", 1, 1000);
//        }
//        name = l.getString("Enter Student name: ");
//        studentsList.add(new Student(id, name));
//        int semester = l.getInt("Enter semester", 1, 10);
//        String courseName = l.getString("Enter Course name: ");
//        coursesList.add(new Course(id, semester, courseName));

        String name;
        int id = l.getInt("Enter Student Id", 1, 1000);
        if (!checkID(studentsList, id)) {
            name = l.getString("Enter Student name: ");
            studentsList.add(new Student(id, name));
        }
        int semester = l.getInt("Enter semester", 1, 10);
        String courseName = l.getString("Enter Course name: ");
        coursesList.add(new Course(id, semester, courseName));
    }

    public void displayStudent(ArrayList<Student> list_s) {
        for (Student s : list_s) {
            System.out.print("Id: " + s.getId() + " | Name: " + s.getName());
            for (Course cs : coursesList) {
                if (s.getId() == cs.getId()) {
                    System.out.println(" | Semester: " + cs.getSemester() + " | Course name: " + cs.getCourseName());
                }
            }
        }
    }

    public void findSort() {
        if (studentsList == null) {
            System.err.println("The list is empty!!!");
            return;
        }
        ArrayList<Student> list_ByName = listByName(studentsList);
        if (list_ByName.isEmpty()) {
            System.err.println("Student not exist!!!");
        } else {
            Collections.sort(list_ByName);
            displayStudent(list_ByName);
        }
    }

    public ArrayList<Student> listByName(ArrayList<Student> list_s) {
        ArrayList<Student> result = new ArrayList<Student>();
        String name = l.getString("Enter name to search: ");
        for (Student s : list_s) {
            if (s.getName().contains(name)) {
                result.add(s);
            }
        }
        return result;
    }

    public void updateDelete() {
        if (studentsList == null) {
            System.err.println("The list is empty!!!");
            return;
        }
        int id = l.getInt("Enter id to search", 1, 1000);
        ArrayList<Student> list_ById = listById(studentsList, id);
        ArrayList<Course> list_ById_cs = listByIdCS(coursesList, id);
        if (list_ById.isEmpty() || list_ById_cs.isEmpty()) {
            System.err.println("Student not exist!!!");
        } else {
            System.out.println("Do you want to update or delete?");
            System.out.println("1. Update");
            System.out.println("2. Delete");
            Student s = list_ById.get(0);
            Course cs = list_ById_cs.get(0);
            int c = l.getInt("Enter choice: ", 1, 2);
            switch (c) {
                case 1:
                    //Exec update
                    s.setId(id);
                    s.setName(l.getString("Enter name: "));
                    cs.setId(id);
                    cs.setSemester(l.getInt("Enter Semester", 1, 10));
                    cs.setCourseName(l.getString("Enter Course name: "));
                    System.out.println("Update student succcess!!!");
                    break;
                case 2:
                    coursesList.remove(cs);
                    studentsList.remove(s);
                    System.out.println("Update student success!!!");
                    break;
                default:
                    return;
            }
        }
    }

    public ArrayList<Course> listByIdCS(ArrayList<Course> list_s, int id) {
        ArrayList<Course> result = new ArrayList<Course>();

        for (Course s : list_s) {
            if (s.getId() == id) {
                result.add(s);
            }
        }
        return result;
    }

    public ArrayList<Student> listById(ArrayList<Student> list_s, int id) {
        ArrayList<Student> result = new ArrayList<Student>();

        for (Student s : list_s) {
            if (s.getId() == id) {
                result.add(s);
            }
        }
        return result;
    }

    public Student GetById(int id) {
        for (Student st : studentsList) {
            if (st.getId() == id) {
                return st;
            }
        }
        return null;
    }

    public boolean checkID(ArrayList<Student> list, int id) {
        if (list.isEmpty()) {
            return false;
        } else {
            for (Student s : studentsList) {
                if (s.getId() == id) {
                    return true;
                }
            }
        }
        return false;
    }

    public void report() {
        if (studentsList == null) {
            System.err.println("The list is empty!!!");
            return;
        }
        ArrayList<Report> reportsList = new ArrayList<>();
        for (Course cs : coursesList) {
            int total = 1;  
            int id = cs.getId();
            String courseName = cs.getCourseName();
            boolean found = false;

            for (Report r : reportsList) {
                if (r.getId() == id && r.getCourseName().equalsIgnoreCase(courseName)) {
                    r.setTotalCourse(r.getTotalCourse() + 1);
                    found = true;
                    break;
                }
            }

            if (!found) {

                reportsList.add(new Report(id, courseName, total));
            }
        }
        for (int i = 0; i < reportsList.size(); i++) {
            System.out.println("Id:" + reportsList.get(i).getId() + "  | Course: " + reportsList.get(i).getCourseName() + " | Total: " + reportsList.get(i).getTotalCourse());
        }
    }

    public boolean checkReport(ArrayList<Report> reportsList, int id, String courseName, int total) {
        for (Report rp : reportsList) {
            if (id == rp.getId() && courseName.equalsIgnoreCase(rp.getCourseName()) && total == rp.getTotalCourse()) {
                return false;
            }
        }
        return true;
    }
}
