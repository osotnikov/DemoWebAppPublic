package osotnikov.utils;

public class MathUtilsImpl implements MathUtils {
	
	/* (non-Javadoc)
	 * @see osotnikov.utils.MathUtils#computeFibonacci(int)
	 */
	@Override
	public int computeFibonacci(int n)  {
	    if(n == 0){
	        return 0;
	    }else if(n == 1){
	    	return 1;
	    }else{
	    	return computeFibonacci(n - 1) + computeFibonacci(n - 2);
	    }
	}
	
	
}
