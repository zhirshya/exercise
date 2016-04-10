import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.EJBException;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.slf4j.Logger;

import com.ajinomoto.framework.enterprise.domain.ConstraintsException;
import com.ajinomoto.framework.enterprise.shared.EnterpriseRuntimeException;
import com.ajinomoto.framework.enterprise.shared.JNDIUtils;
import com.ajinomoto.framework.standard.shared.LoggingUtils;
import com.ajinomoto.framework.standard.shared.MessageUtils;
import com.ajinomoto.wz11.wz1140.service.app.Wz1140002Application;
import com.ajinomoto.wz11.wz1140.service.app.service.Wz1140002Service;
import com.ajinomoto.wz11.wz1140.service.domain.CsvMetaConstant;
import com.ajinomoto.wz11.wz1140.service.domain.MsgConstant;
import com.ajinomoto.wz11.wz1140.service.domain.common.app.Wz11PropertiesUtils;
import com.ajinomoto.wz11.wz1140.service.domain.wz1140001.Wz11tm023;
import com.ajinomoto.wz11.wz1140.service.domain.wz1140002.Wz11tm024;
import com.ajinomoto.wz11.wz1140.service.domain.wz1140002.Wz11tm024Filter;

/**
 * 品目グループマスタファイル作成サービス
 * <dl>
 * <dt>使用条件
 * <dd>SOAPサービスのエンドポイントI/Fの実装を提供すること。
 * </dl>
 */
@WebService(name = "wz1140002Service", serviceName = "wz1140002Service", portName = "wz1140002ServicePort", targetNamespace = "http://wz11.ajinomoto.com/wz1140002/", endpointInterface = "com.ajinomoto.wz11.wz1140.service.app.service.Wz1140002Service")
@HandlerChain(file = "/handler-chain-config.xml")
public class Wz1140002ServiceImpl implements Wz1140002Service {

	/** ロガー */
	private static final Logger LOG = LoggingUtils
			.getLogger(Wz1140002ServiceImpl.class);
	/** アプリケーションI/F */
	// 品目グループマスタファイル作成データ味の素ビュー
	private final Wz1140002Application application = JNDIUtils
			.createRemoteProxy(Wz1140002Application.class);

	/** コンストラクタ */
	public Wz1140002ServiceImpl() {
	}

	/** {@inheritDoc} */
	@Override
	@WebMethod(operationName = "postgre2txt")
	@WebResult
	public void postgre2txt() {
		final String unified = "WZ1140002";

		LoggingUtils.putEjbMDC(unified);
		LOG.debug(MessageUtils.get(MsgConstant.PROCESS_START));
		// 品目グループマスタファイル作成処理
		String strPath = Wz11PropertiesUtils.get(CsvMetaConstant.FILE_WZ1140002_PATH);
		if(null == strPath || strPath.isEmpty()){
			LOG.error("file path can't be determined");
			return;
		}
		BufferedWriter bw = null;
		try {
			// ファイル存在チェック
			File ofile = new File(strPath);
			if (!isFileWritable(ofile)){
	        	LOG.error("destination file unwritable! unable to proceed, exit now.");
	        	return;
	        }else{
	        	if(ofile.exists() && !ofile.delete()){
	        		LOG.error("delete file failed! unable to proceed, exit now.");
	        		return;
	        	}
	        }
//			bw = new BufferedWriter(new FileWriter(ofile));
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ofile,false),"utf-8"));
	        /** 検索情報指定*/
			// 法人コード // 品目グループ
			Wz11tm024Filter wz11tm024Filter = new Wz11tm024Filter("0100", "", "");
	        // 品目グループマスタファイル作成データ味の素ビューDB取得処理
	     	Collection<Wz11tm024> wz11tm024_list = new ArrayList<Wz11tm024>();
	     	wz11tm024_list = application.findMany(wz11tm024Filter);
			
			if (null != wz11tm024_list && !wz11tm024_list.isEmpty()) {
				String strContent = createFirstLine().toString();
				bw.write(strContent);
				bw.newLine();
				for(Wz11tm024 wz11tm024 : wz11tm024_list) {
					strContent = createMeisaiLine(wz11tm024).toString();
					bw.write(strContent);
					bw.newLine();
				}
			}
		} catch (FileNotFoundException e) {
			LOG.error(e.getLocalizedMessage(), e);
			throw new EnterpriseRuntimeException(e.getLocalizedMessage());
		} catch (IOException e) {
			LOG.error(e.getLocalizedMessage(), e);
			throw new EnterpriseRuntimeException(e.getLocalizedMessage());
		} catch (final EJBException e) {
			LOG.error(e.getLocalizedMessage(), e);
			throw new EnterpriseRuntimeException(e.getLocalizedMessage());
		} catch (final Throwable e) {
			LOG.error(e.getLocalizedMessage(), e);
			throw new EnterpriseRuntimeException(e.getLocalizedMessage());
		} finally {
			// ファイル書き込み処理終了
			try{
				if(null != bw)
					bw.close();
			}catch(IOException e){
				LOG.error(e.getLocalizedMessage(), e);
			}
		}
	}
	
	/**
	 * 首行作成処理
	 * 
	 * @param 
	 * @return
	 */
	private StringBuffer createFirstLine(){
		StringBuffer strBuffer = new StringBuffer();

		/** リスト名*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.WZ1140002_LIST_NAME);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_STRING);
		
		/** リストカテゴリー*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.WZ1140002_LIST_CATEGORY);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_STRING);
		
		/** レベル01コード*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.WZ1140002_LEVEL01_CODE);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_STRING);
		
		/** レベル02コード*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_STRING);
		
		/** レベル03コード*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_STRING);
		
		/** レベル04コード*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_STRING);
		
		/** レベル05コード*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_STRING);
		
		/** レベル06コード*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_STRING);
		
		/** レベル07コード*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_STRING);
		
		/** レベル08コード*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_STRING);
		
		/** レベル09コード*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_STRING);
		
		/** レベル10コード*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_STRING);
		
		/** 値*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_STRING);
		
		/** 開始日*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_STRING);
		
		/** 終了日*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_STRING);
		
		/** リスト項目の削除*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.WZ1140002_LIST_DELETE);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		
		return strBuffer;
	}
	
	/**
	 * 明細行作成処理
	 * 
	 * @param data
	 * @return
	 */
	private StringBuffer createMeisaiLine(Wz11tm024 data){
		StringBuffer strBuffer = new StringBuffer();
		
		/** リスト名*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.WZ1140002_LIST_NAME);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_STRING);
		
		/** リストカテゴリー*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.WZ1140002_LIST_CATEGORY);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_STRING);
		
		/** レベル01コード*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.WZ1140002_LEVEL01_CODE);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_STRING);
		
		/** レベル02コード*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		String strMatkl = data.getMatkl();
		if(null != strMatkl)
			strBuffer.append(strMatkl);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_STRING);
		
		/** レベル03コード*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_STRING);
		
		/** レベル04コード*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_STRING);
		
		/** レベル05コード*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_STRING);
		
		/** レベル06コード*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_STRING);
		
		/** レベル07コード*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_STRING);
		
		/** レベル08コード*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_STRING);
		
		/** レベル09コード*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_STRING);
		
		/** レベル10コード*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_STRING);
		
		/** 値*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		String strDescription_Name = data.getWgbez();
		if(null != strDescription_Name)
			strBuffer.append(strDescription_Name);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_STRING);
		
		/** 開始日*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_STRING);
		
		/** 終了日*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.SPILT_STRING);
		
		/** リスト項目の削除*/
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		strBuffer.append(CsvMetaConstant.WZ1140002_LIST_DELETE);
		strBuffer.append(CsvMetaConstant.SPILT_MARK);
		
		return strBuffer;
	}

	/**
	 * ファイル存在チェック
	 * 
	 * @param file
	 * @return
	 */
	private boolean isFileWritable(File file) {
		if(null == file)
			return false;
		else{
			if(file.exists() && (!file.isFile() || !file.canWrite()))
				return false;
			else 
				return true;
		}
	}
}
