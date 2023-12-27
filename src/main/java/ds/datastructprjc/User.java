package ds.datastructprjc;


import java.util.LinkedList;

public class User {
    private int ID;
    private String name;
    LinkedList<Post> posts = new LinkedList<Post>() ;
    LinkedList<Integer>followersIDs = new LinkedList<Integer>();
    int followingNumber ;

    public User(int ID, String name) {
        this.ID = ID;
        this.name = name;
        followingNumber = 0 ;

    }

    public User(){}

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<Post> getPosts() {
        return posts;
    }

    public void setPosts(LinkedList<Post> posts) {
        this.posts = posts;
    }

    public LinkedList<Integer> getFollowersIDs() {
        return followersIDs;
    }

    public void setFollowersIDs(LinkedList<Integer> followersIDs) {
        this.followersIDs = followersIDs;
    }

    public void addPost(Post newPost){
        posts.add(newPost);
    }
    public void addFollower(int id){
        followersIDs.add(id);

    }


}

class Post{
    String body ;
    LinkedList<String> topics = new LinkedList<String>();

    public String getBody() {
        return body;
    }

    public LinkedList<String> getTopics() {
        return topics;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setTopics(LinkedList<String> topics) {
        this.topics = topics;
    }

    Post(){}
}
