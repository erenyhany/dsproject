package ds.datastructprjc;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

public class XMLReader {
    static LinkedList<User>UsersList= new LinkedList<User>();
    static StringBuffer fileText ;

    public static void readFile(String fileName)/*throws Exception*/{
        try{

            File file = new File(fileName);
            Scanner extract = new Scanner(file);

            fileText= new StringBuffer();
            String tempString ;

            while(extract.hasNextLine()){
                tempString = extract.nextLine();
                fileText.append(tempString);
                fileText.append("\n");

            }


        }catch (Exception e){
            e.getMessage();
            System.exit(0);
        }
    }

}
