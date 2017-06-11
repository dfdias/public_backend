public class answer{//mimics siacua's  database MegUserAnswer table 
    private int correct;
   // private int weight;
    private int exid;
    private int muaid;
    

    public answer(int muaid, int exid, int correct){
        this.muaid = muaid;
        this.exid = exid;
        this.correct = correct;
    }

        
}