// Generated from /Users/zhijie/Desktop/UCSD/CSE232B/QueryEnginee/src/main/java/grammar/Xpath.g4 by ANTLR 4.13.1
package gen.base;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link XpathParser}.
 */
public interface XpathListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code childrenAp}
	 * labeled alternative in {@link XpathParser#ap}.
	 * @param ctx the parse tree
	 */
	void enterChildrenAp(XpathParser.ChildrenApContext ctx);
	/**
	 * Exit a parse tree produced by the {@code childrenAp}
	 * labeled alternative in {@link XpathParser#ap}.
	 * @param ctx the parse tree
	 */
	void exitChildrenAp(XpathParser.ChildrenApContext ctx);
	/**
	 * Enter a parse tree produced by the {@code allAp}
	 * labeled alternative in {@link XpathParser#ap}.
	 * @param ctx the parse tree
	 */
	void enterAllAp(XpathParser.AllApContext ctx);
	/**
	 * Exit a parse tree produced by the {@code allAp}
	 * labeled alternative in {@link XpathParser#ap}.
	 * @param ctx the parse tree
	 */
	void exitAllAp(XpathParser.AllApContext ctx);
	/**
	 * Enter a parse tree produced by the {@code haveRp}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterHaveRp(XpathParser.HaveRpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code haveRp}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitHaveRp(XpathParser.HaveRpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code childRp}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterChildRp(XpathParser.ChildRpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code childRp}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitChildRp(XpathParser.ChildRpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code textRp}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterTextRp(XpathParser.TextRpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code textRp}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitTextRp(XpathParser.TextRpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parentRp}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterParentRp(XpathParser.ParentRpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parentRp}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitParentRp(XpathParser.ParentRpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unionRp}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterUnionRp(XpathParser.UnionRpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unionRp}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitUnionRp(XpathParser.UnionRpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code currentRp}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterCurrentRp(XpathParser.CurrentRpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code currentRp}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitCurrentRp(XpathParser.CurrentRpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code filterRP}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterFilterRP(XpathParser.FilterRPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code filterRP}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitFilterRP(XpathParser.FilterRPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code allRP}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterAllRP(XpathParser.AllRPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code allRP}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitAllRP(XpathParser.AllRPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code childrenRP}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterChildrenRP(XpathParser.ChildrenRPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code childrenRP}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitChildrenRP(XpathParser.ChildrenRPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code tagRp}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterTagRp(XpathParser.TagRpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code tagRp}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitTagRp(XpathParser.TagRpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code attributeRp}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterAttributeRp(XpathParser.AttributeRpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code attributeRp}
	 * labeled alternative in {@link XpathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitAttributeRp(XpathParser.AttributeRpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code eqFilter}
	 * labeled alternative in {@link XpathParser#f}.
	 * @param ctx the parse tree
	 */
	void enterEqFilter(XpathParser.EqFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code eqFilter}
	 * labeled alternative in {@link XpathParser#f}.
	 * @param ctx the parse tree
	 */
	void exitEqFilter(XpathParser.EqFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code haveFilter}
	 * labeled alternative in {@link XpathParser#f}.
	 * @param ctx the parse tree
	 */
	void enterHaveFilter(XpathParser.HaveFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code haveFilter}
	 * labeled alternative in {@link XpathParser#f}.
	 * @param ctx the parse tree
	 */
	void exitHaveFilter(XpathParser.HaveFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notFilter}
	 * labeled alternative in {@link XpathParser#f}.
	 * @param ctx the parse tree
	 */
	void enterNotFilter(XpathParser.NotFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notFilter}
	 * labeled alternative in {@link XpathParser#f}.
	 * @param ctx the parse tree
	 */
	void exitNotFilter(XpathParser.NotFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code strFilter}
	 * labeled alternative in {@link XpathParser#f}.
	 * @param ctx the parse tree
	 */
	void enterStrFilter(XpathParser.StrFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code strFilter}
	 * labeled alternative in {@link XpathParser#f}.
	 * @param ctx the parse tree
	 */
	void exitStrFilter(XpathParser.StrFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andFilter}
	 * labeled alternative in {@link XpathParser#f}.
	 * @param ctx the parse tree
	 */
	void enterAndFilter(XpathParser.AndFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andFilter}
	 * labeled alternative in {@link XpathParser#f}.
	 * @param ctx the parse tree
	 */
	void exitAndFilter(XpathParser.AndFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code isFilter}
	 * labeled alternative in {@link XpathParser#f}.
	 * @param ctx the parse tree
	 */
	void enterIsFilter(XpathParser.IsFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code isFilter}
	 * labeled alternative in {@link XpathParser#f}.
	 * @param ctx the parse tree
	 */
	void exitIsFilter(XpathParser.IsFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpFilter}
	 * labeled alternative in {@link XpathParser#f}.
	 * @param ctx the parse tree
	 */
	void enterRpFilter(XpathParser.RpFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpFilter}
	 * labeled alternative in {@link XpathParser#f}.
	 * @param ctx the parse tree
	 */
	void exitRpFilter(XpathParser.RpFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orFilter}
	 * labeled alternative in {@link XpathParser#f}.
	 * @param ctx the parse tree
	 */
	void enterOrFilter(XpathParser.OrFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orFilter}
	 * labeled alternative in {@link XpathParser#f}.
	 * @param ctx the parse tree
	 */
	void exitOrFilter(XpathParser.OrFilterContext ctx);
	/**
	 * Enter a parse tree produced by {@link XpathParser#doc}.
	 * @param ctx the parse tree
	 */
	void enterDoc(XpathParser.DocContext ctx);
	/**
	 * Exit a parse tree produced by {@link XpathParser#doc}.
	 * @param ctx the parse tree
	 */
	void exitDoc(XpathParser.DocContext ctx);
	/**
	 * Enter a parse tree produced by {@link XpathParser#tagName}.
	 * @param ctx the parse tree
	 */
	void enterTagName(XpathParser.TagNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link XpathParser#tagName}.
	 * @param ctx the parse tree
	 */
	void exitTagName(XpathParser.TagNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link XpathParser#attName}.
	 * @param ctx the parse tree
	 */
	void enterAttName(XpathParser.AttNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link XpathParser#attName}.
	 * @param ctx the parse tree
	 */
	void exitAttName(XpathParser.AttNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link XpathParser#str}.
	 * @param ctx the parse tree
	 */
	void enterStr(XpathParser.StrContext ctx);
	/**
	 * Exit a parse tree produced by {@link XpathParser#str}.
	 * @param ctx the parse tree
	 */
	void exitStr(XpathParser.StrContext ctx);
	/**
	 * Enter a parse tree produced by {@link XpathParser#fileName}.
	 * @param ctx the parse tree
	 */
	void enterFileName(XpathParser.FileNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link XpathParser#fileName}.
	 * @param ctx the parse tree
	 */
	void exitFileName(XpathParser.FileNameContext ctx);
}