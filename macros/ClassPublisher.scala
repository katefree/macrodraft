package macros
 
import scala.reflect.macros.Context
import language.experimental.macros
import scala.annotation.MacroAnnotation
import scala.reflect.macros.AnnotationContext


class ClassPublisher extends MacroAnnotation {

  // var methods = Map[String, Map[_ >: String, _ >: Any]]()

  def transform = macro Impl.transformImpl

} 

object Impl {

  def transformImpl(c: AnnotationContext) = {
     import c.universe._
      // println(showRaw(c.annottee));
     // c.annottee match {
      // case ClassDef(Modifiers(flags, privateWithin, annotations), name, tparams, Template(parents, self, body)) =>
      //   val annotations1 = Nil // TODO: find out a good way to exclude the currently expanding annotation
      //   val foo = DefDef(NoMods, TermName("foo"), Nil, Nil, TypeTree(), Literal(Constant("foo")))
      //   ClassDef(Modifiers(flags, privateWithin, annotations1), name, tparams, Template(parents, self, body :+ foo))


      // case DefDef(Modifiers(flags, privateWithin, annotations), name, tparams, Template(parents, self, body)) =>
      //   val annotations1 = Nil // TODO: find out a good way to exclude the currently expanding annotation
      //   val foo = DefDef(NoMods, TermName("foo"), Nil, Nil, TypeTree(), Literal(Constant("foo")))
      //   ClassDef(Modifiers(flags, privateWithin, annotations1), name, tparams, Template(parents, self, body :+ foo))
    // }

    // c.Expr(Block((methods(show(clazzName))(show(funName))).asInstanceOf[c.Tree], c.universe.Ident(show(funName)))).tree
    // val ident = c.universe.Ident(show("v"));
    // val defs = (methods(show("C"))(show("v"))).asInstanceOf[c.Tree]
    // q"{ $defs; $ident}"
      // c.annottee match {
      //   case DefDef(_, term, _, _, _, _) => term
      //   case _ => Nil
      // }

// object Module {
//   def class1 = {
//     def class1(f: Int) = {field1 = f}
//     var field1 = 0 
//     def method1 = {println("method1")}
//     def method2 = {println("method2 " + field1) }
//   }

// }
    // val q"{ ..$defs; () }" = c.annottee
    // val q"{object Module { ..$body } }" = c.annottee
    // val q"$body" = c.annottee

    // println(body)

    // val mod : c.Tree = c.annottee
    // val q"object Module extends AnyRef {$modifiers}" = c.annottee
    // val q" object Module extends scala.AnyRef { ..${_ :: defs} }" = q"$mod" 

      // println(defs);
      // { ..${ :: defs} }
  println(showRaw(c.annottee))


    // val name1 = newTypeName(show(name))
    // q"class $name1 { ..$defs }; () => {new $name1}"


    // ModuleDef(Modifiers(NoFlags, tpnme.EMPTY, List(Apply(Select(New(Ident(TypeName("ClassPublisher"))), nme.CONSTRUCTOR), List()))), 
    //   TermName("Module"), 
    //   Template(List(Select(Ident(scala), TypeName("AnyRef"))), emptyValDef, 
    //     List(DefDef(Modifiers(), nme.CONSTRUCTOR, List(), List(List()), TypeTree(), 
    //       Block(List(pendingSuperCall), Literal(Constant(())))), DefDef(Modifiers(), TermName("class1"), List(), List(), TypeTree(), 
    //       Block(List(DefDef(Modifiers(), TermName("class1"), List(), List(List(ValDef(Modifiers(PARAM), TermName("f"), Ident(TypeName("Int")), EmptyTree))), 
    //         TypeTree(), 
    //         Assign(Ident(TermName("field1")), Ident(TermName("f")))), ValDef(Modifiers(MUTABLE), TermName("field1"), TypeTree(), 
    //         Literal(Constant(0))), DefDef(Modifiers(), TermName("method1"), List(), List(), TypeTree(), 
    //         Apply(Ident(TermName("println")), List(Literal(Constant("method1"))))), 
    //         DefDef(Modifiers(), TermName("method2"), List(), List(), TypeTree(), Apply(Ident(TermName("println")), 
    //           List(Apply(Select(Literal(Constant("method2 ")), TermName("$plus")), List(Ident(TermName("field1")))))))), Literal(Constant(())))))))


  var globalMethods = List[c.Tree]();
  var constructors = List[String]();

  val (moduleName, defs) = c.annottee match {
    case ModuleDef(_, name, defs) => (name, defs)
  }
  // println(defs)

  val classes = defs match {
    case Template(_, _, m) => m
  }
  // println(showRaw(classes))
  // println(classes)

  for(clazz <- classes) {
    val current = clazz match {
      // skip constructor
      case DefDef(_, nme.CONSTRUCTOR, _, _, _, _) => null
      case any => any 
    }
    if (current != null) {
      //generate class constructor and methods
      addClass(current);  
    }
    
  }

  
  def addClass(input: Tree) {

    def checkCode(methodCode: Apply): Apply = {
      val res = methodCode match {
        case Apply(x, List(Apply(y, List(Ident(termName))))) => Apply(x, List(Apply(y, List(Ident(changeTermName(termName))))))
        case other => other //add common logic
      }
      res
    }

    def changeTermName(name: Name) = {
        name match {
          case TermName(name) => TermName("class1"+name)
        }
    }


    val (name, methods) = input match {
      case DefDef(_, TermName(name), _, _, TypeTree(), Block(xxx, _))  => (name, xxx)
    }
    
    println("!!!!!!!!!!!!!!!!!!!")
    println(showRaw(methods))


// DefDef(Modifiers(), TermName("method1"), List(), List(), TypeTree(), 
// Apply(Ident(TermName("println")), List(Literal(Constant("method1")))))

// ModuleDef(Modifiers(NoFlags, tpnme.EMPTY, List(Apply(Select(New(Ident(TypeName("ClassPublisher"))), nme.CONSTRUCTOR), List()))), 
//   TermName("Module"), Template(List(Select(Ident(scala), TypeName("AnyRef"))), emptyValDef, List(DefDef(Modifiers(), nme.CONSTRUCTOR, List(), List(List()), 
//     TypeTree(), Block(List(pendingSuperCall), Literal(Constant(())))), DefDef(Modifiers(), TermName("class1"), List(), List(), TypeTree(), 
//     Block(List(DefDef(Modifiers(), TermName("method1"), List(), List(List()),
// TypeTree(), Apply(Ident(TermName("println")), List(Literal(Constant("method1")))))), Literal(Constant(())))))))


// ModuleDef(Modifiers(NoFlags, tpnme.EMPTY, List(Apply(Select(New(Ident(TypeName("ClassPublisher"))), nme.CONSTRUCTOR), List)))), 
// TermName("Module"), Template(List(Select(Ident(scala), TypeName("AnyRef"))), emptyValDef, List(
//   DefDef(Modifiers(), ne.CONSTRUCTOR, List(), List(List()), TypeTree(), Block(List(pendingSuperCall), Literal(Constant(())))), 
//   DefDef(Modifiers() TermName("class1"), List(), List(), TypeTree(), Block(List(
    // ValDef(Modifiers(MUTABLE), TermName("field1"), TypeTree(), Litral(Constant("asdfghjkl;"))), 
//     DefDef(Modifiers(), TermName("method1"), List(), List(List()), TypeTree(), Apply(Ident(TermNme("println")), List(Literal(Constant("method1"))))), 
//     DefDef(Modifiers(), TermName("method2"), List(), List(List()), TypeTee(), Apply(Ident(TermName("println")), List(Apply(Select(Literal(Constant("method2 ")), TermName("$plus")), List(Ident(TemName("field1")))))))), Literal(Constant(())))))))


// Apply(Ident(TermName("println")), List(Apply(Select(Literal(Constant("method2 ")), TermName("$plus")), List(Ident(TemName("field1")))))))


    for (method <- methods) {
      method match {
        case DefDef(_, TermName(methodName), List(), List(List()), TypeTree(), code/*Apply*/) => {
          val newName = TermName("class1" + methodName);
          val newCode = checkCode(code.asInstanceOf[Apply])
          // val newCode = code
          val method = q"def $newName() = {$newCode}" 
          globalMethods = method :: globalMethods

        }
        case ValDef(_, TermName(fieldName), TypeTree(), value) => {
          val newName = TermName("class1" + fieldName);
          val field = q"var $newName = $value"
          // val field = q"var field1 = 'a'"
          globalMethods = field :: globalMethods

        } 
      }
    }



    // constructors = ("def init" + name + " = {HashMap()}") :: constructors
  }




    val res = q"object Module { ..$globalMethods }"
    println(showRaw(res))
    res
    // q"object Module { def m1 = {println('A')}}"

    // todo add class memberrs + class creation method to predifined module
  }
}