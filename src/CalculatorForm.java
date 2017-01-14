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
import wolframAlpha.WolframConnection;

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
                    label1.setText("Result: " + String.valueOf(df.format(expr.getValue())));

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
