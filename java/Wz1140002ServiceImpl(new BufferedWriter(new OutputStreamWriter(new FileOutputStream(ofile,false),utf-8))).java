import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
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

/**
 * マスタファイル作成サービス
 * <dl>
 * <dt>使用条件
 * <dd>SOAPサービスのエンドポイントI/Fの実装を提供
 * </dl>
 */
@WebService(name = "wz1140002Service", serviceName = "wz1140002Service", portName = "wz1140002ServicePort", targetNamespace = "http://wz11.ajinomoto.com/wz1140002/", endpointInterface = "com.ajinomoto.wz11.wz1140.service.app.service.Wz1140002Service")
@HandlerChain(file = "/handler-chain-config.xml")
public class Wz1140002ServiceImpl implements Wz1140002Service {

	/** ロガー */
	private static final Logger LOG = LoggingUtils
			.getLogger(Wz1140002ServiceImpl.class);
	/** アプリケーションI/F */
	private final Wz1140002Application application = JNDIUtils
			.createRemoteProxy(Wz1140002Application.class);

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
		String strPath = Wz11PropertiesUtils.get(CsvMetaConstant.FILE_WZ1140002_PATH);
		if(null == strPath || strPath.isEmpty()){
			LOG.error("file path can't be determined");
			return;
		}
		BufferedWriter bw = null;
		try {
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
			Wz11tm024Filter wz11tm024Filter = new Wz11tm024Filter("0100", "", "");
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
			try{
				if(null != bw)
					bw.close();
			}catch(IOException e){
				LOG.error(e.getLocalizedMessage(), e);
			}
		}
	}
	
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
