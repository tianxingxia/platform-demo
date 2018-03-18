/**
 * Filename: Initializer Description: Copyright: Copyright (c)2011 Company: bbk
 * @author: guosheng.zhu
 * @version: 1.0 Create at: 2011-10-10 ����06:50:34 Modification History: Date
 *           Author Version Description
 *           ------------------------------------------------------------------
 *           2011-10-10 guosheng.zhu 1.0 1.0 Version
 */
package com.yk.platform.common;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

public class Initializer extends HttpServlet {

	private static final long serialVersionUID = 2556855575925044091L;

	Logger logger = Logger.getLogger(Initializer.class);

	/**
	 * tomcat init
	 */
	public void init() throws ServletException {
		try {

		} catch (Exception e) {
			logger.error("", e);
		}
	}

}
