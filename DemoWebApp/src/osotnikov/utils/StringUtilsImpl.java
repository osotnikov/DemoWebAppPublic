package osotnikov.utils;

public class StringUtilsImpl implements StringUtils {
	
	/* (non-Javadoc)
	 * @see osotnikov.utils.StringUtils#isEmpty(java.lang.String)
	 */
	@Override
	public boolean isEmpty(String str){
		if(str != null && str.trim() != ""){
			return false;
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see osotnikov.utils.StringUtils#getTrimmedOrEmpty(java.lang.String)
	 */
	@Override
	public String getTrimmedOrEmpty(String str){
		if(str == null){
			return "";
		}else{
			return str.trim();
		}
		
	}
}
