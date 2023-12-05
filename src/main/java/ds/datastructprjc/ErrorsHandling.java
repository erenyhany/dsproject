package ds.datastructprjc;
/*************************/


/************************************/

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ErrorsHandling {
    //makin a tree to determine the childs of each tags setting children for each node
    private final static TreeNode id  = new TreeNode("id",null);
    private final static TreeNode name = new TreeNode("name",null);
    private final static TreeNode topic = new TreeNode("topic",null);
    private final static TreeNode body = new TreeNode("body",null);
    private final static TreeNode topics = new TreeNode("topics",new TreeNode[]{topic,null,null,null});
    private final static TreeNode followers = new TreeNode("followers",new TreeNode[]{id,null,null,null});
    private final static TreeNode post = new TreeNode("post",new TreeNode[]{body,topics,null,null});
    private final static TreeNode posts = new TreeNode("posts",new TreeNode[]{post,null,null,null});
    private final static TreeNode user = new TreeNode("user",new TreeNode[]{id,name,posts,followers});
    private final static TreeNode users = new TreeNode("users", new TreeNode[]{user,null,null,null});     //root of the tree


    private static Map<String , TreeNode> map ;


    public static String detectErrors(String fileText){

        initializeMap();
        Stack<String> tagsStack = new Stack<String>();
        int numOflineTags = 0;     //this variable for detecting if  we've missed  '>' and
                                  // in one line we may have multiple tags like opening and closing so
                                    //to differentiate between them , and


        boolean boolforFirstElement = true;  //to check the first element in the stack is <users>
        StringBuilder errormsg =new StringBuilder();
        int lineCounter=1;


        for( int i = 0 ;i<fileText.length();i++){

            if(fileText.charAt(i)=='\n'){
                lineCounter++;
                numOflineTags=0;
            }

            if(fileText.charAt(i) =='<'){
                numOflineTags++;     //increment this variable to know which '<' is not closed by'>' in the same line
                //the string below (temp) contains what is between < and >
                String temp = findWordAtIndex(fileText,i+1).replaceAll("\\s", "").toLowerCase();
                i= i + temp.length();

                //detect if Users tag (special case) doesnt exist
                if(boolforFirstElement){
                    if(!temp.contains("users") ){
                        errormsg.append("line "+(lineCounter-1)+" :you should add <users> at line \n");
                        tagsStack.push("users");
                    }
                    boolforFirstElement=false;
                }

                //detect if '>'is missing in 2 conditions:(1)if temp contains'\n' or (2)if it contains '<'
                if(temp.contains("<")){
                    errormsg.append("line "+lineCounter+" :the \'<\' no "+numOflineTags+" needs a \'>\'\n");
                    i = i-temp.length()+temp.indexOf('<');

                    for (Map.Entry<String, TreeNode> entry : map.entrySet()){
                        if(temp.contains(entry.getKey())){
                            tagsStack.push(entry.getKey());
                        }
                    }

                }


                //detect if </> is missing
              /*continue from here */
                if(!temp.contains("/")&& !temp.contains("<")){
                    tagsStack.push(temp);
                }

            }
        }
        return errormsg.toString();
    }


    private static String findWordAtIndex(String text, int startIndex) {
        int spaceIndex = text.indexOf('>', startIndex); // Find the next space from startIndex

        // If spaceIndex is -1, set it to the end of the string
        if (spaceIndex == -1) {
            spaceIndex = text.length();
        }

        // Extract the word using substring
        String word = text.substring(startIndex, spaceIndex);

        return word;
    }

    private static void initializeMap(){
        if(map == null){
            map = new HashMap<>();
            map.put("users",users);
            map.put("user",user);
            map.put("posts",posts);
            map.put("id",id);
            map.put("name",name);
            map.put("followers",followers);
            map.put("post",post);
            map.put("body",body);
            map.put("topics",topics);
            map.put("topic",topic);

        }
    }


}


class TreeNode {
    String data;
    TreeNode [] children = new TreeNode[4];

    public TreeNode(String data , TreeNode[]children) {
        this.data = data;
        this.children = children;

    }
    public boolean isParent(TreeNode node){
        boolean success = false;
        for(TreeNode child : children){
            if(node == child) success =true;
        }
        return success;
    }

}

