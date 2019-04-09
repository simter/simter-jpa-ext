package tech.simter.jpa.eclipselink;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Collections;
import java.util.Map;

@Configuration
@ConditionalOnClass({
  LocalContainerEntityManagerFactoryBean.class,
  EntityManager.class,
  org.eclipse.persistence.jpa.JpaHelper.class
})
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
public class EclipseLinkJpaConfiguration extends JpaBaseConfiguration {
  protected EclipseLinkJpaConfiguration(
    DataSource dataSource,
    JpaProperties properties,
    ObjectProvider<JtaTransactionManager> jtaTransactionManager,
    ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
    super(dataSource, properties, jtaTransactionManager, transactionManagerCustomizers);
  }

  @Override
  protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
    return new EclipseLinkJpaVendorAdapter();
  }

  @Override
  protected Map<String, Object> getVendorProperties() {
    return Collections.emptyMap();
  }
}
