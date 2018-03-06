package signalFlowgraph;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JComponent;

public class Draw extends JComponent {
	int nodeNumber = 0;
	ArrayList<Integer> fromNode;
	ArrayList<Integer> toNode;
	ArrayList<Double> gainArray;

	public Draw(int numberOfNode, ArrayList<Integer> inputNode,
			ArrayList<Integer> outputNode, ArrayList<Double> gain) {
		fromNode = inputNode;
		toNode = outputNode;
		gainArray = gain;
		nodeNumber = numberOfNode;
	}

	public void paint(Graphics g) {
		Draw d = new Draw(nodeNumber, fromNode, toNode, gainArray);
		Graphics2D g2 = (Graphics2D) g;
		Shape drawEllipse = null;
		int x = 10;
		for (int i = 0; i < nodeNumber; i++) {
			drawEllipse = new Ellipse2D.Float(x, 250, 40, 40);
			g2.setColor(Color.CYAN);
			g2.draw(drawEllipse);
			g2.fill(drawEllipse);
			Font font = new Font("Serif", Font.PLAIN, 25);
			g2.setFont(font);
			g2.setColor(Color.BLACK);
			g2.drawString(String.valueOf(i + 1), x + 10, 280);
			x = x + 1000 / nodeNumber;
		}
		Shape drawLine = null;
		Shape drawArc;
		Shape drawCircle;
		int y = 50;
		int z = 0;
		int w = 0;
		int[] xPoints = new int[3];
		int[] yPoints = new int[3];
		for (int i = 0; i < fromNode.size(); i++) {
			if (Math.abs(fromNode.get(i) - toNode.get(i)) == (1)) {
				y = 50;
				if (fromNode.get(i) < toNode.get(i)) {
					drawArc = new Arc2D.Double(
							30 + (1000 / nodeNumber) * (fromNode.get(i) - 1),
							100,
							(30 + (1000 / nodeNumber) * (toNode.get(i) - 1))
									- (30 + (1000 / nodeNumber)
											* (fromNode.get(i) - 1)),
							Math.abs(250 - 100) * 2, 0, 180, Arc2D.OPEN);

					xPoints = new int[3];
					yPoints = new int[3];
					xPoints[0] = (30
							+ (1000 / nodeNumber) * (fromNode.get(i) - 1))
							+ ((30 + (1000 / nodeNumber) * (toNode.get(i) - 1))
									- (30 + (1000 / nodeNumber)
											* (fromNode.get(i) - 1)))
									/ 2;
					xPoints[1] = xPoints[2] = (30
							+ (1000 / nodeNumber) * (fromNode.get(i) - 1))
							+ (((30 + (1000 / nodeNumber) * (toNode.get(i) - 1))
									- (30 + (1000 / nodeNumber)
											* (fromNode.get(i) - 1)))
									/ 2)
							- 5;
					yPoints[0] = 100;
					yPoints[1] = 90;
					yPoints[2] = 110;
					d.draw(g2, drawArc, Color.MAGENTA, 90, 30, i);
					g2.fillPolygon(xPoints, yPoints, 3);
				} else {
					drawLine = new Line2D.Float(
							y + ((1000) / nodeNumber) * (toNode.get(i) - 1),
							270,
							y + ((1000) / nodeNumber) * (fromNode.get(i) - 1)
									- 40,
							270);
					xPoints = new int[3];
					yPoints = new int[3];
					xPoints[0] = ((y
							+ (1000 / nodeNumber) * (fromNode.get(i) - 1))
							+ (y + (1000 / nodeNumber) * (toNode.get(i) - 1)
									- 40))
							/ 2;
					xPoints[1] = xPoints[2] = (((y
							+ (1000 / nodeNumber) * (fromNode.get(i) - 1))
							+ (y + (1000 / nodeNumber) * (toNode.get(i) - 1)
									- 40))
							/ 2) + 5;
					yPoints[0] = 270;
					yPoints[1] = 260;
					yPoints[2] = 280;
					d.draw(g2, drawLine, Color.RED, 250, 50, i);
					g2.fillPolygon(xPoints, yPoints, 3);
				}

			}

			else if (Math.abs(fromNode.get(i) - toNode.get(i)) > (1)) {
				if (fromNode.get(i) < toNode.get(i)) {
					drawArc = new Arc2D.Double(
							30 + (1000 / nodeNumber) * (fromNode.get(i) - 1),
							100 + z * 10,
							(30 + (1000 / nodeNumber) * (toNode.get(i) - 1))
									- (30 + (1000 / nodeNumber)
											* (fromNode.get(i) - 1)),
							Math.abs(250 - 100 - z * 10) * 2, 0, 180,
							Arc2D.OPEN);
//					System.out.println(drawArc.getBounds2D());
					xPoints = new int[3];
					yPoints = new int[3];
					xPoints[0] = (30
							+ (1000 / nodeNumber) * (fromNode.get(i) - 1))
							+ ((30 + (1000 / nodeNumber) * (toNode.get(i) - 1))
									- (30 + (1000 / nodeNumber)
											* (fromNode.get(i) - 1)))
									/ 2;
					xPoints[1] = xPoints[2] = (30
							+ (1000 / nodeNumber) * (fromNode.get(i) - 1))
							+ (((30 + (1000 / nodeNumber) * (toNode.get(i) - 1))
									- (30 + (1000 / nodeNumber)
											* (fromNode.get(i) - 1)))
									/ 2)
							- 5;
					yPoints[0] = 100 + z * 10;
					yPoints[1] = 90 + z * 10;
					yPoints[2] = 110 + z * 10;

					d.draw(g2, drawArc, Color.MAGENTA, 100 - z , 30, i);
					z = z + 1;
					g2.fillPolygon(xPoints, yPoints, 3);
				} else {
					drawArc = new Arc2D.Double(
							30 + (1000 / nodeNumber) * (toNode.get(i) - 1),
							(100) + w * 10,
							(30 + (1000 / nodeNumber) * (fromNode.get(i) - 1))
									- (30 + (1000 / nodeNumber)
											* (toNode.get(i) - 1)),
							Math.abs(290 - 100 - w * 10) * 2, 180, 180,
							Arc2D.OPEN);
//					System.out.println(drawArc.getBounds2D());
					xPoints = new int[3];
					yPoints = new int[3];
					xPoints[0] = (30
							+ (1000 / nodeNumber) * (toNode.get(i) - 1))
							+ (((30 + (1000 / nodeNumber)
									* (fromNode.get(i) - 1))
									- (30 + (1000 / nodeNumber)
											* (toNode.get(i) - 1)))
									/ 2);
					xPoints[1] = xPoints[2] = (30
							+ (1000 / nodeNumber) * (toNode.get(i) - 1))
							+ (((30 + (1000 / nodeNumber)
									* (fromNode.get(i) - 1))
									- (30 + (1000 / nodeNumber)
											* (toNode.get(i) - 1)))
									/ 2)
							+ 5;
					yPoints[0] = 290 + 190 - w * 10;
					yPoints[1] = 290 + 190 - 10 - w * 10;
					yPoints[2] = 290 + 190 + 10 - w * 10;
					w = w + 1;
					d.draw(g2, drawArc, Color.MAGENTA,  290 + 190 - w ,
							30, i);
					g2.fillPolygon(xPoints, yPoints, 3);
				}

			} else if (Math.abs(fromNode.get(i) - toNode.get(i)) == 0) {
				y = 50;
				drawCircle = new Ellipse2D.Float(
						y + (1000 / nodeNumber) * (fromNode.get(i) - 1) - 20,
						200, 60, 60);
				xPoints = new int[3];
				yPoints = new int[3];
				xPoints[0] = ((y + (1000 / nodeNumber) * (fromNode.get(i) - 1))
						+ (y + (1000 / nodeNumber) * (toNode.get(i) - 1) - 40)
						+ 70) / 2;
				xPoints[1] = xPoints[2] = (((y
						+ (1000 / nodeNumber) * (fromNode.get(i) - 1))
						+ (y + (1000 / nodeNumber) * (toNode.get(i) - 1) - 40)
						+ 70) / 2) + 5;
				yPoints[0] = 200;
				yPoints[1] = 190;
				yPoints[2] = 210;
				d.draw(g2, drawCircle, Color.BLUE, 250, 50, i);
				g2.fillPolygon(xPoints, yPoints, 3);
			}

		}
	}

	public void draw(Graphics2D g2, Shape shape, Color color, int x, int y,
			int i) {
		g2.setColor(color);
		g2.draw(shape);
		Font font = new Font("Serif", Font.PLAIN, 16);
		g2.setFont(font);
		g2.setColor(color);
		g2.drawString(String.valueOf(gainArray.get(i)),
				((y + (1000 / nodeNumber) * (fromNode.get(i) - 1))
						+ (y + (1000 / nodeNumber) * (toNode.get(i) - 1) - 40))
						/ 2,
				x);

	}
}
