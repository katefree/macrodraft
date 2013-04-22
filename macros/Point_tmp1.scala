//package macros
 
import language.experimental.macros
import reflect.macros.Context
import reflect.macros.Macro

//1 polimorphism
//2 essentialy multymethod
//3 
abstract class A extends Macro {
  var methods = Map[String,Map[_ >: String, c.Tree]]()
}

trait Impl extends A {
  
  // var methods = Map[String,Map[_ >: String, c.Tree]]()

  def point(clazzName: c.Tree) (funName: c.Tree): c.Tree = {
     import c.universe._
    
     println(show(methods))
     println(show(methods(show(clazzName))))

     // q"class Aaa { }; () => {new Aaa}"
     methods(show(clazzName))(show(funName))
  }

  def clazz(name: c.Tree)(fun: c.Tree): c.Tree = {
     import c.universe._
    
    val defList = fun match {
      case Block(list, _) => list 
      case _ => Nil
    }

     def f(x:c.Tree) = {
      x match {
        case DefDef(_, term, _, _, _, _) => term
        case _ => Nil
      }
    }
    val defList1 = defList.map (x => (f(x), x))
  
    val clazzMethods = defList1 toMap

    val nameString = show(name)
    // methods = methods + (nameString -> clazzMethods)

    methods = Map[String,Map[_ >: String, c.Tree]]() + (nameString -> clazzMethods)

    println(show(methods))

    fun
  }
}



object Point {
  def point(clazzName: _)(funName: _): _ = macro Impl.point
  def clazz(name: _)(fun: _): _ = macro Impl.clazz
}
 
