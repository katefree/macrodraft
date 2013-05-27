import macros._;
import scala.reflect.runtime.{universe=>ru}
// import ClassPublisher._

@ClassPublisher
object Module {
  def class1 = {
    // def class1(f: Int) = {field1 = f}
    var field1 = "Hello world!" 
    def method1() = {println("method1")}
    def method3() = {println("method3")}
    def method2() = {println("method2 " + field1) }
  }
}

object Test1 extends App {
  // import ClassPublisher._
  import Module._

  // m1;

  // createClass(Module, "class", "name", {println("body")})
  
  class1method1();
  class1method2();
  class1method3();

  // println("!!!!!!!!!!"+t);
  // println("!!!!!!!!!!");
  // pointa(C){x};
  
  // oblect1 = class1(56);
  // var value = method2(oblect1); 
  // var value = point(C)(x) 
  // value = point(C)(v)
  // println("value = " + value);

  // = hello({var dd = 7; var ss = 8; def hey = {println("Hello world!" + dd)}; 
    // def setDD(dd: Int) = {this.dd=dd; this.hey()}; def s6(dd: Int) = {println(8 + dd)} })
  // var x2 = x;



  // println("before");
  // println(x.dd);
  // println(x.hey);
  // x.ss = 88;
  // x.setDD(999);
  // //x.s6(1);
  // println("after set");

  // println(x.ss);

  // println(x.dd);
  // println(x2.dd);

  // x.hey;
  // println(x);
  // x.hey();
  // println(x.dd); 
  
  
  // println("!!!");  
  // println(ru.showRaw(hello({def dd = 7})))
  // println(ru.showRaw(ru.reify(x)))
  // println(ru.showRaw(ru.reify(x)))
  // println("sdfsd")
   // val f = new NewClass1()
}