import inputParsing.InputParsing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    protected CalculatorForm() {
        super("Calculator");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Expression expr = new Expression(InputParsing.prepareString(textField1.getText()));
                label1.setText("Result: " + String.valueOf(expr.getValue()));

                //wolfram
                //label1.setText(WolframConnection.queryWolfram(textField1.getText()));
            }
        });
        setVisible(true);
    }
}
