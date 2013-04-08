package macros
 
import language.experimental.macros
 
import reflect.macros.Context
 
object Second {
  def hello(name: _)(fun: _): _ = macro hello_impl
  def hello_impl(c: Context)(name: c.Tree)(fun: c.Tree): c.Tree = {
    import c.universe._
        	
    val q"{ ..$defs; () }" = fun
    val name1 = newTypeName(show(name))
    q"class $name1 { ..$defs }; () => {new $name1}"
  }


}