
public class DictEntry {
	int doc_freq = 0; // number of documents that contain the term
	int term_freq = 0; //number of times the term is mentioned in the collection
	Posting pList = null;

	public void setDoc_freq(int doc_freq) {
		this.doc_freq = doc_freq;
	}

	public int getDoc_freq() {
		return doc_freq;
	}

	public int getTerm_freq() {
		return term_freq;
	}

	public Posting getpList() {
		return pList;
	}

	public void setpList(Posting pList) {
		this.pList = pList;
	}

	public void setTerm_freq(int term_freq) {
		this.term_freq = term_freq;
	}

}

