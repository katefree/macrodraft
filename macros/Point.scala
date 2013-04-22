package macros
 
import language.experimental.macros
 
import reflect.macros.Context
 
object Pointx {

  var methods = Map[String, Map[_ >: String, _ >: Any]]()

  def point(clazzName: _)(funName: _): _ = macro point_impl
  
  def point_impl(c: Context)(clazzName: c.Tree) (funName: c.Tree): c.Tree = {
     import c.universe._
    
    // c.Expr(Block((methods(show(clazzName))(show(funName))).asInstanceOf[c.Tree], c.universe.Ident(show(funName)))).tree
    val ident = c.universe.Ident(show(funName));
    val defs = (methods(show(clazzName))(show(funName))).asInstanceOf[c.Tree]
    q"{ $defs; $ident}"
  }


  def clazz(name: _)(fun: _): _ = macro clazz_impl

  def clazz_impl(c: Context)(name: c.Tree)(fun: c.Tree): c.Tree = {
    import c.universe._
    
    println(showRaw(fun))

    val defList = fun match {
      case Block(list, _) => list 
      case _ => Nil
    }

    def f(x:c.Tree) = { 
      val term = x match {
        case DefDef(_, term, _, _, _, _) => term
        case _ => Nil
      }
      val y = term match {
        case TermName(y) => y
      }
      y
    }
    val defList1 = defList.map (x => (f(x), x))
  
    val clazzMethods = defList1 toMap

    val nameString = show(name)
    println(show(name))
    
    methods = methods + (nameString -> clazzMethods)
    fun
  }



}