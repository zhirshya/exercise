import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJBException;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.slf4j.Logger;

import com.ajinomoto.framework.enterprise.domain.ConstraintsException;
import com.ajinomoto.framework.enterprise.shared.JNDIUtils;
import com.ajinomoto.framework.standard.shared.LoggingUtils;
import com.ajinomoto.framework.standard.shared.MessageUtils;
import com.ajinomoto.wz11.wz1139.service.app.Wz1139001Application;
import com.ajinomoto.wz11.wz1139.service.app.service.Wz1139001Service;
import com.ajinomoto.wz11.wz1139.service.domain.CsvMetaConstant;
import com.ajinomoto.wz11.wz1139.service.domain.MsgConstant;
import com.ajinomoto.wz11.wz1139.service.domain.common.app.Wz11EntityUtils;
import com.ajinomoto.wz11.wz1139.service.domain.common.app.Wz11PropertiesUtils;
import com.ajinomoto.wz11.wz1139.service.domain.wz1139001.Wz11tt001;
//import org.apache.xmlbeans.impl.piccolo.io.CharsetDecoder;

/**
 * 経費精算データ書き込み処理サービス
 * SOAPサービスのエンドポイントI/Fの実装を提供すること。
 */
@WebService(name = "wz1139001Service", serviceName = "wz1139001Service", portName = "wz1139001ServicePort", targetNamespace = "http://wz11.ajinomoto.com/wz1139001/", endpointInterface = "com.ajinomoto.wz11.wz1139.service.app.service.Wz1139001Service")
@HandlerChain(file = "/handler-chain-config.xml")
public class wz1139001ServiceImpl implements Wz1139001Service {

	/** ロガー */
	private static final Logger LOG = LoggingUtils
			.getLogger(Wz1139001ServiceImpl.class);
	/** アプリケーションI/F */
	private final Wz1139001Application application = JNDIUtils
			.createRemoteProxy(Wz1139001Application.class);
	/** id採番 */
	// private final Wz1139SequenceApplication sequenceApplication = JNDIUtils
	// .createRemoteProxy(Wz1139SequenceApplication.class);
	/** ファイル読み込み結果 */
	private Collection<Wz11tt001> wz11tt001s = new ArrayList<Wz11tt001>();

	/** コンストラクタ */
	public Wz1139001ServiceImpl() {
	}

	/** {@inheritDoc} */
	@Override
	@WebMethod(operationName = "save")
	@WebResult
	public Collection<Wz11tt001> save() {
		final String unified = "WZ1139001";
		wz11tt001s = new ArrayList<Wz11tt001>();
		LoggingUtils.putEjbMDC(unified);
		LOG.debug(MessageUtils.get(MsgConstant.PROCESS_START));
		try {
			// SAEファイルを読み込み
			if (!setFileToEntity()) {
				return wz11tt001s;
			}
			// DB更新処理
			return application.save(wz11tt001s);
		} catch (ConstraintsException e) {
			LOG.error(e.getLocalizedMessage(), e);
		} catch (final EJBException e) {
			LOG.error(e.getLocalizedMessage(), e);
		} catch (final Throwable e) {
			LOG.error(e.getLocalizedMessage(), e);
		} finally {
			LOG.debug(MessageUtils.get(MsgConstant.PROCESS_END));
		}
		return wz11tt001s;
	}

	/**
	 * ファイル読み込み処理
	 * 
	 * @return true 正常、false 非常
	 */
	private boolean setFileToEntity() {
		String ln = null;
		boolean firstLnFlg = true;
		boolean endFlg = false;

		int fileCnt = 0;

		// ファイル読み込み操作
		BufferedReader br = null;
		File folder = new File(
				Wz11PropertiesUtils.get(CsvMetaConstant.FILE_WZ1139001_PATH));
		// フオルダ存在チェック
		if (!folder.isDirectory()) {
			LOG.debug(MessageUtils.get(MsgConstant.ERROR_NOTFOUND_FOLDER));
			return endFlg;
		}

		// ファイル存在チェック
		fileCnt = folder.listFiles().length;
		if (fileCnt == 0) {
			LOG.debug(MessageUtils.get(MsgConstant.ERROR_NOTFOUND_FILE));
			return endFlg;
		}

		Wz11tt001 wz11tt001 = new Wz11tt001();
		List<Map<String, String>> nmList = Wz11EntityUtils
				.getFieldsInfo(wz11tt001);
		int j = 0;

		for (File tempFile : folder.listFiles()) {

			firstLnFlg = true;

			if (!tempFile.getName().contains("extract_CES_SAE_v3_p0006672qg29")) {
				++j;
				continue;
			}
			
			CharsetDetector cd = new CharsetDetector();
			Charset charset = cd.detectCharset(tempFile);
			if(null != charset)
				LOG.debug(tempFile.getName() + "(Charset:" + charset.displayName()+")");
			else
				LOG.debug(tempFile.getName() + "(Charset:null)");
			
			try {
				// ファイル読み込み操作
				br = new BufferedReader(new FileReader(tempFile));
				while ((ln = br.readLine()) != null) {
					// 文字列を区切って、セットする
					LOG.debug(ln);

					// 一行目は処理しない
					if (firstLnFlg) {
						firstLnFlg = false;
						continue;
					}

					String[] cols = lineSplit(ln);

					/*
					 * r02 del // ファイル文字列数チェック if (cols == null ||
					 * CsvMetaConstant.FILE_WZ1139001_LIST.length !=
					 * cols.length) { LOG.warn(MessageUtils
					 * .get(MsgConstant.WARING_FILE_LINE_COL_CHECK)); // endFlg
					 * = false; continue; }
					 */
					// DBマッピングを作成する
					wz11tt001 = new Wz11tt001();
					for (int i = 0; i < cols.length; i++) {
						// マッピング処理
						Wz11EntityUtils.setEntityProp(wz11tt001,
								CsvMetaConstant.FILE_WZ1139001_LIST[i],
								cols[i], nmList);

					}
					// id 採番
					// SequenceFilter sequenceFilter= new
					// SequenceFilter(Wz11tt001.TABLE_ID);
					// wz11tt001.setId(sequenceApplication.count(sequenceFilter));

					wz11tt001.setHojinCode("0100");
					wz11tt001.setShoriYmd(Calendar.getInstance().getTime());
					wz11tt001.setTorikomiFlg("0");
					wz11tt001.setUserid("wz11adm01");
					wz11tt001.setRnewDay(Calendar.getInstance().getTime());

					// リストに追加
					wz11tt001s.add(wz11tt001);
					endFlg = true;
				}
			} catch (FileNotFoundException e) {
				LOG.error(e.getLocalizedMessage(), e);
			} catch (IOException e) {
				LOG.error(e.getLocalizedMessage(), e);
			} catch (IllegalAccessException e) {
				LOG.error(e.getLocalizedMessage(), e);
			} catch (InvocationTargetException e) {
				LOG.error(e.getLocalizedMessage(), e);
			} finally {
				if (br != null)
					try {
						br.close();
					} catch (IOException e) {
						LOG.error(e.getLocalizedMessage(), e);
					}
			}
		}
		if ((fileCnt == j) && (fileCnt != 0)) {
			LOG.debug(MessageUtils.get(MsgConstant.ERROR_NOTFOUND_FILE));
		}
		return endFlg;
	}

	/**
	 * 文字列を分割する
	 * 
	 * @param ln
	 * @return
	 */
	private String[] lineSplit(String ln) {
		if (isNullorSpace(ln)) {
			return null;
		}
		String[] list = ln.split(CsvMetaConstant.SPILT_STRING, -1);
		return list;
	}

	/**
	 * 文字列空白チェック
	 * 
	 * @param str
	 * @return
	 */
	private boolean isNullorSpace(String str) {
		return str == null || str.trim().isEmpty();
	}

	class CharsetDetector {
		public Charset detectCharset(File f) {
			Charset charset = null;
			for (String cs : charsets2bTestd) {
				charset = detectCharset(f, Charset.forName(cs));
				if (null != charset)
					break;
			}
			return charset;
		}

		private Charset detectCharset(File f, Charset cs) {
			try {
				BufferedInputStream bis = new BufferedInputStream(
						new FileInputStream(f));
				CharsetDecoder dcodr = cs.newDecoder();
				dcodr.reset();
				byte[] bfr = new byte[512];
				boolean identified = false;
				while ((-1 != bis.read(bfr)) && !identified)
					identified = identify(bfr, dcodr);
				bis.close();
				if (identified) {
					LOG.debug("detected Charset:" + cs.displayName());
					return cs;
				} else
					return null;
			} catch (Exception e) {
				LOG.error("CharsetDetector::detectCharset err:"
						+ e.getLocalizedMessage());
				return null;
			}
		}

		private boolean identify(byte[] bytes, CharsetDecoder dcodr) {
			try {
				dcodr.decode(ByteBuffer.wrap(bytes));
			} catch (CharacterCodingException e) {
				LOG.error("CharsetDetector::detectCharset err:"
						+ e.getLocalizedMessage());
				return false;
			}
			return true;
		}
		
		private String[] charsets2bTestd = { "utf-8", "Shift_JIS", "sjis",
				"EUC-JP", "ascii", "ISO-2022-JP", "ISO-8859-1" };
	}

}