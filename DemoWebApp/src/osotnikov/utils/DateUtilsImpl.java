package osotnikov.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtilsImpl implements DateUtils{
	
	/* (non-Javadoc)
	 * @see osotnikov.utils.DateUtils#getCurrentDateFormatted()
	 */
	@Override
	public String getCurrentDateFormatted(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
		String date = sdf.format(new Date());
		return date; // 22/10/2016
	}
	
}
