package View;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * La Classe Graph contiene metodi di costruzione di grafici
 */

public class Graph extends JPanel {
    private ArrayList<LocalDate> arrayx1 = new ArrayList<>();
    private ArrayList<Double> arrayy1 = new ArrayList<>();
    private ArrayList<Double> arrayy2 = new ArrayList<>();
    // disyanza dal bordo del pannello in px  (per tutte direzioni)
    private int borderx = 30;
    //massimo valore ordinata
    private double yMax;
    private int yFactor;
    private int xFactor = 60;
    private int pointdim = 5;
    private int  bordery = 20;

    public Graph (ArrayList<LocalDate> x1,ArrayList<Double> y1){
        this.arrayx1 = x1;
        this.arrayy1 = y1;
        double max = 100;
        for (Double valore : y1 ) {
            if (max < valore)
                max = valore;
        }
        this.yMax = max ;
        int i = 1;
        while((max=max/10) > 1) i++;
        double j = (Math.pow(10,i))/2;

        if (yMax <= j) yMax=(int) j;
        else yMax = (int) j*2;

        this.yFactor =(int) yMax/20;
        this.setPreferredSize(new Dimension((x1.size()-1)*xFactor+2*borderx,200));
    }

    public Graph (ArrayList<LocalDate> x1,ArrayList<Double> y1,ArrayList<Double> y2){
        this.arrayx1 = x1;
        this.arrayy1 = y1;
        this.arrayy2 = y2;
        double max1 = 100;
        for (Double valore : y1 ) {
            if (max1 < valore)
                max1 = valore;
        }
        double max2 = 100;
        for (Double valore : y2 ) {
            if (max2 < valore)
                max2 = valore;
        }
        if (max2 > max1) this.yMax = max2 ;
        else this.yMax = max1;

        double tempmax = yMax;

        int i = 1;
        while((tempmax=tempmax/10) > 1) i++;
        double j = (Math.pow(10,i))/2;

        if (yMax <= j) yMax=(int) j;
        else yMax = (int) j*2;

        this.yFactor =(int) yMax/20;
        this.setPreferredSize(new Dimension((x1.size()-1)*xFactor+2*borderx,200));
    }

    /**
     * Override del metodo paintComponenet
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //setta la larghezza in pixel di una unita del grafico sia per l'asciss ache per l'ordinata
        double xScale = ((double) getWidth() - 2 * borderx) / (arrayx1.size() - 1);
        double yScale = ((double) getHeight() - 2 * bordery) / (yMax - 1);
        //disegna quadrettatatura e valori su arrayx1 e arrayy1
        g2.setFont(new Font("Arial", Font.PLAIN, bordery / 2));
        for (int i = 0; i < arrayx1.size(); i++) {
            //int x0 = borderx + i * (getWidth() - 2 * borderx) / (arrayx1.size() - 1) ;
            int x0 = borderx + i * xFactor;
            int y0 = (int) (getHeight() - bordery - yMax * yScale);
            int x1 = x0;
            int y1 = getHeight() - bordery;
            g2.drawLine(x0, y0, x1, y1);
            g2.drawString(arrayx1.get(i).toString(), x1 - borderx + 1, y1 + (2 * bordery / 3));
        }

        for (int i = 0; i <= yMax / yFactor; i++) {
            int y0 = (int) (getHeight() - bordery - i * yScale * yFactor);
            int x0 = borderx;
            int y1 = y0;
            int x1 = getWidth() - borderx;
            g2.drawLine(x0, y0, x1, y1);
            g2.drawString(String.valueOf(i * yFactor), borderx / 5, y0);
        }
        //linea verticale a destra di fine grafico
        g2.drawLine(getWidth() - borderx, bordery, getWidth() - borderx, getHeight() - bordery);

        //calcola le coordinate dei punti1 del grafico corrispondenti ai valori dei due array di dati
        ArrayList<Point> punti1 = new ArrayList<>();
        ArrayList<Point> punti2 = new ArrayList<>();
        for (int i = 0; i < arrayy1.size(); i++) {
            int x1 = (int) (i * xFactor + borderx);
            int y1 = (int) ((yMax - 1 - arrayy1.get(i)) * yScale + bordery);
            punti1.add(new Point(x1, y1));
            if (arrayy2.size() != 0) {
                int y2 = (int) ((yMax - 1 - arrayy2.get(i)) * yScale + bordery);
                punti2.add(new Point(x1, y2));
            }
        }
        //disegna le rette che collegano punti1 consecutivi
        g2.setColor(Color.RED);
        for (int i = 0; i < punti1.size() - 1; i++) {
            int x1 = punti1.get(i).x;
            int y1 = punti1.get(i).y;
            int x2 = punti1.get(i + 1).x;
            int y2 = punti1.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }
        //disegna i punti1
        g2.setColor(Color.RED);
        for (int i = 0; i < punti1.size(); i++) {
            int x1 = punti1.get(i).x - pointdim / 2;
            int y1 = punti1.get(i).y - pointdim / 2;
            g2.fillOval(x1, y1, pointdim, pointdim);

        }


        if (arrayy2.size() != 0) {
            //disegna le rette che collegano punt2 consecutivi
            g2.setColor(Color.BLUE);
            for (int i = 0; i < punti2.size() - 1; i++) {
                int x1 = punti2.get(i).x;
                int y1 = punti2.get(i).y;
                int x2 = punti2.get(i + 1).x;
                int y2 = punti2.get(i + 1).y;
                g2.drawLine(x1, y1, x2, y2);
            }
            //disegna i punti2
            g2.setColor(Color.BLUE);
            for (int i = 0; i < punti2.size(); i++) {
                int x1 = punti2.get(i).x - pointdim / 2;
                int y1 = punti2.get(i).y - pointdim / 2;
                g2.fillOval(x1, y1, pointdim, pointdim);

            }
        }
    }
}
