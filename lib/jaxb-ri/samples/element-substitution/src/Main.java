/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2017 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

// import java content classes generated by binding compiler
import org.example.*;

/*
 * $Id: Main.java,v 1.1 2007-12-05 00:49:23 kohsuke Exp $
 */
 
public class Main {
    
    // This sample application demonstrates how to modify a java content
    // tree and marshal it back to a xml data
    
    public static void main( String[] args ) {
        try {
            // create a JAXBContext capable of handling classes generated into
            // the org.example package
            JAXBContext jc = JAXBContext.newInstance( "org.example" );
            
            // create an Unmarshaller
            Unmarshaller u = jc.createUnmarshaller();
            
            // unmarshal a po instance document into a tree of Java content
            // objects composed of classes from the primer.po package.
	    Object folderE = u.unmarshal( new FileInputStream( "folder.xml" ) );

	    // get XML content.
	    // normalize that unmarshal returns either JAXBElement OR 
	    // class annotated with @XmlRootElement.
	    Folder folder = (Folder)(folderE instanceof JAXBElement ?
				     ((JAXBElement)folderE).getValue() :
				     folderE);

	    JAXBIntrospector inspect = jc.createJAXBIntrospector();
	    System.out.println("Unmarshalled xml element tag is:" + inspect.getElementName(folderE));

            System.out.println("Processing headers...");
            ObjectFactory of = new ObjectFactory();
	    for( JAXBElement<? extends Header> hdrE : folder.getHeaderE()) {
		Header hdr = hdrE.getValue();
		if (hdr instanceof OrderHeader) {
	           OrderHeader oh= (OrderHeader)hdr;
	           System.out.println("OrderHeader info:" + 
				      oh.getOrderInfo());
                } else if (hdr instanceof InvoiceHeader) {
	           InvoiceHeader ih = (InvoiceHeader)hdr;
	           System.out.println("InvoiceHeader info:" + 
				      ih.getInvoiceInfo());
                } else if (hdr instanceof BidHeader ) {
	           BidHeader bh= (BidHeader)hdr;
	           System.out.println("BidHeader info:" + 
				      bh.getBidInfo());
                }
            }

	    InvoiceHeader ih = of.createInvoiceHeader();
	    ih.setGeneralComment("New Comment");
	    ih.setInvoiceInfo("New Invoice Info");
	    JAXBElement newHdr = of.createInvoiceHeaderE(ih);
	    folder.getHeaderE().add(newHdr);

            // create a Marshaller and marshal to a file
            Marshaller m = jc.createMarshaller();
            m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
            m.marshal( folderE, System.out );
            
        } catch( JAXBException je ) {
            je.printStackTrace();
        } catch( IOException ioe ) {
            ioe.printStackTrace();
        }
    }
}
