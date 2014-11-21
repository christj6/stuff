public class Tuple {
		private int docNo;
		private int tf;
 
		public Tuple(int docNo, int tf) {
			this.docNo = docNo;
			this.tf = tf;
		}

		public int getFile()
		{
			return docNo;
		}

		public int getFreq()
		{
			return tf;
		}
	}
