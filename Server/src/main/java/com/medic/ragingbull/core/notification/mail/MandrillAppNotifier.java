/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.notification.mail;

import com.google.inject.Inject;
import com.medic.ragingbull.config.MailChimpConfiguration;
import com.medic.ragingbull.config.MandrillAppConfiguration;
import com.medic.ragingbull.core.notification.Notifiable;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.stringtemplate.v4.ST;

import java.io.File;

/**
 * Created by Vamshi Molleti
 */
public class MandrillAppNotifier extends Notifiable {

    private final MandrillAppConfiguration mandrillAppConfiguration;
    private final MailChimpConfiguration mailChimpConfiguration;


    @Inject
    public MandrillAppNotifier(MandrillAppConfiguration mandrillAppConfiguration, MailChimpConfiguration mailChimpConfiguration) {
        this.mandrillAppConfiguration = mandrillAppConfiguration;
        this.mailChimpConfiguration = mailChimpConfiguration;
    }


    public void notify(String id, NotificationEvent event, Object message) {
//        if (event == NotificationEvent.SIGN_UP) {
//            notifySignUp(id, String.valueOf(message));
//
//            try {
//                ST consultationTemplate = new ST(FileUtils.readFileToString(new File("/Users/vamshi/RagingBull/Server/src/main/resources/ConsultationTemplate.txt")));
//                consultationTemplate.add("doctorName", "TestDoctorName");
//                consultationTemplate.add("specialization", "specialization");
//                consultationTemplate.add("qualification", "qualification");
//                consultationTemplate.add("locationName", "locationName");
//                consultationTemplate.add("locationAddress", "locationAddress");
//                consultationTemplate.add("locationPhone1", "locationPhone1");
//                consultationTemplate.add("locationPhone2", "locationPhone2");
//                consultationTemplate.add("email", "email");
//                consultationTemplate.add("website", "website");
//                consultationTemplate.add("patientName", "patientName");
//                consultationTemplate.add("patientAge", "patientAge");
//                consultationTemplate.add("patientSex", "patientSex");
//                consultationTemplate.add("patientMobile", "patientMobile");
//
//                String renderedTemplate = consultationTemplate.render();
//                PDDocument document = new PDDocument();
//                PDPage page = new PDPage();
//                document.addPage(page);
//
//                // Create a new font object selecting one of the PDF base fonts
//                PDFont font = PDType1Font.HELVETICA_BOLD;
//
//                // Start a new content stream which will "hold" the to be created content
//                PDPageContentStream contentStream = new PDPageContentStream(document, page);
//
//                // Define a text content stream using the selected font, moving the cursor and drawing the text "Hello World"
//                contentStream.beginText();
//                contentStream.setFont(font, 12);
//                contentStream.moveTextPositionByAmount(100, 700);
//                contentStream.drawString(renderedTemplate);
//                contentStream.endText();
//
//                // Make sure that the content stream is closed:
//                contentStream.close();
//
//                // Save the results and ensure that the document is properly closed:
//                document.save("Hello World.pdf");
//                document.close();
//            } catch(Exception e) {
//                e.printStackTrace();
//            }
//
//        } else if (event == NotificationEvent.CONSULTATION_SUBMITTED) {
//
//        }

    }

    private Boolean notifySignUp(String id, String message) {
//        MailDynamicContent dynamicContent = new MailDynamicContent("userName", message);
//        MailConfig config = new MailConfig("Welcome to RagingBull", )
//        Mail mail = new Mail(mandrillAppConfiguration.getApplicationKey(), "sign_up", dynamicContent, );
        return null;
    }
}
