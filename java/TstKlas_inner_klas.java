import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

public class TstKlas {

	public static void main(String[] args){
		String tmpdir = System.getProperty("java.io.tmpdir");
		System.out.println("System.getProperty(\"java.io.tmpdir\") : " + tmpdir);
		
		TstKlas tst = new TstKlas();
		String preA = new String("品目グループ");
		String preB = new String("品目グループテキスト");
		
		System.out.println("fStart:" + tst.fStart);
		System.out.println("fStop:" + tst.fStop);
		tst.fStart = System.nanoTime();
		tst.fStop = 0;
		
		for(int i = 0; i < 1000; ++i){
			TstKlas.Wz11tm024 obj = tst.new Wz11tm024();	
			obj.setMatkl(preA + String.valueOf(i));
			obj.setWgbez(preB + String.valueOf(i));
			tst.wz11tm024_list.add(obj);
		}
		
		tst.fStop = System.nanoTime();
		BigDecimal intrvl = new BigDecimal(tst.fStop-tst.fStart);
		intrvl = intrvl.divide(MILLION, 3, BigDecimal.ROUND_HALF_EVEN);
		System.out.println("#1");
		System.out.println(intrvl + " ms");
		tst.fStart = tst.fStop;

		tst.postgre2txt();
		
		tst.fStop = System.nanoTime();
		intrvl = new BigDecimal(tst.fStop-tst.fStart);
		intrvl = intrvl.divide(MILLION, 3, BigDecimal.ROUND_HALF_EVEN);
		System.out.println("#2");
		System.out.println(intrvl + " ms");
	}
	
	public void postgre2txt() {
		String strPath = System.getProperty("java.io.tmpdir");
		if(null == strPath || strPath.isEmpty()){
			return;
		}
		String strPathSeparatorNO = System.getProperty("path.separator"); // ;
		String strPathSeparator = System.getProperty("file.separator"); //Character that separates components of a file path. This is "/" on UNIX and "\" on Windows.
		System.out.println("System.getProperty(\"path.separator\") : " + strPathSeparatorNO);
		System.out.println("System.getProperty(\"file.separator\") : " + strPathSeparator);
		if(null == strPathSeparator || strPathSeparator.isEmpty()){
			 if(strPath.contains("\\"))
				strPathSeparator = "\\";
			 else
				 strPathSeparator = "/";
		}
		if(!strPath.isEmpty() && !strPath.endsWith(strPathSeparator))
			strPath = strPath + strPathSeparator;
		
		strPath = strPath + "品目グループマスタ_YYYYMMDDhhmmss.txt";
		System.out.println("TstKlas::postgre2txt()|full path of file to create:["+strPath+"]\n");
		BufferedWriter bw = null;
		try {
			// ファイル存在チェック
	        if (!isFileWritable(strPath)){
	        	System.out.println("destination file unwritable!");
	        	System.out.println("destination file unwritable!");
	        	return;
	        }
			bw = new BufferedWriter(new FileWriter(strPath,false));//上書き
			if (null != wz11tm024_list && !wz11tm024_list.isEmpty()) {
				//「一行目はブランク、2行目以降は」
				String list_name = "Expense Entry_49_品目グループ";
				String list_category_name = "Expense Entry_49_品目グループ";
				String level_01_code = "0100";
				String level_02_code = "";
				String level_03_code = "";																	
				String level_04_code = "";																	
				String level_05_code = "";																	
				String level_06_code = "";																	
				String level_07_code = "";																	
				String level_08_code = "";																	
				String level_09_code = "";																	
				String level_10_code = "";																	
				String Description_Name = "";
				String start_date = "";
				String end_date = "";
				String delete_list_item = "N";
				
				bw.write(list_name);
				bw.write("|");
				bw.write(list_category_name);
				bw.write("|");
				bw.write(level_01_code);
				bw.write("|");
				bw.write(level_02_code);
				bw.write("|");
				bw.write(level_03_code);
				bw.write("|");
				bw.write(level_04_code);
				bw.write("|");
				bw.write(level_05_code);
				bw.write("|");
				bw.write(level_06_code);
				bw.write("|");
				bw.write(level_07_code);
				bw.write("|");
				bw.write(level_08_code);
				bw.write("|");
				bw.write(level_09_code);
				bw.write("|");
				bw.write(level_10_code);
				bw.write("|");
				bw.write(Description_Name);
				bw.write("|");
				bw.write(start_date);
				bw.write("|");
				bw.write(end_date);
				bw.write("|");
				bw.write(delete_list_item);
//				bw.write("|"_WZ1140002); //| after last record?
				bw.newLine();
				
				for(Wz11tm024 wz11tm024 : wz11tm024_list) {
					/** 文字列変換及び整形*/
					list_name = "Expense Entry_49_品目グループ";
					list_category_name = "Expense Entry_49_品目グループ";
					level_01_code = "0100";
					String strMatkl = wz11tm024.getMatkl();
					if(null == strMatkl)
						strMatkl = "";
					level_02_code = strMatkl;
					level_03_code = "";																	
					level_04_code = "";																	
					level_05_code = "";																	
					level_06_code = "";																	
					level_07_code = "";																	
					level_08_code = "";																	
					level_09_code = "";																	
					level_10_code = "";
					String strDescription_Name = wz11tm024.getWgbez();
					if(null == strDescription_Name)
						strDescription_Name = "";
					Description_Name = strDescription_Name;
					start_date = "";
					end_date = "";
					delete_list_item = "N";
					
					bw.write(list_name);
					bw.write("|");
					bw.write(list_category_name);
					bw.write("|");
					bw.write(level_01_code);
					bw.write("|");
					bw.write(level_02_code);
					bw.write("|");
					bw.write(level_03_code);
					bw.write("|");
					bw.write(level_04_code);
					bw.write("|");
					bw.write(level_05_code);
					bw.write("|");
					bw.write(level_06_code);
					bw.write("|");
					bw.write(level_07_code);
					bw.write("|");
					bw.write(level_08_code);
					bw.write("|");
					bw.write(level_09_code);
					bw.write("|");
					bw.write(level_10_code);
					bw.write("|");
					bw.write(Description_Name);
					bw.write("|");
					bw.write(start_date);
					bw.write("|");
					bw.write(end_date);
					bw.write("|");
					bw.write(delete_list_item);
//					bw.write("|"_WZ1140002); //| after last record?
					bw.newLine();
				}
			}
		} catch (FileNotFoundException e) {
			//bad to leave catch empty
		} catch (IOException e) {
			//bad to leave catch empty
		}finally {
			// ファイル書き込み処理終了
			try{
				if(null != bw)
					bw.close();
			}catch(IOException e){
			}
		}
	}

	class Wz11tm024{
	    private String hojinCode;
	    private String matkl;
	    private String wgbez;
		public String getHojinCode() {
			return hojinCode;
		}
		public void setHojinCode(String hojinCode) {
			this.hojinCode = hojinCode;
		}
		public String getMatkl() {
			return matkl;
		}
		public void setMatkl(String matkl) {
			this.matkl = matkl;
		}
		public String getWgbez() {
			return wgbez;
		}
		public void setWgbez(String wgbez) {
			this.wgbez = wgbez;
		}
	}

	private boolean isFileWritable(String file) {
		File fl = new File(file);
		if(fl.exists() && (!fl.isFile() || !fl.canWrite()))
				return false;
		return true;
	}
	
	private ArrayList<Wz11tm024> wz11tm024_list = new ArrayList<Wz11tm024>(); // better initialize in constructor?
	
	private long fStart;
	private long fStop;
	/** Converts from nanos to millis. */
	private static final BigDecimal MILLION = new BigDecimal("1000000");
}
