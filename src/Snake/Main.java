package Snake;

import javax.xml.crypto.Data;

public class Main
{
    public static void main(String[] args)
    {
        Menu menu = new Menu();
        menu.runMenu();
        Database save = new Database();
        save.writeData(UserList.getUsersList());
    }
}
