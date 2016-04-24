import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

import com.ajinomoto.framework.enterprise.shared.JNDIUtils;
import com.ajinomoto.framework.standard.shared.LoggingUtils;
import com.ajinomoto.wz11.wz1139.service.app.Wz1139001Application;
import com.ajinomoto.wz11.wz1139.service.app.service.Wz1139001Service;
import com.ajinomoto.wz11.wz1139.service.domain.CsvMetaConstant;
import com.ajinomoto.wz11.wz1139.service.domain.MsgConstant;
import com.ajinomoto.wz11.wz1139.service.domain.common.LogOutput;
import com.ajinomoto.wz11.wz1139.service.domain.common.ReturnObject;
import com.ajinomoto.wz11.wz1139.service.domain.common.app.Wz11EntityUtils;
import com.ajinomoto.wz11.wz1139.service.domain.common.app.Wz11MsgUtils;
import com.ajinomoto.wz11.wz1139.service.domain.common.app.Wz11PropertiesUtils;
import com.ajinomoto.wz11.wz1139.service.domain.wz1139001.Wz11tt001;

import java.io.FileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;

/**
 * 経費精算データ書き込み処理サービス
 * <dl>
 * <dt>使用条件
 * <dd>SOAPサービスのエンドポイントI/Fの実装を提供すること。
 * </dl>
 * 
 * @author jun_chen
 */
@WebService(name = "wz1139001Service", serviceName = "wz1139001Service", portName = "wz1139001ServicePort", targetNamespace = "http://wz11.ajinomoto.com/wz1139001/", endpointInterface = "com.ajinomoto.wz11.wz1139.service.app.service.Wz1139001Service")
@HandlerChain(file = "/handler-chain-config.xml")
public class Wz1139001ServiceImpl implements Wz1139001Service {

	/** ロガー */
	private static final Logger LOG = LoggingUtils
			.getLogger(Wz1139001ServiceImpl.class);
	/** アプリケーションI/F */
	private final Wz1139001Application application = JNDIUtils
			.createRemoteProxy(Wz1139001Application.class);
	/** ファイル読み込み結果 */
	private Collection<Wz11tt001> wz11tt001s = new ArrayList<Wz11tt001>();
	/** ジョブ返却値 */
	private ReturnObject returnObj = new ReturnObject();
	/** ログ出力情報 */
	private LogOutput logOutput = new LogOutput();
	/** 法人コード */
	private static String HOUJIN_CODE = "0100";
	/** 取込済みフラグ */
	private static String TORIKOMI_FLG = "0";
	/** 更新者 */
	private static String UPDATE_USER = "wz11adm01";

	/** コンストラクタ */
	public Wz1139001ServiceImpl() {
	}

	/** {@inheritDoc} */
	@Override
	@WebMethod(operationName = "save")
	@WebResult
	public Collection<Wz11tt001> save() {
		final String processId = "WZ1139001";
		// 　初期化
		wz11tt001s = new ArrayList<Wz11tt001>();
		returnObj = new ReturnObject();
		logOutput = new LogOutput();
		LoggingUtils.putEjbMDC(processId);
		LOG.info(Wz11MsgUtils.get(MsgConstant.I0001, processId));
		try {
			// SAEファイルを読み込み
			if (!setFileToEntity()) {
//				logOutput.printAll(); // finally will do this!
//				LOG.info(Wz11MsgUtils.get(MsgConstant.I0002, processId)); // finally will do this!
				returnObj.setReturnCode(ReturnObject.ERROR_CODE);
				return wz11tt001s; // will go to finally, so better just break?
			}
			// DB更新処理
			int count = application.save(wz11tt001s);
			// 登録成功件数
			logOutput.setInsertCount(count);

			if (count != wz11tt001s.size()) {
				// 失敗件数
				logOutput.setErrorCount(wz11tt001s.size() - count);
				LOG.error(Wz11MsgUtils.get(MsgConstant.I0003, processId));
				returnObj.setReturnCode(ReturnObject.ERROR_CODE);
			} else
				returnObj.setReturnCode(ReturnObject.NORMAL_CODE);
		} catch (FileNotFoundException e) {
			LOG.error(Wz11MsgUtils.get(MsgConstant.I0003, processId));
			returnObj.setReturnCode(ReturnObject.ERROR_CODE);
		} catch (IOException e) {
			LOG.error(Wz11MsgUtils.get(MsgConstant.I0003, processId));
			returnObj.setReturnCode(ReturnObject.ERROR_CODE);
		} catch (IllegalAccessException e) {
			LOG.error(Wz11MsgUtils.get(MsgConstant.I0003, processId));
			returnObj.setReturnCode(ReturnObject.ERROR_CODE);
		} catch (InvocationTargetException e) {
			LOG.error(Wz11MsgUtils.get(MsgConstant.I0003, processId));
			returnObj.setReturnCode(ReturnObject.ERROR_CODE);
		} catch (final EJBException e) {
			LOG.error(Wz11MsgUtils.get(MsgConstant.I0003, processId));
			returnObj.setReturnCode(ReturnObject.ERROR_CODE);
		} catch (final Throwable e) {
			LOG.error(Wz11MsgUtils.get(MsgConstant.I0003, processId));
			returnObj.setReturnCode(ReturnObject.ERROR_CODE);
		} finally {
			String tmpStr = new String();
			LOG.debug(tmpStr
					.format("(\"\".equals(new String())):%s,(new String().length() == 0):%s,new String().isEmpty():%s\n",
							("".equals(tmpStr)), (tmpStr.length() == 0),
							tmpStr.isEmpty()));
			logOutput.printAll();
			if(ReturnObject.NORMAL_CODE.equals(returnObj.getReturnCode())){
				LOG.info(Wz11MsgUtils.get(MsgConstant.I0002, processId));
			} else{
				LOG.error(Wz11MsgUtils.get(MsgConstant.I0003, processId));
			}
			return wz11tt001s;
		}
	}

	/**
	 * ファイル読み込み処理
	 * 
	 * @return true 正常、false 非常
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private boolean setFileToEntity() throws FileNotFoundException,
			IOException, IllegalAccessException, InvocationTargetException {
		String ln = null;
		boolean firstLnFlg = true;
		boolean endFlg = false;

		int fileCnt = 0;

		// ファイル読み込み操作
		BufferedReader br = null;
		File folder = new File(
				Wz11PropertiesUtils.get(CsvMetaConstant.FILE_WZ1139001_PATH));
		// 　ファイル情報セット
		logOutput.setInputFilePath(Wz11PropertiesUtils
				.get(CsvMetaConstant.FILE_WZ1139001_PATH));
		// フオルダ存在チェック
		if (!folder.isDirectory()) {
			returnObj.setMsgCode(MsgConstant.E0002);
			returnObj.setMsg(Wz11MsgUtils.get(MsgConstant.E0002,
					Wz11PropertiesUtils
							.get(CsvMetaConstant.FILE_WZ1139001_PATH)));
			return endFlg;
		}

		// ファイル存在チェック
		//java.io.FilenameFilter
		File[] infiles = folder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.contains(Wz11PropertiesUtils
						.get(CsvMetaConstant.FILE_WZ1139001_NAME));
			}
		});

		//org.apache.commons.io.filefilter.WildcardFileFilter
		FileFilter ff = new WildcardFileFilter("*"
				+ Wz11PropertiesUtils.get(CsvMetaConstant.FILE_WZ1139001_NAME)
				+ "*");
		File[] tmpfls = folder.listFiles(ff);
		LOG.debug(new String()
				.format("filtered via java.io.FilenameFilter %d file(s), via org.apache.commons.io.filefilter.WildcardFileFilter %d file(s)\n",
						infiles.length, tmpfls.length));
		
		if (null != infiles) {
			fileCnt = infiles.length;
			// 　ファイル情報セット
			logOutput.setInputFileCount(fileCnt);
		} else {
			returnObj.setMsgCode(MsgConstant.E0003);
			returnObj.setMsg(Wz11MsgUtils.get(MsgConstant.E0003,
					Wz11PropertiesUtils
							.get(CsvMetaConstant.FILE_WZ1139001_PATH)));
			return endFlg;
		}

		Wz11tt001 wz11tt001 = new Wz11tt001();
		List<Map<String, String>> nmList = Wz11EntityUtils
				.getFieldsInfo(wz11tt001);

		for (File tempFile : infiles) {
			firstLnFlg = true;

			// ファイル読み込み操作
			br = new BufferedReader(new FileReader(tempFile));
			while ((ln = br.readLine()) != null) {
				// 一行目は処理しない
				if (firstLnFlg) {
					firstLnFlg = false;
					continue;
				}

				String[] cols = lineSplit(ln);

				// DBマッピングを作成する
				wz11tt001 = new Wz11tt001();
				for (int i = 0; i < cols.length; i++) {
					// マッピング処理
					Wz11EntityUtils.setEntityProp(wz11tt001,
							CsvMetaConstant.FILE_WZ1139001_LIST[i], cols[i],
							nmList);

				}

				wz11tt001.setHojinCode(HOUJIN_CODE);
				wz11tt001.setShoriYmd(Calendar.getInstance().getTime());
				wz11tt001.setTorikomiFlg(TORIKOMI_FLG);
				wz11tt001.setUserid(UPDATE_USER);
				wz11tt001.setRnewDay(Calendar.getInstance().getTime());

				// リストに追加
				wz11tt001s.add(wz11tt001);
				endFlg = true;
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// なにもしない
				}
			}
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
		return str == null || str.length() == 0;
	}
}