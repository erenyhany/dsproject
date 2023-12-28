package ds.datastructprjc;
import java.util.*;


public class Graph {
    static int Vertices;
    static int Edges=0;
    static ArrayList<LinkedList<User>> adj ;

    static void addEdge(ArrayList<LinkedList<User>> adj, User u, User v) {
        adj.get(u.getID()).add(v);
    }

    public static void constructGraph(){
        Edges = 0;
        XMLReader.parsing(XMLReader.fileText.toString());
        Vertices = XMLReader.usersVector.size();
        adj = new ArrayList<>(Collections.nCopies(100, null));
        for (int i = 0; i < Vertices; i++) {
            adj.set(XMLReader.usersVector.get(i),new LinkedList<>());
        }
        for (int availableUser : XMLReader.usersVector) {
            User user =XMLReader.UsersList.get(availableUser);
            for (int followerID : user.getFollowersIDs()) {
                addEdge(adj, user,XMLReader.UsersList.get(followerID));
                XMLReader.UsersList.get(followerID).followingNumber++;
                Edges++;
            }
        }
    }
    static String printGraph() {
        StringBuilder s=new StringBuilder();
        for (int i = 0; i < Vertices; i++) {
        s .append("\nUser with ID " + (XMLReader.usersVector.get(i)) + " is followed by users with IDs: ");
            for (int j = 0; j < adj.get(XMLReader.usersVector.get(i)).size(); j++) {
               s.append((adj.get(XMLReader.usersVector.get(i))).get(j).getID()+" ");
            }
        }
        return s.toString();
    }


}
