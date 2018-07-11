
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int x = 0;
		int y = 0;
		while (x < 5) {
			if (y < 5) {
				x = x + 1;
				if (y < 3 ) {
					x = x - 1;
				}
			}
			System.out.print(x + "" + y + "");
			x = x + 1;
		}
	}

}
