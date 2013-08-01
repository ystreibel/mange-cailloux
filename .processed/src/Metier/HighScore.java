/**
 * @(#) HighScore.java
 */

package Metier;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

public class HighScore{
	private static HighScore singletonInstance;
	
	private String REC_STORE = "HighScoreStore";
	
	private RecordStore rs;
	
	public String getREC_STORE() {
		return REC_STORE;
	}

	public void setREC_STORE(String rec_store) {
		REC_STORE = rec_store;
	}
 
	public RecordStore getRs() {
		return rs;
	}

	public void setRs(RecordStore rs) {
		this.rs = rs;
	}

	protected HighScore(){
		
		try {
			rs = RecordStore.openRecordStore(REC_STORE, true);
			String appt = "10000";
			byte bytes[] = appt.getBytes();
			rs.addRecord(bytes, 0, bytes.length);
			appt = "Yohann";
			byte bytes2[] = appt.getBytes();
			rs.addRecord(bytes2, 0, bytes2.length);
			appt = "07500";
			byte bytes3[] = appt.getBytes();
			rs.addRecord(bytes3, 0, bytes3.length);
			appt = "Johnn Doe";
			byte bytes4[] = appt.getBytes();
			rs.addRecord(bytes4, 0, bytes4.length);
			rs.closeRecordStore();
		} catch (RecordStoreException e1) {
		}
	}

	public static HighScore getSingletonInstance() {
		if (singletonInstance == null) {
			singletonInstance = new HighScore();
		}
		return singletonInstance;
	}
}
