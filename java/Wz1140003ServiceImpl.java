import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJBException;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

@WebService(name = "wz1140003Service", serviceName = "wz1140003Service", portName = "wz1140003ServicePort", targetNamespace = "http://wz11.ajinomoto.com/wz1140003/", endpointInterface = "com.ajinomoto.wz11.wz1140.service.app.service.Wz1140003Service")
@HandlerChain(file = "/handler-chain-config.xml")
public class Wz1140003ServiceImpl implements Wz1140003Service {
	/** 法人コード */
	private String houjinCode = Wz11PropertiesUtils.get(CsvMetaConstant.ALL_HOUJIN_CODE);
	/** アプリケーションID */
	private final String processId = "WZ1140003";
	/** ワークと履歴比較時のテーブル*/
	private final String COMPARE_COLUMN = "hojinCode,guid,trxType,firstName,middleName,lastName,empId,loginId,password,emailAddress,localCode,countryCode,countrySubCode,ledgerCode,reimbursementCcd,cashAdvanceAcd,orgUnit1,orgUnit2,orgUnit3,orgUnit4,orgUnit5,orgUnit6,custom1,custom2,custom3,custom4,custom5,custom6,custom7,custom8,custom9,custom10,custom11,custom12,custom13,custom14,custom15,auditGroup,empVendorId,deductinoId,payrollId,payrollCcd,userGroupName,notice1,notice2,notice3,notice4,notice5,notice6,notice7,notice8,notice9,notice10,notice11,notice12,notice13,notice14,help1,help2,expRepApprover,cashAdvanceApprover,travelReqApprover,invoiceAprover,traveler,approver,companyCardAdmin,integrationAdmin,receiptProcessor,requestApprover,integrationAdmin2,desktopAdmin,offlineUser,repConfigAdmin,invoiceUser,invoiceApprover,invoiceVendApprover,auditRequired,biManager,travelReqUser,travelReqManager,expRepApprover2,notice15,retiredField,travelExpUser,taxAdmin,fbtAdmin,cliqbookUser,custom22,travelReqApprover2,futureUse02,futureUse03,futureUse04,futureUse05,futureUse06,futureUse07,futureUse08,futureUse09,futureUse10,futureUse11,futureUse12,futureUse13,futureUse14,futureUse15,futureUse16,futureUse17,futureUse18,futureUse19,futureUse20,futureUse21,futureUse22,futureUse23,futureUse24,futureUse25,futureUse26,futureUse27,futureUse28,futureUse29,futureUse30,futureUse31,futureUse32,futureUse33,futureUse34,futureUse35,futureUse36,futureUse37,futureUse38,futureUse39,futureUse40,futureUse41,futureUse42,futureUse43,futureUse44,futureUse45,futureUse46,futureUse47,futureUse48,futureUse49,futureUse50";
	/** メッセージ引数：getSoshikiCc */
	private static final String METHOD_GETSOSHIKICC = "getSoshikiCc";
	/** メッセージ引数：getTanisoshikiCc */
	private static final String METHOD_GETTANISOSHIKICC = "getTanisoshikiCc";

	/** ログ出力情報 */
	private LogOutput logOutput = new LogOutput();
	/** ジョブ返却値 */
	private ReturnObject returnObj = new ReturnObject();
	/** ロガー */
	private static final Logger LOG = LoggingUtils
			.getLogger(Wz1140003ServiceImpl.class);
	/** アプリケーションI/F */
	private final Wz1140003Application application = JNDIUtils
			.createRemoteProxy(Wz1140003Application.class);
	private final Wz11tm011Application wz11tm011Application = JNDIUtils
			.createRemoteProxy(Wz11tm011Application.class);
	private final Wz11tm012Application wz11tm012Application = JNDIUtils
			.createRemoteProxy(Wz11tm012Application.class);
	private final Wz11tm018Application wz11tm018Application = JNDIUtils
			.createRemoteProxy(Wz11tm018Application.class);
	private final Wz11tm020Application wz11tm020Application = JNDIUtils
			.createRemoteProxy(Wz11tm020Application.class);
	private final Wz11tm021Application wz11tm021Application = JNDIUtils
			.createRemoteProxy(Wz11tm021Application.class);
	private final Wz11tm022Application wz11tm022Application = JNDIUtils
			.createRemoteProxy(Wz11tm022Application.class);
	private Wz11FileMasterUtils fu = new Wz11FileMasterUtils();
	private Collection<Wz11tm019> wz11tm019s;
	private Collection<Wz11tm011> wz11tm011s;
	private Collection<Wz11tm012> wz11tm012s;
	private Wz11ConstantUtils cu = new Wz11ConstantUtils();
	
	/** コンストラクタ */
	public Wz1140003ServiceImpl() {
	}

	/** {@inheritDoc} */
	@Override
	@WebMethod(operationName = "save")
	@WebResult
	public void save() {
		LoggingUtils.putEjbMDC(processId);
		LOG.debug(Wz11MsgUtils.get(MsgConstant.I0001, processId));
		String sysTime = Wz11DateUtils.formatDate(Calendar.getInstance()
				.getTime(), Wz11DateUtils.FORMAT_NONE_DATETIME);
		String filepath = fu.getFilePath(houjinCode, processId, sysTime);
		logOutput = new LogOutput();
		returnObj = new ReturnObject();
		wz11tm011s = new ArrayList<Wz11tm011>();
		wz11tm019s = new ArrayList<Wz11tm019>();
		wz11tm012s = new ArrayList<Wz11tm012>();
		BufferedWriter bw = null;
		try {
			// ファイル名称取得失敗時終了
			if (filepath == null || filepath.isEmpty()) {
				logOutput.setOutputFileCount(0);
				logOutput.setOutputFilePath(filepath);
				returnObj.setReturnCode(ReturnObject.ERROR_CODE);
				return;
			}

			// 従業員マスタワークテーブル削除処理
			Wz11tm011Filter wz11tm011Filter = new Wz11tm011Filter(houjinCode,null,null);
			Collection<Wz11tm011> deleteList = wz11tm011Application.findMany(wz11tm011Filter);
			// レコード全件削除
			for (Wz11tm011 wz11tm011 : deleteList) {
				wz11tm011Application.delete(wz11tm011);
			}
			createWorkDB();
			
			//　登録件数をログに出力する Wz11tm011
			logOutput.setInsertCount(wz11tm011s.size());
			int wz11tm011_insert_count = wz11tm011Application.save(wz11tm011s);
			if (wz11tm011_insert_count != wz11tm011s.size()) {
				// 失敗件数
				logOutput.setErrorCount(wz11tm011s.size() - wz11tm011_insert_count);
				returnObj.setReturnCode(ReturnObject.ERROR_CODE);
			} else {
				returnObj.setReturnCode(ReturnObject.NORMAL_CODE);
			}
			
			// ファイル存在チェック
			File writefile = new File(filepath);
			// 抽出情報セット
			logOutput.setOutputFileCount(1);
			logOutput.setOutputFilePath(filepath);
			if (chkFileExist(writefile)) {
				writefile.delete();
			}
			bw = new BufferedWriter(new FileWriter(writefile));
			// 業務実装
			List<Wz1140003File> outputList = editRecord();
			logOutput.setOutputCount(outputList.size());

			for (Wz1140003File wz1140003File : outputList) {
				bw.write(wz1140003File.toString());
				bw.newLine();
			}
			
			if(null != wz11tm012s && !wz11tm012s.isEmpty()) {
				//　登録件数をログに出力する Wz11tm012
				logOutput.setInsertCount(wz11tm012s.size());
				int insertCount = wz11tm012Application.save(wz11tm012s);
				if (insertCount != wz11tm012s.size()) {
					// 失敗件数
					logOutput.setErrorCount(wz11tm012s.size() - insertCount);
					returnObj.setReturnCode(ReturnObject.ERROR_CODE);
				} else {
					returnObj.setReturnCode(ReturnObject.NORMAL_CODE);
				}
			}
			returnObj.setReturnCode(ReturnObject.NORMAL_CODE);
		} catch (FileNotFoundException e) {
			LOG.debug(e.getLocalizedMessage(), e);
			returnObj.setReturnCode(ReturnObject.ERROR_CODE);
		} catch (IOException e) {
			LOG.debug(e.getLocalizedMessage(), e);
			returnObj.setReturnCode(ReturnObject.ERROR_CODE);
		} catch (final EJBException e) {
			LOG.debug(e.getLocalizedMessage(), e);
			returnObj.setReturnCode(ReturnObject.ERROR_CODE);
		} catch (final Throwable e) {
			LOG.debug(e.getLocalizedMessage(), e);
			returnObj.setReturnCode(ReturnObject.ERROR_CODE);
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
			} catch (IOException e) {
				returnObj.setReturnCode(ReturnObject.ERROR_CODE);
			}
			logOutput.printAll();
			if (ReturnObject.NORMAL_CODE.equals(returnObj.getReturnCode())) {
				LOG.info(Wz11MsgUtils.get(MsgConstant.I0002, processId));
			} else {
				LOG.error(Wz11MsgUtils.get(MsgConstant.I0003, processId));
			}
			clearMemory();
		}
	}

	/**
	 * ファイル存在チェック
	 * 
	 * @param file
	 * @return　
	 */
	private boolean chkFileExist(File file) {
		if (file.exists()) {
			if (file.isFile() && file.canRead()) {
				return true;
			}
		}
		return false;
	}

    /**
     * ワークテーブル追加処理
     * @throws ConstraintsException
     */
	private void createWorkDB() {
		// 抽出条件の取得
		String gshokuiCcMaster = cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_GJUGYOINCC);
		Wz11tm019Filter wz11tm019Filter = new Wz11tm019Filter(houjinCode,gshokuiCcMaster);
		// G従業員マスタデータDB取得処理
		wz11tm019s = application.findMany(wz11tm019Filter);
		if (!wz11tm019s.isEmpty()) {
			for (Wz11tm019 wz11tm019 : wz11tm019s) {
				String guid = wz11tm019.getGuid();
				String shainBn = wz11tm019.getShainBn();
				String kaishaCd = wz11tm019.getKaishaCd();
				String soshikiCc = getSoshikiCc(guid,kaishaCd);
				String tanisoshikiCc = getTanisoshikiCc(kaishaCd,soshikiCc);
				String gshokuiCc = wz11tm019.getGshokuiCc();
				Wz11tm022 wz11tm022 = wz11tm022Application.findOne(new Wz11tm022Filter(kaishaCd,guid,shainBn));
				String genkaCd = null;
				String shoninsha1UserCd = null;
				String shoninsha2UserCd = null;
				if (wz11tm022 != null){
					genkaCd = wz11tm022.getGenkaCd();
					shoninsha1UserCd = wz11tm022.getShoninsha1UserCd();
					shoninsha2UserCd = wz11tm022.getShoninsha2UserCd();
				}
				Wz11tm018Filter wz11tm018Filter = new Wz11tm018Filter(guid);
				Wz11tm018 wz11tm018 = wz11tm018Application.findOne(wz11tm018Filter);
				
				Wz11tm011 wz11tm011 = new Wz11tm011();
				wz11tm011.setHojinCode(kaishaCd);
				wz11tm011.setGuid(guid);
				wz11tm011.setTrxType(cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_TRXTYPE));
				wz11tm011.setFirstName(wz11tm018 != null ? wz11tm018.getTushomeiKj() : null);
				wz11tm011.setLastName(wz11tm018 != null ? wz11tm018.getTushoseiKj() : null);
				if(!isNullorEmpty(shainBn)){
					wz11tm011.setEmpId(cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_EMPIDPREFIX) + shainBn);
				}
				wz11tm011.setLoginId(wz11tm018 != null ? wz11tm018.getMailaddress() : null);
				wz11tm011.setPassword(cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_PASSWORD));
				wz11tm011.setEmailAddress(wz11tm018 != null ?wz11tm018.getMailaddress() : null);
				wz11tm011.setLocalCode(cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_LOCALCODE));
				wz11tm011.setCountryCode(cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_COUNTRYCODE));
				wz11tm011.setLedgerCode(cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_LEDGERCODE));
				wz11tm011.setReimbursementCcd(cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_REIMBURSEMENTCCD));
				wz11tm011.setOrgUnit1(cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_ORGUNIT1));
				if(!isNullorEmpty(tanisoshikiCc)){
					wz11tm011.setOrgUnit2(tanisoshikiCc + cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_TANISOSHIKICC_SUFFIX));
				}
				wz11tm011.setOrgUnit3(tanisoshikiCc);
				wz11tm011.setCustom1(cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_CUSTOM1));
				wz11tm011.setCustom2(soshikiCc);
				wz11tm011.setCustom3(cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_CUSTOM3));
				wz11tm011.setCustom4(genkaCd);
				wz11tm011.setCustom5(cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_CUSTOM5));
				wz11tm011.setCustom6(wz11tm022 != null ? wz11tm022.getShokuiCd() : null );
				wz11tm011.setUserGroupName(cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_USERGROUPNAME));
				wz11tm011.setNotice1(cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_NOTICE1));
				wz11tm011.setNotice2(cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_NOTICE2));
				wz11tm011.setNotice3(cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_NOTICE3));
				wz11tm011.setNotice4(cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_NOTICE4));
				wz11tm011.setNotice5(cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_NOTICE5));
				wz11tm011.setNotice6(cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_NOTICE6));
				wz11tm011.setNotice7(cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_NOTICE7));
				wz11tm011.setNotice8(cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_NOTICE8));
				wz11tm011.setNotice13(cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_NOTICE13));
				wz11tm011.setNotice14(cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_NOTICE14));
				wz11tm011.setExpRepApprover(shoninsha1UserCd);
				wz11tm011.setTravelReqApprover(shoninsha1UserCd);
				wz11tm011.setTraveler(cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_TRAVELER));
				String approver = null ;
				if(gshokuiCcMaster.contains(gshokuiCc)) {
					approver = cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_APPROVER_Y);
				}else{
					approver = cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_APPROVER_N);
				}
				wz11tm011.setApprover(approver);
				wz11tm011.setBiManager(tanisoshikiCc);
				wz11tm011.setTravelReqUser(cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_TRAVELREQUSER));
				String travelReqManager = null;
				if(gshokuiCcMaster.contains(gshokuiCc)) {
					travelReqManager = cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_TRAVEL_REQ_MANAGER_Y);
				}else{
					travelReqManager = cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_TRAVEL_REQ_MANAGER_N);
				}
				wz11tm011.setTravelReqManager(travelReqManager);
				wz11tm011.setExpRepApprover2(shoninsha2UserCd);
				wz11tm011.setTravelExpUser(cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_TRAVELEXPUSER));
				wz11tm011.setCliqbookUser(cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_CLIQBOOKUSER));
				wz11tm011.setTravelReqApprover2(shoninsha2UserCd);
				wz11tm011.setUserid(cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_USERID));
				wz11tm011.setRnewDay(Calendar.getInstance().getTime());
				wz11tm011s.add(wz11tm011);
			}
		}
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws ConstraintsException
	 */
	private List<Wz1140003File> editRecord() throws IOException, IllegalAccessException, InvocationTargetException, ConstraintsException {
		Collection<Wz11tm012> wz11tm012list = wz11tm012Application.findMany(new Wz11tm012Filter(houjinCode,null,null));
		List<Wz1140003File> outputList = new  ArrayList<Wz1140003File>();
		String activeFlgN = cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_ACTIVE_N);
		String activeFlgY = cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_ACTIVE_Y);
		
		// 削除対象特定処理
		for(Wz11tm012 wz11tm012 : wz11tm012list) {
			Wz11tm011 wz11tm011 = wz11tm011Application.findOne(new Wz11tm011Filter(wz11tm012.getHojinCode(),wz11tm012.getGuid(),wz11tm012.getTrxType())); 
			if(null == wz11tm011 && activeFlgY.equals(wz11tm012.getActive())) { //削除
				Wz1140003File wz1140003File = new Wz1140003File();
				BeanUtils.copyProperties(wz1140003File,wz11tm012);
				wz1140003File.setFutureUse50(null);
				wz1140003File.setActive(activeFlgN);
				outputList.add(wz1140003File);
				
				wz11tm012.setFutureUse50(null);
				wz11tm012.setActive(activeFlgN);
				wz11tm012s.add(wz11tm012);
			}
		}
		
		// 追加・更新対象特定処理
		for(Wz11tm011 wz11tm011 : wz11tm011s) {
			Wz11tm012 wz11tm012 = wz11tm012Application.findOne(new Wz11tm012Filter(wz11tm011.getHojinCode(),wz11tm011.getGuid(),wz11tm011.getTrxType()));
			Long wz11tm012ID = new Long(0);

			//追加対象特定処理	
			if(wz11tm012 == null) { 
				Wz1140003File wz1140003File = new Wz1140003File();
				BeanUtils.copyProperties(wz1140003File, wz11tm011);
				wz1140003File.setFutureUse50(null);
				wz1140003File.setActive(activeFlgY);
				outputList.add(wz1140003File);
				
				wz11tm012 = new Wz11tm012();
				BeanUtils.copyProperties(wz11tm012,wz11tm011);
				wz11tm012.setId(null);
				wz11tm012s.add(wz11tm012);
				continue;
			}
			//更新対象特定処理	
			if(!isSame(wz11tm012,wz11tm011)) {
				Wz1140003File wz1140003File = new Wz1140003File();
				BeanUtils.copyProperties(wz1140003File, wz11tm011);
				wz1140003File.setFutureUse50(null);
				wz1140003File.setActive(activeFlgY);
				outputList.add(wz1140003File);
				
				wz11tm012ID = wz11tm012.getId();
				wz11tm012 = new Wz11tm012();
				BeanUtils.copyProperties(wz11tm012,wz11tm011);
				wz11tm012.setId(wz11tm012ID);
				wz11tm012.setFutureUse50(null);
				wz11tm012.setActive(activeFlgY);
				wz11tm012.setPersisted();
				wz11tm012s.add(wz11tm012);
			}
		}
		return outputList;
	}
	
	/*
	 * 組織コード取得
	 * 
	 * @param guid
	 * @param kaishaCd
	 * 
	 * @return 組織コード
	 */
	private String getSoshikiCc(String guid, String kaishaCd) {
		String soshikiCc = "";
		String tempDate = "";
		String maxDate = "";
		// 有効終了日最新のレコードを使用
		Collection<Wz11tm020> wz11tm020s = wz11tm020Application
				.findMany(new Wz11tm020Filter(guid, kaishaCd, cu.getValue(houjinCode, processId, MasterConstant.WZ1140003_SHONMUKENMU_FL)));
		if (null == wz11tm020s || wz11tm020s.isEmpty()) {
			LOG.info(Wz11MsgUtils.get(MsgConstant.W0001, METHOD_GETSOSHIKICC,
					guid, kaishaCd));
		} else {
			for (Wz11tm020 wz11tm020 : wz11tm020s) {
				tempDate = wz11tm020.getTekiyosyuryoNb();
				if (isNullorEmpty(tempDate)
						|| !Wz11DateUtils.isDateFormat(tempDate)) {
					continue;
				}
				if (0 > maxDate.compareTo(tempDate)) {
					maxDate = tempDate;
					soshikiCc = wz11tm020.getSoshikiCc();
				}
			}
		}
		return soshikiCc;
	}

	/*
	 * 単位組織コード取得
	 * 
	 * @param kaishaCd
	 * @param soshikiCc
	 * 
	 * @return 単位組織コード
	 */
	private String getTanisoshikiCc(String kaishaCd, String soshikiCc) {
		String tanisoshikiCc = "";
		String tempDate = "";
		String maxDate = "";
		// 有効終了日最新のレコードを使用
		Collection<Wz11tm021> wz11tm021s = wz11tm021Application
				.findMany(new Wz11tm021Filter(kaishaCd, soshikiCc));
		if (null == wz11tm021s || wz11tm021s.isEmpty()) {
			LOG.info(Wz11MsgUtils.get(MsgConstant.W0001, METHOD_GETTANISOSHIKICC,
					kaishaCd, soshikiCc));
		} else {
			for (Wz11tm021 wz11tm021 : wz11tm021s) {
				tempDate = wz11tm021.getTekiyosyuryoNb();
				if (isNullorEmpty(tempDate)
						|| !Wz11DateUtils.isDateFormat(tempDate)) {
					continue;
				}
				if (0 > maxDate.compareTo(tempDate)) {
					maxDate = tempDate;
					tanisoshikiCc = wz11tm021.getTanisoshikiCc();
				}
			}
		}
		return tanisoshikiCc;
	}
	

	/**
	 * エンティティ比較
	 * @param wz11tm012
	 * @param wz11tm011
	 * @return
	 */
	private boolean isSame(Wz11tm012 wz11tm012, Wz11tm011 wz11tm011) {
		String[] compareList = StringUtils.split(COMPARE_COLUMN,",");
		
		boolean flg = false;
		if(wz11tm012 == null || wz11tm011 == null){
			return flg;
		}
		try {
			for (String compareStr : compareList) {
				String compare1 = Wz11EntityUtils.getProperty(wz11tm012, compareStr);
				String compare2 = Wz11EntityUtils.getProperty(wz11tm011, compareStr);
				if(isNullorEmpty(compare1)){
					compare1 = "";
				}
				if(isNullorEmpty(compare2)){
					compare2 = "";
				}
				if(!compare1.equals(compare2)){
					flg = false;
					break;
				}else{
					flg = true;
				}
			}
		} catch (IllegalAccessException e) {
			flg = false;
		} catch (InvocationTargetException e) {
			flg = false;
		} catch (NoSuchMethodException e) {
			flg = false;
		}
		return flg;
	}
	
	/**
	 * 文字列空白チェック
	 * 
	 * @param str
	 * @return チェック結果
	 */
	private boolean isNullorEmpty(String str) {
		return StringUtils.isEmpty(str);
	}
	/**
	 * メモリ解放
	 */
	private void clearMemory() {
		wz11tm011s = null;
		wz11tm019s = null;
		wz11tm012s = null;
		logOutput = null;
		returnObj = null;
		System.gc();

	}
}
