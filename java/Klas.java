public class Klas{
	public static void main(String[] args){
		//debug/log/info... get current line number function name class name file name at runtime
		System.out.println("//debug/log/info... get current line number function name class name file name at runtime");
		for(int i=0; i < 3;++i)
			System.out.println(Thread.currentThread().getStackTrace()[i].getMethodName());
		System.out.println("");
		System.out.println("System.nanoTime():\t\t"+System.nanoTime());
		System.out.println("System.currentTimeMillis():\t"+System.currentTimeMillis());
	}
}