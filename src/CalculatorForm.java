import inputParsing.InputParsing;
import mainMechanism.mainMechanism;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                label1.setText(String.valueOf(mainMechanism.Calculate(InputParsing.getOperationListExpr(InputParsing.removeWhitespaces(textField1.getText())))));
            }
        });
        setVisible(true);
    }
}
