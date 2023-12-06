package ds.datastructprjc;
/*************************/

/************************************/

import java.util.*;

public class ErrorsHandling {
    private static StringBuilder correctedFile;
    private static StringBuilder errormsg;
    private static String [] tagNames = {"id","posts","post","users","user","followers","follower","topics","topic","name","body"};
    //making a tree to determine the childs of each tags setting children for each node
    private final static TreeNode id  = new TreeNode(tagNames[0],new TreeNode[]{null,null,null,null});
    private final static TreeNode name = new TreeNode(tagNames[9],new TreeNode[]{null,null,null,null});
    private final static TreeNode topic = new TreeNode(tagNames[8],new TreeNode[]{null,null,null,null});
    private final static TreeNode body = new TreeNode(tagNames[10],new TreeNode[]{null,null,null,null});
    private final static TreeNode topics = new TreeNode(tagNames[7],new TreeNode[]{topic,null,null,null});
    private final static TreeNode follower = new TreeNode(tagNames[6],new TreeNode[]{id,null,null,null});
    private final static TreeNode followers = new TreeNode(tagNames[5],new TreeNode[]{follower,null,null,null});

    private final static TreeNode post = new TreeNode(tagNames[2],new TreeNode[]{body,topics,null,null});
    private final static TreeNode posts = new TreeNode(tagNames[1],new TreeNode[]{post,null,null,null});
    private final static TreeNode user = new TreeNode(tagNames[4],new TreeNode[]{id,name,posts,followers});
    private final static TreeNode users = new TreeNode(tagNames[3], new TreeNode[]{user,null,null,null});     //root of the tree


    private static Map<String , TreeNode> map = null;





    //this function detects and corrects errors and put the detected msg in a static variable called errormsg
    //and the correction in a static variable called
    public static void handleErrors(String fileText){

        initializeMap();
        errormsg =new StringBuilder();
        correctedFile = new StringBuilder(fileText);
        Stack<String> tagsStack = new Stack<String>();
        int lineCounter=1;
        int numOflineTags = 0;     //this variable for detecting if  we've missed  '>' and
                                  // in one line we may have multiple tags like opening and closing so
                                    //to differentiate between them , and
        int iCorrect=0;
        boolean boolforFirstElement = true;  //to check the first element in the stack is <users>

        for( int i = 0 ;i<fileText.length();i++){        //main loop

            if(fileText.charAt(i)=='\n'){
                lineCounter++;
                numOflineTags=0;

            }

            if(fileText.charAt(i) =='<'){
                numOflineTags++;     //increment this variable to know which '<' is not closed by'>' in the same line
                //the string below (temp) contains what is between < and >
                String temp = findWordAtIndex(fileText,i+1).replaceAll("\\s", "").toLowerCase();

                //detect if Users tag (special case) doesnt exist
                if(boolforFirstElement){
                    tagsStack.push("users");
                    if(!temp.contains("users") ){
                        errormsg.append("line "+(lineCounter-1)+" :you should add <users> at line \n");
                        correctedFile.delete(0, fileText.indexOf('<'));
                        correctedFile.insert(0,"<users>\n");
                        iCorrect =8;

                    }
                    boolforFirstElement=false;
                }

                //detect if '>'is missing in one conditions:(1)if temp contains '<'
                if(temp.contains("<")){
                    errormsg.append("line "+lineCounter+" :the \'<\' no "+numOflineTags+" needs a \'>\'\n");


                    for(int x=0;x<tagNames.length;x++){
                        if(temp.substring(0,temp.indexOf('<')).contains(tagNames[x])){

                            if(temp.substring(0,temp.indexOf('<')).contains("/")){
                                correctedFile.insert(iCorrect+2+tagNames[x].length(),">");
                                tagsStack.pop();
                            }else{
                                correctedFile.insert(iCorrect+1+tagNames[x].length(),">");
                                tagsStack.push(tagNames[x]);

                            }
                            iCorrect++;
                            break;
                        }
                    }


                }


                //detect if the tag doesn't have a '/' :
                // 1) if the top of the stack can be a parent of the current tag then push the current tag
                // 2)else we should detect the error :(a)if peek and temp are sibllings:the tag in the peek should be closed
                //                                    (b)if temp is a grandChild of the peek then we must
                //                                    open the intermediate tag (parent of temp and child of peek)
                if(!temp.contains("/")&& !temp.contains("<")){
                    if(parentAndChild(tagsStack.peek(),temp)){
                        //there are no errors in this case
                        tagsStack.push(temp);
                    }else if (areSibillings(getElementBeforePeek(tagsStack),tagsStack.peek(),temp)){
                        //there is an error in this case
                        

                    }
                }

            }

            iCorrect++;
        }//main loop

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
            map.put("follower",follower);
            map.put("post",post);
            map.put("body",body);
            map.put("topics",topics);
            map.put("topic",topic);

        }
    }

    public static String getElementBeforePeek(Stack<String> stack) {
        String elementBeforePeek = null;
        if (stack.size() >= 2) {
            String temp = stack.peek();
            stack.pop();
            elementBeforePeek= stack.peek();
            stack.push(temp);
        }
        return elementBeforePeek;
    }



    private static boolean parentAndChild(String parent ,String child){
        return (map.get(parent)).isParent(map.get(child));
    }

    private static boolean areSibillings(String comonFather , String sib1 , String sib2){
        return  (map.get(comonFather)).comonFather(map.get(sib1),map.get(sib2));
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

    public boolean comonFather(TreeNode sibilling1, TreeNode sibilling2){
        return  this.isParent(sibilling1) && this.isParent(sibilling2);
    }

}

