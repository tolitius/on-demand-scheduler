package org.gitpod.mailer;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.gitpod.scheduler.FixDelayTaskIntegrationTest;

public class Mailer {

	static Logger logger = Logger.getLogger( Mailer.class );
	
	public void confirmDelivery() {
		
		String now = new SimpleDateFormat( "MM-dd-yyyy:HH-mm-ss" ).format( new Date() );

		logger.info( "\n\n" + now + ": MAIL was delivered\n\n" );

        FixDelayTaskIntegrationTest.mailCount++;
	}
		
	public void confirmPackageDelivery( String packageName, String address ) {
		
		String now = new SimpleDateFormat( "MM-dd-yyyy:HH-mm-ss" ).format( new Date() );

		logger.info( "\n\n" + now + ": '" + packageName + "' package was delivered to " + address + "\n\n" );
        
        FixDelayTaskIntegrationTest.packageCount++;
	}		
}
