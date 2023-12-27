package ds.datastructprjc;
import java.util.*;


public class Graph {
    static int Vertices;
    static int Edges=0;
    static ArrayList<LinkedList<User>> adj ;

    static void addEdge(ArrayList<LinkedList<User>> adj, User u, User v) {
        adj.get(u.getID()-1).add(v);
    }

    public static void constructGraph(){
        Edges = 0;
        XMLReader.parsing(XMLReader.fileText.toString());
        Vertices = XMLReader.UsersList.size();
        adj = new ArrayList<>(Vertices);
        for (int i = 0; i < Vertices; i++) {
            adj.add(new LinkedList<>());
        }
        for (User user : XMLReader.UsersList) {
            for (int followerID : user.getFollowersIDs()) {
                addEdge(adj, user,XMLReader.UsersList.get(followerID-1));
                XMLReader.UsersList.get(followerID-1).followingNumber++;
                Edges++;
            }
        }
    }
    static String printGraph() {
        StringBuilder s=new StringBuilder();
        for (int i = 0; i < adj.size(); i++) {
        s .append("\nUser with ID " + (i+1) + " is followed by users with IDs: ");
            for (int j = 0; j < adj.get(i).size(); j++) {
               s.append(adj.get(i).get(j).getID()+" ");
            }
        }
        return s.toString();
    }


}
