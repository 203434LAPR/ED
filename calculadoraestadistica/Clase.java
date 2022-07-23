
package calculadoraestadistica;


public class Clase 
{
    private int limInf;
    private int limSup;
    private double limInfEx;
    private double limSupEx;
    private int frec;
    private int frecAcum;
    private double frecRel;
    private double marcaCla;
    private static int num = 0;
    private int clase;

    public Clase(int limInf, int limSup) 
    {
        this.limInf = limInf;
        this.limSup = limSup;
        this.frec = 0;
        this.clase = num + 1;
        num++;
    }

    
    public Clase() 
    {
  
    }

    public int getClase() {
        return clase;
    }
    
    public int getLimInf() {
        return limInf;
    }

    public int getLimSup() {
        return limSup;
    }

    public double getLimInfEx() {
        return limInfEx;
    }

    public double getLimSupEx() {
        return limSupEx;
    }

    public int getFrec() {
        return frec;
    }

    public int getFrecAcum() {
        return frecAcum;
    }

    public double getFrecRel() {
        return frecRel;
    }

    public double getMarcaCla() {
        return marcaCla;
    }

    public void setLimInf(int limInf) {
        this.limInf = limInf;
    }

    public void setLimSup(int limSup) {
        this.limSup = limSup;
    }

    public void setLimInfEx(double limInfEx) {
        this.limInfEx = limInfEx;
    }

    public void setLimSupEx(double limSupEx) {
        this.limSupEx = limSupEx;
    }

    public void setFrec(int frec) {
        this.frec = frec;
    }

    public void setFrecAcum(int frecAcum) {
        this.frecAcum = frecAcum;
    }

    public void setFrecRel(double frecRel) {
        this.frecRel = frecRel;
    }

    public void setMarcaCla(double marcaCla) {
        this.marcaCla = marcaCla;
    }
    
    @Override
    public String toString()
    {
        return String.format("%5d %20d %20d %25.1f %25.1f %23d %23d %23.2f %18.1f" , clase, this.limInf, this.limSup, this.limInfEx, this.limSupEx, this.frec, this.frecAcum, this.frecRel, this.marcaCla);
    }
    
    
}
