import macros._;
import scala.reflect.runtime.{universe=>ru}

object Test2 extends App {
  import Second._
  def x1 = hello(ClassName)({var dd = 7; var ss = 8; def hey() = {println("Hello world!" + dd)}; 
    def setDD(dd: Int) = {this.dd=dd; this.hey()}; def s6(dd: Int) = {println(8 + dd)} })
  var x = x1();
  var x2 = x1();
  println("before");
  println(x.dd);
  //println(x.hey);
  //x.ss = 88;
  x.setDD(999);
  //x.s6(1);
  println("after set");
  println(ru.show(x))
  println(ru.show(x2))

  println(ru.show(x2))
  println(x.dd);
  println(x2.dd);

  x.hey;
  println(x);
  // x.hey();
  // println(x.dd);	
  
  
  println("!!!");	
  // println(ru.showRaw(hello({def dd = 7})))
  // println(ru.showRaw(ru.reify(x)))
  println(ru.showRaw(ru.reify(x)))
  println("sdfsd")
   // val f = new NewClass1()
}