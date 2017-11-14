package core.nmvc.persistence;

public interface KeyValue extends Comparable<KeyValue>{
	
	public Object getKeyObject();
	public int getColumnIndex();

}
