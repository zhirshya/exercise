import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;

import framework.enterprise.shared.EnterpriseRuntimeException;
import framework.standard.shared.LoggingUtils;
import framework.standard.shared.MessageUtils;
import service.domain.MsgConstant;

/**
 * Bean Validationの情報定数定義
 * <dl>
 * <dt>使用条件
 * <dd>Bean Validation情報の定数を定義する
 * </dl>
 */
public final class Wz11EntityUtils extends BeanUtils {

	/** ロガー */
	private static final Logger LOG = LoggingUtils
			.getLogger(Wz11EntityUtils.class);

	/**
	 * コンストラクタ
	 */
	private Wz11EntityUtils() {
	}

	/**
	 * 指定Entityの項目情報を取得する
	 * 
	 * @param obj
	 * @return
	 * @throws EnterpriseRuntimeException
	 */
	public static List<Map<String, String>> getFieldsInfo(Object obj)
			throws EnterpriseRuntimeException {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if (obj == null)
			return list;
		Field[] fields = obj.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Map<String, String> m = new HashMap<String, String>();
			m.put(fields[i].getName(), fields[i].getType().toString());
			list.add(m);
		}
		return list;
	}

	/**
	 * Entityの指定クラムに値を設定する
	 * 
	 * @param obj 対象
	 * @param colNm　カラム名
	 * @param value　設定値
	 * @param nmList 対象カラムリスト
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void setEntityProp(Object obj, String colNm, String value,
			List<Map<String, String>> nmList) throws IllegalAccessException,
			InvocationTargetException {
		if (colNm == null || colNm.length() == 0 || nmList == null
				|| nmList.size() == 0)
			return;

		for (Map<String, String> map : nmList) {
			if (map.get(colNm) != null) {
				BeanUtils.setProperty(obj, colNm,
						newInstance(map.get(colNm), value));
				break;
			}
		}
	}

	/**
	 * Entityの指定クラムに値を設定する
	 * 
	 * @param obj 対象Entity
	 * @param colNm　カラム名
	 * @return 取得値
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static Object getEntityProp(Object obj, String colNm,
			List<Map<String, String>> nmList)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {

		Object result = null;
		if (colNm == null || colNm.length() == 0 || nmList == null
				|| nmList.size() == 0)
			return result;
		for (Map<String, String> map : nmList) {
			if (map.get(colNm) != null) {
				result = newInstance(map.get(colNm), BeanUtils.getProperty(obj, colNm));
				break;
			}
		}	
		return result;
	}

	/**
	 * クラス定義より、実例化する
	 * 
	 * @param classNm
	 * @param value
	 * @return
	 */
	@SuppressWarnings({ "deprecation" })
	private static Object newInstance(String classNm, String value) {
		Object obj = new Object();
		if (java.util.Date.class.toString().equals(classNm)) {
			obj = new java.util.Date(value);
			return obj;
		}
		if (java.lang.String.class.toString().equals(classNm)) {
			obj = new java.lang.String(value);
			return obj;
		}
		if (java.lang.Long.class.toString().equals(classNm)) {
			obj = new java.lang.Long(value);
			return obj;
		}
		LOG.warn(MessageUtils.get(MsgConstant.WARING_FORMAT_ERROR));
		return obj;

	}
}
