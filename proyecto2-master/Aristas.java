package libreriaBFS;



public class Aristas {
    
    private int n1,n2;

    public String getN1() {
        return "arista" +n1;
    }

    public String getN2() {
        return "arista" +n2;
    }

    public void setN1(int n1) {
        this.n1 = n1;
    }

    public void setN2(int n2) {
        this.n2 = n2;
    }
    
    
    public Aristas(int i, int j){
        
        this.n1=i;
        this.n2=j;

    }
    
    
    
}
