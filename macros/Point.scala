//package macros
 
import language.experimental.macros
import reflect.macros.Context
import reflect.macros.Macro


trait Impl extends Macro {
  var methods = Map[String,Map[_ >: String, c.Tree]]()

  /** Callback triggered by the compiler whenever it tries to infer type arguments in an expression
   *  which involves one of the macros in the bundle. See http://docs.scala-lang.org/overviews/macros/inference.html for documentation.
   */
  // override def onInfer(tc: c.TypeInferenceContext): Unit = {}

  def point(clazzName: c.Tree) (funName: c.Tree): c.Tree = {
     import c.universe._
    
     println(show(methods))
     println(show(methods(show(clazzName))))

     // q"class Aaa { }; () => {new Aaa}"
     methods(show(clazzName))(show(funName))
  }

  def clazz(name: c.Tree)(fun: c.Tree): c.Tree = {
    
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
    methods = methods + (nameString -> clazzMethods)

    fun
  }
}


object Point {
  def point(clazzName: _)(funName: _): _ = macro Impl.point
  def clazz(name: _)(fun: _): _ = macro Impl.clazz
}
 
