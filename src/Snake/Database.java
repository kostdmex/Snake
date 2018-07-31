package Snake;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

class Database
{
    private File fileDatabase;
    Database()
    {
        fileDatabase = new File("Database.txt");
    }
    ArrayList<User> readData()
    {
        ArrayList<User> userList = new ArrayList<>();
        int record;
        String name;
        try {
            Scanner read = new Scanner(fileDatabase);
            while (read.hasNext()) {
                name = read.next();
                record = Integer.parseInt(read.next());
                userList.add(new User(name, record));
            }
        } catch (IOException e) {
            System.out.println("Nie wykryto bazy danych");
        }
        return userList;
    }
    void writeData(ArrayList<User> userList)
    {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(fileDatabase));
            for (int index = 0; index < userList.size(); index++) {
                out.print(userList.get(index).getName()+ " ");
                out.print(userList.get(index).getScore()+ " ");
            }
            out.close();
        }
        catch (IOException e) {
            System.out.println("Nie zapisano bazy danych");
        }
    }
}