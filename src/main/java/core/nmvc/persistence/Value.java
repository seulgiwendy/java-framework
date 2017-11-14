package core.nmvc.persistence;

public class Value implements KeyValue{
	
	private int columnIndex = 0;
	private Object columnValue = null;

	public Value(Object columnValue, int columnIndex) { 
		this.columnIndex = columnIndex;
		this.columnValue = columnValue;
	}

	@Override
	public Object getKeyObject() {
		return this.columnValue;
	}

	@Override
	public int getColumnIndex() {
		return this.columnIndex;
	}
	
	
	@Override
	public String toString() {
		return String.format("value object = %s order in column = %d", this.columnValue instanceof String ? this.columnValue.toString() : this.columnValue.getClass().getName(), this.columnIndex);
	}
	
	@Override
	public int compareTo(KeyValue o) {
		Value v = o instanceof Value ? (Value) o : null;
		if (v == null) {
			return -1;
		}
		return this.columnIndex - v.getColumnIndex();
	}

}
