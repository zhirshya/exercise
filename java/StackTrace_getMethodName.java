public class StackTrace_getMethodName {
 public static void main(String args[]) {
    trace(Thread.currentThread().getStackTrace()); // output :main
    new StackTrace_getMethodName().doit();
    trace(Thread.currentThread().getStackTrace()); // output :main
 	System.out.println("Thread.currentThread().getStackTrace():"+Thread.currentThread().getStackTrace().toString());
 	System.out.println("Thread.currentThread().getStackTrace().length(assuming array):"+Thread.currentThread().getStackTrace().length);
 }
 public void doit() {
    trace(Thread.currentThread().getStackTrace()); // output :doit
    doitagain();
  }
 public void doitagain() {
    trace(Thread.currentThread().getStackTrace()); // output : doitagain
  }

 public static void trace(StackTraceElement e[]) {
   boolean doNext = false;
   for (StackTraceElement s : e) {
       if (doNext) {
          System.out.println(s.getMethodName());
          return;
       }
       doNext = s.getMethodName().equals("getStackTrace");
   }
 }
}