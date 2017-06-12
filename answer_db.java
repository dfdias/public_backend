import java.util.Arrays;

public class answer_db{

    private answer[] a = new answer[100];
    private int idx = 0;
    private int size = 0;

    public answer_db (int size){//class contructor

    this.a =new answer[size];
    this.size = size;
    this.idx = 0;
    }


    public void addAnswer(int muaid, int exid, int correct){
        if(checkSize()){ //checks if database size is bigger than initialization size
         answer tmp = new answer(muaid,exid,correct);
         a[idx]=tmp;
         idx++;

        }
        else{
            extendArray();
            addAnswer(muaid,exid,correct);
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
        a = Arrays.copyOf(a, size + 100);//allocates 100+ more positions on array
        size += 100;
    }

    public int size(){
        return size;
    }

    public int correctPos(int pos){
        return a[pos].correct();
    }
    public int idx(){
        return idx;
    }


/*    private void initializeDB(){
        for(int i = 0; i < p.length;i++){
         p[i].ndname = " ";
         p[i].stname = " ";
         p[i].userID = 0;
         p[i].nick = " ";
        }
    }

    public void printDB(){
        for(int i = 0; i < idx;i++){
            System.out.print([i].nick);
            System.out.print(" ");
            System.out.print(p[i].stname);  
            System.out.print(" ");    
            System.out.print(p[i].ndname);
             System.out.print(" ");    
            System.out.print(p[i].userID);
            System.out.println(" ");
        }
    }
    */
}
