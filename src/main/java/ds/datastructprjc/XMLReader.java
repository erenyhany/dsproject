package ds.datastructprjc;

import java.io.File;
import java.util.*;

public class XMLReader {
    static ArrayList<User> UsersList= new ArrayList<>();
    static Vector<Integer> usersVector;
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
//        parsing(fileText.toString());
    }

    public static void parsing(String xmlString)
    {
        StringBuffer insideTagContent = new StringBuffer();
        boolean open = false;
        boolean thisistag = false;
        boolean iaminfollower=false;
        User currentUser = null;
        Post curpost=null;
        ArrayList<User> USERS = new ArrayList<>(Collections.nCopies(100, null)) ;
        Vector<Integer>tempUsersVector= new Vector<Integer>();
        LinkedList<Integer> followers = new LinkedList<Integer>() ;
        LinkedList<Post> posts = new LinkedList<Post>() ;
        LinkedList<String> topics = new LinkedList<String>() ;

        for (int i = 0; i < xmlString.length(); i++) {
            insideTagContent.setLength(0);
            char currentChar = xmlString.charAt(i);

            ////////////////////////////////////////////////////////////////////
            if (currentChar == '<') {


                open = true;
                thisistag=true;

            }
            while (open) {
                insideTagContent.append(xmlString.charAt(i));
                if (xmlString.charAt(i) == '>') {
                    open = false;
                    //System.out.println(insideTagContent);
                    break;
                }
                i++;
            }
            ////////////////////////////////////////////////////

            if(thisistag)
            {
                if(insideTagContent.toString().contains("<user>"))
                {
                    currentUser = new User();
                } else if (insideTagContent.toString().contains("</user>")) {
                    currentUser.setFollowersIDs(new LinkedList<>(followers));
                    currentUser.setPosts(new LinkedList<>(posts));
                    USERS.set(currentUser.getID(),currentUser);
                    tempUsersVector.add(currentUser.getID());

                    followers.clear();
                    posts.clear();
                }

                if(insideTagContent.toString().contains("<followers>"))
                {
                    iaminfollower=true;

                }
                else if(insideTagContent.toString().contains("</followers>"))
                {
                    iaminfollower=false;

                }

                if(insideTagContent.toString().contains("<id>")) {
                    if (iaminfollower) {
                        followers.add(Integer.parseInt(connnnn(xmlString, i)));
                        //System.out.println(connnnn(xmlString, i));
                    }

                    else {
                        //System.out.println(connnnn(xmlString, i));
                        currentUser.setID(Integer.parseInt(connnnn(xmlString, i)));
                    }
                }

                if(insideTagContent.toString().contains("<name>"))
                {
                    currentUser.setName(connnnn(xmlString,i));
                }
                if(insideTagContent.toString().contains("<post>"))
                {
                    curpost = new Post();
                }
                else if(insideTagContent.toString().contains("</post>"))
                {
                    curpost.setTopics(new LinkedList<>(topics));
                    posts.add(curpost);
                    topics.clear();
                }
                if(insideTagContent.toString().contains("<body>"))
                {
                    curpost.setBody(connnnn(xmlString,i));
                }
                if(insideTagContent.toString().contains("<topic>"))
                {
                    topics.add(connnnn(xmlString,i));
                }



                thisistag=false;
            }



        }

        UsersList=USERS;
        usersVector = tempUsersVector;

        }


    public static String connnnn(String xmlString, int i) {


        boolean open = false;
        String content = "";
        i++;
        while (true)
        {

            char currentChar = xmlString.charAt(i);
            if (currentChar == '<') {
                return (content).trim();
            }
            content = content + currentChar;
            i++;

        }

    }

}





