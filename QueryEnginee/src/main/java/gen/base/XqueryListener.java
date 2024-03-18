// Generated from /Users/zhijie/Desktop/UCSD/CSE232B/QueryEnginee/src/main/java/grammar/Xquery.g4 by ANTLR 4.13.1
package gen.base;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link XqueryParser}.
 */
public interface XqueryListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code strXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterStrXQ(XqueryParser.StrXQContext ctx);
	/**
	 * Exit a parse tree produced by the {@code strXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitStrXQ(XqueryParser.StrXQContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unionXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterUnionXQ(XqueryParser.UnionXQContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unionXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitUnionXQ(XqueryParser.UnionXQContext ctx);
	/**
	 * Enter a parse tree produced by the {@code flwrXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterFlwrXQ(XqueryParser.FlwrXQContext ctx);
	/**
	 * Exit a parse tree produced by the {@code flwrXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitFlwrXQ(XqueryParser.FlwrXQContext ctx);
	/**
	 * Enter a parse tree produced by the {@code tagXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterTagXQ(XqueryParser.TagXQContext ctx);
	/**
	 * Exit a parse tree produced by the {@code tagXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitTagXQ(XqueryParser.TagXQContext ctx);
	/**
	 * Enter a parse tree produced by the {@code allXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterAllXQ(XqueryParser.AllXQContext ctx);
	/**
	 * Exit a parse tree produced by the {@code allXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitAllXQ(XqueryParser.AllXQContext ctx);
	/**
	 * Enter a parse tree produced by the {@code apXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterApXQ(XqueryParser.ApXQContext ctx);
	/**
	 * Exit a parse tree produced by the {@code apXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitApXQ(XqueryParser.ApXQContext ctx);
	/**
	 * Enter a parse tree produced by the {@code letXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterLetXQ(XqueryParser.LetXQContext ctx);
	/**
	 * Exit a parse tree produced by the {@code letXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitLetXQ(XqueryParser.LetXQContext ctx);
	/**
	 * Enter a parse tree produced by the {@code haveXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterHaveXQ(XqueryParser.HaveXQContext ctx);
	/**
	 * Exit a parse tree produced by the {@code haveXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitHaveXQ(XqueryParser.HaveXQContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterVarXQ(XqueryParser.VarXQContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitVarXQ(XqueryParser.VarXQContext ctx);
	/**
	 * Enter a parse tree produced by the {@code joinXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterJoinXQ(XqueryParser.JoinXQContext ctx);
	/**
	 * Exit a parse tree produced by the {@code joinXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitJoinXQ(XqueryParser.JoinXQContext ctx);
	/**
	 * Enter a parse tree produced by the {@code childXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterChildXQ(XqueryParser.ChildXQContext ctx);
	/**
	 * Exit a parse tree produced by the {@code childXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitChildXQ(XqueryParser.ChildXQContext ctx);
	/**
	 * Enter a parse tree produced by {@link XqueryParser#forClause}.
	 * @param ctx the parse tree
	 */
	void enterForClause(XqueryParser.ForClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link XqueryParser#forClause}.
	 * @param ctx the parse tree
	 */
	void exitForClause(XqueryParser.ForClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link XqueryParser#letClause}.
	 * @param ctx the parse tree
	 */
	void enterLetClause(XqueryParser.LetClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link XqueryParser#letClause}.
	 * @param ctx the parse tree
	 */
	void exitLetClause(XqueryParser.LetClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link XqueryParser#whereClause}.
	 * @param ctx the parse tree
	 */
	void enterWhereClause(XqueryParser.WhereClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link XqueryParser#whereClause}.
	 * @param ctx the parse tree
	 */
	void exitWhereClause(XqueryParser.WhereClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link XqueryParser#returnClause}.
	 * @param ctx the parse tree
	 */
	void enterReturnClause(XqueryParser.ReturnClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link XqueryParser#returnClause}.
	 * @param ctx the parse tree
	 */
	void exitReturnClause(XqueryParser.ReturnClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link XqueryParser#joinClause}.
	 * @param ctx the parse tree
	 */
	void enterJoinClause(XqueryParser.JoinClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link XqueryParser#joinClause}.
	 * @param ctx the parse tree
	 */
	void exitJoinClause(XqueryParser.JoinClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link XqueryParser#tags}.
	 * @param ctx the parse tree
	 */
	void enterTags(XqueryParser.TagsContext ctx);
	/**
	 * Exit a parse tree produced by {@link XqueryParser#tags}.
	 * @param ctx the parse tree
	 */
	void exitTags(XqueryParser.TagsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orCond}
	 * labeled alternative in {@link XqueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterOrCond(XqueryParser.OrCondContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orCond}
	 * labeled alternative in {@link XqueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitOrCond(XqueryParser.OrCondContext ctx);
	/**
	 * Enter a parse tree produced by the {@code empCond}
	 * labeled alternative in {@link XqueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterEmpCond(XqueryParser.EmpCondContext ctx);
	/**
	 * Exit a parse tree produced by the {@code empCond}
	 * labeled alternative in {@link XqueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitEmpCond(XqueryParser.EmpCondContext ctx);
	/**
	 * Enter a parse tree produced by the {@code haveCond}
	 * labeled alternative in {@link XqueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterHaveCond(XqueryParser.HaveCondContext ctx);
	/**
	 * Exit a parse tree produced by the {@code haveCond}
	 * labeled alternative in {@link XqueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitHaveCond(XqueryParser.HaveCondContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andCond}
	 * labeled alternative in {@link XqueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterAndCond(XqueryParser.AndCondContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andCond}
	 * labeled alternative in {@link XqueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitAndCond(XqueryParser.AndCondContext ctx);
	/**
	 * Enter a parse tree produced by the {@code isCond}
	 * labeled alternative in {@link XqueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterIsCond(XqueryParser.IsCondContext ctx);
	/**
	 * Exit a parse tree produced by the {@code isCond}
	 * labeled alternative in {@link XqueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitIsCond(XqueryParser.IsCondContext ctx);
	/**
	 * Enter a parse tree produced by the {@code satCond}
	 * labeled alternative in {@link XqueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterSatCond(XqueryParser.SatCondContext ctx);
	/**
	 * Exit a parse tree produced by the {@code satCond}
	 * labeled alternative in {@link XqueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitSatCond(XqueryParser.SatCondContext ctx);
	/**
	 * Enter a parse tree produced by the {@code eqCond}
	 * labeled alternative in {@link XqueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterEqCond(XqueryParser.EqCondContext ctx);
	/**
	 * Exit a parse tree produced by the {@code eqCond}
	 * labeled alternative in {@link XqueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitEqCond(XqueryParser.EqCondContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notCond}
	 * labeled alternative in {@link XqueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterNotCond(XqueryParser.NotCondContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notCond}
	 * labeled alternative in {@link XqueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitNotCond(XqueryParser.NotCondContext ctx);
	/**
	 * Enter a parse tree produced by {@link XqueryParser#var}.
	 * @param ctx the parse tree
	 */
	void enterVar(XqueryParser.VarContext ctx);
	/**
	 * Exit a parse tree produced by {@link XqueryParser#var}.
	 * @param ctx the parse tree
	 */
	void exitVar(XqueryParser.VarContext ctx);
	/**
	 * Enter a parse tree produced by the {@code childrenAp}
	 * labeled alternative in {@link XqueryParser#ap}.
	 * @param ctx the parse tree
	 */
	void enterChildrenAp(XqueryParser.ChildrenApContext ctx);
	/**
	 * Exit a parse tree produced by the {@code childrenAp}
	 * labeled alternative in {@link XqueryParser#ap}.
	 * @param ctx the parse tree
	 */
	void exitChildrenAp(XqueryParser.ChildrenApContext ctx);
	/**
	 * Enter a parse tree produced by the {@code allAp}
	 * labeled alternative in {@link XqueryParser#ap}.
	 * @param ctx the parse tree
	 */
	void enterAllAp(XqueryParser.AllApContext ctx);
	/**
	 * Exit a parse tree produced by the {@code allAp}
	 * labeled alternative in {@link XqueryParser#ap}.
	 * @param ctx the parse tree
	 */
	void exitAllAp(XqueryParser.AllApContext ctx);
	/**
	 * Enter a parse tree produced by the {@code haveRp}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterHaveRp(XqueryParser.HaveRpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code haveRp}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitHaveRp(XqueryParser.HaveRpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code childRp}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterChildRp(XqueryParser.ChildRpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code childRp}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitChildRp(XqueryParser.ChildRpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code textRp}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterTextRp(XqueryParser.TextRpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code textRp}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitTextRp(XqueryParser.TextRpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parentRp}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterParentRp(XqueryParser.ParentRpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parentRp}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitParentRp(XqueryParser.ParentRpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unionRp}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterUnionRp(XqueryParser.UnionRpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unionRp}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitUnionRp(XqueryParser.UnionRpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code currentRp}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterCurrentRp(XqueryParser.CurrentRpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code currentRp}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitCurrentRp(XqueryParser.CurrentRpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code filterRP}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterFilterRP(XqueryParser.FilterRPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code filterRP}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitFilterRP(XqueryParser.FilterRPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code allRP}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterAllRP(XqueryParser.AllRPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code allRP}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitAllRP(XqueryParser.AllRPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code childrenRP}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterChildrenRP(XqueryParser.ChildrenRPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code childrenRP}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitChildrenRP(XqueryParser.ChildrenRPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code tagRp}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterTagRp(XqueryParser.TagRpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code tagRp}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitTagRp(XqueryParser.TagRpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code attributeRp}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterAttributeRp(XqueryParser.AttributeRpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code attributeRp}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitAttributeRp(XqueryParser.AttributeRpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code eqFilter}
	 * labeled alternative in {@link XqueryParser#f}.
	 * @param ctx the parse tree
	 */
	void enterEqFilter(XqueryParser.EqFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code eqFilter}
	 * labeled alternative in {@link XqueryParser#f}.
	 * @param ctx the parse tree
	 */
	void exitEqFilter(XqueryParser.EqFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code haveFilter}
	 * labeled alternative in {@link XqueryParser#f}.
	 * @param ctx the parse tree
	 */
	void enterHaveFilter(XqueryParser.HaveFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code haveFilter}
	 * labeled alternative in {@link XqueryParser#f}.
	 * @param ctx the parse tree
	 */
	void exitHaveFilter(XqueryParser.HaveFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notFilter}
	 * labeled alternative in {@link XqueryParser#f}.
	 * @param ctx the parse tree
	 */
	void enterNotFilter(XqueryParser.NotFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notFilter}
	 * labeled alternative in {@link XqueryParser#f}.
	 * @param ctx the parse tree
	 */
	void exitNotFilter(XqueryParser.NotFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code strFilter}
	 * labeled alternative in {@link XqueryParser#f}.
	 * @param ctx the parse tree
	 */
	void enterStrFilter(XqueryParser.StrFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code strFilter}
	 * labeled alternative in {@link XqueryParser#f}.
	 * @param ctx the parse tree
	 */
	void exitStrFilter(XqueryParser.StrFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andFilter}
	 * labeled alternative in {@link XqueryParser#f}.
	 * @param ctx the parse tree
	 */
	void enterAndFilter(XqueryParser.AndFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andFilter}
	 * labeled alternative in {@link XqueryParser#f}.
	 * @param ctx the parse tree
	 */
	void exitAndFilter(XqueryParser.AndFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code isFilter}
	 * labeled alternative in {@link XqueryParser#f}.
	 * @param ctx the parse tree
	 */
	void enterIsFilter(XqueryParser.IsFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code isFilter}
	 * labeled alternative in {@link XqueryParser#f}.
	 * @param ctx the parse tree
	 */
	void exitIsFilter(XqueryParser.IsFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpFilter}
	 * labeled alternative in {@link XqueryParser#f}.
	 * @param ctx the parse tree
	 */
	void enterRpFilter(XqueryParser.RpFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpFilter}
	 * labeled alternative in {@link XqueryParser#f}.
	 * @param ctx the parse tree
	 */
	void exitRpFilter(XqueryParser.RpFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orFilter}
	 * labeled alternative in {@link XqueryParser#f}.
	 * @param ctx the parse tree
	 */
	void enterOrFilter(XqueryParser.OrFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orFilter}
	 * labeled alternative in {@link XqueryParser#f}.
	 * @param ctx the parse tree
	 */
	void exitOrFilter(XqueryParser.OrFilterContext ctx);
	/**
	 * Enter a parse tree produced by {@link XqueryParser#doc}.
	 * @param ctx the parse tree
	 */
	void enterDoc(XqueryParser.DocContext ctx);
	/**
	 * Exit a parse tree produced by {@link XqueryParser#doc}.
	 * @param ctx the parse tree
	 */
	void exitDoc(XqueryParser.DocContext ctx);
	/**
	 * Enter a parse tree produced by {@link XqueryParser#tagName}.
	 * @param ctx the parse tree
	 */
	void enterTagName(XqueryParser.TagNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link XqueryParser#tagName}.
	 * @param ctx the parse tree
	 */
	void exitTagName(XqueryParser.TagNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link XqueryParser#attName}.
	 * @param ctx the parse tree
	 */
	void enterAttName(XqueryParser.AttNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link XqueryParser#attName}.
	 * @param ctx the parse tree
	 */
	void exitAttName(XqueryParser.AttNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link XqueryParser#str}.
	 * @param ctx the parse tree
	 */
	void enterStr(XqueryParser.StrContext ctx);
	/**
	 * Exit a parse tree produced by {@link XqueryParser#str}.
	 * @param ctx the parse tree
	 */
	void exitStr(XqueryParser.StrContext ctx);
	/**
	 * Enter a parse tree produced by {@link XqueryParser#fileName}.
	 * @param ctx the parse tree
	 */
	void enterFileName(XqueryParser.FileNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link XqueryParser#fileName}.
	 * @param ctx the parse tree
	 */
	void exitFileName(XqueryParser.FileNameContext ctx);
}