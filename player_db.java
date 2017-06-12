import java.util.Arrays;

public class player_db{

    private player[] p = new player[100];
    private int idx = 0;
    private int size = 0;
    
    public player_db (int size){//class contructor

    this.p =new player[size];
    this.size = size;
    this.idx = 0;
    }

    public boolean exists(String login){
        boolean flag = false;
        for(int i = 0; i <idx;i++){
            if(login.equals(p[i].nick)){
                flag = true;
            }
            else
                flag = false;
        }
        return flag;
    }

    public int findLogin(String login){
        int pos = 0;
        for(int i = 0; i <idx;i++){
         if(login.equals(p[i].nick)){
                pos = i;
                break;
            }
        }
        return pos;
    }

    public void addPlayer(String stname,String ndname,int userid,String nick){
        if(checkSize()){ //checks if database size is bigger than initialization size
         player tmp = new player(stname,ndname,userid,nick);
         p[idx]=tmp;
         idx++;

        }
        else{
            extendArray();
            addPlayer(stname,ndname,userid,nick);
        }
    }
    public void addAnswer(int muaid,int exid,int correct,int pos){
        p[pos].addAnswer(muaid,exid,correct);        
    }

    private boolean checkSize(){
        if(idx == size){
            return false;
        }else{
            return true;
        }
    }

    private void extendArray(){
        p = Arrays.copyOf(p, size + 100);//allocates 100+ more positions to be added on database
        size += 100;
    }

    public int size(){
        return size;
    }

    public int idx(){
        return idx;
    }

    public String loginPos(int i){
        String a = p[i-1].nick;
        return a;
    }

    private void initializeDB(){
        for(int i = 0; i < p.length;i++){
         p[i].ndname = " ";
         p[i].stname = " ";
         p[i].userID = 0;
         p[i].nick = " ";
        }
    }

    public void printDB(){
        for(int i = 0; i < idx;i++){
            System.out.print(p[i].nick);
            System.out.print(" ");
            System.out.print(p[i].stname);  
            System.out.print(" ");    
            System.out.print(p[i].ndname);
             System.out.print(" ");    
            System.out.print(p[i].userID);
            System.out.println(" ");
        }
    }
        public void printDB_score(){
        for(int i = 0; i < idx;i++){
            System.out.print(p[i].nick);
            System.out.print(" ");
            System.out.print(p[i].stname);  
            System.out.print(" ");    
            System.out.print(p[i].ndname);
            System.out.print(" ");    
            System.out.print(p[i].userID);
            System.out.print(" ");
            p[i].generate_score();
            System.out.println(p[i].score);
            System.out.println(" ");
        }
    }
}
