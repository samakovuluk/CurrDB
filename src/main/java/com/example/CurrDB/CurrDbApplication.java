package com.example.CurrDB;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Map;

@Controller
@SpringBootApplication
@EnableScheduling
public class CurrDbApplication {
	@Autowired
	DataSource dataSource;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args)
	{
		SpringApplication.run(CurrDbApplication.class, args);
	}
	

	@RequestMapping("/getcurrency")
	public String getcurr(ModelMap modelMap){
		modelMap.addAttribute("currency",getCurrencyfromDB().get(0).get("Currency"));
		return "currency";
	}

	public List<Map<String, Object>> getCurrencyfromDB(){
		return jdbcTemplate.queryForList("SELECT TOP 1 * FROM Currencies ORDER BY Date desc");
	}

}

