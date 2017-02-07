package View;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class Graph extends JPanel {
    private ArrayList<LocalDate> x;
    private ArrayList<Float> y;
    // disyanza dal bordo del pannello in px  (per tutte direzioni)
    private int border;
    //massimo valore ordinata
    private float yMax;
    private int yFactor;
    private int pointdim;

    public Graph (ArrayList<LocalDate> x,ArrayList<Float> y){
        this.x = x;
        this.y = y;
        this.border = 30;
        float max = 100;
        for (Float valore : y ) {
            if (max < valore)
                max = valore;
        }
        this.yMax =8000 ;
        this.setPreferredSize(new Dimension(1000,300));
        this.yFactor = 500;
        this.pointdim = 5;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //setta la larghezza in pixel di una unita del grafico sia per l'asciss ache per l'ordinata
        double xScale =((double)getWidth() - 2 * border) / (x.size() - 1);
        double yScale = ((double)getHeight() - 2 * border) / (yMax - 1);
        //disegna asse x e y, (considera che le coordinate hanno riferimento)
        // 0 ----->
        // |
        // |
        // V
        g2.drawLine(border, getHeight() - border, border, border);
        g2.drawLine(border, getHeight() - border, getWidth() - border, getHeight() - border);
        //disegna la prima data sull'asse x
        g2.drawString(x.get(0).toString(),border-30, (getHeight() - border/3));
        //disegna quadrettatatura e valori su x e y
        for(int i=1; i< x.size(); i++) {
            int x0 = border + i * (getWidth() - 2 * border) / (x.size() - 1) ;
            int y0 = border;
            int x1=x0;
            int y1= getHeight() - border;
            g2.drawLine(x0,y0,x1,y1);
            g2.drawString(x.get(i).toString(),x1-40,y1+(2*border/3));
        }

        int yScaleFactor = (int) (yFactor * (getHeight() - 2 * border) / (yMax - 1));

        for (int i=1; i< (yMax+1)/yFactor; i++) {
            int y0 = getHeight() - border - i * yScaleFactor;
            int x0 = border;
            int y1=y0;
            int x1= getWidth() - border ;
            g2.drawLine(x0,y0,x1,y1);
            g2.drawString(String.valueOf(i*yFactor),border/4,y0);
        }
        //calcola le coordinate dei punti del grafico corrispondenti ai valori dei due array di dati
        ArrayList<Point> punti = new ArrayList<>();
        for (int i=0; i< y.size(); i++) {
            int x1 = (int) (i*xScale + border);
            int y1 = (int) ((yMax - 1 - y.get(i))* yScale + border);
            punti.add(new Point(x1, y1));
        }
        //disegna le rette che collegano punti consecutivi
        g2.setColor(Color.RED);
        for (int i=0; i< punti.size()-1; i++) {
            int x1 = punti.get(i).x;
            int y1 = punti.get(i).y;
            int x2 = punti.get(i+1).x;
            int y2 = punti.get(i+1).y;
            g2.drawLine(x1, y1, x2, y2);
        }
        //disegna i punti
        g2.setColor(Color.BLUE);
        for (int i=0; i< punti.size(); i++) {
            int x1 = punti.get(i).x - pointdim/2;
            int y1 = punti.get(i).y - pointdim/2;
            g2.fillOval(x1, y1, pointdim, pointdim);

        }


    }

}
