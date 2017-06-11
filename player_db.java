import java.util.Arrays;

public class player_db{

    private player[] p;
    private int idx,size;

    public player_db (int size){//class contructor

    this.p =new player[size];
    this.size = size;
    }

    public boolean exists(String login){
        boolean flag = false;
        for(int i = 0; i < p.length;i++){
            if(login.equals(p[i].nick)){
                flag = true;
            }
            else
                flag = false;
        }
        return flag;
    }

    public void addPlayer(String login,int userID,String stname,String ndname){   
        if(checkSize()){ //checks if database size is bigger than initialization size
         p[idx].ndname = ndname;
         p[idx].stname = stname;
         p[idx].userID = userID;
         p[idx].nick = login;
         idx++;
        }
        else{
            extendArray();
            addPlayer(login, userID, stname, ndname);
        }
    }

    private boolean checkSize(){
        if(idx == size){
            return false;
        }else{
            return true;
        }
    }

    private void extendArray(){
        p = Arrays.copyOf([]p, size + 100);//allocates 100+ more positions to be added on database
        size += 100;
    }


}
