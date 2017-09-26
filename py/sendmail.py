#!/usr/bin/python -tt
# -*- coding: utf-8 -*-

from __future__ import print_function
from __future__ import unicode_literals
from __future__ import division
from __future__ import absolute_import
from builtins import open
from future import standard_library
standard_library.install_aliases()
import sys
import os
import string
import smtplib
import configparser
import codecs
# For guessing MIME type based on file name extension
import mimetypes
#from email.message import EmailMessage
#from email.policy import SMTP
from email.mime.base import MIMEBase
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
from email import Encoders
from email.header import Header
from email.Utils import formatdate
from email import Charset
from email.generator import Generator
from io import StringIO

# todo: reset vim tabwidth to 8 or default and use default tabwidth to indent!

# todo: set config section via command line arg!

def send_email(mailInfoFile):
    if os.path.exists(mailInfoFile):
        config = configparser.ConfigParser()
        config.read(mailInfoFile, encoding='utf-8')
        from_emails = config['PRIVATE']['from']
        to_emails = config['PRIVATE']['to']
        cc_emails = config['PRIVATE']['cc']
        bcc_emails = config['PRIVATE']['bcc']
        subject = config['PRIVATE']['subject']
        body = config['PRIVATE']['body']
        attachments = config['PRIVATE']['attachment']
        passwd = config['PRIVATE']['passwd']
        smtpServer = config['PRIVATE']['smtpServer']
        port = config['PRIVATE']['port']

        # debug
        print('from_emails:[', from_emails, ']')
        print('to_emails:[', to_emails, ']')
        print('cc_emails:[', cc_emails, ']')
        print('bcc_emails:[', bcc_emails, ']')
        print('subject:[', subject, ']')
        print('body:[', repr(body), ']')
        print('attachments:[', repr(attachments), ']')
        print('passwd:[', passwd, ']')
# todo: gmail
        print('smtpServer:[', smtpServer, ']')
        print('port:[', port, ']')

        msg = MIMEMultipart()
#        msg = EmailMessage()
        msg['From'] = from_emails
        msg['To'] = to_emails
        msg['cc'] = cc_emails
        msg['bcc'] = bcc_emails
        #msg['To'] = ', '.join(to_emails)
        #msg['cc'] = ', '.join(cc_emails)
        #msg['bcc'] = ', '.join(bcc_emails)
#https://docs.python.org/2/library/email-examples.html
#https://docs.python.org/3/library/email.examples.html
        msg.preamble = 'You will not see this in a MIME-aware mail reader.\n'

        emails = []
        emails.append(to_emails)
        emails.append(cc_emails)
        emails.append(bcc_emails)

        msg['Date'] = formatdate(localtime=True)

        # Default encoding mode set to Quoted Printable. Acts globally!
        Charset.add_charset('utf-8', Charset.QP, Charset.QP, 'utf-8')

        msg['Subject'] = '{}'.format(Header(subject, 'utf-8'))
        if body:
# todo: opt for rich format such as html
            msg.attach(MIMEText(body, 'plain', 'utf-8'))

        # file name may contain ',' e.g. Pixel Recursive Super
        # Resolution,1702.00783.pdf
        attachmentList = attachments.strip().split('@')
        if attachmentList:
            try:
                for filename in attachmentList:
                    filename = filename.strip()
                    if not os.path.isfile(filename):
                        continue
                    # Guess the content type based on the file's extension.
                    # Encoding will be ignored, although we should check for
                    # simple things like gzip'd or compressed files
                    ctype, encoding = mimetypes.guess_type(filename)
                    if ctype is None or encoding is not None:
                        # No guess could be made, or the file is encoded
                        # (compressed), so use a generic bag-of-bits type.
                        ctype = 'application/octet-stream'
                    maintype, subtype = ctype.split('/', 1)
                    with open(filename, 'rb') as fp:
#https://docs.python.org/3/library/email.examples.html
#                        msg.add_attachment(
#                            fp.read(),
#                            maintype=maintype,
#                            subtype=subtype,
#                            filename=filename)

#https://docs.python.org/2/library/email-examples.html
                        msgAttachment = MIMEBase(maintype, subtype)
                        msgAttachment.set_payload(fp.read())
                        # Encode the payload using Base64
                        Encoders.encode_base64(msgAttachment)

                        # todo1: escape special characters in file name: & ? ! etc., already escaped(\ , \& etc.) path results error!
                        #[Errno 2] No such file or directory: u'/mnt/0/Never\\ trust\\ a\\ girl--always\\ get\\ it\\ on\\ tape\\ and\\ bring\\ backup-XFj2oDvK3m0.mkv'
                        # workaround: leave spaces in paths unquoted and use '@' to delimit files
                        msgAttachment.add_header('Content-Disposition', 'attachment; filename="{}"'.format(os.path.basename(filename)))
                        msg.attach(msgAttachment)
            except IOError as e:
                # todo2: better detailed exception info
                msg = 'Error opening attachment file "{}"'.format(filename)
                print('except e:', e)
                print('os.path.basename(filename)', os.path.basename(filename))
                print(msg)
                sys.exit(1)

        # type(port): <type 'unicode'>
        print('type(port):', type(port))
        smtpObj = smtplib.SMTP(smtpServer, port.encode('ascii', 'ignore'))
        smtpObj.ehlo()
        smtpObj.starttls()
        smtpObj.login(from_emails, passwd)
        smtpObj.sendmail(from_emails, emails, msg.as_string())
        smtpObj.quit()
    else:
        print('Config file not found! Exiting!')
        sys.exit(1)


if __name__ == '__main__':
    print(sys.argv)
    send_email(sys.argv[1])
