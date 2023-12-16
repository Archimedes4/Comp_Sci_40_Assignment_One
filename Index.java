/*
  Andrew Mainella
  Ms Latimer
  18 October 2023

  Assignment One: Financial Calculator
  Level: Gold

  **PLEASE IGNORE the below line 216***

  Also I went a bit above an beyond with the icons. I used stack overflow to help :) 
  https://stackoverflow.com/questions/6975736/java-joptionpane-showmessagedialog-custom-icon-problem.

  Which pointed me to https://docs.oracle.com/javase/tutorial/uiswing/components/icon.html
*/

import java.util.ArrayList;
import java.util.List;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import javax.swing.*;
import java.awt.event.*;

class Index {


  static void interestCalculator() {
    //Interest Calculator Method
    Double i = 0.0; //Interest
    while (true) {
      String iOut = JOptionPane.showInputDialog(null, "Please enter an interest Rate (In Decimal 1.0 = 100%).", "Interest Rate", JOptionPane.QUESTION_MESSAGE);
      if (iOut == null) {//Checking if user cancels
        return;
      }
      try {
        i = Double.parseDouble(iOut);
        break; //Break on successful parse
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Please enter a valid number!", "Error!", JOptionPane.ERROR_MESSAGE);
      }
    }

    Double n = 0.0; //Number of periods
    while (true) { //Loop for number of periods
      String nOut = JOptionPane.showInputDialog(null, "Please enter number of compounding periods per year.", "Compounding periods", JOptionPane.QUESTION_MESSAGE);
      if (nOut == null) {//Checking if user cancels
        return;
      }
      try {
        n = Double.parseDouble(nOut);
        break; //Break on successful parse
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Please enter a valid number!", "Error!", JOptionPane.ERROR_MESSAGE);
      }
    }

    Double y = 0.0; //Years
    while (true) { // Loop for year
      String yOut = JOptionPane.showInputDialog(null, "Please enter the number of years to calculate.", "Number of Years", JOptionPane.QUESTION_MESSAGE);
      if (yOut == null) {//Checking if user cancels
        return;
      }
      try {
        y = Double.parseDouble(yOut);
        break; //Break on successful parse
      } catch (Exception e) {
        //Catch parse error
        JOptionPane.showMessageDialog(null, "Please enter a valid number!", "Error!", JOptionPane.ERROR_MESSAGE);
      }
    }

    Double FV = 0.0; //Future value
    while (true) {
      String FVOut = JOptionPane.showInputDialog("The future value");
      if (FVOut == null) {//Checking if user cancels
        return;
      }
      try {
        FV = Double.parseDouble(FVOut); //Parse Future Value
        break; //Break on successful parse
      } catch (Exception e) {
        //Catch parse error
        JOptionPane.showMessageDialog(null, "Please enter a valid number!", "Error!", JOptionPane.ERROR_MESSAGE);
      }
    }
    
    /*
                (     1     ) ^ n * y
      PV = FV X ( --------- ) 
                ( 1 + (i/n) )

      PV = present value of the amount
      FV = future value of the amount
      i = interest rate (in percentage terms)
      n = Number of compounding periods per year
      y = Number of years
    */

    Double PV = FV * (1.0/Math.pow((1 + (i/n)), (n * y)));

    //Rounding answer
    Double PVRounded = Math.round(PV*100.0)/100.0;

    JOptionPane.showMessageDialog(null, "Amount needed to start investment: $" + PVRounded, "Investment Result", JOptionPane.INFORMATION_MESSAGE);
  
    List<Integer> dataPoints = new ArrayList<Integer>(); //Data to send to graph
    
    Double highestValue = 0.0;
    for (Integer index = 0; index < y; index++) {
      Double PVOnYear = FV * (1.0/Math.pow((1 + (i/n)), (n * index)));
      if (PVOnYear > highestValue) {
        highestValue = PVOnYear;
      }
      dataPoints.add(index, (int)Math.round(PVOnYear));
    }

    DrawGraph.createAndShowGui(dataPoints, highestValue);
  }
  public static boolean isInvestment = true;
  public static void main(String[] args) {
    //Choose program option
    JFrame frame = new JFrame();
    frame.setSize(300, 400);

    //Encrypt button
    JButton investmentButton = new JButton("Investments");
    investmentButton.addActionListener(new ActionListener(){  
      //On Press
      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {
        isInvestment = true;
      }  
    });
    investmentButton.setBounds(0, 0, 150, 100);
    frame.add(investmentButton);
    JButton savingsButton = new JButton("Savings");
    savingsButton.addActionListener(new ActionListener(){  
      //On Press
      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {
        isInvestment = false;
      }  
    });
    savingsButton.setBounds(150, 0, 150, 100);
    frame.add(savingsButton);

    if (isInvestment) {

    }    
  }
}

class InterestCalculator {

}

class SavingsCalculator {  
  JTextField principleField(JFrame frame) throws Exception {
    JTextField numberOfPaymentField = new JTextField("Number of Payments", 16);
    numberOfPaymentField.setBounds(0, 0, 300, 150);
    frame.add(numberOfPaymentField);
    return numberOfPaymentField;
  }
  JTextField rateField(JFrame frame) throws Exception {
    JTextField rateField = new JTextField("Interest Rate", 16);
    rateField.setBounds(0, 0, 300, 150);
    frame.add(rateField);
    return rateField;
  }
  JTextField numberOfPaymentsField(JFrame frame) throws Exception {
    JTextField numberOfPaymentField = new JTextField("Number of Payments", 16);
    numberOfPaymentField.setBounds(0, 0, 300, 150);
    frame.add(numberOfPaymentField);
    return numberOfPaymentField;
  }

  Double calculateSavingsAmount(Double rate, Double principle, Double numberOfPayments) {
    //Save Money for retierment
    /*Calculation for payment amount per period or a
            ( r(1 + r)^n    )
      A = P (---------------)
            ( (1 + r)^n - 1 )
    */    
    Double ratePowPayment = Math.pow((1 + rate), numberOfPayments);
    Double aTop = rate * ratePowPayment;
    Double aBottom = ratePowPayment - 1 * principle;
    Double a = aTop/aBottom;
    return a;
  }

  void main(JFrame frame) {
    frame.removeAll();
    //Offset label holds current offset
    JLabel principleLable = new JLabel("Principle");
    principleLable.setBounds(0, 250, 300, 20);
    frame.add(principleLable);

    //Message Text box
    JTextField messageTextField = new JTextField("enter the text", 16);
    messageTextField.setBounds(0, 0, 300, 150);
    frame.add(messageTextField);

    numberOfPaymentsField(frame);
    

    JOptionPane.showMessageDialog(null, "The amount per payment is $" + Math.round(a * 100)/100);
  }
}

//Andrew Mainella
//**** I DID NOT WRITE THIS CODE ****
//https://stackoverflow.com/questions/8693342/drawing-a-simple-line-graph-in-java

//Code Modified 18 October 2023

@SuppressWarnings("serial")
class DrawGraph extends JPanel {
  private static final int PREF_W = 800;
  private static final int PREF_H = 650;
  private static final int BORDER_GAP = 20;
  private static final Color GRAPH_COLOR = Color.green;
  private static final Color GRAPH_POINT_COLOR = new Color(150, 50, 50, 180);
  private static final Stroke GRAPH_STROKE = new BasicStroke(3f);
  private static final int GRAPH_POINT_WIDTH = 12;
  private static final int Y_HATCH_CNT = 10;
  private List<Integer> scores;
  private Double MAX_SCORE;

  public DrawGraph(List<Integer> scores, Double MAX_SCORE) {
    this.scores = scores;
    this.MAX_SCORE = MAX_SCORE;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    double xScale = ((double) getWidth() - 2 * BORDER_GAP) / (scores.size() - 1);
    double yScale = ((double) getHeight() - 2 * BORDER_GAP) / (MAX_SCORE - 1);

    List<Point> graphPoints = new ArrayList<Point>();
    for (int i = 0; i < scores.size(); i++) {
      int x1 = (int) (i * xScale + BORDER_GAP);
      int y1 = (int) ((MAX_SCORE - scores.get(i)) * yScale + BORDER_GAP);
      graphPoints.add(new Point(x1, y1));
    }

    // create x and y axes 
    g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, BORDER_GAP, BORDER_GAP);
    g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, getWidth() - BORDER_GAP, getHeight() - BORDER_GAP);

    // create hatch marks for y axis. 
    for (int i = 0; i < Y_HATCH_CNT; i++) {
        int x0 = BORDER_GAP;
        int x1 = GRAPH_POINT_WIDTH + BORDER_GAP;
        int y0 = getHeight() - (((i + 1) * (getHeight() - BORDER_GAP * 2)) / Y_HATCH_CNT + BORDER_GAP);
        int y1 = y0;
        g2.drawLine(x0, y0, x1, y1);
    }

    // and for x axis
    for (int i = 0; i < scores.size() - 1; i++) {
        int x0 = (i + 1) * (getWidth() - BORDER_GAP * 2) / (scores.size() - 1) + BORDER_GAP;
        int x1 = x0;
        int y0 = getHeight() - BORDER_GAP;
        int y1 = y0 - GRAPH_POINT_WIDTH;
        g2.drawLine(x0, y0, x1, y1);
    }

    Stroke oldStroke = g2.getStroke();
    g2.setColor(GRAPH_COLOR);
    g2.setStroke(GRAPH_STROKE);
    for (int i = 0; i < graphPoints.size() - 1; i++) {
      int x1 = graphPoints.get(i).x;
      int y1 = graphPoints.get(i).y;
      int x2 = graphPoints.get(i + 1).x;
      int y2 = graphPoints.get(i + 1).y;
      g2.drawLine(x1, y1, x2, y2);         
    }

    g2.setStroke(oldStroke);      
    g2.setColor(GRAPH_POINT_COLOR);
    for (int i = 0; i < graphPoints.size(); i++) {
      int x = graphPoints.get(i).x - GRAPH_POINT_WIDTH / 2;
      int y = graphPoints.get(i).y - GRAPH_POINT_WIDTH / 2;;
      int ovalW = GRAPH_POINT_WIDTH;
      int ovalH = GRAPH_POINT_WIDTH;
      g2.fillOval(x, y, ovalW, ovalH);
    }
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(PREF_W, PREF_H);
  }

  static void createAndShowGui(List<Integer> scores, Double MAX_SCORE) {
    DrawGraph mainPanel = new DrawGraph(scores, MAX_SCORE);

    JFrame frame = new JFrame("Interest Per Year");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(mainPanel);
    frame.pack();
    frame.setLocationByPlatform(true);
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        List<Integer> scores = new ArrayList<Integer>();
        scores.add(0, 5);
        createAndShowGui(scores, 100.0);
      }
    });
  }
}