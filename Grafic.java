
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.ui.ApplicationFrame;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;

/**
 * Clase que implementa los graficos para
 * 	Ek vs tiempo
 *    Ep vs tiempo
 *    Et vs tiempo
 */
public class Grafic extends ApplicationFrame {
	/**
	 * 
	 */

	/** tiempo a mostrarse en los graficos */
	private double plotWindowTime;
    
	/** El numero de graficos a generar. En este caso 3. */
    public static final int SUBPLOT_COUNT = 3;
    
    /** The datasets. */
    private XYSeriesCollection[] datasets;
    
    /** Ultimo valor agregado al dataset */
    private double[] lastValue = new double[SUBPLOT_COUNT];


/**
 * Constructor de Grafic
 * @param title Titulo del grafico
 * @param fondo Jpanel donde el insertado el grafico
 * @param plotWindowTime Tiempo a mostrarse.
 */
    public Grafic(final String title, JPanel fondo, double plotWindowTime) {
    	  super(title);
          this.plotWindowTime=plotWindowTime;
          final CombinedDomainXYPlot plot1 = new CombinedDomainXYPlot(new NumberAxis("Time"));
          final CombinedDomainXYPlot plot2 = new CombinedDomainXYPlot(new NumberAxis("Time"));
          this.datasets = new XYSeriesCollection[SUBPLOT_COUNT];
          final XYSeries series1 = new XYSeries("Energia Cinetica",false);
          final XYSeries series2 = new XYSeries("Energia Potencial",false);
          final XYSeries series3 = new XYSeries("Energia Total",false);
          this.datasets[0] = new XYSeriesCollection(series1);
          this.datasets[1] = new XYSeriesCollection(series2);
          this.datasets[2] = new XYSeriesCollection(series3);
          String eje = "Energia Cinetica";
          for (int i = 0; i < SUBPLOT_COUNT-1; i++) {
              this.lastValue[i] = 100.0;
              if(i==1)eje = "Energia Potencial";
              final NumberAxis rangeAxis = new NumberAxis(eje);
              rangeAxis.setAutoRangeIncludesZero(false);
              final XYPlot subplot = new XYPlot(
                      this.datasets[i], null, rangeAxis, new StandardXYItemRenderer()
              );
              subplot.setBackgroundPaint(Color.lightGray);
              subplot.setDomainGridlinePaint(Color.white);
              subplot.setRangeGridlinePaint(Color.white);
              subplot.getRenderer().setSeriesPaint(0, Color.BLUE);
              if(i==0)plot1.add(subplot);
              else plot2.add(subplot);
          }               
              
          final JFreeChart chart1 = new JFreeChart("Grafico Energia Kinec vs Tiempo", plot1);
          chart1.setBorderPaint(Color.black);
          chart1.setBorderVisible(true);
          chart1.setBackgroundPaint(Color.white);
          
          plot1.setBackgroundPaint(Color.lightGray);
          plot1.setDomainGridlinePaint(Color.white);
          plot1.setRangeGridlinePaint(Color.white);
          final ValueAxis axis = plot1.getDomainAxis();
          axis.setAutoRange(true);
          axis.setFixedAutoRange(plotWindowTime);  // 30 seconds

          final JFreeChart chart2 = new JFreeChart("Grafico Energia Pot vs Tiempo", plot2);
          chart2.setBorderPaint(Color.black);
          chart2.setBorderVisible(true);
          chart2.setBackgroundPaint(Color.white);
          plot2.setBackgroundPaint(Color.lightGray);
          plot2.setDomainGridlinePaint(Color.white);
          plot2.setRangeGridlinePaint(Color.white);
          final ValueAxis axis2 = plot2.getDomainAxis();
          axis2.setAutoRange(true);
          axis2.setFixedAutoRange(plotWindowTime);  // 30 seconds
        
          JFreeChart chart3 = ChartFactory.createXYLineChart("Grafico Energia Mec v/s Tiempo",
          	      "Tiempo", // Leyenda Eje X
          	      "Energia Mec", // Leyenda Eje Y
          	      this.datasets[2],
          	      PlotOrientation.VERTICAL,
          	      false,
          	      false,
          	      false
          	      );
          XYPlot plot3 = chart3.getXYPlot();
          chart3.setBorderPaint(Color.black);
          chart3.setBorderVisible(true);
          plot3.getRenderer().setSeriesPaint(0, Color.BLUE);
    
          final ValueAxis axis3 = plot3.getDomainAxis();
          axis3.setAutoRange(true);
          final ValueAxis ayis3 = plot3.getRangeAxis();
          //ayis3.setInverted(true);          

          final ChartPanel chartPanel1 = new ChartPanel(chart1);
          final ChartPanel chartPanel2 = new ChartPanel(chart2);
          final ChartPanel chartPanel3 = new ChartPanel(chart3);

          fondo.setLayout(new BoxLayout(fondo, BoxLayout.PAGE_AXIS));

          fondo.add(chartPanel2);
          fondo.add(chartPanel1);
          fondo.add(chartPanel3);
                  
          chartPanel1.setPreferredSize(new java.awt.Dimension(500, 190)); //500,380
          chartPanel1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
          chartPanel2.setPreferredSize(new java.awt.Dimension(500, 190));//500,270
          chartPanel2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));   
          chartPanel3.setPreferredSize(new java.awt.Dimension(500, 190));
          chartPanel3.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); 
          setContentPane(fondo);
          
      }
      
    /**
     * Se Encarga de agregar nuevos puntos a los graficos
     * @param x Posicion x de la bola
     * @param y Posicion y de la bola
     * @param t Tiempo de simulacion
     * @param samplingt Tiempo sampling del simulador
     */
      public void add(double x, double y, double t, float samplingt){
    	  if(this.datasets[2].getSeries(0).getItemCount()>=(int)(plotWindowTime/samplingt))//30 seg/sampletime(0.08)
    	  this.datasets[2].getSeries(0).remove(0);	
      this.datasets[0].getSeries(0).add(t,x);	
      this.datasets[1].getSeries(0).add(t,y);
      this.datasets[2].getSeries(0).add(t,(y+x)); 	
      }
      
      /**
       * Se encarga de limpiar los datos actuales de los graficos.
       */
      public void borraGrafic() { 
    	  this.datasets[0].getSeries(0).delete(0,this.datasets[2].getSeries(0).getItemCount()-1);
    	  this.datasets[1].getSeries(0).delete(0,this.datasets[2].getSeries(0).getItemCount()-1);
    	  this.datasets[2].getSeries(0).delete(0,this.datasets[2].getSeries(0).getItemCount()-1);
          
      }
      
      
}
