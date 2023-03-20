
public class Posting {
	int dtf ; // document term frequency*/
	int docId ;
	Posting next;
	public Posting(int  dtf1, int docId1 ){
		next = null;
		dtf = dtf1; // document term frequency*/
		docId = docId1;
	}


	public int getDtf() {
		return dtf;
	}

	public int getDocId() {
		return docId;
	}

	public void setNext(Posting next) {
		this.next = next;
	}

	public void setDtf(int dtf) {
		this.dtf = dtf;
	}
}
