import java.util.ArrayList;
import java.util.Scanner;

class Student {
    String name;
    double grade;

    Student(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }
}

public class GradeTracker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();
        int numberOfStudents;

        System.out.print("Enter the number of students: ");
        numberOfStudents = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < numberOfStudents; i++) {
            System.out.print("Enter student " + (i + 1) + "'s name: ");
            String name = scanner.nextLine();
            System.out.print("Enter " + name + "'s grade: ");
            double grade = scanner.nextDouble();
            scanner.nextLine();

            students.add(new Student(name, grade));
        }
        double total = 0;
        double highest = Double.MIN_VALUE;
        double lowest = Double.MAX_VALUE;
        String highestStudent = "";
        String lowestStudent = "";

        for (Student student : students) {
            total += student.grade;

            if (student.grade > highest) {
                highest = student.grade;
                highestStudent = student.name;
            }

            if (student.grade < lowest) {
                lowest = student.grade;
                lowestStudent = student.name;
            }
        }

        double average = total / numberOfStudents;

        System.out.println("\nGrade Report:");
        System.out.println("Average grade: " + average);
        System.out.println("Highest grade: " + highest + " by " + highestStudent);
        System.out.println("Lowest grade: " + lowest + " by " + lowestStudent);
    }
}
