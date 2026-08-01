/*
  Copyrights 2020 Axiata Digital Labs Pvt Ltd.
  All Rights Reserved.

  These material are unpublished, proprietary, confidential source
  code of Axiata Digital Labs Pvt Ltd (ADL) and constitute a TRADE
  SECRET of ADL.

  ADL retains all title to and intellectual property rights in these
  materials.

 */
package com.adl.et.telco.mvno.productcatalog;

import com.adl.et.telco.dte.mvno.plugin.tmf.TmfPlugin;
import com.adl.et.telco.dte.plugin.pluginenabler.annotations.EnableDtePlugins;
import io.prometheus.client.CollectorRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.metrics.export.prometheus.PrometheusScrapeEndpoint;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;

@SpringBootApplication
@DependsOn({"dteLoggingUtils"})
@Import({PrometheusScrapeEndpoint.class, CollectorRegistry.class})
@EnableDtePlugins
@TmfPlugin
public class Application {
	private static final Logger logger = Logger.getLogger(Application.class.getName());
	public static void main(String[] args) {
		try {
			setHostAddress();
		} catch (UnknownHostException e) {
			logger.severe("Error In Getting Host IP:" + e.getMessage());
		}
		SpringApplication.run(Application.class, args);
	}
	private static void setHostAddress() throws UnknownHostException {
		InetAddress ip = InetAddress.getLocalHost();
		String hostAddress = ip.getHostAddress();
		System.setProperty("host.address",hostAddress);
	}


}
