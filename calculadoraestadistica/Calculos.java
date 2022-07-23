
package calculadoraestadistica;

import java.util.ArrayList;

public class Calculos {

    private ArrayList<Integer> datosNoAgrupados = new ArrayList<>();
    private ArrayList<Clase> clases = new ArrayList<>();
    private double rango;
    private int k;
    private double media;
    private double moda;
    private double mediana;
    private double desEs;
    private int amplitud;
    private static final double UV = 1;

    public Calculos()
    {
        
    }
    public void agregar(int n){
        datosNoAgrupados.add(n);
       
    }

    private void ordenarDatosNoAgrupados() {
        int aux;
        int ent = 0;
        for (int i = 0; i < datosNoAgrupados.size(); i++) 
        {
            for (int j = 0; j < (datosNoAgrupados.size() - ent) - 1; j++) 
            {
                if (datosNoAgrupados.get(j) > datosNoAgrupados.get((j + 1))) 
                {
                    aux = datosNoAgrupados.get(j);
                    datosNoAgrupados.set(j, datosNoAgrupados.get((j + 1)));
                    datosNoAgrupados.set(j + 1, aux);
                }

            }
            ent++;
        }
    }

    private void calcularK() {
        if (datosNoAgrupados.size() < 200) {
            k = (int) Math.round(Math.sqrt(datosNoAgrupados.size()));
        } else {
            k = (int) Math.round(Math.cbrt(datosNoAgrupados.size()));
        }
    }

    private void calcularRango() {
        ordenarDatosNoAgrupados();
        rango = datosNoAgrupados.get(datosNoAgrupados.size() - 1) - datosNoAgrupados.get(0);

    }

    private void calcularAmplitud() {
        calcularK();
        calcularRango();
        amplitud = (int) ((rango / k + UV));
    }

    private void asignarLimites() {
        calcularAmplitud();
        clases.add(new Clase(datosNoAgrupados.get(0), (datosNoAgrupados.get(0) + amplitud - 1)));
        for (int i = 1; i < k; i++) {
            clases.add(new Clase((clases.get(i - 1).getLimInf() + amplitud), (clases.get(i - 1).getLimSup() + amplitud)));
        }

        for (int i = 0; i < k; i++) {
            clases.get(i).setLimInfEx((double) (clases.get(i).getLimInf()) - (UV / 2));
            clases.get(i).setLimSupEx((double) (clases.get(i).getLimSup()) + (UV / 2));
        }

    }

    private void asignarMarcaClase() {
        asignarLimites();
        for (int i = 0; i < k; i++) {
            clases.get(i).setMarcaCla((clases.get(i).getLimSupEx() + clases.get(i).getLimInfEx()) / 2);
        }
    }

    private void asignarFrecAbs() {
        asignarMarcaClase();
        for (int i = 0; i < datosNoAgrupados.size(); i++) {
            for (int j = 0; j < k; j++) {
                if (datosNoAgrupados.get(i) <= clases.get(j).getLimSup() && datosNoAgrupados.get(i) >= clases.get(j).getLimInf()) {
                    clases.get(j).setFrec(clases.get(j).getFrec() + 1);
                }
            }
        }
    }

    private void asignarFrecAcum() {
        asignarFrecAbs();
        clases.get(0).setFrecAcum(clases.get(0).getFrec());
        for (int i = 1; i < k; i++) {
            clases.get(i).setFrecAcum(clases.get(i - 1).getFrecAcum() + clases.get(i).getFrec());
        }

    }

    private void asignarFrecuenciaRel() {
        asignarFrecAcum();
        for (int i = 0; i < k; i++) {
            clases.get(i).setFrecRel((double) (clases.get(i).getFrec()) / datosNoAgrupados.size());
        }

    }

    public void rellenarTabla()throws Exception{
        if(datosNoAgrupados.size() == 0)
        {
            throw new Exception();
        }
            asignarFrecuenciaRel();
        
    }

    private void calcularMedia() {
        double suma = 0;
        for (int i = 0; i < k; i++) {
            suma += (clases.get(i).getMarcaCla()) * (clases.get(i).getFrec());
        }

        media = suma / datosNoAgrupados.size();
    }

    private void calcularModa() {
        int ind = 0;
        int max = clases.get(0).getFrec();
        for (int i = 1; i < k; i++) {
            if (max < clases.get(i).getFrec()) {
                max = clases.get(i).getFrec();
                ind = i;
            }
        }
        if (datosNoAgrupados.size() == 1) {
            moda = (double) (datosNoAgrupados.get(0));
        } else {
            if (ind == 0) {
                moda = clases.get(ind).getLimInfEx() + ((double) (clases.get(ind).getFrec()) / ((clases.get(ind).getFrec()) + (clases.get(ind).getFrec() - clases.get(ind + 1).getFrec()))) * amplitud;
            } else {
                if (ind == (k - 1)) {
                    moda = clases.get(ind).getLimInfEx() + ((double) (clases.get(ind).getFrec() - clases.get(ind - 1).getFrec()) / ((clases.get(ind).getFrec() - clases.get(ind - 1).getFrec()) + (clases.get(ind).getFrec()))) * amplitud;
                } else {
                    moda = clases.get(ind).getLimInfEx() + ((double) (clases.get(ind).getFrec() - clases.get(ind - 1).getFrec()) / ((clases.get(ind).getFrec() - clases.get(ind - 1).getFrec()) + (clases.get(ind).getFrec() - clases.get(ind + 1).getFrec()))) * amplitud;
                }

            }
        }

    }

    private int obtenerPosMediana() {
        double pos = (double) (clases.get(k - 1).getFrecAcum() + 1) / 2;
        for (int i = 0; i < k; i++) {
            if (pos < clases.get(i).getFrecAcum()) {
                return i;
            }
        }
        return -1;
    }

    private void calcularMediana() {

        int clase = obtenerPosMediana();
        if (datosNoAgrupados.size() == 1) {
            mediana = datosNoAgrupados.get(0);
        } else {
            if (clase == 0) {
                mediana = clases.get(clase).getLimInfEx() + ((double) (((double) (datosNoAgrupados.size()) / 2)) / clases.get(clase).getFrec()) * amplitud;
            } else {
                mediana = clases.get(clase).getLimInfEx() + ((double) (((double) (datosNoAgrupados.size()) / 2) - clases.get(clase - 1).getFrecAcum()) / clases.get(clase).getFrec()) * amplitud;
            }
        }

    }

    private void calcularDesEs() {
        double suma = 0;
        for (int i = 0; i < k; i++) {
            suma += Math.pow(Math.abs((clases.get(i).getMarcaCla() - media)), 2) * clases.get(i).getFrec();
        }
        desEs = Math.sqrt(suma / datosNoAgrupados.size());
    }

    public double getMedia() {
        calcularMedia();
        return media;
    }

    public ArrayList<Clase> getClases() {
        return clases;
    }

    public double getModa() {
        calcularModa();
        return moda;
    }

    public double getMediana() {
        calcularMediana();
        return mediana;
    }

    public int getAmplitud() {
        return amplitud;
    }

    public double getDesEs() {
        calcularDesEs();
        return desEs;
    }

    @Override
    public String toString() {
        String cadena = "\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
        cadena += "\n" + String.format("%5s %20s %20s %25s %25s %23s %23s %23s %18s  ", "Clase", "Límite inferior", "Límite superior", "Límite inferior exacto", "Límite superior exacto", "Frecuencia absoluta", "Frecuencia acumulada", "Frecuencia relativa", "Marca de clase");
        cadena += "\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
        for (int i = 0; i < k; i++) {
            cadena += "\n" + clases.get(i).toString();
        }
        cadena += "\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
        return cadena;
    }

}

