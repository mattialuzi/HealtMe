package View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Graph extends JPanel {
    private ArrayList<Integer> punti;
    // disyanza dal bordo del pannello in px  (per tutte direzioni)
    private int border;
    //massimo valore ordinata
    private int yMax;

    public Graph (ArrayList<Integer> punti){
        this.punti = punti;
        this.border = 30;
        this.yMax =11;
        this.setPreferredSize(new Dimension(1000,300));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //setta la larghezza in pixel di una unita del grafico sia per l'asciss ache per l'ordinata
        int xScale = (getWidth() - 2 * border) / (punti.size() - 1);
        int yScale = (getHeight() - 2 * border) / (yMax - 1);
        //disegna asse x e y, (considera che le coordinate hanno riferieìmento)
        // 0 ----->
        // |
        // |
        // V
        g2.drawLine(border, getHeight() - border, border, border);
        g2.drawLine(border, getHeight() - border, getWidth() - border, getHeight() - border);
        //disegno quadrettatatura

        for(int i=1; i< punti.size(); i++) {
            int x0 = border + i * xScale ;
            int y0 = border;
            int x1=x0;
            int y1= getHeight() - border;
            g2.drawLine(x0,y0,x1,y1);
            g2.drawString(String.valueOf(i),x1,y1+border/2);
        }
        g2.setFont(g2.getFont().deriveFont(yScale));
        for (int i=1; i< yMax; i++) {
            int y0 = getHeight() - border - i * yScale;
            int x0 = border;
            int y1=y0;
            int x1= getWidth() - border ;
            g2.drawLine(x0,y0,x1,y1);
            g2.drawString(String.valueOf(i),border/4,y0);
        }


    }

}
