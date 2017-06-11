public class player{
    public String ndname,stname,nick;
    int userID;
    int nMec;
    double score = 0;
    answer_db a;

    public  player(String stname,String ndname,int userid,String nick){
         this.ndname = ndname;
         this.stname = stname;
         this.userID = userid;
         this.nick = nick;
         this.a = new answer_db(1);
    }

    public void addAnswer(int muaid,int exid,int correct){
        a.addAnswer(muaid,exid,correct);        
    }

    public void generate_score(){
        int tmpscore = 0;
        for(int i = 0; i < a.idx();i++){
            tmpscore += a.correctPos(i);
        }
        this.score = tmpscore;
    }


}