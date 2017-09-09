package osotnikov.utils.vo;

import java.io.Serializable;

public abstract class ObjectWithKey<Key> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public abstract Key getKey();

	public abstract void setKey(Key key);
	
	@Override
	public int hashCode(){
		return getKey().hashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		
		if(obj == null || !(obj instanceof ObjectWithKey<?>)){
			return false;
		}else{
			
			ObjectWithKey<Key> otherObjWithKey;
			try {
				otherObjWithKey = (ObjectWithKey<Key>)obj;
			} catch (ClassCastException e) {
				return false;
			}
			
			return this.getKey().equals(otherObjWithKey.getKey());
		}
		
	}
}
