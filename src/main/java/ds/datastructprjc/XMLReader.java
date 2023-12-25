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
        LinkedList<User> USERS = new LinkedList<User>() ;
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
                    USERS.add(currentUser);//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm adding to Userlist

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





