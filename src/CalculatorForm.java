import inputParsing.InputParsing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.net.*;
import javax.imageio.*;
import functionStructure.Expression;
import jdk.internal.util.xml.impl.Input;
import wolframAlpha.WolframConnection;
import inputParsing.DoublePoint;

/**
 * Created by Lucroth on 2016-11-03.
 */
public class CalculatorForm extends JFrame {
    private JButton calculateButton;
    private JPanel rootPanel;
    private JTextField textField1;
    private JLabel label1;
    private URL url;
    private BufferedImage img;
    protected CalculatorForm() {
        super("Calculator");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DecimalFormat df = new DecimalFormat("#.#####");
                df.setRoundingMode(RoundingMode.FLOOR);
                try {
                    Expression expr = new Expression(InputParsing.prepareString(textField1.getText()));
                    if(!InputParsing.isEquation(InputParsing.removeIntegrals(expr.toString()))) {
                        label1.setText("Result: " + String.valueOf(df.format(expr.getValue())));
                    } else {
                        //INSERT GRAPH HERE PLEASE!
                        //InputParsing.getChartPoints(expr, 1000, -50, 50); To jest metoda która zwróci Ci punktu które trzeba wrzucić na chart
                        //Tak dla modyfikacji to możesz zmieniac 3 ostatnie parametry pierwszy "1000" to jest liczba punktów jakie ma zwrócić metoda
                        //-50 to punkt początkowy
                        //50 to punkt końcowy
                    }


                } catch (Exception ex) {
                    try {
                        label1.setText("");
                        url = new URL(WolframConnection.queryWolfram(textField1.getText()));
                        img = ImageIO.read(url);
                    } catch (Exception ex1) {
                        //not gonna happen bro
                    }
                    label1.setIcon(new ImageIcon(img));
                }
            }
        });
        setVisible(true);
    }
}
