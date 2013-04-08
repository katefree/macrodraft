package macros
 
import language.experimental.macros
 
import reflect.macros.Context
 
object Point {

  var methods = Map[Any, Any]()

  def pointa(clazzName: _)(funName: _): _ = macro pointa_impl
  
  def pointa_impl(c: Context)(clazzName: c.Tree) (funName: c.Tree): c.Tree = {
     import c.universe._
    
 //    val body = Apply(Select(Super(This(newTypeName("")), newTypeName("")), nme.CONSTRUCTOR), List())
 //    val ctor = DefDef(NoMods, nme.CONSTRUCTOR, Nil, List(List()), TypeTree(), Block(List(body), Literal(Constant(()))))
 //    val idVal = idParam.duplicate
 //    val inner = fun match {
 //    	case Block(list, _) => list 
 //    	case _ => Nil
 //    }
 
 //    println(show(newClass));

 //    val tmpl = Template(List(Ident(newTypeName("AnyRef"))), emptyValDef, ctor::inner)

 // 	println(showRaw(fun.tree))   
 //    val cdef = ClassDef(NoMods, newTypeName("NewClass"), Nil, tmpl)
 //    println(show(cdef.name))
	// не на основе класс дефа, а на основе пачки функций ()
 
 //    val init = New(Ident(cdef.name), List(List()))
 //    println(show(Block(cdef, init)))
 //    val ooo = c.Expr(Block(cdef, init))

 //    println(show(ooo))
 //    ooo.tree
	//   val a () => c.Tree = {ooo.tree}
 //    a
 //    val clazzDefs = methods(show(className))
    println(show(methods))
    println(show(methods(show(clazzName))))

    q"class Aaa { }; () => {new Aaa}"
    // methods(show(clazzName))
  }


  def clazz(name: _)(fun: _): _ = macro clazz_impl

  def clazz_impl(c: Context)(name: c.Tree)(fun: c.Tree): c.Tree = {
    import c.universe._
    
    val defList = fun match {
      case Block(list, _) => list 
      case _ => Nil
    }
    // defList.map {DefDef(_, term, _, _, _, _) => (term -> )}

    def f(x:c.Tree) = {x match {
        case DefDef(_, term, _, _, _, _) => term
        case _ => Nil
      }
    }
    val defList1 = defList.map (x => (f(x), x))
  
    val clazzMethods = defList1 toMap

    // val q"{ ..$defs; () }" = fun
    val nameString = show(name)

    // q"class $name1 { ..$defs }; () => {new $name1}"
    // val defMap =  
    methods = methods + (nameString -> clazzMethods)
    fun
  }



}