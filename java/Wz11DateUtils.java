import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;

import com.ajinomoto.framework.enterprise.shared.EnterpriseRuntimeException;
import com.ajinomoto.framework.enterprise.shared.JNDIUtils;
import com.ajinomoto.framework.standard.shared.LoggingUtils;
import com.ajinomoto.framework.standard.shared.MessageUtils;
import com.ajinomoto.wz11.wz1139.service.app.Wz11tm007Application;
import com.ajinomoto.wz11.wz1139.service.app.Wz11tm009Application;
import com.ajinomoto.wz11.wz1139.service.domain.MsgConstant;
import com.ajinomoto.wz11.wz1139.service.domain.wz11tm007.Wz11tm007;
import com.ajinomoto.wz11.wz1139.service.domain.wz11tm007.Wz11tm007Filter;
import com.ajinomoto.wz11.wz1139.service.domain.wz11tm009.Wz11tm009;
import com.ajinomoto.wz11.wz1139.service.domain.wz11tm009.Wz11tm009Filter;

/**
 * Bean Validationの情報定数定義
 * <dl>
 * <dt>使用条件
 * <dd>Bean Validation情報の定数を定義する
 * </dl>
 * 
 * @author ﾁﾝﾊﾞﾄ
 */
public final class Wz11DateUtils extends DateUtils {
	/** フォーマット：yyyyMMdd */
	public static final String FORMAT_NONE_YYYYMMDD = "yyyyMMdd";
	/** フォーマット：yyyy/MM/dd */
	public static final String FORMAT_SLASH_YYYYMMDD = "yyyy/MM/dd";
	/** フォーマット：yy/MM/dd */
	public static final String FORMAT_SLASH_YYMMDD = "yy/MM/dd";
	/** フォーマット：yyyy-MM-dd */
	public static final String FORMAT_MID_YYYYMMDD = "yyyy-MM-dd";
	/** フォーマット：yyyy */
	public static final String FORMAT_NONE_YYYY = "yyyy";
	/** フォーマット：MM */
	public static final String FORMAT_NONE_MM = "MM";
	/** フォーマット：dd*/
	public static final String FORMAT_NONE_DD = "dd";
	/** フォーマット：hh:mm:dd*/
	public static final String FORMAT_COLON_HHMMSS = "HH:mm:ss";
	/** フォーマット：yyyyMMddhhmmdd*/
	public static final String FORMAT_NONE_DATETIME = "yyyyMMddHHmmss";
	/** フォーマット：yyyy-MM-dd HH:mm:ss.S*/
	public static final String SQL_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
	/** 営業日アプリケーションI/F */
	private Wz11tm007Application application007;
	/** 営業日カレンダマスタ */
	private Wz11tm007 wz11tm007;
	private ArrayList<Wz11tm007> wz11tm007_arrylst;
	
	/** 銀行営業日カレンダマスタ */
	private Wz11tm009 wz11tm009;
	private ArrayList<Wz11tm009> wz11tm009_arrylst;
	/** 銀行営業日アプリケーションI/F */
	private Wz11tm009Application application009;
	/** ロガー */
	private static final Logger LOG = LoggingUtils
			.getLogger(Wz11EntityUtils.class);
	/**
	 * コンストラクタ
	 */
	public Wz11DateUtils() {
		super();
		wz11tm007_arrylst = new ArrayList<Wz11tm007>();
		wz11tm009_arrylst = new ArrayList<Wz11tm009>();
	}

	/**
	 * 日付フォーマット関数
	 * 
	 * @param date
	 *            編集対象
	 * @param outFormart
	  *			    出力フォーマット
	 * @return String
	 * @throws ParseException
	 *             変換失敗する場合、エラーで落とす
	 * 
	 * @Sample formatDate("99991231", Wz11DateUtils.FORMAT_SLASH_YYYYMMDD));
	 */
	public static String formatDate(String date, String outFormart)
			throws EnterpriseRuntimeException {
		// DATE形式チェック
		if (!isDateFormat(date) || null == outFormart || outFormart.isEmpty()) {
			LOG.error(MessageUtils.get(MsgConstant.WARING_DATE_FORMAT_FAILED));
			return "";
		}
		// 入力文字を日付型へ変換
		SimpleDateFormat sdf;
		if(8 < date.length())
			sdf= new SimpleDateFormat(SQL_DATETIME_FORMAT);
		else
			sdf = new SimpleDateFormat(FORMAT_NONE_YYYYMMDD);
		Date date_out = new Date();
		// 変換後の日付型をフォーマット
		try {
			date_out = sdf.parse(date);
		} catch (ParseException e) {
			throw new EnterpriseRuntimeException(
					MessageUtils.get(MsgConstant.WARING_DATE_FORMAT_FAILED));
		}
		sdf.applyPattern(outFormart);
		return sdf.format(date_out);
	}

/**
	 * 日付フォーマット関数
	 * @param date
	 * @param outFormart
	 * @return
	 */
	public static String formatDate(Date date, String outFormart)
	{
		// DATE形式チェック
		if (null == date || null == outFormart || outFormart.isEmpty()) {
			LOG.error(MessageUtils.get(MsgConstant.WARING_DATE_FORMAT_FAILED));
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(outFormart);
		return sdf.format(date);
	}

	/**
	 * 営業日カレンダマスタ初期化,当年
	 * @param hojinCode 法人コード
	 * @param date 計算日基準日
	 */
	public void initWz11tm007(String hojinCode,String date){
		// （営業日カレンダマスタ）
		if(!isDateFormat(date)){
			LOG.error(MessageUtils.get(MsgConstant.WARING_DATE_FORMAT_FAILED));
			return;
		}
		if(null == hojinCode || hojinCode.isEmpty())
			hojinCode = "0100";
		application007 = JNDIUtils.createRemoteProxy(Wz11tm007Application.class);
		Wz11tm007Filter wz11tm007Filter = new Wz11tm007Filter(hojinCode,formatDate(date,FORMAT_NONE_YYYY),formatDate(date,FORMAT_NONE_MM));
		wz11tm007 = application007.find(wz11tm007Filter);
	}
	
	/**
	 * 営業日カレンダマスタ初期化,前後３年
	 * @param hojinCode 法人コード
	 * @param date 計算日基準日
	 */
	public void initWz11tm007s(String hojinCode,String date){
		// （営業日カレンダマスタ）
		if(!isDateFormat(date)){
			LOG.error(MessageUtils.get(MsgConstant.WARING_DATE_FORMAT_FAILED));
			return;
		}
		if(null == hojinCode || hojinCode.isEmpty())
			hojinCode = "0100";
		application007 = JNDIUtils.createRemoteProxy(Wz11tm007Application.class);
		Wz11tm007Filter wz11tm007Filter = new Wz11tm007Filter(hojinCode,formatDate(date,FORMAT_NONE_YYYY),"");
		wz11tm007_arrylst = (ArrayList<Wz11tm007>) application007.findMany(wz11tm007Filter);
	}
	
	/**
	 * 銀行営業日カレンダマスタ初期化,当年
	 * @param hojinCode 法人コード
	 * @param date 計算日基準日
	 */
	public void initWz11tm009(String hojinCode,String date){
		// （銀行営業日カレンダマスタ）
		if(!isDateFormat(date)){
			LOG.error(MessageUtils.get(MsgConstant.WARING_DATE_FORMAT_FAILED));
			return;
		}
		if(null == hojinCode || hojinCode.isEmpty())
			hojinCode = "0100";
		application009 = JNDIUtils.createRemoteProxy(Wz11tm009Application.class);
		Wz11tm009Filter wz11tm009Filter = new Wz11tm009Filter(hojinCode,formatDate(date,FORMAT_NONE_YYYY),formatDate(date,FORMAT_NONE_MM));
		wz11tm009 = application009.find(wz11tm009Filter);
	}
	
	/**
	 * 銀行営業日カレンダマスタ初期化,前後３年
	 * @param hojinCode 法人コード
	 * @param date 計算日基準日
	 */
	public void initWz11tm009s(String hojinCode,String date){
		// （銀行営業日カレンダマスタ）
		if(!isDateFormat(date)){
			LOG.error(MessageUtils.get(MsgConstant.WARING_DATE_FORMAT_FAILED));
			return;
		}
		if(null == hojinCode || hojinCode.isEmpty())
			hojinCode = "0100";
		application009 = JNDIUtils.createRemoteProxy(Wz11tm009Application.class);
		Wz11tm009Filter wz11tm009Filter = new Wz11tm009Filter(hojinCode,formatDate(date,FORMAT_NONE_YYYY),"");
		wz11tm009_arrylst = (ArrayList<Wz11tm009>) application009.findMany(wz11tm009Filter);
	}
	
	/**
	 * 営業日計算
	 * @param date
	 * @param count
	 * @param countFlg
	 * @return
	 * @throws EnterpriseRuntimeException
	 */
	public String calBsDay(String date, int count, String countFlg)
			throws EnterpriseRuntimeException {
		//　業務処理：営業日計算
		if(!isDateFormat(date)){
			LOG.error(MessageUtils.get(MsgConstant.WARING_DATE_FORMAT_FAILED));
			return "";
		}
		if(0 >= count || null == countFlg || countFlg.isEmpty())
			return "";
		initWz11tm007s("0100",date);
		if(null == wz11tm007_arrylst || wz11tm007_arrylst.isEmpty())
			return "";
		int incdec = 1;
		if("-" == countFlg || "ー" == countFlg) //半全角ハイフォン
			incdec = -1;
		String strYear = formatDate(date,FORMAT_NONE_YYYY);
		String strMonth = formatDate(date,FORMAT_NONE_MM);
		String strDay = formatDate(date,FORMAT_NONE_DD);
		Calendar cal = Calendar.getInstance();
		String strValue = "";
		boolean hasHit = false;
		while(0 < count){
			for(Wz11tm007 wz11tm007_tmp : wz11tm007_arrylst){
				StringBuilder sba = new StringBuilder("Wz11tm007::getYear()=[");
				sba.append(wz11tm007_tmp.getYear());
				sba.append("]\n");
				LOG.debug(sba.toString());
				try{
					cal.set(Integer.parseInt(strYear),Integer.parseInt(strMonth)-1,Integer.parseInt(strDay));
					cal.add(Calendar.DAY_OF_MONTH,incdec);
					strYear = String.valueOf(cal.get(Calendar.YEAR));
					strMonth = String.format("%1$02d",cal.get(Calendar.MONTH)+1);
					strDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
					if(wz11tm007_tmp.getYear().equals(strYear)){
						Method mthd = wz11tm007_tmp.getClass().getMethod("getMon"+strMonth);
						strValue = mthd.invoke(wz11tm007_tmp).toString();
						
						StringBuilder sbb = new StringBuilder("strValue=[");
						sbb.append(strValue);
						sbb.append("]_strValue.charAt(Integer.parseInt(strDay)-1)=[");
						sbb.append(strValue.charAt(Integer.parseInt(strDay)-1));
						sbb.append("]\n");
						LOG.debug(sbb.toString());
						
						if('1' == strValue.charAt(Integer.parseInt(strDay)-1))
							--count;
						hasHit = true;
					}
				}catch(NoSuchMethodException e){
					throw new EnterpriseRuntimeException(
							MessageUtils.get(MsgConstant.WARING_REFLECTION_ERROR));
				}catch(SecurityException e){
					throw new EnterpriseRuntimeException(
							MessageUtils.get(MsgConstant.WARING_REFLECTION_ERROR));
				}catch(IllegalAccessException e){
					throw new EnterpriseRuntimeException(
							MessageUtils.get(MsgConstant.WARING_REFLECTION_ERROR));
				}catch(IllegalArgumentException e){
					throw new EnterpriseRuntimeException(
							MessageUtils.get(MsgConstant.WARING_REFLECTION_ERROR));
				}catch(InvocationTargetException e){
					throw new EnterpriseRuntimeException(
							MessageUtils.get(MsgConstant.WARING_REFLECTION_ERROR));
				}
			}
			if(!hasHit)
				break;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(Wz11DateUtils.FORMAT_NONE_YYYYMMDD);
		return sdf.format(cal.getTime());
	}
	
	/**
	 * 銀行営業日計算
	 * @param date
	 * @param count
	 * @param countFlg
	 * @return
	 * @throws EnterpriseRuntimeException
	 */
	public String calBkBsDay(String date, int count, String countFlg)
			throws EnterpriseRuntimeException {
		//　業務処理：営業日計算
		if(!isDateFormat(date)){
			LOG.error(MessageUtils.get(MsgConstant.WARING_DATE_FORMAT_FAILED));
			return "";
		}
		if(0 >= count || null == countFlg || countFlg.isEmpty())
			return "";
		// DBアクセス（銀行営業日カレンダマスタ）
		initWz11tm009s("0100",date);
		if(null == wz11tm009_arrylst || wz11tm009_arrylst.isEmpty())
			return "";
		int incdec = 1;
		if("-" == countFlg || "ー" == countFlg) //半全角ハイフォン
			incdec = -1;
		String strYear = formatDate(date,FORMAT_NONE_YYYY);
		String strMonth = formatDate(date,FORMAT_NONE_MM);
		String strDay = formatDate(date,FORMAT_NONE_DD);
		Calendar cal = Calendar.getInstance();
		String strValue = "";
		boolean hasHit = false;
		while(0 < count){
			for(Wz11tm009 wz11tm009_tmp : wz11tm009_arrylst){
				StringBuilder sba = new StringBuilder("Wz11tm009::getYear()=[");
				sba.append(wz11tm009_tmp.getYear());
				sba.append("]\n");
				LOG.debug(sba.toString());
				try{
					cal.set(Integer.parseInt(strYear),Integer.parseInt(strMonth)-1,Integer.parseInt(strDay));
					cal.add(Calendar.DAY_OF_MONTH,incdec);
					strYear = String.valueOf(cal.get(Calendar.YEAR));
					strMonth = String.format("%1$02d",cal.get(Calendar.MONTH)+1);
					strDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
					if(wz11tm009_tmp.getYear().equals(strYear)){
						Method mthd = wz11tm009_tmp.getClass().getMethod("getMon"+strMonth);
						strValue = mthd.invoke(wz11tm009_tmp).toString();
						
						StringBuilder sbb = new StringBuilder("strValue=[");
						sbb.append(strValue);
						sbb.append("]_strValue.charAt(Integer.parseInt(strDay)-1)=[");
						sbb.append(strValue.charAt(Integer.parseInt(strDay)-1));
						sbb.append("]\n");
						LOG.debug(sbb.toString());
						
						if('1' == strValue.charAt(Integer.parseInt(strDay)-1))
							--count;
						hasHit = true;
					}
				}catch(NoSuchMethodException e){
					throw new EnterpriseRuntimeException(
							MessageUtils.get(MsgConstant.WARING_REFLECTION_ERROR));
				}catch(SecurityException e){
					throw new EnterpriseRuntimeException(
							MessageUtils.get(MsgConstant.WARING_REFLECTION_ERROR));
				}catch(IllegalAccessException e){
					throw new EnterpriseRuntimeException(
							MessageUtils.get(MsgConstant.WARING_REFLECTION_ERROR));
				}catch(IllegalArgumentException e){
					throw new EnterpriseRuntimeException(
							MessageUtils.get(MsgConstant.WARING_REFLECTION_ERROR));
				}catch(InvocationTargetException e){
					throw new EnterpriseRuntimeException(
							MessageUtils.get(MsgConstant.WARING_REFLECTION_ERROR));
				}
			}
			if(!hasHit)
				break;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_NONE_YYYYMMDD);
		return sdf.format(cal.getTime());
	}
	
	/**
	 * 営業日判断
	 * @param date
	 * @return
	 * @throws EnterpriseRuntimeException
	 */
	public boolean isBsDay(String date)
			throws EnterpriseRuntimeException {
		boolean ret = false;
		if(!isDateFormat(date)){
			LOG.error(MessageUtils.get(MsgConstant.WARING_DATE_FORMAT_FAILED));
			return false;
		}
		//　業務処理：営業日計算
		initWz11tm007("0100",date);
		if(null == wz11tm007)
			return false;
		String strMonth = formatDate(date,FORMAT_NONE_MM);
		String strDay = formatDate(date,FORMAT_NONE_DD);
		String strValue = "";
		try{
			Method mthd = wz11tm007.getClass().getMethod("getMon"+strMonth);
			strValue = mthd.invoke(wz11tm007).toString();
		}catch(NoSuchMethodException e){
			throw new EnterpriseRuntimeException(
					MessageUtils.get(MsgConstant.WARING_REFLECTION_ERROR));
		}catch(SecurityException e){
			throw new EnterpriseRuntimeException(
					MessageUtils.get(MsgConstant.WARING_REFLECTION_ERROR));
		}catch(IllegalAccessException e){
			throw new EnterpriseRuntimeException(
					MessageUtils.get(MsgConstant.WARING_REFLECTION_ERROR));
		}catch(IllegalArgumentException e){
			throw new EnterpriseRuntimeException(
					MessageUtils.get(MsgConstant.WARING_REFLECTION_ERROR));
		}catch(InvocationTargetException e){
			throw new EnterpriseRuntimeException(
					MessageUtils.get(MsgConstant.WARING_REFLECTION_ERROR));
		}
		if('1' == strValue.charAt(Integer.parseInt(strDay)-1))
			ret = true;
		return ret;
	}
	
	/**
	 * 銀行営業日判断
	 * @param date
	 * @return
	 * @throws EnterpriseRuntimeException
	 */
	public boolean isBkBsDay(String date)
			throws EnterpriseRuntimeException {
		boolean ret = false;
		if(!isDateFormat(date)){
			LOG.error(MessageUtils.get(MsgConstant.WARING_DATE_FORMAT_FAILED));
			return false;
		}
		initWz11tm009("0100",date);
		if(null == wz11tm009)
			return false;
		String strMonth = formatDate(date,FORMAT_NONE_MM);
		String strDay = formatDate(date,FORMAT_NONE_DD);
		// DBアクセス（銀行営業日カレンダマスタ）
		String strValue = "";
		try{
			Method mthd = wz11tm009.getClass().getMethod("getMon"+strMonth);
			strValue = mthd.invoke(wz11tm009).toString();
		}catch(NoSuchMethodException e){
			throw new EnterpriseRuntimeException(
					MessageUtils.get(MsgConstant.WARING_REFLECTION_ERROR));
		}catch(SecurityException e){
			throw new EnterpriseRuntimeException(
					MessageUtils.get(MsgConstant.WARING_REFLECTION_ERROR));
		}catch(IllegalAccessException e){
			throw new EnterpriseRuntimeException(
					MessageUtils.get(MsgConstant.WARING_REFLECTION_ERROR));
		}catch(IllegalArgumentException e){
			throw new EnterpriseRuntimeException(
					MessageUtils.get(MsgConstant.WARING_REFLECTION_ERROR));
		}catch(InvocationTargetException e){
			throw new EnterpriseRuntimeException(
					MessageUtils.get(MsgConstant.WARING_REFLECTION_ERROR));
		}
		//　業務処理：銀行営業日計算
		if('1' == strValue.charAt(Integer.parseInt(strDay)-1))
			ret = true;
		return ret;
	}
	
	/**
	 * 月末取得
	 * @param date
	 * @return
	 * @throws EnterpriseRuntimeException
	 */
	public String calLastDay(String date)
			throws EnterpriseRuntimeException {
		//　業務処理：営業日計算
		if(!isDateFormat(date)){
			LOG.error(MessageUtils.get(MsgConstant.WARING_DATE_FORMAT_FAILED));
			return "";
		}
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_NONE_YYYYMMDD);
		try{
			cal.setTime(sdf.parse(date));
		}catch(ParseException e) {
			throw new EnterpriseRuntimeException(
					MessageUtils.get(MsgConstant.WARING_DATE_FORMAT_FAILED));
		}
		cal.add(Calendar.MONTH,1);
		cal.set(Calendar.DAY_OF_MONTH,1);
		cal.add(Calendar.DATE,-1);
		return sdf.format(cal.getTime());
	}
	
	/**
	 * 月末取得
	 * @param date
	 * @return
	 * @throws EnterpriseRuntimeException
	 */
	public String calLastDay(Date date)
			throws EnterpriseRuntimeException {
		if(null == date){
			LOG.error(MessageUtils.get(MsgConstant.WARING_DATE_FORMAT_FAILED));
			return "";
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH,1);
		cal.set(Calendar.DAY_OF_MONTH,1);
		cal.add(Calendar.DATE,-1);
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_NONE_YYYYMMDD);
		return sdf.format(cal.getTime());
	}
	
	/**
	 * DATE形式チェック
	 * 
	 * @param date
	 *            判断対象 入力形式 （YYYYMMDD) 入力範囲 (00010101～99991231)
	 * @return 判断結果
	 */
	public static boolean isDateFormat(String date) {
		// 入力文字が変換可能な文字をチェックする
		if (null == date || 8 != date.length())
			return false;
		long lnDate = Long.parseLong(date);
		// 入力文字が変換範囲内であることをチェックする
		if (lnDate < 10101 || lnDate > 99991231)
			return false;
		return true;
	}
}
