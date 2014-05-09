package worms.model;

import java.util.Map;

import worms.gui.game.IActionHandler;
import worms.model.programs.ProgramParser;


public class Program {

		public Program(Statement s, Map<String,Type> g, IActionHandler handler){
			actionHandler = handler;
			globals = g;
			statement = s;
		}
		
		private IActionHandler actionHandler;
		private Map<String,Type> globals;
		private Statement statement;
	

}
