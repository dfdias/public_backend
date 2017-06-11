public class player_db{

    private player[] p;

    public player_db (int size){//class contructor

    this.p =new player[size];
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

        
    }



}
