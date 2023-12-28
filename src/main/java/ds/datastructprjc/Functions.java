package ds.datastructprjc;


import java.util.*;

//this is an interface for the functions
public class Functions {
    public static Stack<String> undoStack = new Stack<String>();
    public static  Stack<String >redoStack = new Stack<String>();
    public static String detectError(String fileText){
        ErrorsHandling.handleErrors(fileText);
        String temp = ErrorsHandling.errormsg.toString();
        undoStack.push(temp);
        return  temp;
    }

    public static String correctError(String fileText){
        ErrorsHandling.handleErrors(fileText);
        String temp=ErrorsHandling.correctedFile.toString();
        undoStack.push(temp);
        return  temp;
    }

    public static String prettify(String fileText){
        String temp = Prettifying.Prettifying(new StringBuffer(fileText)).toString();
        undoStack.push(temp);
        return temp;
    }

    public static String minify(String fileText){
        String temp =CompDecomp.minifying(fileText);
        undoStack.push(temp);
        return temp;
    }

    public static String compress(String fileText){
        char x = CompDecomp.xmlORjson(fileText);
        if(x == '{'){
            String temp =CompDecomp.jsonEncode(fileText);
            undoStack.push(temp);
            return temp;
        }
        else {
            String temp =CompDecomp.xmlEncode(fileText);
            undoStack.push(temp);
            return temp;
        }
    }

    public static String decompress(String fileText){
        char x = CompDecomp.xmlORjson(fileText);
        if(x == '{'){
            String temp = CompDecomp.jsonDecode(fileText);
            undoStack.push(temp);
            return temp ;
        }
        else{
            String temp = CompDecomp.xmlDecode(fileText);
            undoStack.push(temp);
            return temp ;
        }
    }

    public static String toJSON(String fileText){
        String temp =JSONHandling.XmltoJson(fileText);
        undoStack.push(temp);
        return temp ;
    }

    public static String undo(){
        if(undoStack.size() > 1){
            String temp = undoStack.peek();
            undoStack.pop();
            redoStack.push(temp);
            return undoStack.peek();
        }else {

            return undoStack.peek();
        }
    }

    public static String redo(){

        if(redoStack.size() >=1){
            String temp = redoStack.peek();
            redoStack.pop();
            undoStack.push(temp);
            return temp;
        }
        return "no operation is done";
    }
        public static String postSearch(String wordToFind){
        StringBuffer result = new StringBuffer(wordToFind + " found at:\n");
        int  postCounter =0;
        for (Integer userid : XMLReader.usersVector){
            User user = XMLReader.UsersList.get(userid);
            postCounter = 0;
            for(Post post : user.getPosts()){
                postCounter++;
                if(post.getTopics().contains(wordToFind)){
                    result.append("post "+postCounter+" TOPIC of user number "+userid+"\n");
                }
                if(post.getBody().contains(wordToFind)){
                    result.append("post "+postCounter+" BODY of user number "+userid+"\n");
                }

            }
        }

        return result.toString();
    }

    public static String mostInfluencer(){
        int max=0, index=0,n;
        for(Integer userid : XMLReader.usersVector){
            n=(Graph.adj.get(userid)).size();
            if(n>max){
                max=n;
                index=userid;
            }
        }
        return "Most Influencer : "+(XMLReader.UsersList.get(index)).getName();
    }

    public static String mostActive(){
        int max=0, index=0,n;
        for(Integer userid : XMLReader.usersVector){
            //check number of followings if greater than max
            n=(XMLReader.UsersList.get(userid)).followingNumber;
            if(n>max){
                max=n;
                index=userid;//set index of user have most followings
            }
        }
        return "Most Active: "+(XMLReader.UsersList.get(index)).getName();
    }

    public static String mutualFollowers(int x, int y){
        User a = XMLReader.UsersList.get(x);
        User b = XMLReader.UsersList.get(y);
        List<Integer> nums = new ArrayList<>();
        int longer = (a.followersIDs.size()>b.followersIDs.size())?1:2; //get which user has more followers
        int size = (longer==1)?a.followersIDs.size():b.followersIDs.size(); // get max number of followers one has
        int i=0;

        //search for common followers using User with most followers as reference
        while(i<size){
            if(longer ==1){
                if(b.followersIDs.contains(a.followersIDs.get(i))){
                    nums.add(a.followersIDs.get(i));
                }
                i++;
            }
            else if(longer ==2) {
                if (a.followersIDs.contains(b.followersIDs.get(i))) {
                    nums.add(b.followersIDs.get(i));
                }
                i++;
            }
        }

        //add names to string
        String names="Mutual followers: ";
        for(int j=0;j<nums.size();j++){
            names=names.concat((XMLReader.UsersList.get(nums.get(j))).getName());
            names=names.concat(", ");
        }

        return names;
    }

    public static String suggestFollow(int x){
        User a = XMLReader.UsersList.get(x);
        LinkedList<Integer> temp = new LinkedList<>();
        List<Integer> nums = new ArrayList<>();
        for(int i=0; i<a.followersIDs.size(); i++){
            temp = (XMLReader.UsersList.get(a.followersIDs.get(i))).followersIDs;

            for(int j=0; j<temp.size(); j++){
                if(!(a.followersIDs.contains(temp.get(j))) && !nums.contains(temp.get(j)) && (temp.get(j) != a.getID())){
                    nums.add(temp.get(j));
                }
            }

        }
        String names="Suggested users to follow: ";
        for(int j=0;j<nums.size();j++){
            names=names.concat((XMLReader.UsersList.get(nums.get(j))).getName());
            names=names.concat(", ");
        }

        return names;
    }
}
