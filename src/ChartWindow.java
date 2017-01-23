import inputParsing.DoublePoint;
import inputParsing.InputParsing;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import functionStructure.Expression;
import java.util.ArrayList;

public class ChartWindow extends ApplicationFrame
{
    private double minChartPoint;
    private double maxChartPoint;
    private Expression expr;
    private int amount;
    public ChartWindow(String applicationTitle , Expression expr, double minChartPoint, double maxChartPoint, int amount)
    {
        super(applicationTitle);
        this.minChartPoint = minChartPoint;
        this.maxChartPoint = maxChartPoint;
        this.expr = expr;
        this.amount = amount;
        JFreeChart lineChart = ChartFactory.createLineChart(
                expr.toString(), //chart name
                "X [" + Double.toString(minChartPoint) + " ; " + Double.toString(maxChartPoint) + "] Liczba punktów: " + amount,"Y",
                createDataset(),
                PlotOrientation.VERTICAL,
                true,true,false);

        ChartPanel chartPanel = new ChartPanel( lineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        setContentPane( chartPanel );
    }

    private DefaultCategoryDataset createDataset( )
    {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        ArrayList<DoublePoint> pointsArr = InputParsing.getChartPoints(expr, amount, minChartPoint, maxChartPoint);


        for (int i = 0; i < amount; i++) {
            dataset.addValue(pointsArr.get(i).getY(), expr.toString(), Double.toString(pointsArr.get(i).getX()));
        }
//        dataset.addValue( 15 , "schools" , "1971" );
//        dataset.addValue( 30 , "schools" , "1980" );
//        dataset.addValue( 60 , "schools" ,  "1990" );
//        dataset.addValue( 120 , "schools" , "2000" );
//        dataset.addValue( 240 , "schools" , "2010" );
//        dataset.addValue( 300 , "schools" , "2014" );
        return dataset;
    }
}