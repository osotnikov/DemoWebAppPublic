package osotnikov.utils;

public class DictionaryUtilsImpl implements DictionaryUtils{
	
	/* (non-Javadoc)
	 * @see osotnikov.utils.DictionaryUtils#correctText(java.lang.String)
	 */
	@Override
	public String correctText(String str){
		return str.replaceAll("Abaut","About");
	}
	
}
