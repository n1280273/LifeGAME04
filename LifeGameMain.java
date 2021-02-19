import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class LifeGameMain extends JFrame
				implements Runnable, ActionListener, MouseListener {
	int[][] cell    = new int[34][34];
	int[][] newCell = new int[34][34];
	boolean jikkouChuu;
	
	Container cPane;
	JPanel panelUp;
	Life_Panel panelDown;
	JButton btnExec;
	JButton btnNew;
	JButton btnClear;
	
	public static void main(String[] args) {
		new LifeGameMain();
	}

	public LifeGameMain() {
		guiSetting();
		
		Thread thread = new Thread( this );
		thread.start();
	}


	public void guiSetting() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		cPane = getContentPane();
		cPane.setLayout(new BorderLayout());
		
		panelUp = new JPanel();
		btnExec = new JButton("Exec");
		btnExec.addActionListener( this );
		panelUp.add(btnExec);
		btnNew = new JButton("New");
		btnNew.addActionListener( this );
		panelUp.add(btnNew);
		btnClear = new JButton("Clear");
		btnClear.addActionListener( this );
		panelUp.add(btnClear);
		cPane.add(panelUp, BorderLayout.NORTH);

		panelDown = new Life_Panel(cell);
		panelDown.addMouseListener( this );
		cPane.add(panelDown, BorderLayout.CENTER);
		
		this.setSize(440, 490);
		this.setVisible(true);
		
	}

	public void run() {
		while( true ) {
			try {
				Thread.sleep(1000);
				if (jikkouChuu) {
					cellCalc();
					cellDraw();
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
	}

	public void cellCalc() {
		int n;
		
		for (int y = 1; y < 33; ++y) {
			for (int x = 1; x < 33; ++x) {
				n = kosuu(x, y);
				if ( n == 3 )
					newCell[x][y] = 1;
				else if ( n == 2 )
					newCell[x][y] = cell[x][y];
				else
					newCell[x][y] = 0;
			}
		}
		for (int y = 1; y < 33; ++y) {
			for (int x = 1; x < 33; ++x) {
				cell[x][y] = newCell[x][y];
			}
		}
	}
	
	public void cellDraw() {
		panelDown.repaint();
	}
	
	public int kosuu(int x, int y) {
		int n;
		
		n =   cell[x-1][y-1] + cell[x  ][y-1] + cell[x+1][y-1]
		    + cell[x-1][y  ]                  + cell[x+1][y  ]
		    + cell[x-1][y+1] + cell[x  ][y+1] + cell[x+1][y+1];
		return n;
	}


	public void actionPerformed(ActionEvent evt) {
		if ( evt.getSource() == btnExec ) {
			if ( jikkouChuu ) {
				btnExec.setText("Exec");
				jikkouChuu = false;
			}
			else {
				btnExec.setText("Stop");
				jikkouChuu = true;
			}
		}
		else if ( evt.getSource() == btnNew ) {
			for (int y = 1; y < 33; ++y) {
				for (int x = 1; x < 33; ++x) {
					if ( Math.random() < 0.3 ) {
						cell[x][y] = 1;
					}
					else {
						cell[x][y] = 0;
					}
				}
			}
			panelDown.repaint();
		}
		else if ( evt.getSource() == btnClear ) {
			for (int y = 1; y < 33; ++y) {
				for (int x = 1; x < 33; ++x) {
					cell[x][y] = 0;
				}
			}
			panelDown.repaint();
		}
	}

	public void mouseEntered(MouseEvent evt) {}
	public void mouseExited(MouseEvent evt) {}
	public void mouseClicked(MouseEvent evt) {}
	public void mousePressed(MouseEvent evt) {}
	public void mouseReleased(MouseEvent evt) {
		int x, y;
		
		x = (evt.getX() - 4) / 12;
		y = (evt.getY() - 4) / 12;
		if ( cell[x][y] == 0 ) {
			cell[x][y] = 1;
		}
		else {
			cell[x][y] = 0;
		}
		panelDown.repaint();
	}
}

class Life_Panel extends JPanel {
	public int[][] cell;

	public Life_Panel(int[][] cell) {
		this.cell = cell;
	}
	
	public void paint(Graphics g) {
		g.clearRect(0,0,600,600);
		for (int y = 1; y < 33; ++y) {
			for (int x = 1; x < 33; ++x) {
				if (cell[x][y] == 1)
					g.drawString("●", x * 12 + 10, y * 12 + 10);
				else
					g.drawString("・", x * 12 + 10, y * 12 + 10);
			}
		}
	}
}