package org.gitpod.mailer;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Mailer {

	private static Logger logger = Logger.getLogger( Mailer.class );

    private int mailsDelivered = 0;
    private int packagesDelivered = 0; 

	public void confirmDelivery() {

		String now = new SimpleDateFormat( "MM-dd-yyyy:HH-mm-ss" ).format( new Date() );

		logger.info( "\n\n" + now + ": MAIL was delivered\n\n" );

        this.mailsDelivered++;
	}

	public void confirmPackageDelivery( String packageName, String address ) {

		String now = new SimpleDateFormat( "MM-dd-yyyy:HH-mm-ss" ).format( new Date() );

		logger.info( "\n\n" + now + ": '" + packageName + "' package was delivered to " + address + "\n\n" );

        this.packagesDelivered++;
	}

    public int mailsDelivered() {
        return this.mailsDelivered;
    }
    
    public int packagesDelivered() {
        return this.packagesDelivered;
    }
}
