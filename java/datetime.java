import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;

import framework.enterprise.shared.EnterpriseRuntimeException;
import framework.enterprise.shared.JNDIUtils;
import framework.standard.shared.LoggingUtils;
import framework.standard.shared.MessageUtils;
import service.app.Wz11tm007Application;
import service.app.Wz11tm009Application;
import service.domain.MsgConstant;
import service.domain.wz11tm007.Wz11tm007;
import service.domain.wz11tm007.Wz11tm007Filter;
import service.domain.wz11tm009.Wz11tm009;
import service.domain.wz11tm009.Wz11tm009Filter;


/**
 * Bean Validationの情報定数定義
 * <dl>
 * <dt>使用条件
 * <dd>Bean Validation情報の定数を定義する
 * </dl>
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
	public static final String FORMAT_NONE_dd = "dd";
	/** フォーマット：dd*/
	public static final String FORMAT_COLON_HHMMSS = "hh:mm:dd";
	/** アプリケーションI/F */
	private Wz11tm007Application application007;
	/** 営業日カレンダマスタ */
	private Wz11tm007 wz11tm007;
	private Set<Wz11tm007> wz11tm007_set;
	
	/** 銀行営業日カレンダマスタ */
	private Wz11tm009 wz11tm009;
	private Set<Wz11tm009> wz11tm009_set;
	private Wz11tm009Application application009;
	/** ロガー */
	private static final Logger LOG = LoggingUtils
			.getLogger(Wz11EntityUtils.class);
	/**
	 * コンストラクタ
	 */
	public Wz11DateUtils() {
		super();
		wz11tm007 = null;
		wz11tm009 = null;
		wz11tm007_set = new HashSet<Wz11tm007>();
		wz11tm009_set = new HashSet<Wz11tm009>();
		application009 = null;
	}

	/**
	 * 日付フォーマット関数
	 * 
	 * @param date
	 *            編集対象
	 * @param inFormat
	 *            入力フォーマット
	 * @param outFormart
	 *            　出力フォーマット
	 * @return
	 * @throws ParseException
	 *             変換失敗する場合、エラーで落とす
	 * 
	 * @Sample formatDate("99991231", Wz11DateUtils.FORMAT_SLASH_YYYYMMDD));
	 */
	public static String formatDate(String date, String outFormart)
			throws EnterpriseRuntimeException {
		// DATE形式チェック
		if (!isDateFormat(date) || null == outFormart || outFormart.isEmpty()) {
			throw new EnterpriseRuntimeException(
					MessageUtils.get(MsgConstant.WARING_DATE_FORMAT_FAILED));
		}

		// 入力文字を日付型へ変換
		String out = new String();
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_NONE_YYYYMMDD);
		Date in = new Date();

		// 変換後の日付型をフォーマット
		try {
			in = sdf.parse(date);
		} catch (ParseException e) {
			throw new EnterpriseRuntimeException(
					MessageUtils.get(MsgConstant.WARING_DATE_FORMAT_FAILED));
		}
		sdf.applyPattern(outFormart);
		return sdf.format(in);
	}

/**
	 * 日付フォーマット関数
	 * @param date
	 * @param outFormart
	 * @return
	 * @throws EnterpriseRuntimeException
	 */
	public static String formatDate(Date date, String outFormart)
		throws EnterpriseRuntimeException {
		// DATE形式チェック
		if (!isDateFormat(date.toString()) || null == outFormart || outFormart.isEmpty()) {
			throw new EnterpriseRuntimeException(
					MessageUtils.get(MsgConstant.WARING_DATE_FORMAT_FAILED));
		}
		SimpleDateFormat sdf = new SimpleDateFormat(outFormart);
		return sdf.format(date);
	}

	/**
	 * 営業日カレンダマスタ初期化
	 * @param hojinCode 法人コード
	 * @param date 計算日基準日
	 */
	public void initWz11tm007(String hojinCode,String date){
		// （営業日カレンダマスタ）
		if(null == hojinCode || hojinCode.isEmpty())
			hojinCode = "0100";
		String tempDate = Wz11DateUtils.formatDate(date, Wz11DateUtils.FORMAT_NONE_YYYYMMDD);
		application007 = JNDIUtils.createRemoteProxy(Wz11tm007Application.class);
		Wz11tm007Filter wz11tm007Filter = new Wz11tm007Filter(hojinCode,tempDate.substring(0,4),tempDate.substring(4,6));
//		wz11tm007 = application007.find(wz11tm007Filter);
		wz11tm007_set = (Set<Wz11tm007>) application007.findMany(wz11tm007Filter);
	}
	
	/**
	 * 営業日カレンダマスタ初期化
	 * @param hojinCode 法人コード
	 * @param date 計算日基準日
	 */
	public void initWz11tm007(Wz11tm007 wz11tm007){
		// （営業日カレンダマスタ）
		wz11tm007_set.add(wz11tm007);
	}
	
	/**
	 * 銀行営業日カレンダマスタ初期化
	 * @param hojinCode 法人コード
	 * @param date 計算日基準日
	 */
	public void initWz11tm009(String hojinCode,String date){
		// （銀行営業日カレンダマスタ）
		if(null == hojinCode || hojinCode.isEmpty())
			hojinCode = "0100";
		String tempDate = Wz11DateUtils.formatDate(date, Wz11DateUtils.FORMAT_NONE_YYYYMMDD);
		application009 = JNDIUtils.createRemoteProxy(Wz11tm009Application.class);
		Wz11tm009Filter wz11tm009Filter = new Wz11tm009Filter(hojinCode,tempDate.substring(0,4),tempDate.substring(4,6));
//		wz11tm009 = application009.find(wz11tm009Filter);
		wz11tm009_set = (Set<Wz11tm009>) application009.findMany(wz11tm009Filter);
	}
	
	/**
	 * 銀行営業日カレンダマスタ初期化
	 * @param hojinCode 法人コード
	 * @param date 計算日基準日
	 */
	public void initWz11tm009(Wz11tm009 wz11tm009){
		// （営業日カレンダマスタ）
		wz11tm009_set.add(wz11tm009);
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
//		initWz11tm007("0100",date);
		String tempDate = Wz11DateUtils.formatDate(date, Wz11DateUtils.FORMAT_NONE_YYYYMMDD);
		String strYear = tempDate.substring(0,4);
		String strMonth = tempDate.substring(4,6);
		String strDay = tempDate.substring(6,8);
		int incdec = 1;
		if("-" == countFlg || "ー" == countFlg) //半全角ハイフォン
			incdec = -1;
		String strValue = "";
		Calendar cal = Calendar.getInstance();
		while(count > 0){
			for(Wz11tm007 wz11tm007_tmp : wz11tm007_set){
				if(wz11tm007_tmp.getYear().equals(strYear)){
					try{
						Method mthd = wz11tm007_tmp.getClass().getMethod("getMon"+strMonth);
						strValue = mthd.invoke(wz11tm007_tmp).toString();
						if('1' == strValue.charAt(Integer.parseInt(strDay)-1))
							if(0 == --count)
								break; //or enclose following block inside if(0 < count)  
						cal.set(Integer.parseInt(strYear,10),Integer.parseInt(strMonth)-1,Integer.parseInt(strDay));
						cal.add(Calendar.DAY_OF_MONTH,incdec);
						strYear = String.valueOf(cal.get(Calendar.YEAR));
						strMonth = String.format("%1$02d",cal.get(Calendar.MONTH)+1);
						strDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
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
			}
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
		// DBアクセス（銀行営業日カレンダマスタ）
//		initWz11tm009("0100",date);
		String tempDate = Wz11DateUtils.formatDate(date, Wz11DateUtils.FORMAT_NONE_YYYYMMDD);
		String strYear = tempDate.substring(0,4);
		String strMonth = tempDate.substring(4,6);
		String strDay = tempDate.substring(6,8);
		int incdec = 1;
		if("-" == countFlg || "ー" == countFlg) //半全角ハイフォン
			incdec = -1;
		String strValue = "";
		Calendar cal = Calendar.getInstance();
		while(count > 0){
			for(Wz11tm009 wz11tm009_tmp : wz11tm009_set){
				if(wz11tm009_tmp.getYear().equals(strYear)){
					try{
						Method mthd = wz11tm009_tmp.getClass().getMethod("getMon"+strMonth);
						strValue = mthd.invoke(wz11tm009_tmp).toString();
						if('1' == strValue.charAt(Integer.parseInt(strDay)-1))
							if(0 == --count)
								break; //or enclose following block inside if(0 < count)
						cal.set(Integer.parseInt(strYear,10),Integer.parseInt(strMonth)-1,Integer.parseInt(strDay));
						cal.add(Calendar.DAY_OF_MONTH,incdec);
						strYear = String.valueOf(cal.get(Calendar.YEAR));
						strMonth = String.format("%1$02d",cal.get(Calendar.MONTH)+1);
						strDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
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
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat(Wz11DateUtils.FORMAT_NONE_YYYYMMDD);
		return sdf.format(cal.getTime());
	}
	
	/**
	 * 営業日判断
	 * @param date
	 * @return
	 * @throws EnterpriseRuntimeException
	 */
	public boolean isBsDay(String date)
			/*throws EnterpriseRuntimeException*/ {
		boolean ret = false;
		if(!isDateFormat(date)){
			LOG.warn(MessageUtils.get(MsgConstant.WARING_DATE_FORMAT_FAILED));
			ret = false;
/*			throw new EnterpriseRuntimeException(
					MessageUtils.get(MsgConstant.WARING_DATE_FORMAT_FAILED));*/
		}
		//　業務処理：営業日計算
		String tempDate = Wz11DateUtils.formatDate(date, Wz11DateUtils.FORMAT_NONE_YYYYMMDD);
		String strYear = tempDate.substring(0,4);
		String strMonth = tempDate.substring(4,6);
		String strDay = tempDate.substring(6,8);
		initWz11tm007("0100",date);
		String strValue = "";
		for(Wz11tm007 wz11tm007_tmp : wz11tm007_set){
			if(wz11tm007_tmp.getYear().equals(strYear)){
				try{
					Method mthd = wz11tm007_tmp.getClass().getMethod("getMon"+strMonth);
					strValue = mthd.invoke(wz11tm007_tmp).toString();
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
				if('1' == strValue.charAt(Integer.parseInt(strDay)-1)){
					ret = true;
					break;
				}
			}
		}
		return ret;
	}
	/**
	 * 銀行営業日判断
	 * @param date
	 * @return
	 * @throws EnterpriseRuntimeException
	 */
	public boolean isBkBsDay(String date)
			/*throws EnterpriseRuntimeException*/ {
		boolean ret = false;
		if(!isDateFormat(date)){
			LOG.warn(MessageUtils.get(MsgConstant.WARING_DATE_FORMAT_FAILED));
			ret = false;
		/*	throw new EnterpriseRuntimeException(
					MessageUtils.get(MsgConstant.WARING_DATE_FORMAT_FAILED));*/
		}		
		String tempDate = Wz11DateUtils.formatDate(date, Wz11DateUtils.FORMAT_NONE_YYYYMMDD);
		String strYear = tempDate.substring(0,4);
		String strMonth = tempDate.substring(4,6);
		String strDay = tempDate.substring(6,8);
		// DBアクセス（銀行営業日カレンダマスタ）
		initWz11tm009("0100",date);
		String strValue = "";
		for(Wz11tm009 wz11tm009_tmp : wz11tm009_set){
			if(wz11tm009_tmp.getYear().equals(strYear)){
				try{
					Method mthd = wz11tm009_tmp.getClass().getMethod("getMon"+strMonth);
					strValue = mthd.invoke(wz11tm009_tmp).toString();
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
				//　業務処理：銀行営業日計算
				if('1' == strValue.charAt(Integer.parseInt(strDay)-1)){
					ret = true;
					break;
				}
			}
		}
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
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(Wz11DateUtils.FORMAT_NONE_YYYYMMDD);
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
		if (!isDateFormat(date.toString())) {
			throw new EnterpriseRuntimeException(
					MessageUtils.get(MsgConstant.WARING_DATE_FORMAT_FAILED));
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH,1);
		cal.set(Calendar.DAY_OF_MONTH,1);
		cal.add(Calendar.DATE,-1);
		SimpleDateFormat sdf = new SimpleDateFormat(Wz11DateUtils.FORMAT_NONE_YYYYMMDD);
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
		if (date == null || date.length() != 8) {
			return false;
		}

//		int intDate = Integer.parseInt(date);
		long lnDate = Long.parseLong(date, 10);
		// 入力文字が変換範囲内であることをチェックする
//		if (intDate < 10101 || intDate > 99991231) {
		if (lnDate < 10101 || lnDate > 99991231) {
			return false;
		}
		return true;
	}
}
