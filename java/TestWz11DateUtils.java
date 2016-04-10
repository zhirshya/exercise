/**
 * 
 */
package com.ajinomoto.wz11.wz1139.service.domain.common.app;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.ajinomoto.framework.standard.shared.MessageUtils;
import com.ajinomoto.wz11.wz1139.service.domain.MsgConstant;
import com.ajinomoto.wz11.wz1139.service.domain.wz11tm007.Wz11tm007;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.util.Formatter;
import java.util.Random;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;

import com.ajinomoto.framework.enterprise.shared.EnterpriseRuntimeException;
import com.ajinomoto.framework.enterprise.shared.JNDIUtils;
import com.ajinomoto.framework.standard.shared.LoggingUtils;
import com.ajinomoto.framework.standard.shared.MessageUtils;
import com.ajinomoto.wz11.wz1139.service.app.Wz1139001Application;
import com.ajinomoto.wz11.wz1139.service.app.Wz11tm007Application;
import com.ajinomoto.wz11.wz1139.service.app.Wz11tm009Application;
import com.ajinomoto.wz11.wz1139.service.domain.MsgConstant;
import com.ajinomoto.wz11.wz1139.service.domain.wz11tm007.Wz11tm007;
import com.ajinomoto.wz11.wz1139.service.domain.wz11tm007.Wz11tm007Filter;
import com.ajinomoto.wz11.wz1139.service.domain.wz11tm009.Wz11tm009;
import com.ajinomoto.wz11.wz1139.service.domain.wz11tm009.Wz11tm009Filter;

import java.sql.Timestamp;

/**
 * @author seiso_kanemitsu
 *
 */
public class TestWz11DateUtils {

	private static final Logger LOG = LoggingUtils
			.getLogger(Wz11EntityUtils.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Wz11DateUtils tmp = new Wz11DateUtils();
	/*	String str = tmp.formatDate("2013.4.1", "yyyy.M.d", "MM");
		str = tmp.formatDate("2013.4.1", "yyyy.M.d", "M");
		str = tmp.formatDate("2013.4.1", "yyyy.M.d", "yy");
		str = tmp.formatDate("2013.4.1", "yyyy.M.d", "d");
		str = tmp.formatDate("2013.4.1", "yyyy.M.d", "dd");*/
		String str = "";
		str = Wz11DateUtils.formatDate("20130401", "yyyy");
		str = Wz11DateUtils.formatDate("20130401", "yy");
		str = Wz11DateUtils.formatDate("20130401", "MM");
		str = Wz11DateUtils.formatDate("20130401", "M");
		str = Wz11DateUtils.formatDate("20130401", "d");
		str = Wz11DateUtils.formatDate("20130401", "dd");
//		str = Wz11DateUtils.formatDate("20130401", "d/M'yy"); //err
		str = Wz11DateUtils.formatDate("20130401", "M∓d±yyyy"); // no err
//		str = Wz11DateUtils.formatDate("20130401 1:0:0", Wz11DateUtils.FORMAT_NONE_DATETIME);
		Calendar cal = Calendar.getInstance();
		str = cal.getTime().toString(); //Mon Mar 28 01:55:04 VET 2016
		Timestamp ts_sql = new Timestamp(System.currentTimeMillis()); //2016-03-28 01:53:35.81
		str = ts_sql.toString();
		str = Wz11DateUtils.formatDate(str,Wz11DateUtils.FORMAT_NONE_DATETIME);
		
		
/*		char[] varr = new char[31];
		String strMonth = "";
		int c = 3;
		int year = 2015;
		while(0 < c--){
			Wz11tm007 wz11tm007tmp = new Wz11tm007();
			for(int im = 0; im < 12; ++im){
				for(int id = 0; id < 31; ++id)
					varr[id] = (char)((char)(2*Math.random()) + (char)'0');
				strMonth = String.format("%1$02d",im+1);
				try{
					Method mthd = wz11tm007tmp.getClass().getMethod("setMon"+strMonth,String.class);
					mthd.invoke(wz11tm007tmp,(Object)String.valueOf(varr));
				}catch(NoSuchMethodException e){
					LOG.warn(MessageUtils.get(MsgConstant.WARING_REFLECTION_ERROR));
				}catch(SecurityException e){
					LOG.warn(MessageUtils.get(MsgConstant.WARING_REFLECTION_ERROR));
				}catch(IllegalAccessException e){
					LOG.warn(MessageUtils.get(MsgConstant.WARING_REFLECTION_ERROR));
				}catch(IllegalArgumentException e){
					LOG.warn(MessageUtils.get(MsgConstant.WARING_REFLECTION_ERROR));
				}catch(InvocationTargetException e){
					LOG.warn(MessageUtils.get(MsgConstant.WARING_REFLECTION_ERROR));
				}
			}
			wz11tm007tmp.setHojinCode("0100");
			wz11tm007tmp.setYear(String.valueOf(year++));
			tmp.initWz11tm007(wz11tm007tmp);
			if(0 == c)
				tmp.initWz11tm007a(wz11tm007tmp);
		}
//		String rs = tmp.calBsDay("20170427", 9, "-");
//		rs = tmp.calBsDay("20160326", 7, "+");
		boolean bl = false;
		bl = tmp.isBsDay("20170326");
//		wz11tm009set.add(9a);
//		String strValue = "";
 
 */
	}
}
