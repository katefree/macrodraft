package macros
 
import language.experimental.macros
 
import reflect.macros.Context
 
object One {
  def hello(fun: _): _ = macro hello_impl
  
  def hello_impl(c: Context) (fun: c.Tree): c.Tree = {
     import c.universe._
     val body = Apply(Select(Super(This(newTypeName("")), newTypeName("")), nme.CONSTRUCTOR), List())
    val ctor = DefDef(NoMods, nme.CONSTRUCTOR, Nil, List(List()), TypeTree(), Block(List(body), Literal(Constant(()))))
    // val idVal = idParam.duplicate
    val inner = fun match {
    	case Block(list, _) => list 
    	case _ => Nil
    }
 
    // println(show(newClass));

    val tmpl = Template(List(Ident(newTypeName("AnyRef"))), emptyValDef, ctor::inner)

 	// println(showRaw(fun.tree))   
    val cdef = ClassDef(NoMods, newTypeName("NewClass"), Nil, tmpl)
    println(show(cdef.name))
	// не на основе класс дефа, а на основе пачки функций ()
 
    val init = New(Ident(cdef.name), List(List()))
    // println(show(Block(cdef, init)))
    val ooo = c.Expr(Block(cdef, init))

    println(show(ooo))
    ooo.tree
	  //val a () => c.Tree = {ooo.tree}
    //a
  }
}