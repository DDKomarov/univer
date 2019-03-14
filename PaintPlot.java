import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import static java.lang.Math.pow;

public class PaintPlot {

	public static float[] createArrayX(float x0, float xn, float h) {
		int n = (int) ((xn - x0) / h + 1);
		float[] xa = new float[n];
		float x = x0;
		for (int i = 0; i < n; i++, x += h) {
			xa[i] = x;
		}
		return xa;
	}
	public static float[] createArrayYForNewt(float[] xa) {
		float[] ya = new float[xa.length];
		for (int i = 0; i < ya.length; i++) {
			ya[i] = (float) (-xa[i] + Math.sin(xa[i]));
			//ya[i] = (float) (Math.exp(xa[i])*Math.cos(xa[i]) - 1);
		}
		return ya;
	}

	public static void createAxes(Graphics g) {
		g.drawLine(0, 300, 800, 300);
		g.drawLine(400, 600, 400, 0);
		int tick = 0;
		while (tick < 800) {
			if (tick % 100 == 0) {
				g.drawLine(tick, 296, tick, 304);
				g.drawLine(396, tick, 404, tick);
			} else {
				g.drawLine(tick, 298, tick, 302);
				g.drawLine(398, tick, 402, tick);
			}
			tick += 10;
		}
	}

	public static void paintPlot(Graphics g) {
//		Scanner in = new Scanner(System.in);// preparing to drawing
//		System.out.println("enter range of x");
//		System.out.print("x0: ");
//		float x0 = in.nextFloat();
//		System.out.print('\n' + "xn: ");
//		float xn = in.nextFloat();
//		System.out.print('\n' + "h: ");
//		float h = in.nextFloat();
//
//		in.close();

		float x0 = 0;
		float xn = 1;
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JTextField field = new JTextField(5);
		JButton button = new JButton("give me graph, S U K A!");

		frame.setBounds(0, 0, 800, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);

		panel.add(field);
		panel.add(button);

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				float k;
				k = Float.parseFloat(field.getText());
				float[] xArray = createArrayX(x0, xn, (float) (1/ pow(2,k)));// creating points

				//float[] yArray = createArrayY(xArray);//for interpolation
				float[] yArray = createArrayYForNewt(xArray);//for Newton method

				int[] intXArray = new int[xArray.length];// changing points for drawing
				int[] intYArray = new int[yArray.length];

				for (int i = 0; i < intXArray.length; i++) {
					intXArray[i] = (int) (xArray[i] * 100 + 400);
					intYArray[i] = (int) (yArray[i] * -1 * 100 + 300);
				}

				int nPoint = xArray.length;// number of point

				createAxes(g);

				Color oldColor = g.getColor();// changing line style
				Color newColor = new Color(0, 0, 255);
				g.setColor(newColor);

				g.drawPolyline(intXArray, intYArray, nPoint);

				g.setColor(oldColor);
			}
		});

	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setBounds(0, 0, 800, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Graphics g = frame.getGraphics();

		paintPlot(g);
	}

}
