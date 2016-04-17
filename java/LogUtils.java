public class LogUtils {

  private static final String NEWLINE = System.getProperty("line.separator");

  private LogUtils (){  }

  public static String getStack(int deep) {
    // deep = 0  no level, only current calling method
    //    n      from "n" levels
    StringBuilder sb = new StringBuilder();
    StackTraceElement ste [] = Thread.currentThread().getStackTrace();
    int k = 2; // startingpoint 0:getstacktrace() 1: getStack()
    int j = ste.length - 1;
    // process the stack
    if (deep > j) deep = j;
    else deep = deep + k;

    while (k <= deep) {
      String line = ste[k].toString();
      sb.append(line + NEWLINE);
      k++;
    }
    return sb.toString();
  }

  public static void main(String args[]){
    Test test = new Test();
    test.doit();
  }
}

class Test {
  public void doit() {
    System.out.println("*Howto Trace only 1 level\n" + LogUtils.getStack(1));
    System.out.println("*Howto Trace only 10 levels\n" + LogUtils.getStack(10));
    System.out.println("*Howto Trace no level (current)\n" + LogUtils.getStack(0));
  }
}