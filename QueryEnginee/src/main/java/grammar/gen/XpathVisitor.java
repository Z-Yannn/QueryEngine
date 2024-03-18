// Generated from /Users/zhijie/Desktop/UCSD/CSE232B/QueryEnginee/src/main/java/grammar/Xpath.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link XpathParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface XpathVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code childrenAp}
	 * labeled alternative in {@link XpathParser#ap}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChildrenAp(XpathParser.ChildrenApContext ctx);
	/**
	 * Visit a parse tree produced by the {@code allAp}
	 * labeled alternative in {@link XpathParser#ap}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAllAp(XpathParser.AllApContext ctx);
	/**
	 * Visit a parse tree produced by the {@code haveRp}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHaveRp(XpathParser.HaveRpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code childRp}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChildRp(XpathParser.ChildRpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code textRp}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTextRp(XpathParser.TextRpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parentRp}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParentRp(XpathParser.ParentRpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unionRp}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnionRp(XpathParser.UnionRpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code currentRp}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCurrentRp(XpathParser.CurrentRpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code filterRP}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterRP(XpathParser.FilterRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code allRP}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAllRP(XpathParser.AllRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code childrenRP}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChildrenRP(XpathParser.ChildrenRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tagRp}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTagRp(XpathParser.TagRpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code attributeRp}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributeRp(XpathParser.AttributeRpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code eqFilter}
	 * labeled alternative in {@link XpathParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqFilter(XpathParser.EqFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code haveFilter}
	 * labeled alternative in {@link XpathParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHaveFilter(XpathParser.HaveFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notFilter}
	 * labeled alternative in {@link XpathParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotFilter(XpathParser.NotFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code strFilter}
	 * labeled alternative in {@link XpathParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStrFilter(XpathParser.StrFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andFilter}
	 * labeled alternative in {@link XpathParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndFilter(XpathParser.AndFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code isFilter}
	 * labeled alternative in {@link XpathParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsFilter(XpathParser.IsFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpFilter}
	 * labeled alternative in {@link XpathParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpFilter(XpathParser.RpFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orFilter}
	 * labeled alternative in {@link XpathParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrFilter(XpathParser.OrFilterContext ctx);
	/**
	 * Visit a parse tree produced by {@link XpathParser#doc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoc(XpathParser.DocContext ctx);
	/**
	 * Visit a parse tree produced by {@link XpathParser#tagName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTagName(XpathParser.TagNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link XpathParser#attName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttName(XpathParser.AttNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link XpathParser#str}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStr(XpathParser.StrContext ctx);
	/**
	 * Visit a parse tree produced by {@link XpathParser#fileName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFileName(XpathParser.FileNameContext ctx);
}