package org.naderica.parser.sourcecode.java20.standard.mapper
import java.util.List as JList
import scala.jdk.CollectionConverters._
import scala.jdk.CollectionConverters._
import scala.util.{Try, Success, Failure}
import org.naderica.parser.sourcecode.ast.java.standard.Java20Parser.*
import org.naderica.parser.sourcecode.java20.standard.grammar.RecordBody
import org.naderica.parser.sourcecode.java20.standard.grammar.RecordHeader
import org.naderica.parser.sourcecode.java20.standard.grammar.RecordComponentList
import org.naderica.parser.sourcecode.java20.standard.grammar.RecordComponent
import org.naderica.parser.sourcecode.java20.standard.grammar.VariableArityRecordComponent
import org.naderica.parser.sourcecode.java20.standard.grammar.RecordBodyDeclaration
import org.naderica.parser.sourcecode.java20.standard.grammar.CompactConstructorDeclaration

object RecordMapper {
  def toRecordHeader(ctx: RecordHeaderContext): RecordHeader = {
    val recordComponentList = Option(ctx.recordComponentList()).map(toRecordComponentList)
    
    RecordHeader(recordComponentList = recordComponentList)
  }

  def toRecordBody(ctx: RecordBodyContext): RecordBody = {
    import scala.jdk.CollectionConverters._
    val recordBodyDeclarations = ctx.recordBodyDeclaration().asScala.map(toRecordBodyDeclaration).toList
    
    RecordBody(recordBodyDeclarations = recordBodyDeclarations)
  }

  def toRecordComponentList(ctx: RecordComponentListContext): RecordComponentList = {
    import scala.jdk.CollectionConverters._
    val recordComponents = ctx.recordComponent().asScala.map(toRecordComponent).toList
    // Note: variableArityRecordComponent is handled within RecordComponent context
    
    RecordComponentList(
      recordComponents = recordComponents,
      variableArityRecordComponent = None  // Will be handled in RecordComponent if present
    )
  }

  def toRecordComponent(ctx: RecordComponentContext): RecordComponent = {
    import scala.jdk.CollectionConverters._
    val recordComponentModifiers = ctx.recordComponentModifier().asScala.map(ModifierMapper.toRecordComponentModifier).toList
    val unannType = TypeMapper.toUnannType(ctx.unannType())
    val variableDeclaratorId = org.naderica.parser.sourcecode.java20.standard.grammar.VariableDeclaratorId(
      identifier = org.naderica.parser.sourcecode.java20.standard.grammar.Identifier(ctx.identifier().getText),
      dims = None  // Simple case for now
    )
    
    RecordComponent(
      recordComponentModifiers = recordComponentModifiers,
      unannType = unannType,
      variableDeclaratorId = variableDeclaratorId
    )
  }

  def toVariableArityRecordComponent(ctx: VariableArityRecordComponentContext): VariableArityRecordComponent = {
    import scala.jdk.CollectionConverters._
    val recordComponentModifiers = Option(ctx.recordComponentModifier())
      .map(_.asScala.map(ModifierMapper.toRecordComponentModifier).toList)
      .getOrElse(List.empty)
    val unannType = TypeMapper.toUnannType(ctx.unannType())
    val annotations = Option(ctx.annotation())
      .map(_.asScala.map(AnnotationMapper.toAnnotation).toList)
      .getOrElse(List.empty)
    val identifier = org.naderica.parser.sourcecode.java20.standard.grammar.Identifier(ctx.identifier().getText)
    
    VariableArityRecordComponent(
      recordComponentModifiers = recordComponentModifiers,
      unannType = unannType,
      annotations = annotations,
      identifier = identifier
    )
  }

  def toRecordBodyDeclaration(ctx: RecordBodyDeclarationContext): RecordBodyDeclaration = {
    if (ctx.compactConstructorDeclaration() != null) {
      toCompactConstructorDeclaration(ctx.compactConstructorDeclaration())
    } else {
      // For other record body declarations, we need to cast since RecordBodyDeclaration extends ClassBodyDeclaration
      val classBodyDecl = ClassBodyMapper.toClassBodyDeclaration(ctx.classBodyDeclaration())
      classBodyDecl.asInstanceOf[RecordBodyDeclaration]
    }
  }

  def toCompactConstructorDeclaration(ctx: CompactConstructorDeclarationContext): CompactConstructorDeclaration = {
    import scala.jdk.CollectionConverters._
    val constructorModifiers = ctx.constructorModifier().asScala.map(ModifierMapper.toConstructorModifier).toList
    val simpleTypeName = org.naderica.parser.sourcecode.java20.standard.grammar.SimpleTypeName(
      typeIdentifier = org.naderica.parser.sourcecode.java20.standard.grammar.TypeIdentifier(
        name = ctx.simpleTypeName().getText
      )
    )
    val constructorBody = org.naderica.parser.sourcecode.java20.standard.grammar.ConstructorBody(
      explicitConstructorInvocation = None,  // Compact constructors don't have explicit constructor invocation
      blockStatements = if (ctx.constructorBody().blockStatements() != null) {
        Some(ExpressionMapper.toBlockStatements(ctx.constructorBody().blockStatements()))
      } else {
        None
      }
    )
    
    CompactConstructorDeclaration(
      constructorModifiers = constructorModifiers,
      simpleTypeName = simpleTypeName,
      constructorBody = constructorBody
    )
  }
}