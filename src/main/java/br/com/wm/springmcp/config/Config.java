package br.com.wm.springmcp.config;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import br.com.wm.springmcp.tools.VendasMcpTools;

@Configuration
public class Config {
	
	@Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
	
	@Bean
    public List<ToolCallback> danTools(VendasMcpTools vendasMcpTools) {
        return List.of(ToolCallbacks.from(vendasMcpTools));
    }

}
