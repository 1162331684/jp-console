package com.jeeplus.config;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.jeeplus.sys.service.dto.DataRuleDTO;
import com.jeeplus.sys.utils.UserUtils;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 自定义数据权限
 *
 * @Date 2021-05-7
 */
public class DataPermissionHandlerImpl implements DataPermissionHandler {

    private final Logger logger = LoggerFactory.getLogger(DataPermissionHandlerImpl.class);

    /**
     * 数据范围过滤
     * @param where 当前过滤的实体类
     */
    @Override
    public Expression getSqlSegment(Expression where, String mappedStatementId) {
        if(UserUtils.getCurrentUserDTO () == null ||  StrUtil.isBlank(UserUtils.getCurrentUserDTO ().getId())){
            return where;
        }
        List<DataRuleDTO> dataRuleList = UserUtils.getDataRuleList();

        // 不存在数据规则，则不过滤数据
        if (UserUtils.getCurrentUserDTO ().isAdmin() || dataRuleList.size() == 0) {
            return where;
        }

        // 数据范围
        Expression expression = null;
        for(DataRuleDTO dataRule : dataRuleList){
            if(mappedStatementId.equals(dataRule.getClassName())){
                Expression condExpression;
                try {
                    condExpression = CCJSqlParserUtil.parseCondExpression(dataRule.getDataScopeSql());
                    expression = ObjectUtils.isNotEmpty(expression) ? new AndExpression(expression, condExpression) : condExpression;
                } catch (JSQLParserException e) {
                    e.printStackTrace ();
                    logger.error("{}", e);
                }
            }

        }
        if(ObjectUtils.isEmpty (expression)) {
            return where;
        }else{
            return ObjectUtils.isNotEmpty(where) ? new AndExpression (where, new Parenthesis (expression)) : expression;
        }
    }

}
