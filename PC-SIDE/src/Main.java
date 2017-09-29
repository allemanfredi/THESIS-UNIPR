
public class Main {
	
	public static InitializeProgram init = null;
	
	public static void main ( String[] args ){
		if ( init == null )
			init = new InitializeProgram();
	}
	
}
