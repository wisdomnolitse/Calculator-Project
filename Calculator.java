/**
 * 
 * @author Wisdom Nolitse
 * An application implementing a basic calculator
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

/*
 * The calculator class
 * Sets up, displays and runs a basic calculator
 */
public class Calculator 
{
	private JFrame frame = new JFrame();//the frame on which the calculator is displayed
	private JPanel buttonPanel = new JPanel(new GridLayout(0,4));//the panel on which the buttons of the calculator are placed
																//the layout of the panel will have n rows and 4 columns
	private JPanel finalPanel = new JPanel(new BorderLayout());//the final panel that willbe added to the frame
	private JButton buttons[] = new JButton[20];//an array of buttons of which each element is going on the buttons panel
	final private int TEXTFIELD_LENGTH = 30;//constant corresponding to the max number of symbols/chars allowed
	private JTextField output = new JTextField(TEXTFIELD_LENGTH);//creates the JTextField of corresponding width
	//creates a String array with the caption of each button as a String
	private String captions[] = {"1","2","3","+","4","5","6","-","7","8","9","/","AC","0","=","*", "DEL", "(", ")", "sqrt("};
	private Double answer;//the final answer to be displayed
	private String expression;//the internal String representation of the String to be displayed
	
	/*
	 * constructor function
	 * displays and runs the calculator code
	 */
	Calculator()
	{
		//both the answer and expression are initialized to null values
		answer = 0.0;
		expression = "";
		//frame.getContentPane();
		//sets the caption and the internal name of each button (which is its
		//position in the array of buttons) as well as the background and
		//foreground color, and adds a ActionEvent handler (a ButtonClass object)
		for (int count = 0; count < 20; count++)
		{
			buttons[count] = new JButton(captions[count]);
			buttons[count].setName(Integer.toString(count));
			buttons[count].addActionListener(new ButtonClicked(output, buttons[count]));
			buttons[count].setBackground(new Color(0,0,0));
			buttons[count].setForeground(new Color(255,255,255));
			//adds the button to the panel
			buttonPanel.add(buttons[count]);
		}
		//adds the button panel to the final panel, along with the text field
		//then adds the final panel to the frame
		output.setEditable(false);
		finalPanel.add(output,BorderLayout.NORTH);
		finalPanel.add(buttonPanel, BorderLayout.CENTER);
		frame.add(finalPanel);
		
		//sets up the frame to be displayed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	/*
	 * A listener class for the ActionEvent produced by the 
	 * calculator's button
	 */
	public class ButtonClicked implements ActionListener
	{
		//a JButton representing the calculator's button that was pressed
		private JButton button;
		//a JTextField corresponding to the calculator's text field
		private JTextField textField;
		/**
		 * A ScriptEngineManager and a ScriptEngine objects
		 * Used to run a JavaScript script to evaluate the math 
		 * expression entered by the user
		 * @see javax.script.ScriptEngineManager
		 * @see javax.script.ScriptEngine
		 */
		ScriptEngineManager manager = new ScriptEngineManager();
	    ScriptEngine engine = manager.getEngineByName("JavaScript");
	    
	    /**
	     * Constructor method for the ButtonClicked class. Initializes its data fields
	     * @param field the JTextField serving as the calculator's text field
	     * @param button the JButton corresponding to one of the calculator's buttons
	     */
		public ButtonClicked(JTextField field, JButton button)
		{
			this.textField = field;
			this.button = button;
		}
		
		/**
		 * A method to perform an action in response to an ActionEvent 
		 * @param e an ActionEvent that was produced
		 */
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			Integer content = Integer.parseInt(button.getName());
			switch(content)
			{
			case 14://the user of the calculator pressed on "="
				try 
				{
					//uses the ScriptEngine to evaluate the mathematical expression
					//by calling method eval. The Object returned is casted to a Double
					//and the answer field of the calculator is set to that value.
					answer = (Double) engine.eval(expression + "*1.0");
				} 
				catch (ScriptException e1) 
				{
					e1.printStackTrace();
				}
				//displays the answer on the text field
				textField.setText(answer.toString());

				break;
			case 12://user pressed on the "AC" button
				//the text field is reset to a blank text field
				//while the internal representation of the input
				//and the final answer are set to null values
				textField.setText("");
				expression = "";
				answer = 0.0;
				break;
			case 16://user pressed on "DEL"
				//gets the content of the text field
				String text = textField.getText();
				//if the String is empty set the text field
				//to a blank one, else remove the last element
				//that was entered by the user
				if (text.isEmpty()) 
				{
					textField.setText(null);
					expression = "";
				}
				else
				{
					textField.setText(text.substring(0, text.length() - 1));
					expression = expression.substring(0, expression.length() - 1);

				}
				break;
			case 19://user pressed on the "sqrt" button
				//appends the caption of the button to the expression displayed, while
				//the internal expression is appended with "Math.sqrt(" for evaluation
				//purposes
				expression = expression + "Math.sqrt(";
				textField.setText(textField.getText() + button.getText());
				break;
			default://user presses on any other button but the previous ones
				//the caption of the button is appended on both the expression
				//displayed and the internal expression used for evaluation
				textField.setText(textField.getText() + button.getText());
				expression = expression + button.getText();
				break;
			}

		}
	};
}
