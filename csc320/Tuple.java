public class Tuple {
		private int fileno;
		private int position;
 
		public Tuple(int fileno, int position) {
			this.fileno = fileno;
			this.position = position;
		}

		public int getFile()
		{
			return fileno;
		}
	}
