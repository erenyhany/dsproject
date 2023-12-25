package ds.datastructprjc;
import java.util.*;


public class Graph {
    static int Vertices;
    static int Edges=0;
    static ArrayList<LinkedList<User>> adj ;

    static void addEdge(ArrayList<LinkedList<User>> adj, User u, User v) {
        adj.get(u.getID()).add(v);
    }

    void constructGraph(){
        Edges = 0;
        XMLReader.parsing(XMLReader.fileText.toString());
        Vertices = XMLReader.UsersList.size();
        adj = new ArrayList<>(Vertices);
        for (int i = 0; i < Vertices; i++) {
            adj.add(new LinkedList<>());
        }
        for (User user : XMLReader.UsersList) {
            for (int followerID : user.getFollowersIDs()) {
                addEdge(adj, user,XMLReader.UsersList.get(followerID));
                Edges++;
            }
        }
    }

}