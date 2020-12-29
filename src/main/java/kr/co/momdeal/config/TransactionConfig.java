package kr.co.momdeal.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@Configuration
@EnableAspectJAutoProxy
@MapperScan({"kr.co.momdeal.mapper"})
@EnableTransactionManagement
public class TransactionConfig {
	  private static final int TX_METHOD_TIMEOUT = 300; //테스트용 : 타임아웃 120초, 운영용 : 20초로 바꿔야함. 
	    private static final String AOP_POINTCUT_EXPRESSION = "execution(* kr.co.momdeal..*Service.*(..))";
	    @Autowired
	    private DataSourceTransactionManager transactionManager;
	 
	    @Bean
	    @ConfigurationProperties(prefix = "spring.datasource.hikari")
	    public DataSource getDataSource() {
	    	
	        return DataSourceBuilder.create().build();
	    }
		@Bean
		public DataSourceTransactionManager txManager() {
			return new DataSourceTransactionManager(getDataSource());
		}

	    @Bean
	    public TransactionInterceptor txAdvice() {
	    	TransactionInterceptor txAdvice = new TransactionInterceptor();
	    	Properties txAttributes = new Properties();
	    	List<RollbackRuleAttribute> rollbackRules = new ArrayList<RollbackRuleAttribute>();
	    	rollbackRules.add(new RollbackRuleAttribute(Exception.class));
	    	DefaultTransactionAttribute readOnlyAttribute = new DefaultTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED);
	    	readOnlyAttribute.setReadOnly(true);
	    	readOnlyAttribute.setTimeout(TX_METHOD_TIMEOUT);
	        RuleBasedTransactionAttribute writeAttribute = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED, rollbackRules);
	        writeAttribute.setTimeout(TX_METHOD_TIMEOUT);
			String readOnlyTransactionAttributesDefinition = readOnlyAttribute.toString();
			String writeTransactionAttributesDefinition = writeAttribute.toString();
			txAttributes.setProperty("select*", readOnlyTransactionAttributesDefinition);
			txAttributes.setProperty("get*", readOnlyTransactionAttributesDefinition);
			txAttributes.setProperty("search*", readOnlyTransactionAttributesDefinition);
			txAttributes.setProperty("find*", readOnlyTransactionAttributesDefinition);
			txAttributes.setProperty("count*", readOnlyTransactionAttributesDefinition);
			txAttributes.setProperty("*", writeTransactionAttributesDefinition);
			txAdvice.setTransactionAttributes(txAttributes);
			txAdvice.setTransactionManager(transactionManager);
	        return txAdvice;
	    }
	    @Bean
	    public Advisor txAdviceAdvisor() {
	        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
	        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
	        return new DefaultPointcutAdvisor(pointcut, txAdvice());
	    }
}
